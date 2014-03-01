package org.mifosplatform.billing.eventorder.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.eventmaster.data.EventMasterData;
import org.mifosplatform.billing.eventpricing.data.ClientTypeData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAccountData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class EventOrderData {
	
	private Long id;
	private String eventName;
	private LocalDate bookedDate;
	private BigDecimal eventPrice;
	private String chargeCode;
	private String status;
	private List<EventOrderDeviceData> devices;
	private List<EventMasterData> events;
	private List<EnumOptionData> optType;
	private Collection<MCodeData> codes;
	private List<ClientTypeData> clientType;
	private Collection<MCodeData> partner;
	private Collection<MCodeData> source;
	private LocalDate eventValidtillData;
	private BigDecimal price;
	
	/*
	 * The reason why we are declaring sourceS and partnerS is because variables with name partner,source are already
	 * declared above as a List Type;
	 * */
	
	private String sourceS;
	private String partnerS;
	private Collection<PartnerAccountData> accountPartner;

	public EventOrderData(Long orderid, LocalDate bookedDate, String eventName,
			BigDecimal bookedPrice, String chargeCode, String status) {
		
		this.id=orderid;
		this.eventName=eventName;
		this.bookedDate=bookedDate;
		this.eventPrice=bookedPrice;
		this.chargeCode=chargeCode;
		this.status=status;
	}

	public EventOrderData(List<EventOrderDeviceData> devices, List<EventMasterData> events, List<EnumOptionData> optType, Collection<MCodeData> codes, List<ClientTypeData> clientType) {
		this.devices = devices;
		this.events = events;
		this.optType = optType;
		this.codes = codes;
		this.clientType = clientType;
	}

	public EventOrderData() {
		// TODO Auto-generated constructor stub
	}

	public EventOrderData(BigDecimal eventPrice) {
		this.eventPrice = eventPrice;
	}
	
	public EventOrderData(List<EventMasterData> events,
			Collection<MCodeData> partner, Collection<MCodeData> source) {
	
		this.events = events;
		this.partner = partner;
		this.source = source;
	}

	public EventOrderData(LocalDate bookedDate, LocalDate eventValidtill,
			BigDecimal price, String chargeCode, String source,
			String partner, String eventName) {
		
		this.bookedDate = bookedDate;
		this.eventValidtillData = eventValidtill;
		this.price = price;
		this.chargeCode = chargeCode;
		this.sourceS = source;
		this.partnerS = partner;
		this.eventName = eventName;

	}

	

	public EventOrderData(Collection<MCodeData> source,
			List<EventMasterData> events,
			Collection<PartnerAccountData> partner) {
		this.source = source;
		this.events = events;
		this.accountPartner = partner;
	}

	public Long getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

	public LocalDate getBookedDate() {
		return bookedDate;
	}

	public BigDecimal getEventPrice() {
		return eventPrice;
	}
	public void setEventPrice(final BigDecimal eventPrice){
		this.eventPrice = eventPrice;
	}
	public String getChargeCode() {
		return chargeCode;
	}

	public String getStatus() {
		return status;
	}

}
