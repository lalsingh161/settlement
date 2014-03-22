package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.masterdeduction.data.DeductionMasterData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class RevenueShareData {

	private Long id;
	private Long clientId;
	private Long businessLine;
	private Long mediaCategory;
	private Long revenueShareType;
	private Long revenueShareMasterId;
	private Long startValue;
	private Long endValue;
	private BigDecimal percentage;
	private BigDecimal flat;
	Collection<MCodeData> mediaCategoryData;
	Collection<BusinessLineData> businessLineData;
	Collection<MCodeData> royaltyTypeData;
	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}

	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}

	public Collection<BusinessLineData> getBusinessLineData() {
		return businessLineData;
	}

	public void setBusinessLineData(Collection<BusinessLineData> businessLineData) {
		this.businessLineData = businessLineData;
	}

	public Collection<MCodeData> getRoyaltyTypeData() {
		return royaltyTypeData;
	}

	public void setRoyaltyTypeData(Collection<MCodeData> royaltyTypeData) {
		this.royaltyTypeData = royaltyTypeData;
	}

	public List<RevenueShareData> getPercentageDatas() {
		return percentageDatas;
	}

	public void setPercentageDatas(List<RevenueShareData> percentageDatas) {
		this.percentageDatas = percentageDatas;
	}

	List<RevenueShareData> percentageDatas;
	List<RevenueShareData> masterData;
	private String businessLineStr;
	private String mediaCategoryStr;
	private String revenueShareTypeStr;
	List<RevenueShareData> datas;
	private Long revenueShareCode;
	private String revenueShareCodeStr;
	private Collection<DeductionMasterData> deductionMasterData;
	public RevenueShareData() {
		
	}
	
	public RevenueShareData(Long id, Long businessLine, Long mediaCategory,
			Long revenueShareType) {
		
			this.id=id;
			this.businessLine=businessLine;
			this.mediaCategory=mediaCategory;
			this.revenueShareType=revenueShareType;
			
	}
	
	/*public RevenueShareData(Long id, Long businessLine, Long mediaCategory,
			Long revenueShareType, Long startValue, Long endValue, BigDecimal percentage, BigDecimal flat) {
		
			this.id=id;
			this.clientId = clientId;
			this.businessLine=businessLine;
			this.mediaCategory=mediaCategory;
			this.revenueShareType=revenueShareType;
			this.startValue=startValue;
			this.endValue=endValue;
			this.percentage=percentage;
			this.flat=flat;
	}
*/
	public RevenueShareData(Collection<MCodeData> mediaCategoryData,
			Collection<BusinessLineData> businessLineData,
			Collection<MCodeData> royaltyTypeData,
			List<RevenueShareData> percentageDatas, List<RevenueShareData> datas) {
		
			this.mediaCategoryData=mediaCategoryData;
			this.businessLineData=businessLineData;
			this.royaltyTypeData=royaltyTypeData;
			this.percentageDatas=percentageDatas;
			this.datas=datas;
	}

	public RevenueShareData(Long id, String revenueShareCode,
			String mediaCategory, String revenueShareType) {
		
		this.id = id;
		this.revenueShareCodeStr = revenueShareCode;
		this.mediaCategoryStr = mediaCategory;
		this.revenueShareTypeStr = revenueShareType;

	}

	public RevenueShareData(Long id, Long revenueShareCode, Long mediaCategory,
			Long revenueShareType, Long clientId) {
		this.id=id;
		this.revenueShareCode=revenueShareCode;
		this.mediaCategory=mediaCategory;
		this.revenueShareType=revenueShareType;
		this.clientId = clientId;
	}

	public RevenueShareData(Long startValue, Long endValue,
			BigDecimal percentage, BigDecimal flat) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.percentage = percentage;
		this.flat = flat;
	}

	public void setDeductionMasterData(Collection<DeductionMasterData> deductionMasterData) {
		this.deductionMasterData = deductionMasterData;
	}
	public Collection<DeductionMasterData> getDeductionMasterData() {
		return deductionMasterData;
	}
   
}