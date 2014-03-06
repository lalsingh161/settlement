package org.mifosplatform.billing.media.service;

import java.math.BigDecimal;
import java.util.Map;

import org.mifosplatform.billing.media.domain.MediaAsset;
import org.mifosplatform.billing.media.domain.Settlement;
import org.mifosplatform.billing.media.exception.NotaContentProviderException;
import org.mifosplatform.billing.media.exceptions.NoMoviesFoundException;
import org.mifosplatform.billing.media.exceptions.SettlementNotFoundException;
import org.mifosplatform.billing.media.serialization.MediaAssetCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.mediadetails.domain.MediaAssetRepository;
import org.mifosplatform.billing.mediadetails.domain.MediaassetAttributes;
import org.mifosplatform.billing.mediadetails.domain.MediaassetLocation;
import org.mifosplatform.billing.mediadetails.domain.SettlementJpaRepository;
import org.mifosplatform.billing.mediasettlement.data.PartnerAccountData;
import org.mifosplatform.billing.mediasettlement.service.MediaSettlementReadPlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


@Service
public class MediaAssetWritePlatformServiceImpl implements MediaAssetWritePlatformService {
	 private final static Logger logger = LoggerFactory.getLogger(MediaAssetWritePlatformServiceImpl.class);
	private final PlatformSecurityContext context;
	private final MediaAssetCommandFromApiJsonDeserializer fromApiJsonDeserializer;
	private final FromJsonHelper fromApiJsonHelper;
	private final MediaAssetRepository assetRepository;
	private final SettlementJpaRepository settlementJpaRepository;
	private final MediaSettlementReadPlatformService mediaSettlementReadPlatformService;
	@Autowired
	public MediaAssetWritePlatformServiceImpl(final PlatformSecurityContext context,
			final FromJsonHelper fromApiJsonHelper,final MediaAssetRepository assetRepository,
			final MediaAssetCommandFromApiJsonDeserializer fromApiJsonDeserializer,
			final SettlementJpaRepository settlementJpaRepository,
			final MediaSettlementReadPlatformService mediaSettlementReadPlatformService) {
		this.context = context;
		this.assetRepository=assetRepository;
		this.fromApiJsonDeserializer=fromApiJsonDeserializer;
		this.fromApiJsonHelper=fromApiJsonHelper;
		this.settlementJpaRepository = settlementJpaRepository;
		this.mediaSettlementReadPlatformService = mediaSettlementReadPlatformService;

	}

	@Override
	public CommandProcessingResult createMediaAsset(JsonCommand command) {

		try {

		 this.context.authenticatedUser();
		     this.fromApiJsonDeserializer.validateForCreate(command.json());
			  MediaAsset mediaAsset=MediaAsset.fromJson(command);
			 final JsonArray mediaassetAttributesArray=command.arrayOfParameterNamed("mediaassetAttributes").getAsJsonArray();
			    String[] mediaassetAttributes =null;
			    mediaassetAttributes=new String[mediaassetAttributesArray.size()];
			    for(int i=0; i<mediaassetAttributesArray.size();i++){
			    	mediaassetAttributes[i] =mediaassetAttributesArray.get(i).toString();
			    	//JsonObject temp = mediaassetAttributesArray.getAsJsonObject();
			    	

			    }
			   //For Media Attributes
				 for (String mediaassetAttribute : mediaassetAttributes) 
				 {
				
					     final JsonElement element = fromApiJsonHelper.parse(mediaassetAttribute);
					     final String mediaAttributeType = fromApiJsonHelper.extractStringNamed("attributeType", element);
					     final String mediaattributeName = fromApiJsonHelper.extractStringNamed("attributeName", element);
					     final String mediaattributeValue = fromApiJsonHelper.extractStringNamed("attributevalue", element);
					     final String mediaattributeNickname= fromApiJsonHelper.extractStringNamed("attributeNickname", element);
					     final String mediaattributeImage= fromApiJsonHelper.extractStringNamed("attributeImage", element);
	                     MediaassetAttributes attributes=new MediaassetAttributes(mediaAttributeType,mediaattributeName,mediaattributeValue,
			             mediaattributeNickname,mediaattributeImage);
   	                     mediaAsset.add(attributes);
				  }
				 
				  final JsonArray mediaassetLocationsArray=command.arrayOfParameterNamed("mediaAssetLocations").getAsJsonArray();
				  String[] mediaassetLocations =null;
				  mediaassetLocations=new String[mediaassetLocationsArray.size()];
				  for(int i=0; i<mediaassetLocationsArray.size();i++){
					  
				    	mediaassetLocations[i] =mediaassetLocationsArray.get(i).toString();
   			       
				  }
				   //For Media Attributes
					 for (String mediaassetLocationData : mediaassetLocations) {
						 
						     final JsonElement element = fromApiJsonHelper.parse(mediaassetLocationData);
						     final Long languageId = fromApiJsonHelper.extractLongNamed("languageId", element);
						     final String formatType = fromApiJsonHelper.extractStringNamed("formatType", element);
						     final String location = fromApiJsonHelper.extractStringNamed("location", element);
		              MediaassetLocation mediaassetLocation = new MediaassetLocation(languageId,formatType,location);
		              mediaAsset.addMediaLocations(mediaassetLocation);
					  }		 
				 
                     this.assetRepository.save(mediaAsset);
			         return new CommandProcessingResult(mediaAsset.getId());

		} catch (DataIntegrityViolationException dve) {
			 handleCodeDataIntegrityIssues(command, dve);
			return new CommandProcessingResult(Long.valueOf(-1));
		}
	}
	
