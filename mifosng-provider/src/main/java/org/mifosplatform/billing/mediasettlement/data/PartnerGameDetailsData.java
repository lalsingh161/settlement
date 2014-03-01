package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class PartnerGameDetailsData {

	
	
	private Long gameId;
	private Long serialNumber;
	private String game;
	private Long gameL;
	private LocalDate gameDate;
	private Long gamePlaySource;
	private BigDecimal gamePrice;
	private BigDecimal royaltyPercentage;
	
	private Long id;
	private Long partnerName;

	private BigDecimal royaltyValue;
	private Long royaltySequence;
	private PartnerGameDetailsData partnerGameDetails;
	private Collection<PartnerGameDetailsData> channelPartnerDetails;
	private String mediaCategory;
	private String partnerType;
	private Collection<MCodeData> mediaCategoryData;
	private Collection<MCodeData> playSourceData;
	private Collection<PartnerAccountData> partnerNamesData;
	private Collection<MCodeData> partnerTypeData;
	private String partnerNameStr;
	private String playSourceStr;
	private Long sequence;
	private LocalDate gDate;
	private BigDecimal price;
	private BigDecimal overwriteRoyaltyValue;
	private Long playSource;
	private Long gameDetailsId;
	private Object contentProviderGames;

	
	public PartnerGameDetailsData() {
		
	}
	
    public PartnerGameDetailsData(final Long gameId, final Long serialNumber, final String game, final LocalDate gameDate, final Long gamePlaySource, final BigDecimal gamePrice, final BigDecimal royaltyPercentage) {
		this.gameId = gameId;
		this.serialNumber = serialNumber;
		this.game = game;
		this.gameDate = gameDate;
		this.gamePlaySource = gamePlaySource;
		this.gamePrice = gamePrice;
		this.royaltyPercentage = royaltyPercentage;
	}

	public PartnerGameDetailsData(Long id, Long partnerName,
			Long playSource, BigDecimal royaltyValue, Long royaltySequence) {
		this.id = id;
		this.partnerName = partnerName;
		this.gamePlaySource = playSource;
		this.royaltyValue = royaltyValue;
		this.royaltySequence = royaltySequence;
	}

	public PartnerGameDetailsData(PartnerGameDetailsData partnerGameDetails,
			Collection<PartnerGameDetailsData> channelPartnerDetails) {
		this.partnerGameDetails = partnerGameDetails;
		this.channelPartnerDetails = channelPartnerDetails;
	}
	
	/*
	 * retrivePartnerGameDetails
	 * */
	public PartnerGameDetailsData(Long id, String partnerName,
			Long playSource, BigDecimal royaltyValue,
			Long royaltySequence, String mediaCategory, String partnerType) {
		this.id = id;
		this.partnerNameStr = partnerName;
		this.gamePlaySource = playSource;
		this.royaltyValue = royaltyValue;
		this.royaltySequence = royaltySequence;
		this.mediaCategory = mediaCategory;
		this.partnerType = partnerType;
	}

	public PartnerGameDetailsData(final Long serialNumber, final String game,
			final LocalDate gameDate, final Long gamePlaySource, final BigDecimal gamePrice,
			final BigDecimal royaltyPercentage, final Long id) {
		this.sequence = serialNumber;
		this.game = game;
		this.gDate = gameDate;
		this.playSource = gamePlaySource;
		this.price = gamePrice;
		this.overwriteRoyaltyValue = royaltyPercentage;
		this.gameDetailsId = id;
	}

	
	public PartnerGameDetailsData(final Long serialNumber, final Long game,
			final LocalDate gameDate, final Long gamePlaySource, final BigDecimal gamePrice,
			final BigDecimal royaltyPercentage, final Long id) {
		this.sequence = serialNumber;
		this.gameL = game;
		this.gDate = gameDate;
		this.playSource = gamePlaySource;
		this.price = gamePrice;
		this.overwriteRoyaltyValue = royaltyPercentage;
		this.gameDetailsId = id;
	}
	
	
	public PartnerGameDetailsData(PartnerGameDetailsData partnerGameDetails,
			Collection<PartnerGameDetailsData> channelPartnerDetails,
			Collection<MCodeData> mediaCategoryData,
			Collection<MCodeData> playSourceData,
			Collection<PartnerAccountData> partnerNamesData,
			Collection<PartnerGameData> contentProviderGames) {
		this.partnerGameDetails = partnerGameDetails;
		this.channelPartnerDetails = channelPartnerDetails;
		this.mediaCategoryData = mediaCategoryData;
		this.playSourceData = playSourceData;
		this.partnerNamesData = partnerNamesData;
		
		this.contentProviderGames = contentProviderGames;
	}
	
	/*EditPartnerGameDetailsMapper*/
	public PartnerGameDetailsData(Long id, Long partnerName,
			Long playSource, BigDecimal royaltyValue,
			Long royaltySequence, String mediaCategory, String partnerType) {
		this.id = id;
		this.partnerName = partnerName;
		this.gamePlaySource = playSource;
		this.royaltyValue = royaltyValue;
		this.royaltySequence = royaltySequence;
		this.mediaCategory = mediaCategory;
		this.partnerType = partnerType;
	}

	/*PartnerGameDetailsMapper*/
	public PartnerGameDetailsData(Long id, String partnerName,
			String playSource, BigDecimal royaltyValue, Long royaltySequence) {
		this.id = id;
		this.partnerNameStr = partnerName;
		this.playSourceStr = playSource;
		this.royaltyValue = royaltyValue;
		this.royaltySequence = royaltySequence;
		
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public LocalDate getGameDate() {
		return gameDate;
	}

	public void setGameDate(LocalDate gameDate) {
		this.gameDate = gameDate;
	}

	public Long getGamePlaySource() {
		return gamePlaySource;
	}

	public void setGamePlaySource(Long gamePlaySource) {
		this.gamePlaySource = gamePlaySource;
	}

	public BigDecimal getGamePrice() {
		return gamePrice;
	}

	public void setGamePrice(BigDecimal gamePrice) {
		this.gamePrice = gamePrice;
	}

	public BigDecimal getRoyaltyPercentage() {
		return royaltyPercentage;
	}

	public void setRoyaltyPercentage(BigDecimal royaltyPercentage) {
		this.royaltyPercentage = royaltyPercentage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(Long partnerName) {
		this.partnerName = partnerName;
	}

	
	public BigDecimal getRoyaltyValue() {
		return royaltyValue;
	}

	public void setRoyaltyValue(BigDecimal royaltyValue) {
		this.royaltyValue = royaltyValue;
	}

	public Long getRoyaltySequence() {
		return royaltySequence;
	}

	public void setRoyaltySequence(Long royaltySequence) {
		this.royaltySequence = royaltySequence;
	}

	public Long getGameL() {
		return gameL;
	}

	public void setGameL(Long gameL) {
		this.gameL = gameL;
	}

	public PartnerGameDetailsData getPartnerGameDetails() {
		return partnerGameDetails;
	}

	public void setPartnerGameDetails(PartnerGameDetailsData partnerGameDetails) {
		this.partnerGameDetails = partnerGameDetails;
	}

	public Collection<PartnerGameDetailsData> getChannelPartnerDetails() {
		return channelPartnerDetails;
	}

	public void setChannelPartnerDetails(
			Collection<PartnerGameDetailsData> channelPartnerDetails) {
		this.channelPartnerDetails = channelPartnerDetails;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public Collection<MCodeData> getMediaCategoryData() {
		return mediaCategoryData;
	}

	public void setMediaCategoryData(Collection<MCodeData> mediaCategoryData) {
		this.mediaCategoryData = mediaCategoryData;
	}

	public Collection<MCodeData> getPlaySourceData() {
		return playSourceData;
	}

	public void setPlaySourceData(Collection<MCodeData> playSourceData) {
		this.playSourceData = playSourceData;
	}

	public Collection<PartnerAccountData> getPartnerNamesData() {
		return partnerNamesData;
	}

	public void setPartnerNamesData(Collection<PartnerAccountData> partnerNamesData) {
		this.partnerNamesData = partnerNamesData;
	}

	public Collection<MCodeData> getPartnerTypeData() {
		return partnerTypeData;
	}

	public void setPartnerTypeData(Collection<MCodeData> partnerTypeData) {
		this.partnerTypeData = partnerTypeData;
	}

	public String getPartnerNameStr() {
		return partnerNameStr;
	}

	public void setPartnerNameStr(String partnerNameStr) {
		this.partnerNameStr = partnerNameStr;
	}

	public String getPlaySourceStr() {
		return playSourceStr;
	}

	public void setPlaySourceStr(String playSourceStr) {
		this.playSourceStr = playSourceStr;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public LocalDate getgDate() {
		return gDate;
	}

	public void setgDate(LocalDate gDate) {
		this.gDate = gDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOverwriteRoyaltyValue() {
		return overwriteRoyaltyValue;
	}

	public void setOverwriteRoyaltyValue(BigDecimal overwriteRoyaltyValue) {
		this.overwriteRoyaltyValue = overwriteRoyaltyValue;
	}

	public Long getPlaySource() {
		return playSource;
	}

	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
	}

	public Long getGameDetailsId() {
		return gameDetailsId;
	}

	public void setGameDetailsId(Long gameDetailsId) {
		this.gameDetailsId = gameDetailsId;
	}

	public Object getContentProviderGames() {
		return contentProviderGames;
	}

	public void setContentProviderGames(Object contentProviderGames) {
		this.contentProviderGames = contentProviderGames;
	}

	
}