package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.ReportPostRealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ReportRealEstateRepository extends JpaRepository<ReportPostRealEstate, Long>, JpaSpecificationExecutor<ReportPostRealEstate> {
}
