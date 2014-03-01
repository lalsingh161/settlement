package org.mifosplatform.billing.mediadetails.domain;

import org.mifosplatform.billing.media.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettlementJpaRepository extends JpaRepository<Settlement, Long>,
		JpaSpecificationExecutor<Settlement> {

}
