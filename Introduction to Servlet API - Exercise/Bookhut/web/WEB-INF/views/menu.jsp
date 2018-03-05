<%@ page import="bookhut.models.bindingModels.LoginBindingModel" %>
<%@ page import="bookhut.utils.Constants" %>
<%@ page import="static bookhut.utils.Constants.LOGIN_MODEL" %>
<%@ page import="static bookhut.utils.Constants.USERNAME" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="username" type="java.lang.String"--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/menu.css">
    <title>Menu</title>
</head>
<body>
<ul>
    <li><a href="/bookhut">Home</a></li>
    <li><a href="/bookhut/signup">Sign Up</a></li>

    <%
        LoginBindingModel loginModel = (LoginBindingModel) session.getAttribute(LOGIN_MODEL);
        String username;
        if (loginModel != null) {
            username = loginModel.getUsername();
            request.setAttribute(USERNAME, username);
        }
    %>
    <c:set var="username" value="${username}" scope="session"/>
    <c:choose>
        <c:when test="${username != null}">
            <li><a href="/bookhut/signout">Sign Out(${username})</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="/bookhut/signin">Sign In</a></li>
        </c:otherwise>
    </c:choose>

    <li><a href="/bookhut/add">Add Book</a></li>
    <li><a href="/bookhut/shelves">Shelves</a></li>
</ul>
</body>
</html>
