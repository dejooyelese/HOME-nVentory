<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <h1>Register for HOME nVentory</h1>
        <form action="register" method="POST" >
            Username: <input type="text" name="username" ><br>
            Password: <input type="text" name="password" ><br>
            Re-enter Password: <input type="text" name="repassword" ><br>
            Firstname: <input type="text" name="firstname" ><br>
            Lastname: <input type="text" name="lastname" ><br>
            Email: <input type="email" name="email" ><br>
            <input type="submit" value="Register!">
        </form>
        <div>
            ${message}
        </div>
    </body>
</html>
