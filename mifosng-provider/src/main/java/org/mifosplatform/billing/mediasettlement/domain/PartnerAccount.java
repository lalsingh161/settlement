package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.codes.exception.SystemDefinedCodeCannotBeChangedException;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_account", uniqueConstraints = {@UniqueConstraint(columnNames = {"partner_name"}, name="partner_name_UNIQUE")})
public class PartnerAccount extends AbstractAuditableCustom<AppUser, Long>{

	
	
	@Column(name="partner_type", nullable=false)
	private Long partnerType=0L;
	
	@Column(name="media_category", nullable=false)
	private Long mediaCategory=0L;
	
	@Column(name="partner_name", nullable=false)
	private String partnerName;
	
	@Column(name="partner_address", nullable=false)
	private String partnerAddress;
	
	@Column(name = "is_deleted")
	private Character isDeleted='N';
	
	/*@Column(name="country", nullable=false)
	private Long country;*/
	
	@Column(name="currency_id", nullable=false)
	private Long currencyId;
	
	@Column(name="external_id", nullable=false)
	private Long externalId;
	
	@Column(name="contact_num", nullable=false)
	private String contactNum;
	
	@Column(name="official_email_id", nullable=false)
	private String emailId;
	
	public PartnerAccount() {
	}
	
	public Long getPartnerType() {
		return partnerType;
	}


	public void setPartnerType(Long partnerType) {
		this.partnerType = partnerType;
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


	public String getPartnerAddress() {
		return partnerAddress;
	}


	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}


	public PartnerAccount(final Long partnerType, final Long mediaCategory,
			final String partnerName, final String partnerAddress, /*final Long country,*/ final Long currencyId) {
		this.partnerType = partnerType;
		this.mediaCategory = mediaCategory;
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		/*this.country = country;*/
		 this.currencyId=currencyId;
	}


	public PartnerAccount(String partnerName, String partnerAddress,
			Long currencyId, Long externalId, String contactNum,
			String emailId) {
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
		this.currencyId=currencyId;
		this.externalId=externalId;
		this.contactNum=contactNum;
		this.emailId=emailId;

		
	}

	public static PartnerAccount fomrJson(final JsonCommand command){
		
		/*final Long partnerType = command.longValueOfParameterNamed("partnerType");*/
		final String partnerName = command.stringValueOfParameterNamed("partnerName");
		/*final Long mediaCategory = command.longValueOfParameterNamed("mediaCategory");*/
		final String partnerAddress = command.stringValueOfParameterNamed("partnerAddress");
		/*final Long country=command.longValueOfParameterNamed("country");*/
		final Long currencyId = command.longValueOfParameterNamed("currencyId");
		final Integer externalId= command.integerValueOfParameterNamed("externalId");
		final String contactNum = command.stringValueOfParameterNamed("contactNum");
		final String emailId = command.stringValueOfParameterNamed("emailId");
		return new PartnerAccount(partnerName,partnerAddress,currencyId,externalId!=null?externalId.longValue():externalId,contactNum,emailId);
	}

	public void delete() {
		if (this.isDeleted == 'N') {
				this.isDeleted = 'Y';
				this.partnerName = this.partnerName + "_Deleted";
		}

	}

	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}


	

	public Map<String, Object> update(JsonCommand command) {
		    final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);

	        final String partnerType = "partnerType";
	        final String mediaCategory = "mediaCategory";
	        final String partnerName = "partnerName";
	        final String partnerAddress = "partnerAddress";
	        final String currencyCode = "currencyCode";
	        
	        
	       /* if (command.isChangeInLongParameterNamed(partnerType, this.partnerType)) {
	            final Long newValue = command.longValueOfParameterNamed(partnerType);
	            actualChanges.put(partnerType, newValue);
	            this.partnerType = newValue;
	        }
	        if (command.isChangeInLongParameterNamed(mediaCategory, this.mediaCategory)) {
	            final Long newValue = command.longValueOfParameterNamed(mediaCategory);
	            actualChanges.put(mediaCategory, newValue);
	            this.mediaCategory = newValue;
	        }*/
	        if (command.isChangeInStringParameterNamed("partnerName", this.partnerName)) {
	            final String newValue = command.stringValueOfParameterNamed(partnerName);
	            actualChanges.put("partnerName", newValue);
	            this.partnerName = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        if (command.isChangeInStringParameterNamed("partnerAddress", this.partnerAddress)) {
	            final String newValue = command.stringValueOfParameterNamed("partnerAddress");
	            actualChanges.put("partnerAddress", newValue);
	            this.partnerAddress = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        if (command.isChangeInLongParameterNamed("currencyId", this.currencyId)) {
	            final Long newValue = command.longValueOfParameterNamed("currencyId");
	            actualChanges.put("currencyId", newValue);
	            this.currencyId = newValue;
	        }
	        
	        if (command.isChangeInLongParameterNamed("externalId", this.externalId)) {
	            final Long newValue = command.longValueOfParameterNamed("externalId");
	            actualChanges.put("externalId", newValue);
	            this.externalId =new Long(newValue);
	        }
	        if (command.isChangeInStringParameterNamed("emailId", this.emailId)) {
	            final String newValue = command.stringValueOfParameterNamed("emailId");
	            actualChanges.put("emailId", newValue);
	            this.emailId = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        if (command.isChangeInStringParameterNamed("contactNum", this.contactNum)) {
	            final String newValue = command.stringValueOfParameterNamed("contactNum");
	            actualChanges.put("contactNum", newValue);
	            this.contactNum = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        
	        return actualChanges;
		
	}
	
	
}
