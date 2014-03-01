package org.mifosplatform.billing.mediasettlement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelPartnerMappingJpaRepository extends JpaRepository<ChannelPartnerMapping, Long>,
		JpaSpecificationExecutor<ChannelPartnerMapping> {

}
