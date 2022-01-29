package MainPackage.servlets;

import lombok.SneakyThrows;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


public class FilterCarServlet implements Filter {
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            filterChain.doFilter(servletRequest,servletResponse);


    }
}
