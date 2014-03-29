package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.billingorder.service.BillingOrderWritePlatformService;
import org.mifosplatform.billing.clientbalance.data.ClientBalanceData;
import org.mifosplatform.billing.clientbalance.service.ClientBalanceReadPlatformService;
import org.mifosplatform.billing.revenuemaster.data.DeductionData;
import org.mifosplatform.billing.revenuemaster.data.DeductionTaxesData;
import org.mifosplatform.billing.revenuemaster.data.GenerateInteractiveHeaderData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.billing.revenuemaster.exception.NoDeductionsForOperatorException;
import org.mifosplatform.billing.revenuemaster.exception.NoInteractiveHeadersFoundException;
import org.mifosplatform.billing.revenuemaster.serialization.RevenueCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class InvoiceRevenueClient {
	
	private RevenueMasterReadplatformService revenueReadplatformService;
	private PlatformSecurityContext context;
	private RevenueCommandFromApiJsonDeserializer apiJsonDeserializer;
	private TransactionHistoryWritePlatformService transactionHistoryWritePlatformService; 
	private GenerateInvoiceRevenueService generateInvoiceRevenueService;
	private BillingOrderWritePlatformService billingOrderWritePlatformService;
	private ClientBalanceReadPlatformService clientBalanceReadPlatformService;
	
@Autowired
	public InvoiceRevenueClient(final PlatformSecurityContext context,final RevenueCommandFromApiJsonDeserializer apiJsonDeserializer,final RevenueMasterReadplatformService revenueReadplatformService,final GenerateInvoiceRevenueService generateInvoiceRevenueService,
			final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService,
			final BillingOrderWritePlatformService billingOrderWritePlatformService,
			final ClientBalanceReadPlatformService clientBalanceReadPlatformService){
		this.context =context;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.revenueReadplatformService = revenueReadplatformService;
		this.generateInvoiceRevenueService = generateInvoiceRevenueService;
		this.transactionHistoryWritePlatformService = transactionHistoryWritePlatformService;
		this.billingOrderWritePlatformService = billingOrderWritePlatformService;
		this.clientBalanceReadPlatformService = clientBalanceReadPlatformService;
		
	}

	public CommandProcessingResult createRevenueInvoice(JsonCommand command) {
	
		try {
			context.authenticatedUser();
			this.RevenueOfClient(command.entityId());//BigDecimal invoiceAmount = 
													//invoiceAmount.doubleValue();
			return new CommandProcessingResult(command.entityId());

		} catch (DataIntegrityViolationException dve) {
			return CommandProcessingResult.empty();

		}

	}
	
	
	public BigDecimal RevenueOfClient(Long clientId){
		List<RevenueMasterData> detailDatas=null;
		BigDecimal invoiceAmount=null;
		List<DeductionData> deductionDatas=this.revenueReadplatformService.retriveOperatorDeductionData(clientId);
		if(deductionDatas.size()==0){
			throw new NoDeductionsForOperatorException();
		}
		
		List<GenerateInteractiveHeaderData> headerDatas=this.revenueReadplatformService.retriveInteractiveHeaderDetails(clientId);
		if(headerDatas.size() !=0)
		{
		   for(GenerateInteractiveHeaderData headerData:headerDatas)
			{
			if(isBusinessLineMobile(headerData)){
				 detailDatas=this.revenueReadplatformService.retriveAllinteractiveDetails(headerData.getId());
				 if(detailDatas.size()!=0)
				 {
				 invoiceAmount=this.interactivedetailDeductions(detailDatas,deductionDatas);
		      	}else{
				throw new NoInteractiveHeadersFoundException(headerData.getId());
			    }
			}else if(isBusinessLineAdver(headerData)){
				detailDatas=this.revenueReadplatformService.retriveAllinteractiveDetails(headerData.getId());
				if(detailDatas.size()!=0)
				{
					invoiceAmount =  this.interactivedetailDeductions(detailDatas,deductionDatas);		
				}else {
					throw new NoInteractiveHeadersFoundException(headerData.getId());
				}
		
			}/*else if(headerData.getMediaCategory().equalsIgnoreCase("Non Gam Wall Paper")){
				detailDatas=this.revenueReadplatformService.retriveAllinteractiveDetails(headerData.getId());
				if(detailDatas!=null)
				{
					invoiceAmount =  this.interactivedetailDeductions(detailDatas,deductionDatas);		
				}else {
					throw new NoInteractiveHeadersFoundException(headerData.getId());
				}
				}*/
			
			}
	
	   }else{
		  throw new NoInteractiveHeadersFoundException();
	}
		return invoiceAmount;
		
	}

	public BigDecimal interactivedetailDeductions(List<RevenueMasterData> detailDatas,
			List<DeductionData> deductionDatas) {
		List<DeductionTaxesData> deductionTaxes=new ArrayList<DeductionTaxesData>();
		List<DeductionTaxesData> deductionTax=new ArrayList<DeductionTaxesData>();
		BigDecimal grossRevenueAmount=null;
		  BigDecimal invoiceAmount=BigDecimal.ZERO;
		//BigDecimal totalRevenueAmount=BigDecimal.ZERO;
		if(detailDatas !=null)
		{
		for(RevenueMasterData detailData:detailDatas)
		{
			grossRevenueAmount=detailData.getGrossRevenue();
			if(grossRevenueAmount==null){
				grossRevenueAmount=detailData.getEndUserPrice().multiply(detailData.getDownloads());
			}
		    final  Long  detailId=detailData.getId();
			deductionTax=this.calculatedeductiontaxes(deductionDatas,grossRevenueAmount,detailId,  detailData.getActivityMonth());	
			 deductionTaxes.addAll(deductionTax);
			// System.out.println(deductionTaxes);
		}
	}
		Invoice invoice = this.generateInvoiceRevenueService.generateInvoice(detailDatas,deductionTaxes);
	
		List<ClientBalanceData> clientBalancesDatas = clientBalanceReadPlatformService.retrieveAllClientBalances(detailDatas.get(0).getClientId());
		
		this.billingOrderWritePlatformService.updateClientBalance(invoice,clientBalancesDatas);
		
		 transactionHistoryWritePlatformService.saveTransactionHistory(detailDatas.get(0).getClientId(),"Invoice", new Date(),"Amount:"
 				+invoice.getInvoiceAmount(),"Charge Startdate:"+new Date(),
 				"Charge Enddate:"+new Date());
		
		if(invoice!=null)
		{
			invoiceAmount=invoiceAmount.add(invoice.getInvoiceAmount());
		}
		return invoiceAmount;
	}
	

	public List<DeductionTaxesData> calculatedeductiontaxes(
			List<DeductionData> deductionDatas, BigDecimal grossRevenueAmount,Long detailId, String activityMonth) {
		
	/*	String deductionCode=null;
		String deductionType=null;
		BigDecimal deductionValue=null;*/
		BigDecimal WpcTaxAmount=BigDecimal.ZERO;
		 DateTimeFormatter formatter = DateTimeFormat.forPattern("MMMM yyyy");
		List<DeductionTaxesData> taxes=new ArrayList<DeductionTaxesData>();
		DeductionTaxesData tax=null;
		
/*		if(deductionDatas.size()==0){
		  throw new NoInteractiveHeadersFoundException();

		}*/
		
		for(DeductionData deductionData:deductionDatas)
		{
			String deductionCode=null;
			String deductionType=null;
			BigDecimal deductionValue=null;
			BigDecimal taxAmount=BigDecimal.ZERO;
			//deductionCode=deductionData.getDeductionCode();
	
			
			if(isDeductionCodeWpc(deductionData)){
			
			/*if(deductionData.getDeductionCode().equalsIgnoreCase("wpc"))
			{   */
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
			    deductionValue=deductionData.getDeductionValue();
			    taxAmount=grossRevenueAmount.multiply(deductionValue.divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP);
			    deductionData.setWpcTaxAmount(taxAmount);
			    WpcTaxAmount=deductionData.getWpcTaxAmount();
			
			}else if(deductionData.getDeductionCode().equalsIgnoreCase("ED")&&WpcTaxAmount.compareTo(BigDecimal.ZERO)>0){
				
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
				deductionValue=deductionData.getDeductionValue();
				BigDecimal temAmount=grossRevenueAmount.subtract(WpcTaxAmount);
				taxAmount=temAmount.multiply(deductionData.getDeductionValue().divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP);
			
			}else if(deductionData.getDeductionCode().equalsIgnoreCase("ET")){
				
				if(deductionData.getCircle()>0){
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
				deductionValue=deductionData.getDeductionValue();
				taxAmount=grossRevenueAmount.multiply(deductionData.getDeductionValue().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
				}
			}else{ }
			
			tax=new DeductionTaxesData(deductionCode,deductionValue,deductionType,taxAmount,detailId);
		taxes.add(tax);
		}
		return taxes;

	}

	public boolean isDeductionCodeWpc(DeductionData deductionData) {
		    boolean deductionCode=false;
		   if(deductionData.getDeductionCode().equalsIgnoreCase("wpc")) {
			   deductionCode=true;
		   }
		return deductionCode;
	}
	
	public boolean isBusinessLineMobile(GenerateInteractiveHeaderData headerData) {
		boolean businessLine=false;
		if(headerData.getBusinessLine().equalsIgnoreCase("Mobile")){
			businessLine=true;
		}
		return businessLine;
	}
	
	public boolean isBusinessLineAdver(GenerateInteractiveHeaderData headerData) {
		boolean businessLine=false;
		if(headerData.getBusinessLine().equalsIgnoreCase("Advertisement")){
			businessLine=true;
		}
		return businessLine;
	}
	
}
