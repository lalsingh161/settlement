package org.mifosplatform.billing.revenuemaster.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class NoOPertaorRevenueShareDataFoundException extends AbstractPlatformDomainRuleException{

	public NoOPertaorRevenueShareDataFoundException() {
		super("error.msg.billing.revenue.not.found", "Operator revenue share not found ");
	
	}

}
