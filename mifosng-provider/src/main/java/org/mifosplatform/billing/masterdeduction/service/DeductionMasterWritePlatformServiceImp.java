package org.mifosplatform.billing.masterdeduction.service;


import org.mifosplatform.billing.masterdeduction.domain.DeductionCodes;
import org.mifosplatform.billing.masterdeduction.domain.DeductionJpaRepository;
import org.mifosplatform.billing.masterdeduction.exception.DeductionMasterNotFoundException;
import org.mifosplatform.billing.masterdeduction.serialization.DeductionMasterCommandFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DeductionMasterWritePlatformServiceImp implements DeductionMasterWritePlatformService{

	private final  DeductionJpaRepository deductionJpaRepository;
	private final  PlatformSecurityContext context;
	private final  DeductionMasterCommandFromApiJsonDeserializer apiJsonDeserializer;
	private final  FromJsonHelper fromApiJsonHelper;
	
	private final static Logger logger =LoggerFactory.getLogger(DeductionMasterWritePlatformServiceImp.class);
	
	@Autowired
	public DeductionMasterWritePlatformServiceImp(final DeductionJpaRepository deductionJpaRepository,
			final PlatformSecurityContext context,
			final DeductionMasterCommandFromApiJsonDeserializer apiJsonDeserializer,
			final FromJsonHelper fromApiJsonHelper) {
		this.context = context;
		this.deductionJpaRepository = deductionJpaRepository;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.fromApiJsonHelper = fromApiJsonHelper;
			
	}
	
	@Transactional
	@Override
	public CommandProcessingResult createDeductionCodes(JsonCommand command) {
		
		try {
			context.authenticatedUser();
			   this.apiJsonDeserializer.validateForCreateMasterDeduction(command.json());
			   final DeductionCodes deductionMasterData= DeductionCodes.fromJson(command);
			   this.deductionJpaRepository.save(deductionMasterData);
		
			    
		    return new CommandProcessingResult(deductionMasterData.getId());
			  
		} catch (DataIntegrityViolationException dve) {
			 handleCodeDataIntegrityIssues(command, dve);
			return  CommandProcessingResult.empty();
		}
	}
	

	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		
		
	}
	@Transactional
	@Override
	public CommandProcessingResult updateDeductionCode(Long id,
			JsonCommand command) {
				    context.authenticatedUser();
				    DeductionCodes deductionData=null;
				    DeductionCodes deductionDatas=null;
				    try
				    {
		            this.apiJsonDeserializer.validateForCreateMasterDeduction(command.json());
		             deductionData = deductionJpaRepository.findOne(id);
		             deductionDatas = DeductionCodes.fromJson(command);
		             deductionData.setDeductionCode(deductionDatas.getDeductionCode());
		             deductionData.setDeductionName(deductionDatas.getDeductionName());
		             deductionData.setDeductionType(deductionDatas.getDeductionType());
		             deductionData.setDeductionCircle(deductionDatas.getDeductionCircle());
		             deductionData.setLevelApplicable(deductionDatas.getLevelApplicable());
		             deductionData.setBusiness(deductionDatas.getBusiness());
	       
	             this.deductionJpaRepository.save(deductionData);
	         }
				    catch(DataIntegrityViolationException e){
						//logger.error(e.getMessage(), e);
						return new CommandProcessingResult(Long.valueOf(-1L));
					}
				    
	         return new CommandProcessingResultBuilder() .withCommandId(command.commandId()).withEntityId(id).build(); 
		}
		
	/*private DeductionCodes retrieveCodeBy(final Long id) {
        final DeductionCodes deductionData = this.deductionJpaRepository.findOne(id);
        if (deductionData == null) { throw new CodeNotFoundException(id.toString()); }
        return deductionData;
    }
*/
	 @Override
		public CommandProcessingResult deleteDeductionMaster(Long entityId) {

			 
		    	 this.context.authenticatedUser();
		    	 DeductionCodes deductionMaster=this.deductionJpaRepository.findOne(entityId);
		    	 
		    	 if(deductionMaster==null){
		    		 throw new DeductionMasterNotFoundException(entityId.toString());
		    	 }
		    	 
		    	 deductionMaster.delete();
		    	 this.deductionJpaRepository.save(deductionMaster);
		    	 return new CommandProcessingResult(entityId);
		     
			}
	
	
}
