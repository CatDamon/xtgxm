package ssm.service.systemManage.impl.system;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;
import ssm.exception.SystemServiceException;
import ssm.service.common.impl.BaseServiceImpl;
import ssm.service.systemManage.system.UserManageService;
import ssm.state.AvailableState;
import ssm.utils.CodecAndCrypUtil;
import ssm.utils.Page;
import ssm.utils.PageData;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/5/7.
 */
@Service("UserManageServiceImpl")
public class UserManageServiceImpl extends BaseServiceImpl implements UserManageService {


    public List<PageData> selectUserList(Page page) throws Exception {
        List<PageData> userList = (List<PageData>) this.daoSupport.findForList("UserManageMapper.selectUserlistPage",page);
        for (PageData userData:userList) {
            userData.put("available", AvailableState.codeToDesc(userData.get("available").toString()));
        }
        return userList;
    }

    /**
     * 添加用户
     */
    @Override
    public void saveUser(PageData pageData) throws Exception {
        logger.info("UserManageServiceImpl saveUser...");
        //判断该用户名是否已经存在，存在则添加失败
        List<PageData> ishasAccount = (List<PageData>) this.daoSupport.findForList("UserManageMapper.isHasSaveAccount",pageData);
        if(ishasAccount.size()>0){ //说明该用户名已经存在
            throw new SystemServiceException("该用户名已经存在,请重新输入用户名!");
        }
        //密码DES加密
        pageData.put("userid",get32UUID());
        pageData.put("available",AvailableState.WJH.getType_code());
        if(StringUtils.isNotBlank(pageData.getString("password"))){
            pageData.put("password", CodecAndCrypUtil.MD5(pageData.getString("password")));
        }
        this.daoSupport.save("UserManageMapper.saveUser",pageData);
    }

    /**
     * 激活用户
     *
     * @param pageData
     */
    @Override
    public void activativeAccount(PageData pageData) throws Exception {
        logger.info("UserManageServiceImpl pageData...");
        if(StringUtils.isNoneBlank(pageData.getString("userid"))){
            if(AvailableState.WJH.getShort_desc().equals(pageData.getString("availableCode"))){
                pageData.put("available",AvailableState.YJH.getType_code());
            }else{
                pageData.put("available",AvailableState.WJH.getType_code());
            }
            this.daoSupport.update("UserManageMapper.activativeAccount",pageData);
        }else{
            throw new SystemServiceException("用户id不能为空!");
        }
    }

    /**
     * 修改用户
     *
     * @param pageData
     */
    @Override
    public void editUser(PageData pageData) throws Exception {
        logger.info("UserManageServiceImpl editUser...");
        if(StringUtils.isNotBlank(pageData.getString("password"))){
            pageData.put("password", CodecAndCrypUtil.MD5(pageData.getString("password")));
        }
        this.daoSupport.update("UserManageMapper.editUser",pageData);
    }

    /**
     * 删除用户
     *
     * @param pageData
     */
    @Override
    public void delUser(PageData pageData) throws Exception {
        logger.info("UserManageServiceImpl delUser...");
        this.daoSupport.delete("UserManageMapper.delRole",pageData);
        this.daoSupport.delete("UserManageMapper.delUser",pageData);

    }

    /**
     * 查询所有角色
     */
    @Override
    public List<PageData> findAllRole() throws Exception {
        logger.info("UserManageServiceImpl findAllRole...");
        return (List<PageData>) this.daoSupport.findForList("UserManageMapper.findAllRole");
    }

    /**
     * 根据用户id保存对应角色
     */
    @Override
    public void saveRoleForUser(PageData pageData) throws Exception {
        logger.info("UserManageServiceImpl saveRoleForUser...");
        if(!StringUtil.isNotBlank(pageData.getString("userid"))){
            throw new SystemServiceException("用户id不能为空");
        }
        String[] roleArrs = pageData.getString("roleArr").split(",");
        if(roleArrs.length == 0){
            throw new SystemServiceException("无角色添加!");
        }
        //目前的用户已经拥有的角色id
        List<String> roleIdList = (List<String>) this.daoSupport.findForList("UserManageMapper.findRoleidByUserId",pageData);
        //目前的用户正要添加的角色id,转化成list集合
        List<String> roleIdList2 = new ArrayList<String>();
        for (String roleid2: roleArrs){
            roleIdList2.add(roleid2);
        }
        //去重
        this.distinctRoleId(pageData,roleIdList,roleIdList2);


        this.daoSupport.save("UserManageMapper.saveRoleForUser",pageData);

    }

    /**
     * 去重
     * @param pageData 参数集合
     * @param roleIdList  目前的用户已经拥有的角色id
     * @param roleIdList  目前的用户正要添加的角色id
     * */
    private PageData distinctRoleId(PageData pageData, List<String> roleIdList,List<String> roleIdList2) throws Exception{
        List<String> exitRoles = new ArrayList<String>(); //保存当前移去的roleid
        if(!roleIdList.isEmpty() && !roleIdList2.isEmpty()){ //同时不为空,进行去重
            for (String roleId:roleIdList){
                for(int i = 0; i<roleIdList2.size();i++){
                    if(roleId.equals(roleIdList2.get(i))){
                        exitRoles.add(roleIdList2.get(i));
                        roleIdList2.remove(i);
                    }

                }
            }
        }

        if(roleIdList2.size() == 0){ //当前没有需要添加的角色,把已经存在的角色名称返回到前端
            List<PageData> exitRolesList = (List<PageData>) this.daoSupport.findForList("UserManageMapper.findRoleName",exitRoles);
            StringBuffer sb = new StringBuffer();
            sb.append("当前选择的< ");
            for (PageData exitRole : exitRolesList){
                sb.append(exitRole.getString("rolename")+" ");
            }
            sb.append(" >角色已经存在,请重新选择!");
            throw new SystemServiceException(sb.toString());
        }else{
            pageData.put("roleArrs",roleIdList2.toArray());
        }


        return pageData;
    }


}
