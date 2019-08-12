package filters;

import exceptions.UserDBException;
import models.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

public class AdminFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        
        HttpSession session = ((HttpServletRequest)request).getSession();
        
        AccountService ass = new AccountService();
        
        User u = null;
        try {
            u = ass.get((String) session.getAttribute("username"));
        } catch (UserDBException ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(u.getIsAdmin()) {
            // user is an admin send them on
            chain.doFilter(request, response);
        } else {
            // user is not an admin
            ((HttpServletResponse)response).sendRedirect("inventory");
        }
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
