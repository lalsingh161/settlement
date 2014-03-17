package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class DeductionData {

	private Long clientId;
	private BigDecimal deductionValue;
	private String deductionCode;
	private Long id;
	private String deductionType;
	private Integer circle;
	private BigDecimal wpcTaxAmount;

	public DeductionData(Long id, Long clientId, String deductionCode,
			BigDecimal deductionValue,String deductionType,Integer circle) {
	     this.id=id; 
		this.clientId = clientId;
		this.deductionValue = deductionValue;
		this.deductionCode = deductionCode;
		this.deductionType =  deductionType;
		this.circle = circle;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public Integer getCircle() {
		return circle;
	}

	public void setWpcTaxAmount(BigDecimal taxAmount) {
		this.wpcTaxAmount=taxAmount;
		
	}

	public BigDecimal getWpcTaxAmount() {
		return wpcTaxAmount;
	}
	
}
