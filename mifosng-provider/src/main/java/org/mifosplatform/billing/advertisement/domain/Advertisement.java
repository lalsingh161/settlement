package org.mifosplatform.billing.advertisement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name="obs_stg_advt")
public class Advertisement  extends AbstractAuditableCustom<AppUser, String> {
	
   @Column(name="cust_code")
	 private String customerCode;
   
   
   @Column(name="ba")
	 private String ba;

   
    @Column(name="pc")
	 private String pc;
   
    
    @Column(name="mpm")
  	 private String mpm;
    
    
    @Column(name="invoice_no")
	 private String invoiceNo;
    
    
    @Column(name="invoice_date")
  	 private Date invoiceDate; 
    
    
    @Column(name="customer_name")
  	 private String customerName;
    
    
    @Column(name="customer_circle")
 	 private String customerCircle; 
    
    
    @Column(name="activity_month")
	 private String activityMonth;  
    
    
    @Column(name="business")
	 private String business;
    
    
    @Column(name="currency")
	 private String currency;
    
    
    @Column(name="category")
	 private String category;
    
    
    @Column(name="content_name")
	 private String contentName;
    
    
    @Column(name="content_provider_name")
	 private String contentProviderName;
    
    
    @Column(name="request")
	 private String request;
    
    
    @Column(name="impressions")
  	 private String impressions;
    
    
    @Column(name="clicks")
  	 private String clicks;
    
    
    @Column(name=" click_through_ratio")
	 private String  clickThroughRatio;
    
    
    @Column(name="eipm")
 	 private String eipm;
    
    
    @Column(name="eipc")
 	 private String eipc;
    
    @Column(name="data_upload_date")
    private Date dataUploadDate = new Date();
    
     @Column(name="income")
	 private String  income;
    
     
    @Column(name="ex_rt")
 	 private String exrtaRate;
    
    
     @Column(name="ig_revenueamt")
	 private String  igRevenueAmount;
    
    
    @Column(name="cp_share_amt")
	 private String  cpShareAmount;
    
    @Column(name="net_ig_rev_after_royalty_payouts")
	 private String  igAfterRoyaltyPayouts;

    @Column(name="file_id")
	private String fileId;
    
    @Column(name="client_id")
    private String clientId;
    
    public Advertisement(){
    	
    }
    
    
	public Advertisement(String custCode, String ba, String pc, String mpm,String invoiceNo, LocalDate invoiceDate, String customerName,
			String customerCircle, String activityMonth, String business,String currency, String category, String contentName,
			String contentProviderName, String request, String impressions,String clicks, String clickThroughRatio, String eipm,
			String eipc, String income, String exrtaRate,String igRevenueAmount, String cpShareAmount,
			String igAfterRoyaltyPayouts, String fileId,String clientId) {
		
		this.customerCode = custCode;
		this.ba = ba;
		this.pc = pc;
		this.mpm = mpm;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate.toDate();
		this.customerName = customerName;
		this.customerCircle = customerCircle;
		this.activityMonth = activityMonth;
		this.business = business;
		this.currency = currency;
		this.category = category;
		this.contentName = contentName;
		this.contentProviderName = contentProviderName;
		this.request = request;
		this.impressions = impressions;
		this.clicks = clicks;
		this.clickThroughRatio = clickThroughRatio;
		this.eipm = eipm;
		this.eipc = eipc;
		this.income = income;
		this.exrtaRate = exrtaRate;
		this.igRevenueAmount = igRevenueAmount;
		this.cpShareAmount = cpShareAmount;
		this.igAfterRoyaltyPayouts = igAfterRoyaltyPayouts;
		this.fileId = fileId;
		this.clientId = clientId;

	}


