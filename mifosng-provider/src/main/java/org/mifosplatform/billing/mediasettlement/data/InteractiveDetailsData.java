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
	private Long mediaCategory;
	
	
	
	
	private Long downloadsL;
	private Long id;
	private String playSourceStr;
	private String conentProviderStr;
	private String channelNameStr;
	private String serviceNameStr;
	private String mediaCategoryStr;
	private Long interactiveHeader;

	
	
	
	public InteractiveDetailsData(Long id, Long interactiveHeader,Long playSource, String contentName,
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
		this.id = id;
		this.interactiveHeader = interactiveHeader;
	}




	



	public InteractiveDetailsData(Long mediaCategory, Long playSource,
			String contentName, Long contentProvider, Long channelName,
			Long serviceName, BigDecimal endUserPrice, Long downloads,
			BigDecimal grossRevenue) {
		this.mediaCategory = mediaCategory;
		this.playSource = playSource;
		this.contentName = contentName;
		this.contentProvider = contentProvider;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.endUserPrice = endUserPrice;
		this.downloadsL = downloads;
		this.grossRevenue = grossRevenue;
		
	}








	public InteractiveDetailsData(Long id, String playSource,
			String contentName, String contentProvider, String channelName,
			String serviceName, String mediaCategory,
			BigDecimal endUserPrice, Long downloads, BigDecimal grossRevenue) {
		 
		this.id = id;
		this.playSourceStr = playSource;
		this.contentName = contentName;
		this.conentProviderStr = contentProvider;
		this.channelNameStr = channelName;
		this.serviceNameStr = serviceName;
		this.mediaCategoryStr = mediaCategory;
		this.endUserPrice = endUserPrice;
		this.downloadsL = downloads;
		this.grossRevenue = grossRevenue;
	}








	public Long getPlaySource() {
		return playSource;
	}








	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
	}








	public String getContentName() {
		return contentName;
	}








	public void setContentName(String contentName) {
		this.contentName = contentName;
	}








	public Long getContentProvider() {
		return contentProvider;
	}








	public void setContentProvider(Long contentProvider) {
		this.contentProvider = contentProvider;
	}








	public Long getChannelName() {
		return channelName;
	}








	public void setChannelName(Long channelName) {
		this.channelName = channelName;
	}








	public Long getServiceName() {
		return serviceName;
	}








	public void setServiceName(Long serviceName) {
		this.serviceName = serviceName;
	}








	public BigDecimal getEndUserPrice() {
		return endUserPrice;
	}








	public void setEndUserPrice(BigDecimal endUserPrice) {
		this.endUserPrice = endUserPrice;
	}








	public BigDecimal getDownloads() {
		return downloads;
	}








	public void setDownloads(BigDecimal downloads) {
		this.downloads = downloads;
	}








	public BigDecimal getGrossRevenue() {
		return grossRevenue;
	}








	public void setGrossRevenue(BigDecimal grossRevenue) {
		this.grossRevenue = grossRevenue;
	}








	public Long getMediaCategory() {
		return mediaCategory;
	}








	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}








	public Long getDownloadsL() {
		return downloadsL;
	}








	public void setDownloadsL(Long downloadsL) {
		this.downloadsL = downloadsL;
	}
	
	
	

}
