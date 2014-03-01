package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;

public class InteractiveDetailsData {

	private Long playSource;

	public InteractiveDetailsData(Long playSource, Long contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue) {
		this.playSource = playSource;
	}

}
