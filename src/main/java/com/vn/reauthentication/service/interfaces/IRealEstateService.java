package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface IRealEstateService {
    List<RealEstate> getAllRealEstates();

    RealEstate createRealEstate(RealEstateRequest realEstateRequest);

//    void updateRealEstate(String title, Integer price,Integer landArea,
//                          String mainImage, String cityRe, String districtRe,
//                          String wardRe, String address, String description,
//                          LocalDate dateStart, LocalDate dateEnd,
//                          String type, String statusRe, Long id);

    void updateRealEstate(String title, Double price,
                          Double landArea, String mainImage,
                          String cityRe, String districtRe,
                          String wardRe, String address,
                          String description,
                          LocalDate dateStart,
                          LocalDate dateEnd, String type,
                          String statusRe, Long id);

    Optional<RealEstate> findRealEstateById(Long id);
    Page<RealEstate> findRealEstateWithPaginationAndFilterAndSort(Integer pageNumber, Integer pageSize, String title, String cityRe, String districtRe, String wardRe, String field);
}
