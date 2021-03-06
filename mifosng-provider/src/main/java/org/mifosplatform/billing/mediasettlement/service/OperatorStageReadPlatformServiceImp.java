package org.mifosplatform.billing.mediasettlement.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mediasettlement.data.OperatorStageDetailData;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class OperatorStageReadPlatformServiceImp implements
		OperatorStageDetailReadPlatformService {
	
	
	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;
	
	@Autowired
	public OperatorStageReadPlatformServiceImp(final TenantAwareRoutingDataSource dataSource, final PlatformSecurityContext context) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.context = context;
	}
	
	
	
	@Override
	public List<OperatorStageDetailData> retriveAllData(){
		
		final String sql = "select * from bp_raw_headerdetail where is_processed='N' order by id";
		RawInteractiveHeaderDetailMapper mapper = new RawInteractiveHeaderDetailMapper();
		return jdbcTemplate.query(sql, mapper, new Object[]{});
	}
	
	
	private final class RawInteractiveHeaderDetailMapper implements RowMapper<OperatorStageDetailData>{
		@Override
		public OperatorStageDetailData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long clientName = rs.getLong("cust_name");
			final Long clientCode = rs.getLong("cust_code");
			final String activityMonth = rs.getString("activity_month");
			final LocalDate dataUploadedDate = JdbcSupport.getLocalDate(rs, "data_upload_date");
			final Long businessLine = rs.getLong("business_line");
			final Long mediaCategory = rs.getLong("media_category");
			final Long playSource = rs.getLong("play_source");
			final String contentName = rs.getString("content_name");
			final Long contentProvider = rs.getLong("content_provider");
			final Long channelName = rs.getLong("channel_name");
			final Long serviceName = rs.getLong("service_name");
			final BigDecimal endUserPrice = rs.getBigDecimal("end_user_price");
			final Long downloads = rs.getLong("downloads");
			final BigDecimal grossRevenue = rs.getBigDecimal("gross_revenue");
			return new OperatorStageDetailData(id,clientCode, clientName, activityMonth, businessLine, mediaCategory, playSource, contentName, contentProvider, channelName, serviceName, endUserPrice, downloads, grossRevenue,dataUploadedDate);
		}
	}

}
