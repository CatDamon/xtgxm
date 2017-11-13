package ssm.service.systemManage.system;

import ssm.utils.Page;
import ssm.utils.PageData;

import java.util.List;

/**
 * Created by dxgong on 2017/6/5.
 */
public interface MenuManageService {

    /**根据登录用户动态生成左侧菜单*/
    public Object getMenuJson(PageData pageData)throws Exception;

    /**查询全部权限*/
    public List<PageData> selectMenu(Page page)throws Exception;

    /**保存菜单*/
    public void saveMenu(PageData pageData) throws Exception;

    /**返回权限目录树ztree*/
    public String returnZtreeData() throws Exception;

    /**根据ID查询该权限下所有目录*/
    public String getSonMenu(PageData pageData) throws Exception;

    /**根据ID修改权限信息
     * 如需修改所在目录排序号，请输入 "名称-排序号"
     * */
    public void editPer(String per_id, String nvoidew_per_name) throws Exception;

    /**
     * 根据ID查询权限
     * */
    public PageData selectPer(String per_id)throws Exception;

    /**删除菜单树节点*/
    public void delPer(String per_id) throws Exception;

    /**判断该节点是否有子节点*/
    public Boolean isHashSonNodes(String per_id) throws Exception;
}
