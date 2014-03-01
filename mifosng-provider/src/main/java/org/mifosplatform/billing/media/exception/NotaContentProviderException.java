package org.mifosplatform.billing.media.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;


public class NotaContentProviderException extends AbstractPlatformDomainRuleException {

    public NotaContentProviderException() {
        super("invalid.content.provider", " Invalid Content Provider Exception");
    }
    
    public NotaContentProviderException(String msg1, String msg2) {
        super(msg1, msg2);
    }
    
   
}
