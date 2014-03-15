package org.mifosplatform.billing.mediasettlement.data;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

public class MediaSettlementCommand {

    private Long id;
	private Long partnerAccountId;
	private Long agreementType;
	private Long agreementCategory;
	private Long royaltyType;
	private Date startDate;
	private Date endDate;
	private String agmtLocation;
	private InputStream inputStream;
	private String fileName;
	private Long mediaCategory;
	private String partnerName; 
	private Long partnerType;
	private Long settlementSource;

	private  Set<String> modifiedParameters;
	private MediaSettlementCommand mediaSettlementCommand;
	private PartnerAccountData partnerAccountData;
	
	private Long playSource;
	private BigDecimal royaltyShare;
	private Long royaltySequence;
	private Long status;
	private List<PartnerAgreementData> partnerAgreementDatas;
	
	private List<String> partnerAgreementData;

	/*mgAmount is minimum guarented amoun*/
	private BigDecimal mgAmount;

	
	public MediaSettlementCommand (Long partnerAccountId,String agmtLocation,InputStream inputStream, 
			String fileName	){
		
		this.partnerAccountId=partnerAccountId;
		
		this.agmtLocation=agmtLocation;
		this.inputStream=inputStream;
		this.fileName=fileName;
	 
	}
	
	public MediaSettlementCommand (Set<String> modifiedParameters, Long id, Long partnerAccountId,	Long agreementType,Long agreementCategory,Long royaltyType,Date startDate,
			Date endDate,String agmtLocation,InputStream inputStream, String fileName, Long settlementSource,Long playSource,
			BigDecimal royaltyShare, Long royaltySequence,BigDecimal mgAmount, Long mediaCategory, /*Long partnerType,*/Long status,List<String> partnerAgreementData){
		
		this.modifiedParameters = modifiedParameters;
		this.id = id;
		this.partnerAccountId=partnerAccountId;
		this.agreementType=agreementType;
		this.agreementCategory=agreementCategory;
		this.royaltyType=royaltyType;
		this.startDate=startDate;
		this.endDate=endDate;
		this.agmtLocation=agmtLocation;
		this.inputStream=inputStream;
		this.fileName=fileName;
	 	this.settlementSource=settlementSource;
	 	this.playSource=playSource;
	 	this.royaltyShare=royaltyShare;
	 	this.royaltySequence=royaltySequence;
	 	this.mgAmount = mgAmount;
	 	this.mediaCategory = mediaCategory;
//	 	this.partnerType = partnerType;
	 	this.status=status;
	 	this.partnerAgreementData=partnerAgreementData;
	}

	
	/*public MediaSettlementCommand (Long id,String partnerName,	Long agreementType,Long agreementCategory,Long royaltyType,LocalDate startDate,
			LocalDate endDate,Long partnerType,Long mediaCategory,Long partnerAccountId,Long settlementSource, Long playSource,
			BigDecimal royaltyShare, Long royaltySequence,BigDecimal mgAmount,Long status){
	*/	public MediaSettlementCommand (Long id,String partnerName,	Long agreementType,Long agreementCategory,Long royaltyType,LocalDate startDate,
				LocalDate endDate,Long partnerAccountId,Long settlementSource,BigDecimal mgAmount,Long partnerType){
			
		
   		this.id=id;	 
		this.partnerAccountId=partnerAccountId;
		this.agreementType=agreementType;
		this.agreementCategory=agreementCategory;
		this.royaltyType=royaltyType;
		this.startDate=startDate.toDate();
		this.endDate=endDate.toDate();
		this.partnerName=partnerName;
//		this.mediaCategory=mediaCategory;
	 	this.settlementSource=settlementSource;
	 	this.mgAmount = mgAmount;
	 	this.partnerType=partnerType;
	 	/*
	 	this.playSource=playSource;
	 	this.royaltyShare=royaltyShare;
	 	this.royaltySequence=royaltySequence;
	 	this.status=status;*/
	}

	
	
	
	public MediaSettlementCommand (Long id,String partnerName ){

		this.id=id;
		this.partnerName=partnerName;
	
	}
	
	

