package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.billing.billingorder.commands.InvoiceTaxCommand;
import org.mifosplatform.billing.billingorder.domain.BillingOrder;
import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.billingorder.domain.InvoiceRepository;
import org.mifosplatform.billing.billingorder.domain.InvoiceTax;
import org.mifosplatform.billing.revenuemaster.data.DeductionTaxesData;
import org.mifosplatform.billing.revenuemaster.data.OperatorShareData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.billing.revenuemaster.domain.DeductionTax;
import org.mifosplatform.billing.taxmaster.data.TaxMappingRateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GenerateRevenueServiceImp implements GenerateRevenueService {
	
	private final InvoiceRepository invoiceRepository;
	private final RevenueMasterReadplatformService revenueReadplatformService;

	
	@Autowired
	public GenerateRevenueServiceImp(final InvoiceRepository invoiceRepository,final RevenueMasterReadplatformService revenueReadplatformService){
		
		this.invoiceRepository=invoiceRepository;
		this.revenueReadplatformService =revenueReadplatformService;
		
                 }
	
	public Invoice generateInvoice(List<RevenueMasterData> detailDatas,
			List<DeductionTaxesData> deductionTaxes) {
		
		BigDecimal invoiceAmount=BigDecimal.ZERO;
		BigDecimal netTaxAmount=BigDecimal.ZERO;
		BigDecimal netChargeAmount = BigDecimal.ZERO;
		BigDecimal discountAmount = BigDecimal.ZERO;
	    BigDecimal netChargeTaxAmount = BigDecimal.ZERO;
	    BigDecimal netRevenueAmount = BigDecimal.ZERO;
	    BigDecimal detailChargeTaxAmount = BigDecimal.ZERO;
	    BigDecimal operatorShareAmount = BigDecimal.ZERO;
	    BigDecimal netOperatorShareAmount = BigDecimal.ZERO;
	    BigDecimal totalChargeAmount = BigDecimal.ZERO;
	   
	    
		List<OperatorShareData> revenueShareDatas=this.revenueReadplatformService.retriveOperatorRevenueShareData(detailDatas.get(0).getClientId()); 
		/* OperatorShareData revenueShareData=null;
		
		if(revenueShareDatas.size()!=0){
			revenueShareData=revenueShareDatas.get(0);
		}*/
		
	    Invoice invoice = new Invoice(detailDatas.get(0).getClientId(),new LocalDate().toDate(), invoiceAmount, invoiceAmount, netTaxAmount, "active");
	    
	   // System.out.println("chargecode" +detailDatas.get(0).getChargeCode());
	    BillingOrder charge = new BillingOrder(detailDatas.get(0).getClientId(), detailDatas.get(0).getHeaderId(),new Long(0), detailDatas.get(0).getChargeCode(),detailDatas.get(0).getChargeType(), "None" ,invoiceAmount,
 				discountAmount,	netChargeAmount,new LocalDate().toDate(),new LocalDate().toDate());
	    
	    for(RevenueMasterData detailData:detailDatas)
		{
	
			for(DeductionTaxesData deductionTax:deductionTaxes)
			{
			if(detailData.getId().compareTo(deductionTax.getDetailId())==0){
			 detailChargeTaxAmount=detailChargeTaxAmount.add(deductionTax.getTaxAmount());
			
			DeductionTax deduction=new DeductionTax(invoice,charge,deductionTax.getDeductionCode(),
					                                 deductionTax.getDeductionValue(),deductionTax.getTaxAmount());
			/*InvoiceTax invoiceTax = new InvoiceTax(invoice, charge, deductionTax.getDeductionCode(),
				                deductionTax.getDeductionValue(), deductionTax.getTaxAmount());
			charge.addChargeTaxes(invoiceTax);*/
			charge.addDeductionTaxes(deduction);
			}
			}
			netRevenueAmount=detailData.getGrossRevenue().subtract(detailChargeTaxAmount);
			detailData.setDetailChargeTaxAmount(detailChargeTaxAmount);
		    detailData.updateNetRevenueAmount(netRevenueAmount);
		    //for(OperatorShareData revenuShareData:revenueShareDatas)
		    operatorShareAmount=this.calculateOperatorShareAmount(revenueShareDatas,detailData.getNetRevenueAmount(),detailData);
		    
		    detailData.setOperatorShareAmount(operatorShareAmount);
		    detailChargeTaxAmount=BigDecimal.ZERO;
			System.out.println(detailData.getNetRevenueAmount());
		}
	    
	    
	    for(RevenueMasterData detailData:detailDatas)
	    {
	    	//netChargeTaxAmount=netChargeTaxAmount.add(detailData.getDetailChargeTaxAmount());
	    	netChargeAmount=netChargeAmount.add(detailData.getNetRevenueAmount());	
	    	netOperatorShareAmount=netOperatorShareAmount.add(detailData.getOperatorShareAmount());
	    }
	   
	    List<InvoiceTaxCommand> listOfTaxes = this.calculateTax(detailDatas,netChargeAmount);
	    
	    
	    for(InvoiceTaxCommand  invoiceTaxCommand : listOfTaxes)
	    {
	    	netChargeTaxAmount = netChargeTaxAmount.add(invoiceTaxCommand.getTaxAmount());
			InvoiceTax invoiceTax = new InvoiceTax(invoice, charge, invoiceTaxCommand.getTaxCode(),
					 invoiceTaxCommand.getTaxPercentage(), invoiceTaxCommand.getTaxAmount());
			charge.addChargeTaxes(invoiceTax);
	    }
	    
	    if(detailDatas.get(0).getTaxInclusive()!=null){
		   if(isTaxInclusive(detailDatas.get(0).getTaxInclusive())){
				netChargeAmount = netChargeAmount.subtract(netChargeTaxAmount);
				
				charge.setNetChargeAmount(netChargeAmount);
				charge.setChargeAmount(netChargeAmount);
			}
			  }
	    
		DeductionTax deduction=new DeductionTax(invoice,charge,"OPRS",null,netOperatorShareAmount);
	    charge.addDeductionTaxes(deduction);
	    
	    netTaxAmount = netTaxAmount.add(netChargeTaxAmount);
		totalChargeAmount = totalChargeAmount.add(netChargeAmount);
	    
		charge.setNetChargeAmount(netChargeAmount);
		charge.setChargeAmount(netChargeAmount);
		
	    invoice.addCharges(charge);		
	    
	    invoiceAmount = totalChargeAmount.add(netTaxAmount);
		invoice.setNetChargeAmount(totalChargeAmount);
		invoice.setTaxAmount(netTaxAmount);
		
		invoice.setInvoiceAmount(invoiceAmount);
		invoice.setDueAmount(invoiceAmount);
		
		return this.invoiceRepository.save(invoice);
	}
	
	public BigDecimal calculateOperatorShareAmount(List<OperatorShareData> revenueShareDatas, BigDecimal netRevenueAmount, RevenueMasterData detailData) {
		
	  BigDecimal revenueAmountOfIg=BigDecimal.ZERO;
	  BigDecimal operatorShareAmount = BigDecimal.ZERO;
		   
		   for(OperatorShareData revenueShareData:revenueShareDatas){   
			   
		    	int a=detailData.getDownloads().compareTo(revenueShareData.getStartValue());
		    	int b=detailData.getDownloads().compareTo(revenueShareData.getEndValue());
		    
		   //here revenueShare percentage depending on downloads	
		      if(revenueShareData.getRevenueShareType().equalsIgnoreCase("percentage")){
		    	  
		        if((a==1||a==0)&&(b==-1||b==0)){
		           revenueAmountOfIg=detailData.getNetRevenueAmount().multiply(revenueShareData.getRevenuePercentage().divide
		    			                        (new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
		     operatorShareAmount=detailData.getNetRevenueAmount().subtract(revenueAmountOfIg);	 
		     }
		      }else if(revenueShareData.getRevenueShareType().equalsIgnoreCase("Flat Rate")){
		    	 
		    	  revenueAmountOfIg=detailData.getNetRevenueAmount().multiply(revenueShareData.getRevenuePercentage().divide
	                        (new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
                operatorShareAmount=detailData.getNetRevenueAmount().subtract(revenueAmountOfIg);	 
		     }
		           
		     }
		   
		   detailData.setRevenueAmountOfIg(revenueAmountOfIg);
		  return  operatorShareAmount; 
	}

	public  List<InvoiceTaxCommand> calculateTax(
			List<RevenueMasterData> detailDatas, BigDecimal netChargeAmount) {
		
		
		List<TaxMappingRateData> taxMappingRateDatas = this.revenueReadplatformService.retrieveTaxMappingDate(detailDatas.get(0).getClientId(),detailDatas.get(0).getChargeCode());
		
		if(taxMappingRateDatas.isEmpty()){
			
			 taxMappingRateDatas = this.revenueReadplatformService.retrieveDefaultTaxMappingDate(detailDatas.get(0).getClientId(),detailDatas.get(0).getChargeCode());
		}
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, netChargeAmount,detailDatas.get(0).getClientId());
		//List<InvoiceTax> listOfTaxes = invoiceTaxPlatformService.createInvoiceTax(invoiceTaxCommand);
		return invoiceTaxCommand;
	}
	
	// Generate Invoice Tax
		public List<InvoiceTaxCommand> generateInvoiceTax(List<TaxMappingRateData> taxMappingRateDatas, BigDecimal price, long clientId) {

			BigDecimal taxPercentage = null;
			String taxCode = null;
			BigDecimal taxAmount = null;
			BigDecimal taxFlat=null;
			List<InvoiceTaxCommand> invoiceTaxCommands = new ArrayList<InvoiceTaxCommand>();
			InvoiceTaxCommand invoiceTaxCommand = null;
		
				if (taxMappingRateDatas != null) {

					for (TaxMappingRateData taxMappingRateData : taxMappingRateDatas) {

						if (taxMappingRateData.getTaxType().equalsIgnoreCase("Percentage")) {
						      taxPercentage = taxMappingRateData.getRate();
						      taxCode = taxMappingRateData.getTaxCode();
						      taxAmount = price.multiply(taxPercentage.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
						     } else if (taxMappingRateData.getTaxType().equalsIgnoreCase("Flat")) {
						      taxFlat = taxMappingRateData.getRate();
						      taxCode = taxMappingRateData.getTaxCode();
						      taxAmount =taxFlat;
						     }
						
						/*taxPercentage = taxMappingRateData.getRate();
						taxCode = taxMappingRateData.getTaxCode();
						taxAmount = price.multiply(taxPercentage.divide(new BigDecimal(100)));*/	

						invoiceTaxCommand = new InvoiceTaxCommand(clientId, null, null,
								taxCode, null, taxPercentage, taxAmount);
						invoiceTaxCommands.add(invoiceTaxCommand);
				}

				}
				return invoiceTaxCommands;

			}


	
	
	
	
	
	
	
	
	
	
	
	
	

	public Boolean isTaxInclusive(Integer taxInclusive){
		
		Boolean isTaxInclusive = false;
		if(taxInclusive == 1) isTaxInclusive = true;
		return isTaxInclusive;
	}
		
	}
