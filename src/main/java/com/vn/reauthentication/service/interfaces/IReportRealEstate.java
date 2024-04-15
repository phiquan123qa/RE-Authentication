package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.ReportPostRealEstate;
import org.springframework.data.domain.Page;

public interface IReportRealEstate {
    Page<ReportPostRealEstate> findReportRealEstateWithPaginationPending(Integer pageNumber, Integer pageSize, String status, String sort);
}
