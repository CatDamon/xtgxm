package ssm.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 菜单类别状态
 * @author dxgong
 *
 */
public class IsHeaderState extends SysStateSupport{
	private static Map<String, SysStateSupport> stateMap = new HashMap<String, SysStateSupport>();

	public IsHeaderState(String type_code, String short_desc) {
		this.type_code = type_code;
		this.short_desc = short_desc;
	}

	public static final IsHeaderState GML = new IsHeaderState("0", "有父级目录");
	public static final IsHeaderState FGML = new IsHeaderState("1", "没有父级目录");
	static {
		stateMap.put(GML.getType_code(), GML);
		stateMap.put(FGML.getType_code(), FGML);
	}

	public static List<String[]> findStatesByAll() {
		return makeStatesList(stateMap);
	}

	public static IsHeaderState codeToState(String type_code) {
		return (IsHeaderState) stateMap.get(type_code);
	}

	public static String codeToDesc(String type_code) {
		return getDescOfCode(stateMap, type_code);
	}
}
