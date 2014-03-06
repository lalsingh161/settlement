package org.mifosplatform.billing.businessline.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "bp_intevent_master")
public class BusinessLineMaster extends AbstractPersistable<Long> {

	
	@Column(name = "int_event_code")
	private String eventCode;
	
	@Column(name = "int_event_desc")
	private String eventDescription;
	
	@Column(name = "int_event_status")
	private Integer status;
	
	@Column(name = "charge_code")
	private String chargeCode;
	
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "event" , orphanRemoval = true)
	private List<BusinessLineDetails> details = new ArrayList<BusinessLineDetails>();
	
	public static BusinessLineMaster fromJsom(final JsonCommand command) {
		
		String eventName = command.stringValueOfParameterNamed("eventName");
		String eventDescription = command.stringValueOfParameterNamed("eventDescription");
		Integer status = command.integerValueOfParameterNamed("status");
		String chargeCode = command.stringValueOfParameterNamed("chargeCode");	
		return new BusinessLineMaster(eventName,eventDescription,status,chargeCode);
	}
	
	
	public BusinessLineMaster (String eventName, String eventDescription, Integer status,String chargeCode ) {

		this.eventCode = eventName;
		this.eventDescription = eventDescription;
		this.status = status;
		this.chargeCode=chargeCode;
		
	}
	
	public BusinessLineMaster() {
		
	}
	
	public List<BusinessLineDetails> getDetails() {
		return details;
	}

	public void addMediaDetails(BusinessLineDetails details){
		details.update(this);
		this.details.add(details);
	}
	
	public List<BusinessLineDetails> getEventDetails() {
		return details;
	}
	
	public Map<String, Object> updateBusinessData(JsonCommand command) {
		
		
		final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);
		final String eventName = "eventName";
		if (command.isChangeInStringParameterNamed(eventName,
				this.eventCode)) {
			final String newValue = command
					.stringValueOfParameterNamed("eventName");
			actualChanges.put(eventName, newValue);
			this.eventCode = StringUtils.defaultIfEmpty(newValue, null);
		}
		final String eventDescription = "eventDescription";
		if (command.isChangeInStringParameterNamed(eventDescription,
				this.eventDescription)) {
			final String newValue = command
					.stringValueOfParameterNamed("eventDescription");
			actualChanges.put(eventDescription, newValue);
			this.eventDescription = StringUtils.defaultIfEmpty(newValue, null);
		}
		final String chargeCode = "chargeCode";
		if (command.isChangeInStringParameterNamed(chargeCode,
				this.chargeCode)) {
			final String newValue = command
					.stringValueOfParameterNamed("chargeCode");
			actualChanges.put(chargeCode, newValue);
			this.chargeCode = StringUtils.defaultIfEmpty(newValue, null);
		}
		final String status = "status";
		if (command.isChangeInIntegerParameterNamed(status,
				this.status)) {
			final Integer newValue = command.integerValueOfParameterNamed("status");
			//actualChanges.put(chargeCode, newValue);
			this.status =newValue;
		}
		
		return actualChanges;
	}
	
}
