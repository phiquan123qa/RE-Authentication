package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RealEstateCardResponse;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.entityDTO.RealEstateUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface IRealEstateService {
    List<RealEstate> getAllRealEstates();

    RealEstate createRealEstate(RealEstateRequest realEstateRequest);

    RealEstate updateRealEstate(RealEstateUpdateRequest request);

    Optional<RealEstate> findRealEstateById(Long id);
    Page<RealEstate>
    findRealEstateWithPaginationAndFilterAndSort(Integer pageNumber,
                                                 Integer pageSize,
                                                 String title,
                                                 String type,
                                                 String cityRe,
                                                 String districtRe,
                                                 String wardRe,
                                                 String sort,
                                                 Integer minArea,
                                                 Integer maxArea,
                                                 Integer minPrice,
                                                 Integer maxPrice);
    Page<RealEstate> findRealEstateWithPaginationAndFilterAndSortAdmin(Integer pageNumber,
                                                                       Integer pageSize,
                                                                       String title,
                                                                       String type,
                                                                       String cityRe,
                                                                       String districtRe,
                                                                       String wardRe,
                                                                       String sort,
                                                                       String status,
                                                                       Integer minArea,
                                                                       Integer maxArea,
                                                                       Integer minPrice,
                                                                       Integer maxPrice);
    Page<RealEstateCardResponse>
    findRealEstateWithFiltersOfUser(Integer pageNumber,
                                    Integer pageSize,
                                    String title,
                                    String type,
                                    String city,
                                    String district,
                                    String ward,
                                    String sort,
                                    User user);
    Boolean statusRealEstate(Long id, String status);
}
