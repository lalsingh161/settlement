package org.mifosplatform.billing.mediasettlement.data;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

public class PartnerAgreementView {
	
	
	
	private Long id;
	private Long partnerAccountId;
	private String agreementType;
	private String agreementCategory;
	private String royaltyType;
	private Date startDate;
	private Date endDate;
	private String agmtLocation;
	private String fileName;
	private String partnerName; 
	private String partnerType;
	private String settlementSource;

	private String mediaCategory;
	private String playSource;
	private Long royaltyShare;
	private String royaltySequence;
	private Long status;
	private BigDecimal mgAmount;
	private PartnerAgreementView agreementDetails;
	private List<PartnerAgreementView> mediaCategoryDetails;
	
	public PartnerAgreementView() {
		// TODO Auto-generated constructor stub
	}


	public PartnerAgreementView(Long id, Long partnerAccountId,
			String partnerType, String partnerName, String agreementType,
			String agreementCategory, String royaltyType,
			String settlementSource, LocalDate startDate, LocalDate endDate,
			BigDecimal mgAmount,String fileName) {
		// TODO Auto-generated constructor stub

		this.id = id;
		this.partnerAccountId=partnerAccountId;
		this.agreementType=agreementType;
		this.agreementCategory=agreementCategory;
		this.royaltyType=royaltyType;
		this.startDate=startDate.toDate();
		this.endDate=endDate.toDate();
		this.fileName=fileName;
	 	this.settlementSource=settlementSource;
	 	this.mgAmount = mgAmount;
	 	this.partnerType = partnerType;
	 	this.partnerName=partnerName;
	}


	public PartnerAgreementView(Long id, String mediaCategory,
			String playSource, String royaltySequence, Long royaltyShare,
			Long status) {
		// TODO Auto-generated constructor stub
		

		this.id = id;
		this.mediaCategory=mediaCategory;
		this.playSource=playSource;
		this.royaltySequence=royaltySequence;
		this.royaltyShare=royaltyShare;
		this.status=status;
		
		
	}


	public PartnerAgreementView(PartnerAgreementView agreementDetails,
			List<PartnerAgreementView> mediaCategoryDetails) {
		// TODO Auto-generated constructor stub
		
		this.agreementDetails=agreementDetails;
		this.mediaCategoryDetails=mediaCategoryDetails;
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPartnerAccountId() {
		return partnerAccountId;
	}


	public void setPartnerAccountId(Long partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
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


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public String getPartnerType() {
		return partnerType;
	}


	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}


	public String getSettlementSource() {
		return settlementSource;
	}


	public void setSettlementSource(String settlementSource) {
		this.settlementSource = settlementSource;
	}


	public String getMediaCategory() {
		return mediaCategory;
	}


	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}


	public String getPlaySource() {
		return playSource;
	}


	public void setPlaySource(String playSource) {
		this.playSource = playSource;
	}


	public Long getRoyaltyShare() {
		return royaltyShare;
	}


	public void setRoyaltyShare(Long royaltyShare) {
		this.royaltyShare = royaltyShare;
	}


	public String getRoyaltySequence() {
		return royaltySequence;
	}


	public void setRoyaltySequence(String royaltySequence) {
		this.royaltySequence = royaltySequence;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public BigDecimal getMgAmount() {
		return mgAmount;
	}


	public void setMgAmount(BigDecimal mgAmount) {
		this.mgAmount = mgAmount;
	}
	
	
	
	
	
	


}
