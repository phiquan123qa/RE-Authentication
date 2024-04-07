package com.vn.reauthentication.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateCardResponse {
    private Long id;
    private String title;
    private Double price;
    private Double landArea;
    private String mainImage;
    private String cityRe;
    private String districtRe;
    private String wardRe;
    private String address;
    private Integer room;
    private Integer bedRoom;
    private Integer bathRoom;
    private String description;
    private String legalDocument;
    private String interior;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String type;

}
