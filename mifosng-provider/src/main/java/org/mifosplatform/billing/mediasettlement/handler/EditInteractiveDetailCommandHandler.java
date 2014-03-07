package org.mifosplatform.billing.mediasettlement.handler;

import org.mifosplatform.billing.mediasettlement.service.MediaSettlementWritePlatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

	@Service
	public class  EditInteractiveDetailCommandHandler implements NewCommandSourceHandler{

		final private MediaSettlementWritePlatformService mediaSettlementWritePlatformService;
		
		@Autowired
		public EditInteractiveDetailCommandHandler(final MediaSettlementWritePlatformService mediaSettlementWritePlatformService ) {
			this.mediaSettlementWritePlatformService = mediaSettlementWritePlatformService;
		}
	
		@Override
		public CommandProcessingResult processCommand(final JsonCommand command) {
			return mediaSettlementWritePlatformService.editInteractiveData(command.entityId(), command);
		
		}


}