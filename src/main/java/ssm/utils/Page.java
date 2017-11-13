package ssm.utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author dxgong
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 5859907455479273251L;

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private int pageIndex = 1; // 当前页号，从1开始

	private List<T> data = new ArrayList<T>(); // 当前页中存放的记录,类型一般为List

	private long resultCount; // 总记录数
	private int currentResult; // 当前记录起始索引
	private int pageNumStart;// 页码显示开始listbegin;
	private int pageNumEnd;// 页码显示结束listend;
	private int showPageNum = 10;// 显示页码个数，默认是10
	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private int totalPage;		//总页数
	private PageData pd = new PageData();

	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	public int getCurrentResult() {
		currentResult = (getPageIndex()-1)*getPageSize();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	public int getTotalPage() {
		if(resultCount%pageSize==0)
			totalPage = (int)resultCount/pageSize;
		else
			totalPage = (int)resultCount/pageSize+1;
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	/**
	 * 默认构造方法.
	 *
	 * @param pageIndex
	 *            当前页号
	 * @param totalSize
	 *            数据库中总记录条数

	 */
	public Page(Integer pageIndex, long totalSize) {
		this.pageIndex = pageIndex == null ? 1 : pageIndex;
		this.resultCount = totalSize;
		setPageNumEnd(pageNumEnd);
		setPageNumStart(pageNumStart);
	}

	public Page() {

	}

	/**
	 * 当前页号
	 * 
	 * @return 第一条记录的截取位置
	 */
	public int getPageIndex() {
		pageIndex = pageIndex < 1 ? 1 : pageIndex;
		pageIndex = pageIndex > getPageCount() ? getPageCount() : pageIndex;
		return pageIndex;
	}

	/**
	 * 默认构造方法.
	 *
	 * @param pageIndex
	 *            当前页号
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public Page(Integer pageIndex, long totalSize, int pageSize, List<T> data) {
		this(pageIndex, totalSize);
		this.pageSize = pageSize;
		this.setData(data);
	}

	/**
	 * 默认构造方法.
	 *
	 * @param pageIndex
	 *            当前页号
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 */
	public Page(Integer pageIndex, long totalSize, int pageSize) {
		this(pageIndex, totalSize);
		this.pageSize = pageSize;
	}

	/**
	 * 设置本页包含的数据
	 * 
	 * @param data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 取总记录数.
	 * 
	 * @return 符合查询条件的记录总数
	 */
	public long getResultCount() {
		return this.resultCount;
	}

	/**
	 * 取总页数.
	 * 
	 * @return 符合查询条件的记录总页数
	 */
	public int getPageCount() {
		if (resultCount % pageSize == 0) {
			return (int) (resultCount / pageSize);
		} else {
			return (int) (resultCount / pageSize + 1);
		}
	}

	/**
	 * 取每页数据容量.
	 * 
	 * @return 每页可容纳的记录数量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 * 
	 * @return 当前数据页
	 */
	public List<T> getData() {
		if (this.data == null) {
			this.data = new ArrayList<T>();
		}
		return data;
	}

	public T getFirstData() {
		return ListUtil.isBlank(data) ? null : data.get(0);
	}

	/**
	 * 该页是否有下一页.
	 * 
	 * @return 如果当前页是最后一页，返回false，否则返回true
	 */
	public boolean hasNextPage() {
		return this.getPageIndex() < this.getPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 * 
	 * @return 如果当前页码为0，返回true，否则返回false
	 */
	public boolean hasPreviousPage() {
		return this.getPageIndex() > 0;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageSize
	 *            每页的容量
	 * @return 该页第一条数据在符合条件的查询结果中的位置。
	 */
	public int getStartOfPage(int pageSize) {
		return (pageIndex - 1) * pageSize;
	}

	/**
	 * 获取下一页
	 * 
	 * @return
	 */
	public int getNextPage() {
		return this.getPageIndex() + 1 > this.getPageCount() ? this.getPageCount() : this.getPageIndex() + 1;
	}

	/**
	 * 获取上一页
	 * 
	 * @return
	 */
	public int getPrePage() {
		return this.getPageIndex() - 1 > 0 ? this.getPageIndex() - 1 : 1;
	}

	public int getPageNumStart() {
		return pageNumStart;
	}

	public void setPageNumStart(int pageNumStart) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= pageIndex) {
			this.pageNumStart = 1;
		} else {
			if (pageIndex + halfPage > getPageCount()) {
				this.pageNumStart = (getPageCount() - showPageNum + 1) <= 0 ? 1 : (getPageCount() - showPageNum + 1);
			} else {
				this.pageNumStart = pageIndex - halfPage + 1;
			}
		}
	}

	public int getPageNumEnd() {
		return pageNumEnd;
	}

	public void setPageNumEnd(int pageNumEnd) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= pageIndex) {
			// this.pageNumEnd = showPageNum;
			this.pageNumEnd = showPageNum > getPageCount() ? getPageCount() : showPageNum;
		} else {
			if (pageIndex + halfPage >= getPageCount()) {
				this.pageNumEnd = getPageCount();
			} else {
				this.pageNumEnd = pageIndex + halfPage;
			}
		}
	}

	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}

	// 传给mybatis，limit start,size

	/**
	 * 表示查询时记录从那一行开始
	 * 
	 * @return
	 */
	public long getStart() {
		return ((this.getPageIndex() <= 0 ? 1 : this.getPageIndex()) - 1) * pageSize;// + 1; 
	}

	public long getSize() {
		return (this.getPageIndex() <= 0 ? 1 : this.getPageIndex()) * pageSize;
	}
	
	
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		totalPage=getTotalPage();
		if(getResultCount()>0){
			sb.append("<div class=\"message\">共<i class=\"blue\">"+resultCount+"</i>条记录，当前显示第&nbsp;<i class=\"blue\">"
		               +pageIndex+"&nbsp;</i>页,共&nbsp;<i class=\"blue\">"+totalPage+"&nbsp;</i>页</div>");
			sb.append("	<ul class=\"paginList\">\n");
			if(pageIndex==1){
				sb.append("	<li class=\"paginItem\"><a>首页</a></li>\n");
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagepre\"></span></a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\">首页</a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+(pageIndex-1)+")\"><span class=\"pagepre\"></span></a></li>\n");
			}
			int showTag = 5;//分页标签显示数量
			int startTag = 1;
			if(pageIndex>showTag){
				startTag = pageIndex-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(pageIndex==i)
					sb.append("<li class=\"paginItem current\"><a href=\"javascript:;\">"+i+"</a></li>\n");
				else
					sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
			}
			if(pageIndex==totalPage){
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\"><a>尾页</a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+(pageIndex+1)+")\"><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+totalPage+")\">尾页</a></li>\n");
			}
			
			
			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");
			
			//换页函数
			sb.append("function nextPage(page){");
			sb.append(" ");
			sb.append("	if(true && document.forms[0]){\n");
			/*sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");*/
			sb.append("		var url = $(window.parent.document).find('a[class=\"content-tab active\"]').attr('data-id');\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"pageSize":"page.pageSize")+"="+pageSize+"\";\n");
			sb.append("		document.forms[0].action = url;\n");
			//sb.append("		document.forms[0].submit();\n");
			sb.append("		document.location.href=url;\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('pageIndex')>-1){\n");
			sb.append("				var reg = /pageIndex=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'pageIndex='+page);\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"pageIndex":"page.pageIndex")+"=\"+page;\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"pageIndex":"page.pageIndex")+"=\"+page;}\n");
			sb.append("		url = url  + \"&" +(entityOrField?"pageSize":"page.pageSize")+"="+pageSize+"\";\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			//调整每页显示条数
			sb.append("function changeCount(value){");
			sb.append(" ");
			sb.append("	if(true && document.forms[0]){\n");
			/*sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");*/
			sb.append("		var url = $(window.parent.document).find('a[class=\"content-tab active\"]').attr('data-id');\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";}\n");
			sb.append("		url = url + \"1&" +(entityOrField?"pageSize":"page.pageSize")+"=\"+value;\n");
			sb.append("		document.forms[0].action = url;\n");
			/*sb.append("		document.forms[0].submit();\n");*/
			sb.append("		document.location.href=url;\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('pageIndex')>-1){\n");
			sb.append("				var reg = /pageIndex=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'pageIndex=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"1&"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"pageIndex":"page.pageIndex")+"=\";}\n");
			sb.append("		url = url + \"&" +(entityOrField?"pageSize":"page.pageSize")+"=\"+value;\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			sb.append("</script>\n");
		}
		String pageStr = sb.toString();
		return pageStr;
	}
	
	/**
	 * 基于异步请求的分页查询，获取页码，需要页面自己完善nextPage()
	 * */
	public String getPageStrOnAjax() {
		StringBuffer sb = new StringBuffer();
		totalPage=getTotalPage();
		if(getResultCount()>0){
			sb.append("<div class=\"message\">共<i class=\"blue\">"+resultCount+"</i>条记录，当前显示第&nbsp;<i class=\"blue\">"
		               +pageIndex+"&nbsp;</i>页,共&nbsp;<i class=\"blue\">"+totalPage+"&nbsp;</i>页</div>");
			sb.append("	<ul class=\"paginList\">\n");
			if(pageIndex==1){
				sb.append("	<li class=\"paginItem\"><a>首页</a></li>\n");
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagepre\"></span></a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\">首页</a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+(pageIndex-1)+")\"><span class=\"pagepre\"></span></a></li>\n");
			}
			int showTag = 5;//分页标签显示数量
			int startTag = 1;
			if(pageIndex>showTag){
				startTag = pageIndex-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(pageIndex==i)
					sb.append("<li class=\"paginItem current\"><a href=\"javascript:;\">"+i+"</a></li>\n");
				else
					sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
			}
			if(pageIndex==totalPage){
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\"><a>尾页</a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+(pageIndex+1)+")\"><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage("+totalPage+")\">尾页</a></li>\n");
			}
			
			sb.append("</ul>\n");
			
		}
		String pageStr = sb.toString();
		return pageStr;
	}
	
	/**
	 * 基于异步请求的分页查询，获取页码，需要页面自己完善nextPage()
	 * */
	public String getPageStrOnAjax2() {
		StringBuffer sb = new StringBuffer();
		totalPage=getTotalPage();
		if(getResultCount()>0){
			sb.append("<div class=\"message\">共<i class=\"blue\">"+resultCount+"</i>条记录，当前显示第&nbsp;<i class=\"blue\">"
		               +pageIndex+"&nbsp;</i>页,共&nbsp;<i class=\"blue\">"+totalPage+"&nbsp;</i>页</div>");
			sb.append("	<ul class=\"paginList\">\n");
			if(pageIndex==1){
				sb.append("	<li class=\"paginItem\"><a>首页</a></li>\n");
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagepre\"></span></a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage2(1)\">首页</a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage2("+(pageIndex-1)+")\"><span class=\"pagepre\"></span></a></li>\n");
			}
			int showTag = 5;//分页标签显示数量
			int startTag = 1;
			if(pageIndex>showTag){
				startTag = pageIndex-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(pageIndex==i)
					sb.append("<li class=\"paginItem current\"><a href=\"javascript:;\">"+i+"</a></li>\n");
				else
					sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage2("+i+")\">"+i+"</a></li>\n");
			}
			if(pageIndex==totalPage){
				sb.append("	<li class=\"paginItem\"><a><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\"><a>尾页</a></li>\n");
			}else{
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage2("+(pageIndex+1)+")\"><span class=\"pagenxt\"></span></a></li>\n");
				sb.append("	<li class=\"paginItem\" style=\"cursor:pointer;\"><a onclick=\"nextPage2("+totalPage+")\">尾页</a></li>\n");
			}
			
			sb.append("</ul>\n");
			
		}
		String pageStr = sb.toString();
		return pageStr;
	}
}