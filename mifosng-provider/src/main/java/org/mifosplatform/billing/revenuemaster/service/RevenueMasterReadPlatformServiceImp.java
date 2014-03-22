package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.revenuemaster.data.DeductionData;
import org.mifosplatform.billing.revenuemaster.data.GenerateInteractiveHeaderData;
import org.mifosplatform.billing.revenuemaster.data.OperatorShareData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class RevenueMasterReadPlatformServiceImp implements RevenueMasterReadplatformService{

	private final PlatformSecurityContext context;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public RevenueMasterReadPlatformServiceImp(
			final PlatformSecurityContext context,
			final TenantAwareRoutingDataSource dataSource) {

		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@Override
	public List<GenerateInteractiveHeaderData> retriveInteractiveHeaderDetails(Long clientId){
			final String sql = "select ih.id as id, ih.client_id as clientId,ih.activity_month as activityMonth," +
					"im.int_event_code as businessLine from bp_interactive_header ih,bp_intevent_master im where ih.business_line=im.id and ih.client_id=? and ih.is_deleted = 'N' order by ih.id asc";
			HeaderDataMapper mapper = new HeaderDataMapper();
			return jdbcTemplate.query(sql, mapper, new Object[]{clientId});
		}
		
		final private static class HeaderDataMapper implements RowMapper<GenerateInteractiveHeaderData>{
			@Override
			public GenerateInteractiveHeaderData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				final Long id = rs.getLong("id");
				final Long clientId = rs.getLong("clientId");
				//final Long externalId = rs.getLong("externalId");
				final String businessLine = rs.getString("businessLine");
				final String activityMonth = rs.getString("activityMonth");
				/*final LocalDate dataUploadedDate = JdbcSupport.getLocalDate(rs, "dataUploadedDate");
				final String mediaCategory = rs.getString("mediaCategory");*/
				return new GenerateInteractiveHeaderData(id,clientId,businessLine,activityMonth);
			}
		}

		@Override
		public List<RevenueMasterData> retriveAllinteractiveDetails(Long id) {
			final String sql="select itd.id as id ,itd.interactive_header_id as headerId,ih.activity_month as activityMonth,"+
											"(select partner_name from bp_account where id = itd.content_provider) as contentProvider,"+
											"(select partner_name from bp_account where id = itd.channel_name) as channelName,"+
											"(select partner_name from bp_account where id = itd.service_name) as serviceName,"+
											"itd.end_user_price as endUserPrice, itd.downloads as downloads, itd.gross_revenue as grossRevenue,ih.client_id as clientId,im.charge_code  as chargeCode,"+
                                            "cc.charge_type as chargeType,cc.tax_inclusive as taxInclusive from bp_interactive_detail itd, "+
										   "bp_interactive_header ih, bp_intevent_master im,b_charge_codes cc where ih.id=itd.interactive_header_id and im.id=ih.business_line and cc.charge_code=im.charge_code  and itd.interactive_header_id=?";

						InteractiveDetailMapper mapper=new InteractiveDetailMapper();
						return jdbcTemplate.query(sql, mapper, new Object[]{id}) ;
					}
					
					private final static class InteractiveDetailMapper implements
					RowMapper<RevenueMasterData> {
						
					   @Override
					    public RevenueMasterData mapRow(ResultSet rs, int rowNum) throws SQLException {
						 final Long id=rs.getLong("id");  
						 final Long headerId=rs.getLong("headerId");
					     final String activityMonth = rs.getString("activityMonth");
					    /* final String contentName = rs.getString("contentName");*/
					     final String contentProvider = rs.getString("contentProvider");
					     final String channelName = rs.getString("channelName");
					     final String serviceName = rs.getString("serviceName");
					     final BigDecimal endUserPrice = rs.getBigDecimal("endUserPrice");
					     final BigDecimal downloads = rs.getBigDecimal("downloads");
					     final BigDecimal grossRevenue = rs.getBigDecimal("grossRevenue");
					     final String chargeCode = rs.getString("chargeCode");
						 final String chargeType = rs.getString("chargeType");
						 final Integer taxInclusive = rs.getInt("taxInclusive");
						 final Long clientId = rs.getLong("clientId");
						return new RevenueMasterData(id,headerId,activityMonth,contentProvider,channelName,serviceName,endUserPrice,downloads,grossRevenue,chargeCode,chargeType,taxInclusive,clientId);
					   }
					
					}

					@Override
					public List<DeductionData> retriveOperatorDeductionData(
							Long clientId) {
							final String sql = "select od.id as id ,od.client_id as clientId, od.ded_code as deductionCode, od.ded_value as deductionValue , (select code_value from m_code_value  where id = dc.ded_type) as deductionType,dc.circle as circle from bp_operator_deduction od,bp_deduction_codes dc where client_id = ? and od.ded_code=dc.ded_code and dc.is_deleted='N' order by id";
							DeductionMapper mapper = new DeductionMapper();
							return jdbcTemplate.query(sql,mapper,new Object[]{clientId});
						}
						 private final static class DeductionMapper implements RowMapper<DeductionData>{
							
							@Override
							public DeductionData mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								final Long id = rs.getLong("id");
								final Long clientId = rs.getLong("clientId");
								final String deductionCode = rs.getString("deductionCode");
								final BigDecimal deductionValue = rs.getBigDecimal("deductionValue");
								final String deductionType = rs.getString("deductionType");
								final Integer circle = rs.getInt("circle");
								return new DeductionData(id,clientId, deductionCode, deductionValue,deductionType,circle);
							}
						}

						@Override
						public List<OperatorShareData> retriveOperatorRevenueShareData(
								Long clientId) {
							final String sql="select rm.id as id ,(select ded_code from bp_deduction_codes where id=rm.revenue_share_code) as revenueShareCode,(select code_value from m_code_value where id=rm.revenue_share_type) as revenueShareType,rp.id as revenueParamId, rp.start_value as startValue,rp.end_value as endValue,rp.percentage as revenuePercentage,rp.flat as revenueFlat from bp_revenue_share_master rm,bp_revenue_share_params rp where rm.id=rp.revenue_share_master_id and rm.client_id=?";
							OperatorShareMapper mapper=new OperatorShareMapper();
							return jdbcTemplate.query(sql,mapper,new Object[]{clientId});
						}						
						
						
						 private final static class OperatorShareMapper implements RowMapper<OperatorShareData>{
								
								@Override
								public OperatorShareData mapRow(ResultSet rs, int rowNum)
										throws SQLException {
									final Long id = rs.getLong("id");
									//final String businessLine = rs.getString("businessLine");
									final String revenueShareCode = rs.getString("revenueShareCode");
									final String revenueShareType = rs.getString("revenueShareType");
									final Long revenueParamId = rs.getLong("revenueParamId");
									final  BigDecimal startValue = rs.getBigDecimal("startValue");
									final BigDecimal endValue = rs.getBigDecimal("endValue");
									final BigDecimal revenuePercentage = rs.getBigDecimal("revenuePercentage");
									final BigDecimal revenueFlat = rs.getBigDecimal("revenueFlat");
									return new OperatorShareData(id,revenueShareCode, revenueShareType, revenueParamId,startValue,endValue,revenuePercentage,revenueFlat);
								}
							}
						
						
					}

			
		
		
		
		
		
	


