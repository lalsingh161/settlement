package org.mifosplatform.billing.masterdeduction.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeductionJpaRepository extends JpaRepository<DeductionCodes, Long>,
JpaSpecificationExecutor<DeductionCodes> {


}
