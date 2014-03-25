package org.mifosplatform.billing.mediasettlement.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.billing.mediasettlement.data.OperatorStageDetailData;
import org.mifosplatform.billing.mediasettlement.domain.AccountPartnerJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.ChannelPartnerMappingJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.CurrencyRate;
import org.mifosplatform.billing.mediasettlement.domain.CurrencyRateJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveDetails;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveDetailsJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveHeader;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveHeaderJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.OperatorDeduction;
import org.mifosplatform.billing.mediasettlement.domain.OperatorDeductionJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.OperatorStage;
import org.mifosplatform.billing.mediasettlement.domain.OperatorStageDetailJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAccount;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreement;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreementDetail;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreementDetailRepository;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreementRepository;
import org.mifosplatform.billing.mediasettlement.domain.PartnerGame;
import org.mifosplatform.billing.mediasettlement.domain.PartnerGameDetails;
import org.mifosplatform.billing.mediasettlement.domain.PartnerGameDetailsJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.PartnerGameJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.PlatformStage;
import org.mifosplatform.billing.mediasettlement.domain.PlatformStageJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.RevenueMaster;
import org.mifosplatform.billing.mediasettlement.domain.RevenueMasterJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.RevenueOEMSettlement;
import org.mifosplatform.billing.mediasettlement.domain.RevenueOEMSettlementJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.RevenueParam;
import org.mifosplatform.billing.mediasettlement.domain.RevenueSettlement;
import org.mifosplatform.billing.mediasettlement.domain.RevenueSettlementJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.SettlementSequence;
import org.mifosplatform.billing.mediasettlement.domain.SettlementSequenceJpaRepository;
import org.mifosplatform.billing.mediasettlement.exception.AccountPartnerNotFoundException;
import org.mifosplatform.billing.mediasettlement.exception.CurrencyRateNotFoundException;
import org.mifosplatform.billing.mediasettlement.exception.OperatorDeductionCodeNotFountException;
import org.mifosplatform.billing.mediasettlement.exception.PartnerAgreementNotFoundException;
import org.mifosplatform.billing.mediasettlement.exception.PartnerGameNotFoundException;
import org.mifosplatform.billing.mediasettlement.serialization.MediaSettlementCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.core.service.FileUtils;
import org.mifosplatform.infrastructure.documentmanagement.exception.DocumentManagementException;
import org.mifosplatform.infrastructure.documentmanagement.exception.DocumentNotFoundException;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.client.domain.Client;
import org.mifosplatform.portfolio.client.domain.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@Service
public class MediaSettlementWritePlatformServiceImp implements MediaSettlementWritePlatformService{

	final private AccountPartnerJpaRepository accountPartnerJpaRepository;
	final private OperatorDeductionJpaRepository operatorDeductionJpaRepository;
	final private PartnerGameDetailsJpaRepository partnerGameDetailsJpaRepository;
	final private PlatformSecurityContext context;
	final private MediaSettlementCommandFromApiJsonDeserializer fromApiJsonDeserializer;
	final private FromJsonHelper fromApiJsonHelper;
	final private PartnerGameJpaRepository partnerGameJpaRepository;
	final private MediaSettlementReadPlatformService mediaSettlementReadPlatformService;
	final private RevenueSettlementJpaRepository revenueSettlementJpaRepository;
	final private RevenueOEMSettlementJpaRepository revenueOEMSettlementJpaRepository;
	final private PartnerAgreementRepository partnerAgreementRepository;
	final private PartnerAgreementDetailRepository partnerAgreementDetailRepository;
	final private ChannelPartnerMappingJpaRepository channelPartnerJpaRepository;
	final private SettlementSequenceJpaRepository settlementSequenceJpaRepository;
	final private ClientRepository clientRepository;
	final private InteractiveHeaderJpaRepository interactiveHeaderJpaRepository;
	final private InteractiveDetailsJpaRepository interactiveDetailsJpaRepository;	
	final private RevenueMasterJpaRepository revenueMasterJpaRepository;
	final private CurrencyRateJpaRepository currencyRateJpaRepository;
	final private TransactionHistoryWritePlatformService transactionHistoryWritePlatformService;
	final private OperatorStageDetailJpaRepository rawInteractiveHeaderDetailJpaRepository;
	final private OperatorStageDetailReadPlatformService rawInteractiveHeaderDetailReadPlatformService;
	final private PlatformStageJpaRepository platformStageJpaRepository;
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(MediaSettlementWritePlatformServiceImp.class);
	
