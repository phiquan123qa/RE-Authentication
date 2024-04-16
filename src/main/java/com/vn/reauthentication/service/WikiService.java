package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.Wiki;
import com.vn.reauthentication.repository.WikiRepository;
import com.vn.reauthentication.service.interfaces.IWikiService;
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
public class WikiService implements IWikiService {
    private final WikiRepository wikiRepository;
    @Override
    public Page<Wiki> findWikisWithPaginationAndFilterAndSort(Integer pageNumber, Integer pageSize, String title, String tag, String sort) {
        Specification<Wiki> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (tag != null && !tag.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("tag")), tag.toLowerCase()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("dateDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").descending());
        } else if (sort != null && sort.equals("dateAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateStart").ascending());
        }else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return wikiRepository.findAll(spec, pageable);
    }
}