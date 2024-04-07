package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RealEstateCardResponse;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.entityDTO.RealEstateUpdateRequest;
import com.vn.reauthentication.repository.RealEstateRepository;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RealEstateService implements IRealEstateService {
    private final RealEstateRepository realEstateRepository;
    private final UserRepository userRepository;

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
        if (field != null && !field.isEmpty()) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(field));
        } else pageable = PageRequest.of(pageNumber, pageSize);

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
                realEstate.getType()
        ));
    }

}
