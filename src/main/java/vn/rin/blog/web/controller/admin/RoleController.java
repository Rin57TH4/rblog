package vn.rin.blog.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.rin.blog.base.Result;
import vn.rin.blog.domain.entity.PermissionEntity;
import vn.rin.blog.domain.entity.RoleEntity;
import vn.rin.blog.service.PermissionService;
import vn.rin.blog.service.RoleService;
import vn.rin.blog.web.controller.BaseController;

import java.util.HashSet;
import java.util.List;

/**
 * @author Rin
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public String paging(String name, ModelMap model) {
        Pageable pageable = wrapPageable();
        Page<RoleEntity> page = roleService.paging(pageable, name);
        model.put("name", name);
        model.put("page", page);
        return "/admin/role/list";
    }

    @RequestMapping("/view")
    public String view(Long id, ModelMap model) {
        if (id != null && id > 0) {
            RoleEntity role = roleService.get(id);
            model.put("view", role);
        }

        model.put("permissions", permissionService.tree());
        return "/admin/role/view";
    }

    @RequestMapping("/update")
    public String update(RoleEntity role, @RequestParam(value = "perms", required = false) List<Long> perms, ModelMap model) {
        Result data;

        HashSet<PermissionEntity> permissions = new HashSet<>();
        if (perms != null && perms.size() > 0) {

            for (Long pid : perms) {
                PermissionEntity p = new PermissionEntity();
                p.setId(pid);
                permissions.add(p);
            }
        }

        if (RoleEntity.ADMIN_ID == role.getId()) {
            data = Result.failure("Vai trò quản trị viên không thể chỉnh sửa");
        } else {
            roleService.update(role, permissions);
            data = Result.success();
        }
        model.put("data", data);
        return "redirect:/admin/role/list";
    }

    @RequestMapping("/activate")
    @ResponseBody
    public Result activate(Long id, Boolean active) {
        Result ret = Result.failure("Thao tác thất bại");
        if (id != null && id != RoleEntity.ADMIN_ID) {
            roleService.activate(id, active);
            ret = Result.success();
        }
        return ret;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") Long id) {
        Result ret;
        if (RoleEntity.ADMIN_ID == id) {
            ret = Result.failure("Quản trị viên không thể hoạt động");
        } else if (roleService.delete(id)) {
            ret = Result.success();
        } else {
            ret = Result.failure("Vai trò này không thể bị thao túng");
        }
        return ret;
    }
}
