<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Login</title>
</head>
<body>
<form action="/login" method="post">

    <table cellpadding="5">
        <tr>
            <td><b>Email:</b></td>
            <td><input type="text" name="email" required/></td>
        </tr>

        <tr>
            <td><b>Password:</b></td>
            <td><input type="password" name="password" required/></td>
        </tr>

        <tr>
            <td colspan="2" align="center"><input type="submit" value="Login"/></td>
        </tr>

    </table>
    <c:if test="${not empty unfaithful}">
        <div class="alert alert-danger" role="alert">
                ${unfaithful}
        </div>
    </c:if>
</form>
</body>
</html>