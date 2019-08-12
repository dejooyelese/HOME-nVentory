package servlets;

import exceptions.UserDBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.UserService;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Log the user out on this page
        
        if(request.getParameter("uuid") != null) {
            UserService us = new UserService();
            User u = us.getByRegistrationUUID(request.getParameter("uuid"));
            
            u.setActive(true);
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
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("message", "Account has been registered. You can now login");
            
            AccountService ass = new AccountService();
            
            ass.welcomeEmail(u.getEmail(), getServletContext().getRealPath("/WEB-INF/emailtemplates/welcome.html"));
            
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        session.invalidate();
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String pw1 = request.getParameter("password");
        String pw2 = request.getParameter("repassword");
        String fn = request.getParameter("firstname");
        String ln = request.getParameter("lastname");
        String email = request.getParameter("email");
        
        if(     ((pw1 == null) || (pw1.equals("")))
                || ((username == null) || (username.equals("")))
                || ((fn == null) || (fn.equals("")))
                || ((ln == null) || (ln.equals("")))
                || ((email == null) || (email.equals("")))
                ) {
            request.setAttribute("message", "No fields can be blank");
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);  
            return;
        } else if(!pw1.equals(pw2)) {
            request.setAttribute("message", "Passwords must match");
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);  
            return;
        }
        
        AccountService ass = new AccountService();
        
        try {
            if(ass.insert(username, pw1, email, fn, ln, false, false) == 0) {
                request.setAttribute("message", "Username already taken");
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            }
        } catch (UserDBException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Registration email here
        if(ass.registerAccount(email, getServletContext().getRealPath("/WEB-INF/emailtemplates/register.html"), request.getRequestURL().toString())) {
            request.setAttribute("message", "Registration email sent");
        } else {
            request.setAttribute("message", "Failed to send registration email.");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
