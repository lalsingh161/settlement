package org.mifosplatform.billing.businessline.service;

import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;


public interface BusinessLineReadPlatformService {

	List<EnumOptionData> retrieveNewStatus();
	
	List<BusinessLineData> retrieveEventMasterData();
	
	BusinessLineData retrieveEventMasterDetails(Integer eventId);
	
	List<BusinessLineData> retrieveEventDetailsData(Integer eventId);

	Collection<BusinessLineData> getBusinessLineData();
	
}
