
import filter.MyFilter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import service.SecurityService;

import javax.servlet.ServletException;
import java.io.File;

public class Webapp {

    public static void main(String[] args) {

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        SecurityService securityService = new SecurityService();
        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        Class filterClass = MyFilter.class;
        FilterDef myFilterDef = new FilterDef();
        myFilterDef.setFilterClass(filterClass.getName());
        myFilterDef.setFilterName(filterClass.getSimpleName());


        Context ctx;
        try {
            ctx = tomcat.addWebapp("", docBase.getAbsolutePath());

            ctx.addFilterDef(myFilterDef);

            FilterMap myFilterMap = new FilterMap();
            myFilterMap.setFilterName(filterClass.getSimpleName());

            myFilterMap.addURLPattern("/*");

            ctx.addFilterMap(myFilterMap);

            servletRouter.init(ctx);
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException ex) {
            ex.printStackTrace();
        }

    }

}
