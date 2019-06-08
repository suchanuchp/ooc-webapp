
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<head>
    <%@ page import="java.sql.*" %>
</head>
<body >
<p>${message}</p>


<%

    try
    {
        ResultSet rs= (ResultSet) request.getAttribute("users");
%><table class="w3-table-all w3-card-4">

    <tr>
        <th>Username</th>
        <th>First Name</th>
        <th>Last Name</th>
        <form action="/register" method="get">
            <td><input type = "submit"  value="Add User"></td>
        </form>
        <form action="/edit" method="get">
            <td><input type = "submit"  value="Edit User"></td>
        </form>

    </tr>
    <%while(rs.next())
    {
    %>
    <%String user = rs.getString(1);
    String pass = rs.getString(2);
    String firstname = rs.getString(3);
    String lastname = rs.getString(4); %>
    <tr>
        <td><%out.print(user);%></td>
        <td><%out.print(firstname);%></td>
        <td><%out.print(lastname);%></td>
        <form action="/remove?username=<%=user%>&password=<%=pass%>" method="post">
            <td><input type = "submit"  value="Remove User"></td>
        </form>

    </tr>
    <%
        }%>
</table><br>
<%}
catch(SQLException e){
    out.print(e.getMessage());%><br><%
    }
%>
</body>
</html>