package org.mifosplatform.billing.advertisement.handler;

import org.mifosplatform.billing.advertisement.service.AdvertisementStageWriteplatformService;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateAdvertisementStageCommandHandler  implements NewCommandSourceHandler {

	private final AdvertisementStageWriteplatformService writePlatformService;
	  
	  @Autowired
	    public CreateAdvertisementStageCommandHandler(AdvertisementStageWriteplatformService writePlatformService) {
	        this.writePlatformService = writePlatformService;
	       
	    }
	
	@Override
	public CommandProcessingResult processCommand(JsonCommand command) {
		
		return writePlatformService.createAdvertisementStageData(command);
	}

}