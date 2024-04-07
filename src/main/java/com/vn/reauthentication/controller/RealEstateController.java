package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.entityDTO.RealEstateRequest;
import com.vn.reauthentication.entityDTO.RealEstateUpdateRequest;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import com.vn.reauthentication.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/re")
public class RealEstateController {
    private final IRealEstateService service;
    private final UserRepository userRepository;

    @GetMapping("/findall")
    public APIResponse<Page<RealEstate>> getAllRealEstates(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "typeRe", required = false) String type,
            @RequestParam(name = "city", required = false) String cityRe,
            @RequestParam(name = "district", required = false) String districtRe,
            @RequestParam(name = "ward", required = false) String wardRe,
            @RequestParam(required = false) String field) {
        Page<RealEstate> realEstates = service.findRealEstateWithPaginationAndFilterAndSort(
                offset, pageSize, title, type, cityRe, districtRe, wardRe, field);
        return new APIResponse<>(realEstates.getSize(), realEstates);
    }
    @PostMapping("/upload-images")
    public ResponseEntity<?> uploadImages(@RequestParam("imagesList") MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>() ;
        for (MultipartFile file : files) {
            String imageUrl = ImageUtil.saveImage(file, "./src/main/resources/static/images/real_estate_images");
            if (!imageUrl.isEmpty()) {
                imageUrls.add(imageUrl);
            }
        }
        return ResponseEntity.ok(Collections.singletonMap("imageUrls", imageUrls));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRealEstate(@RequestBody RealEstateRequest request) {
        RealEstate realEstate = service.createRealEstate(request);
        return ResponseEntity.ok(realEstate);
    }

    @GetMapping("/listRe")
    public ResponseEntity<?> getListRe(@RequestParam(defaultValue = "0") int offset,
                                       @RequestParam(defaultValue = "9") int pageSize,
                                       @RequestParam(required = false, defaultValue = "") String title,
                                       @RequestParam(required = false, defaultValue = "") String type,
                                       @RequestParam(required = false, defaultValue = "") String city,
                                       @RequestParam(required = false, defaultValue = "") String district,
                                       @RequestParam(required = false, defaultValue = "") String ward,
                                       @RequestParam(required = false, defaultValue = "" ) String sort) {
        String accName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(accName).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(service.findRealEstateWithFiltersOfUser(offset, pageSize,title, type, city, district, ward, sort, user));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRealEstate(@RequestBody RealEstateUpdateRequest request) {
        RealEstate realEstate = service.updateRealEstate(request);
        return ResponseEntity.ok(realEstate);
    }
}
