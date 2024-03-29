package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/re")
public class RealEstateController {
    private final IRealEstateService service;

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
}
