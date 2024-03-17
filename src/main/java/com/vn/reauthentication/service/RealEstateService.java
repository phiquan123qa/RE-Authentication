package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.repository.RealEstateRepository;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealEstateService implements IRealEstateService {
    private final RealEstateRepository realEstateRepository;
    @Override
    public List<RealEstate> getAllRealEstates() {
        return realEstateRepository.findAll();
    }

    @Override
    public RealEstate createRealEstate(RealEstateRequest realEstateRequest) {
        var realEstate = new RealEstate(realEstateRequest.getTitle(),
                realEstateRequest.getPrice(),
                realEstateRequest.getLandArea(),
                realEstateRequest.getMainImage(),
                realEstateRequest.getCityRe(),
                realEstateRequest.getDistrictRe(),
                realEstateRequest.getWardRe(),
                realEstateRequest.getAddress(),
                realEstateRequest.getDescription(),
                realEstateRequest.getDateStart(),
                realEstateRequest.getDateEnd(),
                realEstateRequest.getType(),
                realEstateRequest.getStatusRe(),
                realEstateRequest.getUser(),
                realEstateRequest.getImagesList());
        return realEstateRepository.save(realEstate);
    }

    @Override
    public void updateRealEstate(String title, Integer price,
                                 Integer landArea, String mainImage,
                                 String cityRe, String districtRe,
                                 String wardRe, String address,
                                 String description,
                                 LocalDate dateStart,
                                 LocalDate dateEnd, Integer type,
                                 String statusRe, Long id){
        realEstateRepository.update(title, price, landArea, mainImage, cityRe, districtRe, wardRe, address, description, dateStart, dateEnd, type, statusRe, id);
    }

    @Override
    public Optional<RealEstate> findRealEstateById(Long id) {
        return Optional.ofNullable(realEstateRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Real estate not found")));
    }
}
