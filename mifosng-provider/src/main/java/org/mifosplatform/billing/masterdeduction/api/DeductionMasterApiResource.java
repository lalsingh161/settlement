package org.mifosplatform.billing.masterdeduction.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.masterdeduction.data.DeductionMasterData;
import org.mifosplatform.billing.masterdeduction.service.DeductionMasterReadPlatformService;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.mcodevalues.service.MCodeReadPlatformService;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;


@Path("/deductionmaster")
@Component
@Scope("singleton")

    public class DeductionMasterApiResource {
	
		private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("deductionCode", "deductionName", "deductionType","business","circle","customerType","levelApplicable"));
	    private final String resourceNameForPermissions = "DeductionMaster";
	    
	    private final PlatformSecurityContext context;
	    private final MCodeReadPlatformService mCodeReadPlatformService;
	    private final DefaultToApiJsonSerializer<DeductionMasterData> toApiJsonSerializer;
	    private final ApiRequestParameterHelper apiRequestParameterHelper;
	    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	    private final DeductionMasterReadPlatformService deductionReadPlatformService;
	    
	    
	    @Autowired
	    public DeductionMasterApiResource(final PlatformSecurityContext context, 
	    		final MCodeReadPlatformService mCodeReadPlatformService, 
	    		final DefaultToApiJsonSerializer<org.mifosplatform.billing.masterdeduction.data.DeductionMasterData> toApiJsonSerializer, 
	    		final ApiRequestParameterHelper apiRequestParameterHelper,
	    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
	    		final DeductionMasterReadPlatformService deductionReadPlatformService) {
			this.context = context;
			this.mCodeReadPlatformService = mCodeReadPlatformService;
			this.toApiJsonSerializer = toApiJsonSerializer;
			this.apiRequestParameterHelper = apiRequestParameterHelper;
			this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
			this.deductionReadPlatformService = deductionReadPlatformService;
	
	    }
	 
    @GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
    public String getMastersTemplate(@Context final UriInfo uriInfo){
    	 Collection<MCodeData> deductionTypeData = mCodeReadPlatformService.getCodeValue("type");
    	 Collection<MCodeData>  customerTypes = mCodeReadPlatformService.getCodeValue("Client Category");
    	 Collection<MCodeData> levelApplicables = mCodeReadPlatformService.getCodeValue("Level Applicable");
    	 Collection<MCodeData> businessCategory = mCodeReadPlatformService.getCodeValue("Business");
    	 Collection<StateDetails> stateDatas=deductionReadPlatformService.retrieveAllStateDetails();
    	 DeductionMasterData deductionData = new DeductionMasterData(deductionTypeData,customerTypes,levelApplicables,businessCategory,stateDatas);
    	return toApiJsonSerializer.serialize(deductionData);
    }
    
    @POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String createNewDeduction(final String apiRequestBodyAsJson) {
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
        final CommandWrapper commandRequest = new CommandWrapperBuilder().createDeductionCodes().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
	}
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllDeductionDetails(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	List<DeductionMasterData> masterDatas = deductionReadPlatformService.retrieveAllDeductionDetails();
    	DeductionMasterData deductionData = new DeductionMasterData(masterDatas);
    	return toApiJsonSerializer.serialize(deductionData);
    }
  
    @GET
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getDeductionDetail(@Context final UriInfo uriInfo, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	DeductionMasterData masterData=null;
    	try{
    		
    		masterData = deductionReadPlatformService.retrieveDeductionDetail(id);
    		masterData.setDeductionTypeData(mCodeReadPlatformService.getCodeValue("type"));
    		masterData.setCustomerTypes(mCodeReadPlatformService.getCodeValue("Client Category"));
    		masterData.setLevelApplicables(mCodeReadPlatformService.getCodeValue("Level Applicable"));
    		masterData.setBusinessCategory(mCodeReadPlatformService.getCodeValue("Business"));
    		masterData.setStateData(deductionReadPlatformService.retrieveAllStateDetails());
    		
    	
    	}catch(EmptyResultDataAccessException accessException){
    		throw new PlatformDataIntegrityException("validation.error.msg.deduction.duplicate.serialNumber", "validation.error.msg.deduction.duplicate.serialNumber", "validation.error.msg.deduction.duplicate.serialNumber");
    	}
    	return toApiJsonSerializer.serialize(masterData);
    }
    
    
    
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateDeductionMaster(final String jsonRequestMessageBody, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().updateDeductionCodes(id).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
   
    
    
    @DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deleteDeductionMaster(@PathParam("id") final Long id) {
	final  CommandWrapper commandRequest = new CommandWrapperBuilder().deleteDeductionMaster(id).build();
    final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
    return this.toApiJsonSerializer.serialize(result);

 }
    
    
}
