package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {// more secure, logout if seeing LoginServlet page
        HttpSession session = request.getSession();
        session.invalidate();
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if((((password == null) || (password.equals(""))) ||((username == null) || (username.equals(""))))) {
            request.setAttribute("message", "Both values are required");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);  
            return;
        }
        
        AccountService ass = new AccountService();
        if ((ass.login(username, password)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("inventory");
        } else {
            request.setAttribute("message", "Username or password is incorrect");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

}
