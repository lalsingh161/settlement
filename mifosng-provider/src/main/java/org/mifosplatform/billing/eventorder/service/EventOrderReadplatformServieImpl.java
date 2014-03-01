package org.mifosplatform.billing.eventorder.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.eventmaster.data.EventMasterData;
import org.mifosplatform.billing.eventorder.data.EventOrderData;
import org.mifosplatform.billing.eventorder.data.EventOrderDeviceData;
import org.mifosplatform.billing.onetimesale.data.OneTimeSaleData;
import org.mifosplatform.billing.order.data.OrderStatusEnumaration;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

@Service
public class EventOrderReadplatformServieImpl implements EventOrderReadplatformServie {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;
	  private CustomValidationProcedure myStoredProcedure; 

	@Autowired
	public EventOrderReadplatformServieImpl(final PlatformSecurityContext context,final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		 this. myStoredProcedure=new CustomValidationProcedure(this.jdbcTemplate.getDataSource());

	}

	

	@Override
	public List<OneTimeSaleData> retrieveEventOrderData(Long clientId) {
		EventOrderDataMapper mapper = new EventOrderDataMapper();

		String sql = "select " + mapper.schema()
				+ " where e.client_id = ? ";

		return this.jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	private static final class EventOrderDataMapper implements
			RowMapper<OneTimeSaleData> {

		public String schema() {
			return " e.id as orderid,e.client_id as clientId,e.event_id as eventId,e.eventprice_id as eventpriceId,0 as movieLink,e.booked_price as bookedPrice,"
			     +" e.charge_code as chargeCode,e.is_invoiced as isInvoiced from b_eventorder e ";

		}

		@Override
		public OneTimeSaleData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			Long orderid = rs.getLong("orderid");
			Long clientId = rs.getLong("clientId");
			String chargeCode = rs.getString("chargeCode");
			BigDecimal bookedPrice = rs.getBigDecimal("bookedPrice");
			String isInvoiced = rs.getString("isInvoiced");
			return new OneTimeSaleData(orderid,clientId, null, chargeCode, null,null, bookedPrice,isInvoiced,orderid,1l);

		}
	}

	@Override
	public boolean CheckClientCustomalidation(Long clientId) {
		 Map<String, Object> string= myStoredProcedure.execute(clientId);
	       
	       return  ((Boolean) string.get("status")).booleanValue();
	     
	}

	@Override
	public List<EventOrderDeviceData> getDevices(Long clientId) {
		final String sql = "select al.serial_no as serialNumber from b_allocation al where client_id=? union select oh.serial_number as serialNumber from b_owned_hardware oh where client_id=?";
		EventOrderDeviceMapper rowMapper = new EventOrderDeviceMapper();
		return this.jdbcTemplate.query(sql,rowMapper,new Object[]{clientId,clientId});
	}
	
