package org.mifosplatform.billing.mediasettlement.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class PartnerAgreementNotFoundException extends AbstractPlatformDomainRuleException {
	
	
	public PartnerAgreementNotFoundException(final String documentId) {
        super("validation.error.msg.partnerdocument.details.not.found", "PartnerDocument Details are does not exist");
    }
    

}