package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_game_details")
public class PartnerGameDetails extends AbstractAuditableCustom<AppUser, Long>{

	
		
	@Column(name="serno")
	private Long serialNumber;
	
	@Column(name="media_id")
	private Long game;
	
	@Column(name="game_dt")
	private Date gameDate;
	
	@Column(name="gameplaysource_code")
	private Long gamePlaySource;
	
	@Column(name="game_price")
	private BigDecimal gamePrice;
	
	@Column(name="royalty_per")
	private BigDecimal royaltyPercentage;
	
	@ManyToOne
	@JoinColumn(name="game_id")
	private PartnerGame partnerGame;
	
	@Column(name = "is_deleted")
	private char isDeleted='N';
	
	public PartnerGameDetails() {
		
	}
	
	public PartnerGameDetails(final Long serialNumber,
			final Long game, final Date gameDate, final Long gamePlaySource,
			final BigDecimal gamePrice, final BigDecimal royaltyPercentage) {
		
	
		this.serialNumber = serialNumber;
		this.game = game;
		this.gameDate = gameDate;
		this.gamePlaySource = gamePlaySource;
		this.gamePrice = gamePrice;
		this.royaltyPercentage = royaltyPercentage;
	}

	public static PartnerGameDetails fromJson(LocalDate gDate,
			Long gameStr, BigDecimal overwriteRoyaltyValue, Long playSource,
			BigDecimal price, Long sequence) {
		return new PartnerGameDetails(sequence,gameStr,gDate.toDate(),playSource,price,overwriteRoyaltyValue);
				
	}

	public void update(PartnerGame partnerGame) {
		this.partnerGame = partnerGame;		
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getGame() {
		return game;
	}

	public void setGame(Long game) {
		this.game = game;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
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

	public PartnerGame getPartnerGame() {
		return partnerGame;
	}

	public void setPartnerGame(PartnerGame partnerGame) {
		this.partnerGame = partnerGame;
	}
	
	public void delete() {
		if(this.isDeleted=='N'){
			this.isDeleted='Y';
		}else{
			
		}	
	}
}
