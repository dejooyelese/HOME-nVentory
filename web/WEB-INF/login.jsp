<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory Login</title>
    </head>
    <body>
        <h1>Welcome to HOME nVentory</h1>
        <form method="post" action="login">
            username: <input type="text" required name="username"><br>
            password: <input type="password" required name="password"><br>
            <input type="submit" value="Submit">
        </form>
        <div>
            <a href="reset" >Reset Password</a>
            <a href="register" >Register</a>
        </div>
        <div>
            ${message}
        </div>
    </body>
</html>
