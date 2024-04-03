package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
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

    @Override
    public List<RealEstate> findRealEstateWithFilters(String title, String city, String district, String ward, boolean sortByDate) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User owner = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        List<RealEstate> listings = owner.getRealEstates();
//
//        Stream<RealEstate> filteredStream = listings.stream();
//
//        if (title != null && !title.trim().isEmpty()) {
//            filteredStream = filteredStream
//                    .filter(listing -> listing.getTitle().toLowerCase().contains(title.toLowerCase()));
//        }
//        if (city != null && !city.trim().isEmpty()) {
//            filteredStream = filteredStream
//                    .filter(listing -> city.equalsIgnoreCase(listing.getCityRe()));
//        }
//        if (district != null && !district.trim().isEmpty()) {
//            filteredStream = filteredStream
//                    .filter(listing -> district.equalsIgnoreCase(listing.getDistrictRe()));
//        }
//        if (ward != null && !ward.trim().isEmpty()) {
//            filteredStream = filteredStream
//                    .filter(listing -> ward.equalsIgnoreCase(listing.getWardRe()));
//        }
//
//        Comparator<RealEstate> dateComparator = Comparator.comparing(RealEstate::getDateStart);
//
//        return filteredStream
//                .sorted(sortByDate ? dateComparator.reversed() : dateComparator)
//                .collect(Collectors.toList());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<RealEstate> realEstates = realEstateRepository.findByOwnerAndFilters(owner, title, city, district, ward);
        if (sortByDate) {
            realEstates.sort(Comparator.comparing(RealEstate::getDateStart).reversed());
        }

        return realEstates;
    }
}
