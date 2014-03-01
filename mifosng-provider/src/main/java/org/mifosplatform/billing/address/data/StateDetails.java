package org.mifosplatform.billing.address.data;

public class StateDetails  {
	
	private Long id;
	private String stateName;
	private String stateCode;

	public StateDetails(Long id, String stateName) {

	    this.id=id;
	    this.stateName=stateName;
	
	}

	public StateDetails(Long id, String stateCode, String stateName) {
		this.id = id;
		this.stateCode = stateCode;
		this.stateName = stateName;
	}

	public Long getId() {
		return id;
	}

	public String getStateName() {
		return stateName;
	}

}
