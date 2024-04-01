package com.vn.reauthentication.entityDTO;

import lombok.Data;

import java.util.List;

@Data
public class RealEstateCreateRequest {
    private String title;
    private Double price;
    private Double landArea;
    private String cityRe;
    private String districtRe;
    private String wardRe;
    private String address;
    private Integer room;
    private Integer bedRoom;
    private Integer bathRoom;
    private String description;
    private String type;
    private String legalDocument;
    private String interior;
    private List<String> imagesList;

}
