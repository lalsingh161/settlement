package org.mifosplatform.billing.media.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


@Entity
@Table(name="b_settlement")
public class Settlement extends AbstractPersistable<Long>{

	@Column(name="Code")
	private String code;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Source")
	private String source;
	
	@Column(name="MediaId")
	private Long mediaId;
	
	@Column(name="Service")
	private String service;
	
	@Column(name="Seq")
	private Long seq;
	
	@Column(name="Type")
	private Character type;
	
	@Column(name="Amt")
	private BigDecimal amt;
	
	
	
	public Settlement() {
		
	}



	public Settlement(Long id, BigDecimal amount, String category,
			String mediaContentProvider, Character mediaType, Long sequence,String source) {
		this.mediaId = id;
		this.amt = amount;
		this.category = category;
		this.description = mediaContentProvider;
		this.type = mediaType;
		this.seq = sequence;
		this.source = source;
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



	public Long getSeq() {
		return seq;
	}



	public void setSeq(Long seq) {
		this.seq = seq;
	}



	public Character getType() {
		return type;
	}



	public void setType(Character type) {
		this.type = type;
	}



	public BigDecimal getAmt() {
		return amt;
	}



	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}



	public static Settlement fromJson(Long id, BigDecimal amount,
			String category, String mediaContentProvider,
			Character mediaType, Long sequence, String source) {
		
		
		return new Settlement(id,amount,category,mediaContentProvider,mediaType,sequence,source);
	}



	public Map<String, Object> update(JsonElement element,FromJsonHelper fromApiJsonHelper,Long mediaId) {
		
		final Map<String, Object> actualChanges = new LinkedHashMap<String,Object>(1);
		
		 
   	     final BigDecimal amount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("amount", element);
	     final String category = fromApiJsonHelper.extractStringNamed("category", element);
	     final String mediaContentProvider = fromApiJsonHelper.extractStringNamed("mediaContentProvider", element);
	     final String mt = fromApiJsonHelper.extractStringNamed("mediaType", element);
	     
	     final Character mediaType = mt.equals("Flat")?'F':'P';
	     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", element);
	     final String source = fromApiJsonHelper.extractStringNamed("source", element);
	     
	     if(mediaId.longValue() != this.mediaId.longValue()){
	    	 actualChanges.put("mediaId", mediaId);
			 this.mediaId = mediaId;	
	     }
	     
	     if(amount.doubleValue() != this.amt.doubleValue()){
	    	 actualChanges.put("amount", amount);
			 this.amt = amount;	
	     }
	     if(!category.equalsIgnoreCase(this.category)){
	    	 actualChanges.put("category", category);
	    	 this.category = category;
	     }
	     if(!mediaContentProvider.equalsIgnoreCase(this.description)){
	    	 actualChanges.put("mediaContentProvider", mediaContentProvider);
	    	 this.description = mediaContentProvider;
	     }
	     if(!mediaType.equals(this.type)){
	    	 actualChanges.put("mediaType", mediaType);
	    	 this.type = mediaType;
	     }
	     if(sequence.longValue()!=this.seq.longValue()){
	    	 actualChanges.put("sequence", sequence);
	    	 this.seq = sequence;
	     }
	     if(!source.equalsIgnoreCase(this.source)){
	    	 actualChanges.put("source", source);
	    	 this.source = source;
	     }
	     
	     
	     return actualChanges;
	}



	
	
	
	
	
	
	
	
}
