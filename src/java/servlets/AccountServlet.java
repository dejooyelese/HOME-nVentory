package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        UserService us = new UserService();
        
        try {
            request.setAttribute("user", us.get((String) session.getAttribute("username")));
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = null;
        
        UserService us = new UserService();
        try {
            u = us.get((String) session.getAttribute("username"));
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(request.getParameter("deactivate") != null) {
            
            try {
                u.setActive(false);
                
                us.update(u.getUsername(),
                        u.getPassword(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getActive(),
                        u.getIsAdmin(),
                        null,
                        null);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("message", "Account has been deactivated.");
            
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        String fn = request.getParameter("newFirstname");
        String ln = request.getParameter("newLastname");
        String email = request.getParameter("newEmail");
        
        if(     ((fn == null) || (fn.equals("")))
                || ((ln == null) || (ln.equals("")))
                || ((email == null) || (email.equals("")))
                ) {
            request.setAttribute("message", "No fields can be blank");
        } else {
        
            u.setFirstName(fn);
            u.setLastName(ln);
            u.setEmail(email);

            try {
                us.update(u.getUsername(),
                        u.getPassword(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getActive(),
                        u.getIsAdmin(),
                        null,
                        null);
            } catch (Exception ex) {
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }



            request.setAttribute("message", "Account information has been updated successfully.");
        }
        
        try {
            request.setAttribute("user", us.get((String) session.getAttribute("username")));
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);  
 
    }
}
