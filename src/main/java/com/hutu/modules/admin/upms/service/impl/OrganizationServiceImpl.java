package com.hutu.modules.admin.upms.service.impl;

import com.hutu.common.entity.TreeNode;
import com.hutu.common.util.TreeUtil;
import com.hutu.modules.admin.upms.entity.Organization;
import com.hutu.modules.admin.upms.mapper.OrganizationMapper;
import com.hutu.modules.admin.upms.service.OrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {
    @Override
    public List getOrganizationTree(){
        List<Organization> list = list();
        List<TreeNode> treeList = new ArrayList<>();
        for (Organization obj : list) {
            TreeNode treenode = new TreeNode(obj.getId(), obj.getPId(), obj.getName());
            treeList.add(treenode);
        }
        return TreeUtil.buildByRecursive(treeList, 0);
    }
}
