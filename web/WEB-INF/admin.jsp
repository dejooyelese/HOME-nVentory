<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h1>HOME nVentory Administration</h1>
        
        <h4>Menu</h4>
        <ul>
            <li><a href="inventory">Inventory</a></li>
            <li><a href="admin">Admin</a></li>
            <li><a href="accountsettings">Account</a></li>
            <li><a href="category" >Category Management</a></li>
            <li><p>Logged in as: ${username}</p><a href="login">Logout</a></li>
        </ul>
        
        <h3>Manage Users</h3>
        <c:choose>
            <c:when test="${selectedUser == null}">
                <table>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="user" items="${users}" >
                        <tr>
                            <td>${user.username}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <form action="admin?delete" method="POST">
                                <td>
                                    <input type="hidden" name="userToDelete" value="${user.username}">
                                    <input type="submit" value="Delete">
                                </td>
                            </form>
                            <form action="admin?edit" method="POST">
                                <td>
                                    <input type="hidden" name="userToEdit" value="${user.username}">
                                    <input type="submit" value="Edit">
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:when test="${selectedUser != null}">
                <form action="admin?save" method="POST">
                    Username: <input type="text" name="selUserUN" value="${selectedUser.username}" readonly="readonly" ><br>
                    Password: <input type="text" name="selUserPW" value="${selectedUser.password}"><br>
                    Email: <input type="text" name="selUserEmail" value="${selectedUser.email}"><br>
                    First Name: <input type="text" name="selUserFN" value="${selectedUser.firstName}"><br>
                    Last Name: <input type="text" name="selUserLN" value="${selectedUser.lastName}"><br>
                    Active: <input type="checkbox" name="selActive" <c:if test="${selectedUser.active}"> checked </c:if>><br>
                    Admin: <input type="checkbox" name="selIsAdmin" <c:if test="${selectedUser.isAdmin}"> checked </c:if>><br>
                    <input type="submit" value="save">
                </form>
            </c:when>
        </c:choose>
        
        <h4>Add User</h4>
        <form action="admin?add" method="POST">
            Username: <input type="text" name="newUserUN" ><br>
            Password: <input type="text" name="newUserPW" ><br>
            Email: <input type="text" name="newUserEmail" ><br>
            First Name: <input type="text" name="newUserFN" ><br>
            Last Name: <input type="text" name="newUserLN" ><br>
            <input type="submit" value="save" >
        </form>
        <div>
            ${message}
        </div>
    </body>
</html>
