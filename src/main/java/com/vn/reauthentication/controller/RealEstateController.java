package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.LikedRealEstate;
import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.*;
import com.vn.reauthentication.repository.LikedRealEstateRepository;
import com.vn.reauthentication.repository.RealEstateRecommedRepository;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.ILikeRealEstateService;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import com.vn.reauthentication.service.interfaces.IUserService;
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
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/re")
public class RealEstateController {
    private final IRealEstateService realEstateService;
    private final IUserService userService;
    private final ILikeRealEstateService likeRealEstateService;
    @GetMapping("/findall")
    public APIResponse<Page<RealEstate>> getAllRealEstates(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "typeRe", required = false) String type,
            @RequestParam(name = "city", required = false) String cityRe,
            @RequestParam(name = "district", required = false) String districtRe,
            @RequestParam(name = "ward", required = false) String wardRe,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "minArea", required = false) Integer minArea,
            @RequestParam(name = "maxArea", required = false) Integer maxArea,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        Page<RealEstate> realEstates = realEstateService.findRealEstateWithPaginationAndFilterAndSort(
                offset, pageSize, title, type, cityRe, districtRe, wardRe, sort, minArea, maxArea, minPrice, maxPrice);
        return new APIResponse<>(realEstates.getSize(), realEstates);
    }
    @GetMapping("/admin/findallre")
    public APIResponse<Page<RealEstate>> getAllRealEstatesAdmin(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "typeRe", required = false) String type,
            @RequestParam(name = "city", required = false) String cityRe,
            @RequestParam(name = "district", required = false) String districtRe,
            @RequestParam(name = "ward", required = false) String wardRe,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "minArea", required = false) Integer minArea,
            @RequestParam(name = "maxArea", required = false) Integer maxArea,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        Page<RealEstate> realEstates = realEstateService.findRealEstateWithPaginationAndFilterAndSortAdmin(
                offset, pageSize, title, type, cityRe, districtRe, wardRe, sort, status, minArea, maxArea, minPrice, maxPrice);
        return new APIResponse<>(realEstates.getSize(), realEstates);
    }
    @GetMapping("/admin/findallreaccept")
    public APIResponse<Page<RealEstate>> getAllRealEstatesAdminAccept(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "9") int pageSize){
        Page<RealEstate> realEstates = realEstateService.findRealEstateWithPaginationAndFilterAndSortAdminAccept(
                offset, pageSize);
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
        RealEstate realEstate = realEstateService.createRealEstate(request);
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
        User user = userService.findUserByEmail(accName).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(realEstateService.findRealEstateWithFiltersOfUser(offset, pageSize,title, type, city, district, ward, sort, user));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRealEstate(@RequestBody RealEstateUpdateRequest request) {
        RealEstate realEstate = realEstateService.updateRealEstate(request);
        return ResponseEntity.ok(realEstate);
    }

    @PostMapping("/favorite/add")
    public ResponseEntity<?> addRealEstateToFavorite(@RequestBody AddFavoriteRequest request) {
        User user = userService.findUserByEmail(request.getUserName()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        RealEstate realEstate = realEstateService.findRealEstateById(request.getRealEstateId()).orElseThrow();
        LikedRealEstate likedRealEstate = new LikedRealEstate(user, realEstate);
        return ResponseEntity.ok(likeRealEstateService.save(likedRealEstate));
    }
    @PostMapping("/favorite/delete")
    public ResponseEntity<?> deleteRealEstateFromFavorite(@RequestBody AddFavoriteRequest request) {
        User user = userService.findUserByEmail(request.getUserName()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        RealEstate realEstate = realEstateService.findRealEstateById(request.getRealEstateId()).orElseThrow();
        return ResponseEntity.ok(likeRealEstateService.delete(user, realEstate));
    }
    @PostMapping("/favorite/check")
    public ResponseEntity<?> checkRealEstateInFavorite(@RequestBody AddFavoriteRequest request) {
        User user = userService.findUserByEmail(request.getUserName()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        RealEstate realEstate = realEstateService.findRealEstateById(request.getRealEstateId()).orElseThrow();
        return ResponseEntity.ok(likeRealEstateService.existsByUserAndRealEstate(user, realEstate));
    }
    @GetMapping("/favorite/list")
    public APIResponse<?> getListFavorite() {
        String accName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(accName).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        List<RealEstate> likedRealEstates = realEstateService.findAllByUser(user);
        return new APIResponse<>(likedRealEstates.size(),likedRealEstates);
    }

    @PostMapping("/admin/status")
    public ResponseEntity<?> changeRealEstateStatus(@RequestBody RealEstateStatusRequest request) {
        return ResponseEntity.ok(realEstateService.statusRealEstate(request.getId(), request.getStatus()));
    }

    @GetMapping("/admin/findallredata")
    public APIResponse<List<RealEstate>> getAllRealEstatesAdminData(){
        List<RealEstate> realEstates = realEstateService.getAllRealEstates().stream().filter(realEstate -> Objects.equals(realEstate.getStatusRe(), "ACTIVE")).toList();
        return new APIResponse<>(realEstates.size(), realEstates);
    }

    @GetMapping("/admin/findrerecommenddata")
    public APIResponse<List<RealEstate>> getAllRealEstatesAdminRecommendData(){
        List<RealEstate> realEstates = realEstateService.findAllRealEstateActive();
        return new APIResponse<>(realEstates.size(), realEstates);
    }
    @GetMapping("/findrerecommenddataforuser")
    public APIResponse<List<RealEstate>> getAllRealEstatesRecommendDataForUser(){
        List<RealEstate> realEstates = realEstateService.findAllRealEstateActive();
        return new APIResponse<>(realEstates.size(), realEstates);
    }

    @PostMapping("/admin/addrecomre")
    public ResponseEntity<?> addRecommendRealEstate(@RequestBody AddToRecommendReRequest request) {
        realEstateService.processRealEstateChanges(request.getRealEstateIds());
        return ResponseEntity.ok("Changes saved successfully");
    }

}
