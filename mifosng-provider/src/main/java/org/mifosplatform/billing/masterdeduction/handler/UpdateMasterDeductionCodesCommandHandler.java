package org.mifosplatform.billing.masterdeduction.handler;

import org.mifosplatform.billing.masterdeduction.service.DeductionMasterWritePlatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMasterDeductionCodesCommandHandler implements NewCommandSourceHandler {
	
	
	final private DeductionMasterWritePlatformService deductionWritePlatformService;
	
	
	
	@Autowired
	public UpdateMasterDeductionCodesCommandHandler(final DeductionMasterWritePlatformService deductionWritePlatformService) {
		this.deductionWritePlatformService= deductionWritePlatformService;
	}
	
	@Override
	public CommandProcessingResult processCommand(JsonCommand command){
		return this.deductionWritePlatformService.updateDeductionCode(command.entityId(),command);
	}



}
