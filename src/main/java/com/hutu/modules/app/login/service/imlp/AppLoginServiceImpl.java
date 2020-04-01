package com.hutu.modules.app.login.service.imlp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.common.annotation.OperationLog;
import com.hutu.common.constant.Constant;
import com.hutu.common.entity.CallerInfo;
import com.hutu.common.entity.R;
import com.hutu.common.enums.ResultCode;
import com.hutu.common.enums.OperateEnum;
import com.hutu.common.util.CacheUtils;
import com.hutu.common.util.TreeUtil;
import com.hutu.common.util.YimiUtil;
import com.hutu.modules.admin.followup.form.DoctorRegisterForm;
import com.hutu.modules.admin.followup.service.AdminDoctorService;
import com.hutu.modules.admin.upms.entity.*;
import com.hutu.modules.admin.upms.mapper.*;
import com.hutu.modules.admin.upms.service.RolePermissionService;
import com.hutu.modules.admin.upms.service.UserRoleService;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.admin.upms.vo.PermissionTreeNode;
import com.hutu.modules.app.login.service.AppLoginService;
import com.hutu.modules.app.login.utils.AppJwtUtils;
import com.hutu.modules.common.entity.Doctor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author hutu.
 * @date: 2017/11/6 10:38
 */
@SuppressWarnings(value = "all")
@Service("ApploginServiceImpl")
public class AppLoginServiceImpl implements AppLoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("adminDoctorServiceImpl")
    private AdminDoctorService doctorService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper usePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R register(DoctorRegisterForm data) {
        String yimiNumber = YimiUtil.getYimiNumber();
        if (!StringUtils.isEmpty(userService.getOne(new QueryWrapper<User>().eq("name", data.getPhone())))) {
            return R.error(ResultCode.USERNAME_ALREADY_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(data, user);
        //设置为3
        user.setType(Constant.YIMI_DOCTOR_TYPE)
                .setTypeShow("随访医生")
                .setName(data.getPhone())
                .setPass(data.getPassword());
        //给一个用户默认昵称名字
        if (data.getNick() == null) {
            user.setNick("医生" + yimiNumber);
        }
        boolean b1 = userService.saveUser(user);
        if(!b1){
            try {
                throw new Exception("用户添加失败，请联系管理员");
            } catch (Exception e) {
                throw new Exception("用户添加失败，请联系管理员");
            } finally {
                return R.error(500, "用户添加失败，请联系管理员");
            }
        }
        Integer userId = user.getId();
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(data, doctor);
        doctor.setUserId(userId);
        doctor.setVerifyStatus(4);
        if (StrUtil.isNotEmpty(yimiNumber)) {
            doctor.setYimiNumber(yimiNumber);
        } else {
            try {
                throw new Exception("依米医生注册失败！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean b2 = doctorService.save(doctor);
        if(!b2){
            try {
                throw new Exception("医生添加失败，请联系管理员");
            } catch (Exception e) {
                throw new Exception("医生添加失败，请联系管理员");
            } finally {
                return R.error(500, "医生添加失败，请联系管理员");
            }
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(5);
        userRole.setUserId(userId);
        boolean b4 = userRoleService.save(userRole);
        if(!b4){
            try {
                throw new Exception("用户角色添加失败，请联系管理员");
            } catch (Exception e) {
                throw new Exception("用户角色添加失败，请联系管理员");
            } finally {
                return R.error(500, "用户角色添加失败，请联系管理员");
            }
        }
        if (b1 && b2 && b4) {
            return R.ok();
        }
        return R.error("注册");
    }

    @Override
    @OperationLog(value = "登录后台", type = OperateEnum.SELECT, module = "com.hutu.modules.login")
    public R login(String username, String password) {
        List<User> list = userMapper.selectList(new QueryWrapper<User>().eq("name", username));
        User user = (list != null && list.size() == 1) ? list.get(0) : null;
        CallerInfo callerInfo = new CallerInfo();
        String token;
        if (user == null || !user.getPass().equals(password)) {
            return R.error(ResultCode.USERNAME_OR_PASS_ERROR);
        } else {
            callerInfo.uid = user.getId();
            callerInfo.name = user.getName();
            callerInfo.nick = user.getNick();
            callerInfo.type = user.getType();
            callerInfo.tenantId = user.getOrgId();
            token = AppJwtUtils.createToken(callerInfo);
            CacheUtils.userCache.put(user.getId().toString(), token);
        }
        return R.ok().put("token", token);
    }

    @Override
    public List<Permission> getPermissionByUserId(Integer userId) {
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("userId", userId));
        List<UserPermission> userPermissionList = usePermissionMapper.selectList(new QueryWrapper<UserPermission>()
                .eq("userId", userId));
        Set<Integer> roleIds = userRoleList.stream().filter(obj -> obj.getRoleId() != 0).map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<Integer> permissionIds = new HashSet<>();
        if (roleIds.size() > 0) {
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>()
                    .in("roleId", roleIds));
            permissionIds = rolePermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(RolePermission::getPermissionId).collect(Collectors.toSet());
        }
        permissionIds.addAll(userPermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(UserPermission::getPermissionId).collect(Collectors.toSet()));
        // 无权限
        if (permissionIds.size() == 0) {
            return new ArrayList();
        }
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>()
                .in("id", permissionIds).orderByAsc("orders"));

        return permissionList;
    }

    @Override
    public List getUserPermissions(Integer userId) {
        List<Permission> list = getPermissionByUserId(userId);
        List<String> permissions = list.stream().map(Permission::getPermissionValue).collect(Collectors.toList());
        return permissions;
    }

    @Override
    public List getPermissionTree() {
        List<Permission> list = permissionMapper.selectList(null);
        List<PermissionTreeNode> treeList = new ArrayList<>();
        for (Permission obj : list) {
            PermissionTreeNode treenode = new PermissionTreeNode(obj.getId(), obj.getPid(), obj.getTitle(), obj.getName());
            treeList.add(treenode);
        }
        return TreeUtil.buildByRecursive(treeList, 0);
    }

    @Override
    public List getRolePermissionIds(Integer roleId) {
        // 找到是目录的菜单
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>().eq("type", 1));
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("roleId", roleId));
        List<Integer> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        for (Permission obj : permissionList) {
            permissionIds.removeIf(id -> obj.getId().equals(id));
        }
        return permissionIds;
    }

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(value = "更新角色权限树", type = OperateEnum.UPDATE, module = "com.hutu.modules.upms")
    public boolean updateRolePermission(Integer[] permissionIds, Integer roleId) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("roleId", roleId));

        Set<RolePermission> rolePermissions = new HashSet<>();
        List<Permission> permissionList = permissionMapper.selectList(null);

        for (int permissionId : permissionIds) {
            findParentPermission(permissionList, rolePermissions, roleId, permissionId);
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissions);

        return true;
    }

    public void findParentPermission(List<Permission> permissionList, Set<RolePermission> rolePermissions, int roleId, int permissionId) {
        int parentId = 0;
        for (Permission obj : permissionList) {
            if (obj.getId().equals(permissionId) && obj.getPid() != 0) {
                for (Permission tmp : permissionList) {
                    if (tmp.getId().equals(obj.getPid())) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setPermissionId(tmp.getId());
                        rolePermissions.add(rolePermission);
                        break;
                    }
                }
                parentId = obj.getPid();
                break;
            }
        }
        if (parentId != 0) {
            findParentPermission(permissionList, rolePermissions, roleId, parentId);
        }
    }
}
