<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Welcome, ${username}</h2>

<form action=/remove>
    <input type="submit" value="remove user" name="conf"/>
</form>
<form action=/register>
    <input type="submit" value="register user" name="conf"/>
</form>
<form action=/logout>
    <input type="submit" value="log out" name="conf"/>
</form>

</body>
</html>
