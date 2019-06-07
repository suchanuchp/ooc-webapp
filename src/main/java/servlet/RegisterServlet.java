package servlet;

import org.apache.commons.lang.StringUtils;
import routable.Routable;
import service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/register";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");


        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)&&
        !StringUtils.isBlank(lastname)&&!StringUtils.isBlank(firstname)){

            if(securityService.containsUser(username)){
                String error = "Username is already taken.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(request, response);

            }
            else{
                securityService.registerUser(username, password, firstname, lastname);
                response.sendRedirect("/");
            }

        }else{
            String error = "Enter all the fields required.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(request, response);
        }


        // check username and password against database
        // if valid then set username attribute to session via securityService
        // else put error message to render error on the login form

    }

}
