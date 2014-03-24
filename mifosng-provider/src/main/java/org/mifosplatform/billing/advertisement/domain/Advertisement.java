package org.mifosplatform.billing.advertisement.domain;

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
@Table(name="obs_stg_advt")
public class Advertisement  extends AbstractAuditableCustom<AppUser, Long> {
	
   @Column(name="cust_code")
	 private Long customerCode;
   
   
   @Column(name="ba")
	 private String ba;

   
    @Column(name="pc")
	 private Long pc;
   
    
    @Column(name="mpm")
  	 private Long mpm;
    
    
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
	 private Long request;
    
    
    @Column(name="impressions")
  	 private Long impressions;
    
    
    @Column(name="clicks")
  	 private Long clicks;
    
    
    @Column(name=" click_through_ratio")
	 private String  clickThroughRatio;
    
    
    @Column(name="eipm")
 	 private BigDecimal eipm;
    
    
    @Column(name="eipc")
 	 private BigDecimal eipc;
    
    
     @Column(name="income")
	 private BigDecimal  income;
    
     
    @Column(name="ex_rt")
 	 private BigDecimal exrtaRate;
    
    
     @Column(name="ig_revenueamt")
	 private BigDecimal  igRevenueAmount;
    
    
    @Column(name="cp_share_amt")
	 private BigDecimal  cpShareAmount;
    
    @Column(name="net_ig_rev_after_royalty_payouts")
	 private BigDecimal  igAfterRoyaltyPayouts;
    
    public Advertisement(){
    	
    }
    
    
	public Advertisement(Long custCode, String ba, Long pc, Long mpm,String invoiceNo, LocalDate invoiceDate, String customerName,
			String customerCircle, String activityMonth, String business,String currency, String category, String contentName,
			String contentProviderName, Long request, Long impressions,Long clicks, String clickThroughRatio, BigDecimal eipm,
			BigDecimal eipc, BigDecimal income, BigDecimal exrtaRate,BigDecimal igRevenueAmount, BigDecimal cpShareAmount,
			BigDecimal igAfterRoyaltyPayouts) {
		
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

	}


	public static Advertisement fromJson(final JsonCommand command) {
		
		final Long custCode = command.longValueOfParameterNamed("custCode");
		final String ba = command.stringValueOfParameterNamed("ba");
		final Long pc = command.longValueOfParameterNamed("pc");
		final Long mpm = command.longValueOfParameterNamed("mpm");
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
		final Long request = command.longValueOfParameterNamed("request");
		final Long impressions = command.longValueOfParameterNamed("impressions");
		final Long clicks = command.longValueOfParameterNamed("clicks");
		final String clickThroughRatio = command.stringValueOfParameterNamed("clickThroughRatio");
		final BigDecimal eipm = command.bigDecimalValueOfParameterNamed("eipm");
		final BigDecimal eipc = command.bigDecimalValueOfParameterNamed("eipc");
		final BigDecimal income = command.bigDecimalValueOfParameterNamed("income");
		final BigDecimal exrtaRate = command.bigDecimalValueOfParameterNamed("exrtaRate");
		final BigDecimal igRevenueAmount = command.bigDecimalValueOfParameterNamed("igRevenueAmount");
		final BigDecimal cpShareAmount = command.bigDecimalValueOfParameterNamed("cpShareAmount");
		final BigDecimal igAfterRoyaltyPayouts = command.bigDecimalValueOfParameterNamed("igAfterRoyaltyPayouts");
		
		return new Advertisement(custCode,ba,pc,mpm,invoiceNo,invoiceDate,customerName,customerCircle,activityMonth,business,currency,category,contentName,contentProviderName,
				request,impressions,clicks,clickThroughRatio,eipm,eipc,income,exrtaRate,igRevenueAmount,cpShareAmount,igAfterRoyaltyPayouts);
	}


	public Long getCustomerCode() {
		return customerCode;
	}


	public void setCustomerCode(Long customerCode) {
		this.customerCode = customerCode;
	}

	public String getBa() {
		return ba;
	}


	public void setBa(String ba) {
		this.ba = ba;
	}


	public Long getPc() {
		return pc;
	}


	public void setPc(Long pc) {
		this.pc = pc;
	}


	public Long getMpm() {
		return mpm;
	}


	public void setMpm(Long mpm) {
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


	public Long getRequest() {
		return request;
	}


	public void setRequest(Long request) {
		this.request = request;
	}


	public Long getImpressions() {
		return impressions;
	}


	public void setImpressions(Long impressions) {
		this.impressions = impressions;
	}


	public Long getClicks() {
		return clicks;
	}


	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}


	public String getClickThroughRatio() {
		return clickThroughRatio;
	}


	public void setClickThroughRatio(String clickThroughRatio) {
		this.clickThroughRatio = clickThroughRatio;
	}


	public BigDecimal getEipm() {
		return eipm;
	}


	public void setEipm(BigDecimal eipm) {
		this.eipm = eipm;
	}


	public BigDecimal getEipc() {
		return eipc;
	}


	public void setEipc(BigDecimal eipc) {
		this.eipc = eipc;
	}


	public BigDecimal getIncome() {
		return income;
	}


	public void setIncome(BigDecimal income) {
		this.income = income;
	}


	public BigDecimal getExrtaRate() {
		return exrtaRate;
	}


	public void setExrtaRate(BigDecimal exrtaRate) {
		this.exrtaRate = exrtaRate;
	}


	public BigDecimal getIgRevenueAmount() {
		return igRevenueAmount;
	}


	public void setIgRevenueAmount(BigDecimal igRevenueAmount) {
		this.igRevenueAmount = igRevenueAmount;
	}


	public BigDecimal getCpShareAmount() {
		return cpShareAmount;
	}


	public void setCpShareAmount(BigDecimal cpShareAmount) {
		this.cpShareAmount = cpShareAmount;
	}


	public BigDecimal getIgAfterRoyaltyPayouts() {
		return igAfterRoyaltyPayouts;
	}


	public void setIgAfterRoyaltyPayouts(BigDecimal igAfterRoyaltyPayouts) {
		this.igAfterRoyaltyPayouts = igAfterRoyaltyPayouts;
	}
   
    
    
    
}
