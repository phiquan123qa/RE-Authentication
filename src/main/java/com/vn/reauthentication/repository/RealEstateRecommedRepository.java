package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.RealEstateRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface RealEstateRecommedRepository extends JpaRepository<RealEstateRecommend, Long> {
    @Query("SELECT r.realEstate FROM RealEstateRecommend r")
    List<RealEstate> findAllRealEstate();

    @Modifying
    @Transactional
    @Query("delete from RealEstateRecommend r where r.realEstate = :realEstate")
    void deleteByRealEstate(@Param("realEstate")RealEstate realEstate);

//    void deleteByRealEstate(RealEstate realEstate);
}
