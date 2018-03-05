<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>

<form action="/bookhut/shelves/edit" method="post">
    <label for="title">Title</label>
    <input id="title" type="text" name="title" value="${TITLE}" required="required">
    <label for="author">Content</label>
    <input id="author" name="author" value="${AUTHOR}" required="required">
    <label for="pages">Pages</label>
    <input id="pages" name="pages" value="${PAGES}" required="required">
    <input type="hidden" value="${TITLE}" name="oldTitle">
    <input type="submit" value="Edit" name="edit">
</form
</body>
</html>