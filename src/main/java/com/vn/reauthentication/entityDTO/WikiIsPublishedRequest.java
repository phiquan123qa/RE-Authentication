package com.vn.reauthentication.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WikiIsPublishedRequest {
    private Long id;
    private Boolean isPublished;
}
