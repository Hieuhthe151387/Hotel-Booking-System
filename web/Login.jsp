<%-- 
    Document   : Login
    Created on : May 31, 2022, 12:42:06 AM
    Author     : Linh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form>
            <div>
                <label>Username</label>
                <input id="user" name="email" type="text" class="input" required="">
            </div>
            <div>
                <label>Password</label>
                <input id="pass" name="password" type="password" class="input" required="">
            </div>

            <div class="group">
                <c:if test="${not empty message}">
                    <p style="color: white">${message}</p>  
                </c:if>
            </div>
            <div class="group">
                <input type="submit" value="Sign In">
            </div>
        </form>
    </body>
</html>
