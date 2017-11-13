package ssm.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 定义类型值的类
 * @author phu
 * 2015-12-29
 */
public class SysStateSupport {
	protected static final Logger logger = LoggerFactory.getLogger(SysStateSupport.class);

    protected String type_code;

    protected String short_desc;

    public String getType_code() {
        return type_code;
    }

    public String getShort_desc() {
        return short_desc;
    }

    /**
     * 将类型值转换成数组
     * @param stateMap
     * @return
     */
    protected static List<String[]> makeStatesList(Map<String, SysStateSupport> stateMap) {
        Iterator<String> iter = stateMap.keySet().iterator();
        List<String[]> list = new ArrayList<String[]>();
        while (iter.hasNext()) {
            String type_code = (String) iter.next();
            logger.debug(" type_code : " + type_code);
            SysStateSupport statesupport =stateMap
                    .get(type_code);
            String ss[] = new String[2];
            ss[0] = statesupport.getType_code();
            ss[1] = statesupport.getShort_desc();
            list.add(ss);
        }
        return list;
    }

    /**
     * 根据类型code获取类型名称
     * @param stateMap
     * @param type_code
     * @return
     */
    protected static String getDescOfCode(Map<String,SysStateSupport> stateMap, String type_code) {
        SysStateSupport state = stateMap.get(type_code);
        return state == null ? "" : state.getShort_desc();
    }

    /**
     * 根据指定的类型，将类型值转换成String集合
     * @param stateMap
     * @param type "codeType"：生成code列表；"shortDesc"：生成名称列表
     * @return
     */
    protected static List<String> makeStatesListByType(Map<String, SysStateSupport> stateMap, String type) {
        Iterator<String> iter = stateMap.keySet().iterator();
        List<String> list = new ArrayList<String>();
        while (iter.hasNext()) {
            String type_code = (String) iter.next();
            logger.debug(" type_code : " + type_code);
            SysStateSupport statesupport = stateMap.get(type_code);
            if ("shortDesc".equals(type)) {
            	list.add(statesupport.getShort_desc());
            } else if ("codeType".equals(type)) {
            	list.add(statesupport.getType_code());
            }
        }
        return list;
    }

}
