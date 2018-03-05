<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>

<form action="/bookhut/signup" method="post">
    <label for="username">Username</label>
    <input id="username" type="text" name="username" required="required">
    <label for="password">Password</label>
    <input id="password" type="password" name="password" required="required">
    <input type="submit" value="Sign Up" name="signup">
</form>
</body>
</html>