	@Override
	public CommandProcessingResult createGameMediaAsset(JsonCommand command) {
		
		MediaAsset mediaAsset = null;
		try{
			this.context.authenticatedUser();
			fromApiJsonDeserializer.validateForCreateGame(command.json());
			mediaAsset = MediaAsset.fromGameMediaJsonForm(command);
			
			PartnerAccountData contentProvider = mediaSettlementReadPlatformService.retrieveContentProviderPartnerId(mediaAsset.getContentProvider()); 
			if(!command.stringValueOfParameterNamed("mediaCategory").equalsIgnoreCase("Games")){
				throw new NotaContentProviderException("This game category is not available","this game category is not available");
			}
			if(contentProvider == null){
				throw new NotaContentProviderException();
			}else if(contentProvider!=null){
				if(!contentProvider.getPartnerName().equalsIgnoreCase("Content Provider")){
					throw new NotaContentProviderException();
				}
			}
			mediaAsset.setContentProvider(contentProvider.getId().toString());
			assetRepository.save(mediaAsset);
			
			/*final JsonArray gameData = command.arrayOfParameterNamed("gameMediaData").getAsJsonArray();
		    for(int i=0; i<gameData.size();i++){
		    	String currentData = gameData.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    	
		    	 final BigDecimal amount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("amount", element);
			     final String category = fromApiJsonHelper.extractStringNamed("category", element);
			     final String mediaContentProvider = fromApiJsonHelper.extractStringNamed("mediaContentProvider", element);
			     final String mt = fromApiJsonHelper.extractStringNamed("mediaType", element);
			     final Character mediaType = mt.equals("Flat")?'F':'P';
			     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
			     final String source = fromApiJsonHelper.extractStringNamed("source", element);
			     
			     
			     Settlement settlement = Settlement.fromJson(mediaAsset.getId(),amount,category,mediaContentProvider,mediaType,sequence,source);
			     settlementJpaRepository.save(settlement);		     
		    }*/
			
		}catch(EmptyResultDataAccessException e){
			throw new PlatformDataIntegrityException("not.a.valid.content.provider", "not.a.valid.content.provider", "ContentProvider");	
		}catch (DataIntegrityViolationException dve) {
			 handleCodeDataIntegrityIssues(command, dve);
			 throw new DataIntegrityViolationException(dve.toString());
		}catch(Exception e){
			throw new DataIntegrityViolationException(e.toString());
		}
		  return new CommandProcessingResult(mediaAsset.getId());
	}
	
