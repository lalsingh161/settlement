package org.mifosplatform.billing.eventorder.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.LocalDate;
import org.mifosplatform.billing.eventmaster.domain.EventMaster;
import org.mifosplatform.billing.eventpricing.domain.EventPricing;
import org.mifosplatform.billing.media.exceptions.NoEventPriceFoundException;
import org.mifosplatform.billing.media.exceptions.NoPricesFoundException;
import org.mifosplatform.billing.mediadevice.data.MediaDeviceData;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name = "b_eventorder")
public class EventOrder extends AbstractAuditableCustom<AppUser, Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "eventprice_id")
	private Long eventpriceId;


	@Column(name = "booked_price")
	private Double bookedPrice;

	@Column(name = "event_bookeddate")
	private Date eventBookedDate;

	@Column(name = "event_validtill")
	private Date eventValidtill;

	@Column(name = "event_status")
	private int eventStatus;

	@Column(name = "charge_code")
	private String chargeCode;

	@Column(name = "cancel_flag")
	private char cancelFlag;
  
	public void setBookedPrice(Double bookedPrice) {
		this.bookedPrice = bookedPrice;
	}

	@Column(name="is_invoiced")
	private char isInvoiced='N';
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "eventOrder" , orphanRemoval = true)
	private List<EventOrderdetials> eventOrderdetials = new ArrayList<EventOrderdetials>();
	

	 public EventOrder() {
		// TODO Auto-generated constructor stub
			
	}

	public EventOrder(Long eventId, LocalDate eventBookedDate,Date eventValidtill, Long eventPriceId, Double bookedPrice,
			Long clientId, int status, String chargeCode) {

	       this.eventId=eventId;
	       this.eventBookedDate=eventBookedDate.toDate();
	       this.eventValidtill=eventValidtill;
	       this.bookedPrice=bookedPrice;
	       this.clientId=clientId;
	      // this.movieLink=movieLink;
	       this.eventpriceId=eventPriceId;
	       this.eventStatus=status;
	       this.chargeCode=chargeCode;
	}

	public static EventOrder fromJson(JsonCommand command, EventMaster eventMaster, MediaDeviceData details, final Long cType,final Long eventId) {
		 
		 
		 final Long clientId = command.longValueOfParameterNamed("cId");//details.getClientId();
		 final LocalDate eventBookedDate=command.localDateValueOfParameterNamed("eventBookedDate");
		 Long clientType=command.longValueOfParameterNamed("categoryType");//details.getClientTypeId();
		 	if(clientType == null){
		 		clientType = cType;
		 	}
		 final String optType=command.stringValueOfParameterNamed("optType");
		 final String formatType=command.stringValueOfParameterNamed("formatType");
		 final Date eventValidtill=eventMaster.getEventValidity();
		 
		 if(eventMaster.getEventPricings() == null || eventMaster.getEventPricings().isEmpty()){
			 throw new NoEventPriceFoundException();
		 }
		 
		 final Long eventPriceId=eventMaster.getEventPricings().get(0).getId();
		 Double bookedPrice=0D;
		 List<EventPricing> eventPricings=eventMaster.getEventPricings();
		 
		 for(EventPricing eventPricing:eventPricings){
			 if(eventPricing.getClientType().longValue()==clientType.longValue() && eventPricing.getFormatType().equalsIgnoreCase(formatType) && eventPricing.getOptType().equalsIgnoreCase(optType)){
				   bookedPrice=eventPricing.getPrice();
			 }
		 }
		if(bookedPrice == null){
			
				 throw new NoPricesFoundException();
			
		}
			
		 final int status=eventMaster.getStatus();
		 final String chargeCode=eventMaster.getChargeCode();
		 
		 return new EventOrder(eventId,eventBookedDate,eventValidtill,eventPriceId,bookedPrice,clientId,status,chargeCode);
	}

	public Long getClientId() {
		return clientId;
	}

	public Long getEventId() {
		return eventId;
	}

	public Long getEventpriceId() {
		return eventpriceId;
	}



	public Double getBookedPrice() {
		return bookedPrice;
	}

	public Date getEventBookedDate() {
		return eventBookedDate;
	}

	public Date getEventValidtill() {
		return eventValidtill;
	}

	public int getEventStatus() {
		return eventStatus;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public char getCancelFlag() {
		return cancelFlag;
	}

	

	public void setInvoiced() {
		this.isInvoiced='Y';
		
	}

	public void addEventOrderDetails(EventOrderdetials eventOrderdetials) {
             eventOrderdetials.update(this);
             this.eventOrderdetials.add(eventOrderdetials);
		
	}

	public char getIsInvoiced() {
		return isInvoiced;
	}

	public List<EventOrderdetials> getEventOrderdetials() {
		return eventOrderdetials;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public void setEventpriceId(Long eventpriceId) {
		this.eventpriceId = eventpriceId;
	}

	public void setEventBookedDate(Date eventBookedDate) {
		this.eventBookedDate = eventBookedDate;
	}

	public void setEventValidtill(Date eventValidtill) {
		this.eventValidtill = eventValidtill;
	}

	public void setEventStatus(int eventStatus) {
		this.eventStatus = eventStatus;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public void setCancelFlag(char cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public void setIsInvoiced(char isInvoiced) {
		this.isInvoiced = isInvoiced;
	}

	public void setEventOrderdetials(List<EventOrderdetials> eventOrderdetials) {
		this.eventOrderdetials = eventOrderdetials;
	}

	
	
}
