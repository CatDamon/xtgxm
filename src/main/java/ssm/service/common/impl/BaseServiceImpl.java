package ssm.service.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ssm.dao.DaoSupport;
import ssm.service.common.BaseService;
import ssm.utils.UuidUtil;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;


public class BaseServiceImpl implements BaseService {
    public static Logger logger=LoggerFactory.getLogger(BaseServiceImpl.class);
    
	@Resource
	protected DaoSupport daoSupport;

	public Object save(String str, Object obj) throws Exception {
		return this.daoSupport.save(str, obj);
	}

	public Object update(String str, Object obj) throws Exception {
		return this.daoSupport.update(str, obj);
	}

	public Object delete(String str, Object obj) throws Exception {
		return this.daoSupport.delete(str, obj);
	}

	public Object findForObject(String str, Object obj) throws Exception {
		return this.daoSupport.findForObject(str, obj);
	}

	public Object findForList(String str, Object obj) throws Exception {
		  return this.daoSupport.findForList(str, obj);
	}

	public Object findForMap(String sql, Object obj, String key, String value) throws Exception {
		return this.daoSupport.findForMap(sql, obj, key, value);
	}
	
	public Object findForList(String str) throws Exception {
		return this.daoSupport.findForList(str);
	}
	/**
	 * 得到32位的uuid作为表ID
	 * @return
	 */
	public static String get32UUID(){
		return UuidUtil.get32UUID();
	}
	/**
	 * 比较两个对象，把新对象的值赋值给原对象
	 * @param newmap 新对象
	 * @param oldmap 原对象
	 */
	public void newCopyForOld(Map newmap,Map oldmap){
		//把新值赋值给旧的对象
		if(newmap==null||oldmap==null){
			oldmap=newmap;
			return;
		}
		Iterator iter=newmap.keySet().iterator();
		while(iter.hasNext()){
			Object obj=iter.next();
			oldmap.put(obj, newmap.get(obj));
		}
	}
}
