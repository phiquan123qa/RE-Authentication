package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.ReportUser;
import org.springframework.data.domain.Page;

public interface IReportUser {
    Page<ReportUser> findReportUserWithPaginationPending(Integer pageNumber, Integer pageSize, String status, String sort);
}
