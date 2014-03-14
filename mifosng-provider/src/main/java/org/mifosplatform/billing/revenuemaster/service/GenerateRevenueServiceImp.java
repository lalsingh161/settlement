package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.billing.billingorder.commands.InvoiceTaxCommand;
import org.mifosplatform.billing.billingorder.domain.BillingOrder;
import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.billingorder.domain.InvoiceRepository;
import org.mifosplatform.billing.billingorder.domain.InvoiceTax;
import org.mifosplatform.billing.revenuemaster.data.DeductionTaxesData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;
import org.mifosplatform.billing.revenuemaster.domain.DeductionTax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GenerateRevenueServiceImp implements GenerateRevenueService {
	
	private final InvoiceRepository invoiceRepository;

	
	@Autowired
	public GenerateRevenueServiceImp(final InvoiceRepository invoiceRepository){
		
		this.invoiceRepository=invoiceRepository;
		
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
	    
	    Invoice invoice = new Invoice(detailDatas.get(0).getClientId(),new LocalDate().toDate(), invoiceAmount, invoiceAmount, netTaxAmount, "active");
	    
	   // System.out.println("chargecode" +detailDatas.get(0).getChargeCode());
	    BillingOrder charge = new BillingOrder(detailDatas.get(0).getClientId(), detailDatas.get(0).getHeaderId(),new Long(0), detailDatas.get(0).getChargeCode(),detailDatas.get(0).getChargeType(), "None" ,invoiceAmount,
 				discountAmount,	netChargeAmount,new LocalDate().toDate(),new LocalDate().toDate());
	    
	    for(RevenueMasterData detailData:detailDatas)
		{
	    	
			for(DeductionTaxesData deductionTax:deductionTaxes)
			{
			if(detailData.getId()==deductionTax.getDetailId()){
				/*System.out.println(detailChargeTaxAmount);*/
			 detailChargeTaxAmount=detailChargeTaxAmount.add(deductionTax.getTaxAmount());
			
			DeductionTax deduction=new DeductionTax(invoice,charge,deductionTax.getDeductionCode(),
					                                 deductionTax.getDeductionValue(),deductionTax.getTaxAmount());
			InvoiceTax invoiceTax = new InvoiceTax(invoice, charge, deductionTax.getDeductionCode(),
				                deductionTax.getDeductionValue(), deductionTax.getTaxAmount());
			charge.addChargeTaxes(invoiceTax);
			charge.addDeductionTaxes(deduction);
			}
			}
			netRevenueAmount=detailData.getGrossRevenue().subtract(detailChargeTaxAmount);
			detailData.setDetailChargeTaxAmount(detailChargeTaxAmount);
		    detailData.updateNetRevenueAmount(netRevenueAmount);
		    detailChargeTaxAmount=BigDecimal.ZERO;
			System.out.println(detailData.getNetRevenueAmount());
			
		}
	    
	    
	    for(RevenueMasterData detailData:detailDatas)
	    {
	    	netChargeTaxAmount=netChargeTaxAmount.add(detailData.getDetailChargeTaxAmount());
	    	netChargeAmount=netChargeAmount.add(detailData.getNetRevenueAmount());	
	    	
	    }
	   
		charge.setNetChargeAmount(netChargeAmount);
		charge.setChargeAmount(netChargeAmount);    
	    
	    
		invoiceAmount=netChargeAmount;
		
		invoice.addCharges(charge);
		
	    
		invoice.setNetChargeAmount(invoiceAmount);
		invoice.setTaxAmount(netTaxAmount);
		
		invoice.setInvoiceAmount(invoiceAmount);
		invoice.setDueAmount(invoiceAmount);
		
		return this.invoiceRepository.save(invoice);
	}
	

	public Boolean isTaxInclusive(Integer taxInclusive){
		
		Boolean isTaxInclusive = false;
		if(taxInclusive == 1) isTaxInclusive = true;
		return isTaxInclusive;
	}
		
	}
