package org.mifosplatform.billing.mediasettlement.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.joda.time.LocalDate;



public class PartnerAgreementData {

	private Long id;
	
	private String partnerName;
	private String agreementType;
	private String agreementCategory;
	private String  royaltyType;
	private Date startDate;
	private Date endDate;
	private String agmtLocation;
	private List<PartnerAgreementData> partnerAgreementDatas;
	private String partnerType;
	private Long mediaCategory;
	private String fileName;
	private String settlementSource;
	private Long royaltySequence;
	private Long royaltyShare;
	private Long status;
	private Long playSource;
	
	
	public PartnerAgreementData() {
		
	}
	
	public PartnerAgreementData(Long id,
	
	String partnerType,
	String partnerName,
	String  agreementType,
	String  agreementCategory,
	String royaltyType,
	LocalDate startDate,
	LocalDate endDate,
	String agmtLocation,
	String settlementSource,
	String fileName){
		
		
		this.id = id;
		this.partnerName = partnerName;
		this.agreementType = agreementType;
		this.agreementCategory = agreementCategory;
		this.royaltyType = royaltyType;
		this.startDate = startDate.toDate();
		this.endDate = endDate.toDate();
		this.agmtLocation = agmtLocation;
		this.settlementSource=settlementSource;
		this.fileName  = fileName;
		this.partnerType=partnerType;
	}
	

	
	public String getSettlementSource() {
		return settlementSource;
	}

	public void setSettlementSource(String settlementSource) {
		this.settlementSource = settlementSource;
	}

	public PartnerAgreementData(List<PartnerAgreementData> partnerAgreementDatas) {
		this.partnerAgreementDatas = partnerAgreementDatas;
	}
	
	
	

	public PartnerAgreementData(Long id,Long mediaCategory,Long royaltySequence, Long playSource,
			Long royaltyShare, Long status) {
		// TODO Auto-generated constructor stub
		this.royaltySequence=royaltySequence;
		this.playSource=playSource;
		this.royaltyShare=royaltyShare;
		this.status=status;
		this.mediaCategory=mediaCategory;
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	public String getAgreementCategory() {
		return agreementCategory;
	}

	public void setAgreementCategory(String agreementCategory) {
		this.agreementCategory = agreementCategory;
	}

	public String getRoyaltyType() {
		return royaltyType;
	}

	public void setRoyaltyType(String royaltyType) {
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

	public List<PartnerAgreementData> getPartnerAgreementDatas() {
		return partnerAgreementDatas;
	}

	public void setPartnerAgreementDatas(
			List<PartnerAgreementData> partnerAgreementDatas) {
		this.partnerAgreementDatas = partnerAgreementDatas;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public Long getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getRoyaltySequence() {
		return royaltySequence;
	}

	public void setRoyaltySequence(Long royaltySequence) {
		this.royaltySequence = royaltySequence;
	}

	public Long getRoyaltyShare() {
		return royaltyShare;
	}

	public void setRoyaltyShare(Long royaltyShare) {
		this.royaltyShare = royaltyShare;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getPlaySource() {
		return playSource;
	}

	public void setPlaySource(Long playSource) {
		this.playSource = playSource;
	}
	
	
	
}
