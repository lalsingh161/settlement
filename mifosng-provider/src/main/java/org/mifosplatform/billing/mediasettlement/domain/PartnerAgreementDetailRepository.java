package org.mifosplatform.billing.mediasettlement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnerAgreementDetailRepository extends JpaRepository<PartnerAgreementDetail, Long>, JpaSpecificationExecutor<PartnerAgreementDetail> {


}
