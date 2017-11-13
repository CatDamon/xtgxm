package ssm.service.systemManage.impl.system;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;
import ssm.exception.SystemServiceException;
import ssm.service.common.impl.BaseServiceImpl;
import ssm.service.systemManage.system.RoleManageService;
import ssm.utils.Page;
import ssm.utils.PageData;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dxgong on 2017/7/4.
 */
@Service("RoleManageServiceImpl")
public class RoleManageServiceImpl extends BaseServiceImpl implements RoleManageService{


    /**
     * 查询全部角色
     *
     * @param page
     */
    @Override
    public List<PageData> findRole(Page page) throws Exception {
        logger.info("RoleManageServiceImpl findRole...");
        return (List<PageData>) this.daoSupport.findForList("RoleManageMapper.selectRolelistPage",page);
    }

    /**
     * 保存角色
     *
     * @param pageData
     */
    @Override
    public void saveRole(PageData pageData) throws Exception {
        logger.info("RoleManageServiceImpl saveRole...");
        pageData.put("roleid",get32UUID());
        this.daoSupport.save("RoleManageMapper.saveRole",pageData);
    }

    /**
     * 删除角色
     *
     * @param pageData
     */
    @Override
    public void delRole(PageData pageData) throws Exception {
        logger.info("RoleManageServiceImpl delRole...");
        this.daoSupport.delete("RoleManageMapper.delRole",pageData);
    }

    /**
     * 修改角色
     *
     * @param pageData
     */
    @Override
    public void editRole(PageData pageData) throws Exception {
        logger.info("RoleManageServiceImpl editRole...");
        //校验要修改的角色名称是否重复
        PageData roleData = (PageData) this.daoSupport.findForObject("RoleManageMapper.isHasSameRole",pageData);
        if(roleData != null){ //名字重复,抛出异常
            throw new SystemServiceException("角色名称重复,请重新输入!");
        }
        this.daoSupport.update("RoleManageMapper.editRole",pageData);
    }

    /**
     * 保存角色对应权限
     *
     * @param pageData
     */
    @Override
    public void saveRolePri(PageData pageData) throws Exception {
        logger.info("RoleManageServiceImpl saveRolePri...");
        this.daoSupport.delete("RoleManageMapper.deleteRolePri",pageData);
        String checkArrStr = pageData.getString("checkArr");
        if(StringUtil.isNotBlank(checkArrStr)){
            String[] checkArrs = checkArrStr.split(",");
            pageData.put("checkArrs",checkArrs);
            this.daoSupport.save("RoleManageMapper.saveRolePri",pageData);
        }else{
            throw new SystemServiceException("请勾选需要分配的权限！");
        }

    }

    /**
     * 根据RoleId查询权限
     *
     * @param pd
     */
    @Override
    public List<PageData> findPriByRoleId(PageData pd) throws Exception{
        logger.info("RoleManageServiceImpl findPriByRoleId...");
        return (List<PageData>)this.daoSupport.findForList("RoleManageMapper.findPriByRoleId",pd);
    }
}
