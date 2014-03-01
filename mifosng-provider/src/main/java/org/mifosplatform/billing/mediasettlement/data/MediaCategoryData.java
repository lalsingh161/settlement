package org.mifosplatform.billing.mediasettlement.data;

import java.util.Collection;

public class MediaCategoryData {

	
	private Long mediaCategoryId;
	private String mediaCategory;
	private Long id;
	private String partnerType;
	private MediaCategoryData mediaCategoryData;
	private MediaCategoryData partnerTypeData;
	private Long partnerTypeId;
	private Collection<PartnerGameData> contentProviderGames;
	
	public MediaCategoryData() {
		
	}
	
	public MediaCategoryData(final Long id, final String mediaCategory, final Long mediaCategoryId){
		this.id = id;
		this.mediaCategoryId = mediaCategoryId;
		this.mediaCategory = mediaCategory;
	}

	public MediaCategoryData(final Long id,final  Long partnerTypeId, final String partnerType) {
		this.id = id;
		this.partnerType = partnerType;
		this.partnerTypeId=partnerTypeId;
	}

	public MediaCategoryData(MediaCategoryData mediaCategory2,
			MediaCategoryData partnerType2) {		
		this.mediaCategoryData = mediaCategory2;
		this.partnerTypeData = partnerType2;
	}

	public MediaCategoryData(MediaCategoryData mediaCategory2,
			MediaCategoryData partnerType2,
			Collection<PartnerGameData> contentProviderGames) {
		this.mediaCategoryData = mediaCategory2;
		this.partnerTypeData = partnerType2;
		this.contentProviderGames = contentProviderGames;
	}

	public Long getMediaCategoryId() {
		return mediaCategoryId;
	}

	public void setMediaCategoryId(Long mediaCategoryId) {
		this.mediaCategoryId = mediaCategoryId;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
