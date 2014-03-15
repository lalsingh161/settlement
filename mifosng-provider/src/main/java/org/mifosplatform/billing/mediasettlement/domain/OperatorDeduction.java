package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.codes.exception.SystemDefinedCodeCannotBeChangedException;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_operator_deduction" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","ded_code","ded_value"}, name="operatordeductionuniquekey")})
public class OperatorDeduction extends AbstractAuditableCustom<AppUser,Long>{

	
	@Column(name="client_id")
	private Long clientId;
	
	@Column(name="ded_code")
	private String deductionCode;
	
	@Column(name="ded_value")
	private BigDecimal deductionValue;
	
	public OperatorDeduction() {
		
	}
	
	OperatorDeduction(final Long clientId,final BigDecimal deductionValue, final String deductionCode){
		this.clientId = clientId;
		this.deductionCode = deductionCode;
		this.deductionValue = deductionValue;
	}
	
	
	public static OperatorDeduction fromJson(final Long clientId, final String deductionCode, final BigDecimal deductionValue){
		
		return new OperatorDeduction(clientId,deductionValue,deductionCode);
	}

	public Map<String, Object> update(JsonCommand command) {
		final Map<String,Object> actualChanges = new LinkedHashMap<String,Object>(1);
		
        final String firstnameParamName = "deductionCode";
        if (command.isChangeInStringParameterNamed(firstnameParamName, this.deductionCode)) {
            final String newValue = command.stringValueOfParameterNamed(firstnameParamName);
            actualChanges.put(firstnameParamName, newValue);
            this.deductionCode = StringUtils.defaultIfEmpty(newValue, null);
        }
        final String secondParameterName = "deductionValue";
        if (command.isChangeInBigDecimalParameterNamed(secondParameterName, this.deductionValue)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(secondParameterName);
            actualChanges.put(firstnameParamName, newValue);
            this.deductionValue = newValue;
        }

        return actualChanges;
	}

	
	
}
