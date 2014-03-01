package org.mifosplatform.billing.media.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.mcodevalues.data.MCodeData;
import org.mifosplatform.billing.mediadetails.data.MediaLocationData;
import org.mifosplatform.billing.paymode.data.McodeData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.core.data.MediaEnumoptionData;

public class MediaAssetData {


	private   Long mediaId;
	private  String mediaTitle;
	private  String mediaImage;
	private  BigDecimal mediaRating;
	private  Long eventId;
	private Long noOfPages;
	private Long pageNo;
	private String assetTag;
	private List<EnumOptionData> mediaStatus;
	private List<MediaassetAttribute> mediaAttributes;
	private List<MediaassetAttribute> mediaFormat;
	private String status;
	private LocalDate releaseDate;
	private List<MediaEnumoptionData> mediaTypeData;
	private List<McodeData> mediaCategeorydata;
	private List<McodeData> mediaLanguageData;
	private MediaAssetData mediaAssetData;
	private List<MediaLocationData> mediaLocationData;
	private List<MediaassetAttributeData> mediaassetAttributes;
	private String mediatype;
	private String genre;
	private Long catageoryId;
	private String subject;
	private String overview;
	private Long contentProvider;
	private String rated;
	private BigDecimal rating;
	private String duration;
	private Long ratingCount;
	private String mediaCategoryG;
	private Collection<MCodeData> mediaCategory;
	private List<MCodeData> mediaContentProvider;
	private Collection<MCodeData> mCProvider;
	private Collection<MCodeData> revenueHolders;
	private Collection<MCodeData> mediaType;
	private List<SettlementData> settlementData;
	private Collection<MCodeData> source;
	private Collection<MCodeData> oem;
	private Collection<MCodeData> cProvider;
	private Collection<MCodeData> wrappers;
	private Collection<GameMediaDetailsData> contentProviderName;
	private Object partnerName;
	
	public MediaAssetData(Collection<MCodeData> mediaCategory, Collection<MCodeData> mediaType, Collection<MCodeData> source) {	
		this.mediaId=null;
		this.mediaTitle=null;
		this.mediaImage=null;
		this.mediaRating=null;
		this.eventId=null;
		this.mediaCategory = mediaCategory;
		this.mediaType = mediaType;
		this.source = source;
	
	}
	public MediaAssetData(Collection<MCodeData> mediaCategory, Collection<MCodeData> mediaType, Collection<MCodeData> source, final List<MCodeData> mediaContentProvider) {	
		this.mediaId=null;
		this.mediaTitle=null;
		this.mediaImage=null;
		this.mediaRating=null;
		this.eventId=null;
		this.mediaCategory = mediaCategory;
		this.mediaType = mediaType;

		this.source = source;
		this.mediaContentProvider = mediaContentProvider;
	}
	
