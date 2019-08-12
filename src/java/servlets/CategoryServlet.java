package servlets;

import exceptions.CategoryDBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.InventoryService;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        InventoryService is = new InventoryService();
        
        try {
            request.setAttribute("categories", is.getAllCategories());
        } catch (CategoryDBException ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        InventoryService is = new InventoryService();
        
        int check = 0;
        if(request.getParameter("add") != null) {
            check = is.insertCategory(request.getParameter("newCategoryName"));
            if (check == 1) {
                request.setAttribute("message", "The category was successfully added");
            }else  {
                request.setAttribute("message", "Failed to add category");
            }
        } else if(request.getParameter("edit") != null) {
            try {
                request.setAttribute("selectedCategory", is.getCategory(Integer.parseInt(request.getParameter("categoryToEdit"))));
            } catch (CategoryDBException ex) {
                Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(request.getParameter("save") != null) {
            check = is.updateCategory(Integer.parseInt(request.getParameter("selCategoryID")), request.getParameter("selCategoryName"));
            if (check == 1) {
                request.setAttribute("message", "The category was successfully updated");
            }else  {
                request.setAttribute("message", "Failed to update category");
            }
        }
        
        try {
            request.setAttribute("categories", is.getAllCategories());
        } catch (CategoryDBException ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
    }
}