	@Override
	public CommandProcessingResult updateGameMediaAsset(JsonCommand command) {
		
		MediaAsset mediaAssetMaster = null;
		try{
			this.context.authenticatedUser();
			final Long mediaId = command.entityId();
			mediaAssetMaster = this.assetRepository.findOne(mediaId);

	        if (mediaAssetMaster == null) { throw new NoMoviesFoundException("error.media.assets.game.not.found","No Game Asset Found"); }
	        
	        final Map<String, Object> changes = mediaAssetMaster.updateForGame(command);
	        if(!changes.isEmpty()){
	        	assetRepository.save(mediaAssetMaster);
	        }		
			
			final JsonArray gameData = command.arrayOfParameterNamed("gameMediaData").getAsJsonArray();
		    for(int i=0; i<gameData.size();i++){
		    	String currentData = gameData.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    	 
		    	 final Long settlementId = fromApiJsonHelper.extractLongNamed("settlementId", element);
		    	 if(settlementId==null){
		    		 
		    		 
			    	 final BigDecimal amount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("amount", element);
				     final String category = fromApiJsonHelper.extractStringNamed("category", element);
				     final String mediaContentProvider = fromApiJsonHelper.extractStringNamed("mediaContentProvider", element);
				     final String mt = fromApiJsonHelper.extractStringNamed("mediaType", element);
				     final Character mediaType = mt.equals("Flat")?'F':'P';
				     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
				     final String source = fromApiJsonHelper.extractStringNamed("source", element);
				    
		    		 final Settlement settlement = Settlement.fromJson(mediaId, amount, category, mediaContentProvider, mediaType, sequence,source);
		    		 settlementJpaRepository.save(settlement);
		    	 }else{
		    		 final Settlement settlement = settlementJpaRepository.findOne(settlementId);
			    	 if(settlement==null){
			    		 throw new SettlementNotFoundException();
			    	 }
			    	 		    	 
			    	 final Map<String,Object> settlementChanges = settlement.update(element,fromApiJsonHelper,mediaId);

				     if(!settlementChanges.isEmpty()){
				    	 settlementJpaRepository.save(settlement);
				     }
		    	 }
		    	 
		    	 
		    	
		    	
			     		     
		    }
			
		}catch (DataIntegrityViolationException dve) {
			 handleCodeDataIntegrityIssues(command, dve);
				return new CommandProcessingResult(Long.valueOf(-1));
		}
		  return new CommandProcessingResult(mediaAssetMaster.getId());
	}
	
	
	
	
	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		
		Throwable realCause = dve.getMostSpecificCause();
        if (realCause.getMessage().contains("title_UNIQUE")){
        	throw new PlatformDataIntegrityException("game already exist", "game already exist", "game already exist","");      	
        }
		
	}

	@Override
	public CommandProcessingResult updateAsset(JsonCommand command) {
		try {
		    this.context.authenticatedUser();
		    this.fromApiJsonDeserializer.validateForCreate(command.json());
		    final MediaAsset mediaAsset=retriveMessageBy(command.entityId());
		    mediaAsset.getMediaassetAttributes().clear();
		    mediaAsset.getMediaassetLocations().clear();
		 
	        final Map<String, Object> changes = mediaAsset.updateAssetDetails(command);
	        
	        final JsonArray mediaassetAttributesArray=command.arrayOfParameterNamed("mediaassetAttributes").getAsJsonArray();
	        
		    String[] assetAttributes =null;
		    assetAttributes=new String[mediaassetAttributesArray.size()];
		    for(int i=0; i<mediaassetAttributesArray.size();i++){
		    	assetAttributes[i] =mediaassetAttributesArray.get(i).toString();			    
		    }
		   //For Media Attributes
			 for (String mediaassetAttribute : assetAttributes) {
				
				     final JsonElement element = fromApiJsonHelper.parse(mediaassetAttribute);
				     final String mediaAttributeType = fromApiJsonHelper.extractStringNamed("attributeType", element);
				     final String mediaattributeName = fromApiJsonHelper.extractStringNamed("attributeName", element);
				     final String mediaattributeValue = fromApiJsonHelper.extractStringNamed("attributevalue", element);
				     final String mediaattributeNickname= fromApiJsonHelper.extractStringNamed("attributeNickname", element);
				     final String mediaattributeImage= fromApiJsonHelper.extractStringNamed("attributeImage", element);
				     MediaassetAttributes attributes=new MediaassetAttributes(mediaAttributeType,mediaattributeName,
		               mediaattributeValue,mediaattributeNickname,mediaattributeImage);
	   	                     mediaAsset.add(attributes);	           
	            
			  }
			 
			  final JsonArray mediaassetLocationsArray=command.arrayOfParameterNamed("mediaAssetLocations").getAsJsonArray();
			 
			  String[] mediaassetLocations =null;
			  mediaassetLocations=new String[mediaassetLocationsArray.size()];
			  for(int i=0; i<mediaassetLocationsArray.size();i++){
				  
			    	mediaassetLocations[i] =mediaassetLocationsArray.get(i).toString();
			       
			  }
			   //For Media Attributes
				 for (String mediaassetLocationData : mediaassetLocations) {
					 
					     final JsonElement element = fromApiJsonHelper.parse(mediaassetLocationData);
					     final Long languageId = fromApiJsonHelper.extractLongNamed("languageId", element);
					     final String formatType = fromApiJsonHelper.extractStringNamed("formatType", element);
					     final String location = fromApiJsonHelper.extractStringNamed("location", element);
	              MediaassetLocation mediaassetLocation = new MediaassetLocation(languageId,formatType,location);
	              mediaAsset.addMediaLocations(mediaassetLocation);
				  }		 
				 if(!changes.isEmpty()){
				     this.assetRepository.save(mediaAsset);
				   }
                 return new CommandProcessingResult(mediaAsset.getId());
				 
                
             }catch (DataIntegrityViolationException dve) {
               			 handleCodeDataIntegrityIssues(command, dve);
               			return new CommandProcessingResult(Long.valueOf(-1));
              }
	}
	
	 private MediaAsset retriveMessageBy(Long assetId) {
         final MediaAsset mediaAsset = this.assetRepository.findOne(assetId);
          return mediaAsset;
}

	 @Override
		public CommandProcessingResult deleteAsset(JsonCommand command) {
			               context.authenticatedUser();
			               final MediaAsset mediaAsset=retriveMessageBy(command.entityId());
			               if (mediaAsset == null) {
				                throw new DataIntegrityViolationException(mediaAsset.toString());
			                }
			                  mediaAsset.isDelete();
			                  this.assetRepository.save(mediaAsset);
			                  return new CommandProcessingResult(mediaAsset.getId());
	    }
}