	public MediaAssetData(Collection<MCodeData> mediaCategory, Collection<MCodeData> mediaType,Collection<MCodeData> source,Collection<MCodeData> oem,Collection<MCodeData> cProvider,Collection<MCodeData> wrappers) {	
		this.mediaId=null;
		this.mediaTitle=null;
		this.mediaImage=null;
		this.mediaRating=null;
		this.eventId=null;
		this.mediaCategory = mediaCategory;
		this.mediaType = mediaType;
		this.source = source;
		this.oem = oem;
		this.cProvider = cProvider;
		this.wrappers = wrappers;
	}
	
	
public MediaAssetData(final Long mediaId,final String mediaTitle,final String image,final BigDecimal rating, Long eventId, String assetTag){
	this.mediaId=mediaId;
	this.mediaTitle=mediaTitle;
	this.mediaImage=image;
	this.mediaRating=rating;
	this.eventId=eventId;
	this.assetTag=assetTag;
}
public MediaAssetData(Long noOfPages, Long pageNo) {
	this.mediaId=null;
	this.mediaTitle=null;
	this.mediaImage=null;
	this.mediaRating=null;
	this.eventId=null;
	this.noOfPages=noOfPages;
	this.pageNo=pageNo;
}
public MediaAssetData(MediaAssetData mediaAssetData, List<MediaassetAttributeData> mediaassetAttributes, List<MediaLocationData> mediaLocationData, List<EnumOptionData> status,List<MediaassetAttribute> data, List<MediaassetAttribute> mediaFormat,
		List<MediaEnumoptionData> mediaTypeData, List<McodeData> mediaCategeorydata,List<McodeData> mediaLangauagedata) {

	this.mediaAssetData=mediaAssetData;
	this.mediaStatus=status;
	this.mediaAttributes=data;
	this.mediaFormat=mediaFormat;
	this.mediaId=null;
	this.mediaTitle=null;
	this.mediaImage=null;
	this.mediaRating=null;
	this.eventId=null;
	this.mediaTypeData=mediaTypeData;
	this.mediaCategeorydata=mediaCategeorydata;
	this.mediaLanguageData=mediaLangauagedata;
	this.mediaLocationData=mediaLocationData;
	this.mediaassetAttributes=mediaassetAttributes;
	
}

public MediaAssetData(MediaAssetData mediaAssetData, List<MediaassetAttributeData> mediaassetAttributes, List<MediaLocationData> mediaLocationData, List<EnumOptionData> status,List<MediaassetAttribute> data, List<MediaassetAttribute> mediaFormat,
		List<MediaEnumoptionData> mediaTypeData, List<McodeData> mediaCategeorydata,List<McodeData> mediaLangauagedata, List<SettlementData> settlementData) {

	this.mediaAssetData=mediaAssetData;
	this.mediaStatus=status;
	this.mediaAttributes=data;
	this.mediaFormat=mediaFormat;
	this.mediaId=null;
	this.mediaTitle=null;
	this.mediaImage=null;
	this.mediaRating=null;
	this.eventId=null;
	this.mediaTypeData=mediaTypeData;
	this.mediaCategeorydata=mediaCategeorydata;
	this.mediaLanguageData=mediaLangauagedata;
	this.mediaLocationData=mediaLocationData;
	this.mediaassetAttributes=mediaassetAttributes;
	this.settlementData = settlementData;
	
}

public MediaAssetData(Long mediaId, String mediaTitle, String status,
		LocalDate releaseDate, BigDecimal rating) {
          this.mediaId=mediaId;
          this.mediaTitle=mediaTitle;
          this.status=status;
          this.releaseDate=releaseDate;
          this.mediaRating=rating;
      	this.mediaImage=null;
      	this.eventId=null;
}

public MediaAssetData(Long mediaId, String mediatitle, String type,
		String genre, Long catageoryId, LocalDate releaseDate, String subject,
		String overview, String image, Long contentProvider, String rated,
		BigDecimal rating, Long ratingCount, String status, String duration,String mediaCategoryG) {
	// TODO Auto-generated constructor stub
	 this.mediaId=mediaId;
     this.mediaTitle=mediatitle;
     this.mediatype=type;
     this.genre=genre;
     this.catageoryId=catageoryId;
     this.releaseDate=releaseDate;
     this.subject=subject;
     this.overview=overview;
     this.mediaImage=image;
     this.contentProvider=contentProvider;
     this.rated=rated;
     this.mediaRating=rating;
     this.rating=rating;
     this.ratingCount=ratingCount;
     this.duration=duration;
     this.status=status;   
 	 this.eventId=null;
 	 this.mediaCategoryG = mediaCategoryG;
}



public MediaAssetData(Long mediaId, String mediaTitle, String status,
		LocalDate releaseDate, BigDecimal rating, String mediaCategory) {

	
	this.mediaId=mediaId;
    this.mediaTitle=mediaTitle;
    this.status=status;
    this.releaseDate=releaseDate;
    this.mediaRating=rating;
	this.mediaImage=null;
	this.eventId=null;
	this.mediaCategoryG = mediaCategory;
}

public MediaAssetData(Long mediaId, String mediaTitle, String status,
		LocalDate releaseDate, BigDecimal rating, String mediaCategory,final String partnerName) {

	
	this.mediaId=mediaId;
    this.mediaTitle=mediaTitle;
    this.status=status;
    this.releaseDate=releaseDate;
    this.mediaRating=rating;
	this.mediaImage=null;
	this.eventId=null;
	this.mediaCategoryG = mediaCategory;
	this.partnerName = partnerName;
}


public MediaAssetData(Collection<MCodeData> mCProvider) {
	this.mCProvider = mCProvider;
}


public MediaAssetData(Collection<MCodeData> mediaCategory,
		Collection<MCodeData> mediaType, Collection<MCodeData> source,
		List<MCodeData> mediaContentProvider,
		Collection<GameMediaDetailsData> contentProviderName) {
	
	this.mediaCategory = mediaCategory;
	this.mediaType = mediaType;
	this.source = source;
	this.mediaContentProvider = mediaContentProvider;
	this.contentProviderName = contentProviderName;
	
	
	
}
public Long getMediaId() {
	return mediaId;
}
public String getMediaTitle() {
	return mediaTitle;
}
public String getMediaImage() {
	return mediaImage;
}
public BigDecimal getMediaRating() {
	return mediaRating;
}
public Long getEventId() {
	return eventId;
}


public void setSettlementData(List<SettlementData> settlementData) {
	this.settlementData = settlementData;
	
}


public void setMediaCategory(Collection<MCodeData> mediaCategory) {
	this.mediaCategory = mediaCategory;
	
}


public void setMediaType(Collection<MCodeData> mediaType) {
	this.mediaType = mediaType;
	
}


public void setMCProvider(Collection<MCodeData> mCProvider) {
	this.mCProvider = mCProvider;
	
}


public void setSource(Collection<MCodeData> source) {
	this.source = source;
	
}


public Long getNoOfPages() {
	return noOfPages;
}


public void setNoOfPages(Long noOfPages) {
	this.noOfPages = noOfPages;
}


public Long getPageNo() {
	return pageNo;
}


public void setPageNo(Long pageNo) {
	this.pageNo = pageNo;
}


public String getAssetTag() {
	return assetTag;
}


public void setAssetTag(String assetTag) {
	this.assetTag = assetTag;
}


public List<EnumOptionData> getMediaStatus() {
	return mediaStatus;
}


public void setMediaStatus(List<EnumOptionData> mediaStatus) {
	this.mediaStatus = mediaStatus;
}


public List<MediaassetAttribute> getMediaAttributes() {
	return mediaAttributes;
}


public void setMediaAttributes(List<MediaassetAttribute> mediaAttributes) {
	this.mediaAttributes = mediaAttributes;
}


public List<MediaassetAttribute> getMediaFormat() {
	return mediaFormat;
}


public void setMediaFormat(List<MediaassetAttribute> mediaFormat) {
	this.mediaFormat = mediaFormat;
}


public String getStatus() {
	return status;
}


public void setStatus(String status) {
	this.status = status;
}


public LocalDate getReleaseDate() {
	return releaseDate;
}


public void setReleaseDate(LocalDate releaseDate) {
	this.releaseDate = releaseDate;
}


public List<MediaEnumoptionData> getMediaTypeData() {
	return mediaTypeData;
}


public void setMediaTypeData(List<MediaEnumoptionData> mediaTypeData) {
	this.mediaTypeData = mediaTypeData;
}


public List<McodeData> getMediaCategeorydata() {
	return mediaCategeorydata;
}


public void setMediaCategeorydata(List<McodeData> mediaCategeorydata) {
	this.mediaCategeorydata = mediaCategeorydata;
}


public List<McodeData> getMediaLanguageData() {
	return mediaLanguageData;
}


public void setMediaLanguageData(List<McodeData> mediaLanguageData) {
	this.mediaLanguageData = mediaLanguageData;
}


public MediaAssetData getMediaAssetData() {
	return mediaAssetData;
}


public void setMediaAssetData(MediaAssetData mediaAssetData) {
	this.mediaAssetData = mediaAssetData;
}


public List<MediaLocationData> getMediaLocationData() {
	return mediaLocationData;
}


public void setMediaLocationData(List<MediaLocationData> mediaLocationData) {
	this.mediaLocationData = mediaLocationData;
}


public List<MediaassetAttributeData> getMediaassetAttributes() {
	return mediaassetAttributes;
}


public void setMediaassetAttributes(
		List<MediaassetAttributeData> mediaassetAttributes) {
	this.mediaassetAttributes = mediaassetAttributes;
}


public String getMediatype() {
	return mediatype;
}


public void setMediatype(String mediatype) {
	this.mediatype = mediatype;
}


public String getGenre() {
	return genre;
}


public void setGenre(String genre) {
	this.genre = genre;
}


public Long getCatageoryId() {
	return catageoryId;
}


public void setCatageoryId(Long catageoryId) {
	this.catageoryId = catageoryId;
}


public String getSubject() {
	return subject;
}


public void setSubject(String subject) {
	this.subject = subject;
}


public String getOverview() {
	return overview;
}


public void setOverview(String overview) {
	this.overview = overview;
}


public Long getContentProvider() {
	return contentProvider;
}


public void setContentProvider(Long contentProvider) {
	this.contentProvider = contentProvider;
}


public String getRated() {
	return rated;
}


public void setRated(String rated) {
	this.rated = rated;
}


public BigDecimal getRating() {
	return rating;
}


public void setRating(BigDecimal rating) {
	this.rating = rating;
}


public String getDuration() {
	return duration;
}


public void setDuration(String duration) {
	this.duration = duration;
}


public Long getRatingCount() {
	return ratingCount;
}


public void setRatingCount(Long ratingCount) {
	this.ratingCount = ratingCount;
}


public String getMediaCategoryG() {
	return mediaCategoryG;
}


public void setMediaCategoryG(String mediaCategoryG) {
	this.mediaCategoryG = mediaCategoryG;
}


public Collection<MCodeData> getMediaCategory() {
	return mediaCategory;
}


public Collection<MCodeData> getMCProvider() {
	return mCProvider;
}


public Collection<MCodeData> getMediaType() {
	return mediaType;
}


public List<SettlementData> getSettlementData() {
	return settlementData;
}


public Collection<MCodeData> getSource() {
	return source;
}


public Collection<MCodeData> getRevenueHolders() {
	return revenueHolders;
}


public void setRevenueHolders(Collection<MCodeData> revenueHolders) {
	this.revenueHolders = revenueHolders;
}
public Collection<GameMediaDetailsData> getContentProviderName() {
	return contentProviderName;
}
public void setContentProviderName(
		Collection<GameMediaDetailsData> contentProviderName) {
	this.contentProviderName = contentProviderName;
}
public void setPartnerName(String partnerName) {
	this.partnerName = partnerName;
	
}
	

}
