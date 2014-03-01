package org.mifosplatform.billing.mediasettlement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name="bp_royalty_seq")
public class SettlementSequence  extends AbstractPersistable<Long> {

	

	@Column(name="oem_id")
	private Long oemId;
	
	@Column(name="seq")
	private Long seq;
	
	@Column(name="partner_type")
	private Long partnerType;
	
	@Column(name="Active_flag")
	private String ActiveFlag;
	
	public SettlementSequence(){
		
	}
	
	
	public SettlementSequence(Long oemId, Long seq, Long partnerType,String activeFlag) {
		// TODO Auto-generated constructor stub
		this.oemId=oemId;
		this.seq=seq;
		this.partnerType=partnerType;
		this.ActiveFlag=activeFlag;
	}
		
		
/*public static SettlementSequence fomrJson(final JsonCommand command){
		
		final Long oemId = command.longValueOfParameterNamed("oemId");
		final Long seq = command.longValueOfParameterNamed("seq");
		final Long partnerType = command.longValueOfParameterNamed("partnerType");
		final String activeFlag= command.stringValueOfParameterNamed("activeFlag");
		return new SettlementSequence(oemId,seq,partnerType,activeFlag);
	}*/


public Long getOemId() {
	return oemId;
}


public Long getSeq() {
	return seq;
}


public Long getPartnerType() {
	return partnerType;
}


public String getActiveFlag() {
	return ActiveFlag;
}


public void setOemId(Long oemId) {
	this.oemId = oemId;
}


public void setSeq(Long seq) {
	this.seq = seq;
}


public void setPartnerType(Long partnerType) {
	this.partnerType = partnerType;
}


public void setActiveFlag(String activeFlag) {
	ActiveFlag = activeFlag;
}	
	



}