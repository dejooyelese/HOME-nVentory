<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Account</title>
    </head>
    <body>
        <h1>Edit Account</h1>
        
        <h4>Menu</h4>
        <ul>
            <li><a href="inventory">Inventory</a></li>
            <li><a href="admin">Admin</a></li>
            <li><a href="accountsettings">Account</a></li>
            <li><a href="category" >Category Management</a></li>
            <li><p>Logged in as: ${username}</p><a href="login">Logout</a></li>
        </ul>
        
        <h3>Account Information</h3>
        <form action="accountsettings" method="POST">
            Firstname: <input type="text" name="newFirstname" value="${user.firstName}"><br>
            Lastname: <input type="text" name="newLastname" value="${user.lastName}"><br>
            Email: <input type="text" name="newEmail" value="${user.email}"><br>
            <input type="submit" value="Update Information"><br>
        </form>
        <div>
            ${message}
        </div>
        
        <h2>Danger Zone</h2>
        <form action="accountsettings?deactivate" method="POST">
            <p><i><strong>To revert this choice you must contact an Admin</strong></i></p>
            <input type="submit" value="Deactivate Account">
        </form>
        
    </body>
</html>
