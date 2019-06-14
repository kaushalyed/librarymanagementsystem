<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Authorization Server</title>
</head>

<body>
<div style="color:red">
${SPRING_SECURITY_LAST_EXCEPTION.message} 
</div>
<h1>Login</h1>

<%-- <div style="color:red;" th:if="${param.error}">
    Invalid username and password.
</div>
<div th:if="${param.logout}">
    You have been logged out.
</div> --%>

<!-- <form name='f' th:action="@{/login}" method='POST'> -->
<form name='f' action="/login" method='POST'>
    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <!-- <tr>
            <td>ClientId:</td>
            <td><input type="text" name="client_id" value="4AF383AF-C812-47CA-8444-1D1B7050C6B4" /></td>
        </tr> -->
        <tr>
            <td><input name="submit" type="submit" value="Sign In" /></td>
        </tr>
    </table>
</form>
</body>
</html>
