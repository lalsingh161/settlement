package org.mifosplatform.billing.advertisement.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mifosplatform.billing.advertisement.data.AdvertisementData;
import org.mifosplatform.billing.mcodevalues.service.MCodeReadPlatformService;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Path("/advertisement")
@Component
@Scope("singleton")


public class AdvertisementApiResource {
	
	
	private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("custCode", "customerName", "customerCircle","business","activityMonth","contentName","category"));
    private final String resourceNameForPermissions = "Advertisement";
   
    private final PlatformSecurityContext context;
    private final MCodeReadPlatformService mCodeReadPlatformService;
    private final DefaultToApiJsonSerializer<AdvertisementData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    
    
    @Autowired
    public AdvertisementApiResource(final PlatformSecurityContext context, 
    		final MCodeReadPlatformService mCodeReadPlatformService, 
    		final DefaultToApiJsonSerializer<AdvertisementData> toApiJsonSerializer, 
    		final ApiRequestParameterHelper apiRequestParameterHelper,
    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
		this.context = context;
		this.mCodeReadPlatformService = mCodeReadPlatformService;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.apiRequestParameterHelper = apiRequestParameterHelper;
		this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		
    }
    
    @POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String createAdvertisment(final String apiRequestBodyAsJson) {
    	context.authenticatedUser().validateHasPermissionTo(resourceNameForPermissions);
        final CommandWrapper commandRequest = new CommandWrapperBuilder().createAdvertiseStageData().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
	}
    
    
}
