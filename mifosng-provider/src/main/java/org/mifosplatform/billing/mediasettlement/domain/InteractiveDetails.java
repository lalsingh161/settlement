 package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="bp_interactive_detail")
public class InteractiveDetails extends AbstractAuditableCustom<AppUser, Long>{

	@ManyToOne
    @JoinColumn(name="interactive_header_id")
	private InteractiveHeader eventId;
	
	
	@Column(name="play_source", nullable=false)
	private Long playSource;
	
	@Column(name="content_name", nullable=false)
	private String contentName;
	
	@Column(name="media_category", nullable=false)
	private Long mediaCategory;
	
	@Column(name="content_provider", nullable=false)
	private Long contentProvider;

	@Column(name="channel_name", nullable=false)
	private Long channelName;
	
	@Column(name="service_name", nullable=false)
	private Long serviceName;

	@Column(name="end_user_price", nullable=false)
	private BigDecimal endUserPrice;
	
	@Column(name="downloads", nullable=false)
	private Long downloads;

	@Column(name="gross_revenue", nullable=false)
	private BigDecimal grossRevenue;
	
	@Column(name = "is_deleted")
	private char isDeleted='N';
	
	
	
	
	public InteractiveDetails(){
		
	}
	
	
       public InteractiveDetails(Long playSource, String contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, Long downloads, BigDecimal grossRevenue, Long mediaCategory) {
		
    	   this.playSource=playSource;
    	   this.contentName=contentName;
    	   this.contentProvider=contentProvider;
    	   this.channelName =channelName;
    	   this.serviceName =serviceName;
    	   this.endUserPrice = endUserPrice;
    	   this.downloads =downloads;
    	   this.grossRevenue =grossRevenue;
    	   this.mediaCategory = mediaCategory;
    	   
	}
    

	

	public Long getPlaySource() {
		return playSource;
	}

	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
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

	

	public Long getDownloads() {
		return downloads;
	}

	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}

	

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}


	public InteractiveHeader getEventId() {
		return eventId;
	}


	public void setEventId(InteractiveHeader eventId) {
		this.eventId = eventId;
	}


	public BigDecimal getEndUserPrice() {
		return endUserPrice;
	}


	public void setEndUserPrice(BigDecimal endUserPrice) {
		this.endUserPrice = endUserPrice;
	}


	public BigDecimal getGrossRevenue() {
		return grossRevenue;
	}


	public void setGrossRevenue(BigDecimal grossRevenue) {
		this.grossRevenue = grossRevenue;
	}


	public static InteractiveDetails fromJson(Long playSource,
			String contentName, Long contentProvider, Long channelName,
			Long serviceName, BigDecimal endUserPrice, BigDecimal grossRevenue,
			Long downloads,Long mediaCategory) {
		
		return new InteractiveDetails(playSource,contentName,contentProvider,channelName,serviceName,endUserPrice,downloads,grossRevenue,mediaCategory);
	}


	public void update(InteractiveHeader interactiveHeader) {
		this.eventId = interactiveHeader;
		
	}


	public void setContentName(String contentName) {
		this.contentName = contentName;
		
	}


	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
		
	}

	
	
	
}