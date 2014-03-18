package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDate;

public class RawInteractiveHeaderDetailData {

	
	private Long id;
	private Long clientCode;
	private Long clientName;
	private String activityMonth;
	private Long businessLine;
	private Long mediaCategory;
	private Long playSource;
	private String contentName;
	private Long contentProvider;
	private Long channelName;
	private Long serviceName;
	private BigDecimal endUserPrice;
	private Long downloads;
	private BigDecimal grossRevenue;
	private Date dataUploadedDate;

	
	
	
	public RawInteractiveHeaderDetailData(Long id, Long clientCode,
			Long clientName, String activityMonth, Long businessLine,
			Long mediaCategory, Long playSource, String contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, Long downloads,
			BigDecimal grossRevenue, LocalDate dataUploadedDate) {
		this.id = id;
		this.clientCode = clientCode;
		this.clientName = clientName;
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
		this.dataUploadedDate = dataUploadedDate==null?null:dataUploadedDate.toDate();//--
	}


	public InteractiveHeaderData getHeaderData(){
		
		return new InteractiveHeaderData(this.clientCode,this.clientName,this.activityMonth,this.dataUploadedDate,this.businessLine);
	}
	
	public InteractiveDetailsData getDetailData(){
		return new InteractiveDetailsData(mediaCategory,playSource,contentName,contentProvider,channelName,serviceName,endUserPrice,downloads,grossRevenue);
	}
}
