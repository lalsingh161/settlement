package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="bp_operator_deduction")
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

	
	
}
