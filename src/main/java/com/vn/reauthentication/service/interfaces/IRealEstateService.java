package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.RealEstateRequest;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface IRealEstateService {
    List<RealEstate> getAllRealEstates();

    RealEstate createRealEstate(RealEstateRequest realEstateRequest);

    void updateRealEstate(String title, Integer price,Integer landArea,
                          String mainImage, String cityRe, String districtRe,
                          String wardRe, String address, String description,
                          LocalDate dateStart, LocalDate dateEnd,
                          Integer type, String statusRe, Long id);
    Optional<RealEstate> findRealEstateById(Long id);
}
