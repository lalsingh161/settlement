package org.mifosplatform.billing.mediasettlement.service;

import java.io.InputStream;

import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface MediaSettlementWritePlatformService {

	public CommandProcessingResult createAccountPartner(JsonCommand command);

	public CommandProcessingResult updatePartnerAccount(JsonCommand command,
			Long entityId);

	public CommandProcessingResult createPartnerGame(JsonCommand command);

	/*
	 * public CommandProcessingResult updatePartnerGame(JsonCommand command,
	 * Long entityId);
	 */

	public CommandProcessingResult createRevenueSettlement(JsonCommand command);

	public CommandProcessingResult updateDocument(
			MediaSettlementCommand documentCommand, InputStream inputStream,
			String entityType, Long entityId);

	public CommandProcessingResult deletePartnerAccount(Long entityId);

	public CommandProcessingResult createAgreement(
			MediaSettlementCommand command);

	public CommandProcessingResult createOperatorDeduction(JsonCommand command);

	public CommandProcessingResult deletePartnerDocument(Long entityId);

	public CommandProcessingResult deletePartnerGame(Long entityId);

	public CommandProcessingResult updateSettlementSequenceData(
			JsonCommand command);

	public CommandProcessingResult createClientRoyalty(Long entityId,
			JsonCommand command);

	public CommandProcessingResult createGameEvent(JsonCommand command,
			Long entityId);


	public CommandProcessingResult createRevenue(JsonCommand command);

	

	CommandProcessingResult updateRevenue(JsonCommand command);
	
	CommandProcessingResult createPAmediaCatregory(JsonCommand command);

	CommandProcessingResult updatePAmediaCatregory(JsonCommand command);
	
	public CommandProcessingResult deleteMediaCategoryDetails(Long detailId);

	public CommandProcessingResult createCurrencyRate(JsonCommand command);

	public CommandProcessingResult updateCurrencyRateDetail(Long entityId,
			JsonCommand command);

	public CommandProcessingResult deleteCurrencyRateDetail(Long entityId,
			JsonCommand command);

	
	public CommandProcessingResult updateOperatorDeduction(JsonCommand command,
			Long entityId);


	public CommandProcessingResult createPlatformStageData(JsonCommand command);

	CommandProcessingResult createOperatorData(JsonCommand command);

	

	public CommandProcessingResult updateInteractiveHeaderData(Long entityId,
			JsonCommand command);

	CommandProcessingResult updateInteractiveDetailData(Long entityId,
			JsonCommand command);
}
