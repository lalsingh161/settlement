package org.mifosplatform.billing.mediasettlement.handler;

import org.mifosplatform.billing.mediasettlement.service.MediaSettlementWritePlatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRevenueSettlementCommandHandler implements
		NewCommandSourceHandler {

final private MediaSettlementWritePlatformService mediaSettlementWritePlatformService;
	
	@Autowired
	public CreateRevenueSettlementCommandHandler(final MediaSettlementWritePlatformService mediaSettlementWritePlatformService) {
		this.mediaSettlementWritePlatformService = mediaSettlementWritePlatformService;
	}
	
	@Override
	public CommandProcessingResult processCommand(JsonCommand command) {
		
		return mediaSettlementWritePlatformService.createRevenueSettlement(command);
	}

}
