package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.LikedRealEstate;
import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;

import java.util.List;

public interface ILikeRealEstateService {
    LikedRealEstate save(LikedRealEstate likedRealEstate);
    String delete(User user, RealEstate realEstate);

    Boolean existsByUserAndRealEstate(User user, RealEstate realEstate);
}
