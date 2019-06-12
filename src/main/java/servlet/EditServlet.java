package servlet;

import routable.Routable;
import service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EditServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/edit";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oldUsername = request.getParameter("old_username");
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");

        if(securityService.containsUser(username)&&!username.equals(oldUsername)){
            String error = "Username already exists";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
            rd.include(request, response);
        }
        else if(!securityService.containsUser(oldUsername)){
            String error = "Username does not exist";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
            rd.include(request, response);
        }
        else{
            try {
                securityService.editUser(oldUsername, username, firstName, lastName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/users");
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
        rd.include(request, response);
    }


}
