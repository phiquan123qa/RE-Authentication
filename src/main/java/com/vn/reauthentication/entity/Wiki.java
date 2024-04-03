package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String mainImage;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate date;
    private LocalDate lastUpdate;
    @ElementCollection
    private List<String> imagesList;
    @ElementCollection
    private List<String> attachment;
    private Boolean isPublished;
    private String tag;
}
