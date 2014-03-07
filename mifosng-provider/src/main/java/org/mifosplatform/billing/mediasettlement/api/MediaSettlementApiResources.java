package org.mifosplatform.billing.mediasettlement.api;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.billing.chargecode.service.ChargeCodeReadPlatformService;
import org.mifosplatform.billing.item.data.ChargesData;
import org.mifosplatform.billing.item.service.ItemReadPlatformService;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.mcodevalues.service.MCodeReadPlatformService;
import org.mifosplatform.billing.media.data.MediaAssetData;
import org.mifosplatform.billing.media.service.MediaAssetReadPlatformService;
import org.mifosplatform.billing.mediasettlement.data.DisbursementsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveDetailsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveHeaderData;
import org.mifosplatform.billing.mediasettlement.data.MediaCategoryData;
import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.billing.mediasettlement.data.OperatorDeductionData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAccountData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAgreementData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameDetailsData;
import org.mifosplatform.billing.mediasettlement.data.RevenueSettlementData;
import org.mifosplatform.billing.mediasettlement.data.RevenueSettlementSequenceData;
import org.mifosplatform.billing.mediasettlement.data.RevenueShareData;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreement;
import org.mifosplatform.billing.mediasettlement.domain.PartnerAgreementRepository;
import org.mifosplatform.billing.mediasettlement.service.MediaSettlementReadPlatformService;
import org.mifosplatform.billing.mediasettlement.service.MediaSettlementWritePlatformService;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiConstants;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.service.FileUtils;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.monetary.data.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;


