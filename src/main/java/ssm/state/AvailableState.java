package ssm.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * 账号是否可用
 * @author dxgong
 *
 */
public class AvailableState extends SysStateSupport{
	private static Map<String, SysStateSupport> stateMap = new HashMap<String, SysStateSupport>();

	public AvailableState(String type_code, String short_desc) {
		this.type_code = type_code;
		this.short_desc = short_desc;
	}

	public static final AvailableState WJH = new AvailableState("0", "未激活");
	public static final AvailableState YJH = new AvailableState("1", "已激活");
	static {
		stateMap.put(WJH.getType_code(), WJH);
		stateMap.put(YJH.getType_code(), YJH);
	}

	public static List<String[]> findStatesByAll() {
		return makeStatesList(stateMap);
	}

	public static AvailableState codeToState(String type_code) {
		return (AvailableState) stateMap.get(type_code);
	}

	public static String codeToDesc(String type_code) {
		return getDescOfCode(stateMap, type_code);
	}
}
