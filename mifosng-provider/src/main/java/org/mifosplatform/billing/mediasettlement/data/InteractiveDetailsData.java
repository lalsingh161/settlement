package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;

public class InteractiveDetailsData {

	private Long playSource;
	private String contentName;
	private Long contentProvider;
	private Long channelName;
	private Long serviceName;
	private BigDecimal endUserPrice;
	private BigDecimal downloads;
	private BigDecimal grossRevenue;
	private Object mediaCategory;

	public InteractiveDetailsData(Long playSource, String contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue, Long mediaCategory) {
		this.playSource = playSource;
		this.contentName = contentName;
		this.contentProvider = contentProvider;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.endUserPrice = endUserPrice;
		this.downloads = downloads;
		this.grossRevenue = grossRevenue;
		this.mediaCategory = mediaCategory;
	}

}
