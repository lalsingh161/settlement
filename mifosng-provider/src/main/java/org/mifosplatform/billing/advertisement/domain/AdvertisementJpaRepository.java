package org.mifosplatform.billing.advertisement.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AdvertisementJpaRepository extends JpaRepository<Advertisement, Long>,
JpaSpecificationExecutor<Advertisement>{

}