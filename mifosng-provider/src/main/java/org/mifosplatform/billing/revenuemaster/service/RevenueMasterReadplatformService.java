package org.mifosplatform.billing.revenuemaster.service;

import java.util.List;

import org.mifosplatform.billing.revenuemaster.data.DeductionData;
import org.mifosplatform.billing.revenuemaster.data.GenerateInteractiveHeaderData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;

public interface RevenueMasterReadplatformService {

	List<GenerateInteractiveHeaderData> retriveInteractiveHeaderDetails(Long entityId);

	List<RevenueMasterData> retriveAllinteractiveDetails(Long id);

	List<DeductionData> retriveOperatorDeductionData(Long entityId);

}
