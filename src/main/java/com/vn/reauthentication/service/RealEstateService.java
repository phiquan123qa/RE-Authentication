package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.repository.RealEstateRepository;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
                realEstateRequest.getRoom(),
                realEstateRequest.getBedRoom(),
                realEstateRequest.getBathRoom(),
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
    public void updateRealEstate(String title, Double price,
                                 Double landArea, String mainImage,
                                 String cityRe, String districtRe,
                                 String wardRe, String address,
                                 String description,
                                 LocalDate dateStart,
                                 LocalDate dateEnd, String type,
                                 String statusRe, Long id){
        realEstateRepository.update(title, price, landArea, mainImage, cityRe, districtRe, wardRe, address, description, dateStart, dateEnd, type, statusRe, id);
    }

    @Override
    public Optional<RealEstate> findRealEstateById(Long id) {
        return Optional.ofNullable(realEstateRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Real estate not found")));
    }

    @Override
    public Page<RealEstate> findRealEstateWithPaginationAndFilterAndSort(Integer pageNumber,
                                                                         Integer pageSize,
                                                                         String title,
                                                                         String type,
                                                                         String cityRe,
                                                                         String districtRe,
                                                                         String wardRe,
                                                                         String field) {
        Specification<RealEstate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (type != null && !type.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("type")), type.toLowerCase()));
            }
            if (cityRe != null && !cityRe.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("cityRe")), cityRe.toLowerCase()));
            }
            if (districtRe != null && !districtRe.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("districtRe")), districtRe.toLowerCase()));
            }
            if (wardRe != null && !wardRe.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("wardRe")), wardRe.toLowerCase()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if(field != null && !field.isEmpty()) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(field));
        }else pageable = PageRequest.of(pageNumber, pageSize);

        return realEstateRepository.findAll(spec, pageable);
    }
}
