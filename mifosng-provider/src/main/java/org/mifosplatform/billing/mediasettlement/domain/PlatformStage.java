package org.mifosplatform.billing.mediasettlement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="obs_stg_platform")
public class PlatformStage extends AbstractAuditableCustom<AppUser, Long>{
	
	@Column(name="cust_code")
	private Long custCode;
	
	@Column(name="ba")
	private String ba;
	
	@Column(name="pc")
	private Long pc;
	
	@Column(name="mpm")
	private Long mpm;
	
	@Column(name="invoice_no")
	private String invoiceNumber;
	
	@Column(name="invoice_date")
	private String invoiceDate;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_circle")
	private String customerCircle;
	
	@Column(name="activity_month")
	private String activityMonth;
	
	@Column(name="business")
	private String business;
	
	@Column(name="type")
	private String type;
	
	@Column(name="category")
	private String category;
	
	@Column(name="content_name")
	private String contentName;
	
	@Column(name="content_provider_name")
	private String contentProviderName;
	
	@Column(name="channel_name")
	private String channelName;
	
	@Column(name="service_name")
	private String serviceName;
	
	@Column(name="eup")
	private BigDecimal eup;
	
	@Column(name="downloads")
	private Long downloads;
	
	@Column(name="gross_revenue")
	private BigDecimal grossRevenue;
	
	@Column(name="wpc")
	private BigDecimal wpc;
	
	@Column(name="employee_deduction")
	private BigDecimal employeeDeduction;
	
	@Column(name="ent_tax")
	private BigDecimal entTax;
	
	@Column(name="waivers")
	private BigDecimal waivers;
	
	@Column(name="net_revenue_after_tax")
	private BigDecimal netRevenueAfterTax;
	
	@Column(name="operator_rev_share")
	private String operatorRevShare;
	
	@Column(name="operator_revenue_amt")
	private BigDecimal operatorRevenueAmt;
	
	
	public PlatformStage() {
		
	}
	
	
	public PlatformStage(final Long custCode, final String ba,
			final Long pc, final Long mpm, final String invoiceNumber,
			final String invoiceDate, final String customerName,
			final String customerCircle, final String activityMonth,
			final String business, final String type, final String category,
			final String contentName, final String contentProviderName,
			final String channelName, final String serviceName,
			final BigDecimal eup, final Long downloads,
			final BigDecimal grossRevenue, final BigDecimal wpc,
			final BigDecimal employeeDeduction, final BigDecimal entTax,
			final BigDecimal waivers, final BigDecimal netRevenueAfterTax,
			final String operatorRevShare, final BigDecimal operatorRevenueAmt) {
		
		this.custCode = custCode;
		this.ba = ba;
		this.pc= pc;
		this.mpm = mpm;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.customerName = customerName;
		this.customerCircle = customerCircle;
		this.activityMonth = activityMonth;
		this.business = business;
		this.type = type;
		this.category = category;
		this.contentName = contentName;
		this.contentProviderName = contentProviderName;
		this.channelName = channelName;
		this.serviceName = serviceName;
		this.eup = eup;
		this.downloads = downloads;
		this.grossRevenue = grossRevenue;
		this.wpc = wpc;
		this.employeeDeduction = employeeDeduction;
		this.entTax = entTax;
		this.waivers = waivers;
		this.netRevenueAfterTax = netRevenueAfterTax;
		this.operatorRevShare = operatorRevShare;
		this.operatorRevenueAmt = operatorRevenueAmt;
			

	}
	


	


	public String getBa() {
		return ba;
	}


	public Long getPc() {
		return pc;
	}


	public Long getMpm() {
		return mpm;
	}


	public String getCustomerName() {
		return customerName;
	}


	public String getCustomerCircle() {
		return customerCircle;
	}


	public String getActivityMonth() {
		return activityMonth;
	}


	public String getBusiness() {
		return business;
	}


	public String getType() {
		return type;
	}


	public String getCategory() {
		return category;
	}


	public String getContentName() {
		return contentName;
	}


	public String getContentProviderName() {
		return contentProviderName;
	}


	public String getChannelName() {
		return channelName;
	}


	public String getServiceName() {
		return serviceName;
	}


	public Long getDownloads() {
		return downloads;
	}

	public void setBa(String ba) {
		this.ba = ba;
	}


	public void setPc(Long pc) {
		this.pc = pc;
	}


	public void setMpm(Long mpm) {
		this.mpm = mpm;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public void setCustomerCircle(String customerCircle) {
		this.customerCircle = customerCircle;
	}


	public void setActivityMonth(String activityMonth) {
		this.activityMonth = activityMonth;
	}


	public void setBusiness(String business) {
		this.business = business;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public void setContentName(String contentName) {
		this.contentName = contentName;
	}


	public void setContentProviderName(String contentProviderName) {
		this.contentProviderName = contentProviderName;
	}


	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}
	
	
	public static PlatformStage fromJson(JsonCommand command){
		
		final Long custCode = command.longValueOfParameterNamed("custCode");
		final String ba = command.stringValueOfParameterNamed("ba");
		final Long pc = command.longValueOfParameterNamed("pc");
		final Long mpm = command.longValueOfParameterNamed("mpm");
		final String invoiceNumber = command.stringValueOfParameterNamed("invoiceNo");
		final String invoiceDate  = command.stringValueOfParameterNamed("invoiceDate");
		final String customerName = command.stringValueOfParameterNamed("customerName");
		final String customerCircle = command.stringValueOfParameterNamed("customerCircle");
		final String activityMonth = command.stringValueOfParameterNamed("activityMonth");
		final String business = command.stringValueOfParameterNamed("business");
		final String type = command.stringValueOfParameterNamed("type");
		final String category = command.stringValueOfParameterNamed("category");
		final String contentName = command.stringValueOfParameterNamed("contentName");
		final String contentProviderName = command.stringValueOfParameterNamed("contentProviderName");
		final String channelName = command.stringValueOfParameterNamed("channelName");
		final String serviceName = command.stringValueOfParameterNamed("serviceName");
		final BigDecimal eup = command.bigDecimalValueOfParameterNamed("eup");
		final Long downloads = command.longValueOfParameterNamed("downloads");
		final BigDecimal grossRevenue = command.bigDecimalValueOfParameterNamed("grossRevenue");
		final BigDecimal wpc = command.bigDecimalValueOfParameterNamed("wpc");
		final BigDecimal employeeDeduction = command.bigDecimalValueOfParameterNamed("employeeDeduction");
		final BigDecimal entTax = command.bigDecimalValueOfParameterNamed("entTax");
		final BigDecimal waivers = command.bigDecimalValueOfParameterNamed("waivers");
		final BigDecimal netRevenueAfterTax = command.bigDecimalValueOfParameterNamed("netRevenueAfterTax");
		final String operatorRevShare = command.stringValueOfParameterNamed("operatorRevShare");
		final BigDecimal operatorRevenueAmt = command.bigDecimalValueOfParameterNamed("operatorRevenueAmt");
		
		return new PlatformStage(custCode, ba, pc, mpm, invoiceNumber, invoiceDate, customerName, customerCircle, activityMonth, business, type, category, contentName, contentProviderName, channelName, serviceName, eup, downloads, grossRevenue, wpc, employeeDeduction, entTax, waivers, netRevenueAfterTax, operatorRevShare, operatorRevenueAmt);
	}
	
	
	
	
	
}
