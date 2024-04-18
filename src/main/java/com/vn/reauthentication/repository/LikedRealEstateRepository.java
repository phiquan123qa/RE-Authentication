package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.LikedRealEstate;
import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
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
public interface LikedRealEstateRepository extends JpaRepository<LikedRealEstate, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM LikedRealEstate r WHERE r.user = :user AND r.realEstate = :realEstate")
    void delete(@Param("user") User user, @Param("realEstate") RealEstate realEstate);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM LikedRealEstate r WHERE r.user = :user AND r.realEstate = :realEstate")
    boolean existsByUserAndRealEstate(@Param("user") User user, @Param("realEstate") RealEstate realEstate);

    List<LikedRealEstate> findAllByUser(User user);
}
