package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name="bp_currency_rate")
public class CurrencyRate extends AbstractAuditableCustom<AppUser, Long> {

	@Column(name ="source_currency",nullable=false)
	private Long sourceCurrency;
	
	@Column(name ="target_currency",nullable=false)
	private Long targetCurrency;
	
	@Column(name = "exchange_rate",nullable=false)
	private BigDecimal exchangeRate;
	
	@Column(name ="e_start_date",nullable=false)
	private Date startDate;	
	
	@Column(name ="e_end_date",nullable=false)
	private Date endDate;
	
	@Column(name="is_deleted")
	private Character isDeleted='N';
	
	public CurrencyRate(){
		
	}

	public CurrencyRate(Long sourceCurrency, Long targetCurrency,
			BigDecimal exchangeRate, LocalDate startDate, LocalDate endDate) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.exchangeRate = exchangeRate;
		this.startDate = startDate.toDate();
		this.endDate = endDate.toDate();
	}

	public static CurrencyRate fromjson(final JsonCommand command) {
		
		final Long sourceCurrency = command.longValueOfParameterNamed("sourceCurrency");
		final Long targetCurrency = command.longValueOfParameterNamed("targetCurrency");
		final BigDecimal exchangeRate = command.bigDecimalValueOfParameterNamed("exchangeRate");
		final LocalDate startDate = command.localDateValueOfParameterNamed("startDate");
		final LocalDate endDate = command.localDateValueOfParameterNamed("endDate");
		
		return new CurrencyRate(sourceCurrency,targetCurrency,exchangeRate,startDate,endDate);
	}

	public Long getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(Long sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public Long getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Long targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void delete() {
		if(this.isDeleted=='N')
		{
			this.isDeleted='Y';
	}else{}
	
	}
	
}
