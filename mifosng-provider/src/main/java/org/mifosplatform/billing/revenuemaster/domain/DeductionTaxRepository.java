package org.mifosplatform.billing.revenuemaster.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeductionTaxRepository extends  JpaRepository<DeductionTax, Long>,
JpaSpecificationExecutor<DeductionTax>{

}





