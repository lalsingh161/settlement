package org.mifosplatform.billing.mediasettlement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name="bp_ch_map_cp", uniqueConstraints = {@UniqueConstraint(columnNames={"partner_id","channel_partner_id"},name="channelmappinguniquekey")})
public class ChannelPartnerMapping extends AbstractPersistable<Long>{

	
	@Column(name="partner_id")
	private Long partnerId;
	
	@Column(name="channel_partner_id")
	private Long channelPartnerId;
	
	@Column(name="mapped")
	private Character isMapped = 'N';

	@Column(name="is_deleted")
	private Character isDeleted='N';
	
	public ChannelPartnerMapping() {
		
	}
	
	
	
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getChannelPartnerId() {
		return channelPartnerId;
	}

	public void setChannelPartnerId(Long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	public Character getIsMapped() {
		return isMapped;
	}

	public void setIsMapped(Character isMapped) {
		this.isMapped = isMapped;
	}


	public ChannelPartnerMapping(Long channelPartnerId){
		this.channelPartnerId = channelPartnerId;
	}
	
	public ChannelPartnerMapping(Long partnerId,Long l){
		this.partnerId = partnerId;
	}
	 

	public static ChannelPartnerMapping newInstance(long channelPartnerId) {
		
		return new ChannelPartnerMapping(channelPartnerId);
	}
	
	
}
