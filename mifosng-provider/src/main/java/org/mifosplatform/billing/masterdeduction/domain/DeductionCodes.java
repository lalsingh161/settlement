package org.mifosplatform.billing.masterdeduction.domain;

	import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
	import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

	@Entity
	@Table(name="bp_deduction_codes")
	public class DeductionCodes extends AbstractPersistable<Long>{

		
		@Column(name="ded_code")
		private String deductionCode;
		
		@Column(name="deduction")
		private String deductionName;
		
		@Column(name="ded_type")
		private String deductionType;
		
		@Column(name="circle")
		private String deductionCircle;
		
		@Column(name = "ded_source")
		private String levelApplicable;
		
		/*@Column(name = "Customer_type")
		private Long customerType;*/
		
		@Column(name = "ded_category")
		private String business;
	    
		@Column(name="is_deleted")
		private Character isDeleted='N';
		
		
		public String getDeductionCode() {
			return deductionCode;
		}

		
		public DeductionCodes(){
			
		}
		
		
		public DeductionCodes(String deductionCode, String deductionName,
				String deductionType,String levelApplicable, String circle ,String business) {
			
			this.deductionCode = deductionCode;
			this.deductionName = deductionName;
			this.deductionType = deductionType;
			this.levelApplicable = levelApplicable;
			this.deductionCircle = circle;
			this.business = business;
		}


		public String getDeductionCircle() {
			return deductionCircle;
		}


		public void setDeductionCircle(String deductionCircle) {
			this.deductionCircle = deductionCircle;
		}


		public String getLevelApplicable() {
			return levelApplicable;
		}


		public void setLevelApplicable(String levelApplicable) {
			this.levelApplicable = levelApplicable;
		}


		public String getBusiness() {
			return business;
		}


		public void setBusiness(String business) {
			this.business = business;
		}

		public void setDeductionCode(String deductionCode) {
			this.deductionCode = deductionCode;
		}

		public String getDeductionName() {
			return deductionName;
		}

		public void setDeductionName(String deductionName) {
			this.deductionName = deductionName;
		}

		public String getDeductionType() {
			return deductionType;
		}

		public void setDeductionType(String deductionType) {
			this.deductionType = deductionType;
		}


		public Character getIsDeleted() {
			return isDeleted;
		}


		public void setIsDeleted(Character isDeleted) {
			this.isDeleted = isDeleted;
		}
				
		
	    public static DeductionCodes fromJson(final JsonCommand command){
			
			final String deductionCode = command.stringValueOfParameterNamed("deductionCode");
			final String deductionName = command.stringValueOfParameterNamed("deductionName");
			final String deductionType = command.stringValueOfParameterNamed("deductionType");
			final String levelApplicable = command.stringValueOfParameterNamed("levelApplicable");
			final String circle = command.stringValueOfParameterNamed("circle");
			final String business = command.stringValueOfParameterNamed("business");
			return new DeductionCodes(deductionCode,deductionName,deductionType,levelApplicable,circle,business);
		}

		public void delete() {
			
			if(this.isDeleted=='N'){
				this.isDeleted='Y';
			}else{
				
			}
		}
}
		
		
	


