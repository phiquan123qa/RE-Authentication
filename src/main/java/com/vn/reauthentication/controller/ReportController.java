package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.ReportPostRealEstate;
import com.vn.reauthentication.entity.ReportUser;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.entityDTO.RealEstateStatusRequest;
import com.vn.reauthentication.entityDTO.ReportRealEstateRequest;
import com.vn.reauthentication.entityDTO.ReportUserRequest;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import com.vn.reauthentication.service.interfaces.IReportRealEstateService;
import com.vn.reauthentication.service.interfaces.IReportUserService;
import com.vn.reauthentication.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final IRealEstateService realEstateService;
    private final IUserService userService;
    private final IReportRealEstateService reportRealEstateService;
    private final IReportUserService reportUserService;
    @PostMapping("/re")
    public ResponseEntity<?> createReportRe(@RequestBody ReportRealEstateRequest request) {
        RealEstate realEstate = realEstateService.findRealEstateById(request.getRealEstateId()).orElseThrow(() -> new RuntimeException("Real estate not found"));
        ReportPostRealEstate report = new ReportPostRealEstate(request.getContent(), request.getType(), request.getStatus(), request.getEmailAuthor(), request.getPhoneAuthor(), realEstate);
        return ResponseEntity.ok(reportRealEstateService.save(report));
    }
    @PostMapping("/user")
    public ResponseEntity<?> createReportUser(@RequestBody ReportUserRequest request) {
        User user = userService.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ReportUser report = new ReportUser(request.getContent(), request.getType(), request.getStatus(), request.getEmailAuthor(), request.getPhoneAuthor(), user);
        return ResponseEntity.ok(reportUserService.save(report));
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
        ReportPostRealEstate report = reportRealEstateService.findReportRealEstateById(request.getId()).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(request.getStatus());
        return ResponseEntity.ok(reportRealEstateService.save(report));
    }
    @PostMapping("/admin/statususer")
    public ResponseEntity<?> changestatususer(@RequestBody RealEstateStatusRequest request) {
        ReportUser report = reportUserService.findReportUserById(request.getId()).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(request.getStatus());
        return ResponseEntity.ok(reportUserService.save(report));
    }
}
