package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.Wiki;
import org.springframework.data.domain.Page;

public interface IWikiService {
    Page<Wiki> findWikisWithPaginationAndFilterAndSort(
            Integer pageNumber,
            Integer pageSize,
            String title,
            String tag,
            String sort,
            Boolean isPublished);
    Boolean disableEnableWiki(Long id, Boolean isPublished);
}
