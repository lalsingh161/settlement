package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class RevenueSettlementSequenceData {

	
	private Long id;
	private Long partnerTypeId;
	private String partnerType;
	private String mediaCategory;
	private Long mediaCategoryId;
	private String partnerName;
	private Long agreementCategory;
	private Long settlementSource;
	private Long playSourceId; 
	private String playSource;
	private BigDecimal royaltyValueForGame;
	private Long royaltySequence; 
	private Long gameId;
	private String game;
	private BigDecimal royaltyPercentage; 
	private BigDecimal royalty;
	private Collection<RevenueSettlementSequenceData> settlementSequenceData;
	private Collection<MCodeData> agreementCategoryData;
	private Collection<MCodeData> agreementTypeData;
	private Collection<MCodeData> playSourceData;
	private Collection<MCodeData> royaltyTypeData;
	private Collection<MCodeData> mediaCategoryData;
	private Collection<MCodeData> partnerTypeData;
	private Collection<MCodeData> settlementSourceData;
	private Object royaltyType;
	private BigDecimal price;
	private BigDecimal gamePrice;
	private Long partnerAccountId;
	private Long partnerType4;
	private Long partnerType5;
	private Long partnerType6;
	private Long partnerType2;
	private Long partnerType1;
	private Long partnerType3;
	private Long defaultData;
	private RevenueSettlementSequenceData defaultDataValues;
	
	
	public RevenueSettlementSequenceData(Long id, Long partnerTypeId,
			String partnerType, String mediaCategory, Long mediaCategoryId,
			String partnerName, Long agreementCategory,
			Long settlementSource, Long playSourceId, String playSource,
			BigDecimal royaltyValueForGame, Long royaltySequence, Long gameId,
			String game, BigDecimal royaltyPercentage, BigDecimal royalty,BigDecimal gamePrice) {
		
		this.id = id;
		this.partnerTypeId = partnerTypeId;
		this.partnerType = partnerType;
		this.mediaCategory = mediaCategory;
		this.mediaCategoryId = mediaCategoryId;
		this.partnerName = partnerName;
		this.agreementCategory = agreementCategory;
		this.settlementSource = settlementSource;
		this.playSourceId = playSourceId;
		this.playSource = playSource;
		this.royaltyValueForGame = royaltyValueForGame;
		this.royaltySequence = royaltySequence;
		this.gameId = gameId;
		this.game = game;
		this.royaltyPercentage = royaltyPercentage;
		this.royalty = royalty;
		this.gamePrice = gamePrice;
	}


	public RevenueSettlementSequenceData(
			Collection<RevenueSettlementSequenceData> collection) {
		this.settlementSequenceData = collection;
	}


	public RevenueSettlementSequenceData(
			Collection<RevenueSettlementSequenceData> collection,
			Collection<MCodeData> agreementCData,
			Collection<MCodeData> agreementTypeData,
			Collection<MCodeData> playSourceData,
			Collection<MCodeData> royaltyTypeData,
			Collection<MCodeData> mediaCategoryData,
			Collection<MCodeData> partnerTypeData,
			Collection<MCodeData> settlementSourceData) {
		
		this.settlementSequenceData = collection;
		this.agreementCategoryData = agreementCData;
		this.agreementTypeData = agreementTypeData;
		this.playSourceData = playSourceData;
		this.royaltyTypeData = royaltyTypeData;
		this.mediaCategoryData = mediaCategoryData;
		this.partnerTypeData = partnerTypeData;
		this.settlementSourceData = settlementSourceData;
	}

	
public RevenueSettlementSequenceData(Long partnerAccountId,Long royaltySequence,String partnerName,
			
			Long partnerType4,Long partnerType5,Long partnerType6) {
		this.partnerAccountId = partnerAccountId;
		this.royaltySequence = royaltySequence;
		this.partnerName=partnerName;
		this.partnerType4=partnerType4;
		this.partnerType5=partnerType5;
		this.partnerType6=partnerType6;
	}
	public RevenueSettlementSequenceData(Long partnerType1,Long partnerType2,Long partnerType3 ,Long defaultData) {
		
		this.partnerType1=partnerType1;
		this.partnerType2=partnerType2;
		this.partnerType3=partnerType3;
		this.defaultData=defaultData;
	}

	public RevenueSettlementSequenceData(BigDecimal price) {
		this.price = price;
	}

	
	 public RevenueSettlementSequenceData(
				Collection<RevenueSettlementSequenceData> collection,
				Collection<MCodeData> partnerTypeData,
				RevenueSettlementSequenceData defaultDataValues) {
			
			this.settlementSequenceData = collection;
			this.partnerTypeData = partnerTypeData;
			this.defaultDataValues=defaultDataValues;

		}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPartnerTypeId() {
		return partnerTypeId;
	}


	public void setPartnerTypeId(Long partnerTypeId) {
		this.partnerTypeId = partnerTypeId;
	}


	public String getPartnerType() {
		return partnerType;
	}


	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}


	public String getMediaCategory() {
		return mediaCategory;
	}


	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}


	public Long getMediaCategoryId() {
		return mediaCategoryId;
	}


	public void setMediaCategoryId(Long mediaCategoryId) {
		this.mediaCategoryId = mediaCategoryId;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public Long getAgreementCategory() {
		return agreementCategory;
	}


	public void setAgreementCategory(Long agreementCategory) {
		this.agreementCategory = agreementCategory;
	}


	public Long getSettlementSource() {
		return settlementSource;
	}


	public void setSettlementSource(Long settlementSource) {
		this.settlementSource = settlementSource;
	}


	public Long getPlaySourceId() {
		return playSourceId;
	}


	public void setPlaySourceId(Long playSourceId) {
		this.playSourceId = playSourceId;
	}


	public String getPlaySource() {
		return playSource;
	}


	public void setPlaySource(String playSource) {
		this.playSource = playSource;
	}


	public BigDecimal getRoyaltyValueForGame() {
		return royaltyValueForGame;
	}


	public void setRoyaltyValueForGame(BigDecimal royaltyValueForGame) {
		this.royaltyValueForGame = royaltyValueForGame;
	}


	public Long getRoyaltySequence() {
		return royaltySequence;
	}


	public void setRoyaltySequence(Long royaltySequence) {
		this.royaltySequence = royaltySequence;
	}


	public Long getGameId() {
		return gameId;
	}


	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}


	public String getGame() {
		return game;
	}


	public void setGame(String game) {
		this.game = game;
	}


	public BigDecimal getRoyaltyPercentage() {
		return royaltyPercentage;
	}


	public void setRoyaltyPercentage(BigDecimal royaltyPercentage) {
		this.royaltyPercentage = royaltyPercentage;
	}


	public BigDecimal getRoyalty() {
		return royalty;
	}


	public void setRoyalty(BigDecimal royalty) {
		this.royalty = royalty;
	}


	public Collection<RevenueSettlementSequenceData> getSettlementSequenceData() {
		return settlementSequenceData;
	}


	public void setSettlementSequenceData(
			Collection<RevenueSettlementSequenceData> settlementSequenceData) {
		this.settlementSequenceData = settlementSequenceData;
	}


	public Collection<MCodeData> getAgreementCategoryData() {
		return agreementCategoryData;
	}


	public void setAgreementCategoryData(Collection<MCodeData> agreementCategoryData) {
		this.agreementCategoryData = agreementCategoryData;
	}


	public Collection<MCodeData> getAgreementTypeData() {
		return agreementTypeData;
	}


	public void setAgreementTypeData(Collection<MCodeData> agreementTypeData) {
		this.agreementTypeData = agreementTypeData;
	}


	public Collection<MCodeData> getPlaySourceData() {
		return playSourceData;
	}


	public void setPlaySourceData(Collection<MCodeData> playSourceData) {
		this.playSourceData = playSourceData;
	}


	public Collection<MCodeData> getRoyaltyTypeData() {
		return royaltyTypeData;
	}


	public void setRoyaltyTypeData(Collection<MCodeData> royaltyTypeData) {
		this.royaltyTypeData = royaltyTypeData;
	}


	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}


	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}


	public Collection<MCodeData> getPartnerTypeData() {
		return partnerTypeData;
	}


	public void setPartnerTypeData(Collection<MCodeData> partnerTypeData) {
		this.partnerTypeData = partnerTypeData;
	}


	public Collection<MCodeData> getSettlementSourceData() {
		return settlementSourceData;
	}


	public void setSettlementSourceData(Collection<MCodeData> settlementSourceData) {
		this.settlementSourceData = settlementSourceData;
	}


	public Object getRoyaltyType() {
		return royaltyType;
	}


	public void setRoyaltyType(Object royaltyType) {
		this.royaltyType = royaltyType;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	

}
