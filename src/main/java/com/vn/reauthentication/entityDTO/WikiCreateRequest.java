package com.vn.reauthentication.entityDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WikiCreateRequest {
    private String title;
    private String content;
    private String authorName;
    private Boolean isPublished;
    private LocalDate createDate;
    private String tag;
}
