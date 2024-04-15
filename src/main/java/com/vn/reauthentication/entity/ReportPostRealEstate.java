package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ReportPostRealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String type;
    private String status;
    private String emailAuthor;
    private String phoneAuthor;
    private LocalDate dateCreate = LocalDate.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "re_id")
    private RealEstate realEstate;

    public ReportPostRealEstate(String content, String type, String status, String emailAuthor, String phoneAuthor, RealEstate realEstate) {
        this.content = content;
        this.type = type;
        this.status = status;
        this.emailAuthor = emailAuthor;
        this.phoneAuthor = phoneAuthor;
        this.realEstate = realEstate;
    }
}
