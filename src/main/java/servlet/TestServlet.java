package servlet;

import routable.Routable;
import service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet implements Routable {
    @Override
    public String getMapping() {
        return "/test";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/test.jsp");
        rd.include(request, response);
    }
}
