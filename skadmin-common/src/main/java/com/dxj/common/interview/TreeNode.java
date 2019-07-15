package com.dxj.common.interview;

import lombok.Data;

import java.util.List;

/**
 * List 实现树形结构
 *
 * @Author: dxj
 * @Date: 2019-07-15 08:51
 */
@Data
class TreeNode {

    private String id;
    private String parentId;
    private String name;
    private List<TreeNode> children;

    TreeNode(String id, String name, String parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    TreeNode(String id, String name, TreeNode parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
    }


}
