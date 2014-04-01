package org.mifosplatform.billing.mediasettlement.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.item.data.ChargesData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.media.data.MediaAssetData;

public class InteractiveHeaderData {

	
	private Collection<StateDetails> circleData;
	private Collection<MCodeData> businessLineData;
	private Collection<MCodeData> mediaCategoryData;
	private Collection<MCodeData> playSourceData;
	private List<MediaAssetData> contentNameData;
	private List<ChargesData> chargeCodesData;
	private Long clientId;
	private String circle;
	private String businessLine;
	private String activityMonth;
	private Date dataUploadedDate;
	/*private String mediaCategory;*/
	private String chargeCode;
	private Long id;
	private String externalId;
	private Long businessLineL;
	/*private Long mediaCategoryL;*/
	private Long chargeCodeL;
	private Long clientCode;
	private String clientName;
	private String mediaCategory;

	public InteractiveHeaderData() {
		
	}

	public InteractiveHeaderData(Collection<StateDetails> circleData,
			Collection<MCodeData> businessLineData,
			Collection<MCodeData> mediaCategoryData,
			Collection<MCodeData> playSourceData,
			List<MediaAssetData> contentNameData,
			List<ChargesData> chargeCodesData) {
		this.circleData = circleData;
		this.businessLineData = businessLineData;
		this.mediaCategoryData = mediaCategoryData;
		this.playSourceData = playSourceData;
		this.contentNameData = contentNameData;
		this.chargeCodesData = chargeCodesData;

		
	}

	public InteractiveHeaderData(Long id,Long clientId, String externalId,
			String businessLineStr, String activityMonth, LocalDate dataUploadedDate,
			String chargeCodeStr) {
		this.id = id;
		this.clientId = clientId;
		this.externalId = externalId;
		this.businessLine = businessLineStr;
		this.activityMonth = activityMonth;
		this.dataUploadedDate = (null==dataUploadedDate)?null:dataUploadedDate.toDate();

		this.chargeCode = chargeCodeStr;
	}

	public InteractiveHeaderData(Long clientId, String externalId,
			String activityMonth, Date date, Long businessLine,
			/*Long mediaCategory,*/ Long chargeCode) {
		this.clientId = clientId;
		this.externalId = externalId;
		this.activityMonth = activityMonth;
		this.dataUploadedDate = date;
		this.businessLineL = businessLine;
		/*this.mediaCategoryL = mediaCategory;*/
		this.chargeCodeL = chargeCode;
	}

	

	public InteractiveHeaderData(Long clientCode, Long clientName,
			String activityMonth, Date dataUploadedDate, Long businessLine) {
		this.clientCode = clientCode;
		this.clientId = clientName;
		this.activityMonth = activityMonth;
		this.dataUploadedDate = dataUploadedDate;
		this.businessLineL = businessLine;
	}

	public Collection<StateDetails> getCircleData() {
		return circleData;
	}

	public Collection<MCodeData> getBusinessLineData() {
		return businessLineData;
	}

	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}

	public Collection<MCodeData> getPlaySourceData() {
		return playSourceData;
	}

	public List<MediaAssetData> getContentNameData() {
		return contentNameData;
	}

	public List<ChargesData> getChargeCodesData() {
		return chargeCodesData;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getCircle() {
		return circle;
	}

	public String getBusinessLine() {
		return businessLine;
	}

	public String getActivityMonth() {
		return activityMonth;
	}

	public Date getDataUploadedDate() {
		return dataUploadedDate;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public Long getId() {
		return id;
	}

	public String getExternalId() {
		return externalId;
	}

	public Long getBusinessLineL() {
		return businessLineL;
	}

	public Long getChargeCodeL() {
		return chargeCodeL;
	}

	public Long getClientCode() {
		return clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setCircleData(Collection<StateDetails> circleData) {
		this.circleData = circleData;
	}

	public void setBusinessLineData(Collection<MCodeData> businessLineData) {
		this.businessLineData = businessLineData;
	}

	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}

	public void setPlaySourceData(Collection<MCodeData> playSourceData) {
		this.playSourceData = playSourceData;
	}

	public void setContentNameData(List<MediaAssetData> contentNameData) {
		this.contentNameData = contentNameData;
	}

	public void setChargeCodesData(List<ChargesData> chargeCodesData) {
		this.chargeCodesData = chargeCodesData;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public void setBusinessLine(String businessLine) {
		this.businessLine = businessLine;
	}

	public void setActivityMonth(String activityMonth) {
		this.activityMonth = activityMonth;
	}

	public void setDataUploadedDate(Date dataUploadedDate) {
		this.dataUploadedDate = dataUploadedDate;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public void setBusinessLineL(Long businessLineL) {
		this.businessLineL = businessLineL;
	}

	public void setChargeCodeL(Long chargeCodeL) {
		this.chargeCodeL = chargeCodeL;
	}

	public void setClientCode(Long clientCode) {
		this.clientCode = clientCode;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}
	
	
}
