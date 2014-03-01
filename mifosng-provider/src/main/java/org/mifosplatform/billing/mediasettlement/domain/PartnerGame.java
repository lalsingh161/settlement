package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mifosplatform.billing.mediadetails.domain.MediaassetAttributes;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_games", uniqueConstraints = {@UniqueConstraint(columnNames = {"partner_id","play_source","royalty_value","royalty_sequence"},name="bp_games_pid_ps_rv_rs_uniquekey")})
public class PartnerGame extends AbstractAuditableCustom<AppUser, Long>{

	
	@Column(name="partner_id")
	private Long partnerId;
	
	
	@Column(name="play_source")
	private Long playSource;
	
	@Column(name="royalty_value")
	private BigDecimal royaltyValue;
	
	@Column(name="royalty_sequence")
	private Long royaltySequence;
	
	@Column(name = "is_deleted")
	private char isDeleted='N';
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "partnerGame", orphanRemoval = true)
	private List<PartnerGameDetails> partnerGameDetails = new ArrayList<PartnerGameDetails>();
	
	public PartnerGame() {
		// TODO Auto-generated constructor stub
	}
	
	public PartnerGame(Long partnerId, Long playSource, BigDecimal royaltyValue,
			Long royaltySequence) {
		
		
		this.partnerId = partnerId;
		this.playSource = playSource;
		this.royaltyValue = royaltyValue;
		this.royaltySequence = royaltySequence;
		
	}

	public static PartnerGame fromJson(JsonCommand command){
		final Long partnerId = command.longValueOfParameterNamed("partnerName");
		final Long playSource = command.longValueOfParameterNamed("gamePlaySource");
		final BigDecimal royaltyValue = command.bigDecimalValueOfParameterNamed("royaltyValue");
		final Long royaltySequence = command.longValueOfParameterNamed("royaltySequence");
		return new PartnerGame(partnerId,playSource,royaltyValue,royaltySequence);
	}
	
	public void add(PartnerGameDetails partnerGameDetails) {
		partnerGameDetails.update(this);
		this.partnerGameDetails.add(partnerGameDetails);
		
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
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

	public List<PartnerGameDetails> getPartnerGameDetails() {
		return partnerGameDetails;
	}

	public void setPartnerGameDetails(List<PartnerGameDetails> partnerGameDetails) {
		this.partnerGameDetails = partnerGameDetails;
	}

	public Map<String, Object> update(JsonCommand command) {
		
		final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);

        final String partnerName = "partnerName";
        final String gamePlaySource = "gamePlaySource";
        final String royaltyValue = "royaltyValue";
        final String royaltySequence = "royaltySequence";
        
        
        if (command.isChangeInLongParameterNamed(partnerName, this.partnerId)) {
            final Long newValue = command.longValueOfParameterNamed(partnerName);
            actualChanges.put(partnerName, newValue);
            this.partnerId = newValue;
        }
        if (command.isChangeInLongParameterNamed(gamePlaySource, this.playSource)) {
            final Long newValue = command.longValueOfParameterNamed(gamePlaySource);
            actualChanges.put(gamePlaySource, newValue);
            this.playSource = newValue;
        }
        if (command.isChangeInBigDecimalParameterNamed(royaltyValue, this.royaltyValue)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(royaltyValue);
            actualChanges.put(royaltyValue, newValue);
            this.royaltyValue = newValue;
        }
        if (command.isChangeInLongParameterNamed(royaltySequence, this.royaltySequence)) {
            final Long newValue = command.longValueOfParameterNamed(royaltySequence);
            actualChanges.put(royaltySequence, newValue);
            this.royaltySequence = newValue;
        }

        return actualChanges;
	}
	
	public void delete() {
		if(this.isDeleted=='N'){
			this.isDeleted='Y';
			}else{}
			
	}
}
