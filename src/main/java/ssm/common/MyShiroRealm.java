package ssm.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import ssm.exception.SystemCtrlException;
import ssm.service.systemManage.system.UserManageService;

import javax.annotation.Resource;

import ssm.utils.CodecAndCrypUtil;
import ssm.utils.PageData;

public class MyShiroRealm extends AuthorizingRealm{

	@Resource(name="UserManageServiceImpl")
	private UserManageService userManageService;

	/**
	 * 权限认证
	 * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		System.out.println("哈哈"+principal.getPrimaryPrincipal());

		return null;
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
