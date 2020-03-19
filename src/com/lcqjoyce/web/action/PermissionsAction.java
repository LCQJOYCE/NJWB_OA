package com.lcqjoyce.web.action;

import com.lcqjoyce.My_JDBC.Init.BeanFactory;
import com.lcqjoyce.entity.Menu;
import com.lcqjoyce.entity.Permissions;
import com.lcqjoyce.entity.Role;
import com.lcqjoyce.service.MenuService;
import com.lcqjoyce.service.PermissionsService;
import com.lcqjoyce.service.RoleService;
import com.lcqjoyce.util.page.PageIndex;
import com.lcqjoyce.util.page.PageResult;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author ：LCQJOYCE
 * @date ：Created in 2020/3/17 21:29
 * @description：
 * @version: $
 */
public class PermissionsAction {
    private static Logger logger = Logger.getLogger(PermissionsAction.class);
    private PermissionsService permissionsService;
    private RoleService roleService;
    private MenuService menuService;

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setPermissionsService(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }


    //获取role名字
    public String getAllRoleName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = "success";
        List<Role> roles = roleService.getAllroles();
        PrintWriter out;
        try {
            out = response.getWriter();
            String json = JSONArray.fromObject(roles).toString();
            out.write(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //获取role名字
    public String getAllMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = "success";
        List<Menu> menus = menuService.getallMenus();
        PrintWriter out;
        try {
            out = response.getWriter();
            String json = JSONArray.fromObject(menus).toString();
            out.write(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }


    public String queryPermissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId = request.getParameter("roleId");
        String menuId = request.getParameter("menuId");

        Integer currentPage = 1;
        if (null != request.getParameter("currentPage") && !"".equals(request.getParameter("currentPage"))) {
            currentPage = Integer.valueOf(request.getParameter("currentPage").toString());
        }
        //holidayResult  获取成功
        PageResult permissionsResult = permissionsService.getPermissionsWithConditionByPage(roleId, menuId,currentPage);
        PageIndex permissionsIndex = PageIndex.getPageIndex(3,currentPage,permissionsResult.getTotalPage());
        logger.info(permissionsResult.getListData());
        request.setAttribute("permissionsResult",permissionsResult);
        request.setAttribute("permissionsIndex",permissionsIndex);
        request.setAttribute("roleId",roleId);
        request.setAttribute("menuId",menuId);

        return "success";
    }

    public String addPermissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = "success";
        Permissions permissions = new Permissions();
        Role role = new Role();
        Menu menu = new Menu();
        role.setId(Integer.valueOf(request.getParameter("roleId")));
        menu.setId(Integer.valueOf(request.getParameter("menuId")));
        permissions.setRole(role);
        permissions.setMenu(menu);
        logger.info(permissions.toString());
        PermissionsService service=(PermissionsService) BeanFactory.getObject("permissionsService");
        if(service.addPermissions(permissions)==1)
            return resultString;

        return "fail";
    }


}
