package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class PartnerGameData {

	private Long partnerType;
	private Long playSource;
	private BigDecimal royaltyValue;
	private Long royaltySequence;
	private Collection<MCodeData> gamePlaySource;
	private Collection<MCodeData> playSourceData;
	private Collection<PartnerAccountData> accountDatas;
	private Long id;
	private String partnerName;
	private PartnerGameDetailsData partnerGameDetails;
	private Collection<PartnerGameDetailsData> channelPartnerDetails;
	private Collection<PartnerAccountData> partnerNamesData;
	private Collection<PartnerGameData> contentProviderGames;

	
	public PartnerGameData() {
		
	}


	public PartnerGameData(Collection<MCodeData> gamePlaySource,
			Collection<PartnerAccountData> accountDatas,
			final Collection<MCodeData> playSource,
			final Collection<PartnerGameData> contentProviderGames) {
		this.gamePlaySource = gamePlaySource;
		this.accountDatas = accountDatas;
		this.playSourceData = playSource;
		this.contentProviderGames = contentProviderGames;
	}


	public PartnerGameData(Long id, String partnerName) {
		this.id = id;
		this.partnerName = partnerName;
	}




	public Long getPartnerType() {
		return partnerType;
	}


	public void setPartnerType(Long partnerType) {
		this.partnerType = partnerType;
	}


	public Long getPlaySource() {
		return playSource;
	}


	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
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
	
	
}
