package ssm.ctrl.system.roleManage;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ssm.ctrl.common.BaseController;
import ssm.service.systemManage.system.RoleManageService;
import ssm.utils.Page;
import ssm.utils.PageData;

import javax.annotation.Resource;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dxgong on 2017/7/4.
 */
@RequestMapping("/system/RoleManageCtrl")
@Controller
public class RoleManageCtrl extends BaseController {

    @Resource(name="RoleManageServiceImpl")
    private RoleManageService roleManageService;

    /**跳转到角色列表*/
    @RequiresPermissions("/system/RoleManageCtrl/toRoleIndex")
    @RequestMapping("/toRoleIndex")
    public ModelAndView toRoleIndex (Page page) throws Exception{
        logger.info("RoleManageCtrl toRoleIndex...");
        ModelAndView mv = new ModelAndView("/system/roleManage/roleIndex.html");
        PageData pd = this.getPageData();
        this.setConditionForQuery(pd,mv,page); //设置查询条件函数
        List<PageData> roleList = this.roleManageService.findRole(page);
        mv.addObject("roleList",roleList);
        return mv;
    }

    /**跳转到添加角色页面*/
    @RequiresPermissions("/system/RoleManageCtrl/toAddRole")
    @RequestMapping("/toAddRole")
    public ModelAndView toAddRole () throws Exception{
        logger.info("RoleManageCtrl toAddRole...");
        ModelAndView mv = new ModelAndView("/system/roleManage/addRole.html");
        return mv;
    }

    /**保存角色*/
    @RequestMapping("/saveRole")
    @ResponseBody
    public Map<String, Object> saveRole(){
        logger.info("RoleManageCtrl saveRole...");
        Map<String,Object> map = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try {
            this.roleManageService.saveRole(pd);
        } catch (Exception e) {
            map.put("error","添加失败!");
            e.printStackTrace();
        }
        return map;
    }


    /**删除角色*/
    @RequiresPermissions("/system/RoleManageCtrl/delRole")
    @RequestMapping("/delRole")
    public ModelAndView delRole() throws Exception {
        logger.info("RoleManageCtrl delRole...");
        ModelAndView mv = new ModelAndView("forward:/system/RoleManageCtrl/toRoleIndex");
        PageData pd = this.getPageData();
        this.roleManageService.delRole(pd);
        return mv;
    }


    /**跳转到修改角色页面*/
    @RequiresPermissions("/system/RoleManageCtrl/toEditRole")
    @RequestMapping("/toEditRole")
    public ModelAndView toEditRole () throws Exception{
        logger.info("RoleManageCtrl toeEditRole...");
        ModelAndView mv = new ModelAndView("/system/roleManage/editRole.html");
        return mv;
    }


    @RequestMapping("/editRole")
    @ResponseBody
    public Map<String, Object> editRole()  {
        logger.info("RoleManageCtrl editRole...");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try {
            this.roleManageService.editRole(pd);
        } catch (Exception e) {
            map.put("error",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**跳转到分配权限页面*/
    @RequiresPermissions("/system/RoleManageCtrl/toChmodRolePri")
    @RequestMapping("/toChmodRolePri")
    public ModelAndView toChmodRolePri (){
        logger.info("RoleManageCtrl toChmodRolePri...");
        ModelAndView mv = new ModelAndView("/system/roleManage/chmodRolePri.html");
        PageData pd = this.getPageData();
        List<PageData> list = null;
        try {
            list = this.roleManageService.findPriByRoleId(pd);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++){
                sb.append(list.get(i).getString("per_id"));
                if(i != (list.size()-1)){
                    sb.append(",");
                }
            }
            mv.addObject("perList",sb);

            mv.addObject("rolename",pd.getString("rolename"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

    @RequestMapping("/saveRolePri")
    @ResponseBody
    public Map<String, Object> saveRolePri () {
        logger.info("RoleManageCtrl saveRolePri...");
        Map<String,Object> map = new HashMap<String, Object>();
        PageData pageData = this.getPageData();
        try {
            this.roleManageService.saveRolePri(pageData);
        } catch (Exception e) {
            map.put("error",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

}
