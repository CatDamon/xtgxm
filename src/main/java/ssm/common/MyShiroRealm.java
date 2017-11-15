package ssm.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import ssm.exception.SystemCtrlException;
import ssm.service.systemManage.system.UserManageService;

import javax.annotation.Resource;

import ssm.utils.CodecAndCrypUtil;
import ssm.utils.PageData;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm{

	@Resource(name="UserManageServiceImpl")
	private UserManageService userManageService;

	/**
	 * 权限认证
	 * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		String userName = (String) principal.getPrimaryPrincipal();   //获取用户名
		//根据用户名获取用户相关权限(用户所拥有的角色对应的权限)
		List<String> userPerList = null;
 		try {
			userPerList =  this.userManageService.selectPerByUserName(userName);
			userPerList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
 		info.addStringPermissions(userPerList);
		return info;
	}

	/**
	 *身份认证 
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();

		PageData pd = null;
		try {
			pd = this.userManageService.selectUserByUsername(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, pd.getString("password"),getName());
		if(info != null){
			return info;
		}else{
			return null;
		}
	}

}
