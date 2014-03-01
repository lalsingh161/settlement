package org.mifosplatform.billing.mediasettlement.data;


public class OperatorDeductionData {

	
	private Long clientId;
	private Long deductionValue;
	private String deductionCode;
	private Long id;
	private String deduction;
	private Character deductionType;
	private String deductionSource;
	private String deductionCategory;
	
	public OperatorDeductionData() {
		
	}

	public OperatorDeductionData(final Long clientId, final String deductionCode, final Long deductionValue){
		this.clientId = clientId;
		this.deductionValue = deductionValue;
		this.deductionCode = deductionCode;
	}

	public OperatorDeductionData(Long id, String deductionCode,
			String deduction, Character deductionType, String deductionSource,
			String deductionCategory) {
		
		this.id = id;
		this.deductionCode = deductionCode;
		this.deduction = deduction;
		this.deductionType = deductionType;
		this.deductionSource = deductionSource;
		this.deductionCategory = deductionCategory;
		
		
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getDeductionValue() {
		return deductionValue;
	}

	public void setDeductionValue(Long deductionValue) {
		this.deductionValue = deductionValue;
	}

	public String getDeductionCode() {
		return deductionCode;
	}

	public void setDeductionCode(String deductionCode) {
		this.deductionCode = deductionCode;
	}
	
	
	
}
