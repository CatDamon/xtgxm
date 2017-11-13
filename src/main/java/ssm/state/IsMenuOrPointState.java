package ssm.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 权限类别状态
 * @author dxgong
 *
 */
public class IsMenuOrPointState extends SysStateSupport{
	private static Map<String, SysStateSupport> stateMap = new HashMap<String, SysStateSupport>();

	public IsMenuOrPointState(String type_code, String short_desc) {
		this.type_code = type_code;
		this.short_desc = short_desc;
	}

	public static final IsMenuOrPointState CDQX = new IsMenuOrPointState("0", "菜单权限");
	public static final IsMenuOrPointState YMQXD = new IsMenuOrPointState("1", "页面权限点");
	static {
		stateMap.put(CDQX.getType_code(), CDQX);
		stateMap.put(YMQXD.getType_code(), YMQXD);
	}

	public static List<String[]> findStatesByAll() {
		return makeStatesList(stateMap);
	}

	public static IsMenuOrPointState codeToState(String type_code) {
		return (IsMenuOrPointState) stateMap.get(type_code);
	}

	public static String codeToDesc(String type_code) {
		return getDescOfCode(stateMap, type_code);
	}
}
