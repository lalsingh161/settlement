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
	private Long externalId;
	private Long businessLineL;
	/*private Long mediaCategoryL;*/
	private Long chargeCodeL;

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

	public InteractiveHeaderData(Long id,Long clientId, Long externalId,
			String businessLineStr, String activityMonth, LocalDate dataUploadedDate,
			/*String mediaCategoryStr,*/ String chargeCodeStr) {
		this.id = id;
		this.clientId = clientId;
		this.externalId = externalId;
		this.businessLine = businessLineStr;
		this.activityMonth = activityMonth;
		this.dataUploadedDate = (null==dataUploadedDate)?null:dataUploadedDate.toDate();
		/*this.mediaCategory = mediaCategoryStr;*/
		this.chargeCode = chargeCodeStr;
	}

	public InteractiveHeaderData(Long clientId, Long externalId,
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
}
