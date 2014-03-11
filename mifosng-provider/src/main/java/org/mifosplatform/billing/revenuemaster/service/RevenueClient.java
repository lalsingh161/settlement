package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mifosplatform.billing.billingorder.domain.Invoice;
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
public class RevenueClient {
	
	private ClientBalanceReadPlatformService clientBalanceReadPlatformService;
	private RevenueMasterReadplatformService revenueReadplatformService;
	private PlatformSecurityContext context;
	private RevenueCommandFromApiJsonDeserializer apiJsonDeserializer;
	private TransactionHistoryWritePlatformService transactionHistoryWritePlatformService; 
	private GenerateRevenueService generateRevenueService;
	
@Autowired
	public RevenueClient(final PlatformSecurityContext context,final RevenueCommandFromApiJsonDeserializer apiJsonDeserializer,final RevenueMasterReadplatformService revenueReadplatformService,final GenerateRevenueService generateRevenueService){
		this.context =context;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.revenueReadplatformService = revenueReadplatformService;
		this.generateRevenueService = generateRevenueService;
		
	}

	public CommandProcessingResult createRevenueInvoice(JsonCommand command) {
	
		try
		{
		context.authenticatedUser();
		BigDecimal invoiceAmount=this.RevenueOfClient(command.entityId());      
	      invoiceAmount.doubleValue();
	      return new CommandProcessingResult(command.entityId());
	
			}
		catch(DataIntegrityViolationException dve) {
			return CommandProcessingResult.empty();
			
		}

	}
	
	
	public BigDecimal RevenueOfClient(Long clientId){
		List<RevenueMasterData> detailDatas=null;
		List<DeductionData> deductionDatas=this.revenueReadplatformService.retriveOperatorDeductionData(clientId);
		if(deductionDatas.size()==0){
			throw new NoDeductionsForOperatorException();
		}
		
		List<GenerateInteractiveHeaderData> headerDatas=this.revenueReadplatformService.retriveInteractiveHeaderDetails(clientId);
		BigDecimal invoiceAmount=null;
		if(headerDatas.size() !=0)
		{
		   for(GenerateInteractiveHeaderData headerData:headerDatas)
			{
			if(headerData.getMediaCategory().equalsIgnoreCase("Game")){
				 detailDatas=this.revenueReadplatformService.retriveAllinteractiveDetails(headerData.getId());
				 if(detailDatas!=null)
				 {
				 invoiceAmount=this.interactivedetailDeductions(detailDatas,deductionDatas);
		      	}else{
				throw new NoInteractiveHeadersFoundException(headerData.getId());
			    }
			}else if(headerData.getMediaCategory().equalsIgnoreCase("Non Game")){
				detailDatas=this.revenueReadplatformService.retriveAllinteractiveDetails(headerData.getId());
				if(detailDatas!=null)
				{
					invoiceAmount =  this.interactivedetailDeductions(detailDatas,deductionDatas);		
				}else {
					throw new NoInteractiveHeadersFoundException(headerData.getId());
				}
				}
			
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
		    long  detailId=detailData.getId();
			deductionTax=this.calculatedeductiontaxes(deductionDatas,grossRevenueAmount,detailId);	
			 deductionTaxes.addAll(deductionTax);
			// System.out.println(deductionTaxes);
		}
	}
		Invoice invoice = this.generateRevenueService.generateInvoice(detailDatas,deductionTaxes);
	
		if(invoice!=null)
		{
			invoiceAmount=invoiceAmount.add(invoice.getInvoiceAmount());
		}
		return invoiceAmount;
	}
	

	public List<DeductionTaxesData> calculatedeductiontaxes(
			List<DeductionData> deductionDatas, BigDecimal grossRevenueAmount,Long detailId) {
		
		String deductionCode=null;
		String deductionType=null;
		BigDecimal deductionValue=null;
		BigDecimal taxAmount=BigDecimal.ZERO;
		List<DeductionTaxesData> taxes=new ArrayList<DeductionTaxesData>();
		DeductionTaxesData tax=null;
		
		if(deductionDatas.size()==0){
		  throw new NoInteractiveHeadersFoundException();

		}
		
		for(DeductionData deductionData:deductionDatas)
		{
			deductionCode=deductionData.getDeductionCode();
	
			if(deductionData.getDeductionCode().equalsIgnoreCase("wpc"))
			{   
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
				deductionValue=deductionData.getDeductionValue();
			taxAmount=grossRevenueAmount.multiply(deductionData.getDeductionValue().divide(new BigDecimal(100)));
			
			} if(deductionData.getDeductionCode().equalsIgnoreCase("ED")){
				
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
				deductionValue=deductionData.getDeductionValue();
				BigDecimal temAmount=grossRevenueAmount.subtract(taxAmount);
				taxAmount=temAmount.multiply(deductionData.getDeductionValue().divide(new BigDecimal(100)));
			
			} if(deductionData.getDeductionCode().equalsIgnoreCase("ET")){
				
				if(deductionData.getCircle()>0){
				deductionType=deductionData.getDeductionType();
				deductionCode=deductionData.getDeductionCode();
				deductionValue=deductionData.getDeductionValue();
				taxAmount=grossRevenueAmount.multiply(deductionData.getDeductionValue().divide(new BigDecimal(100)));
				}
			}
			
			tax=new DeductionTaxesData(deductionCode,deductionValue,deductionType,taxAmount,detailId);
		taxes.add(tax);
		}
		return taxes;

	}
}
