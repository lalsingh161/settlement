package org.mifosplatform.billing.businessline.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.eventmaster.domain.EventMaster;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "bp_intevent_dtl")
public class BusinessLineDetails extends AbstractPersistable<Long> {


	@ManyToOne
	@JoinColumn(name = "intevent_id")
	private BusinessLineMaster event;

	@Column(name = "int_category")
	private Long mediaId;
	
	public BusinessLineDetails(){
		
	}
	public BusinessLineDetails(final Long mediaId) {
		this.mediaId = mediaId;
		this.event = null;
	}

	public void update (BusinessLineMaster event){
		this.event = event;
	}
	
	public BusinessLineDetails(BusinessLineMaster event) {
		this.event = event;
	}
	
	public void delete(BusinessLineMaster event) { 
		this.event =event;
	}
	
}
	