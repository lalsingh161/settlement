package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;

public class InteractiveDetailsData {

	private Long playSource;
	private Long contentName;
	private Long contentProvider;
	private Long channelName;
	private Long serviceName;
	private BigDecimal endUserPrice;
	private BigDecimal downloads;
	private BigDecimal grossRevenue;

	public InteractiveDetailsData(Long playSource, Long contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue) {
		this.playSource = playSource;
		this.contentName = contentName;
		this.contentProvider = contentProvider;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.endUserPrice = endUserPrice;
		this.downloads = downloads;
		this.grossRevenue = grossRevenue;
	}

}
