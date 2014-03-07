package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;


public class OperatorDeductionData {

	
	private Long clientId;
	private BigDecimal deductionValue;
	private String deductionCode;
	private Long id;
	private String deduction;
	private Character deductionType;
	private String deductionSource;
	private String deductionCategory;
	private Collection<OperatorDeductionData> deductionCodes;
	private OperatorDeductionData deductionCodeValues;
	
	public OperatorDeductionData() {
		
	}

	public OperatorDeductionData(final Long id,final Long clientId, final String deductionCode, final BigDecimal deductionValue){
		this.id = id;
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

	public OperatorDeductionData(OperatorDeductionData operatorDeductionDatas,
			Collection<OperatorDeductionData> deductionCodes) {
		this.deductionCodes = deductionCodes;
		this.deductionCodeValues = operatorDeductionDatas;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public BigDecimal getDeductionValue() {
		return deductionValue;
	}

	public void setDeductionValue(BigDecimal deductionValue) {
		this.deductionValue = deductionValue;
	}

	public String getDeductionCode() {
		return deductionCode;
	}

	public void setDeductionCode(String deductionCode) {
		this.deductionCode = deductionCode;
	}
	
	
	
}
