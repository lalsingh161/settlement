package org.mifosplatform.billing.businessline.service;

import java.util.List;
import java.util.Map;

import org.mifosplatform.billing.businessline.domain.BusinessLineDetails;
import org.mifosplatform.billing.businessline.domain.BusinessLineMaster;
import org.mifosplatform.billing.businessline.domain.BusinessLineMasterRepository;
import org.mifosplatform.billing.businessline.serialization.BusinessLineFromApiJsonDeserializer;
import org.mifosplatform.billing.media.data.MediaAssetData;
import org.mifosplatform.billing.media.domain.MediaAsset;
import org.mifosplatform.billing.media.service.MediaAssetReadPlatformService;
import org.mifosplatform.billing.mediadetails.domain.MediaAssetRepository;
import org.mifosplatform.billing.paymode.domain.Paymode;
import org.mifosplatform.billing.paymode.domain.PaymodeRepository;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;

@Service
public class BusinessLineWritePlatformServiceImpl implements
BusinessLineWritePlatformService {
	
	private PlatformSecurityContext context;
	private BusinessLineMasterRepository businessLineMasterRepository;
	private PaymodeRepository paymodeRepository;
	private BusinessLineFromApiJsonDeserializer apiJsonDeserializer;
	private MediaAssetReadPlatformService assetReadPlatformService;
	
	@Autowired
	public BusinessLineWritePlatformServiceImpl (final PlatformSecurityContext context,
											    final BusinessLineMasterRepository businessLineMasterRepository,
											    final BusinessLineFromApiJsonDeserializer apiJsonDeserializer,
											    final PaymodeRepository paymodeRepository,
											    final MediaAssetReadPlatformService assetReadPlatformService) {
		this.context = context;
		this.businessLineMasterRepository = businessLineMasterRepository;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.paymodeRepository = paymodeRepository;
		this.assetReadPlatformService = assetReadPlatformService;
	}
	@Transactional
	@Override
	public CommandProcessingResult createBusinessLine(JsonCommand command) {
			
		try {
			this.context.authenticatedUser();
			Long createdbyId = context.authenticatedUser().getId();
			this.apiJsonDeserializer.validateForCreate(command.json());
			BusinessLineMaster eventMaster = BusinessLineMaster.fromJsom(command);		
			final JsonArray array = command.arrayOfParameterNamed("categoryData").getAsJsonArray();
			String[] category  = null;
			category = new String[array.size()];
			for(int i=0 ; i<array.size() ; i++) {
				category[i] = array.get(i).getAsString();
			}
			for(String mediaId : category) {
				final Long id = Long.valueOf(mediaId);
				Paymode paymode = this.paymodeRepository.findOne(id);
				BusinessLineDetails detail = new BusinessLineDetails(paymode.getId());
				eventMaster.addMediaDetails(detail);
			}
			//eventMaster.setCreatedbyId(createdbyId);
			this.businessLineMasterRepository.save(eventMaster);
			
			return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(eventMaster.getId()).build();
		} catch(DataIntegrityViolationException dve) {
			return new CommandProcessingResult(Long.valueOf(-1));
		}
	}
	
	@SuppressWarnings("unused")
	@Transactional
	@Override
	public CommandProcessingResult updateBusinessLine(JsonCommand command) {
		try {
			this.context.authenticatedUser();
			this.apiJsonDeserializer.validateForCreate(command.json());
			BusinessLineMaster newEvent = BusinessLineMaster.fromJsom(command);
			System.out.println(command.entityId());
			BusinessLineMaster oldEvent = this.businessLineMasterRepository.findOne(command.entityId());
			List<MediaAssetData> mediaData = this.assetReadPlatformService.retrieveAllmediaAssetdata();
			
			for(MediaAssetData data : mediaData) {
				oldEvent.getEventDetails().clear();
				final JsonArray array = command.arrayOfParameterNamed("categoryData").getAsJsonArray();
				String[] category = null;
				category  =new String[array.size()];
				
				for(int i=0;i<array.size();i++) {
					category[i] = array.get(i).getAsString();
				}
				
				for(String categoryId : category) {
					final Long id = Long.valueOf(categoryId);
					//MediaAsset mediaAsset = this.assetRepository.findOne(id);
					Paymode paymode = this.paymodeRepository.findOne(id);
					BusinessLineDetails detail = new BusinessLineDetails(paymode.getId());
					oldEvent.addMediaDetails(detail);
				}
			}
			final Map<String, Object> changes = oldEvent.updateBusinessData(command);
			//oldEvent = (BusinessLineMaster) UpdateCompareUtil.compare(oldEvent,newEvent);
			this.businessLineMasterRepository.save(oldEvent);
			return new CommandProcessingResultBuilder().withEntityId(command.entityId()).withCommandId(command.commandId()).build();
		} catch (DataIntegrityViolationException dve) {
			return new CommandProcessingResult(Long.valueOf(-1));
		}
	}
	
	
	/* NOT COMPLETED */
	
	@Override
	public CommandProcessingResult deleteBusinessLine(Long entityId) {
			
		List<MediaAssetData> mediaAsset = this.assetReadPlatformService.retrieveAllmediaAssetdata();
		BusinessLineMaster event = this.businessLineMasterRepository.findOne(entityId);
		for(MediaAssetData data : mediaAsset) {
			BusinessLineDetails details = new BusinessLineDetails(data.getMediaId());
			details.delete(event);
		}
		//event.delete();
		this.businessLineMasterRepository.save(event);
		return new CommandProcessingResultBuilder().withEntityId(entityId).build();
	}
		
}
