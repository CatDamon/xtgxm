package ssm;



import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import jdk.internal.org.xml.sax.SAXException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author dxgong
 * 2017年3月13日
 * */
public class StartJetty {

    public static final Logger logger = LoggerFactory.getLogger(StartJetty.class);
	public static void main(String[] args) {
        try {
            // 服务器的监听端口  
            Server server = new Server(8088);
            // 关联一个已经存在的上下文  
            WebAppContext context = new WebAppContext();
            // 设置描述符位置
            context.setDefaultsDescriptor("/WEB-INF/web.xml");
            // 设置Web资源文件路径
            context.setBaseResource(Resource.newClassPathResource("/static/"));
            // 设置上下文路径  
            context.setContextPath("/");
            context.setMaxFormContentSize(Integer.MAX_VALUE);
            //context.setParentLoaderPriority(true);  
            server.setHandler(context);  
            // 启动  
            server.start();  
            server.join();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (SAXException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
}
