/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import routable.Routable;
import service.SecurityService;
import servlet.*;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gigadot
 */
public class ServletRouter {

    private static final List<Class<? extends Routable>> routables = new ArrayList<>();

    static {
        routables.add(HomeServlet.class);
        routables.add(LoginServlet.class);
        routables.add(RegisterServlet.class);
        routables.add(RemoveServlet.class);
        routables.add(UsersServlet.class);
        routables.add(LogoutServlet.class);

        routables.add(EditServlet.class);
    }

    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void init(Context ctx) {
        for (Class<? extends Routable> routableClass : routables) {
            try {
                Routable routable = routableClass.newInstance();
                routable.setSecurityService(securityService);
                String name = routable.getClass().getSimpleName();
                Tomcat.addServlet(ctx, name, (HttpServlet) routable);
                System.out.println(routable.getMapping());
                ctx.addServletMapping(routable.getMapping(), name);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
