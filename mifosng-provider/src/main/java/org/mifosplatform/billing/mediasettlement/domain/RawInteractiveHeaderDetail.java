package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_raw_headerdetail")
public class RawInteractiveHeaderDetail extends AbstractAuditableCustom<AppUser, Long>{

	
	@Column(name="cust_name")
	private String clientName;
	
	@Column(name="cust_code")
	private Long clientCode;
	
	
	
	@Column(name="activity_month")
	private String activityMonth;
		
	
	@Column(name="data_upload_date")
	private Date dataUploadedDate = new Date();
	
	@Column(name="business_line")
	private String businessLine;
	
	@Column(name="media_category")
	private String mediaCategory;
	
	/*@Column(name="charge_code")
	private String chargeCode;
	*/
	
	@Column(name="play_source", nullable=false)
	private String playSource;
	
	@Column(name="content_name", nullable=false)
	private String contentName;
	
	@Column(name="content_provider", nullable=false)
	private String contentProvider;

	@Column(name="channel_name", nullable=false)
	private String channelName;
	
	@Column(name="service_name", nullable=false)
	private String serviceName;

	@Column(name="end_user_price", nullable=false)
	private BigDecimal endUserPrice;
	
	@Column(name="downloads", nullable=false)
	private Long downloads;

	@Column(name="gross_revenue", nullable=false)
	private BigDecimal grossRevenue;
	
	@Column(name="is_processed")
	private Character isProcessed = 'N';
	

	
	public RawInteractiveHeaderDetail() {
		
	}



	public RawInteractiveHeaderDetail(Long custCode, String custName,
			String activityMonth, String businessLine, String mediaCategory,
			String playSource, String contentName, String contentProvider,
			String channelName, String serviceName, BigDecimal endUserPrice,
			Long downloads, BigDecimal grossRevenue) {
		
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
		

	}
	
	



	public String getClientName() {
		return clientName;
	}



	public void setClientName(String clientName) {
		this.clientName = clientName;
	}



	public Long getClientCode() {
		return clientCode;
	}

	public void setClientCode(Long clientCode) {
		this.clientCode = clientCode;
	}

	public String getActivityMonth() {
		return activityMonth;
	}



	public void setActivityMonth(String activityMonth) {
		this.activityMonth = activityMonth;
	}



	public Date getDataUploadedDate() {
		return dataUploadedDate;
	}



	public void setDataUploadedDate(Date dataUploadedDate) {
		this.dataUploadedDate = dataUploadedDate;
	}



	public String getBusinessLine() {
		return businessLine;
	}



	public void setBusinessLine(String businessLine) {
		this.businessLine = businessLine;
	}



	public String getMediaCategory() {
		return mediaCategory;
	}



	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}



	public String getPlaySource() {
		return playSource;
	}



	public void setPlaySource(String playSource) {
		this.playSource = playSource;
	}



	public String getContentName() {
		return contentName;
	}



	public void setContentName(String contentName) {
		this.contentName = contentName;
	}



	public String getContentProvider() {
		return contentProvider;
	}



	public void setContentProvider(String contentProvider) {
		this.contentProvider = contentProvider;
	}



	public String getChannelName() {
		return channelName;
	}



	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}



	public String getServiceName() {
		return serviceName;
	}



	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public BigDecimal getEndUserPrice() {
		return endUserPrice;
	}



	public void setEndUserPrice(BigDecimal endUserPrice) {
		this.endUserPrice = endUserPrice;
	}



	public Long getDownloads() {
		return downloads;
	}



	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}



	public BigDecimal getGrossRevenue() {
		return grossRevenue;
	}



	public void setGrossRevenue(BigDecimal grossRevenue) {
		this.grossRevenue = grossRevenue;
	}



	public Character getIsProcessed() {
		return isProcessed;
	}



	public void setIsProcessed(Character isProcessed) {
		this.isProcessed = isProcessed;
	}



	public static RawInteractiveHeaderDetail fromJson(JsonCommand command) {
		final Long custCode = command.longValueOfParameterNamed("externalId");
		final String custName = command.stringValueOfParameterNamed("clientId");
		final String activityMonth = command.stringValueOfParameterNamed("activityMonth");
		final String businessLine = command.stringValueOfParameterNamed("businessLine");
		final String mediaCategory = command.stringValueOfParameterNamed("mediaCategory");
		
		final String playSource = command.stringValueOfParameterNamed("playSource"); 
		final String contentName = command.stringValueOfParameterNamed("contentName");
		final String contentProvider = command.stringValueOfParameterNamed("contentProvider");
		final String channelName = command.stringValueOfParameterNamed("channelName");
		final String serviceName = command.stringValueOfParameterNamed("serviceName");
		final BigDecimal endUserPrice = command.bigDecimalValueOfParameterNamed("endUserPrice");
		final Long downloads = command.longValueOfParameterNamed("downloads");
		final BigDecimal grossRevenue = command.bigDecimalValueOfParameterNamed("grossRevenue");
		return new RawInteractiveHeaderDetail(custCode,custName,activityMonth,businessLine,mediaCategory,playSource,contentName,contentProvider,channelName,serviceName,endUserPrice,downloads,grossRevenue);
	}
	
	
}
