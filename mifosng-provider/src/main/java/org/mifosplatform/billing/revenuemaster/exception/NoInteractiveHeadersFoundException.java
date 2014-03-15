package org.mifosplatform.billing.revenuemaster.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class NoInteractiveHeadersFoundException extends AbstractPlatformDomainRuleException {

	public NoInteractiveHeadersFoundException() {
        super("error.msg.billing.revenue.not.found", "InteractiveHeader events not found ");
    }

	public NoInteractiveHeadersFoundException(Long id) {
		super("error.msg.no.active.details.available.for.this.interactiveheader", "No Active Details Available For This InteractiveHeader ", id);
	}
	
	
}
