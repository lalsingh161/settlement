package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDate;

public class RawInteractiveHeaderDetailData {

	
	private Long id;
	private Long clientCode;
	private String clientName;
	private String activityMonth;
	private String businessLine;
	private String mediaCategory;
	private String playSource;
	private String contentName;
	private String contentProvider;
	private String channelName;
	private String serviceName;
	private BigDecimal endUserPrice;
	private Long downloads;
	private BigDecimal grossRevenue;
	private Date dataUploadedDate;

	public RawInteractiveHeaderDetailData(Long id,Long custCode, String custName,
			String activityMonth, String businessLine, String mediaCategory,
			String playSource, String contentName, String contentProvider,
			String channelName, String serviceName, BigDecimal endUserPrice,
			Long downloads, BigDecimal grossRevenue, LocalDate dataUploadedDate) {
		
		this.id = id;
		this.clientCode = custCode;
		this.clientName = custName;
		this.activityMonth = activityMonth;
		this.businessLine = businessLine;
		this.mediaCategory = mediaCategory;
		this.playSource = playSource;
		this.contentName = contentName;
		this.contentProvider = contentProvider;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.endUserPrice = endUserPrice;
		this.downloads = downloads;
		this.grossRevenue = grossRevenue;
		this.dataUploadedDate = dataUploadedDate==null?null:dataUploadedDate.toDate();
		

	}
}
