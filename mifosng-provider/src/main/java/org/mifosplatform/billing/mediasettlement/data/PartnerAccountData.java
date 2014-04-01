package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.media.data.MediaAssetData;
import org.mifosplatform.organisation.monetary.data.CurrencyData;

public class PartnerAccountData {

	private Collection<MCodeData> partnerTypeData;
	private Collection<MCodeData> mediaCategoryData;
	private List<PartnerAccountData> partnerAccountDatas;
	
	private Collection<MCodeData> agreementTypeData;
	private Collection<MCodeData> agreementCategoryData;
	private Collection<MCodeData> royaltyTypeData;
	private Collection<MCodeData> settlementSourceData;
	private Collection<PartnerAccountData> mediaSettlementPartnerName;
	/*private String countryCode;
	private String countryName;*/
	
	
	private Long partnerType;
	private Long mediaCategory;
	private String partnerName;
	private String partnerAddress;
	private Long id;
	private String partnerTypeStr;
	private String mediaCategoryStr;
	private Long channelPartnerId;
	private String channelPartnerName;
	private Collection<PartnerAccountData> channelPartnerData;
	private Object channelPartners;
	private List<PartnerAccountData> countryData;
	private Long country;
	private Collection<MCodeData> playSourceData;
	private String royaltyType;
	private String code;
	private Long currencyId;
	private Collection<CurrencyData> currencyCodes;
	private String currencyCode;
	private Long eventId;
	private Long clientId;
	private Collection<PartnerAccountData> eventData;
	private Collection<MediaAssetData> mediaData;
	private Collection<PartnerAccountData> contentData;
	private Collection<PartnerAccountData> channelData;
	private Collection<PartnerAccountData> serviceData;
	private Long playSource;
	private Long contentName;
	private Long contentProvider;
	private Long channelName;
	private Long serviceName;
	private BigDecimal endUserPrice;
	private BigDecimal downloads;
	private BigDecimal grossRevenue;
	private Long externalId;
	private String contactNum;
	private String emailId;
	private Collection<InteractiveHeaderData> interactiveHeaderData;
	private Collection<InteractiveDetailsData> interactiveDetailsData;
	private Long sourceCurrency;
	private Long targetCurrency;
	private BigDecimal exchangeRate;
	private LocalDate startDate;
	private LocalDate endDate;
	private String sourceCurrencyStr;
	private String targetCurrencyStr;
	private String partnerTypeName;
	private String currencyName;
	private String royaltySequence;

	public PartnerAccountData(Collection<MCodeData> partnerType,
			Collection<MCodeData> mediaCategory, List<PartnerAccountData> countryData) {
		
		this.partnerTypeData = partnerType;
		this.mediaCategoryData = mediaCategory;
		this.countryData = countryData;
	}
	
	public PartnerAccountData(Collection<MCodeData> partnerType,
			Collection<MCodeData> mediaCategory,Collection<CurrencyData> currencyCodes) {
		
		this.partnerTypeData = partnerType;
		this.mediaCategoryData = mediaCategory;
		this.currencyCodes = currencyCodes;
	}
	
	
	public PartnerAccountData(Collection<MCodeData> partnerTypeData,
			Collection<MCodeData> mediaCategoryData,
			Collection<MCodeData> agreementTypeData,
			Collection<MCodeData> agreementCategoryData,
			Collection<MCodeData> royaltyTypeData,
			Collection<MCodeData> settlementSourceData,
			Collection<PartnerAccountData> partnerNames,
			Collection<MCodeData>  	playSourceData) {
		
		this.partnerTypeData = partnerTypeData;
		this.mediaCategoryData = mediaCategoryData;
		this.agreementTypeData = agreementTypeData;
		this.agreementCategoryData = agreementCategoryData;
		this.royaltyTypeData = royaltyTypeData;
		this.settlementSourceData=settlementSourceData;
		this.mediaSettlementPartnerName=partnerNames;
		this.playSourceData = playSourceData;
	}
	public PartnerAccountData(Long id,Long partnerType,	String partnerTypeName,String partnerName, String partnerAddress,Long currencyId,String currencyCode,
			Long externalId,String contactNum,String emailId) {
		this.id = id;
		this.partnerType=partnerType;
		this.partnerTypeName = partnerTypeName;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		this.currencyId=currencyId;
		this.externalId=externalId;
		this.contactNum=contactNum;
		this.emailId=emailId;
		this.currencyCode = currencyCode;
		
	}
	
	public PartnerAccountData(Long id,Long partnerType, Long mediaCategory,
			String partnerName, String partnerAddress) {
		
		this.id = id;
		this.partnerType = partnerType;
		this.mediaCategory  = mediaCategory;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		
	}
	
	/*public PartnerAccountData(Long id, String country, String countryCode) {
		this.id=id;
		this.countryName=country;
		this.countryCode=countryCode;
	}*/

