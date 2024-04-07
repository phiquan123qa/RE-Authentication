package com.vn.reauthentication.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateUpdateRequest {
    private Long id;
    private String title;
    private String type;
    private String address;
    private Double price;
    private String legalDocument;
    private String interior;
    private String description;
    private Integer room;
    private Integer bedRoom;
    private Integer bathRoom;
}