	@Autowired
	public MediaSettlementWritePlatformServiceImp(final AccountPartnerJpaRepository accountPartnerJpaRepository,
			final PlatformSecurityContext context,
			final MediaSettlementCommandFromApiJsonDeserializer apiJsonDeserializer,
			final PartnerGameJpaRepository partnerGameJpaRepository,
			final FromJsonHelper fromApiJsonHelper,
			final PartnerGameDetailsJpaRepository partnerGameDetailsJpaRepository,
			final MediaSettlementReadPlatformService mediaSettlementReadPlatformService,
			final RevenueSettlementJpaRepository revenueSettlementJpaRepository,
			final PartnerAgreementRepository partnerAgreementRepository,
			final RevenueOEMSettlementJpaRepository revenueOEMSettlementJpaRepository,
			final ChannelPartnerMappingJpaRepository channelPartnerMappingJpaRepository,
			final OperatorDeductionJpaRepository operatorDeductionJpaRepository,
			final SettlementSequenceJpaRepository settlementSequenceJpaRepository,
			final ClientRepository clientReposritory,
			final InteractiveHeaderJpaRepository interactiveHeaderJpaRepository,
			final InteractiveDetailsJpaRepository interactiveDetailsJpaRepository,
			final RevenueMasterJpaRepository revenueMasterJpaRepository,
			final PartnerAgreementDetailRepository partnerAgreementDetailRepository,
			final  CurrencyRateJpaRepository currencyRateJpaRepository,
			final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService,
			final OperatorStageDetailJpaRepository rawInteractiveHeaderDetailJpaRepository,
			final OperatorStageDetailReadPlatformService rawInteractiveHeaderDetailReadPlatformService,
			final PlatformStageJpaRepository platformStageJpaRepository) {

		this.context = context;
		this.accountPartnerJpaRepository = accountPartnerJpaRepository;
		this.fromApiJsonDeserializer = apiJsonDeserializer;
		this.partnerGameJpaRepository = partnerGameJpaRepository;
		this.fromApiJsonHelper = fromApiJsonHelper;
		this.partnerGameDetailsJpaRepository = partnerGameDetailsJpaRepository;
		this.mediaSettlementReadPlatformService = mediaSettlementReadPlatformService;
		this.revenueSettlementJpaRepository = revenueSettlementJpaRepository;
		this.partnerAgreementRepository = partnerAgreementRepository;
		this.revenueOEMSettlementJpaRepository = revenueOEMSettlementJpaRepository;
		this.channelPartnerJpaRepository = channelPartnerMappingJpaRepository;
		this.operatorDeductionJpaRepository = operatorDeductionJpaRepository;
		this.settlementSequenceJpaRepository = settlementSequenceJpaRepository;
		this.clientRepository = clientReposritory;
		this.interactiveHeaderJpaRepository = interactiveHeaderJpaRepository;
		this.interactiveDetailsJpaRepository = interactiveDetailsJpaRepository;
		this.revenueMasterJpaRepository = revenueMasterJpaRepository;
		this.partnerAgreementDetailRepository=partnerAgreementDetailRepository;
		this.currencyRateJpaRepository = currencyRateJpaRepository;
		this.transactionHistoryWritePlatformService = transactionHistoryWritePlatformService;
		this.rawInteractiveHeaderDetailJpaRepository = rawInteractiveHeaderDetailJpaRepository;
		this.rawInteractiveHeaderDetailReadPlatformService = rawInteractiveHeaderDetailReadPlatformService;
		this.platformStageJpaRepository = platformStageJpaRepository;

	}
	
	
	@Transactional
	@Override
	public CommandProcessingResult createAccountPartner(JsonCommand command) {
		
		context.authenticatedUser();
		PartnerAccount accountPartner = null;
		try{
			fromApiJsonDeserializer.validateForCreate(command.json());
			accountPartner = PartnerAccount.fomrJson(command);
			
			 accountPartnerJpaRepository.save(accountPartner);		
			 
			 //Long mediaCategory;String partnerName;String partnerAddress;Character isDeleted='N';Long currencyId;Long externalId;String contactNum;String emailId;
			 
			
			}catch(DataIntegrityViolationException e){
				handleDataIntegrityIssue(e);
				return new CommandProcessingResult(Long.valueOf(-1L));
			}
			return new CommandProcessingResultBuilder().withEntityId(accountPartner.getId()).build();	
		}
	
	
	@Transactional
	@Override
	public CommandProcessingResult createPartnerGame(JsonCommand command){
		
		context.authenticatedUser();
		PartnerGame game = null;
		/*PartnerGameDetails partnerGameDetails = null;*/
		
		try{
		
			fromApiJsonDeserializer.validateForCreatePartnerGame(command.json());
			game = PartnerGame.fromJson(command);
			
					
			
			final JsonArray gameMediaPartnerDataArray = command.arrayOfParameterNamed("gameMediaPartnerData").getAsJsonArray();
		    for(int i=0; i<gameMediaPartnerDataArray.size();i++){
		    	String currentData = gameMediaPartnerDataArray.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    	
		    	 final LocalDate gDate = fromApiJsonHelper.extractLocalDateNamed("gDate", element);
			     Long gameL = fromApiJsonHelper.extractLongNamed("game", element);
			     
			     if(gameL == null){
			    	 gameL = fromApiJsonHelper.extractLongNamed("gameL", element);
			     }
			     
			     
			     
			     BigDecimal overwriteRoyaltyValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("overwriteRoyaltyValue", element);
			     if(overwriteRoyaltyValue == null){
			    	 overwriteRoyaltyValue = game.getRoyaltyValue();
			     }
			     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
			     final BigDecimal price = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("price", element);
			     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
			     PartnerGameDetails gameDetails = PartnerGameDetails.fromJson(gDate,gameL,overwriteRoyaltyValue,playSource,price,sequence);
			     game.add(gameDetails);		     
		    }
		    partnerGameJpaRepository.save(game);
					
		}catch(DataIntegrityViolationException e){
			handleDataIntegrityIssue(e);
			throw e;
		}catch(PlatformDataIntegrityException e){
			throw new PlatformDataIntegrityException(e.getGlobalisationMessageCode(), e.getDefaultUserMessage(), e.getDefaultUserMessageArgs());
		}	
		return new CommandProcessingResultBuilder().withEntityId(game.getId()).build();
	}
	
	
	private void handleDataIntegrityIssue(DataIntegrityViolationException e) {
		
		Throwable realCause = e.getMostSpecificCause();
		logger.error(e.getMessage(), e);
        if (realCause.getMessage().contains("partner_name_UNIQUE")) {

            final String externalId = "partnerName";
            throw new PlatformDataIntegrityException("error.msg.settlement.duplicate.partnerName", "Partner Account with name `" + externalId
                    + "` already exists", "PartnerName", externalId);
        }else if (realCause.getMessage().contains("bp_games_pid_ps_rv_rs_uniquekey")) {

            final String externalId = "partnerName";
            throw new PlatformDataIntegrityException("error.msg.settlement.duplicate.games.assignment", "Partner Account with name `" + externalId
                    + "` already exists", "PartnerName", externalId);
        }else if (realCause.getMessage().contains("channelmappinguniquekey")) {

            final String externalId = "partnerName";
            throw new PlatformDataIntegrityException("error.msg.settlement.duplicate.games.assignment", "Partner Account with name `" + externalId
                    + "` already exists", "PartnerName", externalId);
        }
		
	}


