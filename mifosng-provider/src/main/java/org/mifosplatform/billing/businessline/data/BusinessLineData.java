package org.mifosplatform.billing.businessline.data;

import java.util.List;
import org.mifosplatform.billing.item.data.ChargesData;
import org.mifosplatform.billing.media.data.MediaAssetData;
import org.mifosplatform.billing.paymode.data.McodeData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class BusinessLineData {

	public List<EnumOptionData> getStatusData() {
		return statusData;
	}


	public void setStatusData(List<EnumOptionData> statusData) {
		this.statusData = statusData;
	}


	public List<MediaAssetData> getMediaAsset() {
		return mediaAsset;
	}


	public void setMediaAsset(List<MediaAssetData> mediaAsset) {
		this.mediaAsset = mediaAsset;
	}


	public List<ChargesData> getChargeDatas() {
		return chargeDatas;
	}


	public void setChargeDatas(List<ChargesData> chargeDatas) {
		this.chargeDatas = chargeDatas;
	}


	public List<McodeData> getMediaCategeorydata() {
		return mediaCategeorydata;
	}


	public void setMediaCategeorydata(List<McodeData> mediaCategeorydata) {
		this.mediaCategeorydata = mediaCategeorydata;
	}


	private Long id;
	private String eventName;
	private String eventDescription;
	private String status;
	private String chargeCode;
	private Long category;
	private List<EnumOptionData> statusData;
	private List<MediaAssetData> mediaAsset;
	private List<ChargesData> chargeDatas;
	

	private List<McodeData> mediaCategeorydata;
	private List<BusinessLineData> selectedMedia;
	public Long getCategoryId() {
		return categoryId;
	}


	public List<BusinessLineData> getSelectedMedia() {
		return selectedMedia;
	}


	public void setSelectedMedia(List<BusinessLineData> selectedMedia) {
		this.selectedMedia = selectedMedia;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	private Long statusId;
	private  Long eventId;
	private Long categoryId;
	private String mediaTitle;
	private String mCodeValue;
	private String businessLineDescription; 
	
	
	
	public BusinessLineData() {
		
	}


	public BusinessLineData(Long id, String eventName,
			String eventDescription, String status, String chargeCode) {
		
			this.id=id;
			this.eventName=eventName;
			this.eventDescription=eventDescription;
			this.status=status;
			this.chargeCode=chargeCode;
	}


	public BusinessLineData(List<MediaAssetData> mediaData,
			List<EnumOptionData> statusData, List<ChargesData> chargeDatas) {
		
		this.mediaAsset=mediaData;
		this.statusData=statusData;
		this.chargeDatas=chargeDatas;
		
	}


	public BusinessLineData(Long id, String eventName,
			String eventDescription, Long status, String chargeData,
			Long category) {
			
			this.id=id;
			this.eventName=eventName;
			this.eventDescription=eventDescription;
			this.statusId=status;
			this.chargeCode=chargeData;
			this.category=category;
		
	}


	public BusinessLineData(Long id, Long eventId, Long categoryId,
			String mediaTitle) {
		
			this.id=id;
			this.eventId=eventId;
			this.categoryId=categoryId;
			this.mediaTitle=mediaTitle;
			
	}


	public BusinessLineData(Long id, String codeValue,
			String businessLineDescription) {
		this.id = id;
		this.mCodeValue = codeValue;
		this.businessLineDescription = businessLineDescription;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getEventDescription() {
		return eventDescription;
	}


	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getChargeCode() {
		return chargeCode;
	}


	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}


	public Long getCategory() {
		return category;
	}


	public void setCategory(Long category) {
		this.category = category;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}


	public String getMediaTitle() {
		return mediaTitle;
	}


	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}


	public String getmCodeValue() {
		return mCodeValue;
	}


	public void setmCodeValue(String mCodeValue) {
		this.mCodeValue = mCodeValue;
	}


	public String getBusinessLineDescription() {
		return businessLineDescription;
	}


	public void setBusinessLineDescription(String businessLineDescription) {
		this.businessLineDescription = businessLineDescription;
	}
	
	
}
