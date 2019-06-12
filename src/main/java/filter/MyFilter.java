package filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class MyFilter implements Filter {
    static HashSet<String> servlet;
    static{
        servlet = new HashSet<>(Arrays.asList("/login", "/users", "/remove", "/edit","/index.jsp","/logout","/register"));

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //httpResponse.sendRedirect("/");

        if (servletRequest instanceof HttpServletRequest) {

            String path = ((HttpServletRequest) servletRequest).getServletPath();
            if(!servlet.contains(path)){
                System.out.println("here");
                ((HttpServletResponse) servletResponse).sendRedirect("/users");


            }
            else{
                filterChain.doFilter(servletRequest, httpResponse);
            }
            System.out.println(path);

        }
        else{
            filterChain.doFilter(servletRequest, httpResponse);
        }




    }

    @Override
    public void destroy() {

    }
}
