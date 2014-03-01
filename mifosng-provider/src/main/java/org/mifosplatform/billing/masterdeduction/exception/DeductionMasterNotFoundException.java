package org.mifosplatform.billing.masterdeduction.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class DeductionMasterNotFoundException extends AbstractPlatformDomainRuleException {

	
	public DeductionMasterNotFoundException(final String documentId) {
        super("validation.error.msg.DeductionMaster.details.not.found", "DeductionMaster Details are does not exist");
    }
	
	
}