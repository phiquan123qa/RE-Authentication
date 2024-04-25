package com.vn.reauthentication.entityDTO;

import lombok.Data;

@Data
public class WikiUpdateRequest {
    private Long id;
    private String title;
    private String content;
    private String tag;
}
