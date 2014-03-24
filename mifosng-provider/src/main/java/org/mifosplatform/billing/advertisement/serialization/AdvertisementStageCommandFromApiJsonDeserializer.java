package org.mifosplatform.billing.advertisement.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


@Component
public class AdvertisementStageCommandFromApiJsonDeserializer {

	 private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList(
	    		"custCode","ba","pc","mpm","invoiceNo","invoiceDate","customerName","customerCircle","activityMonth","business","currency","category","contentName","contentProviderName",
	    		"request","impressions","clicks","clickThroughRatio","eipm","eipc","income","exrtaRate","igRevenueAmount","cpShareAmount","igAfterRoyaltyPayouts","dateFormat","locale"));
	    private final FromJsonHelper fromApiJsonHelper;
	 
	 @Autowired
	 public AdvertisementStageCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
	        this.fromApiJsonHelper = fromApiJsonHelper;
	    }   
	
	 
		public void validateForCreateAdvertisement(String json) {
			
			   final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
		        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

		        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("advertisement");

		        final JsonElement element = fromApiJsonHelper.parse(json);
			
		        final Long custCode = fromApiJsonHelper.extractLongNamed("custCode", element);
		        baseDataValidator.reset().parameter("custCode").value(custCode).notBlank();
		        
		        final String  ba= fromApiJsonHelper.extractStringNamed("ba", element);
		        baseDataValidator.reset().parameter("ba").value(ba).notBlank();
		
		        final Long pc = fromApiJsonHelper.extractLongNamed("pc", element);
		        baseDataValidator.reset().parameter("pc").value(pc).notBlank();
		        
		        final Long mpm = fromApiJsonHelper.extractLongNamed("mpm", element);
		        baseDataValidator.reset().parameter("mpm").value(mpm).notBlank();
		        
		        final String invoiceNo = fromApiJsonHelper.extractStringNamed("invoiceNo", element);
		        baseDataValidator.reset().parameter("invoiceNo").value(invoiceNo).notBlank();
		        
		        final LocalDate invoiceDate = fromApiJsonHelper.extractLocalDateNamed("invoiceDate", element);
		        baseDataValidator.reset().parameter("invoiceDate").value(invoiceDate).notBlank();
		        
		        final String customerName = fromApiJsonHelper.extractStringNamed("customerName", element);
		        baseDataValidator.reset().parameter("customerName").value(customerName).notBlank();
		        
		        final String customerCircle = fromApiJsonHelper.extractStringNamed("customerCircle", element);
		        baseDataValidator.reset().parameter("customerCircle").value(customerCircle).notBlank();
		        
		        final String activityMonth = fromApiJsonHelper.extractStringNamed("activityMonth", element);
		        baseDataValidator.reset().parameter("activityMonth").value(activityMonth).notBlank();
		       
		        final String business = fromApiJsonHelper.extractStringNamed("business", element);
		        baseDataValidator.reset().parameter("business").value(business).notBlank();
		        
		        final String currency = fromApiJsonHelper.extractStringNamed("currency", element);
		        baseDataValidator.reset().parameter("currency").value(currency).notBlank();
		        
		        final String category = fromApiJsonHelper.extractStringNamed("category", element);
		        baseDataValidator.reset().parameter("business").value(category).notBlank();
		        
		        final String contentName = fromApiJsonHelper.extractStringNamed("contentName", element);
		        baseDataValidator.reset().parameter("contentName").value(contentName).notBlank();
		        
		        final String contentProviderName = fromApiJsonHelper.extractStringNamed("contentProviderName", element);
		        baseDataValidator.reset().parameter("contentProviderName").value(contentProviderName).notBlank();
		 
		        final Long request = fromApiJsonHelper.extractLongNamed("request", element);
		        baseDataValidator.reset().parameter("request").value(request).notBlank();
		        
		        final Long impressions = fromApiJsonHelper.extractLongNamed("impressions", element);
		        baseDataValidator.reset().parameter("impressions").value(impressions).notBlank();
		        
		        final Long clicks = fromApiJsonHelper.extractLongNamed("clicks", element);
		        baseDataValidator.reset().parameter("clicks").value(clicks).notBlank();
		        
		        
		        final String clickThroughRatio = fromApiJsonHelper.extractStringNamed("clickThroughRatio", element);
		        baseDataValidator.reset().parameter("clickThroughRatio").value(clickThroughRatio).notBlank();
		        
		        final BigDecimal eipm = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("eipm", element);
		        baseDataValidator.reset().parameter("eipm").value(eipm).notBlank().notExceedingLengthOf(50);
		        
		        final BigDecimal eipc = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("eipc", element);
		        baseDataValidator.reset().parameter("eipc").value(eipc).notBlank().notExceedingLengthOf(50); 
	   
		        final BigDecimal income = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("income", element);
		        baseDataValidator.reset().parameter("income").value(income).notBlank().notExceedingLengthOf(50); 
		      
		        final BigDecimal exrtaRate = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("exrtaRate", element);
		        baseDataValidator.reset().parameter("exrtaRate").value(exrtaRate).notBlank().notExceedingLengthOf(50); 
		        
		        final BigDecimal igRevenueAmount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("igRevenueAmount", element);
		        baseDataValidator.reset().parameter("igRevenueAmount").value(igRevenueAmount).notBlank().notExceedingLengthOf(50); 
		        
		        final BigDecimal cpShareAmount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("cpShareAmount", element);
		        baseDataValidator.reset().parameter("cpShareAmount").value(cpShareAmount).notBlank().notExceedingLengthOf(50); 
		        
		        final BigDecimal igAfterRoyaltyPayouts = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("igAfterRoyaltyPayouts", element);
		        baseDataValidator.reset().parameter("igAfterRoyaltyPayouts").value(igAfterRoyaltyPayouts).notBlank().notExceedingLengthOf(50); 
		        
		        throwExceptionIfValidationWarningsExist(dataValidationErrors);
		}
	 
		  private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
		        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
		                "Validation errors exist.", dataValidationErrors); }
		    }
}