	/*@Transactional
	@Override
	public CommandProcessingResult updatePartnerGame(JsonCommand command, Long entityId) {
		
		PartnerGame game = null;
		
		try{
			fromApiJsonDeserializer.validateForCreatePartnerGame(command.json());
			game = partnerGameJpaRepository.findOne(entityId);
			game.getPartnerGameDetails().clear();
			final Map<String, Object> gameChanges = game.update(command);			
			
			final JsonArray gameMediaPartnerDataArray = command.arrayOfParameterNamed("gameMediaPartnerData").getAsJsonArray();
		    for(int i=0; i<gameMediaPartnerDataArray.size();i++){
		    	String currentData = gameMediaPartnerDataArray.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    	
		    	 final LocalDate gDate = fromApiJsonHelper.extractLocalDateNamed("gDate", element);
			     Long gameL = fromApiJsonHelper.extractLongNamed("game", element);
			     if(gameL == null){
			    	 gameL = fromApiJsonHelper.extractLongNamed("gameL", element);
			     }
			     final BigDecimal overwriteRoyaltyValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("overwriteRoyaltyValue", element);
			     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
			     final BigDecimal price = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("price", element);
			     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
			     PartnerGameDetails gameDetails = PartnerGameDetails.fromJson(gDate,gameL,overwriteRoyaltyValue,playSource,price,sequence);
			     game.add(gameDetails);		     
		    }
		
                this.partnerGameJpaRepository.save(game);
		    
		}catch(DataIntegrityViolationException e){
			throw e;
		}
		
		return new CommandProcessingResultBuilder().withEntityId(game.getId()).build();
	}*/
	
	
	@Transactional
	@Override
	public CommandProcessingResult createRevenueSettlement(JsonCommand command) {
		
		RevenueSettlement revenueSettlement = null;
		RevenueOEMSettlement revenueOEMSettlement = null;
		/*revenueSettlementJpaRepository.deleteAll();
		revenueOEMSettlementJpaRepository.deleteAll();*/
		String g = command.stringValueOfParameterNamed("game");
		BigDecimal gamePrice = command.bigDecimalValueOfParameterNamed("gamePrice");
				/*if(g==null){
					throw new PlatformDataIntegrityException("No Game Defined", "No Game Defined","game","No Game Defined");
	
				}*/
		fromApiJsonDeserializer.validateForCreatePartnerRevenue(command.json());
		try{
			/*BigDecimal gamePrice = mediaSettlementReadPlatformService.getGamePrice(command.stringValueOfParameterNamed("game")).getPrice();*/
			final String partnerType = command.stringValueOfParameterNamed("partnerType");
			
				
				if(partnerType.equalsIgnoreCase("Channel") || partnerType.equalsIgnoreCase("CH")){
					revenueOEMSettlement = RevenueOEMSettlement.fromJson(command,gamePrice);
					if(revenueOEMSettlement.getAgreementCategory() == null || revenueOEMSettlement.getAgreementCategory() <= 0){
						/*throw new PlatformDataIntegrityException("no agreement found", "no agreement found", "no agreement found");*/
					}
					revenueOEMSettlementJpaRepository.save(revenueOEMSettlement);
				}else{
					revenueSettlement = RevenueSettlement.fromJson(command,gamePrice);
					if(revenueSettlement.getAgreementCategory() == null || revenueSettlement.getAgreementCategory() <= 0){
						/*throw new PlatformDataIntegrityException("no agreement found", "no agreement found", "no agreement found");*/
					}
					revenueSettlementJpaRepository.save(revenueSettlement);
				}
				
			
			
		}catch(NullPointerException e){
			throw new PlatformDataIntegrityException("Game Price Is not Defined for This Game","Game Price Is not Defined for This Game", "Game Price Is not Defined for This Game");
		}catch(DataIntegrityViolationException dve){
			//logger.error(dve.getMessage(), dve);
			handleDataIntegrityIssue(dve);
		}
		return new CommandProcessingResultBuilder().withEntityId(revenueSettlement==null?revenueOEMSettlement.getId():revenueSettlement.getId()).build();
	}
	
	
	@Transactional
	@Override
	public CommandProcessingResult updatePartnerAccount(JsonCommand command,
			Long entityId) {
		context.authenticatedUser();
		
		
		try{
			fromApiJsonDeserializer.validateForCreate(command.json());
			final PartnerAccount account = accountPartnerJpaRepository.findOne(entityId);
			final Map<String,Object> changes = account.update(command);;

            if (!changes.isEmpty()) {
                this.accountPartnerJpaRepository.save(account);
            }
		}catch(DataIntegrityViolationException e){
			handleCodeDataIntegrityIssues(command, e);
			return new CommandProcessingResult(Long.valueOf(-1L));
		}
		
		return new CommandProcessingResultBuilder().withEntityId(command.entityId()).build();
	}
	
	
	@Override
	public CommandProcessingResult createPAmediaCatregory(JsonCommand command) {
		// TODO Auto-generated method stub
		PartnerAgreement detailMaster=null;
		PartnerAgreementDetail detailChild=null;
		PartnerAgreement detailMasterTable=null;
		
		try{
		this.fromApiJsonDeserializer.validateForCreatePartnerAgreement(command.json());
		
		detailMaster=PartnerAgreement.fromJson(command);
		
		
		Long paId=mediaSettlementReadPlatformService.checkPartnerAgreementId(detailMaster.getPartnerAccountId());
        if(paId == null){
        	
        	final JsonArray partnerAgreementDataArray = command.arrayOfParameterNamed("partnerAgreementData").getAsJsonArray();
		    for(int i=0; i<partnerAgreementDataArray.size();i++){
		    	String currentData = partnerAgreementDataArray.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    	
			     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
			     final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
			     BigDecimal royaltyShare = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("royaltyShare", element);
	    		 final Long royaltySequence = fromApiJsonHelper.extractLongNamed("royaltySequence", element);
			     final Long status = fromApiJsonHelper.extractLongNamed("status", element);
			     final Long partnerAccountId = detailMaster.getPartnerAccountId();
			    
			     detailChild=PartnerAgreementDetail.createNew(playSource,royaltyShare,royaltySequence, mediaCategory,status,partnerAccountId);
		          detailMaster.add(detailChild);
		    }
	          this.partnerAgreementRepository.save(detailMaster);		        	 
        }
        else{
        	
        	 detailMasterTable=this.partnerAgreementRepository.findOne(paId);
 	         
        	if(detailMasterTable.getAgreementCategory() == null && detailMaster.getAgreementCategory() == null){
   			 
        	}else if(detailMasterTable.getAgreementCategory() == null && detailMaster.getAgreementCategory() != null){
   		 		detailMasterTable.setAgreementCategory(detailMaster.getAgreementCategory());
   		 	}else if(!detailMasterTable.getAgreementCategory().equals(detailMaster.getAgreementCategory()) ){
        		detailMasterTable.setAgreementCategory(detailMaster.getAgreementCategory());		    		
   		 	}
        	
        	if(detailMasterTable.getAgreementType() == null && detailMaster.getAgreementType() == null){
      			 
        	}else if(detailMasterTable.getAgreementType() == null && detailMaster.getAgreementType() != null){
   		 		detailMasterTable.setAgreementType(detailMaster.getAgreementType());
   		 	}else if(!detailMasterTable.getAgreementType().equals(detailMaster.getAgreementType()) ){
        		detailMasterTable.setAgreementType(detailMaster.getAgreementType());		    		
   		 	}
        	
        	if(detailMasterTable.getRoyaltyType() == null && detailMaster.getRoyaltyType() == null){
     			 
        	}else if(detailMasterTable.getRoyaltyType() == null && detailMaster.getRoyaltyType() != null){
   		 		detailMasterTable.setRoyaltyType(detailMaster.getRoyaltyType());
   		 	}else if(!detailMasterTable.getRoyaltyType().equals(detailMaster.getRoyaltyType()) ){
        		detailMasterTable.setRoyaltyType(detailMaster.getRoyaltyType());		    		
   		 	}
        	
        	if(detailMasterTable.getStartDate() == null && detailMaster.getStartDate() == null){
    			 
        	}else if(detailMasterTable.getStartDate() == null && detailMaster.getStartDate() != null){
   		 		detailMasterTable.setStartDate(detailMaster.getStartDate());
   		 	}else if(!detailMasterTable.getStartDate().equals(detailMaster.getStartDate()) ){
        		detailMasterTable.setStartDate(detailMaster.getStartDate());		    		
   		 	}
        	
        	if(detailMasterTable.getEndDate() == null && detailMaster.getEndDate() == null){
   			 
        	}else if(detailMasterTable.getEndDate() == null && detailMaster.getEndDate() != null){
   		 		detailMasterTable.setEndDate(detailMaster.getEndDate());
   		 	}else if(!detailMasterTable.getEndDate().equals(detailMaster.getEndDate()) ){
        		detailMasterTable.setEndDate(detailMaster.getEndDate());		    		
   		 	}
	         
        	if(detailMasterTable.getSettlementSource() == null && detailMaster.getSettlementSource() == null){
      			 
        	}else if(detailMasterTable.getSettlementSource() == null && detailMaster.getSettlementSource() != null){
   		 		detailMasterTable.setSettlementSource(detailMaster.getSettlementSource());
   		 	}else if(!detailMasterTable.getSettlementSource().equals(detailMaster.getSettlementSource()) ){
        		detailMasterTable.setSettlementSource(detailMaster.getSettlementSource());		    		
   		 	}
        	
        	if(detailMasterTable.getMgAmount() == null && detailMaster.getMgAmount() == null){
     			 
        	}else if(detailMasterTable.getMgAmount() == null && detailMaster.getMgAmount() != null){
   		 		detailMasterTable.setMgAmount(detailMaster.getMgAmount());
   		 	}else if(detailMasterTable.getMgAmount() == detailMaster.getMgAmount()){
   		 		
   		 	}else if(!detailMasterTable.getMgAmount().equals(detailMaster.getMgAmount()) ){
        		detailMasterTable.setMgAmount(detailMaster.getMgAmount());		    		
   		 	}
        	
        	
	         final JsonArray partnerAgreementDataArray = command.arrayOfParameterNamed("partnerAgreementData").getAsJsonArray();
			    for(int i=0; i<partnerAgreementDataArray.size();i++){
			    	String currentData = partnerAgreementDataArray.get(i).toString();
			    	final JsonElement element = fromApiJsonHelper.parse(currentData);
			    	
				     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
				     final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
				     BigDecimal royaltyShare = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("royaltyShare", element);
		    		 final Long royaltySequence = fromApiJsonHelper.extractLongNamed("royaltySequence", element);
				     final Long status = fromApiJsonHelper.extractLongNamed("status", element);
				     final Long partnerAccountId = detailMaster.getPartnerAccountId();
				    
				     detailChild=PartnerAgreementDetail.createNew(playSource,royaltyShare,royaltySequence, mediaCategory,status,partnerAccountId);
				     detailMasterTable.add(detailChild);
			    }
	          this.partnerAgreementRepository.save(detailMasterTable);
        	
        }
			   
		
		
	} catch (DataIntegrityViolationException dve) {
		handleCodeDataIntegrityIssues(command, dve);
	     throw new PlatformDataIntegrityException("error.msg.document.unknown.data.integrity.issue",
	             ""+dve.getMostSpecificCause().getMessage());
	  
	}
		if(detailMaster.getId() == null){
			return new CommandProcessingResult( detailMasterTable.getId());	
		}else{
			return new CommandProcessingResult( detailMaster.getId());
		}
			
	}
	
	
	
	
	@Transactional
	@Override
	public CommandProcessingResult createAgreement(
			MediaSettlementCommand command) {
		// TODO Auto-generated method stub
	
			PartnerAgreement detailMaster=null;
			try {
			
		         String fileLocation=null;
		         fileLocation = FileUtils.saveToFileSystem(command.getInputStream(), command.getAgmtLocation(),command.getFileName());
		         context.authenticatedUser().getId();
		         
		         
		         Long paId=mediaSettlementReadPlatformService.checkPartnerAgreementId(command.getPartnerAccountId());
		         if(paId == null){
		        	 
		        	 detailMaster=PartnerAgreement.createFileLocation(command.getPartnerAccountId(),fileLocation,command.getFileName());			
		        	 this.partnerAgreementRepository.save(detailMaster);	
		         }
		         else{
		        	 detailMaster=this.partnerAgreementRepository.findOne(paId);
		        	 
		        	 if(detailMaster.getAgmtLocation() == null && fileLocation == null){
		    			 
		    		 }else if(detailMaster.getAgmtLocation() == null && fileLocation != null){
		    			 detailMaster.setAgmtLocation(fileLocation);
		    		 }else if(!detailMaster.getAgmtLocation().equals(fileLocation) ){
			        	 detailMaster.setAgmtLocation(fileLocation);				    		
		    		 }
		        	 
		        	 if(detailMaster.getFileName() == null && command.getFileName() == null){
		    			 
		    		 }else if(detailMaster.getFileName() == null && command.getFileName() != null){
		    			 detailMaster.setFileName(command.getFileName());
		    		 }else if(!detailMaster.getFileName().equals(command.getFileName()) ){
			        	 detailMaster.setFileName(fileLocation);				    		
		    		 }
		        	 
		        	 this.partnerAgreementRepository.save(detailMaster);	
		        	 
		         }
		         
		} catch (DataIntegrityViolationException dve) {
			logger.error(dve.getMessage(), dve);
		     throw new PlatformDataIntegrityException("error.msg.document.unknown.data.integrity.issue",
		             ""+dve.getMostSpecificCause().getMessage());
		     
		}catch (final IOException ioe) {
		    logger.error(ioe.getMessage(), ioe);
		    throw new DocumentManagementException(command.getFileName());
		}
			
				return new CommandProcessingResult( detailMaster.getId());	
				
		
	}	

