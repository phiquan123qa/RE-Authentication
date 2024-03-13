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
    private Integer price;
    private Integer landArea;
    private String mainImage;
    private String cityRe;
    private String districtRe;
    private String wardRe;
    private String address;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer type;
    private String statusRe;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ElementCollection
    private List<String> imagesList;
}
