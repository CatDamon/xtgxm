package ssm.service.login.impl;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;
import ssm.exception.SystemServiceException;
import ssm.service.common.impl.BaseServiceImpl;
import ssm.service.login.LoginService;
import ssm.utils.PageData;

import java.util.List;


@Service("LoginServiceImpl")
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

	public List<PageData> select() throws Exception {
		return (List<PageData>) this.findForList("loginMapper.select");
	}

	@Override
	public PageData selectUserAll(PageData userData) throws Exception{
		logger.info("LoginServiceImpl selectUserAll...");
		if(StringUtil.isNotBlank(userData.getString("userName"))){
			return  (PageData)this.daoSupport.findForObject("loginMapper.selectUserAll",userData);
		}else{
			throw new SystemServiceException("用户名不能为空");
		}
	}


}
