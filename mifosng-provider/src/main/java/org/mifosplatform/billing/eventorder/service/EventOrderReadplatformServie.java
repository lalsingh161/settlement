package org.mifosplatform.billing.eventorder.service;

import java.math.BigDecimal;
import java.util.List;

import org.mifosplatform.billing.eventmaster.data.EventMasterData;
import org.mifosplatform.billing.eventorder.data.EventOrderData;
import org.mifosplatform.billing.eventorder.data.EventOrderDeviceData;
import org.mifosplatform.billing.onetimesale.data.OneTimeSaleData;

public interface EventOrderReadplatformServie {
	
	List<OneTimeSaleData> retrieveEventOrderData(Long clientId);

	boolean CheckClientCustomalidation(Long clientId);

	List<EventOrderData> getTheClientEventOrders(Long clientId);

	List<EventOrderDeviceData> getDevices(Long clientId);

	List<EventMasterData> getEvents();

	BigDecimal retriveEventPrice(String fType, String oType, Long clientId,
			Long eventId);

	Long getCurrentRow(String fType, String oType, Long clientId,Long eventId);

	BigDecimal retriveEventPrice(Long eventId);

	Long getClientType(Long cId);

	List<EventOrderData> getEventOrderGame(Long clientId);

	Long getEventId(String eventName);

	
}
