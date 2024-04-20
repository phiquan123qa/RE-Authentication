package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.LikedRealEstate;
import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.RealEstateRecommend;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RealEstateCardResponse;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.entityDTO.RealEstateUpdateRequest;
import com.vn.reauthentication.repository.LikedRealEstateRepository;
import com.vn.reauthentication.repository.RealEstateRecommedRepository;
import com.vn.reauthentication.repository.RealEstateRepository;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealEstateService implements IRealEstateService {
    private final RealEstateRepository realEstateRepository;
    private final UserRepository userRepository;
    private final RealEstateRecommedRepository recommedRepository;
    private final LikedRealEstateRepository likedRealEstateRepository;


    @Override
    public List<RealEstate> getAllRealEstates() {
        return realEstateRepository.findAll();
    }

    @Override
    public RealEstate createRealEstate(RealEstateRequest realEstateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Assuming the username is the email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var realEstate = new RealEstate(
                realEstateRequest.getTitle(),
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
                LocalDate.now(),
                LocalDate.ofEpochDay(LocalDate.now().plusDays(10).toEpochDay()),
                realEstateRequest.getType(),
                realEstateRequest.getLegalDocument(),
                realEstateRequest.getInterior(),
                "INACTIVE",
                user,
                realEstateRequest.getImagesList());
        return realEstateRepository.save(realEstate);
    }

    @Override
    public RealEstate updateRealEstate(RealEstateUpdateRequest request) {
        RealEstate realEstate = realEstateRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Real estate not found"));
        realEstate.setTitle(request.getTitle());
        realEstate.setPrice(request.getPrice());
        realEstate.setAddress(request.getAddress());
        realEstate.setDescription(request.getDescription());
        realEstate.setType(request.getType());
        realEstate.setLegalDocument(request.getLegalDocument());
        realEstate.setInterior(request.getInterior());
        realEstate.setRoom(request.getRoom());
        realEstate.setBedRoom(request.getBedRoom());
        realEstate.setBathRoom(request.getBathRoom());
        return realEstateRepository.save(realEstate);
    }

    @Override
    public Optional<RealEstate> findRealEstateById(Long id) {
        return Optional.ofNullable(realEstateRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Real estate not found")));
    }

    @Override
    public Page<RealEstate> findRealEstateWithPaginationAndFilterAndSort(Integer pageNumber,
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
                                                                         Integer maxPrice) {
        Specification<RealEstate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("statusRe"), "ACTIVE"));
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
            if(minArea != null && maxArea != null){
                if(minArea.equals(maxArea) && minArea == 500){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("landArea"), minArea));
                } else if (minArea == 0 && maxArea == 500){
                    // No action needed when both minArea and maxArea are null
                }
                else{
                    predicates.add(cb.between(root.get("landArea"), minArea, maxArea));
                }
            }
            if(minPrice != null && maxPrice != null){
                if(minPrice.equals(maxPrice) && minPrice == 0){
                    predicates.add(cb.isNull(root.get("price")));
                } else if (minPrice.equals(maxPrice) && minPrice == 1000000){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
                } else {
                    predicates.add(cb.between(root.get("price"), minPrice, maxPrice));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("dateDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").descending());
        } else if (sort != null && sort.equals("dateAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").ascending());
        } else if (sort != null && sort.equals("priceDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").descending());
        } else if (sort != null && sort.equals("priceAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return realEstateRepository.findAll(spec, pageable);
    }
    @Override
    public Page<RealEstate> findRealEstateWithPaginationAndFilterAndSortAdmin(Integer pageNumber,
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
                                                                         Integer maxPrice) {
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
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("statusRe")), status.toLowerCase()));
            }
            if(minArea != null && maxArea != null){
                if(minArea.equals(maxArea) && minArea == 500){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("landArea"), minArea));
                } else if (minArea == 0 && maxArea == 500){
                    // No action needed when both minArea and maxArea are null
                }
                else{
                    predicates.add(cb.between(root.get("landArea"), minArea, maxArea));
                }
            }
            if(minPrice != null && maxPrice != null){
                if(minPrice.equals(maxPrice) && minPrice == 0){
                    predicates.add(cb.isNull(root.get("price")));
                } else if (minPrice.equals(maxPrice) && minPrice == 1000000){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
                } else {
                    predicates.add(cb.between(root.get("price"), minPrice, maxPrice));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("dateDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").descending());
        } else if (sort != null && sort.equals("dateAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").ascending());
        } else if (sort != null && sort.equals("priceDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").descending());
        } else if (sort != null && sort.equals("priceAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return realEstateRepository.findAll(spec, pageable);
    }

    @Override
    public Page<RealEstateCardResponse> findRealEstateWithFiltersOfUser(Integer pageNumber,
                                                                        Integer pageSize,
                                                                        String title,
                                                                        String type,
                                                                        String city,
                                                                        String district,
                                                                        String ward,
                                                                        String sort,
                                                                        User user) {
        Specification<RealEstate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("user"), user));
            //predicates.add(cb.equal(root.get("statusRe"), "ACTIVE"));
            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (type != null && !type.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("type")), type.toLowerCase()));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("cityRe")), city.toLowerCase()));
            }
            if (district != null && !district.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("districtRe")), district.toLowerCase()));
            }
            if (ward != null && !ward.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("wardRe")), ward.toLowerCase()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("dateDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").descending());
        } else if (sort != null && sort.equals("dateAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").ascending());
        } else if (sort != null && sort.equals("priceDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").descending());
        } else if (sort != null && sort.equals("priceAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }


        Page<RealEstate> realEstatePage = realEstateRepository.findAll(spec, pageable);
        return realEstatePage.map(realEstate -> new RealEstateCardResponse(
                realEstate.getId(),
                realEstate.getTitle(),
                realEstate.getPrice(),
                realEstate.getLandArea(),
                realEstate.getMainImage(),
                realEstate.getCityRe(),
                realEstate.getDistrictRe(),
                realEstate.getWardRe(),
                realEstate.getAddress(),
                realEstate.getRoom(),
                realEstate.getBedRoom(),
                realEstate.getBathRoom(),
                realEstate.getDescription(),
                realEstate.getLegalDocument(),
                realEstate.getInterior(),
                realEstate.getDateStart(),
                realEstate.getDateEnd(),
                realEstate.getStatusRe(),
                realEstate.getType()
        ));
    }

    @Override
    public Page<RealEstate> findRealEstateWithPaginationAndFilterAndSortAdminAccept(Integer pageNumber, Integer pageSize) {
        return realEstateRepository.findByStatusRe("INACTIVE", PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Boolean statusRealEstate(Long id, String status) {
        RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
        if (realEstate != null) {
            realEstate.setStatusRe(status);
            realEstateRepository.save(realEstate);
            return true;
        }
        return false;
    }

    @Override
    public void processRealEstateChanges(List<Long> realEstateIds) {
        List<RealEstate> existingRealEstates  = recommedRepository.findAllRealEstate();
        List<Long> existingIds = existingRealEstates .stream().map(RealEstate::getId).toList();
        for(Long id : realEstateIds) {
            if(!existingIds.contains(id)) {
                RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
                recommedRepository.save(new RealEstateRecommend(realEstate));
            }
        }
        List<Long> idsToRemove = existingIds.stream().filter(id -> !realEstateIds.contains(id)).toList();
        for (Long id : idsToRemove) {
            RealEstate realEstateToRemove = existingRealEstates.stream().filter(re -> re.getId().equals(id)).findFirst().orElse(null);
            if (realEstateToRemove != null) {
                recommedRepository.deleteByRealEstate(realEstateToRemove);
            }
        }

    }

    @Override
    public List<RealEstate> findAllByUser(User user) {
        return likedRealEstateRepository.findAllByUser(user).stream().map(LikedRealEstate::getRealEstate).filter(realEstate -> realEstate.getStatusRe().equals("ACTIVE")).toList();
    }

    @Override
    public List<RealEstate> findAllRealEstateActive() {
        return recommedRepository.findAllRealEstate().stream().filter(realEstate -> Objects.equals(realEstate.getStatusRe(), "ACTIVE")).toList();
    }
}
