package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name="bp_agreement")
public class PartnerAgreement extends AbstractAuditableCustom<AppUser, Long>{



	@Column(name="partner_account_id")
	private Long partnerAccountId;
	
	@Column(name="agreement_type")
	private Long agreementType;
	
	@Column(name="agreement_category")
	private Long agreementCategory;
	
	@Column(name="royalty_type")
	private Long royaltyType;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="agmt_location")
	private String agmtLocation;
	
	@Column(name="filename")
	private String fileName;
	
	@Column(name="settle_source")
	private Long settlementSource;
	
	@Column(name = "is_deleted")
	private char isDeleted='N';

	@Column(name="mg_amount")
	private BigDecimal mgAmount;

		
	public PartnerAgreement(){}

	public static PartnerAgreement createNew( Long partnerAccountId, Long agreementType, Long agreementCategory,
			 Long royaltyType, Date startDate, Date endDate, String agmtLocation,String fileName,Long settlementSource,BigDecimal mgAmount) {
		return new PartnerAgreement(partnerAccountId,agreementType,agreementCategory,royaltyType,startDate,endDate,agmtLocation,fileName,settlementSource,mgAmount);
   }
		
	public PartnerAgreement ( Long partnerAccountId, Long agreementType, Long agreementCategory,
			 Long royaltyType, Date startDate, Date endDate, String agmtLocation,String fileName,Long settlementSource,BigDecimal mgAmount) {
		this.partnerAccountId=partnerAccountId;
		this.agreementType=agreementType;
		this.agreementCategory=agreementCategory;
		this.royaltyType=royaltyType;
		this.startDate=startDate;
		this.endDate=endDate;
		this.agmtLocation=agmtLocation;
		this.fileName=fileName;
		this.settlementSource=settlementSource;
		this.mgAmount=mgAmount;

	}


	public Long getPartnerAccountId() {
		return partnerAccountId;
	}


	public void setPartnerAccountId(Long partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
	}


	public Long getAgreementType() {
		return agreementType;
	}


	public void setAgreementType(Long agreementType) {
		this.agreementType = agreementType;
	}


	public Long getSettlementSource() {
		return settlementSource;
	}

	public void setSettlementSource(Long settlementSource) {
		this.settlementSource = settlementSource;
	}

	public Long getAgreementCategory() {
		return agreementCategory;
	}


	public void setAgreementCategory(Long agreementCategory) {
		this.agreementCategory = agreementCategory;
	}


	public Long getRoyaltyType() {
		return royaltyType;
	}


	public void setRoyaltyType(Long royaltyType) {
		this.royaltyType = royaltyType;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getAgmtLocation() {
		return agmtLocation;
	}


	public void setAgmtLocation(String agmtLocation) {
		this.agmtLocation = agmtLocation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void update(MediaSettlementCommand command) {
		// TODO Auto-generated method stub

		
		if (command.isPartnerAccountIdChanged()) {
            this.partnerAccountId = command.getPartnerAccountId();
        }
        if (command.isAgreementTypeChanged()) {
            this.agreementType = command.getAgreementType();
        }
        if (command.isAgreementCategoryChanged()) {
            this.agreementCategory = command.getAgreementCategory();
        }
        if (command.isRoyaltyTypeChanged()) {
            this.royaltyType = command.getRoyaltyType();
        }
        if (command.isStartDateChanged()) {
            this.startDate = command.getStartDate();
        }
        if (command.isEndDateChanged()) {
            this.endDate = command.getEndDate();
        }
        if (command.isAgmtLocationChanged()) {
            this.agmtLocation = command.getAgmtLocation();
        }
        if (command.isFileNameChanged()) {
            this.fileName = command.getFileName();
        }
        if (command.isSettlementSourceChanged()) {
            this.settlementSource = command.getSettlementSource();
        }
        
        if (command.isMgAmountChanged()) {
            this.mgAmount = command.getMgAmount();
        }
       
	}

	
	
	public void delete() {
		if(this.isDeleted=='N'){
			this.isDeleted='Y';
		}else{
			
		}
		
	}

	
	
	
}