	@SuppressWarnings("unused")
	private final class EventOrderDeviceMapper implements RowMapper<EventOrderDeviceData>{
		@Override
		public EventOrderDeviceData mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			final String serialNumber = rs.getString("serialNumber");
			return new EventOrderDeviceData(serialNumber);
		}
		
	}
	
	
	@Override
	public List<EventMasterData> getEvents() {
		
		final String sql = "select id as id, event_name as eventName, event_description as eventDescription from b_event_master where status=1";
		EventMasterDataMapper rowMapper = new EventMasterDataMapper();
		return this.jdbcTemplate.query(sql,rowMapper);
	}
	
	private final class EventMasterDataMapper implements RowMapper<EventMasterData>{
		@Override
		public EventMasterData mapRow(ResultSet rs, int rowNum) throws SQLException {
			final Long id = rs.getLong("id");
			final String eventName = rs.getString("eventName");
			final String eventDescription = rs.getString("eventDescription");
			return new EventMasterData(id,eventName,eventDescription);
		}
	}
	
	
	@Override
	public BigDecimal retriveEventPrice(String fType, String oType, Long clientId, Long eventId) {
		/*final String sql = "select price from b_event_pricing where format_type=? and Opt_type=? and client_typeid=(select category_type from m_client where m_client.id = ?) and event_id = ? and is_deleted='n' limit 1";*/
		final String sql = "select price from b_event_pricing where event_id = ? and is_deleted='n' limit 1";
		return jdbcTemplate.queryForObject(sql, new Object[]{eventId},BigDecimal.class);
	}
	
	@Override
	public Long getCurrentRow(String fType, String oType, Long clientId,Long eventId){
		final String sql = "select id from b_event_pricing where format_type=? and Opt_type=? and client_typeid=(select category_type from m_client where m_client.id = ?) and event_id = ? and is_deleted='n' limit 1";
		return jdbcTemplate.queryForLong(sql, new Object[]{fType,oType,clientId,eventId});
	}
	
	@Override
	public BigDecimal retriveEventPrice(Long eventId) {
		final String sql = "select price from b_event_pricing where event_id = ? and is_deleted='n' limit 1";
		return jdbcTemplate.queryForObject(sql, new Object[]{eventId},BigDecimal.class);
	}
	
	
	@Override
	public Long getClientType(Long cId) {
		final String sql = "select category_type from m_client where id = ?";
		return jdbcTemplate.queryForLong(sql,new Object[]{cId});
	}
	
	
	@Override
	public List<EventOrderData> getEventOrderGame(final Long clientId){
		
		final String sql = "select eo.event_bookeddate as eventBookedDate, eo.event_validtill as eventValidtill, eo.booked_price as bookedPrice, eo.charge_code as chargeCode, eod.format_type as source, eod.opt_type as partner,  em.event_name as eventName from b_eventorder eo inner join b_eventorder_details eod on eo.id=eod.eventorder_id inner join b_event_master em on eo.event_id=em.id where eo.client_id = ?";
		GameEventOrderMapper mapper = new GameEventOrderMapper();
		return jdbcTemplate.query(sql,mapper,new Object[]{clientId});
	}
	
	
	
	public static final class GameEventOrderMapper implements RowMapper<EventOrderData>{
		@Override
		public EventOrderData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final LocalDate bookedDate = JdbcSupport.getLocalDate(rs,"eventBookedDate");
			final LocalDate eventValidtill = JdbcSupport.getLocalDate(rs,"eventValidtill");
			final BigDecimal price = rs.getBigDecimal("bookedPrice");
			final String chargeCode = rs.getString("chargeCode");
			final String source = rs.getString("source");
			final String partner = rs.getString("partner");
			final String eventName = rs.getString("eventName");
			return new EventOrderData(bookedDate,eventValidtill,price,chargeCode,source,partner,eventName);
		}
	}
	
	@Override
	public Long getEventId(String eventName) {
		final String sql = "select id from b_event_master where event_name = ?";
		return jdbcTemplate.queryForLong(sql,new Object[]{eventName});
	}
	
	@Override
	public List<EventOrderData> getTheClientEventOrders(Long clientId) {
		
		try{
		EventOrderMapper mapper = new EventOrderMapper();
		String sql = "select " + mapper.schema()
				+ " and eo.client_id = ? order by id";

		return this.jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}catch(EmptyResultDataAccessException accessException){
	           return null;	
	}
	}

	private static final class EventOrderMapper implements
			RowMapper<EventOrderData> {

		public String schema() {
			return " eo.id AS id,eo.event_bookeddate AS bookedDate,em.event_name AS eventName,eo.charge_code AS chargeCode,eo.event_status as status,eo.booked_price AS price" +
					" FROM b_eventorder eo, b_event_master em WHERE eo.event_id = em.id ";

		}

		@Override
		public EventOrderData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			Long orderid = rs.getLong("id");
			LocalDate bookedDate = JdbcSupport.getLocalDate(rs,"bookedDate");
			String eventName = rs.getString("eventName");
			BigDecimal bookedPrice = rs.getBigDecimal("price");
			String chargeCode = rs.getString("chargeCode");
			int statusId = rs.getInt("status");
			EnumOptionData Enumstatus=OrderStatusEnumaration.OrderStatusType(statusId);
			String status=Enumstatus.getValue();
			return new EventOrderData(orderid,bookedDate,eventName,bookedPrice,chargeCode,status);

		}
		}
}
@SuppressWarnings("unused")
final class CustomValidationProcedure extends StoredProcedure {
        
  final static String SQL ="custom_validate_eventorders";//.getProcedureName();

	public CustomValidationProcedure(DataSource dataSource) {
		 super(dataSource,SQL);  
		 declareParameter( new SqlParameter( "clientid", Types.INTEGER) ); //declaring sql in parameter to pass input   
	      declareParameter( new SqlOutParameter( "status", Types.BOOLEAN ) ); //declaring sql out parameter   
	      compile();     
        }
		
			@SuppressWarnings("rawtypes")
		public Map execute(int emp_id) {
				//MyStoredProcedure myStoredProcedure;
				  Map<String, Object> results = super.execute(emp_id);   
				   return (Map) results.get("name"); 
    }
			
			
}
