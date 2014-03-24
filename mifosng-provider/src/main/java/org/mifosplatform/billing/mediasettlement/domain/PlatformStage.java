package org.mifosplatform.billing.mediasettlement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;


@Entity
@Table(name="obs_stg_platform")
public class PlatformStage extends AbstractAuditableCustom<AppUser, Long>{
	
	@Column(name="cust_code")
	private Double custCode;
	
	@Column(name="ba")
	private String ba;
	
	@Column(name="pc")
	private Double pc;
	
	@Column(name="mpm")
	private Double mpm;
	
	@Column(name="invoice_no")
	private Double invoiceNumber;
	
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
	private Double eup;
	
	@Column(name="downloads")
	private Double downloads;
	
	@Column(name="gross_revenue")
	private String grossRevenue;
	
	@Column(name="wpc")
	private String wpc;
	
	@Column(name="employee_deduction")
	private String employeeDeduction;
	
	@Column(name="ent_tax")
	private Double entTax;
	
	@Column(name="waivers")
	private Double waivres;
	
	@Column(name="net_revenue_after_tax")
	private String netRevenueAfterTax;
	
	@Column(name="operator_rev_share")
	private Double operatorRevShare;
	
	@Column(name="operator_revenue_amt")
	private String operatorRevenueAmt;
	
	
	public PlatformStage() {
		
	}


	public Double getCustCode() {
		return custCode;
	}


	public String getBa() {
		return ba;
	}


	public Double getPc() {
		return pc;
	}


	public Double getMpm() {
		return mpm;
	}


	public Double getInvoiceNumber() {
		return invoiceNumber;
	}


	public Date getInvoiceDate() {
		return invoiceDate;
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


	public Double getEup() {
		return eup;
	}


	public Double getDownloads() {
		return downloads;
	}


	public String getGrossRevenue() {
		return grossRevenue;
	}


	public String getWpc() {
		return wpc;
	}


	public String getEmployeeDeduction() {
		return employeeDeduction;
	}


	public Double getEntTax() {
		return entTax;
	}


	public Double getWaivres() {
		return waivres;
	}


	public String getNetRevenueAfterTax() {
		return netRevenueAfterTax;
	}


	public Double getOperatorRevShare() {
		return operatorRevShare;
	}


	public String getOperatorRevenueAmt() {
		return operatorRevenueAmt;
	}


	public void setCustCode(Double custCode) {
		this.custCode = custCode;
	}


	public void setBa(String ba) {
		this.ba = ba;
	}


	public void setPc(Double pc) {
		this.pc = pc;
	}


	public void setMpm(Double mpm) {
		this.mpm = mpm;
	}


	public void setInvoiceNumber(Double invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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


	public void setEup(Double eup) {
		this.eup = eup;
	}


	public void setDownloads(Double downloads) {
		this.downloads = downloads;
	}


	public void setGrossRevenue(String grossRevenue) {
		this.grossRevenue = grossRevenue;
	}


	public void setWpc(String wpc) {
		this.wpc = wpc;
	}


	public void setEmployeeDeduction(String employeeDeduction) {
		this.employeeDeduction = employeeDeduction;
	}


	public void setEntTax(Double entTax) {
		this.entTax = entTax;
	}


	public void setWaivres(Double waivres) {
		this.waivres = waivres;
	}


	public void setNetRevenueAfterTax(String netRevenueAfterTax) {
		this.netRevenueAfterTax = netRevenueAfterTax;
	}


	public void setOperatorRevShare(Double operatorRevShare) {
		this.operatorRevShare = operatorRevShare;
	}


	public void setOperatorRevenueAmt(String operatorRevenueAmt) {
		this.operatorRevenueAmt = operatorRevenueAmt;
	}
	
	
	
	
	
	
	
	
}
