package org.mifosplatform.billing.mediasettlement.handler;

import org.mifosplatform.billing.mediasettlement.service.MediaSettlementWritePlatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOperatorDeductionCommandHandler implements NewCommandSourceHandler {

	final private MediaSettlementWritePlatformService mediaSettlementWritePlatformService;
	
	@Autowired
	public UpdateOperatorDeductionCommandHandler(final MediaSettlementWritePlatformService mediaSettlementWritePlatformService ) {
		this.mediaSettlementWritePlatformService = mediaSettlementWritePlatformService;
	}
	
	
	@Override
	public CommandProcessingResult processCommand(final JsonCommand command) {
		return this.mediaSettlementWritePlatformService.updateOperatorDeduction(command,command.entityId());
	}

}