package org.mifosplatform.billing.mediasettlement.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public class MediaSettlementCommandFromApiJsonDeserializer {

	
    private final static Set<String> supportedParameters = new HashSet<String>(Arrays.asList("partnerName","partnerType","mediaCategory",
    		"partnerAddress","gamePlaySource","gameMediaPartnerData","game","gDate","royaltySequence",
    		"royaltyValue","overwriteRoyaltyValue","playSource","price","sequence","locale",
    		"id","chData","deductionCodes","deductionData","clientId","deductionValue","deductionCode","country",
    		"settlementSequenceData","partnerType1","partnerType2","partnerType3","partnerType4",
    		"partnerType5","partnerType6","royaltyType","currencyId","currencyCode","eventId","playSource","contentName",
    		"contentProvider","channelName","serviceName","endUserPrice","downloads","grossRevenue","activeData","externalId","contactNum","emailId"));
    
    private final static Set<String> supportedParametersforGameEvent = new HashSet<String>(Arrays.asList("locale","dateFormat",
    		"clientId","circle","externalId","activityMonth","businessLine","mediaCategory","contentName",
    		"chargeCode","dataUploadedDate","cId","activeData","sourceCurrency","targetCurrency","exchangeRate","startDate","endDate"));
	
    private final static Set<String> supportedParametersforRevenue = new HashSet<String>(
			Arrays.asList("locale", "businessLine", "mediaCategory",
					"revenueShareType", "startValue", "endValue", "percentage",
					"flat", "percentageParams","clientId"));
    
    private final static Set<String> supportedParametersForUpdateDeductionCode = new HashSet<String>(Arrays.asList("deductionCode","deductionValue","locale","clientId"));
    
    private final FromJsonHelper fromApiJsonHelper;
	

    @Autowired
    public MediaSettlementCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }

    public void validateForCreate(final String json) {
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");

        final JsonElement element = fromApiJsonHelper.parse(json);
        
        
        /*final String partnerType = fromApiJsonHelper.extractStringNamed("partnerType", element);
        baseDataValidator.reset().parameter("partnerType").value(partnerType).notBlank().notExceedingLengthOf(50);
        
        final String mediaCategory = fromApiJsonHelper.extractStringNamed("mediaCategory", element);
        baseDataValidator.reset().parameter("mediaCategory").value(mediaCategory).notBlank().notExceedingLengthOf(50);*/
        
        final String partnerName = fromApiJsonHelper.extractStringNamed("partnerName", element);
        baseDataValidator.reset().parameter("partnerName").value(partnerName).notBlank().notExceedingLengthOf(100);
        
        final String partnerAddress = fromApiJsonHelper.extractStringNamed("partnerAddress",element);
        baseDataValidator.reset().parameter("partnerAddress").value(partnerAddress).notBlank().notExceedingLengthOf(255);
        
        final BigDecimal externalId = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("externalId",element);
        baseDataValidator.reset().parameter("externalId").value(externalId).notBlank();
        
        final String contactNum = fromApiJsonHelper.extractStringNamed("contactNum",element);
        baseDataValidator.reset().parameter("contactNum").value(contactNum).notBlank().notExceedingLengthOf(11);
        
        final String emailId = fromApiJsonHelper.extractStringNamed("emailId",element);
        baseDataValidator.reset().parameter("emailId").value(emailId).notBlank().notExceedingLengthOf(100);
        
        
        if(fromApiJsonHelper.parameterExists("currencyCode", element)){
        	final BigDecimal currencyCode = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("currencyCode",element);
            baseDataValidator.reset().parameter("currencyCode").value(currencyCode).notBlank().notExceedingLengthOf(255);
        }
        
        /*final String country = fromApiJsonHelper.extractStringNamed("country", element);
        baseDataValidator.reset().parameter("country").value(country).notBlank().notExceedingLengthOf(50);*/
        
        if(fromApiJsonHelper.parameterExists("currencyId", element)){
        final Long currencyId = fromApiJsonHelper.extractLongNamed("currencyId",element);
        baseDataValidator.reset().parameter("currencyId").value(currencyId).notBlank();
        }
        
        if(fromApiJsonHelper.parameterExists("chData", element)){
        	
        	final JsonArray chDataArray = fromApiJsonHelper.extractJsonArrayNamed("chData",element);
        	
        	
        	
        	 String[] chStringArray = new String[chDataArray.size()];
        	 /*int chStringArraySize = chDataArray.size();*/
    	     /*baseDataValidator.reset().parameter(null).value(chStringArraySize).integerGreaterThanZero();*/
        	
    	     for(int i=0; i<chDataArray.size();i++){
    	    	 chStringArray[i] = chDataArray.get(i).toString();
     	     }
        	
        	for(String s: chStringArray){
        		
        		final JsonElement el = fromApiJsonHelper.parse(s);
        		final String channelPartnerName = fromApiJsonHelper.extractStringNamed("channelPartnerName", el);
        		baseDataValidator.reset().parameter("channelPartnerName").value(channelPartnerName).notBlank();
        		
        		/*final String channelPartnerAddress = fromApiJsonHelper.extractStringNamed("channelPartnerAddress", el);
        		baseDataValidator.reset().parameter("channelPartnerAddress").value(channelPartnerAddress).notBlank();
        		throwExceptionIfValidationWarningsExist(dataValidationErrors);*/
        	}
        }
        
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }


	public void validateForCreatePartnerGame(final String json) {
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");

        final JsonElement element = fromApiJsonHelper.parse(json);
        
        
        final Long partnerName = fromApiJsonHelper.extractLongNamed("partnerName", element);
        baseDataValidator.reset().parameter("partnerName").value(partnerName).notBlank();
        
        final Long gamePlaySource = fromApiJsonHelper.extractLongNamed("gamePlaySource", element);
        baseDataValidator.reset().parameter("gamePlaySource").value(gamePlaySource).notBlank();
        
        final Long royaltyValue = fromApiJsonHelper.extractLongNamed("royaltyValue", element);
        baseDataValidator.reset().parameter("royaltyValue").value(royaltyValue).notBlank();
        
        final Long royaltySequence = fromApiJsonHelper.extractLongNamed("royaltySequence", element);
        baseDataValidator.reset().parameter("royaltySequence").value(royaltySequence).notBlank();
        
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
        
        
          final JsonArray gameMediaPartnerDataArray = fromApiJsonHelper.extractJsonArrayNamed("gameMediaPartnerData",element);
          String[] gameMediaPartnerDataAttributes =null;
          gameMediaPartnerDataAttributes = new String[gameMediaPartnerDataArray.size()];
 	      int gameMediaPartnerDataSize = gameMediaPartnerDataArray.size();
 	      baseDataValidator.reset().parameter(null).value(gameMediaPartnerDataSize).integerGreaterThanZero();
 	    
 	      for(int i=0; i<gameMediaPartnerDataArray.size();i++){
 	    	 gameMediaPartnerDataAttributes[i] = gameMediaPartnerDataArray.get(i).toString();
 	       }
        
 	      for(String singleGameMediaPartnerData: gameMediaPartnerDataAttributes){
 	    	 
 	    	 final JsonElement elements = fromApiJsonHelper.parse(singleGameMediaPartnerData);
 	    	 
 	    	 final LocalDate gDate = fromApiJsonHelper.extractLocalDateNamed("gDate", elements);
		     baseDataValidator.reset().parameter("gDate").value(gDate).notBlank();
		     
		     
		     if(fromApiJsonHelper.parameterExists("game", elements)){
		    	 final String game = fromApiJsonHelper.extractStringNamed("game", elements);
			     baseDataValidator.reset().parameter("game").value(game).notBlank();
		     }else if(fromApiJsonHelper.parameterExists("gameL", elements)){
		    	 final String gameL = fromApiJsonHelper.extractStringNamed("gameL", elements);
			     baseDataValidator.reset().parameter("gameL").value(gameL).notBlank();
		     }
		     
		     
		     final BigDecimal overwriteRoyaltyValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("overwriteRoyaltyValue", elements);
		     baseDataValidator.reset().parameter("overwriteRoyaltyValue").value(overwriteRoyaltyValue).notBlank();
		     
		     final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", elements);
		     baseDataValidator.reset().parameter("playSource").value(playSource).notBlank();
		     
		     final BigDecimal price = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("price", elements);
		     baseDataValidator.reset().parameter("price").value(price).notBlank();
		     
		     final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", elements);
		     baseDataValidator.reset().parameter("sequence").value(sequence).notBlank();
		     
		     throwExceptionIfValidationWarningsExist(dataValidationErrors);
		     
 	      }
    }
	
	

    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }

	public void validateForCreatePartnerRevenue(String json) {

        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

             

        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");

        final JsonElement element = fromApiJsonHelper.parse(json);
        
        
        final Long agreementCategory = fromApiJsonHelper.extractLongNamed("agreementCategory", element);
        baseDataValidator.reset().parameter("agreementCategory").value(agreementCategory).notBlank();
        
       
		     throwExceptionIfValidationWarningsExist(dataValidationErrors);
		     
 	      }

	public void validateForCreateDeduction(String json) {
		
		
		if(StringUtils.isBlank(json)){throw new InvalidJsonException();}
		
		final Type typeOfMap = new TypeToken<Map<String,Object>>(){}.getType();
		fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		
		final List<ApiParameterError> dataValidatorErrors = new ArrayList<ApiParameterError>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidatorErrors).resource("operator.deduction");
		
		final JsonElement element = fromApiJsonHelper.parse(json);
		
		final JsonArray deductionData = fromApiJsonHelper.extractJsonArrayNamed("deductionData",element);
		
		String[] chStringArray = new String[deductionData.size()];
   	 	int chStringArraySize = deductionData.size();
	     /*baseDataValidator.reset().parameter(null).value(chStringArraySize).integerGreaterThanZero();*/
   	
	     for(int i=0; i<chStringArraySize;i++){
	    	 chStringArray[i] = deductionData.get(i).toString();
	     }
   	
	     final Long clientId = fromApiJsonHelper.extractLongNamed("clientId", element);
	     baseDataValidator.reset().parameter("clientId").value(clientId).notBlank();
	     throwExceptionIfValidationWarningsExist(dataValidatorErrors);
	     
   	for(String s: chStringArray){
   		
   		final JsonElement el = fromApiJsonHelper.parse(s);
   		
   		final String deductionCode = fromApiJsonHelper.extractStringNamed("deductionCode", el);
   		baseDataValidator.reset().parameter("deductionCode").value(deductionCode).notBlank();
   		
   		final BigDecimal deductionValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("deductionValue", el);
   		baseDataValidator.reset().parameter("deductionValue").value(deductionValue).notBlank().inMinAndMaxAmountRange(BigDecimal.ZERO, BigDecimal.valueOf(100));
   		
   		throwExceptionIfValidationWarningsExist(dataValidatorErrors);
   	}
		
	}

	public void validateForCreateSettlementSequenceData(String json) {

        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

             

        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");

        final JsonElement element = fromApiJsonHelper.parse(json);
        
        
        final Long partnerType1 = fromApiJsonHelper.extractLongNamed("partnerType1", element);
        baseDataValidator.reset().parameter("partnerType1").value(partnerType1).notNull();
        
        final Long partnerType2 = fromApiJsonHelper.extractLongNamed("partnerType2", element);
        baseDataValidator.reset().parameter("partnerType2").value(partnerType2).notNull();
          
        final Long partnerType3 = fromApiJsonHelper.extractLongNamed("partnerType3", element);
        baseDataValidator.reset().parameter("partnerType3").value(partnerType3).notNull();
          
        
        if(fromApiJsonHelper.parameterExists("settlementSequenceData", element)){
        	
        	final JsonArray ssDataArray = fromApiJsonHelper.extractJsonArrayNamed("settlementSequenceData",element);
        	
        	
        	if(ssDataArray == null){
        		/*throw new PlatformDataIntegrityException("Please select channel partners", "Please select channel partners", "");*/
        	}
        	 String[] ssStringArray = new String[ssDataArray.size()];
        	 
    	     for(int i=0; i<ssDataArray.size();i++){
    	    	 ssStringArray[i] = ssDataArray.get(i).toString();
     	     }
        	
        	for(String s: ssStringArray){
        		
        		final JsonElement el = fromApiJsonHelper.parse(s);
        		final Long partnerType4 = fromApiJsonHelper.extractLongNamed("partnerType4", el);
        		baseDataValidator.reset().parameter("partnerType4").value(partnerType4).notNull();
        		
        		final Long partnerType5 = fromApiJsonHelper.extractLongNamed("partnerType5", el);
        		baseDataValidator.reset().parameter("partnerType5").value(partnerType5).notNull();
        		
        		final Long partnerType6 = fromApiJsonHelper.extractLongNamed("partnerType6", el);
        		baseDataValidator.reset().parameter("partnerType6").value(partnerType6).notNull();
        		
        		/*final String channelPartnerAddress = fromApiJsonHelper.extractStringNamed("channelPartnerAddress", el);
        		baseDataValidator.reset().parameter("channelPartnerAddress").value(channelPartnerAddress).notBlank();
        		throwExceptionIfValidationWarningsExist(dataValidationErrors);*/
        	}
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
            
 	}
		
		public void validateForCreateRoyalty(String json) {
				
		        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }
		
		        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
		        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		
		        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");
		
		        final JsonElement element = fromApiJsonHelper.parse(json);
		        final Long  royaltyType = fromApiJsonHelper.extractLongNamed("royaltyType", element);
		        baseDataValidator.reset().parameter("royaltyType").value(royaltyType).notBlank();
		        throwExceptionIfValidationWarningsExist(dataValidationErrors);
		    }
		
		public void validateForCreateGameEvent(String json){

	        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

	        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
	        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersforGameEvent);

	        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("media.settlement");

	        final JsonElement element = fromApiJsonHelper.parse(json);
	        
	        
	        final BigDecimal clientId = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("clientId", element);
	        baseDataValidator.reset().parameter("clientId").value(clientId).notBlank().notExceedingLengthOf(50);
	        
	        
	        
	        /*final BigDecimal circle = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("circle", element);
	        baseDataValidator.reset().parameter("circle").value(circle).notBlank().notExceedingLengthOf(50);*/
	        
	        final BigDecimal externalId = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("externalId", element);
	        baseDataValidator.reset().parameter("externalId").value(externalId).notBlank().notExceedingLengthOf(50);
	        
	        final BigDecimal activityMonth = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("activityMonth",element);
	        baseDataValidator.reset().parameter("activityMonth").value(activityMonth).notBlank().notExceedingLengthOf(50);
	        
	        final BigDecimal businessLine = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("businessLine", element);
	        baseDataValidator.reset().parameter("businessLine").value(businessLine).notBlank().notExceedingLengthOf(50);
	        
	        final BigDecimal mediaCategory = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("mediaCategory",element);
	        baseDataValidator.reset().parameter("mediaCategory").value(mediaCategory).notBlank().notExceedingLengthOf(50);
	        
	
	        /*final BigDecimal chargeCode = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("chargeCode", element);
	        baseDataValidator.reset().parameter("chargeCode").value(chargeCode).notBlank().notExceedingLengthOf(50);*/
	        
	        /*final LocalDate dataUploadedDate = fromApiJsonHelper.extractLocalDateNamed("dataUploadedDate", element);
	        baseDataValidator.reset().parameter("dataUploadedDate").value(dataUploadedDate).notBlank();*/
	        
	        
	          final JsonArray interactiveDataArray = fromApiJsonHelper.extractJsonArrayNamed("activeData",element);
	          String[] interactiveDataArrayAttributes =null;
	          interactiveDataArrayAttributes = new String[interactiveDataArray.size()];
	 	      int interactiveDataSize = interactiveDataArray.size();
	 	      baseDataValidator.reset().parameter("interactiveDataArray").value(interactiveDataSize).integerGreaterThanZero();
	 	    
	 	      for(int i=0; i<interactiveDataArray.size();i++){
	 	    	 interactiveDataArrayAttributes[i] = interactiveDataArray.get(i).toString();
	 	       }
	        
	 	      for(String singleinteractiveData: interactiveDataArrayAttributes){
	 	    	 
	 	    	 final JsonElement elements = fromApiJsonHelper.parse(singleinteractiveData);
	 	    	 
	 	    	final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", elements);
		        baseDataValidator.reset().parameter("playSource").value(playSource).notBlank();
	 	    	
		        final String contentName = fromApiJsonHelper.extractStringNamed("contentName", elements);
		        baseDataValidator.reset().parameter("contentName").value(contentName).notBlank();
		        
		        final Long contentProvider = fromApiJsonHelper.extractLongNamed("contentProvider", elements);
		        baseDataValidator.reset().parameter("contentProvider").value(contentProvider).notBlank();
		       
		        final Long channelName = fromApiJsonHelper.extractLongNamed("channelName", elements);
		        baseDataValidator.reset().parameter("channelName").value(channelName).notBlank();
		        
		        final Long serviceName = fromApiJsonHelper.extractLongNamed("serviceName", elements);
		        baseDataValidator.reset().parameter("serviceName").value(serviceName).notBlank();
		        
		        final BigDecimal endUserPrice = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("endUserPrice", elements);
		        baseDataValidator.reset().parameter("endUserPrice").value(endUserPrice).notBlank();
		        
		        final BigDecimal grossRevenue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("grossRevenue", elements);
		        baseDataValidator.reset().parameter("grossRevenue").value(grossRevenue).notBlank();
		        
		        final Integer downloads = fromApiJsonHelper.extractIntegerWithLocaleNamed("downloads", elements);
		        baseDataValidator.reset().parameter("downloads").value(downloads).notBlank().integerGreaterThanZero();   
		      		     
			    // final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", elements);
			     //baseDataValidator.reset().parameter("sequence").value(sequence).notBlank();
			     
			     throwExceptionIfValidationWarningsExist(dataValidationErrors);
	 	      }
	        
	        
	        throwExceptionIfValidationWarningsExist(dataValidationErrors);
	    
		}

		public void validateForCreateInteractive(String json) {
		
		
		   final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
	        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

	        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("interactivedetail");

	        final JsonElement element = fromApiJsonHelper.parse(json);
	        

	          final JsonArray interactiveDataArray = fromApiJsonHelper.extractJsonArrayNamed("activeData",element);
	          String[] interactiveDataArrayAttributes =null;
	          interactiveDataArrayAttributes = new String[interactiveDataArray.size()];
	 	      int interactiveDataSize = interactiveDataArray.size();
	 	      baseDataValidator.reset().parameter(null).value(interactiveDataSize).integerGreaterThanZero();
	 	    
	 	      for(int i=0; i<interactiveDataArray.size();i++){
	 	    	 interactiveDataArrayAttributes[i] = interactiveDataArray.get(i).toString();
	 	       }
	        
	 	      for(String singleinteractiveData: interactiveDataArrayAttributes){
	 	    	 
	 	    	 final JsonElement elements = fromApiJsonHelper.parse(singleinteractiveData);
	 	    	 
	 	    	final Long playSource = fromApiJsonHelper.extractLongNamed("playSource", elements);
		        baseDataValidator.reset().parameter("playSource").value(playSource).notBlank();
	 	    	
		        final String contentName = fromApiJsonHelper.extractStringNamed("contentName", elements);
		        baseDataValidator.reset().parameter("contentName").value(contentName).notBlank();
		        
		        final Long contentProvider = fromApiJsonHelper.extractLongNamed("contentProvider", elements);
		        baseDataValidator.reset().parameter("contentProvider").value(contentProvider).notBlank();
		       
		        final Long channelName = fromApiJsonHelper.extractLongNamed("channelName", elements);
		        baseDataValidator.reset().parameter("channelName").value(channelName).notBlank();
		        
		        final Long serviceName = fromApiJsonHelper.extractLongNamed("serviceName", elements);
		        baseDataValidator.reset().parameter("serviceName").value(serviceName).notBlank();
		        
		        final BigDecimal endUserPrice = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("endUserPrice", elements);
		        baseDataValidator.reset().parameter("endUserPrice").value(endUserPrice).notBlank();
		        
		        final BigDecimal grossRevenue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("grossRevenue", elements);
		        baseDataValidator.reset().parameter("grossRevenue").value(grossRevenue).notBlank();
		        
		        final Long downloads = fromApiJsonHelper.extractLongNamed("downloads", elements);
		        baseDataValidator.reset().parameter("downloads").value(downloads).notBlank();   
		      		     
			    // final Long sequence = fromApiJsonHelper.extractLongNamed("sequence", elements);
			     //baseDataValidator.reset().parameter("sequence").value(sequence).notBlank();
			     
			     throwExceptionIfValidationWarningsExist(dataValidationErrors);
	 	      }
	}

		public void validateForCreateRevenue(String json) {
			
			
			   final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
		        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersforRevenue);

		        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("revenueShare");

		        final JsonElement element = fromApiJsonHelper.parse(json);
		        
		        final Long businessLine = fromApiJsonHelper.extractLongNamed("businessLine", element);
		        baseDataValidator.reset().parameter("businessLine").value(businessLine).notBlank();
		        
		        final Long mediaCategory = fromApiJsonHelper.extractLongNamed("mediaCategory", element);
		        baseDataValidator.reset().parameter("mediaCategory").value(mediaCategory).notBlank();
		        
		        final Long clientId = fromApiJsonHelper.extractLongNamed("clientId",element);
		        baseDataValidator.reset().parameter("clientId").value(clientId).notBlank();
		        
		        
		        final Long revenueShareType = fromApiJsonHelper.extractLongNamed("revenueShareType", element);
		        baseDataValidator.reset().parameter("revenueShareType").value(revenueShareType).notBlank();

		          final JsonArray percentageParamsDataArray = fromApiJsonHelper.extractJsonArrayNamed("percentageParams",element);
		          
		          if(percentageParamsDataArray!=null && percentageParamsDataArray.size()>0){
		        	    String[] percentageParamsDataArrayAttributes =null;
				          percentageParamsDataArrayAttributes = new String[percentageParamsDataArray.size()];
				 	      int interactiveDataSize = percentageParamsDataArray.size();
				 	      baseDataValidator.reset().parameter(null).value(interactiveDataSize).integerGreaterThanZero();
				 	    
				 	      for(int i=0; i<percentageParamsDataArray.size();i++){
				 	    	 percentageParamsDataArrayAttributes[i] = percentageParamsDataArray.get(i).toString();
				 	       }
				        
				 	      for(String singleinteractiveData: percentageParamsDataArrayAttributes){
				 	    	 
				 	    	 final JsonElement elements = fromApiJsonHelper.parse(singleinteractiveData);
				 	    	 
				 	    	final Long startValue = fromApiJsonHelper.extractLongNamed("startValue", elements);
					        baseDataValidator.reset().parameter("startValue").value(startValue).notBlank();
				 	    	
					        final Long endValue = fromApiJsonHelper.extractLongNamed("endValue", elements);
					        baseDataValidator.reset().parameter("endValue").value(endValue).notBlank();
					        
					        final BigDecimal percentage = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("percentage", elements);
					        baseDataValidator.reset().parameter("percentage").value(percentage).notBlank();
					       
						     throwExceptionIfValidationWarningsExist(dataValidationErrors);
				 	      }
		          }else{
		        	  final BigDecimal flat = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("flat", element);
				       baseDataValidator.reset().parameter("flat").value(flat).notBlank();
				        
		        	  throwExceptionIfValidationWarningsExist(dataValidationErrors);
		          }
		      
		}
		
		public void validateForCreateCurrency(String json) {
			
			   final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
		        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersforGameEvent);

		        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("currency");

		        final JsonElement element = fromApiJsonHelper.parse(json);
			
		        final Long sourceCurrency = fromApiJsonHelper.extractLongNamed("sourceCurrency", element);
		        baseDataValidator.reset().parameter("sourceCurrency").value(sourceCurrency).notBlank();
		        
		        final Long targetCurrency = fromApiJsonHelper.extractLongNamed("targetCurrency", element);
		        baseDataValidator.reset().parameter("targetCurrency").value(targetCurrency).notBlank();
		        
		        final BigDecimal exchangeRate = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("exchangeRate", element);
		        baseDataValidator.reset().parameter("exchangeRate").value(exchangeRate).notBlank().notExceedingLengthOf(50); 
		        
		        final LocalDate startDate = fromApiJsonHelper.extractLocalDateNamed("startDate", element);
		        baseDataValidator.reset().parameter("startDate").value(startDate).notBlank();
		        
		        final LocalDate endDate = fromApiJsonHelper.extractLocalDateNamed("endDate", element);
		        baseDataValidator.reset().parameter("endDate").value(endDate).notBlank();
		        
		        throwExceptionIfValidationWarningsExist(dataValidationErrors);
		}

		public void validateForUpdateDeductionCode(String json) {
			final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
	        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersForUpdateDeductionCode);

	        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("currency");

	        final JsonElement element = fromApiJsonHelper.parse(json);
		
	        final String deductionCode = fromApiJsonHelper.extractStringNamed("deductionCode", element);
	        baseDataValidator.reset().parameter("deductionCode").value(deductionCode).notBlank();
	        
	        final BigDecimal deductionValue = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("deductionValue", element);
	        baseDataValidator.reset().parameter("deductionValue").value(deductionValue).notBlank(); 
	        
	        throwExceptionIfValidationWarningsExist(dataValidationErrors);
		}
		
	}

