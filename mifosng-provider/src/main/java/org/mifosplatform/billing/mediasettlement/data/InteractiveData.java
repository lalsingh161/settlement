package org.mifosplatform.billing.mediasettlement.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.item.data.ChargesData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.media.data.MediaAssetData;

public class InteractiveData {

	
	private Collection<MediaAssetData> mediaData;
	private Collection<MCodeData> playSourceData;
	private Collection<PartnerAccountData> contentData;
	private Collection<PartnerAccountData> channelData;
	private Collection<PartnerAccountData> serviceData;
	private Collection<InteractiveHeaderData> interactiveHeaderData;
	private Collection<InteractiveDetailsData> interactiveDetailsData;
	
	private Long clientId;
	private Long externalId;
	private Long activityMonth;
	private Date dataUploadedDate;
	private Long businessLine;
	private Long mediaCategory;
	private Long chargeCode;
	private List<ChargesData> chargeCodeData;
	private Collection<BusinessLineData> businessLineData;
	private Collection<MCodeData> mediaCategoryData;

	public InteractiveData(
		Collection<MediaAssetData> mediaData,
		Collection<MCodeData> playSource,
		Collection<PartnerAccountData> contentData,
		Collection<PartnerAccountData> channelData,
		Collection<PartnerAccountData> serviceData,
		Collection<InteractiveHeaderData> interactiveHeaderData,
		Collection<InteractiveDetailsData> interactiveDetailsData) {
	
	this.mediaData = mediaData;
	this.playSourceData = playSource;
	this.contentData = contentData;
	this.channelData = channelData;
	this.serviceData = serviceData;
	this.interactiveHeaderData = interactiveHeaderData;
	this.interactiveDetailsData = interactiveDetailsData;
	}



	public InteractiveData() {
		// TODO Auto-generated constructor stub
	}

	public InteractiveData(Long clientId, Long externalId,
			Long activityMonth, Date date, Long businessLine,
			Long mediaCategory, Long chargeCode) {
		this.clientId = clientId;
		this.externalId = externalId;
		this.activityMonth = activityMonth;
		this.dataUploadedDate = date;
		this.businessLine = businessLine;
		this.mediaCategory = mediaCategory;
		this.chargeCode = chargeCode;
	}



	public Collection<MediaAssetData> getMediaData() {
		return mediaData;
	}

	public void setMediaData(Collection<MediaAssetData> mediaData) {
		this.mediaData = mediaData;
	}

	public Collection<MCodeData> getPlaySourceData() {
		return playSourceData;
	}

	public void setPlaySourceData(Collection<MCodeData> playSourceData) {
		this.playSourceData = playSourceData;
	}

	public Collection<PartnerAccountData> getContentData() {
		return contentData;
	}

	public void setContentData(Collection<PartnerAccountData> contentData) {
		this.contentData = contentData;
	}

	public Collection<PartnerAccountData> getChannelData() {
		return channelData;
	}

	public void setChannelData(Collection<PartnerAccountData> channelData) {
		this.channelData = channelData;
	}

	public Collection<PartnerAccountData> getServiceData() {
		return serviceData;
	}

	public void setServiceData(Collection<PartnerAccountData> serviceData) {
		this.serviceData = serviceData;
	}

	public Collection<InteractiveHeaderData> getInteractiveHeaderData() {
		return interactiveHeaderData;
	}

	public void setInteractiveHeaderData(
			Collection<InteractiveHeaderData> interactiveHeaderData) {
		this.interactiveHeaderData = interactiveHeaderData;
	}

	public Collection<InteractiveDetailsData> getInteractiveDetailsData() {
		return interactiveDetailsData;
	}

	public void setInteractiveDetailsData(
			Collection<InteractiveDetailsData> interactiveDetailsData) {
		this.interactiveDetailsData = interactiveDetailsData;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public Long getActivityMonth() {
		return activityMonth;
	}

	public void setActivityMonth(Long activityMonth) {
		this.activityMonth = activityMonth;
	}

	public Date getDataUploadedDate() {
		return dataUploadedDate;
	}

	public void setDataUploadedDate(Date dataUploadedDate) {
		this.dataUploadedDate = dataUploadedDate;
	}

	public Long getBusinessLine() {
		return businessLine;
	}

	public void setBusinessLine(Long businessLine) {
		this.businessLine = businessLine;
	}

	public Long getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public Long getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(Long chargeCode) {
		this.chargeCode = chargeCode;
	}

	

	public List<ChargesData> getChargeCodeData() {
		return chargeCodeData;
	}

	public void setChargeCodeData(List<ChargesData> chargeCodeData) {
		this.chargeCodeData = chargeCodeData;
	}

	public void setBisinessLineData(Collection<BusinessLineData> businessLineData) {
		this.businessLineData = businessLineData;		
	}

	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
		
	}
}
