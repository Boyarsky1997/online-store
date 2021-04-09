<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Addition</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form action="/addition" method="post" class="container g-3 col-sm-6">
    <h1>Add a product</h1>
    <div class="form-group row">
        <label for="inputName" class="col-sm-2 col-form-label">Name</label>
        <div class="col-sm-10">
            <input type="text" name="name" class="form-control" id="inputName" placeholder="Name">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputPrice" class="col-sm-2 col-form-label">Price</label>
        <div class="col-sm-10">
            <input type="number" name="price" class="form-control" id="inputPrice" placeholder="Price">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputEmail3" class="col-sm-2 col-form-label">Count</label>
        <div class="col-sm-10">
            <input type="number" name="count" class="form-control" id="inputEmail3" placeholder="Count">
        </div>
    </div>
    <p></p>
    <div class="form-group row">
        <label for="inputPassword3" class="col-sm-2 col-form-label">Description</label>
        <div class="col-sm-10">
            <input type="text" name="description" class="form-control" id="inputPassword3" placeholder="Description">
        </div>
    </div>
    <p></p>
    <div class="col-12">
        <button type="submit" class="btn btn-primary">Addition</button>
    </div>
    <p></p>
<%--    <c:if test="${not empty message}">--%>
<%--        <div class="alert alert-danger" role="alert">--%>
<%--                ${message}--%>
<%--        </div>--%>
<%--    </c:if>--%>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