	public static Advertisement fromJson(final JsonCommand command) {
		
		final String custCode = command.stringValueOfParameterNamed("custCode");
		final String ba = command.stringValueOfParameterNamed("ba");
		final String pc = command.stringValueOfParameterNamed("pc");
		final String mpm = command.stringValueOfParameterNamed("mpm");
		final String invoiceNo = command.stringValueOfParameterNamed("invoiceNo");
		final LocalDate invoiceDate = command.localDateValueOfParameterNamed("invoiceDate");
		final String customerName = command.stringValueOfParameterNamed("customerName");
		final String customerCircle = command.stringValueOfParameterNamed("customerCircle");
		final String activityMonth = command.stringValueOfParameterNamed("activityMonth");
		final String business = command.stringValueOfParameterNamed("business");
		final String currency = command.stringValueOfParameterNamed("currency");
		final String category = command.stringValueOfParameterNamed("category");
		final String contentName = command.stringValueOfParameterNamed("contentName");
		final String contentProviderName = command.stringValueOfParameterNamed("contentProviderName");
		final String request = command.stringValueOfParameterNamed("request");
		final String impressions = command.stringValueOfParameterNamed("impressions");
		final String clicks = command.stringValueOfParameterNamed("clicks");
		final String clickThroughRatio = command.stringValueOfParameterNamed("clickThroughRatio");
		final String eipm = command.stringValueOfParameterNamed("eipm");
		final String eipc = command.stringValueOfParameterNamed("eipc");
		final String income = command.stringValueOfParameterNamed("income");
		final String exrtaRate = command.stringValueOfParameterNamed("exrtaRate");
		final String igRevenueAmount = command.stringValueOfParameterNamed("igRevenueAmount");
		/*final String cpShareAmount = command.stringValueOfParameterNamed("cpShareAmount");
		final String igAfterRoyaltyPayouts = command.stringValueOfParameterNamed("igAfterRoyaltyPayouts");*/
		final String fileId  = command.stringValueOfParameterNamed("fileName");
		final String clientId = command.stringValueOfParameterNamed("clientId");
		
		return new Advertisement(custCode,ba,pc,mpm,invoiceNo,invoiceDate,customerName,customerCircle,activityMonth,business,currency,category,contentName,contentProviderName,
				request,impressions,clicks,clickThroughRatio,eipm,eipc,income,exrtaRate,igRevenueAmount,/*cpShareAmount*/null,null/*igAfterRoyaltyPayouts*/,fileId,clientId);
	}


	public String getCustomerCode() {
		return customerCode;
	}


	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getBa() {
		return ba;
	}


	public void setBa(String ba) {
		this.ba = ba;
	}


	public String getPc() {
		return pc;
	}


	public void setPc(String pc) {
		this.pc = pc;
	}


	public String getMpm() {
		return mpm;
	}


	public void setMpm(String mpm) {
		this.mpm = mpm;
	}


	public String getInvoiceNo() {
		return invoiceNo;
	}


	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public Date getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCustomerCircle() {
		return customerCircle;
	}


	public void setCustomerCircle(String customerCircle) {
		this.customerCircle = customerCircle;
	}


	public String getActivityMonth() {
		return activityMonth;
	}


	public void setActivityMonth(String activityMonth) {
		this.activityMonth = activityMonth;
	}


	public String getBusiness() {
		return business;
	}


	public void setBusiness(String business) {
		this.business = business;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getContentName() {
		return contentName;
	}


	public void setContentName(String contentName) {
		this.contentName = contentName;
	}


	public String getContentProviderName() {
		return contentProviderName;
	}


	public void setContentProviderName(String contentProviderName) {
		this.contentProviderName = contentProviderName;
	}


	public String getRequest() {
		return request;
	}


	public void setRequest(String request) {
		this.request = request;
	}


	public String getImpressions() {
		return impressions;
	}


	public void setImpressions(String impressions) {
		this.impressions = impressions;
	}


	public String getClicks() {
		return clicks;
	}


	public void setClicks(String clicks) {
		this.clicks = clicks;
	}


	public String getClickThroughRatio() {
		return clickThroughRatio;
	}


	public void setClickThroughRatio(String clickThroughRatio) {
		this.clickThroughRatio = clickThroughRatio;
	}


	public String getEipm() {
		return eipm;
	}


	public void setEipm(String eipm) {
		this.eipm = eipm;
	}


	public String getEipc() {
		return eipc;
	}


	public void setEipc(String eipc) {
		this.eipc = eipc;
	}


	public String getIncome() {
		return income;
	}


	public void setIncome(String income) {
		this.income = income;
	}


	public String getExrtaRate() {
		return exrtaRate;
	}


	public void setExrtaRate(String exrtaRate) {
		this.exrtaRate = exrtaRate;
	}


	public String getIgRevenueAmount() {
		return igRevenueAmount;
	}


	public void setIgRevenueAmount(String igRevenueAmount) {
		this.igRevenueAmount = igRevenueAmount;
	}


	public String getCpShareAmount() {
		return cpShareAmount;
	}


	public void setCpShareAmount(String cpShareAmount) {
		this.cpShareAmount = cpShareAmount;
	}


	public String getIgAfterRoyaltyPayouts() {
		return igAfterRoyaltyPayouts;
	}


	public void setIgAfterRoyaltyPayouts(String igAfterRoyaltyPayouts) {
		this.igAfterRoyaltyPayouts = igAfterRoyaltyPayouts;
	}
   
    
    
    
}
