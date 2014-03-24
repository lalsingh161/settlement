package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class OperatorShareData {

	private Long id;
	private String businessLine;
	private String revenueShareType;
	private Long revenueParamId;
	private BigDecimal startValue;
	private BigDecimal endValue;
	private BigDecimal revenuePercentage;
	private BigDecimal revenueFlat;

	public OperatorShareData(Long id, String businessLine,
			String revenueShareType, Long revenueParamId, BigDecimal startValue,
			BigDecimal endValue, BigDecimal revenuePercentage, BigDecimal revenueFlat) {
		this.id = id;
		this.businessLine = businessLine;
		this.revenueShareType = revenueShareType;
		this.revenueParamId = revenueParamId;
		this.startValue = startValue;
		this.endValue = endValue;
		this.revenuePercentage = revenuePercentage;
		this.revenueFlat = revenueFlat;
	}

	public Long getId() {
		return id;
	}

	public String getBusinessLine() {
		return businessLine;
	}

	public String getRevenueShareType() {
		return revenueShareType;
	}

	public Long getRevenueParamId() {
		return revenueParamId;
	}

	public BigDecimal getStartValue() {
		return startValue;
	}

	public BigDecimal getEndValue() {
		return endValue;
	}

	public BigDecimal getRevenuePercentage() {
		return revenuePercentage;
	}

	public BigDecimal getRevenueFlat() {
		return revenueFlat;
	}

}