<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>

<form action="/bookhut/signin" method="post">
    <label for="username">Username</label>
    <input id="username" type="text" name="username" required="required">
    <label for="password">Password</label>
    <input id="password" type="password" name="password" required="required">
    <input type="submit" value="Sign In" name="signin">
</form>
</body>
</html>