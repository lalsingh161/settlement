package org.mifosplatform.billing.masterdeduction.data;


import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.businessline.data.BusinessLineData;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

	
	public class DeductionMasterData {

	private Long id;
		
		private String deductionCode;
		private String deductionTypeStr;
		private String deductionName;
		private String levelApplicableStr;
		private String businessStr;
		private String circleStr;
	    private List<DeductionMasterData> masterDatas;
		private Collection<MCodeData> levelApplicables;
		private Collection<MCodeData> deductionTypeData;
	    private Collection<BusinessLineData> businessCategory;
	    private  Collection<StateDetails> stateDatas;

     	private Long circle;
     	private Long deductionType;
     	private Long business;
		private Long levelApplicable;
	   
		public DeductionMasterData(Collection<MCodeData> deductionTypeData,
			   Collection<MCodeData> levelApplicables,
				Collection<BusinessLineData> businessCategory, Collection<StateDetails> stateDatas) {
			this.deductionTypeData = deductionTypeData;
			this.levelApplicables = levelApplicables;
			this.businessCategory= businessCategory;
			this.stateDatas = stateDatas;
			
		}


		public DeductionMasterData(Long id,String deductionCode,
				String deductionName, String deductionType,
				String levelApplicable, String business, 
				String circle) {
			this.id= id;
			this.deductionCode = deductionCode;
			this.deductionName = deductionName;
			this.deductionTypeStr = deductionType;
			this.levelApplicableStr = levelApplicable;
			this.businessStr = business;
			this.circleStr = circle;
		}
		public DeductionMasterData(List<DeductionMasterData> masterDatas) {
			this.masterDatas=masterDatas;
		}


		public DeductionMasterData(Long id, String deductionCode,
				String deductionName, Long deductionType,
				Long levelApplicable, Long business,
				Long circle) {
			this.id=id;
			this.deductionCode=deductionCode;
			this.deductionName=deductionName;
			this.deductionType = deductionType;
			this.levelApplicable = levelApplicable;
			this.business = business;
			this.circle = circle ;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getDeductionCode() {
			return deductionCode;
		}


		public void setDeductionCode(String deductionCode) {
			this.deductionCode = deductionCode;
		}


		public String getDeductionTypeStr() {
			return deductionTypeStr;
		}


		public void setDeductionType(Collection<MCodeData> deductionTypeData) {
			this.deductionTypeData = deductionTypeData;
		}


		public String getDeductionName() {
			return deductionName;
		}


		public void setDeductionName(String deductionName) {
			this.deductionName = deductionName;
		}


		public String getLevelApplicableStr() {
			return levelApplicableStr;
		}

		public String getBusinessStr() {
			return businessStr;
		}

		public String getCircleStr() {
			return circleStr;
		}

		public List<DeductionMasterData> getMasterDatas() {
			return masterDatas;
		}


		public void setMasterDatas(List<DeductionMasterData> masterDatas) {
			this.masterDatas = masterDatas;
		}


		public Collection<MCodeData> getDeductionTypeData() {
			return deductionTypeData;
		}


		public void setDeductionTypeData(Collection<MCodeData> deductionTypeData) {
			this.deductionTypeData = deductionTypeData;
		}


		public Collection<BusinessLineData> getBusinessCategory() {
			return businessCategory;
		}


		public void setBusinessCategory(Collection<BusinessLineData> businessCategory) {
			this.businessCategory = businessCategory;
		}

		public void setLevelApplicables(Collection<MCodeData> levelApplicables) {
			this.levelApplicables = levelApplicables;
		}


		public Collection<MCodeData> getLevelApplicables() {
			return levelApplicables;
		}


		public Collection<StateDetails> getStateDatas() {
			return stateDatas;
		}


		public void setStateDatas(Collection<StateDetails> stateDatas) {
			this.stateDatas = stateDatas;
		}


		public Long getCircle() {
			return circle;
		}

		public Long getDeductionType() {
			return deductionType;
		}

		public Long getBusiness() {
			return business;
		}

		public Long getLevelApplicable() {
			return levelApplicable;
		}
		
		
	}

	
	
	
	
	
	
	
	

