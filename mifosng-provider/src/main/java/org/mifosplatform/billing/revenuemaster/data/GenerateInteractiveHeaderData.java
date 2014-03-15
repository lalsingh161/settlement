package org.mifosplatform.billing.revenuemaster.data;

import java.util.Date;

public class GenerateInteractiveHeaderData {
	
	private Long id;
	private Long clientId;
	private Long externalId;
	private String businessLineStr;
	private String mediaCategory;
	private String activityMonth;

	public GenerateInteractiveHeaderData(Long id, Long clientId,
			Long externalId, String businessLineStr, String activityMonth,
			String mediaCategory) {
		
		this.id=id;
		this.clientId= clientId;
		this.externalId= externalId;
		this.businessLineStr = businessLineStr;
		this.mediaCategory = mediaCategory;
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

	public String getBusinessLineStr() {
		return businessLineStr;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public String getActivityMonth() {
		return activityMonth;
	}
	
}
