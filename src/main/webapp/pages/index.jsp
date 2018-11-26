<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>fufu登录管理系统</title>
</head>
<body>
<h1>欢迎登录，${user.username}</h1>
    <a href=<%=request.getContextPath() +"/admin" %>>admin权限访问</a>
    <a href=<%=request.getContextPath() +"/test" %>>guest权限访问</a>
    <input type = "button" value = "登出" onclick="javascript:window.location.href='/logout';">
</body>
</html>