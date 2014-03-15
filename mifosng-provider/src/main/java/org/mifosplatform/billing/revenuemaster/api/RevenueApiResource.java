package org.mifosplatform.billing.revenuemaster.api;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.billing.revenuemaster.service.RevenueClient;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;


@Path("/revenuemaster")
@Component
@Scope("singleton")
public class RevenueApiResource {

    //private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("systemDate"));
    private final String resourceNameForPermissions = "REVENUEMASTER";

    private final PlatformSecurityContext context;
    private final DefaultToApiJsonSerializer<RevenueMasterData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final RevenueClient revenueClient;
    private final FromJsonHelper fromApiJsonHelper;
	@Autowired
	RevenueApiResource(final PlatformSecurityContext context, 
            final DefaultToApiJsonSerializer<RevenueMasterData> toApiJsonSerializer, final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,final RevenueClient revenueClient,final FromJsonHelper fromApiJsonHelper){
		
		this.context = context;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		this.revenueClient=revenueClient;
		this.fromApiJsonHelper=fromApiJsonHelper;
	}
	
	
	@POST
	@Path("{clientId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String createRevenue(@PathParam("clientId") final Long clientId,final String apiRequestBodyAsJson) {
		 final CommandWrapper wrapper = new CommandWrapperBuilder().createRevenueInvoice(clientId).withJson(apiRequestBodyAsJson).build();
		 final String json = wrapper.getJson();
			CommandProcessingResult result = null;
			
			final JsonElement parsedCommand = this.fromApiJsonHelper.parse(json);
				final JsonCommand command = JsonCommand.from(json, parsedCommand,
						this.fromApiJsonHelper, wrapper.getEntityName(),
						wrapper.getEntityId(), wrapper.getSubentityId(),
						wrapper.getGroupId(), wrapper.getClientId(),
						wrapper.getLoanId(), wrapper.getSavingsId(),
						wrapper.getCodeId(), wrapper.getSupportedEntityType(),
						wrapper.getSupportedEntityId(), wrapper.getTransactionId());
		this.revenueClient.createRevenueInvoice(command); 
		return this.toApiJsonSerializer.serialize(result);
	}
}