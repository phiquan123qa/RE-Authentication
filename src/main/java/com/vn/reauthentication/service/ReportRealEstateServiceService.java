package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.ReportPostRealEstate;
import com.vn.reauthentication.repository.ReportRealEstateRepository;
import com.vn.reauthentication.service.interfaces.IReportRealEstateService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportRealEstateServiceService implements IReportRealEstateService {
    private final ReportRealEstateRepository reportRealEstateRepository;

    @Override
    public Page<ReportPostRealEstate>
    findReportRealEstateWithPaginationPending(Integer pageNumber,
                                              Integer pageSize,
                                              String status,
                                              String sort) {
        Specification<ReportPostRealEstate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("dateDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateCreate").descending());
        } else if (sort != null && sort.equals("dateAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateCreate").ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return reportRealEstateRepository.findAll(spec, pageable);
    }

    @Override
    public ReportPostRealEstate save(ReportPostRealEstate reportPostRealEstate) {
        return reportRealEstateRepository.save(reportPostRealEstate);
    }

    @Override
    public Optional<ReportPostRealEstate> findReportRealEstateById(Long id) {
        return Optional.ofNullable(reportRealEstateRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Report real estate not found")));
    }
}