	@Transactional
    @Override
    public CommandProcessingResult updateDocument( MediaSettlementCommand documentCommand,  InputStream inputStream,
    		String entityType,Long entityId) {
        try {
            this.context.authenticatedUser();

           /*PartnerAgreementDetail documentUpdate = this.partnerAgreementDetailRepository.findOne(documentCommand.getId());  
           documentUpdate.update(documentCommand);*/
            
            String oldLocation = null;
            PartnerAgreement documentForUpdate = this.partnerAgreementRepository.findOne(documentCommand.getId());
            if (documentForUpdate == null) { 
            	
            	throw new DocumentNotFoundException(entityType,
            			entityId, documentCommand.getId());
            	
            }
            oldLocation = documentForUpdate.getAgmtLocation();
            // if a new file is also passed in
            if (inputStream != null && documentCommand.isFileNameChanged()) {
            	
                final String fileUploadLocation = FileUtils.generateFileParentDirectory(entityType,entityId );

                /** Recursively create the directory if it does not exist **/
                if (!new File(fileUploadLocation).isDirectory()) {
                    new File(fileUploadLocation).mkdirs();
                }

                // TODO provide switch to toggle between file system appender
                // and Amazon S3 appender
                final String fileLocation = FileUtils.saveToFileSystem(inputStream, fileUploadLocation, documentCommand.getFileName());
                documentCommand.setAgmtLocation(fileLocation);
            }

            documentForUpdate.update(documentCommand);

            if (inputStream != null && documentCommand.isFileNameChanged()) {
                // delete previous file
                deleteFile(documentCommand.getFileName(), oldLocation);
            }

            
//            documentForUpdate.setLastModifiedDate(documentUpdate.getLastModifiedDate());
            
            this.partnerAgreementRepository.saveAndFlush(documentForUpdate);
//            this.partnerAgreementDetailRepository.saveAndFlush(documentUpdate);
            
           

            return new CommandProcessingResult(documentForUpdate.getId());
        } catch (final DataIntegrityViolationException dve) {
            logger.error(dve.getMessage(), dve);
            throw new PlatformDataIntegrityException(dve.getMessage(),
                    "Unknown data integrity issue with resource.");
        } catch (final IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
            throw new DocumentManagementException(documentCommand.getFileName());
        }
    }

