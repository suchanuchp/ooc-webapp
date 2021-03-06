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

public class RemoveServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/remove";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/remove.jsp");
//        rd.include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String current_username = (String) request.getSession()
                .getAttribute("username");

        if (!StringUtils.isBlank(username)) {

            if (securityService.isAuthorizedDB(request)
                    && !current_username.equals(username)) {

                try {
                    securityService.removeUser(username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String msg = "Successfully removed "+username;
                request.setAttribute("message", msg);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/users.jsp");
                rd.include(request, response);

            }else if (!securityService.isAuthorizedDB(request)){
                String error = "Login to remove users";
                request.setAttribute("message", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
            }
            else if (current_username.equals(username)){

                String error = "Cannot remove yourself from the users";
                request.setAttribute("message", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/users.jsp");
                rd.include(request, response);
            }


            else{
                response.sendRedirect("/users");
            }


        }
        else{
            String error = "Enter username and password.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/remove.jsp");
            rd.include(request, response);
        }
    }
}
