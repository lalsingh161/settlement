package org.mifosplatform.billing.masterdeduction.data;


	import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;

	
	public class DeductionMasterData {

	private Long id;
		
		private String deductionCode;
		private String deductionType;
		private String deductionName;
		private String levelApplicable;
		private Long customerType;
		private String business;
		private String circle;
		private List<DeductionMasterData> masterDatas;

		private Collection<MCodeData> levelApplicables;
		private Collection<MCodeData> deductionTypeData;
	    private Collection<MCodeData> businessCategory;
	    private Collection<MCodeData> customerTypes;
	    private Collection<StateDetails> stateDatas;
	   
		public DeductionMasterData(Collection<MCodeData> deductionTypeData,
				Collection<MCodeData> customerTypes, Collection<MCodeData> levelApplicables,
				Collection<MCodeData> businessCategory, Collection<StateDetails> stateDatas) {
			this.deductionTypeData = deductionTypeData;
			this.levelApplicables = levelApplicables;
			this.businessCategory= businessCategory;
			this.customerTypes = customerTypes;
			this.stateDatas = stateDatas;
			
		}


		public DeductionMasterData(Long id,String deductionCode,
				String deductionName, String deductionType,
				String levelApplicable, String business, Long customerType,
				String circle) {
			this.id= id;
			this.deductionCode = deductionCode;
			this.deductionName = deductionName;
			this.deductionType = deductionType;
			this.levelApplicable = levelApplicable;
			this.business = business;
			this.circle = circle;
			this.customerType = customerType;
		}


		public DeductionMasterData(List<DeductionMasterData> masterDatas) {
			this.masterDatas=masterDatas;
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


		public String getDeductionType() {
			return deductionType;
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


		public String getLevelApplicable() {
			return levelApplicable;
		}


		public void setLevelApplicable(String levelApplicable) {
			this.levelApplicable = levelApplicable;
		}


		public Long getCustomerType() {
			return customerType;
		}


		public void setCustomerType(Long customerType) {
			this.customerType = customerType;
		}


		public String getBusiness() {
			return business;
		}


		public void setBusiness(String business) {
			this.business = business;
		}


		public String getCircle() {
			return circle;
		}


		public void setCircle(String circle) {
			this.circle = circle;
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


		public Collection<MCodeData> getBusinessCategory() {
			return businessCategory;
		}


		public void setBusinessCategory(Collection<MCodeData> businessCategory) {
			this.businessCategory = businessCategory;
		}


		public Collection<MCodeData> getCustomerTypes() {
			return customerTypes;
		}


		public void setCustomerTypes(Collection<MCodeData> customerTypes) {
			this.customerTypes = customerTypes;
		}


		public void setLevelApplicables(Collection<MCodeData> levelApplicables) {
			this.levelApplicables = levelApplicables;
		}


		public Collection<MCodeData> getLevelApplicables() {
			return levelApplicables;
		}


		public void setStateData(
				Collection<StateDetails> retrieveAllStateDetails) {
			this.stateDatas = retrieveAllStateDetails;
			
		}


		
		
		
	}

	
	
	
	
	
	
	
	

