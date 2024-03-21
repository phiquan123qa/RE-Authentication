package com.vn.reauthentication.entityDTO;

import com.vn.reauthentication.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RealEstateRequest {
    private String title;
    private Double price;
    private Double landArea;
    private String mainImage;
    private String cityRe;
    private String districtRe;
    private String wardRe;
    private String address;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String type;
    private String statusRe;
    private User user;
    private List<String> imagesList;
}
