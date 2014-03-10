package org.mifosplatform.billing.businessline.api;

import java.util.Arrays;
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

import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.businessline.service.BusinessLineReadPlatformService;
import org.mifosplatform.billing.item.data.ChargesData;
import org.mifosplatform.billing.item.service.ItemReadPlatformService;
import org.mifosplatform.billing.media.data.MediaAssetData;
import org.mifosplatform.billing.media.service.MediaAssetReadPlatformService;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiParameterHelper;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/businessline")
@Component
@Scope("singleton")
public class BusinessLineApiResource {
	
	private final Set<String> RESPONSE_PARAMETERS = new HashSet<String>(Arrays.asList("id","eventName","eventDescription","status","chargeData"));
	
	private PortfolioCommandSourceWritePlatformService commandSourceWritePlatformService;
	private DefaultToApiJsonSerializer<BusinessLineData> toApiJsonSerializer;
	private ApiRequestParameterHelper apiRequestParameterHelper;
	private PlatformSecurityContext context;
	private BusinessLineReadPlatformService businessLineReadPlatformService;
	private MediaAssetReadPlatformService assetReadPlatformService;
	private MediaAssetReadPlatformService mediaAssetReadPlatformService;
	private final ItemReadPlatformService itemReadPlatformService;
	
	@Autowired
	public BusinessLineApiResource(final PortfolioCommandSourceWritePlatformService commandSourceWritePlatformService,
								  final DefaultToApiJsonSerializer<BusinessLineData> toApiJsonSerializer,
								  final PlatformSecurityContext context,
								  final ApiRequestParameterHelper apiRequestParameterHelper,
								  final MediaAssetReadPlatformService assetReadPlatformService,
								  final BusinessLineReadPlatformService businessLineReadPlatformService,
								  final ItemReadPlatformService itemReadPlatformService,
								  final MediaAssetReadPlatformService mediaAssetReadPlatformService) {
		this.commandSourceWritePlatformService = commandSourceWritePlatformService;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.apiRequestParameterHelper =  apiRequestParameterHelper;
		this.context = context;
		this.assetReadPlatformService = assetReadPlatformService;
		this.businessLineReadPlatformService = businessLineReadPlatformService;
		this.itemReadPlatformService=itemReadPlatformService;
		this.mediaAssetReadPlatformService=mediaAssetReadPlatformService;
	}

	@GET
	@Path("template")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String retrieveEventMasterTempleteData(@Context final UriInfo uriInfo) {
		context.authenticatedUser().validateHasReadPermission("CLIENT");
		Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(uriInfo.getQueryParameters());
		responseParameters.addAll(RESPONSE_PARAMETERS);
		BusinessLineData templetData = handleTemplateRelatedData(responseParameters);		
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	
		return this.toApiJsonSerializer.serialize(settings, templetData, RESPONSE_PARAMETERS);
	}
	public BusinessLineData handleTemplateRelatedData(final Set<String> responseParameters) {
		
	
	    List<MediaAssetData> mediaData   = this.assetReadPlatformService.retrieveAllAssetdata();
		List<EnumOptionData> statusData = this.businessLineReadPlatformService.retrieveNewStatus();
		List<ChargesData> chargeDatas = this.itemReadPlatformService.retrieveChargeCode();
		BusinessLineData singleEvent  = new BusinessLineData(mediaData,statusData,chargeDatas);
		
		return singleEvent;	
	}

	@SuppressWarnings("unused")
	@GET
	@Path("{eventId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String retrieveEventMaster(@PathParam("eventId")Integer eventId,@Context final UriInfo uriInfo) {
		context.authenticatedUser().validateHasReadPermission("CLIENT");
		Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(uriInfo.getQueryParameters());
		responseParameters.addAll(RESPONSE_PARAMETERS);
		List<MediaAssetData> mediaData   = this.assetReadPlatformService.retrieveAllAssetdata();
		List<EnumOptionData> statusData = this.businessLineReadPlatformService.retrieveNewStatus();
		List<BusinessLineData> details = this.businessLineReadPlatformService.retrieveEventDetailsData(eventId);
		List<ChargesData> chargeDatas = this.itemReadPlatformService.retrieveChargeCode();
		BusinessLineData event = this.businessLineReadPlatformService.retrieveEventMasterDetails(eventId);
	
		/*int size = mediaData.size();
		int selectedSize = details.size();
		for(int i=0;i<selectedSize;i++) {
			Long selected = details.get(i).getCategoryId();
			for(int j=0;j<size;j++) {
				Long available = mediaData.get(j).getMediaId();
				if(selected == available) {
					mediaData.remove(j);
					size--;
				}
			}
		}*/
		
		event.setMediaAsset(mediaData);
		event.setStatusData(statusData);
		event.setSelectedMedia(details);
		event.setChargeDatas(chargeDatas);
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	
		return this.toApiJsonSerializer.serialize(settings, event, RESPONSE_PARAMETERS);
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String retrieveEventMasterData(@Context UriInfo uriInfo) {
		
		final List<BusinessLineData> data = this.businessLineReadPlatformService.retrieveEventMasterData();
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, data, RESPONSE_PARAMETERS);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String createEventMaster( final String jsonRequestBody) {
		context.authenticatedUser().validateHasReadPermission("BUSINESSLINE");
		final CommandWrapper commandRequest = new CommandWrapperBuilder().createBusinessLine().withJson(jsonRequestBody).build();
		final CommandProcessingResult result  = this.commandSourceWritePlatformService.logCommandSource(commandRequest);
		return this.toApiJsonSerializer.serialize(result);
	}
	
	@PUT
	@Path("{eventId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String updateEventMaster(@PathParam("eventId")Long eventId,final String jsonRequestBody) {
		context.authenticatedUser().validateHasReadPermission("BUSINESSLINE");
		final CommandWrapper commandRequest = new CommandWrapperBuilder().updateBusinessLine(eventId).withJson(jsonRequestBody).build();
		final CommandProcessingResult result  = this.commandSourceWritePlatformService.logCommandSource(commandRequest);
		return this.toApiJsonSerializer.serialize(result);
	}
	
	/* NOT COMPLETED*/
/*	@DELETE
	@Path("{eventId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String deleteEventMaster(@PathParam("eventId") Long eventId) {
		final CommandWrapper commandRequest = new CommandWrapperBuilder().deleteBusinessLine(eventId).build();
		final CommandProcessingResult result = this.commandSourceWritePlatformService.logCommandSource(commandRequest);
		return this.toApiJsonSerializer.serialize(result);
	}*/
}