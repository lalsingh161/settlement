package org.mifosplatform.billing.masterdeduction.service;

import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.masterdeduction.data.DeductionMasterData;

public interface DeductionMasterReadPlatformService {

	List<DeductionMasterData> retrieveAllDeductionDetails();

	DeductionMasterData retrieveDeductionDetail(Long id);

	Collection<StateDetails> retrieveAllStateDetails();

	Collection<DeductionMasterData> getDeductionCodeData();

}
