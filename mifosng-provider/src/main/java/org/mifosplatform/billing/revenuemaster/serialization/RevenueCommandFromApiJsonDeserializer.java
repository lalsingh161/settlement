package org.mifosplatform.billing.revenuemaster.serialization;

import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RevenueCommandFromApiJsonDeserializer {
	
	 private final FromJsonHelper fromApiJsonHelper;
	
	 @Autowired
	    public RevenueCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
	        this.fromApiJsonHelper = fromApiJsonHelper;
	    }
	
}