	public PartnerAccountData(List<PartnerAccountData> partnerAccountData) {
		this.partnerAccountDatas = partnerAccountData;
	}

	public PartnerAccountData(Long id, String partnerName) {
		this.id = id;
		this.partnerName = partnerName;
	}
	
		
	public PartnerAccountData(String channelPartnerName,Long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
		this.channelPartnerName = channelPartnerName;
	}

	public PartnerAccountData(Long id, String partnerType,
			String mediaCategory, String partnerName, String partnerAddress) {
		this.id = id;
		this.partnerTypeStr = partnerType;
		this.mediaCategoryStr = mediaCategory;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
	}
	
	public PartnerAccountData(Long id, String partnerType,
			String mediaCategory, String partnerName, String partnerAddress, String currencyCode) {
		this.id = id;
		this.partnerTypeStr = partnerType;
		this.mediaCategoryStr = mediaCategory;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		this.currencyCode = currencyCode;
	}

	public PartnerAccountData(Collection<PartnerAccountData> channelPartnerData) {
		this.channelPartnerData = channelPartnerData;
	}


	public PartnerAccountData(String channelPartnerName, String channelType,
			String gameCategory, String partnerAddress) {
		this.channelPartnerName = channelPartnerName;
		this.partnerTypeStr = channelType;
		this.mediaCategoryStr = gameCategory;
		this.partnerAddress = partnerAddress;
	}


	public PartnerAccountData(String channelPartnerName,
			Long channelPartnerId, Object object, Object object2,
			Object object3) {
		
		this.channelPartnerId = channelPartnerId;
		this.channelPartnerName = channelPartnerName;
		
	}


	public PartnerAccountData(Long id, Long partnerType, Long mediaCategory,
			String partnerName, String partnerAddress, Long currencyCode) {
		this.id = id;
		this.partnerType = partnerType;
		this.mediaCategory = mediaCategory;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		this.currencyCode = currencyCode.toString();
	}


	public PartnerAccountData(String royaltyType) {
		this.royaltyType = royaltyType;
	}

	public PartnerAccountData(Long eventId, Long clientId) {
		this.eventId=eventId;
		this.clientId=clientId;
	}
	
	/*public PartnerAccountData(
			Collection<MediaAssetData> mediaData,
			Collection<MCodeData> playSource,
			Collection<PartnerAccountData> contentData,
			Collection<PartnerAccountData> channelData,
			Collection<PartnerAccountData> serviceData,
			Collection<InteractiveHeaderData> interactiveHeaderData,
			Collection<InteractiveDetailsData> interactiveDetailsData) {
		
		this.mediaData = mediaData;
		this.playSourceData = playSource;
		this.contentData = contentData;
		this.channelData = channelData;
		this.serviceData = serviceData;
		this.interactiveHeaderData = interactiveHeaderData;
		this.interactiveDetailsData = interactiveDetailsData;
		
	
	}*/

	public PartnerAccountData(Long playSource, Long contentName,
			Long contentProvider, Long channelName, Long serviceName,
			BigDecimal endUserPrice, BigDecimal downloads,
			BigDecimal grossRevenue) {
		this.playSource = playSource;
		this.contentName = contentName;
		this.contentProvider = contentProvider;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.endUserPrice = endUserPrice;
		this.downloads = downloads;
		this.grossRevenue = grossRevenue;
	}

	public PartnerAccountData(Long id, String partnerName,
			String partnerAddress, Object object, String currencyCode,
			Long externalId, String contactNum, String emailId) {
		
	}

	public PartnerAccountData(final Long id, final String sourceCurrency,
			final String targetCurrency, final BigDecimal exchangeRate,final LocalDate startDate,
			final LocalDate endDate) {
	
		this.id =id;
		this.sourceCurrencyStr = sourceCurrency;
		this.targetCurrencyStr = targetCurrency;
		this.exchangeRate = exchangeRate;
		this.startDate =startDate;
		this.endDate = endDate;
			
	}

	public PartnerAccountData(Long id,final Long sourceCurrency,
			final Long targetCurrency, final BigDecimal exchangeRate,
			final LocalDate startDate, final LocalDate endDate) {
		this.id =id;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.exchangeRate = exchangeRate;
		this.startDate =startDate;
		this.endDate = endDate;
		
		
	}

	public PartnerAccountData(Long id, Long externalId, String partnerName,
			String partnerType, String contactNum, String emailId,
			String currencyName, String royaltySequence) {
		// TODO Auto-generated constructor stub
		
		this.id=id;
		this.externalId=externalId;
		this.partnerName=partnerName;
		this.partnerTypeName=partnerType;
		this.contactNum=contactNum;
		this.emailId=emailId;
		this.currencyName=currencyName;
		this.royaltySequence=royaltySequence;
		
	}
	
