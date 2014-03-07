package org.mifosplatform.billing.mediasettlement.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class CurrencyRateNotFoundException extends AbstractPlatformDomainRuleException {

	public CurrencyRateNotFoundException(final String id) {
		super("error.msg.currency.details.not.found", "CurrencyRate Details are does not exist");	
		}
	
}
