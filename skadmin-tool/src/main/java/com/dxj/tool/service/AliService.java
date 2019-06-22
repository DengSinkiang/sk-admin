package com.dxj.tool.service;

import com.dxj.tool.domain.AliConfig;
import com.dxj.tool.domain.AliContent;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-06-21 21:09
 */
@Service
public class AliService {
    public Void find() {
        return null;
    }

    public AliContent upload(MultipartFile file, Object o) {
        return null;
    }

    public Object queryAll(AliContent resources, Pageable pageable) {
        return null;
    }

    public void update(AliConfig aliConfig) {

    }

    public Object findByContentId(Long id) {
        return null;
    }

    public Object download(Object byContentId, Object o) {
        return null;
    }

    public void deleteAll(Long[] ids, Object o) {

    }

    public void synchronize(Object o) {

    }

    public void delete(Object byContentId, Object o) {

    }
}
