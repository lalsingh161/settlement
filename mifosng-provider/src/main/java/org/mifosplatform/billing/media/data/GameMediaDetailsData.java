package org.mifosplatform.billing.media.data;

import java.util.Collection;

import org.mifosplatform.billing.mcodevalues.data.MCodeData;

public class GameMediaDetailsData {
	
	
	private String code;
	private String description;
	private String category;
	private String source;
	private Long mediaId;
	private String service;
	private Long sequence;
	private Character type;
	private Double amount;
	private Collection<MCodeData> mediaCategory;
	private Collection<MCodeData> mediaContentProvider;
	private Collection<MCodeData> mediaType;
	private Long id;
	private String partnerName;
	
	public GameMediaDetailsData() {
		
	}

	public GameMediaDetailsData(Collection<MCodeData> mediaCategory,
			Collection<MCodeData> mediaType,
			Collection<MCodeData> mediaContentProvider) {
		
		this.mediaCategory = mediaCategory;
		this.mediaType = mediaType;
		this.mediaContentProvider = mediaContentProvider;
	}

	public GameMediaDetailsData(Long id, String partnerName) {
		this.id = id;
		this.partnerName = partnerName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
