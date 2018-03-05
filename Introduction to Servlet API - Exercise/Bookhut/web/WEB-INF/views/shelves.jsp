<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bookhut.models.bindingModels.LoginBindingModel" %>
<%@ page import="static bookhut.utils.Constants.ALL_BOOKS" %>
<%@ page import="static bookhut.utils.Constants.USERNAME" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="ALL_BOOKS" type="java.util.Set"--%>
<html>
<head>
    <title>Home</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/table.css">
<jsp:include page="menu.jsp"></jsp:include>

<table class="tb">
    <thead>
    <th>Title</th>
    <th>Author</th>
    <th>Pages</th>
    <th>Edit Book</th>
    <th>Delete Book</th>
    </thead>
    <tbody>
    <%--<c:set var="books" value="${ALL_BOOKS}"/>--%>
    <c:forEach var="currentBook" items="${ALL_BOOKS}">
        <tr>
            <td>
                <c:out value="${currentBook.title}"/>
            </td>
            <td>
                <c:out value="${currentBook.author}"/>
            </td>
            <td>
                <c:out value="${currentBook.pages}"/>
            </td>
            <td>
                <a href="/bookhut/shelves/edit/${currentBook.title}">Edit</a>
            </td>
            <td>
                <a href="/bookhut/shelves/delete/${currentBook.title}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>