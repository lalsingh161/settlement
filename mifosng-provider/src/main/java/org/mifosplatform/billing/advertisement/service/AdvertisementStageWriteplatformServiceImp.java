package org.mifosplatform.billing.advertisement.service;

import org.mifosplatform.billing.advertisement.domain.Advertisement;
import org.mifosplatform.billing.advertisement.domain.AdvertisementJpaRepository;
import org.mifosplatform.billing.advertisement.serialization.AdvertisementStageCommandFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdvertisementStageWriteplatformServiceImp implements AdvertisementStageWriteplatformService{

	private final  AdvertisementJpaRepository advertisementJpaRepository;
	private final  PlatformSecurityContext context;
	private final  AdvertisementStageCommandFromApiJsonDeserializer apiJsonDeserializer;
	private final  FromJsonHelper fromApiJsonHelper;
	
	private final static Logger logger =LoggerFactory.getLogger(AdvertisementStageWriteplatformServiceImp.class);
	
	
	@Autowired
	public AdvertisementStageWriteplatformServiceImp(final  AdvertisementJpaRepository advertisementJpaRepository,
			final PlatformSecurityContext context,
			final AdvertisementStageCommandFromApiJsonDeserializer apiJsonDeserializer,
			final FromJsonHelper fromApiJsonHelper) {
		this.context = context;
		this.advertisementJpaRepository = advertisementJpaRepository;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.fromApiJsonHelper = fromApiJsonHelper;
			
	}
	@Transactional
	@Override
	public CommandProcessingResult createAdvertisementStageData(
			JsonCommand command) {
		
		try {
			   context.authenticatedUser();
			   //this.apiJsonDeserializer.validateForCreateAdvertisement(command.json());
			   final Advertisement advertisementData= Advertisement.fromJson(command);
			   this.advertisementJpaRepository.save(advertisementData);
		
			    
		    return new CommandProcessingResult(advertisementData.getId());
			  
		}catch(DataIntegrityViolationException dve){
			
			return new CommandProcessingResult(Long.valueOf(-1));
	}
	}
}
