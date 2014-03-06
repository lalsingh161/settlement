package org.mifosplatform.billing.businessline.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface BusinessLineWritePlatformService {


	CommandProcessingResult createBusinessLine(JsonCommand command);

	CommandProcessingResult updateBusinessLine(JsonCommand command);

	CommandProcessingResult deleteBusinessLine(Long entityId); 

}
