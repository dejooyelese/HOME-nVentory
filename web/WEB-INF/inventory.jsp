<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
    </head>
    <style>
        table {
            border: 1px solid black;
        }
        td {
            border: 1px solid black;
        }
    </style>
    <body>
        <h1>HOME nVentory</h1>
        
        <h4>Menu</h4>
        <ul>
            <li><a href="inventory">Inventory</a></li>
            <li><a href="admin">Admin</a></li>
            <li><a href="accountsettings">Account</a></li>
            <li><a href="category" >Category Management</a></li>
            <li><p>Logged in as: ${username}</p><a href="login">Logout</a></li>
        </ul>
        
        <h3>Inventory for ${firstname} ${lastname}</h3>
        <c:choose>
            <c:when test="${selectedItem == null}">
                <table>
                    <tr>
                        <th>Category</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${items}" >
                        <tr>
                            <td>${item.category.categoryName}</td>
                            <td>${item.itemName}</td>
                            <td><fmt:formatNumber value = "${item.price}" type = "currency"/></td>
                            <form action="inventory?edit" method="POST">
                                <td>
                                    <input type="hidden" name="itemToEdit" value="${item.itemID}">
                                    <input type="submit" value="Edit">
                                </td>
                            </form>
                            <form action="inventory?delete" method="POST">
                                <td>
                                    <input type="hidden" name="itemToDelete" value="${item.itemID}">
                                    <input type="submit" value="Delete">
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:when test="${selectedItem != null}">
                <form action="inventory?save" method="POST">
                    <input type="hidden" name="itemToEditID" value="${selectedItem.itemID}">
                    Name: <input type="text" name="editName" value="${selectedItem.itemName}"><br>
                    Price: <input type="number" name="editItemPrice" step="0.01" value="${selectedItem.price}"><br>
                    Category: <select name="editItemCategory">
                                <c:forEach var="category" items="${categories}" >
                                    <option value="${category.categoryID}" <c:if test="${selectedItem.category == category.categoryName}">selected="selected"</c:if>>${category.categoryName}</option>
                                </c:forEach>
                              </select><br>
                    <input type="submit" value="Save Changes">
                </form>
            </c:when>
        </c:choose>
                
        <h4>Add Item</h4>
        <form action="inventory?add" method="POST">
            Category: <select name="newItemCategory">
                        <c:forEach var="category" items="${categories}" >
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                      </select><br>
            Name: <input type="text" name="newItemName"><br>
            Price: <input type="number" name="newItemPrice" step="0.01"><br>
            <input type="submit" value="save" >
        </form>
        <div>
            ${message}
        </div>
    </body>
</html>
