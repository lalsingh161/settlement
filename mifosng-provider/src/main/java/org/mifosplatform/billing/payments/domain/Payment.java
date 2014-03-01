package org.mifosplatform.billing.payments.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@SuppressWarnings("serial")
@Entity
@Table(name = "b_payments")
public class Payment extends AbstractAuditableCustom<AppUser, Long> {

	@Column(name = "client_id", nullable = false)
	private Long clientId;

	@Column(name = "amount_paid", scale = 6, precision = 19, nullable = false)
	private BigDecimal amountPaid;

	@Column(name = "bill_id", nullable = false)
	private Long statementId;

	@Column(name = "is_deleted", nullable = false)
	private boolean deleted = false;

	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "paymode_id")
	private int paymode_id;
	

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "invoice_id", nullable = false)
	private Long invoiceId;


	public Payment() {
	}

	public Payment(final Long clientId, final Long paymentId,final Long externalId, final BigDecimal amountPaid,final Long statmentId, final LocalDate paymentDate,
			final String remark, final Long paymodeCode, String transId,Long invoiceId) {

		this.clientId = clientId;

		this.statementId = statmentId;
		this.amountPaid = amountPaid;
		this.paymentDate = paymentDate.toDate();
		this.remarks = remark;
		this.paymode_id = paymodeCode.intValue();
		this.transactionId=transId;
		this.invoiceId=invoiceId;

	}

	public static Payment fromJson(JsonCommand command, Long clientid) {
		final LocalDate paymentDate = command.localDateValueOfParameterNamed("paymentDate");
		final Long paymentCode = command.longValueOfParameterNamed("paymentCode");
				
		final BigDecimal amountPaid = command.bigDecimalValueOfParameterNamed("amountPaid");
		final String remarks = command.stringValueOfParameterNamed("remarks");
		final String txtid=command.stringValueOfParameterNamed("txn_id");
		final Long invoiceId=command.longValueOfParameterNamed("invoiceId");
		return new Payment(clientid, null, null, amountPaid, null, paymentDate,
				remarks, paymentCode,txtid,invoiceId);

	}

	public Long getClientId() {
		return clientId;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public Long getStatementId() {
		return statementId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getPaymodeCode() {
		return paymode_id;
	}

		public void updateBillId(Long billId) {
		this.statementId=billId;

	}

		public Long getInvoiceId() {
			return invoiceId;
		}

		public int getPaymode_id() {
			return paymode_id;
		}

		public void setPaymode_id(int paymode_id) {
			this.paymode_id = paymode_id;
		}

		public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

		public void setClientId(Long clientId) {
			this.clientId = clientId;
		}

		public void setAmountPaid(BigDecimal amountPaid) {
			this.amountPaid = amountPaid;
		}

		public void setStatementId(Long statementId) {
			this.statementId = statementId;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public void setInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
		}


}
