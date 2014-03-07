package org.mifosplatform.billing.businessline.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BusinessLineMasterRepository extends JpaRepository<BusinessLineMaster, Long>,
JpaSpecificationExecutor<BusinessLineMaster>{

}
