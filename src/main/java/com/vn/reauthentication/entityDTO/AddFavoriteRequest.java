package com.vn.reauthentication.entityDTO;

import lombok.Data;

@Data
public class AddFavoriteRequest {
    private String userName;
    private Long realEstateId;
}
