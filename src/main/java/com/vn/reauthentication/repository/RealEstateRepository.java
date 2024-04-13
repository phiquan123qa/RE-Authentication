package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RealEstateCardResponse;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RealEstateRepository extends JpaRepository<RealEstate, Long>, JpaSpecificationExecutor<RealEstate> {
    @Modifying
    @Query("UPDATE RealEstate r SET r.title =: title, r.price =: price, " +
            "r.address =: address, r.description =: description, " +
            "r.type =: type, r.legalDocument =: legalDocument, " +
            "r.interior =: interior, r.room =: room," +
            "r.bedRoom =: bedRoom,r.bathRoom =: bathRoom " +
            "WHERE r.id =: id ")
    void update(String title, Double price,
                                String address, String description,
                                String type, String legalDocument,
                                String interior, Integer room,
                                Integer bedRoom, Integer bathRoom,
                                Long id);
    @Query("SELECT re FROM RealEstate re WHERE re.user = :owner" +
            " AND (:title = '' OR LOWER(re.title) LIKE LOWER(CONCAT('%', :title, '%')))" +
            " AND (:city = '' OR LOWER(re.cityRe) = LOWER(:city))" +
            " AND (:district = '' OR LOWER(re.districtRe) = LOWER(:district))" +
            " AND (:ward = '' OR LOWER(re.wardRe) = LOWER(:ward))" +
            " ORDER BY re.dateStart DESC")
    List<RealEstate> findByOwnerAndFilters(@Param("owner") User owner,
                                           @Param("title") String title,
                                           @Param("city") String city,
                                           @Param("district") String district,
                                           @Param("ward") String ward);

    Page<RealEstate> findByStatusRe(String status, Pageable pageable);
}
