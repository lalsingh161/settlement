package org.mifosplatform.billing.revenuemaster.handler;

import org.mifosplatform.billing.revenuemaster.service.InvoiceRevenueClient;
import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRevenueInvoiceCommandHandler implements NewCommandSourceHandler {
		
		private final InvoiceRevenueClient invoiceRevenueClient;
		
		@Autowired
	    public CreateRevenueInvoiceCommandHandler(final InvoiceRevenueClient invoiceRevenueClient) {
	        this.invoiceRevenueClient = invoiceRevenueClient;
	    }
		
	    @Transactional
	    @Override
	    public CommandProcessingResult processCommand(final JsonCommand command) {

	        return this.invoiceRevenueClient.createRevenueInvoice(command);
	    }
	    

	}