	private void deleteFile(final String documentName, final String location) {
        final File fileToBeDeleted = new File(location);
        final boolean fileDeleted = fileToBeDeleted.delete();
        if (!fileDeleted) { throw new DocumentManagementException(documentName); }
    }
	
	
	@Override
	public CommandProcessingResult deletePartnerAccount(Long entityId) {
	
		 try{
	    	 this.context.authenticatedUser();
	    	 PartnerAccount accountPartner=this.accountPartnerJpaRepository.findOne(entityId);
	    	 
	    	 if(accountPartner==null){
	    		 throw new AccountPartnerNotFoundException(entityId.toString());
	    	 }
	    	 
	    	 accountPartner.delete();
	    	 this.accountPartnerJpaRepository.save(accountPartner);
	    	 return new CommandProcessingResult(entityId);
	    	 
	    	 
	     }catch(DataIntegrityViolationException e){
	    	 throw new PlatformDataIntegrityException(""+e.getMessage(), ""+e.getMostSpecificCause().getCause().getMessage(), "");
	     }
		}
	
	@Override
	public CommandProcessingResult createOperatorDeduction(JsonCommand command) {
		
		
		OperatorDeduction deduction = null;
		
		try{
			
		this.fromApiJsonDeserializer.validateForCreateDeduction(command.json());
		
		final JsonElement element = fromApiJsonHelper.parse(command.json());
		
		final JsonArray deductionData = fromApiJsonHelper.extractJsonArrayNamed("deductionData",element);
		
		String[] chStringArray = new String[deductionData.size()];
   	 	int chStringArraySize = deductionData.size();
	     /*baseDataValidator.reset().parameter(null).value(chStringArraySize).integerGreaterThanZero();*/
   	
	     for(int i=0; i<chStringArraySize;i++){
	    	 chStringArray[i] = deductionData.get(i).toString();
	     }
   	
	     final Long clientId = fromApiJsonHelper.extractLongNamed("clientId", element);

	     
   	for(String s: chStringArray){
   		
   		final JsonElement el = fromApiJsonHelper.parse(s);
   		
   		final String deductionCode = fromApiJsonHelper.extractStringNamed("deductionCode", el);
   		
   		final BigDecimal deductionValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("deductionValue", el);
   		
		deduction = OperatorDeduction.fromJson(clientId,deductionCode,deductionValue);
		operatorDeductionJpaRepository.save(deduction);
   	}
		return new CommandProcessingResultBuilder().withEntityId(clientId).build();
		
		}catch(DataIntegrityViolationException dve){
			handleCodeDataIntegrityIssues(command, dve);
			logger.error(dve.getMessage(),dve);
			return new CommandProcessingResultBuilder().withEntityId(-1L).build();
		}
	}
	
	@Transactional
	@Override
	public CommandProcessingResult updateOperatorDeduction(JsonCommand command,
			Long entityId) {

		OperatorDeduction operatorDeduction = null;
		try{
			
			operatorDeduction = operatorDeductionJpaRepository.findOne(entityId);
			
			if(null == operatorDeduction){
				throw new OperatorDeductionCodeNotFountException(entityId.toString());
			}
			
			fromApiJsonDeserializer.validateForUpdateDeductionCode(command.json());
			
			Map<String, Object> actualChanges = operatorDeduction.update(command);
			if(!actualChanges.isEmpty()){
				OperatorDeduction d = operatorDeductionJpaRepository.save(operatorDeduction);
			}
			
			return new CommandProcessingResultBuilder().withEntityId(operatorDeduction.getId()).build();
		}catch(DataIntegrityViolationException dve){
			handleCodeDataIntegrityIssues(command, dve);
			logger.error(dve.getMessage(),dve);
			return new CommandProcessingResultBuilder().withEntityId(-1L).build();
		}
	}
	
	@Transactional
	@Override
	public CommandProcessingResult deletePartnerDocument(Long entityId) {
		
		 this.context.authenticatedUser();
    	 PartnerAgreement agreement=this.partnerAgreementRepository.findOne(entityId);
    	 
    	 if(agreement==null){
    		 throw new PartnerAgreementNotFoundException(entityId.toString());
    	 }
    	 
    	 agreement.delete();
    	 this.partnerAgreementRepository.save(agreement);
    	 return new CommandProcessingResult(entityId);

	}
	
	@Transactional
	@Override
	public CommandProcessingResult deletePartnerGame(Long entityId) {
		
		this.context.authenticatedUser(); 
    	PartnerGame game=this.partnerGameJpaRepository.findOne(entityId);
    	
    	if(game==null){
	    		 throw new PartnerGameNotFoundException(entityId.toString());
	    }
	    	 
    	List<PartnerGameDetails> gamedetails=game.getPartnerGameDetails();
    	  	 
    	for(PartnerGameDetails details:gamedetails){
    		 details.delete();
    	}
    		
    	game.delete();
    	this.partnerGameJpaRepository.save(game);
    	return new CommandProcessingResult(entityId);

	}
	
