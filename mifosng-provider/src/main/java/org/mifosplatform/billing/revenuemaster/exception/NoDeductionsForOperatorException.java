package org.mifosplatform.billing.revenuemaster.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class NoDeductionsForOperatorException extends AbstractPlatformDomainRuleException {

	public NoDeductionsForOperatorException() {
        super("error.msg.billing.revenue.not.found", "Operator Deduction taxes  not found ");
    }
	
	
}
