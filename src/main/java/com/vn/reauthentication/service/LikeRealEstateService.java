package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.LikedRealEstate;
import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.repository.LikedRealEstateRepository;
import com.vn.reauthentication.service.interfaces.ILikeRealEstateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeRealEstateService implements ILikeRealEstateService {
    private final LikedRealEstateRepository likedRealEstateRepository;

    @Override
    public LikedRealEstate save(LikedRealEstate likedRealEstate) {
        return likedRealEstateRepository.save(likedRealEstate);
    }

    @Override
    public String delete(User user, RealEstate realEstate) {
        likedRealEstateRepository.delete(user, realEstate);
        return "Delete success";
    }

    @Override
    public Boolean existsByUserAndRealEstate(User user, RealEstate realEstate) {
        return likedRealEstateRepository.existsByUserAndRealEstate(user, realEstate);
    }
}
