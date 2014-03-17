package org.mifosplatform.billing.revenuemaster.data;

import java.util.Date;

public class GenerateInteractiveHeaderData {
	
	private Long id;
	private Long clientId;
	private Long externalId;
	private String businessLine;
	private String mediaCategory;
	private String activityMonth;

	public GenerateInteractiveHeaderData(Long id, Long clientId,
			Long externalId, String businessLine, String activityMonth) {
		
		this.id=id;
		this.clientId= clientId;
		this.externalId= externalId;
		this.businessLine = businessLine;
		this.activityMonth = activityMonth;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public String getBusinessLine() {
		return businessLine;
	}

	public String getActivityMonth() {
		return activityMonth;
	}
	
}
