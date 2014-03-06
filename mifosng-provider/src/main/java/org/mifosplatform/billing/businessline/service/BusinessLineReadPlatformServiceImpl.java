package org.mifosplatform.billing.businessline.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.order.data.OrderStatusEnumaration;
import org.mifosplatform.billing.plan.domain.StatusTypeEnum;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class BusinessLineReadPlatformServiceImpl implements
BusinessLineReadPlatformService {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BusinessLineReadPlatformServiceImpl (final TenantAwareRoutingDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<EnumOptionData> retrieveNewStatus() {
		EnumOptionData active = OrderStatusEnumaration
				.OrderStatusType(StatusTypeEnum.ACTIVE);
		EnumOptionData inactive = OrderStatusEnumaration
				.OrderStatusType(StatusTypeEnum.INACTIVE);
		List<EnumOptionData> categotyType = Arrays.asList(active, inactive);
		return categotyType;

	}

	@Override
	public List<BusinessLineData> retrieveEventMasterData() {
		try {
			final EventMasterMapper eventMasterMapper = new EventMasterMapper();
			
			final String sql = "select " + eventMasterMapper.eventMasterSchema();
			return jdbcTemplate.query(sql, eventMasterMapper,new Object[] {});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	private static final class EventMasterMapper implements RowMapper<BusinessLineData> {
		public String eventMasterSchema() {
			return "bim.id as id,bim.int_event_code as code,bim.int_event_desc as description,"+
					"bim.int_event_status as status,bim.charge_code as chargeCode from bp_intevent_master bim";
		}
		@Override
		public BusinessLineData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			
			final Long id = rs.getLong("id");
			final String eventName = rs.getString("code");
			final String eventDescription = rs.getString("description");
			final Integer statusId = rs.getInt("status");
			final String chargeCode = rs.getString("chargeCode");
			String status=OrderStatusEnumaration.OrderStatusType(statusId).getValue();
			return new BusinessLineData(id, eventName, eventDescription,status,chargeCode);
		}
	}

	@Override
	public BusinessLineData retrieveEventMasterDetails(Integer eventId){
		String sql = "select bm.id as id,bm.int_event_code as eventName, bm.int_event_desc as eventDescription," +
						" bm.int_event_status as status,bm.charge_code as chargeCode"
						+ " from bp_intevent_master bm where bm.id=?";
		RowMapper<BusinessLineData> rm = new EventMapper();
		return this.jdbcTemplate.queryForObject(sql, rm,new Object[]{eventId});
	}
	
	private static final class EventMapper implements RowMapper<BusinessLineData> {
		@Override
		public BusinessLineData mapRow(final ResultSet rs,final int rowNum) throws SQLException {
			Long id = rs.getLong("id");
			String eventName = rs.getString("eventName");
			String eventDescription = rs.getString("eventDescription");
			Long status = rs.getLong("status");
			String chargeData = rs.getString("chargeCode"); 
			//Long category=rs.getLong("Category");
			return new BusinessLineData(id, eventName, eventDescription, status,chargeData,null);
		}
	}
	
	@Override
	public List<BusinessLineData> retrieveEventDetailsData(Integer eventId) {
		String sql = "Select bl.id as id, bl.intevent_id as eventId,bl.int_category as categoryId,m.title as title  "
					  +" from bp_intevent_dtl bl,b_media_asset m where bl.int_category=m.id and intevent_id = ?";
		
		RowMapper<BusinessLineData> rm = new EventDetailsMapper();
		
		return this.jdbcTemplate.query(sql, rm,new Object[]{eventId});
	}

	private static final class EventDetailsMapper implements RowMapper<BusinessLineData> {
		@Override
		public BusinessLineData mapRow(final ResultSet rs,final int rowNum) throws SQLException {
			Long id = rs.getLong("id");
			Long eventId = rs.getLong("eventId");
			Long categoryId = rs.getLong("categoryId");
			String mediaTitle = rs.getString("title");
			return new BusinessLineData(id, eventId, categoryId, mediaTitle);
		}
	}
}	

	
