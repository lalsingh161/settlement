package org.mifosplatform.billing.revenuemaster.data;

import java.math.BigDecimal;

public class RevenueMasterData {
	
	private Long id;
	private String playSource;
	private String contentName;
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

	public RevenueMasterData(Long id,Long headerId,String playSource, String contentName,
			String contentProvider, String channelName, String serviceName,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue,String chargeCode,String chargeType,Integer taxInclusive,Long clientId) {
		this.id=id;
		this.headerId=headerId;
		this.contentName=contentName;
		this.endUserPrice=endUserPrice;
		this.downloads= downloads;
		this.grossRevenue = grossRevenue;
		this.chargeCode = chargeCode;
		this.chargeType = chargeType;
		this.taxInclusive = taxInclusive;
		this.clientId = clientId;
	}

	public Long getId() {
		return id;
	}
	public String getContentName() {
		return contentName;
	}
	public String getChannelName() {
		return channelName;
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


	
}