@Path("/mediasettlements")
@Component
@Scope("singleton")
public class MediaSettlementApiResources {
	
	
	private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("partnerType", "partnerTypeId", "mediaCategory","mediaCategoryId"));
    private final String resourceNameForPermissions = "MEDIASETTLEMENT";
    private final String resourceNameForPermissionsForGamePartner  = "PARTNERGAME"; 
	
	
    private final PlatformSecurityContext context;
    private final MCodeReadPlatformService mCodeReadPlatformService;
    private final DefaultToApiJsonSerializer<PartnerAccountData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final MediaSettlementReadPlatformService mediaSettlementReadPlatformService;
    private final PartnerAgreementRepository partnerAgreementRepository;
    private final MediaSettlementWritePlatformService mediaSettlementWritePlatformService;
    private final ChargeCodeReadPlatformService chargeCodeReadPlatformService;
    private final MediaAssetReadPlatformService mediaAssetReadPlatformService;
    private final ItemReadPlatformService itemReadPlatformService;
    private final MediaAssetReadPlatformService assetReadPlatformService;
    
    
    @Autowired
    public MediaSettlementApiResources(final PlatformSecurityContext context, 
    		final MCodeReadPlatformService mCodeReadPlatformService, 
    		final DefaultToApiJsonSerializer<PartnerAccountData> toApiJsonSerializer, 
    		final ApiRequestParameterHelper apiRequestParameterHelper,
    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
    		final MediaSettlementReadPlatformService mediaSettlementReadPlatformService,
    		final PartnerAgreementRepository partnerAgreementRepository,
    		final MediaSettlementWritePlatformService mediaSettlementWritePlatformService,
    		final ChargeCodeReadPlatformService chargeCodeReadPlatformService ,
    		final MediaAssetReadPlatformService mediaAssetReadPlatformService,
    		final ItemReadPlatformService itemReadPlatformService,
    		final MediaAssetReadPlatformService assetReadPlatformService) {
		this.context = context;
		this.mCodeReadPlatformService = mCodeReadPlatformService;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.apiRequestParameterHelper = apiRequestParameterHelper;
		this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		this.mediaSettlementReadPlatformService = mediaSettlementReadPlatformService;
		this.partnerAgreementRepository = partnerAgreementRepository;
		this.mediaSettlementWritePlatformService = mediaSettlementWritePlatformService;
		this.chargeCodeReadPlatformService = chargeCodeReadPlatformService;
		this.mediaAssetReadPlatformService = mediaAssetReadPlatformService;
		this.itemReadPlatformService = itemReadPlatformService;
		this.assetReadPlatformService = assetReadPlatformService;
	
	}
    
    /*
     * Partner Account Related Code
     * */
    
    @GET
    @Path("/partnertype/template")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getPartnerAccountTemplate(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	/*Collection<MCodeData> partnerType = mCodeReadPlatformService.getCodeValue("Partner Type");
    	Collection<MCodeData> mediaCategory = mCodeReadPlatformService.getCodeValue("Media Category");*/
    	Collection<CurrencyData> currencyCodes = this.mediaSettlementReadPlatformService.retrieveCurrency();
    	
    	/*List<PartnerAccountData> countryData = this.mediaSettlementReadPlatformService.retrieveCountryDetails();*/
    	PartnerAccountData accountData = new PartnerAccountData(null,null,currencyCodes);
    	return toApiJsonSerializer.serialize(accountData);
    }
    
    @GET
    @Path("/partnertype/partnerid")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getPartnerAccountTemplate1(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	Collection<PartnerAccountData> channelPartnerData = this.mediaSettlementReadPlatformService.retriveAllChannelPartner(); 
    	PartnerAccountData channelPartnerDatas = new PartnerAccountData(channelPartnerData);
    	return toApiJsonSerializer.serialize(channelPartnerDatas/*accountData*/);
    }
    
    
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getPartnerAccountDetails(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	List<PartnerAccountData> partnerAccountData = mediaSettlementReadPlatformService.retrieveAllAccountPartnerDetails();
    	
    	PartnerAccountData pd = new PartnerAccountData(partnerAccountData);
    	return toApiJsonSerializer.serialize(pd);
    }
    
    @GET
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getPartnerAccountDetail(@Context final UriInfo uriInfo, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	
    	PartnerAccountData partnerAccountData = null;
    	/*List<PartnerAccountData> channelsPartner = null;*/
    	try{
    		partnerAccountData = mediaSettlementReadPlatformService.retrieveAllAccountPartnerDetail(id);
    		/*List<PartnerAccountData> countryData = this.mediaSettlementReadPlatformService.retrieveCountryDetails();*/
    		/*partnerAccountData.setMediaCategory(mCodeReadPlatformService.getCodeValue("Media Category"));*/
    		/*partnerAccountData.setPartnerType(mCodeReadPlatformService.getCodeValue("Partner Type"));*/
    		/*partnerAccountData.setChannelPartnerData(mediaSettlementReadPlatformService.retriveAllChannelPartner());*/
    		/*partnerAccountData.setCountry(countryData);*/
    		partnerAccountData.setCurrencyCodes(mediaSettlementReadPlatformService.retrieveCurrency());
    		/*channelsPartner = mediaSettlementReadPlatformService.getChannelPartnerDetails(id);*/
    		/*partnerAccountData.setChannelPartner(channelsPartner);*/
    		    		
    		/*for(PartnerAccountData pd: channelsPartner){
    			partnerAccountData.getChannelPartnerData().add(pd);
    		}*/
    		
    	}catch(EmptyResultDataAccessException accessException){
    		throw new PlatformDataIntegrityException("validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber","validation.error.msg.inventory.item.duplicate.serialNumber");
    	}
    	return toApiJsonSerializer.serialize(partnerAccountData);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createAccountPartner(final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createPartnerAccount().withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateAccountPartner(final String jsonRequestMessageBody, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().updatePartnerAccount(id).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    /*End of Partner Account Code
     * 
     * 
     * Start of partner Agreee
     * */
    
 
    
    @GET
    @Path("/partnergame/template")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retrievePartnerSupportedGame(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<MCodeData> gamePlaySource = mCodeReadPlatformService.getCodeValue("Play Source");
    	Collection<PartnerAccountData> partnerNames = mediaSettlementReadPlatformService.retrievePartnerNames();
    	Collection<MCodeData> playSource = mCodeReadPlatformService.getCodeValue("Play Source");
    	/*Collection<PartnerGameData> contentProviderGames = mediaSettlementReadPlatformService.retriveAllContentProviderGames();*/
    	PartnerGameData object = new PartnerGameData(gamePlaySource,partnerNames,playSource,null);
    	return toApiJsonSerializer.serialize(object);
    }
    
    
    @GET
    @Path("/partnergame")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retrieveCategoryAndPartnerType(@Context final UriInfo uriInfo, @QueryParam("partnerName") final Long partnerId){
    	try{
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	MediaCategoryData mediaCategory = mediaSettlementReadPlatformService.retrieveMediaCategory(partnerId);
    	MediaCategoryData partnerType = mediaSettlementReadPlatformService.retrivePartnerType(partnerId);
    	Collection<PartnerGameData> contentProviderGames = mediaSettlementReadPlatformService.retriveAllContentProviderGames(partnerId);
    	MediaCategoryData mediaCategoryData = new MediaCategoryData(mediaCategory,partnerType,contentProviderGames);
    	return toApiJsonSerializer.serialize(mediaCategoryData);
    	}catch(EmptyResultDataAccessException e){
    		throw new PlatformDataIntegrityException("empty.result.set", "empty.result.set", "empty.result.set");
    	}
    }
    
    @POST
    @Path("/partnergame")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createGamePartner(final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createPartnerGame().withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    @GET
    @Path("/deductionoperator/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getOperatorDeduction(@Context final UriInfo uriInfo, final String jsonRequestMessageBody, @PathParam("clientId") final Long clientId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	Collection<OperatorDeductionData> partnerGameDetails = mediaSettlementReadPlatformService.getOperatorDeduction(clientId);
    	return toApiJsonSerializer.serialize(partnerGameDetails);
    }
    
    
    @GET
    @Path("/deductionoperator/template")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getOperatorDeductionTamplate(final String jsonRequestBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<OperatorDeductionData> deductionCodes = mediaSettlementReadPlatformService.getDeductionCodes();
    	return toApiJsonSerializer.serialize(deductionCodes);
    }
    
    @GET
    @Path("deductionoperator/edit/{codeId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getOperatorSingleDeductionCode(final String jsonRequestBody, @PathParam("codeId") final Long codeId){
    	context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
    	OperatorDeductionData operatorDeductionDatas = mediaSettlementReadPlatformService.getOperatorSingleDeductionCode(codeId);
    	Collection<OperatorDeductionData> deductionCodes = mediaSettlementReadPlatformService.getDeductionCodes();
    	OperatorDeductionData deductionData = new OperatorDeductionData(operatorDeductionDatas,deductionCodes);
    	return toApiJsonSerializer.serialize(deductionData);
    }
    
    @PUT
    @Path("deductionoperator/edit/{codeId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String updateOperatorDeductionCode(final String jsonRequestBody, @PathParam("codeId") final Long codeId){
    	context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
    	final CommandWrapper commandRequest = new CommandWrapperBuilder().updateOperatorDeductionCode(codeId).withJson(jsonRequestBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    @POST
    @Path("/deductionoperator/template")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createOperatorDeduction(final String jsonRequestBody){
    	
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	CommandWrapper commandRequest = new CommandWrapperBuilder().createOperatorDeduction().withJson(jsonRequestBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    
    @GET
    @Path("/partnergame/partnergamedetails")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String retriveAllGamePartners(@Context final UriInfo uriInfo, final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	Collection<PartnerGameDetailsData> partnerGameDetails = mediaSettlementReadPlatformService.retriveAllPartnerGameDetails();
    	return toApiJsonSerializer.serialize(partnerGameDetails);
    }
    
    
    
    @GET
    @Path("/partnergame/partnergamedetails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String retriveAllGamePartnersDetails(@Context final UriInfo uriInfo, @PathParam("id") final Long id, final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	PartnerGameDetailsData partnerGameDetails = mediaSettlementReadPlatformService.retrivePartnerGameDetails(id);
    	if(partnerGameDetails == null){
    		throw new PlatformDataIntegrityException("no.game.partner.found", "no.game.partner.found", "no.game.partner.found");
    	}
    	/*Collection<MCodeData> mediaCategoryData  = mCodeReadPlatformService.getCodeValue("Media Category");*/
    	Collection<MCodeData> playSourceData = mCodeReadPlatformService.getCodeValue("Play Source");
    	Collection<PartnerGameData> contentProviderGames = mediaSettlementReadPlatformService.retriveAllContentProviderGames();
    	Collection<PartnerAccountData> partnerNamesData = mediaSettlementReadPlatformService.retrievePartnerNames(); 
    	/*Collection<MCodeData> partnerTypeData  = mCodeReadPlatformService.getCodeValue("Partner Type");*/
    	Collection<PartnerGameDetailsData> channelPartnerDetails = mediaSettlementReadPlatformService.retriveChannelGameDetails(partnerGameDetails.getId());
    	PartnerGameDetailsData data = new PartnerGameDetailsData(partnerGameDetails,channelPartnerDetails,null,playSourceData,partnerNamesData,contentProviderGames);
    	return toApiJsonSerializer.serialize(data);
    }
    
    @PUT
    @Path("/partnergame/partnergamedetails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateGamePartnersDetails(final String jsonRequestMessageBody, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	final CommandWrapper commandRequest = new CommandWrapperBuilder().updatePartnerGame(id).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    /*
     * Revenue Settlement Code
     * **/
    
    @GET
    @Path("/revenuesettlement/template")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getRevenueSettlementTemplate(@Context final UriInfo uriInfo,@QueryParam("rvData") final String revenueData){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	/*Collection<MCodeData> partnerType = mCodeReadPlatformService.getCodeValue("Partner Type");*/
    	Collection<MCodeData> mediaCategoryData = mCodeReadPlatformService.getCodeValue("Media Category");

    	if(revenueData!=null){
    		Collection<MCodeData> businessLineData = this.mCodeReadPlatformService.getCodeValue("Business");
    		Collection<MCodeData> royaltyType = this.mCodeReadPlatformService.getCodeValue("Royalty Type");
        	RevenueSettlementData  datas = new RevenueSettlementData(businessLineData,mediaCategoryData,royaltyType);
        	return toApiJsonSerializer.serialize(datas);
    	}
    	RevenueSettlementData  datas = new RevenueSettlementData(mediaCategoryData);
    	return toApiJsonSerializer.serialize(datas);
    }
    
   /* @GET
    @Path("/revenuesettlement/template/{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getRevenueSettlementTemplate(@Context final UriInfo uriInfo, @PathParam("categoryId") final Long categoryId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<RevenueSettlementSequenceData> collection = mediaSettlementReadPlatformService.retriveRevenueSettlementSequenceData(categoryId);
    	
    	Collection<MCodeData> agreementCData = mCodeReadPlatformService.getCodeValue("Agreement Category");
    	Collection<MCodeData> agreementTypeData = mCodeReadPlatformService.getCodeValue("Agreement Type");
    	Collection<MCodeData> playSourceData = mCodeReadPlatformService.getCodeValue("Play Source");
    	Collection<MCodeData> royaltyTypeData = mCodeReadPlatformService.getCodeValue("Royalty Type");
    	Collection<MCodeData> mediaCategoryData = mCodeReadPlatformService.getCodeValue("Media Category");
    	Collection<MCodeData> settlementSourceData = mCodeReadPlatformService.getCodeValue("Settlement Source");
    	
    	Collection<MCodeData> partnerTypeData = mCodeReadPlatformService.getCodeValue("Partner Type");
    	
    	RevenueSettlementSequenceData data = new RevenueSettlementSequenceData(collection,agreementCData,agreementTypeData,playSourceData,royaltyTypeData,mediaCategoryData,partnerTypeData,settlementSourceData);
    	return toApiJsonSerializer.serialize(data);
    }*/
    
    
    @GET
    @Path("/revenuesettlement/template/{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getRevenueSettlementTemplate(@Context final UriInfo uriInfo, @PathParam("categoryId") final Long categoryId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<RevenueSettlementSequenceData> collection = mediaSettlementReadPlatformService.retriveRevenueSettlementData(categoryId);
    	RevenueSettlementSequenceData defaultDataValues=mediaSettlementReadPlatformService.retriveRevenueSettlementDefaultData();
    	Collection<MCodeData> partnerTypeData = mCodeReadPlatformService.getCodeValue("Partner Type");
    	
    	RevenueSettlementSequenceData data = new RevenueSettlementSequenceData(collection,partnerTypeData,defaultDataValues);
    	return toApiJsonSerializer.serialize(data);
    }
    
    
    
    @PUT
    @Path("/settlementSequenceData")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSettlementSequenceDataDetails(final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	final CommandWrapper commandRequest = new CommandWrapperBuilder().updateSettlementSequenceData().withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    @POST
    @Path("/revenuesettlement/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createRevenueSettlementSequence(final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createRevenueSettlement().withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    /*
     * Lala singh code. Agreement
     * 
     * */
    @GET
    @Path("/partneragreement")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String retrievePartnerAgreementDetails(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	List<PartnerAgreementData> partnerAgreementDatas = mediaSettlementReadPlatformService.retrievePartnerAgreementDetails();
    	PartnerAgreementData partnerAgreement = new PartnerAgreementData(partnerAgreementDatas);
    	return toApiJsonSerializer.serialize(partnerAgreement);
    }
    
    
    @GET
	@Path("{uploadfileId}/print")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response downloadFile(@PathParam("uploadfileId") final Long id) {
    	PartnerAgreement partnerAgreement = this.partnerAgreementRepository.findOne(id);
		/*String printFileName = partnerAgreement.getAgmtLocation();*/
		String fileName=partnerAgreement.getFileName();
		File file = new File(partnerAgreement.getAgmtLocation());
		ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
//		response.header("Content-Type", "application/vnd.ms-excel");
        /*response.header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");*/
        response.header("Content-Type", "application/vnd.text");
		return response.build();
	}
       
    
    @GET
    @Path("{documentId}/attachment")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getDocument(@PathParam("entityType") final String entityType, @PathParam("entityId") final Long entityId,
            @PathParam("documentId") final Long documentId) {
    	
    	Collection<MCodeData>  	partnerTypeData = mCodeReadPlatformService.getCodeValue("Partner Type");
    	Collection<MCodeData>  	mediaCategoryData = mCodeReadPlatformService.getCodeValue("Media Category");
    	Collection<MCodeData>  	agreementTypeData = mCodeReadPlatformService.getCodeValue("Agreement Type");
    	Collection<MCodeData>  	agreementCategoryData = mCodeReadPlatformService.getCodeValue("Agreement Category");
    	Collection<MCodeData>  	royaltyTypeData = mCodeReadPlatformService.getCodeValue("Royalty Type");
    	Collection<MCodeData>  	settlementSourceData = mCodeReadPlatformService.getCodeValue("Settlement Source");
    	Collection<MCodeData>  	playSourceData = mCodeReadPlatformService.getCodeValue("Play Source");
    	
    	Collection<PartnerAccountData> partnerNames = mediaSettlementReadPlatformService.retrievePartnerNames();
    	
    	PartnerAccountData partnerAccountData = new PartnerAccountData(partnerTypeData,mediaCategoryData,agreementTypeData,agreementCategoryData,royaltyTypeData,settlementSourceData,partnerNames,playSourceData);
    	
    	MediaSettlementCommand mediaSettlementCommand = mediaSettlementReadPlatformService.retrieveDocument(documentId);
    	
    	MediaSettlementCommand mediaSettlement=new MediaSettlementCommand(mediaSettlementCommand,partnerAccountData);
        
    	return toApiJsonSerializer.serialize(mediaSettlement);
       
    }
    
    @POST
    @Path("/document")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    public String createUploadFile(@PathParam("entityType") final String entityType, @PathParam("entityId") final Long entityId,
    		@HeaderParam("Content-Length") Long fileSize, @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails, @FormDataParam("file") FormDataBodyPart bodyPart,
            @FormDataParam("partnerAccountId") Long partnerAccountId, @FormDataParam("agreementType") Long agreementType,
            @FormDataParam("agreementCategory") Long agreementCategory, @FormDataParam("royaltyType") Long royaltyType,
            @FormDataParam("settlementSource") Long settlementSource,@FormDataParam("startDate") Date startDate, @FormDataParam("endDate") Date endDate,
            @FormDataParam("playSource") Long playSource,@FormDataParam("royaltyShare") BigDecimal royaltyShare,@FormDataParam("royaltySequence") Long royaltySequence,
            @FormDataParam("mgAmount") BigDecimal mgAmount,@FormDataParam("mediaCategory") Long mediaCategory,@FormDataParam("partnerType") Long partnerType) {
    	
    	FileUtils.validateFileSizeWithinPermissibleRange(fileSize, null, ApiConstants.MAX_FILE_UPLOAD_SIZE_IN_MB);
        /*inputStreamObject=inputStream;*/
                
        String fileUploadLocation = FileUtils.generateFileParentDirectory(entityType, entityId);
        String fileName=fileDetails.getFileName();
        
        
        if (!new File(fileUploadLocation).isDirectory()) {
        	new File(fileUploadLocation).mkdirs();
        }

        
        MediaSettlementCommand mediaSettlementCommand=new MediaSettlementCommand(null,null,partnerAccountId,agreementType,
        		agreementCategory,royaltyType,startDate,endDate,fileUploadLocation,inputStream,fileName,settlementSource,playSource,royaltyShare,royaltySequence,mgAmount,mediaCategory,partnerType);
        CommandProcessingResult result = this.mediaSettlementWritePlatformService.createAgreement(mediaSettlementCommand);
        return toApiJsonSerializer.serialize(result);
    }
    
    
    @POST
    @Path("{documentId}/document")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    public String updateDocument(@PathParam("entityType") final String entityType, @PathParam("entityId") final Long entityId,
            @PathParam("documentId") final Long documentId, @HeaderParam("Content-Length") Long fileSize, @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails, @FormDataParam("file") FormDataBodyPart bodyPart,
            @FormDataParam("partnerAccountId") Long partnerAccountId, @FormDataParam("agreementType") Long agreementType,
            @FormDataParam("agreementCategory") Long agreementCategory, @FormDataParam("royaltyType") Long royaltyType,
            @FormDataParam("settlementSource") Long settlementSource,@FormDataParam("startDate") Date startDate, @FormDataParam("endDate") Date endDate,
            @FormDataParam("playSource") Long playSource,@FormDataParam("royaltyShare") BigDecimal royaltyShare,@FormDataParam("royaltySequence") Long royaltySequence,
            @FormDataParam("mgAmount") BigDecimal mgAmount,@FormDataParam("mediaCategory") Long mediaCategory,@FormDataParam("partnerType") Long partnerType) {

    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
        FileUtils.validateFileSizeWithinPermissibleRange(fileSize, null, ApiConstants.MAX_FILE_UPLOAD_SIZE_IN_MB);

        /*String fileUploadLocation = FileUtils.generateFileParentDirectory(entityType, entityId);*/
        String fileName=fileDetails.getFileName();
        
        final Set<String> modifiedParams = new HashSet<String>();
        modifiedParams.add("partnerAccountId");
        modifiedParams.add("agreementType");
        modifiedParams.add("agreementCategory");
        modifiedParams.add("royaltyType");
        modifiedParams.add("startDate");
        modifiedParams.add("endDate");
        modifiedParams.add("settlementSource");
        modifiedParams.add("playSource");
        modifiedParams.add("royaltyShare");
        modifiedParams.add("royaltySequence");
        modifiedParams.add("mgAmount");
        modifiedParams.add("mediaCategory");
        modifiedParams.add("partnerType");
        
        
        /***
         * Populate Document command based on whether a file has also been
         * passed in as a part of the update
         ***/
        MediaSettlementCommand documentCommand = null;
        if (inputStream != null && fileDetails.getFileName() != null) {
            
        	modifiedParams.add("fileName");
            modifiedParams.add("agmtLocation");
          
            documentCommand=new MediaSettlementCommand(modifiedParams,documentId,partnerAccountId,agreementType,
            		agreementCategory,royaltyType,startDate,endDate,null,inputStream,fileName,settlementSource,playSource,royaltyShare,royaltySequence,mgAmount,mediaCategory,partnerType);
           
        } else {
        	documentCommand=new MediaSettlementCommand(modifiedParams,documentId,partnerAccountId,agreementType,
            		agreementCategory,royaltyType,startDate,endDate,null,inputStream,fileName,settlementSource,playSource,royaltyShare,royaltySequence,mgAmount,mediaCategory,partnerType);
        }
        /***
         * TODO: does not return list of changes, should be done for consistency
         * with rest of API
         **/
        final CommandProcessingResult identifier = this.mediaSettlementWritePlatformService.updateDocument(documentCommand, inputStream,entityType, entityId);

        return this.toApiJsonSerializer.serialize(identifier);
    }
    

    
    @GET
	@Path("template")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public String retriveMediaSettleTemplate(@Context UriInfo uriInfo){
    	Collection<MCodeData>  	partnerTypeData = mCodeReadPlatformService.getCodeValue("Partner Type");
    	Collection<MCodeData>  	mediaCategoryData = mCodeReadPlatformService.getCodeValue("Media Category");
    	Collection<MCodeData>  	agreementTypeData = mCodeReadPlatformService.getCodeValue("Agreement Type");
    	Collection<MCodeData>  	agreementCategoryData = mCodeReadPlatformService.getCodeValue("Agreement Category");
    	Collection<MCodeData>  	royaltyTypeData = mCodeReadPlatformService.getCodeValue("Royalty Type");
    	Collection<MCodeData>  	settlementSourceData = mCodeReadPlatformService.getCodeValue("Settlement Source");
    	Collection<MCodeData>  	playSourceData = mCodeReadPlatformService.getCodeValue("Play Source");
    	Collection<PartnerAccountData> mediaSettlementPartnerName = mediaSettlementReadPlatformService.retrievePartnerNames();
    	PartnerAccountData partnerAccountData = new PartnerAccountData(partnerTypeData,mediaCategoryData,agreementTypeData,agreementCategoryData,royaltyTypeData,settlementSourceData,mediaSettlementPartnerName,playSourceData);
    	return toApiJsonSerializer.serialize(partnerAccountData);
	}
	
    
    /*
    @GET
    @Path("disbursements")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDisburseement(@Context final UriInfo uriInfo){
    	context.authenticatedUser();
    	Collection<DisbursementData> disbursementData = mediaSettlementReadPlatformService.retriveDisbursementData();
    	return null;
    }*/
    
    @GET
    @Path("/disbursements")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getDistributionDetails(@Context final UriInfo uriInfo, @QueryParam("month") final Long month,
    		@QueryParam("partnerName") final String partnerName, @QueryParam("partnertypeId") final Long partnerTypeId){
    	
    	List<DisbursementsData> disbursementsData = mediaSettlementReadPlatformService.retrieveAllDisbursementsDataDetails( month, partnerName, partnerTypeId);
    	DisbursementsData dData = new DisbursementsData(disbursementsData);
    	return toApiJsonSerializer.serialize(dData);
    }
    
    
    /*@GET
    @Path("/partnername")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retrieveCategoryAndPartnerType(@Context final UriInfo uriInfo, @QueryParam("partnertype") final Long partnerType,@QueryParam("mediaCategory") final Long mediaCategory){
    	
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	List<DisbursementsData> partnerNameDate = mediaSettlementReadPlatformService.retrieveAllPartnerName(partnerType,mediaCategory);
    	
    	return toApiJsonSerializer.serialize(partnerNameDate);
    	
    }*/
    
    @GET
    @Path("/procedureCall")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retrieveProcedureforSettlement(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	this.mediaSettlementReadPlatformService.retrieveProcedureforSettlementData();
    	return toApiJsonSerializer.serialize(1L);
    }
    
    
    /*Ranjith code*/
    
    @DELETE
	@Path("{partnerId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deletePartnerAccount(@PathParam("partnerId") final Long partnerId) {
	final  CommandWrapper commandRequest = new CommandWrapperBuilder().deletePartnerAccount(partnerId).build();
    final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
    return this.toApiJsonSerializer.serialize(result);
    
    

 }
    
    @DELETE
	@Path("/agreement/{documentId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deletePartnerDocument(@PathParam("documentId") final Long documentId) {
	final  CommandWrapper commandRequest = new CommandWrapperBuilder().deletePartnerDocument(documentId).build();
    final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
    return this.toApiJsonSerializer.serialize(result);

 }

    @DELETE
	@Path("/partnergame/{gameId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deletePartnerGameDetails(@PathParam("gameId") final Long gameId) {
	final  CommandWrapper commandRequest = new CommandWrapperBuilder().deletePartnerGame(gameId).build();
    final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
    return this.toApiJsonSerializer.serialize(result);

    }

    
    @GET
    @Path("/client/{clientId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getClientRoyalDetail(@Context final UriInfo uriInfo, @PathParam("clientId") final Long clientId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	List<PartnerAccountData> royaltyData=null;
    	try{
    		royaltyData = mediaSettlementReadPlatformService.retrieveClientRoyaltyDetails(clientId);
    	//	PartnerAccountData royaltyData=new PartnerAccountData(partnerAccountData);
    	}catch(EmptyResultDataAccessException accessException){
    		throw new PlatformDataIntegrityException("validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber","validation.error.msg.inventory.item.duplicate.serialNumber");
    	}
    	return toApiJsonSerializer.serialize(royaltyData);
    }

    @POST
    @Path("/client/{clientId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createClientRoyaltyType(@PathParam("clientId") final Long clientId,final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createClientRoyalty(clientId).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    @GET
    @Path("/creategameevent")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createGameEvent(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	/*Collectin<StateDetails> circleData = this.mediaSettlementReadPlatformService.retrieveAllStateDetails();*/
    	Collection<MCodeData> businessLineData = this.mCodeReadPlatformService.getCodeValue("Business");
    	Collection<MCodeData> mediaCategoryData = this.mCodeReadPlatformService.getCodeValue("Media Category");
    	Collection<MCodeData> playSourceData = this.mCodeReadPlatformService.getCodeValue("Play Source");
    	Collection<PartnerAccountData> contentData=this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Content Provider","Partner Type");    	 
      	Collection<PartnerAccountData> channelData = this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Channel","Partner Type");
      	Collection<PartnerAccountData> serviceData = this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Service","Partner Type");
    	/*List<MediaAssetData> contentNameData = this.mediaAssetReadPlatformService.retrieveAllAssetdata();*/
    	List<ChargesData> chargeCodesData = this.itemReadPlatformService.retrieveChargeCode();
    	InteractiveData interactiveData = new InteractiveData();//circleData*/null,businessLineData,mediaCategoryData,playSourceData,contentNameData,chargeCodesData);
    	
    	interactiveData.setBisinessLineData(businessLineData);
    	interactiveData.setMediaCategoryData(mediaCategoryData);
    	interactiveData.setPlaySourceData(playSourceData);
    	interactiveData.setContentData(contentData);
    	interactiveData.setChannelData(channelData);
    	interactiveData.setServiceData(serviceData);
    	interactiveData.setChargeCodeData(chargeCodesData);
    	
    	return toApiJsonSerializer.serialize(interactiveData);
    }
    
    @GET
    @Path("/creategameevent/{clientId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retriveAllGameEvent(@Context final UriInfo uriInfo, @PathParam("clientId") final Long clientId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	List<InteractiveHeaderData> interactiveDatas = mediaSettlementReadPlatformService.retriveAllInteractiveForThisClient(clientId);	   	
    	return toApiJsonSerializer.serialize(interactiveDatas);
    }
    
    @POST
    @Path("/creategameevent/{clientId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createGameEvent(@PathParam("clientId") final Long clientId,final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createGameEvent(clientId).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    @GET
   	@Path("/interactive/{eventId}")
   	@Produces({MediaType.APPLICATION_JSON})
   	@Consumes({MediaType.APPLICATION_JSON})
   	public String retriveInteractiveDetailTemplate(@Context UriInfo uriInfo,@PathParam("eventId") final Long eventId){
       	 Collection<MediaAssetData> mediaData=this.assetReadPlatformService.retrieveAllAssetdata();
       	 
       	 InteractiveData interactiveData = this.mediaSettlementReadPlatformService.retrieveInteractiveHeaderData(eventId);
       	 
       	 Collection<MCodeData> businessLineData = this.mCodeReadPlatformService.getCodeValue("Business");
      	 Collection<MCodeData> mediaCategoryData = this.mCodeReadPlatformService.getCodeValue("Media Category"); 
      	 List<ChargesData> chargeCodesData = this.itemReadPlatformService.retrieveChargeCode();
      	 
       	 
       	 Collection<InteractiveDetailsData> interactiveDetailsData = this.mediaSettlementReadPlatformService.retriveInteractiveDetailsData(eventId);
       	 
       	 Collection<MCodeData> playSourceData = mCodeReadPlatformService.getCodeValue("Play Source");
       	 Collection<PartnerAccountData> contentData=this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Content Provider","Partner Type");    	 
       	 Collection<PartnerAccountData> channelData = this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Channel","Partner Type");
       	 Collection<PartnerAccountData> serviceData = this.mediaSettlementReadPlatformService.retrieveAllPartnerType("Service","Partner Type");
       	 
       	 
       	
       	 interactiveData.setInteractiveDetailsData(interactiveDetailsData);
       	 interactiveData.setPlaySourceData(playSourceData);
       	 interactiveData.setContentData(contentData);
       	 interactiveData.setChannelData(channelData);
       	 interactiveData.setServiceData(serviceData);
       	 interactiveData.setChargeCodeData(chargeCodesData);
       	 interactiveData.setBisinessLineData(businessLineData);
       	 interactiveData.setMediaCategoryData(mediaCategoryData);
       	 
       	 
       	 return toApiJsonSerializer.serialize(interactiveData);
   	}
    
    
       
     /*  
    @POST
    @Path("/interactive/{eventId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createClientInteractiveDetails(@PathParam("eventId") final Long eventId,final String jsonRequestMessageBody){
       	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
       	CommandWrapper commandRequest= new CommandWrapperBuilder().createInteractiveDetails(eventId).withJson(jsonRequestMessageBody).build();
       	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
       	return toApiJsonSerializer.serialize(result);
    }*/
    
    @PUT
    @Path("/interactive/{eventId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateClientInteractiveData(@PathParam("eventId") final Long eventId, final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasReadPermission(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest = new CommandWrapperBuilder().updateInteractiveData(eventId).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    
    @POST
    @Path("/revenue/{clientId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createRevenue(@PathParam("clientId") final Long clientId,final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().createRevenue(clientId).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    @GET
    @Path("/revenueShare/{clientId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retriveAllRevenue(@Context final UriInfo uriInfo, @PathParam("clientId") final Long clientId){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	List<RevenueShareData> interactiveDatas = mediaSettlementReadPlatformService.retriveAllrevenueshareForThisClient(clientId);	   	
    	return toApiJsonSerializer.serialize(interactiveDatas);
    }
    
    @GET
    @Path("/revenueShare/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String retriveSingleRevenueRecord(@Context final UriInfo uriInfo, @PathParam("id") final Long id){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	
    	RevenueShareData templateData=mediaSettlementReadPlatformService.retriveEditRevenueRecord(id);
    	Collection<MCodeData> mediaCategoryData = mCodeReadPlatformService.getCodeValue("Media Category");
    	Collection<MCodeData> businessLineData = this.mCodeReadPlatformService.getCodeValue("Business");
 		Collection<MCodeData>  	royaltyTypeData = mCodeReadPlatformService.getCodeValue("Royalty Type");
    	List<RevenueShareData> percentageDatas = mediaSettlementReadPlatformService.retriveSingleRevenueRecord(id);
    	templateData.setMediaCategoryData(mediaCategoryData);
    	templateData.setBusinessLineData(businessLineData);
    	templateData.setRoyaltyTypeData(royaltyTypeData);
    	templateData.setPercentageDatas(percentageDatas);

    	//List<RevenueShareData> datas = mediaSettlementReadPlatformService.retriveEditRevenueRecord(id);
    	//RevenueShareData revenueShareData=new RevenueShareData(mediaCategoryData,businessLineData,royaltyTypeData,percentageDatas,datas);
    	return toApiJsonSerializer.serialize(templateData);
    }
    
    @PUT
    @Path("/revenue/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateRevenue(@PathParam("id") final Long id,final String jsonRequestMessageBody){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissionsForGamePartner);
    	CommandWrapper commandRequest= new CommandWrapperBuilder().updateRevenue(id).withJson(jsonRequestMessageBody).build();
    	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
    	return toApiJsonSerializer.serialize(result);
    }
    
    //currency rate
    @GET
    @Path("/currency")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllDeductionDetails(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<PartnerAccountData>  currencyRateData = this.mediaSettlementReadPlatformService.retrieveAllCurrencyRateDetails();
    	PartnerAccountData  currencyData = new PartnerAccountData(currencyRateData);
    	return toApiJsonSerializer.serialize(currencyData);
    }
    
    @GET
    @Path("/currency/template")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getCurrencyTemplate(@Context final UriInfo uriInfo){
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
    	Collection<CurrencyData> currencyCodes = this.mediaSettlementReadPlatformService.retrieveCurrency();
    	/*List<PartnerAccountData> countryData = this.mediaSettlementReadPlatformService.retrieveCountryDetails();*/
    	PartnerAccountData accountData = new PartnerAccountData(null,null,currencyCodes);
    	return toApiJsonSerializer.serialize(accountData);
    }
 
   @POST
   @Path("/currency")
   @Consumes({ MediaType.APPLICATION_JSON })
	  @Produces({ MediaType.APPLICATION_JSON })
	  public String createCurrencyRate(final String apiRequestBodyAsJson) {
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
        final CommandWrapper commandRequest = new CommandWrapperBuilder().createCurrencyRate().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
	}
   
   
   @GET
   @Path("/currency/{id}")
   @Consumes({MediaType.APPLICATION_JSON})
   @Produces({MediaType.APPLICATION_JSON})
   public String getCurrencyDetail(@Context final UriInfo uriInfo, @PathParam("id") final Long id){
   	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
   	PartnerAccountData currencyData=null;
   	try{
   		
   		currencyData = mediaSettlementReadPlatformService.retrieveCurrencyRateDetail(id);
   		currencyData.setCurrencyCodes(mediaSettlementReadPlatformService.retrieveCurrency());
   	}catch(EmptyResultDataAccessException accessException){
   		throw new PlatformDataIntegrityException("validation.error.msg.currency.duplicate.serialNumber", "validation.error.msg.currency.serialNumber.not.exist", "validation.error.msg.currency.serialNumber.not.exist");
   	}
   	return toApiJsonSerializer.serialize(currencyData);
   } 
   
   @Path("/currency/{id}")
   @PUT
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON})
   public String updateCurrencyDetail(final String jsonRequestMessageBody, @PathParam("id") final Long id){
   	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
   	CommandWrapper commandRequest= new CommandWrapperBuilder().updateCurrencyRateDetail(id).withJson(jsonRequestMessageBody).build();
   	CommandProcessingResult result = commandsSourceWritePlatformService.logCommandSource(commandRequest);
   	return toApiJsonSerializer.serialize(result);
   }
   
   
 @DELETE
	@Path("/currency/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deleteCurrencyRate(@PathParam("id") final Long id) {
	final  CommandWrapper commandRequest = new CommandWrapperBuilder().deleteCurrencyRateDetail(id).build();
   final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
   return this.toApiJsonSerializer.serialize(result);

}
    
    
}











