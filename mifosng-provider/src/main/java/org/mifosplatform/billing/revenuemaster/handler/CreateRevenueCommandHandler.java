package org.mifosplatform.billing.revenuemaster.handler;

import org.mifosplatform.billing.revenuemaster.service.RevenueClient;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRevenueCommandHandler implements NewCommandSourceHandler {
		
		private final RevenueClient revenueClient;
		
		@Autowired
	    public CreateRevenueCommandHandler(final RevenueClient revenueClient) {
	        this.revenueClient = revenueClient;
	    }
		
	    @Transactional
	    @Override
	    public CommandProcessingResult processCommand(final JsonCommand command) {

	        return this.revenueClient.createRevenue(command);
	    }
	    

	}
