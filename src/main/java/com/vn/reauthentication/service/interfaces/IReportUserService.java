package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.ReportUser;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IReportUserService {
    Page<ReportUser> findReportUserWithPaginationPending(Integer pageNumber, Integer pageSize, String status, String sort);
    ReportUser save(ReportUser reportUser);
    Optional<ReportUser> findReportUserById(Long id);
}
