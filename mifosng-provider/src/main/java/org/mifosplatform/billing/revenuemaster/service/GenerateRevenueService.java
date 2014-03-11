package org.mifosplatform.billing.revenuemaster.service;

import java.math.BigDecimal;
import java.util.List;

import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.revenuemaster.data.DeductionTaxesData;
import org.mifosplatform.billing.revenuemaster.data.RevenueMasterData;

public interface GenerateRevenueService {

	public Invoice generateInvoice(List<RevenueMasterData> detailDatas,
			List<DeductionTaxesData> deductionTaxes);
	
}
