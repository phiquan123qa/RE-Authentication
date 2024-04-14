package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "RealEstateRecommend")
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateRecommend {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "real_estate_recommed_id", referencedColumnName = "id")
    private RealEstate realEstate;

    public RealEstateRecommend(RealEstate realEstate) {
        this.realEstate = realEstate;
    }
}
