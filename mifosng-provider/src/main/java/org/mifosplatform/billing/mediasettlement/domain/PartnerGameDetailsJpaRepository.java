package org.mifosplatform.billing.mediasettlement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartnerGameDetailsJpaRepository extends JpaRepository<PartnerGameDetails, Long>,
		JpaSpecificationExecutor<PartnerGameDetails> {

	@Query("from PartnerGameDetails gd where gd.game = :game")
    PartnerGameDetails findByGameId(@Param("game") Long game);
    
}
