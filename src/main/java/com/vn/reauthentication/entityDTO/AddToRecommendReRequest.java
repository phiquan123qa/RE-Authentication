package com.vn.reauthentication.entityDTO;

import lombok.Data;

import java.util.List;

@Data
public class AddToRecommendReRequest {
    List<Long> realEstateIds;
}
