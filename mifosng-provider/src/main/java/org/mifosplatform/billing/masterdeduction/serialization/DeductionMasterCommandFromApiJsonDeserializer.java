package org.mifosplatform.billing.masterdeduction.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public class DeductionMasterCommandFromApiJsonDeserializer {

    private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList(
    		"deductionCode","deductionName","deductionType","levelApplicable","circle","business"));
    private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public DeductionMasterCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
	
    public void validateForCreateMasterDeduction(String json) {
		
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("Master");

        final JsonElement element = fromApiJsonHelper.parse(json);

        final String deductionCode = fromApiJsonHelper.extractStringNamed("deductionCode", element);
        baseDataValidator.reset().parameter("deductionCode").value(deductionCode).notBlank();
        
        final String deductionName = fromApiJsonHelper.extractStringNamed("deductionName", element);
        baseDataValidator.reset().parameter("deductionName").value(deductionName).notBlank();
        
        final Long  deductionType = fromApiJsonHelper.extractLongNamed("deductionType", element);
        baseDataValidator.reset().parameter("deductionType").value(deductionType).notBlank();
        
        final Long  levelApplicable = fromApiJsonHelper.extractLongNamed("levelApplicable", element);
        baseDataValidator.reset().parameter("levelApplicable").value(levelApplicable).notBlank();
        
      //  final String circle = fromApiJsonHelper.extractStringNamed("circle", element);
       // baseDataValidator.reset().parameter("circle").value(circle).notBlank();
        

      //  final Long customerType = fromApiJsonHelper.extractLongNamed("customerType", element);
       // baseDataValidator.reset().parameter("customerType").value(customerType).notBlank();
        
        final Long business = fromApiJsonHelper.extractLongNamed("business", element);
        baseDataValidator.reset().parameter("business").value(business).notBlank();
        
        
        
        
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

	

    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }
	
}
