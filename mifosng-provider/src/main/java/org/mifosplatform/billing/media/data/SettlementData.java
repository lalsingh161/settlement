package org.mifosplatform.billing.media.data;

import java.math.BigDecimal;

import javax.persistence.Column;

public class SettlementData {

private String code;

	private Long settlementId;

	private String mediaContentProvider;
	
	private String category;
	
	private String source;
		
	private Long mediaId;
		
	private String service;
		
	private Long sequence;
		
	private String mediaType;
		
	private BigDecimal amount;

	private Long id;

	private Object description;
	



	public SettlementData(Long id, BigDecimal amount, String category,
			String mediaContentProvider, String mediaType, Long sequence, Long mediaId,String source) {
		this.settlementId = id;
		this.amount = amount;
		this.category = category;
		this.mediaContentProvider = mediaContentProvider;
		this.mediaType = mediaType;
		this.sequence = sequence;
		this.mediaId = mediaId;
		this.source = source;
	}




	public SettlementData(Long id, Long mediaId, String description,
			String category, String source, Long sequence,
			String mediaType, BigDecimal amount) {
		this.id = id;
		this.mediaId = mediaId;
		this.description = description;
		this.category = category;
		this.source = source;
		this.sequence = sequence;
		this.mediaType = mediaType;
		this.amount = amount;
	}
	
	

}
