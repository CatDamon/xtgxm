package ssm.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	Logger logger=LoggerFactory.getLogger(PageData.class);
	Map map = null;
	HttpServletRequest request;
	
	public PageData(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Entry entry;
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		return (String)get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
	/** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public  Object convertToBean(Class type) throws IntrospectionException, 
                             InstantiationException, IllegalAccessException{
    	BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
    	Object obj = type.newInstance(); // 创建 JavaBean 对象 
    	if(map==null){
    		return obj;
    	}
    	// 给 JavaBean 对象的属性赋值 
    	PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
    	for (int i = 0; i< propertyDescriptors.length; i++) { 
    		PropertyDescriptor descriptor = propertyDescriptors[i];
    		String propertyName = descriptor.getName();
    		if (map.containsKey(propertyName)) {
    			try {
    				Object value = map.get(propertyName);
        			Object[] args = new Object[1]; 
        			args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (IllegalAccessException e) {
					logger.error(e.toString());
				} catch (IllegalArgumentException e) {
					logger.error(e.toString());
				} catch (InvocationTargetException e) {
					logger.error(e.toString());
				}catch(Exception e){
					logger.error(e.toString());
				}
    		} 
    	} 
    	return obj; 
    } 

}
