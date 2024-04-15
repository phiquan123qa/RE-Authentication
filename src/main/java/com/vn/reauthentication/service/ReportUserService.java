package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.ReportPostRealEstate;
import com.vn.reauthentication.entity.ReportUser;
import com.vn.reauthentication.repository.ReportUserRepository;
import com.vn.reauthentication.service.interfaces.IReportUser;
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

@Service
@RequiredArgsConstructor
public class ReportUserService implements IReportUser {
    private final ReportUserRepository reportUserRepository;
    @Override
    public Page<ReportUser> findReportUserWithPaginationPending(Integer pageNumber, Integer pageSize, String status, String sort) {
        Specification<ReportUser> spec = (root, query, cb) -> {
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
        return reportUserRepository.findAll(spec, pageable);
    }
}
