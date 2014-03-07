package org.mifosplatform.billing.mediasettlement.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class OperatorDeductionCodeNotFountException extends AbstractPlatformDomainRuleException{

	public OperatorDeductionCodeNotFountException(final String data) {
		super("error.msg.deduction.code.not.found", "Deduction Code does not exist with id "+data);
	}
	
	
}
