package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class DeductionTaxesData {

	private String deductionCode;
	private BigDecimal deductionValue;
	private BigDecimal taxAmount;
	//private BigDecimal employeeDeductionAmount;
	//private BigDecimal entertainmentTaxAmount;
	private BigDecimal netRevenueAmount;	
	private String deductionType;
	private Long detailId;

	public DeductionTaxesData(final String deductionCode,final BigDecimal deductionValue,final String deductionType, final BigDecimal taxAmount, Long detailId) {
		this.deductionCode = deductionCode;
		this.deductionValue = deductionValue;
		this.deductionType = deductionType;
		this.taxAmount = taxAmount;
		this.detailId = detailId;
		
	}

	public String getDeductionCode() {
		return deductionCode;
	}

	public BigDecimal getDeductionValue() {
		return deductionValue;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

/*	public BigDecimal getEmployeeDeductionAmount() {
		return employeeDeductionAmount;
	}

	public BigDecimal getEntertainmentTaxAmount() {
		return entertainmentTaxAmount;
	}*/

	public BigDecimal getNetRevenueAmount() {
		return netRevenueAmount;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public Long getDetailId() {
		return detailId;
	}
	
	

}
