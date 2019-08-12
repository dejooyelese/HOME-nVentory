<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form action="reset?newPass" method="POST">
            <input type="hidden" name="resetUUID" value="${uuid}" >
            <input type="text" name="newPassword" >
            <input type="submit" value="Submit" >
        </form>
        <div>
            ${message}
        </div>
    </body>
</html>
