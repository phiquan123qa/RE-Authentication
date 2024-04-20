package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.ReportPostRealEstate;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IReportRealEstateService {
    Page<ReportPostRealEstate> findReportRealEstateWithPaginationPending(Integer pageNumber, Integer pageSize, String status, String sort);
    ReportPostRealEstate save(ReportPostRealEstate reportPostRealEstate);
    Optional<ReportPostRealEstate> findReportRealEstateById(Long id);
}
