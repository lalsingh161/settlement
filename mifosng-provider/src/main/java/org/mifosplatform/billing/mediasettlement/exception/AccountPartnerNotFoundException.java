package org.mifosplatform.billing.mediasettlement.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class AccountPartnerNotFoundException extends AbstractPlatformDomainRuleException{
	
	public AccountPartnerNotFoundException(final String partnerId) {
        super("error.msg.accountpartner.details.not.found", "PartnerAccount Details are does not exist");
    }
    

}