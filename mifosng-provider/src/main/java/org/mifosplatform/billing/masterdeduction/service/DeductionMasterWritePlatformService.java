package org.mifosplatform.billing.masterdeduction.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface DeductionMasterWritePlatformService {

	

	public CommandProcessingResult createDeductionCodes(JsonCommand command);

	public CommandProcessingResult updateDeductionCode(Long id,
			JsonCommand command) ;

	public CommandProcessingResult deleteDeductionMaster(Long entityId);
}
