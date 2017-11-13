package ssm.service.systemManage.system;




import ssm.utils.Page;
import ssm.utils.PageData;

import java.util.List;

/**
 *
 * Created by dxgong on 2017/5/7.
 *
 */
public interface UserManageService {


    public List<PageData> selectUserList(Page page) throws Exception;

    /**添加用户*/
    public void saveUser(PageData pageData) throws Exception;

    /**激活用户*/
    public void activativeAccount(PageData pageData) throws Exception;

    /**修改用户*/
    public void editUser(PageData pageData) throws Exception;

    /**删除用户*/
    public void delUser(PageData pageData) throws Exception;

    /**查询所有角色*/
    public List<PageData> findAllRole() throws Exception;

    /**根据用户id保存对应角色*/
    public void saveRoleForUser(PageData pageData) throws Exception;
}



