package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.LikedRealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LikeRealEstateRepository extends JpaRepository<LikedRealEstate, Long>, JpaSpecificationExecutor<LikedRealEstate> {
}
