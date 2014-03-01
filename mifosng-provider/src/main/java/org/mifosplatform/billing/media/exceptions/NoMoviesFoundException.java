package org.mifosplatform.billing.media.exceptions;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;


public class NoMoviesFoundException extends AbstractPlatformDomainRuleException {

    public NoMoviesFoundException() {
        super("error.msg.movie.not.found", " Movie Format Does Not Exist ");
    }
    
    public NoMoviesFoundException(String msg1, String msg2) {
        super(msg1, msg2);
    }
    
   
}
