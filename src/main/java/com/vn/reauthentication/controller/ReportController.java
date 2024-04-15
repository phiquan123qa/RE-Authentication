package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.ReportPostRealEstate;
import com.vn.reauthentication.entity.ReportUser;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.entityDTO.RealEstateStatusRequest;
import com.vn.reauthentication.entityDTO.ReportRealEstateRequest;
import com.vn.reauthentication.entityDTO.ReportUserRequest;
import com.vn.reauthentication.repository.RealEstateRepository;
import com.vn.reauthentication.repository.ReportRealEstateRepository;
import com.vn.reauthentication.repository.ReportUserRepository;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.ReportRealEstateService;
import com.vn.reauthentication.service.ReportUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final RealEstateRepository realEstateRepository;
    private final UserRepository userRepository;
    private final ReportRealEstateRepository reportRealEstateRepository;
    private final ReportUserRepository reportUserRepository;
    private final ReportRealEstateService reportRealEstateService;
    private final ReportUserService reportUserService;
    @PostMapping("/re")
    public ResponseEntity<?> createReportRe(@RequestBody ReportRealEstateRequest request) {
        RealEstate realEstate = realEstateRepository.findById(request.getRealEstateId()).orElseThrow(() -> new RuntimeException("RealEstate not found"));
        ReportPostRealEstate report = new ReportPostRealEstate(request.getContent(), request.getType(), request.getStatus(), request.getEmailAuthor(), request.getPhoneAuthor(), realEstate);
        return ResponseEntity.ok(reportRealEstateRepository.save(report));
    }
    @PostMapping("/user")
    public ResponseEntity<?> createReportUser(@RequestBody ReportUserRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ReportUser report = new ReportUser(request.getContent(), request.getType(), request.getStatus(), request.getEmailAuthor(), request.getPhoneAuthor(), user);
        return ResponseEntity.ok(reportUserRepository.save(report));
    }
    @GetMapping("/admin/re")
    public APIResponse<Page<ReportPostRealEstate>> getAllReportRe(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "type", required = false) String type) {
        Page<ReportPostRealEstate> reports = reportRealEstateService.findReportRealEstateWithPaginationPending(
                offset, pageSize, status, type
        );
        return new APIResponse<>(reports.getSize(), reports);
    }
    @GetMapping("/admin/user")
    public APIResponse<Page<ReportUser>> getAllReportUser(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "type", required = false) String type) {
        Page<ReportUser> reports = reportUserService.findReportUserWithPaginationPending(
                offset, pageSize, status, type);
        return new APIResponse<>(reports.getSize(), reports);
    }
    @PostMapping("/admin/statusre")
    public ResponseEntity<?> changestatusre(@RequestBody RealEstateStatusRequest request) {
        ReportPostRealEstate report = reportRealEstateRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(request.getStatus());
        return ResponseEntity.ok(reportRealEstateRepository.save(report));
    }
    @PostMapping("/admin/statususer")
    public ResponseEntity<?> changestatususer(@RequestBody RealEstateStatusRequest request) {
        ReportUser report = reportUserRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(request.getStatus());
        return ResponseEntity.ok(reportUserRepository.save(report));
    }
}
