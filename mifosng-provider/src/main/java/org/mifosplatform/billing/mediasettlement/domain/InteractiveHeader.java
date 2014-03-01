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
import org.mifosplatform.billing.mediadetails.domain.MediaassetAttributes;
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
	private Long activityMonth;
	
	
	
	@Column(name="data_upload_date")
	private Date dataUploadedDate;
	
	@Column(name="business_line")
	private Long businessLine;
	
	@Column(name="media_category")
	private Long mediaCategory;
	
	@Column(name="charge_code")
	private Long chargeCode;
	
	
	@Column(name="is_deleted")
	private Character isDeleted='N';

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId", orphanRemoval = true)
	private List<InteractiveDetails> interactiveDetailData = new ArrayList<InteractiveDetails>();
	
	public InteractiveHeader() {
		
	}
	
	
	public InteractiveHeader(Long clientId, Long externalId,
			Long activityMonth, Long businessLine, Long mediaCategory, Long chargeCode, Date dataUploadedDate) {

		
		this.clientId = clientId;
		
		this.externalId = externalId;
		this.activityMonth = activityMonth;
		this.businessLine = businessLine;
		this.mediaCategory = mediaCategory;
		
		this.chargeCode = chargeCode;
		this.dataUploadedDate = dataUploadedDate;
	}

	public String displayProperties(){
		return "_clientId:"+clientId+" _externalId:"+externalId+" _activityMonth:"+activityMonth+"" +
				"_dataUploadedDate:"+dataUploadedDate+" _businessLine:"+businessLine+" _mediaCategory:"+mediaCategory+" _chargeCode:"+chargeCode+" _isDeleted:"+isDeleted;
	}

	public static InteractiveHeader fromJson(JsonCommand command){
		
		Long clientId = command.longValueOfParameterNamed("clientId");
		Long externalId = command.longValueOfParameterNamed("externalId");
		Long activityMonth = command.longValueOfParameterNamed("activityMonth");
		Long businessLine = command.longValueOfParameterNamed("businessLine");
		Long mediaCategory = command.longValueOfParameterNamed("mediaCategory");
		Long chargeCode = command.longValueOfParameterNamed("chargeCode");
		Date dataUploadedDate = command.DateValueOfParameterNamed("dataUploadedDate");
		
		return new InteractiveHeader(clientId,externalId,activityMonth,businessLine,mediaCategory,chargeCode,dataUploadedDate);
	}


	public void add(InteractiveDetails interactiveDetailData) {
		interactiveDetailData.update(this);
		this.interactiveDetailData.add(interactiveDetailData);
		
	}
}