	@Transactional
	@Override
	public CommandProcessingResult updateSettlementSequenceData(
			JsonCommand command) {
		// TODO Auto-generated method stub

	try{
		fromApiJsonDeserializer.validateForCreateSettlementSequenceData(command.json());
		
	    Long defaultData= command.longValueOfParameterNamed("defaultData");
		 Long k=1L,m=1L;
		 String str="";
		 for(int i=1;i<4;i++){
		 String activeFlag="Y";	 
		 String name="partnerType"+i;		
		 Long partnerType = command.longValueOfParameterNamed(name); 
		 Long seq=k;
		 Long id=mediaSettlementReadPlatformService.check(defaultData, seq, partnerType,activeFlag);
		 if(id == null){
			 mediaSettlementReadPlatformService.updateChecked(defaultData);
			  str="updated";
		 }
		 k++;
		 }
		 
		 for(int i=1;i<4;i++){
			 String activeFlag="Y";	
			 String name="partnerType"+i;		
			 Long partnerType = command.longValueOfParameterNamed(name); 
			 Long seq=m;
			 if(str.equalsIgnoreCase("updated")){
				 SettlementSequence settlementSequence=new SettlementSequence(defaultData,seq, partnerType,activeFlag);
				 this.settlementSequenceJpaRepository.save(settlementSequence);	 
			 }
			 m++;
			 
			 }
		str="";	
		final JsonArray settlementSequenceData = command.arrayOfParameterNamed("settlementSequenceData").getAsJsonArray();
		
		 if(settlementSequenceData!=null){
			 String str1="";
//			 Long  pad[]= new Long[5];
			 List<Long> pad = new ArrayList<Long>();
		     for (JsonElement jsonelement : settlementSequenceData) {
		    	 
		    	 Long partnerData = fromApiJsonHelper.extractLongNamed("partnerAccountId", jsonelement); 
		    	 int n=4;
		    	 Long se=1L;
	    	     for(int i=1;i<4;i++){
	    	    	 String activeFlag="Y";	 
	    	    	 String name="partnerType"+n;
	    	    	 Long seq=se;
	    	    	 Long partnerType = fromApiJsonHelper.extractLongNamed(name, jsonelement);
	    	    	 Long id=mediaSettlementReadPlatformService.check(partnerData, seq, partnerType,activeFlag);
	    	    	 if(id == null){
	    				 mediaSettlementReadPlatformService.updateChecked(partnerData);
	    				  str1="updated";
	    				  if(pad.contains(partnerData)){
	    					  
	    				  }else{
	    					  pad.add(partnerData);  
	    				  } 
	    			 }
	    	    	 n=n+1;	
	    	    	 se++;
	    	     }
		     }
		     for (JsonElement jsonelement : settlementSequenceData) {
		    	 Long partnerData = fromApiJsonHelper.extractLongNamed("partnerAccountId", jsonelement); 
		    	 int n=4;
		    	 Long se=1L;
		    	 int j=0;
		    	 if(pad.contains(partnerData)){
	    	     for(int i=1;i<4;i++){
	    			 String activeFlag="Y";	
	    			 String name="partnerType"+n;		
	    			 Long partnerType = fromApiJsonHelper.extractLongNamed(name, jsonelement);
	    			 Long seq=se;
	    			 if(str1.equalsIgnoreCase("updated")){
	    				 SettlementSequence settlementSequence=new SettlementSequence(partnerData,seq, partnerType,activeFlag);
	    				 this.settlementSequenceJpaRepository.save(settlementSequence);	 
	    			 }
	    			 n=n+1;	
	    	    	 se++;
	    	       }
		    	 }
		     }
		     pad.clear();
		 }  
		}catch(DataIntegrityViolationException dve){
			handleCodeDataIntegrityIssues(command, dve);
		}
		return new CommandProcessingResult(new Long(1));
	}
	
