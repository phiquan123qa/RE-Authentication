package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/re")
public class RealEstateController {
    private final IRealEstateService service;

    @GetMapping("/findall/{offset}/{pageSize}//{title}/{cityRe}/{districtRe}/{wardRe}/{field}")
    public APIResponse<Page<RealEstate>> getAllRealEstates(@PathVariable int offset,
                                                           @PathVariable int pageSize,
                                                           @PathVariable String title,
                                                           @PathVariable String cityRe,
                                                           @PathVariable String districtRe,
                                                           @PathVariable String wardRe,
                                                           @PathVariable String field) {
        Page<RealEstate> realEstates = service.findRealEstateWithPaginationAndFilterAndSort(
                offset, pageSize, title, cityRe, districtRe, wardRe, field);
        return new APIResponse<>(realEstates.getSize(), realEstates);
    }
}
