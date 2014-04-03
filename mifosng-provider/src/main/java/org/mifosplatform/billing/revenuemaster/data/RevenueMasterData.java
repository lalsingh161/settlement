package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class RevenueMasterData {
	
	private Long id;
	private String activityMonth;
	private String contentProvider;
	private String channelName;
	private String serviceName;
	private BigDecimal endUserPrice;
	private BigDecimal downloads;
	private BigDecimal grossRevenue;
	private Long headerId;
	private String chargeCode;
	private String chargeType;
	private Integer taxInclusive;
	private Long clientId;
	private BigDecimal netRevenueAmount=BigDecimal.ZERO;
	private BigDecimal detailChargeTaxAmount=BigDecimal.ZERO;
	private BigDecimal operatorShareAmount=BigDecimal.ZERO;
	private BigDecimal revenueAmountOfIg=BigDecimal.ZERO;
	private String clientType;
	private BigDecimal platformShareAmount=BigDecimal.ZERO;;

	public RevenueMasterData(Long id,Long headerId,String activityMonth,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue,String chargeCode,String chargeType,Integer taxInclusive,Long clientId,String clientType) {
		this.id=id;
		this.headerId=headerId;
		this.activityMonth=activityMonth;
		this.endUserPrice=endUserPrice;
		this.downloads= downloads;
		this.grossRevenue = grossRevenue;
		this.chargeCode = chargeCode;
		this.chargeType = chargeType;
		this.taxInclusive = taxInclusive;
		this.clientId = clientId;
		this.clientType = clientType;
	}

	public Long getId() {
		return id;
	}
	

	public BigDecimal getEndUserPrice() {
		return endUserPrice;
	}

	public BigDecimal getDownloads() {
		return downloads;
	}

	public BigDecimal getGrossRevenue() {
		return grossRevenue;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public String getChargeType() {
		return chargeType;
	}

	public Integer getTaxInclusive() {
		return taxInclusive;
	}

	public Long getClientId() {
		return clientId;
	}

	public BigDecimal getNetRevenueAmount() {
		return netRevenueAmount;
	}

	/*public void setNetRevenueAmount(BigDecimal netRevenueAmount) {
		this.netRevenueAmount = netRevenueAmount;
	}*/

	public void updateNetRevenueAmount(BigDecimal netRevenueAmount) {
		
		this.netRevenueAmount=this.netRevenueAmount.add(netRevenueAmount);
						}

	public BigDecimal getDetailChargeTaxAmount() {
		return detailChargeTaxAmount;
	}

	public void setDetailChargeTaxAmount(BigDecimal detailChargeTaxAmount){
		this.detailChargeTaxAmount = detailChargeTaxAmount;
	}

	public void setOperatorShareAmount(BigDecimal operatorShareAmount) {
		
		this.operatorShareAmount = operatorShareAmount;
	}

	public String getActivityMonth() {
		return activityMonth;
	}


	public BigDecimal getOperatorShareAmount() {
		return operatorShareAmount;
	}

	public void setRevenueAmountOfIg(BigDecimal revenueAmountOfIg) {
	
		this.revenueAmountOfIg =revenueAmountOfIg;
	}

	public BigDecimal getRevenueAmountOfIg() {
		return revenueAmountOfIg;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getClientType() {
		return clientType;
	}

	public void setplatformShareAmount(BigDecimal platformShareAmount) {
	
		this.platformShareAmount = platformShareAmount;
	}

	public BigDecimal getPlatformShareAmount() {
		return platformShareAmount;
	}
	
}
