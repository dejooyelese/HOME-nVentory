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
import services.AccountService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        AccountService ass = new AccountService();
        
        try {
            request.setAttribute("users", ass.getAll());
        } catch (UserDBException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        AccountService ass = new AccountService();
        
        int check = 0;
        if(request.getParameter("add") != null) {
            
            try {
                check = ass.insert(((String) request.getParameter("newUserUN")),
                        ((String) request.getParameter("newUserPW")),
                        ((String) request.getParameter("newUserEmail")),
                        ((String) request.getParameter("newUserFN")),
                        ((String) request.getParameter("newUserLN")),
                        true, false);
                        
                        } catch (UserDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(check == 1) {
                request.setAttribute("message", "The user was successfully added");
            } else {
                request.setAttribute("message", "Failed to add user");
            }
            
        } else if(request.getParameter("delete") != null) {
            try {
                check = ass.delete((String) session.getAttribute("username"),
                        (String) request.getParameter("userToDelete"));
            } catch (UserDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(check == 1) {
                request.setAttribute("message", "The user was successfully deleted");
            } else {
                request.setAttribute("message", "Can not delete yourself");
            }
        } else if(request.getParameter("edit") != null) {
            try {
                request.setAttribute("selectedUser", ass.get(((String) request.getParameter("userToEdit"))));
            } catch (UserDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(request.getParameter("save") != null) {
            try {
                boolean active = false;
                if(request.getParameter("selActive") != null) {
                    active = true;
                }
                boolean admin = false;
                if(request.getParameter("selIsAdmin") != null) {
                    admin = true;
                }
                // Check is this is supposed to be able to edit active and isAdmin
                check = ass.update(((String) request.getParameter("selUserUN")),
                        ((String) request.getParameter("selUserPW")),
                        ((String) request.getParameter("selUserEmail")),
                        ((String) request.getParameter("selUserFN")),
                        ((String) request.getParameter("selUserLN")),
                        active, admin);
            } catch (UserDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(check == 1) {
                request.setAttribute("message", "The user was successfully updated");
            } else {
                request.setAttribute("message", "Could not update user");
            }
        }

        try {
            request.setAttribute("users", ass.getAll());
        } catch (UserDBException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
