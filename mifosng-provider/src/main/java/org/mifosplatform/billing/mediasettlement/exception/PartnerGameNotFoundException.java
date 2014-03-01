package org.mifosplatform.billing.mediasettlement.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class PartnerGameNotFoundException extends AbstractPlatformDomainRuleException {
	
	
	public PartnerGameNotFoundException(final String gameId) {
        super("validation.error.msg.partnerGame.details.not.found", "PartnerGame Details are does not exist");
    }
    

}