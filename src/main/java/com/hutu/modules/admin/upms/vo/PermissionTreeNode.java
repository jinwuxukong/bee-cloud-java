package com.hutu.modules.admin.upms.vo;

import com.hutu.common.entity.TreeNode;

public class PermissionTreeNode extends TreeNode {
    private String name;

    public PermissionTreeNode(int id, int parentId, String label, String name) {
        super(id, parentId, label);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
