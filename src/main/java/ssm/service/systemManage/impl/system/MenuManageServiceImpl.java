package ssm.service.systemManage.impl.system;

import org.eclipse.jetty.util.StringUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Service;
import ssm.exception.SystemServiceException;
import ssm.service.common.impl.BaseServiceImpl;
import ssm.service.systemManage.system.MenuManageService;
import ssm.state.IsHeaderState;
import ssm.state.IsMenuOrPointState;
import ssm.utils.Page;
import ssm.utils.PageData;

import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by dxgong on 2017/5/7.
 */
@Service("MenuManageServiceImpl")
public class MenuManageServiceImpl extends BaseServiceImpl implements MenuManageService {


    /**
     * 根据登录用户动态生成左侧菜单
     *
     * @param pageData
     */
    @Override
    public Object getMenuJson(PageData pageData) throws Exception {
        logger.info("MenuManageServiceImpl getMenuJson...");
        StringBuilder sb = new StringBuilder();
        pageData.put("ismenuorpoint",IsMenuOrPointState.CDQX.getType_code());
        pageData.put("isheader",IsHeaderState.GML.getType_code());  //父级类别
        //查询根目录
        List<PageData> rootList = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.selectRootMenuData",pageData);
        sb.append("[");
        for (int i =0;i < rootList.size(); i++){
            recursiveSonMenuData(rootList.get(i),sb);
            if(i != (rootList.size()-1)){ //判断最后一个元素不需要加,号
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**递归获取子菜单*/
    private StringBuilder recursiveSonMenuData(PageData fatherData,StringBuilder sb){
        List<PageData> sonList = null;
        try {
            //获取该菜单的子节点
            fatherData.put("ismenuorpoint",IsMenuOrPointState.CDQX.getType_code());
            sonList = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.selectUnRootMenuData",fatherData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sonList.size()==0){ //判断该节点下是否还有节点
            sb.append("{");
            sb.append("\"id\":\""+fatherData.get("per_id").toString()+"\",");
            sb.append("\"name\":\""+fatherData.get("per_name").toString()+"\",");
            sb.append("\"parentId\":\""+(fatherData.containsKey("parentid")?fatherData.get("parentid").toString():"")+"\",");
            sb.append("\"url\":\""+ (fatherData.containsKey("url")?fatherData.get("url").toString():"")+"\",");
            sb.append("\"icon\":\""+(fatherData.containsKey("icon")?fatherData.get("icon").toString():"&#xe604;")+"\",");
            sb.append("\"order\":\""+fatherData.get("orders").toString()+"\",");
            sb.append("\"isHeader\":\""+fatherData.get("isheader").toString()+"\",");
            //递归获取子节点
            sb.append("\"childMenus\":\"\"");
            sb.append("}");
            return sb;
        }else{
            sb.append("{");
            sb.append("\"id\":\""+fatherData.get("per_id").toString()+"\",");
            sb.append("\"name\":\""+fatherData.get("per_name").toString()+"\",");
            sb.append("\"parentId\":\""+(fatherData.containsKey("parentid")?fatherData.get("parentid").toString():"")+"\",");
            sb.append("\"url\":\""+ (fatherData.containsKey("url")?fatherData.get("url").toString():"")+"\",");
            sb.append("\"icon\":\""+(fatherData.containsKey("icon")?fatherData.get("icon").toString():"")+"\",");
            sb.append("\"order\":\""+fatherData.get("orders").toString()+"\",");
            sb.append("\"isHeader\":\""+fatherData.get("isheader").toString()+"\",");
            sb.append("\"childMenus\":[");
            //递归获取子节点
            for(int j =0; j<sonList.size(); j++){
                PageData pd = sonList.get(j);
                recursiveSonMenuData(pd,sb);
                if(j != (sonList.size()-1)){ //判断最后一个元素后不需要加,号
                    sb.append(",");
                }
                continue;
            }
            sb.append("]");
            sb.append("}");
            return sb;
        }


    }



    @Override
    public List<PageData> selectMenu(Page page) throws Exception {

        return (List<PageData>) this.daoSupport.findForList("MenuManageMapper.selectMenulistPage",page);
    }

    /**
     * 保存菜单
     *
     * @param pageData
     */
    @Override
    public void saveMenu(PageData pageData) throws Exception {
        logger.info("MenuManageServiceImpl saveMenu...");
        pageData.put("per_id",get32UUID()); //设置主键
        if(IsMenuOrPointState.YMQXD.getType_code().equals(pageData.getString("ismenuorpoint"))){  //如果是页面权限点，则isheader的值改成有父级目录
            pageData.put("isheader",IsHeaderState.FGML.getType_code());
        }
        //判断该保存权限类别
        // 当且仅当ismenuorpoint属于菜单权限,并且isheader属于根目录时候,上级菜单不用做校验
        if(!(IsMenuOrPointState.CDQX.getType_code().equals(pageData.getString("ismenuorpoint")) && IsHeaderState.FGML.getType_code().equals(pageData.getString("isheader")))){
            if(!StringUtil.isNotBlank(pageData.getString("parentname"))){  //非菜单根目录都需要选择上级菜单
                throw new SystemServiceException("该目录不是根目录,请在右侧点击选择相应的上级菜单");
            }else{

                this.daoSupport.save("MenuManageMapper.saveMenu",pageData);
            }
        }else{
            this.daoSupport.save("MenuManageMapper.saveMenu",pageData);
        }

    }

    /**
     * 返回权限目录树ztree
     *
     */
    @Override
    public String returnZtreeData() throws Exception {
        logger.info("MenuManageServiceImpl returnZtreeData...");
        //查询菜单权限的根目录
        PageData pd = new PageData();
        StringBuilder sb = new StringBuilder();
        pd.put("isheader",IsHeaderState.FGML.getType_code());
        pd.put("ismenuorpoint",IsMenuOrPointState.CDQX.getType_code());
        List<PageData> rootList = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.returnZtreeMenuRootData",pd);
        List<PageData> sonList = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.returnZtreeMenuSonDataByRoot",rootList);
        sb.append("[");
        for(int i =0; i<rootList.size(); i++){
            sb.append("{");
            sb.append("id:\""+ rootList.get(i).getString("per_id")+"\",");
            sb.append("name:\""+ rootList.get(i).getString("per_name")+"\",");
            sb.append("open:"+ true+",");
            //遍历二级子目录
            if(sonList.size()>0){
                sb.append("children:[");
                for(int j=0; j<sonList.size(); j++){
                    //判断当前父类下面的子节点
                    if(rootList.get(i).getString("per_id").equals(sonList.get(j).getString("parentid"))){
                        sb.append("{");
                        sb.append("id:\""+ sonList.get(j).getString("per_id")+"\",");
                        sb.append("name:\""+ sonList.get(j).getString("per_name")+"\",");
                        sb.append("open:"+ false+",");
                        sb.append("isParent:"+ true);
                        sb.append("}");
                        if(j != (sonList.size()-1)){ //非最后一个后面需要加,号
                            sb.append(",");
                        }
                    }
                }
                sb.append("]");
                sb.append(",");
            }
            sb.append("isParent:"+ true);
            sb.append("}");
            if(i != (rootList.size()-1)){//判断不是最后一个,后面加,号
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 根据ID查询该权限下所有目录
     *
     * @param pageData
     */
    @Override
    public String getSonMenu(PageData pageData) throws Exception {
        logger.info("MenuManageServiceImpl getSonMenu...");
        StringBuilder sb = new StringBuilder(); //封装ztree目录数据
        if(StringUtil.isNotBlank(pageData.getString("id"))){
            List<PageData> list = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.findSonMenu",pageData);
            if(list.size() == 0){ //该目录下没有子菜单
                return "[]";
                //throw new SystemServiceException("没有可用的子菜单!");
            }
            sb.append("[");
            for(int i=0; i<list.size(); i++){
                PageData pd = list.get(i);
                sb.append("{");
                sb.append("id:\""+ pd.getString("per_id")+"\",");
                sb.append("name:\""+ pd.getString("per_name")+"\",");
                if(IsHeaderState.GML.getType_code().equals(pd.get("isheader").toString())){ //判断是否是父目录
                    sb.append("isParent:"+ true +",");
                }
                sb.append("open:"+ false);
                sb.append("}");
                if(i != (list.size()-1)){//判断不是最后一个,后面加,号
                    sb.append(",");
                }
            }
            sb.append("]");
        }else{
            throw new SystemServiceException("权限id不能为空!");
        }
        return sb.toString();
    }

    /**
     * 根据ID修改权限信息
     * 如需修改所在目录排序号，请输入 "名称-排序号"
     *
     * @param per_id
     * @param new_per_name
     */
    @Override
    public void editPer(String per_id, String new_per_name) throws Exception{
        logger.info("MenuManageServiceImpl editPer...");
        if(StringUtil.isNotBlank(per_id)){
            PageData pd = new PageData();
            //判断new_per_name是否带有顺序号
            String[] str = new_per_name.split("-");
            if(str.length>1){  //2个元素,说明具有orders属性
                //校验数字
                if(Pattern.matches("^\\+?[1-9][0-9]*$",str[1])){
                    pd.put("orders",str[1]);
                }else{
                    throw new SystemServiceException("修改失败,顺序号必须填非零正整数！");
                }

            }
            pd.put("per_id",per_id);
            pd.put("per_name",str[0]);
            this.daoSupport.update("MenuManageMapper.editPer",pd);
        }else{
            throw new SystemServiceException("权限ID不能为空!");
        }
    }

    /**
     * 根据ID查询权限
     *
     * @param per_id
     */
    @Override
    public PageData selectPer(String per_id) throws Exception {
        logger.info("MenuManageServiceImpl selectPer...");
        if(StringUtil.isNotBlank(per_id)){
            return (PageData) this.daoSupport.findForObject("MenuManageMapper.selectPer",per_id);
        }else{
            throw new SystemServiceException("权限ID不能为空");
        }
    }

    /**
     * 删除菜单树节点
     *
     * @param per_id
     */
    @Override
    public void delPer(String per_id) throws Exception {
        //logger.info("MenuManageServiceImpl delPer...");
        if(StringUtil.isNotBlank(per_id)){
            //删除选中节点
            this.daoSupport.delete("MenuManageMapper.delPer",per_id);
        }else{
            throw new SystemServiceException("权限ID不能为空!");
        }
    }

    /**
     * 判断该节点是否有子节点
     *
     * @param per_id
     */
    @Override
    public Boolean isHashSonNodes(String per_id) throws Exception {
        logger.info("MenuManageServiceImpl isHashSonNodes..");
        List<PageData> list = (List<PageData>) this.daoSupport.findForList("MenuManageMapper.isHasSonNodes",per_id);
        if(list.size()>0){ //说明有子节点
            return true;
        }else{
            return false;
        }
    }


}
