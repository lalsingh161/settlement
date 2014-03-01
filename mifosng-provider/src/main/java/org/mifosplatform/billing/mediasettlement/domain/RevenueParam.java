package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name = "bp_revenue_share_params")
public class RevenueParam extends AbstractAuditableCustom<AppUser, Long> {

	@ManyToOne
	@JoinColumn(name = "revenue_share_master_id")
	private RevenueMaster revenueMaster;

	@Column(name = "start_value")
	private Long startValue;

	@Column(name = "end_value")
	private Long endValue;

	@Column(name = "percentage")
	private BigDecimal percentage;

	@Column(name = "flat")
	private BigDecimal flat;

	public RevenueParam() {
	}

	public RevenueParam(Long startValue, Long endValue, BigDecimal percentage,
			BigDecimal flat) {

		this.startValue = startValue;
		this.endValue = endValue;
		this.percentage = percentage;
		this.flat = flat;
	}

	public RevenueParam(Long startValue, Long endValue, BigDecimal percentage) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.percentage = percentage;
	}

	public static RevenueParam fromJson(JsonCommand command) {

		final Long startValue = command.longValueOfParameterNamed("startValue");
		final Long endValue = command.longValueOfParameterNamed("endValue");
		final BigDecimal percentage = command
				.bigDecimalValueOfParameterNamed("percentage");
		final BigDecimal flat = command.bigDecimalValueOfParameterNamed("flat");
		return new RevenueParam(startValue, endValue, percentage, flat);
	}

	public void addRevenueDetails(RevenueMaster revenueMaster) {

		this.revenueMaster = revenueMaster;
	}
	
	public void update(JsonCommand command) {

		final String startValue = "startValue";
		final Long newStartValue = command
				.longValueOfParameterNamed("startValue");
		this.startValue = newStartValue;

		final String endValue = "endValue";
		final Long newEndValue = command.longValueOfParameterNamed("endValue");
		this.endValue = newEndValue;

		final String percentage = "percentage";
		final BigDecimal newpercentage = command
				.bigDecimalValueOfParameterNamed("percentage");
		this.percentage = newpercentage;

		final String flat = "flat";
		final BigDecimal newflat = command
				.bigDecimalValueOfParameterNamed("flat");
		this.flat = newflat;
	}

}