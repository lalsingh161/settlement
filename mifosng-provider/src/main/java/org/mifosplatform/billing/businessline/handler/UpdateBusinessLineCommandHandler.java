package org.mifosplatform.billing.businessline.handler;

import org.mifosplatform.billing.businessline.service.BusinessLineWritePlatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBusinessLineCommandHandler implements NewCommandSourceHandler {

	@Autowired
	private BusinessLineWritePlatformService businessLineWritePlatformService;

	@Override
	public CommandProcessingResult processCommand(JsonCommand command) {
		return this.businessLineWritePlatformService.updateBusinessLine(command);
	}

}
