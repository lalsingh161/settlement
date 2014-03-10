package org.mifosplatform.billing.mediasettlement.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;


public class DisbursementsData {
	

	private String branch;
	private String city;
	private String state;
	private String client;
	private String invoiceId;
	private BigDecimal invoiceAmount;
	private Date invoiceDate;
	private String videoGame;
	private BigDecimal partnerTypeId;
	private String amountSharable;
	private Long  sequence;
	private String playSource;
	private String royaltyType;
	private String partnerType;
	private BigDecimal commPercent;
	private BigDecimal royaltyAmount;
	private BigDecimal netAmount;
	private String partnerName;
	private Long mediaCategory;
	
	List<DisbursementsData> distributionData;
	
	public DisbursementsData(){
		
	}
	
	public DisbursementsData(List<DisbursementsData> distributionData) {
		this.distributionData = distributionData;
	}
	
	public DisbursementsData(String partnerName){
		this.partnerName=partnerName;
	}
	
	public DisbursementsData(
			 String branch,
			 String city,
			 String state,
			 String client,
			 String invoiceId,
			 BigDecimal invoiceAmount,
			 LocalDate invoiceDate,
			 Long mediaCategory,
			 BigDecimal partnerTypeId,
			 String amountSharable,
			 Long  sequence,
			 String playSource,
			 String partnerName,
			 String royaltyType,
			 String partnerType,
			 BigDecimal commPercent,
			 BigDecimal royaltyAmount,
			 BigDecimal netAmount ){
		

		this.branch=branch;
		this.city=city;
		this.state=state;
		this.client=client;
		this.invoiceId=invoiceId;
		this.invoiceAmount=invoiceAmount;
		this.invoiceDate=invoiceDate.toDate();
		this.mediaCategory=mediaCategory;
		this.partnerTypeId=partnerTypeId;
		this.amountSharable=amountSharable;
		this.sequence=sequence;
		this.playSource=playSource;
		this.partnerName=partnerName;
		this.royaltyType=royaltyType;
		this.partnerType=partnerType;
		this.commPercent=commPercent;
		this.royaltyAmount=royaltyAmount;
		this.netAmount=netAmount;
		
	}
	
	
	

	public String getBranch() {
		return branch;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getClient() {
		return client;
	}

	
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public String getVideoGame() {
		return videoGame;
	}

	public BigDecimal getPartnerTypeId() {
		return partnerTypeId;
	}


	public Long getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public Long getSequence() {
		return sequence;
	}

	public String getPlaySource() {
		return playSource;
	}

	public String getRoyaltyType() {
		return royaltyType;
	}

	public String getPartnerType() {
		return partnerType;
	}


	public BigDecimal getRoyaltyAmount() {
		return royaltyAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public List<DisbursementsData> getDistributionData() {
		return distributionData;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setClient(String client) {
		this.client = client;
	}

	

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setVideoGame(String videoGame) {
		this.videoGame = videoGame;
	}

	public void setPartnerTypeId(BigDecimal partnerTypeId) {
		this.partnerTypeId = partnerTypeId;
	}



	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public void setPlaySource(String playSource) {
		this.playSource = playSource;
	}

	public void setRoyaltyType(String royaltyType) {
		this.royaltyType = royaltyType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}



	public void setRoyaltyAmount(BigDecimal royaltyAmount) {
		this.royaltyAmount = royaltyAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void setDistributionData(List<DisbursementsData> distributionData) {
		this.distributionData = distributionData;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getAmountSharable() {
		return amountSharable;
	}

	public void setAmountSharable(String amountSharable) {
		this.amountSharable = amountSharable;
	}

	public BigDecimal getCommPercent() {
		return commPercent;
	}

	public void setCommPercent(BigDecimal commPercent) {
		this.commPercent = commPercent;
	}


	
	
	
}