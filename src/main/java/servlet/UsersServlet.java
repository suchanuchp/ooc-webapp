package servlet;



import routable.Routable;
import service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/users";
    }

    @Override
    public void setSecurityService(SecurityService securityService)  {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;
        if(securityService.isAuthorizedDB(request)){
            ResultSet rs;
            try {
                rs =  securityService.getUsersRS();
                request.setAttribute("users", rs);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            rd = request.getRequestDispatcher("WEB-INF/users.jsp");

            rd.include(request, response);
        }
        else{
            String error = "Login to see users list.";
            request.setAttribute("error", error);
            rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request, response);
        }



    }
}
