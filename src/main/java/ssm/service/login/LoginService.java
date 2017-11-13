package ssm.service.login;



import ssm.utils.PageData;

import java.util.List;


public interface LoginService {
	
	//查询
	public List<PageData> select()  throws Exception;

	/**根据用户名称查询用户信息*/
    public PageData selectUserAll(PageData userData) throws Exception;
}
