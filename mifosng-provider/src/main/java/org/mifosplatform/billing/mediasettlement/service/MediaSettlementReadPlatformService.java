package org.mifosplatform.billing.mediasettlement.service;

import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.mediasettlement.data.DisbursementData;
import org.mifosplatform.billing.mediasettlement.data.DisbursementsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveDetailsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveHeaderData;
import org.mifosplatform.billing.mediasettlement.data.MediaCategoryData;
import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.billing.mediasettlement.data.OperatorDeductionData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAccountData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAgreementData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameDetailsData;
import org.mifosplatform.billing.mediasettlement.data.RevenueSettlementSequenceData;
import org.mifosplatform.billing.mediasettlement.data.RevenueShareData;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveHeader;
import org.mifosplatform.organisation.monetary.data.CurrencyData;


public interface MediaSettlementReadPlatformService {

	List<PartnerAccountData> retrieveAllAccountPartnerDetails();

	PartnerAccountData retrieveAllAccountPartnerDetail(Long id);

	List<PartnerAgreementData> retrievePartnerAgreementDetails();

	Collection<PartnerAccountData> retrievePartnerNames();

	MediaCategoryData retrieveMediaCategory(Long partnerId);

	MediaCategoryData retrivePartnerType(Long partnerId);

	Collection<PartnerGameDetailsData> retriveAllPartnerGameDetails();

	PartnerGameDetailsData retrivePartnerGameDetails(Long id);
	Collection<PartnerGameDetailsData> retriveChannelGameDetails(Long id);

	Collection<RevenueSettlementSequenceData> retriveRevenueSettlementSequenceData(
			Long categoryId);

	/*RevenueSettlementSequenceData getGamePrice(String stringValueOfParameterNamed);*/

	MediaSettlementCommand retrieveDocument(Long documentId);

	Collection<PartnerGameData> retriveAllContentProviderGames();

	RevenueSettlementSequenceData getGamePrice(Long gameId);

	PartnerAccountData retrieveContentProviderPartnerId(String mediaCategory);

	

	

	Collection<PartnerAccountData> retriveAllChannelPartner();

	String getPartnerType(Long partnerType);

	Collection<DisbursementData> retriveDisbursementData();

	Long getPartnerType(String string);

	Long getMapId(Long channelPartnerId);

	List<PartnerAccountData> getChannelPartnerDetails(Long id);

	Collection<PartnerGameData> retriveAllContentProviderGames(Long partnerId);

	
	List<DisbursementsData> retrieveAllDisbursementsDataDetails(Long month,String partnerName,Long partnerTypeId);

	/*List<DisbursementsData> retrieveAllPartnerName(Long partnerType,Long mediaCategory);*/

	Long getPartnerId(String stringCellValue);
	
	 void  retrieveProcedureforSettlementData();
	public Collection<OperatorDeductionData> getOperatorDeduction(Long clientId);

	Collection<OperatorDeductionData> getDeductionCodes();

	/*List<PartnerAccountData> retrieveCountryDetails();*/

	Collection<RevenueSettlementSequenceData> retriveRevenueSettlementData(
			Long categoryId);

	RevenueSettlementSequenceData retriveRevenueSettlementDefaultData();

	Long check(Long defaultData, Long seq, Long partnerType, String activeFlag);

	void updateChecked(Long oemId);

	List<PartnerAccountData> retrieveClientRoyaltyDetails(Long clientId);

	Collection<StateDetails> retrieveAllStateDetails();

	

	List<InteractiveHeaderData> retriveAllInteractiveForThisClient(Long clientId);
	
	Collection<CurrencyData> retrieveCurrency();

	Collection<PartnerAccountData> retrieveEventDetails(Long eventId);

	List<PartnerAccountData> retrieveAllPartnerType(String codeValue,
			String codeName);

	List<RevenueShareData> retriveSingleRevenueRecord(Long id);

	List<RevenueShareData> retriveAllrevenueshareForThisClient(Long clientId);

	List<RevenueShareData> retriveEditRevenueRecord(Long id);

	Collection<InteractiveDetailsData> retriveInteractiveDetailsData(
			Long eventId);

	Collection<InteractiveHeaderData> retrieveInteractiveHeaderData(Long eventId);

	

	
	
}