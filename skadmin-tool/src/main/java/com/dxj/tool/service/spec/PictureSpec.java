package com.dxj.tool.service.spec;

import com.dxj.tool.domain.Picture;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2019-05-31
 */
public class PictureSpec {
    public static Specification<Picture> getSpec(Picture picture) {
        return (Specification<Picture>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if(!ObjectUtils.isEmpty(picture.getFilename())){
                //模糊
                list.add(cb.like(root.get("filename").as(String.class),"%"+picture.getFilename()+"%"));
            }
            if(!ObjectUtils.isEmpty(picture.getUsername())){
                //模糊
                list.add(cb.like(root.get("username").as(String.class),"%"+picture.getUsername()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
