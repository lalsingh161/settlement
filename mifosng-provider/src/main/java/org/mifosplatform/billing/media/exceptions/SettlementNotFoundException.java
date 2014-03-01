package org.mifosplatform.billing.media.exceptions;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class SettlementNotFoundException extends AbstractPlatformDomainRuleException{

	public SettlementNotFoundException() {
		super("error.settlement.not.found","Settlement Not Found ");
	}
}
