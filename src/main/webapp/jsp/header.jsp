<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/products">Products</a>
            </li>

            <c:if test="${sessionScope.client.role=='ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="/addition">Add product</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/buyers">Buyers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/blacklist">Blacklist</a>
                </li>
            </c:if>
            <div class="navbar-nav ms-auto">
                <c:if test="${sessionScope.client != null}">
                    <c:if test="${sessionScope.client.role=='BUYER'}">
                    <a class="navbar-brand" style="margin-left: 20px; color: orangered" href="/">
                            ${sessionScope.client.name} ${sessionScope.client.surname}</a>
                    <form action="/basket" method="get" class="container g-3 col-sm-6">
                        <button type="submit" class="btn btn-secondary">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-cart4" viewBox="0 0 16 16">
                                <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"></path>
                            </svg>
                        </button>
                    </form>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="/profile">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">logout</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.client == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/registration">Registration</a>
                    </li>

                </c:if>

            </div>

                <c:if test="${sessionScope.client.role=='ADMIN'}">
                    <li>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-person-check-fill" viewBox="0 0 16 16">
                            <path fill-rule="evenodd"
                                  d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                            <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                        </svg>
                    </li>
                </c:if>
        </ul>
    </div>
</nav>