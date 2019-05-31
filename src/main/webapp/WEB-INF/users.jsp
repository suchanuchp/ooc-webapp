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
<head>Users</title>
    <%@ page import="java.sql.*" %>
</head>
<body >
<%
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=(Connection) DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/a4","user","12345");
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from users;");
%><table border=1 align=center style="text-align: center">
    <%while(rs.next())
    {
    %>
    <tr>
        <td><%out.print(rs.getString(1));%></td>
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