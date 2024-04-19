package com.vn.reauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull
    private String title;
    private Double price;
    @NonNull
    private Double landArea;
    private String mainImage;
    @NonNull
    private String cityRe;
    @NonNull
    private String districtRe;
    @NonNull
    private String wardRe;
    @NonNull
    private String address;
    @NonNull
    private Integer room;
    @NonNull
    private Integer bedRoom;
    @NonNull
    private Integer bathRoom;
    @NonNull
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    @NonNull
    private String type;
    @NonNull
    private String legalDocument;
    @NonNull
    private String interior;
    @NonNull
    private String statusRe;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<LikedRealEstate> likedByUsers;
    @ElementCollection
    private List<String> imagesList;
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReportPostRealEstate> receivedReportsPost;
    @OneToOne(mappedBy = "realEstate", cascade = CascadeType.ALL)
    @JsonIgnore
    private RealEstateRecommend recommend;

    public RealEstate(String title, Double price,
                      Double landArea, String mainImage,
                      String cityRe, String districtRe,
                      String wardRe, String address,
                      Integer room, Integer bedroom,
                      Integer bathRoom,
                      String description,
                      LocalDate dateStart,
                      LocalDate dateEnd, String type,
                      String legalDocument, String interior,
                      String statusRe, User user,
                      List<String> imagesList) {
        this.title = title;
        this.price = price;
        this.landArea = landArea;
        this.mainImage = mainImage;
        this.cityRe = cityRe;
        this.districtRe = districtRe;
        this.wardRe = wardRe;
        this.address = address;
        this.room = room;
        this.bedRoom = bedroom;
        this.bathRoom = bathRoom;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.type = type;
        this.legalDocument = legalDocument;
        this.interior = interior;
        this.statusRe = statusRe;
        this.user = user;
        this.imagesList = imagesList;
    }
}
