package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entity.Wiki;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.entityDTO.WikiCreateRequest;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.repository.WikiRepository;
import com.vn.reauthentication.service.WikiService;
import com.vn.reauthentication.utility.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wiki")
public class WikiController {
    private final UserRepository userRepository;
    private final WikiRepository wikiRepository;
    private final WikiService wikiService;
    @PostMapping("/upload_image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile file) throws Exception{
        Map<String, Object> responseData = new HashMap<>();
        try {
            String imageUrl = ImageUtil.saveImage(file, "./src/main/resources/static/images/wikis/");
            responseData.put("uploaded", true);
            responseData.put("url", "/static/images/wikis/"+imageUrl);
            return responseData;
        }catch (Exception e){
            responseData.put("uploaded", false);
            return responseData;
        }
    }

    @PostMapping("/admin/upload")
    public ResponseEntity<?> upload(@RequestBody WikiCreateRequest request) {
        User user = userRepository.findByEmail(request.getAuthorName()).orElseThrow(() -> new RuntimeException("User not found"));
        Wiki wiki = new Wiki(request.getTitle(), request.getContent(), user, LocalDate.now(), true, request.getTag());
        return ResponseEntity.ok(wikiRepository.save(wiki));
    }

    @GetMapping("/findall")
    public APIResponse<Page<Wiki>> getAllWikis(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "sort", required = false) String sort) {
        Page<Wiki> wikis = wikiService.findWikisWithPaginationAndFilterAndSort(offset, pageSize, title, tag, sort);
        return new APIResponse<>(wikis.getSize(), wikis);
    }
}
