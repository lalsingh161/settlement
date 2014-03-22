package org.mifosplatform.billing.mediasettlement.data;

import java.util.Collection;

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.masterdeduction.data.DeductionMasterData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class RevenueSettlementData {

	
	private Collection<MCodeData> mediaCategoryData;
	private Collection<BusinessLineData> businessLineData;
	private Collection<MCodeData> royatyTypeData;
	private Collection<DeductionMasterData> deductionMasterData;

	public RevenueSettlementData() {
		
	}
	
	public RevenueSettlementData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}

	public RevenueSettlementData(Collection<DeductionMasterData> deductionMasterData,
			Collection<MCodeData> mediaCategoryData,Collection<MCodeData> royaltyType) {
		//this.businessLineData = businessLineData;
		this.deductionMasterData = deductionMasterData;
		this.mediaCategoryData = mediaCategoryData;
		this.royatyTypeData = royaltyType;
	}

	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}

	public Collection<MCodeData> getRoyatyTypeData() {
		return royatyTypeData;
	}

	public Collection<DeductionMasterData> getDeductionMasterData() {
		return deductionMasterData;
	}

}
