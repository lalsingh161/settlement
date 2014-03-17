package org.mifosplatform.billing.mediasettlement.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mediasettlement.data.RawInteractiveHeaderDetailData;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class RawInteractiveHeaderDetailReadPlatformServiceImp implements
		RawInteractiveHeaderDetailReadPlatformService {
	
	
	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;
	
	@Autowired
	public RawInteractiveHeaderDetailReadPlatformServiceImp(final TenantAwareRoutingDataSource dataSource, final PlatformSecurityContext context) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.context = context;
	}
	
	
	
	@Override
	public List<RawInteractiveHeaderDetailData> retriveAllData(){
		
		final String sql = "select * from bp_raw_headerdetail where is_processed='N'";
		RawInteractiveHeaderDetailMapper mapper = new RawInteractiveHeaderDetailMapper();
		return jdbcTemplate.query(sql, mapper, new Object[]{});
	}
	
	
	private final class RawInteractiveHeaderDetailMapper implements RowMapper<RawInteractiveHeaderDetailData>{
		@Override
		public RawInteractiveHeaderDetailData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String clientName = rs.getString("cust_name");
			final Long clientCode = rs.getLong("cust_code");
			final String activityMonth = rs.getString("activity_month");
			final LocalDate dataUploadedDate = JdbcSupport.getLocalDate(rs, "data_upload_date");
			final String businessLine = rs.getString("business_line");
			final String mediaCategory = rs.getString("media_category");
			final String playSource = rs.getString("play_source");
			final String contentName = rs.getString("content_name");
			final String contentProvider = rs.getString("content_provider");
			final String channelName = rs.getString("channel_name");
			final String serviceName = rs.getString("service_name");
			final BigDecimal endUserPrice = rs.getBigDecimal("end_user_price");
			final Long downloads = rs.getLong("downloads");
			final BigDecimal grossRevenue = rs.getBigDecimal("gross_revenue");
			return new RawInteractiveHeaderDetailData(id,clientCode, clientName, activityMonth, businessLine, mediaCategory, playSource, contentName, contentProvider, channelName, serviceName, endUserPrice, downloads, grossRevenue,dataUploadedDate);
		}
	}

}