	@Transactional
	@Override
	public CommandProcessingResult createClientRoyalty(Long entityId,
			JsonCommand command) {
		
		context.authenticatedUser();
		Client clientData=null;
		Client	clientDatas=null;
		try{
			
			this.fromApiJsonDeserializer.validateForCreateRoyalty(command.json());
			clientData=this.clientRepository.findOne(entityId);
			clientDatas=Client.fromJson(command);
			clientData.setRoyaltyType(clientDatas.getRoyaltyType());
			this.clientRepository.save(clientData);
		
		return new CommandProcessingResultBuilder() .withCommandId(command.commandId()).withEntityId(entityId).build();
		} catch (DataIntegrityViolationException dve) {
		 handleCodeDataIntegrityIssues(command, dve);
		return  CommandProcessingResult.empty();
	}    
	}

	
	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		final Throwable realCause = dve.getMostSpecificCause();
		logger.error(dve.getMessage(), dve);
		if(realCause.getMessage().contains("operatordeductionuniquekey")) {
            final String externalId = "partnerName";
            throw new PlatformDataIntegrityException("error.msg.settlement.duplicate.operatordeduction.code.assignment", "Operator Deduction code with combination `" + externalId
                    + "` already exists", "Operator Deduction Code", externalId);
        }
		 
		 
	        throw new PlatformDataIntegrityException("error.msg.settlement.create.unknown.data.integrity.issue",
	                "Unknown data integrity issue with resource Settlement: " + realCause.getMessage());
	   
	}
	
	@Transactional
	@Override
	public CommandProcessingResult createGameEvent(JsonCommand command,
			Long entityId) {
		
		
		
		InteractiveHeader header = null; 
		/*final Long eventId=entityId;*/
		
		 
		
		
		try{
			fromApiJsonDeserializer.validateForCreateGameEvent(command.json());
			
			header = InteractiveHeader.fromJson(command);
			
			
			
			
			final JsonArray interactiveDataArray = command.arrayOfParameterNamed("activeData").getAsJsonArray();
		    for(int i=0; i<interactiveDataArray.size();i++){
		    	String currentData = interactiveDataArray.get(i).toString();
		    	final JsonElement element = fromApiJsonHelper.parse(currentData);
		    //	final Long eventId = fromApiJsonHelper.extractLongNamed("eventId", element);
			     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
			     final String contentName = fromApiJsonHelper.extractStringNamed("contentName", element);
			     final Long contentProvider = fromApiJsonHelper.extractLongNamed("contentProvider", element);
			     final Long channelName = fromApiJsonHelper.extractLongNamed("channelName", element);
			     final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
			     final Long serviceName = fromApiJsonHelper.extractLongNamed("serviceName", element);
			     final BigDecimal endUserPrice = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("endUserPrice", element);
			     final BigDecimal grossRevenue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("grossRevenue", element);
			     final Long downloads = fromApiJsonHelper.extractLongNamed("downloads", element);
			     //final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
			     
			     InteractiveDetails interactiveDetailData= InteractiveDetails.fromJson(playSource,contentName,contentProvider,channelName,serviceName,endUserPrice,grossRevenue,downloads,mediaCategory);
			     header.add(interactiveDetailData);
		    }
			
		    interactiveHeaderJpaRepository.save(header);
			
		}catch(DataIntegrityViolationException e){
			handleCodeDataIntegrityIssues(command, e);
			return CommandProcessingResult.empty();
		}
		
		
		return new CommandProcessingResultBuilder().withEntityId(header.getId()).build();
	}
	
	
	 	
	 	@Transactional
		@Override
		public CommandProcessingResult createRevenue(JsonCommand command) {
				context.authenticatedUser();
				fromApiJsonDeserializer.validateForCreateRevenue(command.json());
				RevenueMaster revenueMaster = RevenueMaster.fromJson(command);
				
				final JsonArray revenueparamArray=command.arrayOfParameterNamed("percentageParams").getAsJsonArray();
				
				if(revenueparamArray!=null && revenueparamArray.size()>0){
				     for (JsonElement jsonelement : revenueparamArray) {	
				    	     final Long startValue = fromApiJsonHelper.extractLongNamed("startValue", jsonelement);
				    	     final Long endValue = fromApiJsonHelper.extractLongNamed("endValue", jsonelement);
				    	     final BigDecimal percentage = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("percentage", jsonelement);
				    	     RevenueParam revenueParam=new RevenueParam(startValue,endValue,percentage);
				    	     revenueMaster.addRevenueParamValues(revenueParam);				 
				     }
				 }else{
					 RevenueParam revenueParam=RevenueParam.fromJson(command);
					 revenueMaster.addRevenueParamValues(revenueParam);
				 }
				revenueMasterJpaRepository.save(revenueMaster);
			return new CommandProcessingResult(revenueMaster.getId());
		}
	 	
	 	 @Transactional
			@Override
			public CommandProcessingResult updateRevenue(JsonCommand command) {
				
				context.authenticatedUser();
				fromApiJsonDeserializer.validateForCreateRevenue(command.json());
				
				RevenueMaster revenueOld = revenueMasterJpaRepository.findOne(command.entityId());
				revenueOld.getDetails().clear();
				
				revenueOld.update(command);
				
				/*revenueOld.setBusinessLine(revenueNew.getBusinessLine());
				revenueOld.setMediaCategory(revenueNew.getMediaCategory());
				revenueOld.setRevenueShareType(revenueNew.getRevenueShareType());
				revenueOld.setClientId(revenueNew.getClientId());*/
					
				
				final JsonArray revenueparamArray=command.arrayOfParameterNamed("percentageParams").getAsJsonArray();
				
				if(revenueparamArray!=null && revenueparamArray.size()>0){
				     for (JsonElement jsonelement : revenueparamArray) {	
				    	     final Long startValue = fromApiJsonHelper.extractLongNamed("startValue", jsonelement);
				    	     final Long endValue = fromApiJsonHelper.extractLongNamed("endValue", jsonelement);
				    	     final BigDecimal percentage = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("percentage", jsonelement);
				    	     RevenueParam revenueParam=new RevenueParam(startValue,endValue,percentage);
				    	     revenueOld.addRevenueParamValues(revenueParam);				 
				     }
				 }else{
					 RevenueParam revenueParam=RevenueParam.fromJson(command);
					 revenueOld.addRevenueParamValues(revenueParam);
				 }
				
				
		      
		        this.revenueMasterJpaRepository.saveAndFlush(revenueOld);
		      
		        return new CommandProcessingResultBuilder() //
		                .withCommandId(command.commandId()) //
		                .withEntityId(command.entityId()) //
		                .with(null) //
		                .build();

			}

 
	 	 @Transactional
			@Override
			public CommandProcessingResult updatePAmediaCatregory(JsonCommand command) {
				
				context.authenticatedUser();
				PartnerAgreement master=null;
				PartnerAgreementDetail detail= null;
				
				try{
					this.fromApiJsonDeserializer.validateForCreatePartnerAgreement(command.json());
				
					final JsonArray partnerAgreementDatasArray = command.arrayOfParameterNamed("partnerAgreementData").getAsJsonArray();
					List<Long> id = new ArrayList<Long>();
					for(int i=0; i<partnerAgreementDatasArray.size();i++){
						String currentData = partnerAgreementDatasArray.get(i).toString();
						final JsonElement element = fromApiJsonHelper.parse(currentData);
						 final Long ids = fromApiJsonHelper.extractLongNamed("id", element);
						//detail = this.partnerAgreementDetailRepository.findOne(id);
						
						 
						 if(ids== null){
							 final Long partnerAccountId = fromApiJsonHelper.extractLongNamed("partnerAccountId", element);

								Long paId=mediaSettlementReadPlatformService.checkPartnerAgreementId(partnerAccountId);

					        	PartnerAgreement detailMasterTable=this.partnerAgreementRepository.findOne(paId);
						        	
									     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
									     final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
									     BigDecimal royaltyShare = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("royaltyShare", element);
							    		 final Long royaltySequence = fromApiJsonHelper.extractLongNamed("royaltySequence", element);
									     final Long status = fromApiJsonHelper.extractLongNamed("status", element);
									    
									     detail=PartnerAgreementDetail.createNew(playSource,royaltyShare,royaltySequence, mediaCategory,status,partnerAccountId);
									     detailMasterTable.add(detail);
									     this.partnerAgreementRepository.saveAndFlush(detailMasterTable); 	        	 
									     id.add(detail.getId());
									     
								    }else{
								    	 id.add(ids);
								    }
				        }
						 
						
					
					for(int i=0; i<partnerAgreementDatasArray.size();i++){
						
						for (JsonElement jsonelement : partnerAgreementDatasArray) {
						
							if(id.get(i) == null){
								
							}else{
						detail = this.partnerAgreementDetailRepository.findOne(id.get(i));
						Long elementId =fromApiJsonHelper.extractLongNamed("id", jsonelement);
						
						if(detail.getId().equals(id.get(i) )  && detail.getId().equals(elementId)){
						
						 Long playSource =fromApiJsonHelper.extractLongNamed("playSource", jsonelement);
			    		 Long royaltySequence =fromApiJsonHelper.extractLongNamed("royaltySequence", jsonelement);
			    		 Long mediaCategory =fromApiJsonHelper.extractLongNamed("mediaCategory", jsonelement);
//			    		 Long partnerType =fromApiJsonHelper.extractLongNamed("partnerType", jsonelement);
			    		 Long status =fromApiJsonHelper.extractLongNamed("status", jsonelement);
			    		 BigDecimal royaltyShare = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("royaltyShare", jsonelement);
			    		 
				    		 if(!detail.getPlaySource().equals(playSource) ){
				    			 detail.setPlaySource(playSource);				    		
				    		 }
				    		 if(!detail.getRoyaltySequence().equals(royaltySequence)){
				    			 detail.setRoyaltySequence(royaltySequence);
				    		 }
				    		 if(!detail.getMediaCategory().equals(mediaCategory)){
				    			 detail.setMediaCategory(mediaCategory);
				    		 }
				    		 /*if(!detail.getPartnerType().equals(partnerType)){
				    			 detail.setPartnerType(partnerType);
				    		 }*/
				    		 if(!detail.getStatus().equals(status)){
				    			 detail.setStatus(status);
				    		 }
				    		 if(!detail.getRoyaltyShare().equals(royaltyShare)){
				    			 detail.setRoyaltyShare(royaltyShare);
				    		 }
						
				    		 this.partnerAgreementDetailRepository.saveAndFlush(detail); 
						}
						}
						}	
					}
				

					
			 	}catch (DataIntegrityViolationException dve) {
			 		handleCodeDataIntegrityIssues(command, dve);
					throw new PlatformDataIntegrityException(dve.getLocalizedMessage(), dve.getRootCause().getCause().getMessage(), "");
				}
				return new CommandProcessingResultBuilder() 
                .withCommandId(command.commandId()) 
                .withEntityId(command.entityId()) 
                .build();
				 
	 	 }		

	 	 @Transactional
	 	 @Override
	 	public CommandProcessingResult updateInteractiveData(Long entityId,
	 			JsonCommand command) {
	 		 
	 		InteractiveHeader headerOld = null, headerNew = null;
	 		
	 		try{
	 			fromApiJsonDeserializer.validateForCreateGameEvent(command.json());
	 			headerOld = interactiveHeaderJpaRepository.findOne(entityId);
	 			
	 			headerNew = InteractiveHeader.fromJson(command);
	 			
	 			//clientId,externalId,activityMonth,businessLine,mediaCategory,chargeCode,dataUploadedDate
	 			
	 			headerOld.getInteractiveDetailData().clear();
	 			
	 			headerOld.setClientId(headerNew.getClientId());
	 			headerOld.setExternalId(headerNew.getExternalId());
	 			headerOld.setBusinessLine(headerNew.getBusinessLine());
	 			headerOld.setActivityMonth(headerNew.getActivityMonth());
	 			headerOld.setChargeCode(headerNew.getChargeCode());
	 			headerOld.setDataUploadedDate(headerNew.getDataUploadedDate());
	 			
	 			
	 			
	 			final JsonArray interactiveDataArray = command.arrayOfParameterNamed("activeData").getAsJsonArray();
			    for(int i=0; i<interactiveDataArray.size();i++){
			    	String currentData = interactiveDataArray.get(i).toString();
			    	final JsonElement element = fromApiJsonHelper.parse(currentData);
			    //	final Long eventId = fromApiJsonHelper.extractLongNamed("eventId", element);
				     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", element);
				     final String contentName = fromApiJsonHelper.extractStringNamed("contentName", element);
				     final Long contentProvider = fromApiJsonHelper.extractLongNamed("contentProvider", element);
				     final Long channelName = fromApiJsonHelper.extractLongNamed("channelName", element);
				     final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
				     final Long serviceName = fromApiJsonHelper.extractLongNamed("serviceName", element);
				     final BigDecimal endUserPrice = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("endUserPrice", element);
				     final BigDecimal grossRevenue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("grossRevenue", element);
				     final Long downloads = fromApiJsonHelper.extractLongNamed("downloads", element);
				     
				     //final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
				     
				     InteractiveDetails interactiveDetailData= InteractiveDetails.fromJson(playSource,contentName,contentProvider,channelName,serviceName,endUserPrice,grossRevenue,downloads,mediaCategory);
				     headerOld.add(interactiveDetailData);
			    }
				
			    interactiveHeaderJpaRepository.save(headerOld);
	 			
	 			
	 			
	 		}catch(DataIntegrityViolationException dev){
	 			handleDataIntegrityIssue(dev);
	 			return CommandProcessingResult.empty();
	 		}
	 		
	 		
	 		return new CommandProcessingResultBuilder().withEntityId(headerOld.getId()).build();
	 	}
		 	@Transactional
			@Override
			public CommandProcessingResult createCurrencyRate(JsonCommand command) {
			
		 		try {
					context.authenticatedUser();
					   this.fromApiJsonDeserializer.validateForCreateCurrency(command.json());
				       final CurrencyRate currencyRate=CurrencyRate.fromjson(command);
					   this.currencyRateJpaRepository.save(currencyRate);
					   return new  CommandProcessingResultBuilder().withEntityId(currencyRate.getId()).build();
			
			}catch(DataIntegrityViolationException dve){
				throw new PlatformDataIntegrityException("unknown.data.integrity.issue","unkown.currency.data.issue","unkown.currency.data.issue");
			}
	}
		 	@Transactional
			@Override
			public CommandProcessingResult updateCurrencyRateDetail(Long id,
					JsonCommand command) {
						    context.authenticatedUser();
						    CurrencyRate currencyData=null;
						    CurrencyRate currencyRateData=null;
						    
						    try
						    {
						    this.fromApiJsonDeserializer.validateForCreateCurrency(command.json());
						    currencyData=this.currencyRateJpaRepository.findOne(id);
						    currencyRateData=CurrencyRate.fromjson(command);
						    currencyData.setSourceCurrency(currencyRateData.getSourceCurrency());
						    currencyData.setTargetCurrency(currencyRateData.getTargetCurrency());
						    currencyData.setExchangeRate(currencyRateData.getExchangeRate());
						    currencyData.setStartDate(currencyRateData.getStartDate());
						    currencyData.setEndDate(currencyRateData.getEndDate());
						    this.currencyRateJpaRepository.save(currencyData);
				             
			         }
						    catch(DataIntegrityViolationException e){
								//logger.error(e.getMessage(), e);
								return new CommandProcessingResult(Long.valueOf(-1L));
							}
						    
			         return new CommandProcessingResultBuilder() .withCommandId(command.commandId()).withEntityId(id).build(); 
				}

		 	@Transactional
			@Override
			public CommandProcessingResult deleteCurrencyRateDetail(Long entityId,
					JsonCommand command) {
					    	 this.context.authenticatedUser();
					    	 CurrencyRate currencyRate=this.currencyRateJpaRepository.findOne(entityId);
					    	 
					    	 if(currencyRate==null){
					    		 throw new CurrencyRateNotFoundException(entityId.toString());
					    	 }
					    	 
					    	 currencyRate.delete();
					    	 this.currencyRateJpaRepository.save(currencyRate);
					    	 return new CommandProcessingResult(entityId);
					     
						}

		 		
		 	@Transactional
			@Override
			public CommandProcessingResult deleteMediaCategoryDetails(Long entityId) {
					    	 this.context.authenticatedUser();
					    	 mediaSettlementReadPlatformService.retrieveDeleteMediaCategoryData(entityId);
					    	 return new CommandProcessingResult(entityId);
					     
						}	
	
		 	
		 	@Transactional
		 	@Override
		 	public CommandProcessingResult createRawData(JsonCommand command) {
		 		
		 		context.authenticatedUser();
		 		OperatorStage rawData = null;
		 		
		 		try{
		 			
		 			fromApiJsonDeserializer.validateForRawData(command.json());
		 			rawData = OperatorStage.fromJson(command);
		 			rawInteractiveHeaderDetailJpaRepository.saveAndFlush(rawData);
		 			
		 			//executeRawData();
		 		}catch(DataIntegrityViolationException dve){
		 			handleCodeDataIntegrityIssues(command, dve);
		 			return new CommandProcessingResultBuilder().withEntityId(-1L).build();
		 		}		 		
		 		return new CommandProcessingResultBuilder().withEntityId(rawData.getId()).build();
		 	}
		 	
		 	public void executeRawData(){
		 		
		 		List<OperatorStageDetailData> rawData = rawInteractiveHeaderDetailReadPlatformService.retriveAllData();
		 		List<InteractiveHeader> headerData = new InteractiveHeaderHandler(rawData).getData();
		 		interactiveHeaderJpaRepository.save(headerData);
		 		//System.out.println(rawData);
		 		
		 	}
		 	
		 	@Override
		 	public CommandProcessingResult createPlatformStageData(JsonCommand command) {
		 		PlatformStage entity = null;
		 		context.authenticatedUser();
		 		try{
		 			
		 			entity = PlatformStage.fromJson(command);
		 			platformStageJpaRepository.save(entity);
		 		}catch(DataIntegrityViolationException dve){
		 			handleCodeDataIntegrityIssues(command, dve);
		 		}
		 		return new CommandProcessingResultBuilder().withEntityId(entity.getId()).build();
		 	}

}
