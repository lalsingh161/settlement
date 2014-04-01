package org.mifosplatform.billing.advertisement.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface AdvertisementStageWriteplatformService {
	
	public CommandProcessingResult createAdvertisementStageData(JsonCommand command);

}
