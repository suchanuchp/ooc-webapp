<html>
<body>
<h2>Edit User</h2>
<p>${error}</p>
<form action="/edit" method="post">
    Old username:<br/>
    <input type="text" name="old_username"/>
    <br/>
    New username:<br/>
    <input type="text" name="username"/>
    <br/>
    First name:<br/>
    <input type="text" name="firstname"/>
    <br/>
    Last name:<br/>
    <input type="text" name="lastname"/>
    <br/>
    <br><br>
    <input type="submit" value="Submit">

</form>
</body>
</html>