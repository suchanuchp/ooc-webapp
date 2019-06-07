

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<title>W3.CSS</title>--%>
<%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">--%>
<%--<body>--%>

<%--<div class="w3-container">--%>
<%--    <h2>Table as a Card</h2>--%>
<%--    <p>The w3-card-* classes makes the table look like a card (adds shadows):</p>--%>

<%--    <table class="w3-table-all w3-card-4">--%>
<%--        <tr>--%>
<%--            <th>First Name</th>--%>
<%--            <th>Last Name</th>--%>
<%--            <th>Points</th>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Jill</td>--%>
<%--            <td>Smith</td>--%>
<%--            <td>50</td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Eve</td>--%>
<%--            <td>Jackson</td>--%>
<%--            <td>94</td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Adam</td>--%>
<%--            <td>Johnson</td>--%>
<%--            <td>67</td>--%>
<%--        </tr>--%>
<%--    </table>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>


<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<head>
    <%@ page import="java.sql.*" %>
    <%@ page import="java.util.List" %>
    <%@ page import="service.User" %>
</head>
<body >
<p>${message}</p>



<%--<tr>--%>
<%--    <th>Username</th>--%>
<%--    <th>First Name</th>--%>
<%--    <th>Last Name</th>--%>
<%--    <form action="/register" method="get">--%>
<%--        <td><input type = "submit"  value="Add User"></td>--%>
<%--    </form>--%>

<%--</tr>--%>
<%--<c:forEach var="user" items="${usersList}">--%>
<%--    <!-- create an html table row -->--%>
<%--    <tr>--%>
<%--        <!-- create cells of row -->--%>

<%--        <td>${user.userName}</td>--%>
<%--        <td>${user.firstName}</td>--%>
<%--        <td>${user.lastName}</td>--%>
<%--&lt;%&ndash;        <form action="/remove?username=<%=user.userName%>&password=<%=pass%>" method="post">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <td><input type = "submit"  value="Remove User"></td>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </form>&ndash;%&gt;--%>
<%--        <!-- close row -->--%>
<%--    </tr>--%>
<%--    <!-- close the loop -->--%>
<%--</c:forEach>--%>

<%
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=(Connection) DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/a4","user","12345");
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from users;");
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