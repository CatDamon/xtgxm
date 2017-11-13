package ssm.common;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import java.io.IOException;

;


public class MyBeetlGroupUtilConfiguration extends BeetlGroupUtilConfiguration {

  @Override
  public void init() {
    try {
      initGroupTemplate();

      config(groupTemplate);

    } catch (IOException e) {
      throw new RuntimeException("加载GroupTemplate失败", e);
    }
  }

  private void initGroupTemplate() throws IOException {
    // 配置数据加载
    Configuration configuration = Configuration.defaultConfiguration();

    ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/templates") {
      @Override
      public void init(GroupTemplate gt) {
        this.setAutoCheck(true);
        this.setCharset("UTF-8");
      }
    };
    groupTemplate = new GroupTemplate(resourceLoader, configuration);
   // groupTemplate.registerFunctionPackage("shiroTag", new SdShiro());
    if (errorHandler != null) {
      groupTemplate.setErrorHandler(errorHandler);
    }

    // 设置共享变量
    if (sharedVars != null) {
      sharedVars.put("layer","");
      groupTemplate.setSharedVars(sharedVars);
    }
  }

}
