package org.mifosplatform.billing.revenuemaster.service;

import java.util.List;

import org.mifosplatform.billing.revenuemaster.data.DeductionData;
import org.mifosplatform.billing.revenuemaster.data.GenerateInteractiveHeaderData;
import org.mifosplatform.billing.revenuemaster.data.OperatorShareData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.billing.taxmaster.data.TaxMappingRateData;

public interface RevenueMasterReadplatformService {

	List<GenerateInteractiveHeaderData> retriveInteractiveHeaderDetails(Long entityId);

	List<RevenueMasterData> retriveAllinteractiveDetails(Long id);

	List<DeductionData> retriveOperatorDeductionData(Long entityId);

	List<OperatorShareData> retriveRevenueShareData(Long clientId);

	List<TaxMappingRateData> retrieveTaxMappingDate(Long clientId,
			String chargeCode);

	List<TaxMappingRateData> retrieveDefaultTaxMappingDate(Long clientId,
			String chargeCode);

}
