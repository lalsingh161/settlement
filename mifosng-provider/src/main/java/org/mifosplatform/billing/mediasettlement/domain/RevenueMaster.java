package org.mifosplatform.billing.mediasettlement.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name="bp_revenue_share_master")
public class RevenueMaster extends AbstractAuditableCustom<AppUser, Long>{

	@Column(name="client_id")
	private Long clientId;

	@Column(name="business_line")
	private Long businessLine;
	
	@Column(name="media_category")
	private Long mediaCategory;
	
	@Column(name="revenue_share_type")
	private Long revenueShareType;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "revenueMaster", orphanRemoval = true)
	private List<RevenueParam> details = new ArrayList<RevenueParam>();
	
		public RevenueMaster(){
			
		}
		
		public RevenueMaster(Long businessLine, Long mediaCategory,
			Long revenueShareType, Long clientId) {
		this.businessLine=businessLine;
		this.mediaCategory=mediaCategory;
		this.revenueShareType=revenueShareType;
		this.clientId=clientId;
	}
		public static RevenueMaster fromJson(final JsonCommand command){
		
		final Long businessLine = command.longValueOfParameterNamed("businessLine");
		final Long mediaCategory = command.longValueOfParameterNamed("mediaCategory");
		final Long revenueShareType = command.longValueOfParameterNamed("revenueShareType");
		return new RevenueMaster(businessLine,mediaCategory,revenueShareType,command.entityId());
	}

	public void addRevenueParamValues(RevenueParam revenueParam) {

		revenueParam.addRevenueDetails(this);
		this.details.add(revenueParam);

	}
	
	public Map<String, Object> update(JsonCommand command) {
		final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(
				1);

		final String businessLine = "businessLine";
		final String mediaCategory = "mediaCategory";
		final String revenueShareType = "revenueShareType";

		if (command.isChangeInLongParameterNamed(businessLine,
				this.businessLine)) {
			final Long newValue = command
					.longValueOfParameterNamed(businessLine);
			actualChanges.put(businessLine, newValue);
			this.businessLine = newValue;
		}
		if (command.isChangeInLongParameterNamed(mediaCategory,
				this.mediaCategory)) {
			final Long newValue = command
					.longValueOfParameterNamed(mediaCategory);
			actualChanges.put(mediaCategory, newValue);
			this.mediaCategory = newValue;
		}
		if (command.isChangeInLongParameterNamed(revenueShareType,
				this.revenueShareType)) {
			final Long newValue = command
					.longValueOfParameterNamed(revenueShareType);
			actualChanges.put(revenueShareType, newValue);
			this.revenueShareType = newValue;
		}

		return actualChanges;

	}
	
	public void updateRevenueParamters(JsonCommand command) {

		List<RevenueParam> parameters = new ArrayList<RevenueParam>();
		
		for (RevenueParam revenueParams : this.details) {
			
			
			
			revenueParams.update(command);

			parameters.add(revenueParams);
		}

		this.details.clear();
		this.details.addAll(parameters);

	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getBusinessLine() {
		return businessLine;
	}

	public void setBusinessLine(Long businessLine) {
		this.businessLine = businessLine;
	}

	public Long getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(Long mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public Long getRevenueShareType() {
		return revenueShareType;
	}

	public void setRevenueShareType(Long revenueShareType) {
		this.revenueShareType = revenueShareType;
	}

	public List<RevenueParam> getDetails() {
		return details;
	}

	public void setDetails(List<RevenueParam> details) {
		this.details = details;
	}
	
	
}