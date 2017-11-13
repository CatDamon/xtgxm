package ssm.ctrl.system.menuManage;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ssm.ctrl.common.BaseController;
import ssm.exception.SystemServiceException;
import ssm.service.systemManage.system.MenuManageService;
import ssm.state.IsHeaderState;
import ssm.utils.Page;
import ssm.utils.PageData;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/system/MenuManageCtrl")
public class MenuManageCtrl extends BaseController {

	@Resource(name = "MenuManageServiceImpl")
	private MenuManageService menuManageService;


	/**跳转到菜单管理界面*/
	@RequestMapping("/toMenuIndex")
	public ModelAndView toMenuIndex(Page page) throws Exception {
		logger.info("MenuManageCtrl toMenuIndex...");
		ModelAndView mv = new ModelAndView("/system/menuManage/menuIndex.html");

		mv.addObject("menuList" ,(List<PageData>)this.menuManageService.selectMenu(page));
		return mv;
	}

	/**保存菜单权限*/
	@RequestMapping("/saveMenu")
	@ResponseBody
	public Map<String, Object> saveMenu(){
		logger.info("MenuManageCtrl saveMenu...");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		try {
			//保存权限
			this.menuManageService.saveMenu(pd);
		} catch (Exception e) {
			map.put("error",e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**生成菜单权限树*/
	@RequestMapping("/returnZtreeData")
	@ResponseBody
	public Map<String, Object> returnZtreeData(){
		logger.info("MenuManageCtrl returnZtreeData...");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String data = this.menuManageService.returnZtreeData();
			map.put("data",data);
		} catch (Exception e) {
			map.put("error","加载权限树失败!请联系管理员进行操作");
			e.printStackTrace();
		}
		return map;
	}

	/**ztree父级目录异步加载子目录*/
	@RequestMapping(value = "/loadSonMenu",produces = {"text/json;charset=UTF-8"})
	@ResponseBody
	public Object loadSonMenu(){
		logger.info("MenuManageCtrl loadSonMenu...");
		PageData pd = this.getPageData();
		String data = null;
        try {
            data = this.menuManageService.getSonMenu(pd);
        } catch (Exception e) {
            data = e.getMessage();
            e.printStackTrace();
        }

        return data;
	}

	/**修改菜单名称和顺序号*/
	@RequestMapping("/editPer")
    @ResponseBody
	public Map<String, Object> editPer(@RequestParam String per_id, @RequestParam String new_per_name) {
	    logger.info("MenuManageCtrl editPer...");
	    Map<String, Object> map = new HashMap<String, Object>();
		try {
			//先查询原名称保存到内存
			PageData perData = this.menuManageService.selectPer(per_id);
			map.put("oldPerName",perData!=null?perData.getString("per_name"):new_per_name);
			this.menuManageService.editPer(per_id, new_per_name);
		} catch (Exception e) {
			map.put("error",e.getMessage());
			e.printStackTrace();
		}
		return map;
    }

	/**判断是否有子节点*/
	@RequestMapping("/isHasSonNodes")
	@ResponseBody
	public Map<String, Object> isHasSonNodes(@RequestParam String per_id) {
		logger.info("MenuManageCtrl isHasSonNodes...");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Boolean temp = this.menuManageService.isHashSonNodes(per_id);
			map.put("temp",temp);
		} catch (Exception e) {
			map.put("error","删除失败");
			e.printStackTrace();
		}
		return map;
	}


    /**删除菜单树节点*/
	@RequestMapping("/delPer")
	@ResponseBody
	public Map<String, Object> delPer(@RequestParam String per_id) {
		logger.info("MenuManageCtrl delPer...");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.menuManageService.delPer(per_id);
		} catch (Exception e) {
			map.put("error","删除失败");
			e.printStackTrace();
		}
		return map;
	}


	/**动态生成用户菜单*/
	@RequestMapping("/getMenuJson")
	@ResponseBody
	public Object getMenuJson()  {
		logger.info("MenuManageCtrl getMenuJson...");
		PageData pd = new PageData();
		pd.put("userid" ,this.getUser().getUserid());
		Object obj = null;
		try {
			obj = this.menuManageService.getMenuJson(pd);

		} catch (Exception e) {
			e.printStackTrace();
			pd.put("errorInfo",e.getMessage());
		}
		System.out.print(obj);
		return null;
	}




}
