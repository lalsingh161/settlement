package org.mifosplatform.billing.payments.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.paymode.data.McodeData;

public class PaymentData {
	
    private Collection<McodeData> data;
	private LocalDate paymentDate;
	private String clientName;
	private BigDecimal amountPaid;
	private String payMode;
	private Boolean isDeleted;
	private Long billNumber;
	private String receiptNo;
	private Long id;
	private BigDecimal amount;
	private BigDecimal availAmount;
	
	public PaymentData(Collection<McodeData> data){
		this.data= data;
		this.paymentDate=new LocalDate();
		
	}
	
	
	public PaymentData(String clientName, String payMode,LocalDate paymentDate, BigDecimal amountPaid, Boolean isDeleted, Long billNumber, String receiptNumber) {
		  this.clientName = clientName;
		  this.payMode = payMode;
		  this.paymentDate = paymentDate;
		  this.amountPaid = amountPaid;
		  this.isDeleted = isDeleted;
		  this.billNumber = billNumber;
		  this.receiptNo = receiptNumber;
		 }


	public PaymentData(Long id, LocalDate paymentdate, BigDecimal amount,
			String recieptNo, BigDecimal availAmount, String receiptNo) {
		this.id = id;
		this.paymentDate = paymentdate;
		this.amount = amount;
		this.receiptNo = receiptNo;
		this.availAmount = availAmount;
	}


	public PaymentData(Long id, LocalDate paymentdate, BigDecimal amount,
			String recieptNo, BigDecimal availAmount) {
		this.id = id;
		this.paymentDate = paymentdate;
		this.amount = amount;
		this.receiptNo = recieptNo;
		this.availAmount = availAmount;
	}


	public Collection<McodeData> getData() {
		return data;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public String getClientName() {
		return clientName;
	}


	public BigDecimal getAmountPaid() {
		return amountPaid;
	}


	public String getPayMode() {
		return payMode;
	}


	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public Long getBillNumber() {
		return billNumber;
	}


	public String getReceiptNo() {
		return receiptNo;
	}
	
	
}
