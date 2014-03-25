package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class OperatorShareData {

	private Long id;
	private String revenueShareCode;
	private String revenueShareType;
	private Long revenueParamId;
	private BigDecimal startValue;
	private BigDecimal endValue;
	private BigDecimal revenuePercentage;
	private BigDecimal revenueFlat;
	private BigDecimal Percentage=BigDecimal.ZERO;;

	public OperatorShareData(Long id, String revenueShareCode,
			String revenueShareType, Long revenueParamId, BigDecimal startValue,
			BigDecimal endValue, BigDecimal revenuePercentage, BigDecimal revenueFlat) {
		this.id = id;
		this.revenueShareCode = revenueShareCode;
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

	public String getRevenueShareCode() {
		return revenueShareCode;
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

	public void setPercentage(BigDecimal revenuePercentage) {
		
		this.Percentage = revenuePercentage;
	}

	public BigDecimal getPercentage() {
		return Percentage;
	}
    
	
}
