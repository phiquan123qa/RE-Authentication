package com.vn.reauthentication.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateRequestFind {
    private String title;
    private String cityRe;
    private String districtRe;
    private String wardRe;
}
