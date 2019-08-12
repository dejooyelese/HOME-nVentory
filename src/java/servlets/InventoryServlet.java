package servlets;

import exceptions.CategoryDBException;
import exceptions.ItemDBException;
import exceptions.UserDBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;

import services.AccountService;
import services.InventoryService;
import models.User;
import models.Item;


public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService ass = new AccountService();
        InventoryService is = new InventoryService();
        
        User u = null;
        try {
            u = ass.get((String) session.getAttribute("username"));
            
            request.setAttribute("categories", is.getAllCategories(
            ));
            
        } catch (UserDBException | CategoryDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("firstname", u.getFirstName());
        request.setAttribute("lastname", u.getLastName());
        request.setAttribute("items", u.getItemList());
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        InventoryService is = new InventoryService();
        AccountService ass = new AccountService();
        
        String username = (String) session.getAttribute("username");
        
        User u = null;
        try {
            u = ass.get(username);
        } catch (UserDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("firstname", u.getFirstName());
        request.setAttribute("lastname", u.getLastName());
        
        if(request.getParameter("delete") != null) {
            
            Item itemToDelete = null;
            try {
                itemToDelete = is.getItem(Integer.parseInt(request.getParameter("itemToDelete")));
            } catch (ItemDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int check = is.deleteItem(Integer.parseInt(request.getParameter("itemToDelete")), username);
            if(check == 1) {
                //u.getItemList().remove(itemToDelete);
                request.setAttribute("message", "The item has been successfully deleted.");
            } else if(check == 0) {
                request.setAttribute("message", "You can only delete your own items");
            }
            
        } else if(request.getParameter("add") != null) {
            try {
                is.insertItem(request.getParameter("newItemName"),
                        Double.parseDouble(request.getParameter("newItemPrice")),
                        is.getCategory(Integer.parseInt(request.getParameter("newItemCategory"))),
                        ass.get((String) session.getAttribute("username")));
            } catch (CategoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("message", "The item has been successfully added.");
        } else if(request.getParameter("edit") != null) {
            try {
                //TODO create ablility to edit inventory items
                request.setAttribute("selectedItem", is.getItem(Integer.parseInt(request.getParameter("itemToEdit"))));
            } catch (ItemDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(request.getParameter("save") != null) {
            String itemName = request.getParameter("editName");
            Double itemPrice = Double.parseDouble(request.getParameter("editItemPrice"));
            Category itemCategory = null;
            try {
                itemCategory = is.getCategory(Integer.parseInt(request.getParameter("editItemCategory")));
            } catch (CategoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(itemCategory != null) {
                if(is.updateItem(username, Integer.parseInt(request.getParameter("itemToEditID")), itemName, itemPrice, itemCategory) == 0) {
                    request.setAttribute("message", "The item has been successfully updated.");
                } else {
                    request.setAttribute("message", "Can only edit your own items");
                }
            }
        }
        
        try {
            request.setAttribute("categories", is.getAllCategories());
        } catch (CategoryDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            request.setAttribute("items", ass.get(username).getItemList());
        } catch (UserDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
