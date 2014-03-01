package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_settlement")
public class RevenueSettlement extends AbstractAuditableCustom<AppUser, Long> {

	
	@Column(name="media_category_id", nullable=false)
	private Long mediaCategoryId;
	
	@Column(name="partner_id", nullable=false)
	private Long partnerId;
	
	@Column(name="media_id", nullable=false)
	private Long mediaId;
	
	@Column(name="agreement_category", nullable=false)
	private Long agreementCategory;
	
	@Column(name="gameplaysource_code", nullable=false)
	private String gameplaysourceCode;
	
	@Column(name="settle_source", nullable=false)
	private String settleSource;
	
	
	@Column(name="game_price", nullable=false)
	private BigDecimal gamePrice;
	
	@Column(name="Seq", nullable=false)
	private Long sequence;
	
	@Column(name="Type", nullable=false)
	private String type;
	
	
	@Column(name="Amt", nullable=false)
	private BigDecimal amount;
	
	
	@Column(name = "Exclusion_flag")
	private char exclusionflag;
	
	
	public RevenueSettlement() {
		
	}


	public RevenueSettlement(Long mediaCategoryId, Long partnerId,
			Long agreementCategory, String playSource,
			String settlementSource, BigDecimal gamePrice, Long sequence,
			String type, BigDecimal amt, Long mediaId) {
		
		this.mediaCategoryId = mediaCategoryId;
		this.partnerId = partnerId;
		this.agreementCategory = agreementCategory;
		this.gameplaysourceCode = playSource;
		this.settleSource = settlementSource;
		this.gamePrice = gamePrice;
		this.sequence = sequence;
		this.type = type;
		this.amount = amt;
		this.mediaId = mediaId;
	}


	public static RevenueSettlement fromJson(JsonCommand command, BigDecimal gPrice) {
		
		final Long mediaCategoryId = command.longValueOfParameterNamed("mediaCategoryId");
		final Long partnerId = command.longValueOfParameterNamed("id");
		final Long agreementCategory = command.longValueOfParameterNamed("agreementCategory");
		final String playSource = command.stringValueOfParameterNamed("playSource");
		final String settlementSource = command.stringValueOfParameterNamed("settlementSource");
		final BigDecimal gamePrice  = gPrice;
		
		final Long sequence = command.longValueOfParameterNamed("royaltySequence");
		final String type = command.stringValueOfParameterNamed("royaltyType");
		/*final BigDecimal amt = command.bigDecimalValueOfParameterNamed("royalty");*/
		final BigDecimal amt = command.bigDecimalValueOfParameterNamed("royaltyValueForGame");
		final Long mediaId = command.longValueOfParameterNamed("gameId");	
		return new RevenueSettlement(mediaCategoryId,partnerId,agreementCategory,playSource,settlementSource,gamePrice,sequence,type,amt,mediaId);
	}


	public Long getMediaCategoryId() {
		return mediaCategoryId;
	}


	public void setMediaCategoryId(Long mediaCategoryId) {
		this.mediaCategoryId = mediaCategoryId;
	}


	public Long getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}


	public Long getMediaId() {
		return mediaId;
	}


	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}


	public Long getAgreementCategory() {
		return agreementCategory;
	}


	public void setAgreementCategory(Long agreementCategory) {
		this.agreementCategory = agreementCategory;
	}


	public String getGameplaysourceCode() {
		return gameplaysourceCode;
	}


	public void setGameplaysourceCode(String gameplaysourceCode) {
		this.gameplaysourceCode = gameplaysourceCode;
	}


	public String getSettleSource() {
		return settleSource;
	}


	public void setSettleSource(String settleSource) {
		this.settleSource = settleSource;
	}


	public BigDecimal getGamePrice() {
		return gamePrice;
	}


	public void setGamePrice(BigDecimal gamePrice) {
		this.gamePrice = gamePrice;
	}


	public Long getSequence() {
		return sequence;
	}


	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public char getExclusionflag() {
		return exclusionflag;
	}


	public void setExclusionflag(char exclusionflag) {
		this.exclusionflag = exclusionflag;
	}
	
}
