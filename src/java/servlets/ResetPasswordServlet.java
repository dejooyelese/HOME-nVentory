package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("uuid") != null) {
            request.setAttribute("uuid", request.getParameter("uuid"));
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AccountService ass = new AccountService();
        
        if(request.getParameter("newPass") != null) {
           if(ass.changePassword(request.getParameter("resetUUID"), (String) request.getParameter("newPassword"))) {
               request.setAttribute("message", "Password has been reset");
           } else {
               request.setAttribute("message", "Failed to reset password.");
           }
           
           getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
           return;
        }
        
        if(ass.resetPassword(request.getParameter("emailAddress"), getServletContext().getRealPath("/WEB-INF/emailtemplates/resetpassword.html"), request.getRequestURL().toString())) {
            request.setAttribute("message", "Recovery email sent.");
        } else {
            request.setAttribute("message", "No user with that email address");
        }
    
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }
}
