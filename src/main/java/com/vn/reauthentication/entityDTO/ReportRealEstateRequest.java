package com.vn.reauthentication.entityDTO;

import lombok.Data;

@Data
public class ReportRealEstateRequest {
    private String content;
    private String type;
    private String status;
    private String emailAuthor;
    private String phoneAuthor;
    private Long realEstateId;
}
