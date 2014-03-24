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
			 String businessLine, String activityMonth) {
		
		this.id=id;
		this.clientId= clientId;
		this.businessLine = businessLine;
		this.activityMonth = activityMonth;
		
	}

	public Long getId() {
		return id;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getBusinessLine() {
		return businessLine;
	}

	public String getActivityMonth() {
		return activityMonth;
	}
	
}
