package org.mifosplatform.billing.revenuemaster.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mifosplatform.billing.billingorder.domain.BillingOrder;
import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.billingorder.domain.InvoiceTax;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name = "ia_charge_ded")
public class DeductionTax extends AbstractPersistable<Long>{
	
	
	/*@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;*/
	
	@ManyToOne
	@JoinColumn(name = "charge_id", insertable = true, updatable = true, nullable = true, unique = true)
	private BillingOrder charge;

	@ManyToOne
	@JoinColumn(name = "invoice_id", insertable = true, updatable = true, nullable = true, unique = true)
	private Invoice invoice;

	
	@Column(name = "detail_id")
	private Long detailId;

	
	@Column(name = "ded_code")
	private String deductionCode;
/*
	@Column(name = "ded_value")
	private BigDecimal deductionValue;*/

	@Column(name = "ded_percentage")
	private BigDecimal deductionPercentage;

	@Column(name = "ded_amount")
	private BigDecimal deductionTaxAmount;
	
	@Column(name = "bill_id")
	private Long billId;
	
	
	private DeductionTax(){
		
	}
	
	public DeductionTax(final Invoice invoice, final BillingOrder charge,final Long detailId,
			final String deductionCode, final BigDecimal deductionValue,
			 final BigDecimal deductionTaxAmount) {

		this.charge = charge;
		this.invoice = invoice;
		this.detailId = detailId;
		this.deductionCode = deductionCode;
		this.deductionPercentage = deductionValue;
		this.deductionTaxAmount = deductionTaxAmount;
	}

	public BillingOrder getCharge() {
		return charge;
	}

/*	public void setCharge(BillingOrder charge) {
		this.charge = charge;
	}*/

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getDeductionCode() {
		return deductionCode;
	}

	public void setDeductionCode(String deductionCode) {
		this.deductionCode = deductionCode;
	}

	public BigDecimal getDeductionPercentage() {
		return deductionPercentage;
	}

	public void setDeductionPercentage(BigDecimal deductionPercentage) {
		this.deductionPercentage = deductionPercentage;
	}

	public BigDecimal getDeductionTaxAmount() {
		return deductionTaxAmount;
	}

	public void setDeductionTaxAmount(BigDecimal deductionTaxAmount) {
		this.deductionTaxAmount = deductionTaxAmount;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public void update(BillingOrder charge) {
		
		this.charge=charge;
	}

	public Long getDetailId() {
		return detailId;
	}
	

}
