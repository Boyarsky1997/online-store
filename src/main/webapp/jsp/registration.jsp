<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Registration</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form action="/registration" method="post" class="container g-3 col-sm-6">
    <h1>Registration</h1>
    <div class="form-group row">
        <label for="inputName" class="col-sm-2 col-form-label">Name</label>
        <div class="col-sm-10">
            <input type="text" name="name" class="form-control" id="inputName" placeholder="Name">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputSurname" class="col-sm-2 col-form-label">Surname</label>
        <div class="col-sm-10">
            <input type="text" name="surname" class="form-control" id="inputSurname" placeholder="Surname">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputEmail3" class="col-sm-2 col-form-label">Login</label>
        <div class="col-sm-10">
            <input type="email" name="login" class="form-control" id="inputEmail3" placeholder="Login">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
        <div class="col-sm-10">
            <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="Password">
        </div>
    </div>
    <p></p>
    <fieldset class="form-group">
        <div class="row">
            <legend class="col-form-label col-sm-2 pt-0">Role</legend>
            <div class="col-sm-10">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="role" id="gridRadios1" value="ADMIN" checked>
                    <label class="form-check-label" for="gridRadios1">
                        Admin
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="role" id="gridRadios2" value="BUYER">
                    <label class="form-check-label" for="gridRadios2">
                        Buyer
                    </label>
                </div>
            </div>
        </div>
    </fieldset>
    <p></p>
    <div class="col-12">
        <button type="submit" class="btn btn-primary">Register</button>
    </div>
    <p></p>
    <c:if test="${not empty message}">
        <div class="alert alert-danger" role="alert">
                ${message}
        </div>
    </c:if>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
