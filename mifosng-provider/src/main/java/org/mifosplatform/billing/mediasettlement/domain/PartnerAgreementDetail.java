package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_agreement_dtl")
public class PartnerAgreementDetail extends AbstractAuditableCustom<AppUser, Long>{

	@ManyToOne
    @JoinColumn(name="agmt_id")
	private PartnerAgreement paObject;
	
	@Column(name="play_source")
	private Long playSource;

	@Column(name="royalty_share")
	private BigDecimal royaltyShare;

	@Column(name="royalty_sequence")
	private Long royaltySequence;
	
	@Column(name="media_category")
	private Long mediaCategory;
	
	@Column(name="partner_type")
	private Long partnerType=0L;
	
/*	@Column(name="agmt_id")
	private Long agmtId;*/
	
	@Column(name="status")
	private Long status;
	
	@Column(name="partner_account_id")
	private Long partnerAccountId;
	
	public PartnerAgreementDetail() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static PartnerAgreementDetail createNew(Long playSource,BigDecimal royaltyShare, Long royaltySequence,Long mediaCategory, Long status,Long partnerAccountId) {
	
		return new PartnerAgreementDetail(playSource,royaltyShare,royaltySequence,mediaCategory,status,partnerAccountId);
	}
	
	public void update(PartnerAgreement partnerAgreement) {
		this.paObject = partnerAgreement;
		
	}
	
	public PartnerAgreementDetail ( Long playSource, BigDecimal royaltyShare,
				Long royaltySequence, Long mediaCategory,Long status,Long partnerAccountId) {
		
		
		this.playSource=playSource;
		this.royaltyShare=royaltyShare;
		this.royaltySequence=royaltySequence;
		this.mediaCategory = mediaCategory;
//		this.partnerType = partnerType;
		this.status=status;
		this.partnerAccountId=partnerAccountId;
		
	}

	/*
	public static PartnerAgreementDetail createNews(Long agmtId,Long playSource,BigDecimal royaltyShare, Long royaltySequence,Long mediaCategory, Long status,Long partnerAccountId) {
		
		return new PartnerAgreementDetail(playSource,royaltyShare,royaltySequence,mediaCategory,status,partnerAccountId);
	}
	public PartnerAgreementDetail (Long agmtId, Long playSource, BigDecimal royaltyShare,
			Long royaltySequence, Long mediaCategory,Long status,Long partnerAccountId) {
	
	this.agmtId=agmtId;
	this.playSource=playSource;
	this.royaltyShare=royaltyShare;
	this.royaltySequence=royaltySequence;
	this.mediaCategory = mediaCategory;
//	this.partnerType = partnerType;
	this.status=status;
	this.partnerAccountId=partnerAccountId;
	
}*/

	public PartnerAgreement getPaObject() {
		return paObject;
	}


	public void setPaObject(PartnerAgreement paObject) {
		this.paObject = paObject;
	}


	public Long getPlaySource() {
		return playSource;
	}


	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
	}


	public BigDecimal getRoyaltyShare() {
		return royaltyShare;
	}


	public void setRoyaltyShare(BigDecimal royaltyShare) {
		this.royaltyShare = royaltyShare;
	}


	public Long getRoyaltySequence() {
		return royaltySequence;
	}


	public void setRoyaltySequence(Long royaltySequence) {
		this.royaltySequence = royaltySequence;
	}


	public Long getMediaCategory() {
		return mediaCategory;
	}


	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}


	public Long getPartnerType() {
		return partnerType;
	}


	public void setPartnerType(Long partnerType) {
		this.partnerType = partnerType;
	}


	/*public Long getAgmtId() {
		return agmtId;
	}


	public void setAgmtId(Long agmtId) {
		this.agmtId = agmtId;
	}*/


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}
	
	
	
	public Long getPartnerAccountId() {
		return partnerAccountId;
	}


	public void setPartnerAccountId(Long partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
	}


/*	public void update(MediaSettlementCommand command) {
		// TODO Auto-generated method stub

		
		if (command.isPartnerAccountIdChanged()) {
            this.agmtId = command.getId();
        }
       
        if (command.isPlaySourceChanged()) {
            this.playSource = command.getPlaySource();
        }
        if (command.isRoyaltyShareChanged()) {
            this.royaltyShare = command.getRoyaltyShare();
        }
        if (command.isRoyaltySequenceChanged()) {
            this.royaltySequence = command.getRoyaltySequence();
        }
        if (command.isMediaCategoryChanged()){
        	this.mediaCategory=command.getMediaCategory();
        }
        if(command.isPartnerTypeChanged()){
        	this.partnerType=command.getPartnerType();
        }
        if(command.isStatusChanged()){
        	this.status=command.getStatus();
        }
	}
*/
	
	public Map<String, Object> update(JsonCommand command) {
		
		final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);

		final String playSource ="playSource";
		final String royaltySequence ="royaltySequence";
		final String mediaCategory ="mediaCategory";
		final String partnerType ="partnerType";
		final String status ="status";
        
        
        if (command.isChangeInLongParameterNamed(playSource, this.playSource)) {
            final Long newValue = command.longValueOfParameterNamed(playSource);
            actualChanges.put(playSource, newValue);
            this.playSource = newValue;
        }
        if (command.isChangeInLongParameterNamed(royaltySequence, this.royaltySequence)) {
            final Long newValue = command.longValueOfParameterNamed(royaltySequence);
            actualChanges.put(royaltySequence, newValue);
            this.royaltySequence = newValue;
        }
        if (command.isChangeInLongParameterNamed(mediaCategory, this.mediaCategory)) {
            final Long newValue = command.longValueOfParameterNamed(mediaCategory);
            actualChanges.put(mediaCategory, newValue);
            this.mediaCategory = newValue;
        }
        /*if (command.isChangeInLongParameterNamed(partnerType, this.partnerType)) {
            final Long newValue = command.longValueOfParameterNamed(partnerType);
            actualChanges.put(partnerType, newValue);
            this.partnerType = newValue;
        }*/
        
        if (command.isChangeInLongParameterNamed(status, this.status)) {
            final Long newValue = command.longValueOfParameterNamed(status);
            actualChanges.put(status, newValue);
            this.status = newValue;
        }

        return actualChanges;
	}
	
	
	
}
