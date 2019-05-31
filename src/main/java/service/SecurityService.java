package service;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;


/**
 *
 * @author gigadot
 */
public class SecurityService {

//
//    private Map<String, String> userCredentials = new HashMap<String, String>() {{
//        put("admin", "123456");
//        put("muic", "1111");
//    }};


    static Connection conn;
    static{
       String url = "jdbc:mysql://localhost:3307/a4";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url,"user","12345");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public boolean authenticateDB(String username, String password, HttpServletRequest request){

        String passwordInDB = getPassword(username);
        boolean isMatched = StringUtils.equals(password, passwordInDB);
        if (isMatched) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }

    }

    public void registerUser(String username, String password, String firstname, String lastname){
        try {

            String query = "insert into users (username, password, firstname, lastname)"
                    + " values (?, ?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, username);
            preparedStmt.setString (2, password);
            preparedStmt.setString (3, firstname);
            preparedStmt.setString (4, lastname);
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isAuthorizedDB(HttpServletRequest request) throws SQLException {

        String username = (String) request.getSession()
                .getAttribute("username");
        // do checking
        return (username != null && containsUser(username));

    }

    private String getPassword(String username){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT password from users where username='"+username+"'");
            rs.next();
            return rs.getString(1); //here

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        SecurityService service = new SecurityService();
        //service.removeUser("nn");


    }

    public boolean containsUser(String username) throws SQLException {
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT COUNT(1) FROM users WHERE username='"+username+"'");//"+username);
        int exist = 0;
        if ( rs.next() ) {
            exist = rs.getInt(1);
        }
        return exist==1;

    }

    public boolean isValidUserPwd(String username, String password){

        try {
            if(containsUser(username)){
                String passwordDB = getPassword(username);
                return passwordDB.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void removeUser(String username) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM users WHERE username='"+username+"'");


    }



}