	 public Set<String> getModifiedParameters() {
	        return this.modifiedParameters;
	    }

	public MediaSettlementCommand(MediaSettlementCommand mediaSettlementCommand,
			PartnerAccountData partnerAccountData,List<PartnerAgreementData> partnerAgreementDatas ) {
		// TODO Auto-generated constructor stub
		this.mediaSettlementCommand=mediaSettlementCommand;
		this.partnerAccountData=partnerAccountData;
		this.partnerAgreementDatas=partnerAgreementDatas;
	}



	public Long getId() {
        return this.id;
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



	public Long getAgreementType() {
		return agreementType;
	}



	public void setAgreementType(Long agreementType) {
		this.agreementType = agreementType;
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



	public InputStream getInputStream() {
		return inputStream;
	}



	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public Long getMediaCategory() {
		return mediaCategory;
	}



	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}



	public String getPartnerName() {
		return partnerName;
	}



	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}



	public Long getSettlementSource() {
		return settlementSource;
	}


	public void setSettlementSource(Long settlementSource) {
		this.settlementSource = settlementSource;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public Long getPartnerType() {
		return partnerType;
	}



	public void setPartnerType(Long partnerType) {
		this.partnerType = partnerType;
	}


	public boolean isPartnerAccountIdChanged() {
        return this.modifiedParameters.contains("partnerAccountId");
    }

    public boolean isFileNameChanged() {
        return this.modifiedParameters.contains("fileName");
    }

    public boolean isAgreementTypeChanged() {
        return this.modifiedParameters.contains("agreementType");
    }

    public boolean isAgreementCategoryChanged() {
        return this.modifiedParameters.contains("agreementCategory");
    }

    public boolean isRoyaltyTypeChanged() {
        return this.modifiedParameters.contains("royaltyType");
    }

    public boolean isStartDateChanged() {
        return this.modifiedParameters.contains("startDate");
    }
	
    public boolean isEndDateChanged() {
        return this.modifiedParameters.contains("endDate");
    }
	
    public boolean isAgmtLocationChanged() {
        return this.modifiedParameters.contains("agmtLocation");
    }
	
    public boolean isSettlementSourceChanged() {
        return this.modifiedParameters.contains("settlementSource");
    }


	public MediaSettlementCommand getMediaSettlementCommand() {
		return mediaSettlementCommand;
	}


	public void setMediaSettlementCommand(
			MediaSettlementCommand mediaSettlementCommand) {
		this.mediaSettlementCommand = mediaSettlementCommand;
	}


	public PartnerAccountData getPartnerAccountData() {
		return partnerAccountData;
	}


	public void setPartnerAccountData(PartnerAccountData partnerAccountData) {
		this.partnerAccountData = partnerAccountData;
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


	public void setModifiedParameters(Set<String> modifiedParameters) {
		this.modifiedParameters = modifiedParameters;
	}
	
	
 
	public List<String> getPartnerAgreementData() {
		return partnerAgreementData;
	}


	public void setPartnerAgreementData(List<String> partnerAgreementData) {
		this.partnerAgreementData = partnerAgreementData;
	}


	public boolean isPlaySourceChanged() {
        return this.modifiedParameters.contains("playSource");
    }
    
    public boolean isRoyaltyShareChanged() {
        return this.modifiedParameters.contains("royaltyShare");
    }
    
    public boolean isRoyaltySequenceChanged() {
        return this.modifiedParameters.contains("royaltySequence");
    
    }


	public BigDecimal getMgAmount() {
		return mgAmount;
	}


	public void setMgAmount(BigDecimal mgAmount) {
		this.mgAmount = mgAmount;
	}
	public boolean isMgAmountChanged() {
        return this.modifiedParameters.contains("mgAmount");
    }

	public boolean isMediaCategoryChanged() {
		return this.modifiedParameters.contains("mediaCategory");
	}

	public boolean isPartnerTypeChanged() {
		return this.modifiedParameters.contains("partnerType");
	}
	
	public boolean isStatusChanged() {
		return this.modifiedParameters.contains("status");
	}

    
	
	
}
