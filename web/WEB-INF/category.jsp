<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory</title>
        <style>
            table {
                border: 1px solid black;
            }
            td {
                border: 1px solid black;
            }
        </style>
    </head>
    <body>
        <h1>Category Management</h1>
        
        <h4>Menu</h4>
        <ul>
            <li><a href="inventory">Inventory</a></li>
            <li><a href="admin">Admin</a></li>
            <li><a href="accountsettings">Account</a></li>
            <li><a href="category" >Category Management</a></li>
            <li><p>Logged in as: ${username}</p><a href="login">Logout</a></li>
        </ul>
        
        <h2>Manage Categories</h2>
        <c:choose>
            <c:when test="${selectedCategory == null}">
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="category" items="${categories}" >
                        <tr>
                            <td>${category.categoryName}</td>
                            <form action="category?edit" method="POST">
                                <td>
                                    <input type="hidden" name="categoryToEdit" value="${category.categoryID}">
                                    <input type="submit" value="Edit">
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:when test="${selectedCategory != null}">
                <form action="category?save" method="POST">
                    <input type="hidden" name="selCategoryID" value="${selectedCategory.categoryID}">
                    Name: <input type="text" name="selCategoryName" value="${selectedCategory.categoryName}">
                    <input type="submit" value="save">
                </form>
            </c:when>
        </c:choose>
        
        <h4>Add Category</h4>
        <form action="category?add" method="POST">
            Name: <input type="text" name="newCategoryName">
            <input type="submit" value="save" >
        </form>
        <div>
            ${message}
        </div>
    </body>
</html>
