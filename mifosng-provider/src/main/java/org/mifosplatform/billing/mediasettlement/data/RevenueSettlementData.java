package org.mifosplatform.billing.mediasettlement.data;

import java.util.Collection;

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class RevenueSettlementData {

	
	private Collection<MCodeData> mediaCategoryData;
	private Collection<BusinessLineData> businessLineData;
	private Collection<MCodeData> royatyTypeData;

	public RevenueSettlementData() {
		
	}
	
	public RevenueSettlementData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}

	public RevenueSettlementData(Collection<BusinessLineData> businessLineData,
			Collection<MCodeData> mediaCategoryData,Collection<MCodeData> royaltyType) {
		this.businessLineData = businessLineData;
		this.mediaCategoryData = mediaCategoryData;
		this.royatyTypeData = royaltyType;
	}

	
	

}