	public PartnerAccountData( String partnerAddress,Long id,Long externalId,String partnerName,String partnerType, String contactNum,
			String emailId,String currencyName ) {
		// TODO Auto-generated constructor stub
		
		this.id=id;
		this.externalId=externalId;
		this.partnerName=partnerName;
		this.partnerTypeName=partnerType;
		this.contactNum=contactNum;
		this.emailId=emailId;
		this.currencyName=currencyName;
		this.partnerAddress=partnerAddress;
		
	}
	
	

	public Collection<MCodeData> getPartnerType() {
		return partnerTypeData;
	}

	public void setPartnerType(Collection<MCodeData> partnerType) {
		this.partnerTypeData = partnerType;
	}

	public Collection<MCodeData> getMediaCategory() {
		return mediaCategoryData;
	}

	public void setMediaCategory(Collection<MCodeData> mediaCategory) {
		this.mediaCategoryData = mediaCategory;
	}


	public Collection<MCodeData> getPartnerTypeData() {
		return partnerTypeData;
	}


	public void setPartnerTypeData(Collection<MCodeData> partnerTypeData) {
		this.partnerTypeData = partnerTypeData;
	}


	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}


	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}


	public List<PartnerAccountData> getPartnerAccountDatas() {
		return partnerAccountDatas;
	}


	public void setPartnerAccountDatas(List<PartnerAccountData> partnerAccountDatas) {
		this.partnerAccountDatas = partnerAccountDatas;
	}


	public Collection<MCodeData> getAgreementTypeData() {
		return agreementTypeData;
	}


	public void setAgreementTypeData(Collection<MCodeData> agreementTypeData) {
		this.agreementTypeData = agreementTypeData;
	}


	public Collection<MCodeData> getAgreementCategoryData() {
		return agreementCategoryData;
	}


	public void setAgreementCategoryData(Collection<MCodeData> agreementCategoryData) {
		this.agreementCategoryData = agreementCategoryData;
	}


	public Collection<MCodeData> getRoyaltyTypeData() {
		return royaltyTypeData;
	}


	public void setRoyaltyTypeData(Collection<MCodeData> royaltyTypeData) {
		this.royaltyTypeData = royaltyTypeData;
	}


	public Collection<MCodeData> getSettlementSourceData() {
		return settlementSourceData;
	}


	public void setSettlementSourceData(Collection<MCodeData> settlementSourceData) {
		this.settlementSourceData = settlementSourceData;
	}


	public Collection<PartnerAccountData> getMediaSettlementPartnerName() {
		return mediaSettlementPartnerName;
	}


	public void setMediaSettlementPartnerName(
			Collection<PartnerAccountData> mediaSettlementPartnerName) {
		this.mediaSettlementPartnerName = mediaSettlementPartnerName;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public String getPartnerAddress() {
		return partnerAddress;
	}


	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPartnerTypeName() {
		return partnerTypeName;
	}


	public void setPartnerTypeName(String partnerTypeName) {
		this.partnerTypeName = partnerTypeName;
	}


	public String getMediaCategoryStr() {
		return mediaCategoryStr;
	}


	public void setMediaCategoryStr(String mediaCategoryStr) {
		this.mediaCategoryStr = mediaCategoryStr;
	}


	public void setPartnerType(Long partnerType) {
		this.partnerType = partnerType;
	}


	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}


	public void setChannelPartner(List<PartnerAccountData> channelsPartner) {
		channelPartners = channelsPartner;
		
	}


	public void setChannelPartnerData(
			Collection<PartnerAccountData> channelPartnerData) {
		this.channelPartnerData = channelPartnerData;
		
	}


	public Long getChannelPartnerId() {
		return channelPartnerId;
	}


	public void setChannelPartnerId(Long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}


	public String getChannelPartnerName() {
		return channelPartnerName;
	}


	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}


	public Object getChannelPartners() {
		return channelPartners;
	}


	public void setChannelPartners(Object channelPartners) {
		this.channelPartners = channelPartners;
	}


	public Collection<PartnerAccountData> getChannelPartnerData() {
		return channelPartnerData;
	}


	public void setCountry(List<PartnerAccountData> countryData) {
		this.countryData = countryData;
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Collection<CurrencyData> getCurrencyCodes() {
		return currencyCodes;
	}

	public void setCurrencyCodes(Collection<CurrencyData> currencyCodes) {
		this.currencyCodes = currencyCodes;
	}

	public Long getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(Long sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public Long getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Long targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getSourceCurrencyStr() {
		return sourceCurrencyStr;
	}

	public void setSourceCurrencyStr(String sourceCurrencyStr) {
		this.sourceCurrencyStr = sourceCurrencyStr;
	}

	public String getTargetCurrencyStr() {
		return targetCurrencyStr;
	}

	public void setTargetCurrencyStr(String targetCurrencyStr) {
		this.targetCurrencyStr = targetCurrencyStr;
	}
	
	
	

}
