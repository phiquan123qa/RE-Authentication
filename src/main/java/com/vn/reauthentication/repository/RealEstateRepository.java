package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@EnableJpaRepositories
public interface RealEstateRepository extends JpaRepository<RealEstate, Long>, JpaSpecificationExecutor<RealEstate> {
    @Modifying
    @Query("UPDATE RealEstate r SET r.title =: title, r.price =: price, " +
            "r.landArea =: landArea, r.mainImage =: mainImage, " +
            "r.cityRe =: cityRe, r.districtRe =: districtRe, " +
            "r.wardRe =: wardRe, r.address =: address," +
            "r.description =: description, " +
            "r.dateStart =: dateStart, r.dateEnd =: dateEnd, " +
            "r.type =: type, r.statusRe =: statusRe WHERE r.id =: id ")
    void update(String title, Double price,
                Double landArea, String mainImage,
                String cityRe, String districtRe,
                String wardRe, String address,
                String description,
                LocalDate dateStart,
                LocalDate dateEnd, String type,
                String statusRe, Long id);
}
