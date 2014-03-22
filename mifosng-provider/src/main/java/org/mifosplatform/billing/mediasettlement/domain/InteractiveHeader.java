package org.mifosplatform.billing.mediasettlement.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;



@Entity
@Table(name="bp_interactive_header")
public class InteractiveHeader extends AbstractAuditableCustom<AppUser, Long>{

	@Column(name="client_id")
	private Long clientId;
	
	@Column(name="client_external_id")
	private Long externalId;
	
	
	@Column(name="activity_month")
	private String activityMonth;
	
	
	
	@Column(name="data_upload_date")
	private Date dataUploadedDate = new Date();
	
	@Column(name="business_line")
	private Long businessLine;
	
	
	
	@Column(name="charge_code")
	private Long chargeCode;
	
	
	@Column(name="is_deleted")
	private Character isDeleted='N';

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId", orphanRemoval = true)
	private List<InteractiveDetails> interactiveDetailData = new ArrayList<InteractiveDetails>();
	
	public InteractiveHeader() {
		
	}
	
	
	public InteractiveHeader(Long clientId, Long externalId,Long businessLine, Long chargeCode, LocalDate dataUploadedDate,String activityMonth) {

		
		this.clientId = clientId;
		
		this.externalId = externalId;
		this.businessLine = businessLine;
		this.activityMonth = activityMonth;
		
		this.chargeCode = chargeCode;
		this.dataUploadedDate = (null==dataUploadedDate)?new Date():dataUploadedDate.toDate();
	}

	public InteractiveHeader(Long clientId, Long clientCode,
			 Long businessLineL, Long chargeCode,
			Date dataUploadedDate,String activityMonth) {
		
		this.clientId = clientId;
		
		this.externalId = clientCode;
		this.businessLine = businessLineL;
		
		this.activityMonth = activityMonth;
		this.chargeCode = chargeCode;
		this.dataUploadedDate  = dataUploadedDate;
	
	}


	public String displayProperties(){
		return "_clientId:"+clientId+" _externalId:"+externalId+
				"_dataUploadedDate:"+dataUploadedDate+" _businessLine:"+businessLine+" _chargeCode:"+chargeCode+" _isDeleted:"+isDeleted;
	}

	public static InteractiveHeader fromJson(JsonCommand command){
		
		Long clientId = command.longValueOfParameterNamed("clientId");
		Long externalId = command.longValueOfParameterNamed("externalId");
		String activityMonth = command.stringValueOfParameterNamed("activityMonth");
		Long businessLine = command.longValueOfParameterNamed("businessLine");
		Long chargeCode = command.longValueOfParameterNamed("chargeCode");
		LocalDate dataUploadedDate = command.localDateValueOfParameterNamed("dataUploadedDate");
		
		return new InteractiveHeader(clientId,externalId,businessLine,chargeCode,dataUploadedDate,activityMonth);
	}


	public void add(InteractiveDetails interactiveDetailData) {
		interactiveDetailData.update(this);
		this.interactiveDetailData.add(interactiveDetailData);
		
	}


	public Long getClientId() {
		return clientId;
	}


	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}


	public Long getExternalId() {
		return externalId;
	}


	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}


	


	public Date getDataUploadedDate() {
		return dataUploadedDate;
	}


	public void setDataUploadedDate(Date dataUploadedDate) {
		this.dataUploadedDate = dataUploadedDate;
	}


	public Long getBusinessLine() {
		return businessLine;
	}


	public void setBusinessLine(Long businessLine) {
		this.businessLine = businessLine;
	}


	public Long getChargeCode() {
		return chargeCode;
	}


	public void setChargeCode(Long chargeCode) {
		this.chargeCode = chargeCode;
	}


	public Character getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}


	public List<InteractiveDetails> getInteractiveDetailData() {
		return interactiveDetailData;
	}


	public void setInteractiveDetailData(
			List<InteractiveDetails> interactiveDetailData) {
		this.interactiveDetailData = interactiveDetailData;
	}


	public String getActivityMonth() {
		return activityMonth;
	}


	public void setActivityMonth(String activityMonth) {
		this.activityMonth = activityMonth;
	}




	


	
}
