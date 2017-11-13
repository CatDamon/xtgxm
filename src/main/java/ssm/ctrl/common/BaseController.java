package ssm.ctrl.common;


import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import ssm.StartJetty;
import ssm.entity.User;
import ssm.utils.Const;
import ssm.utils.Page;
import ssm.utils.PageData;
import ssm.utils.UuidUtil;

import javax.servlet.http.HttpServletRequest;


public class BaseController{
	public static final Logger logger = StartJetty.logger;

	private static final long serialVersionUID = 6357869213649815390L;
	
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public static String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	//异常处理
	/*@ExceptionHandler  
	public String exp(HttpServletRequest request, Exception ex) {  
		if(ex instanceof SDSJControllerException){
			request.setAttribute("ex", ex.getMessage());  
		}else if(ex instanceof SDSJServiceException){
			request.setAttribute("ex", ex.getMessage());  
		}else{
			request.setAttribute("ex", "访问出现异常，请稍后再试！");  
		}
		
		logger.error(ex.toString(),ex);
		return "/error.html";
	}*/
	/**
	 * 获取会话中的用户对象
	 * @return
	 */
	protected User getUser(){
		return (User)getRequest().getSession().getAttribute(Const.SESSION_USER);
	}
	
	protected org.apache.shiro.session.Session getSession(){
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session =  subject.getSession();
		return session;
	}

	/**输入条件查询的数据
	 * pd 请求参数
	 * mv 模型
	 * page
	 *
	 * */
	protected void setConditionForQuery(PageData pd,ModelAndView mv,Page page){
		if(pd.containsKey("condition")){
			if(!"-1".equals(pd.get("condition"))){ //非-1说明是条件查询
				page.setPd(pd);
				mv.addObject(pd.getString("condition"),true);
				if(pd.containsKey("conditionVal")){
					mv.addObject("conditionVal",pd.getString("conditionVal"));
				}
			}
		}
	}
}
