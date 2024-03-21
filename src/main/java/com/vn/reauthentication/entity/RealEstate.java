package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "RealEstate")
@AllArgsConstructor
@NoArgsConstructor
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ElementCollection
    private List<String> imagesList;

    public RealEstate(String title, Double price, Double landArea, String mainImage, String cityRe, String districtRe, String wardRe, String address, String description, LocalDate dateStart, LocalDate dateEnd, String type, String statusRe, User user, List<String> imagesList) {
        this.title = title;
        this.price = price;
        this.landArea = landArea;
        this.mainImage = mainImage;
        this.cityRe = cityRe;
        this.districtRe = districtRe;
        this.wardRe = wardRe;
        this.address = address;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.type = type;
        this.statusRe = statusRe;
        this.user = user;
        this.imagesList = imagesList;
    }
}
