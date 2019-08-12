package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        // This code will execute before AdminServlet and InventoryServlet
        HttpSession session = ((HttpServletRequest)request).getSession();
        
        if(session.getAttribute("username") != null) {
            // if they are authenicated (have username in session) then allow
            // them to continue to servlet
            chain.doFilter(request, response);
        } else {
            // No username in session so, send them to login page
            ((HttpServletResponse)response).sendRedirect("login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
