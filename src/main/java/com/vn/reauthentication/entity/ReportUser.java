package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ReportUser {
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
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    public ReportUser(String content, String type, String status, String emailAuthor, String phoneAuthor, User reportedUser) {
        this.content = content;
        this.type = type;
        this.status = status;
        this.emailAuthor = emailAuthor;
        this.phoneAuthor = phoneAuthor;
        this.reportedUser = reportedUser;
    }
}
