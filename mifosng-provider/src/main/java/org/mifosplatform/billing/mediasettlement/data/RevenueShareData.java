package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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
	Collection<MCodeData> businessLineData;
	Collection<MCodeData> royaltyTypeData;
	List<RevenueShareData> percentageDatas;
	List<RevenueShareData> masterData;
	private String businessLineStr;
	private String mediaCategoryStr;
	private String revenueShareTypeStr;
	List<RevenueShareData> datas;
	public RevenueShareData() {
		
	}
	
	public RevenueShareData(Long id, Long businessLine, Long mediaCategory,
			Long revenueShareType) {
		
			this.id=id;
			this.businessLine=businessLine;
			this.mediaCategory=mediaCategory;
			this.revenueShareType=revenueShareType;
			
	}
	
	public RevenueShareData(Long id, Long businessLine, Long mediaCategory,
			Long revenueShareType, Long startValue, Long endValue, BigDecimal percentage, BigDecimal flat) {
		
			this.id=id;
			this.businessLine=businessLine;
			this.mediaCategory=mediaCategory;
			this.revenueShareType=revenueShareType;
			this.startValue=startValue;
			this.endValue=endValue;
			this.percentage=percentage;
			this.flat=flat;
	}

	public RevenueShareData(Collection<MCodeData> mediaCategoryData,
			Collection<MCodeData> businessLineData,
			Collection<MCodeData> royaltyTypeData,
			List<RevenueShareData> percentageDatas, List<RevenueShareData> datas) {
		
			this.mediaCategoryData=mediaCategoryData;
			this.businessLineData=businessLineData;
			this.royaltyTypeData=royaltyTypeData;
			this.percentageDatas=percentageDatas;
			this.datas=datas;
	}

	public RevenueShareData(Long id, String businessLine,
			String mediaCategory, String revenueShareType) {
		
		this.id = id;
		this.businessLineStr = businessLine;
		this.mediaCategoryStr = mediaCategory;
		this.revenueShareTypeStr = revenueShareType;

	}

}