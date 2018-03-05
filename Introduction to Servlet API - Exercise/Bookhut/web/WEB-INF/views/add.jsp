<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>

<form action="/bookhut/add" method="post">
    <label for="title">Title</label>
    <input id="title" type="text" name="title" required="required">
    <label for="author">Author</label>
    <input id="author" name="author" required="required">
    <label for="pages">Pages</label>
    <input id="pages" name="pages" required="required">
    <input type="submit" value="Add" name="add">
</form
</body>
</html>