<html>
<body>
<h2>Register</h2>
<p>${error}</p>
<form action="/register" method="post">
    Username:<br/>
    <input type="text" name="username"/>
    <br/>
    First name:<br/>
    <input type="text" name="firstname"/>
    <br/>
    Last name:<br/>
    <input type="text" name="lastname"/>
    <br/>
    Password:<br/>
    <input type="password" name="password">
    <br><br>
    <input type="submit" value="Submit">

</form>
</body>
</html>