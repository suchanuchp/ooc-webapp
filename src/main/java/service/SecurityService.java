package service;

import java.util.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author gigadot
 */
public class SecurityService {
    public static final String DATABASE_HOSTNAME = Optional.ofNullable(System.getenv("DATABASE_HOSTNAME")).orElse("localhost");
    public static final String DATABASE_PORT = Optional.ofNullable(System.getenv("DATABASE_PORT")).orElse("3307");

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = String.format("jdbc:mysql://%s:%s/logindata?useSSL=false&characterEncoding=UTF-8&user=root&password=12345", DATABASE_HOSTNAME, DATABASE_PORT);


    static private List<User> users = new ArrayList<>();

    static Connection conn;
    static{
       String url = "jdbc:mysql://localhost:3307/a4";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,"user","12345");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static{
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from users;");
            while(rs.next()){
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                User user = new User(firstname, lastname, username, pass);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public boolean authenticateDB(String username, String password, HttpServletRequest request){

        String passwordInDB = getPassword(username);
        boolean isMatched = checkPass(password, passwordInDB);
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
            String hashed = hashPassword(password);
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, username);
            preparedStmt.setString (2, hashed);
            preparedStmt.setString (3, firstname);
            preparedStmt.setString (4, lastname);
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isAuthorizedDB(HttpServletRequest request){

        String username = (String) request.getSession()
                .getAttribute("username");
        // do checking
        return (username != null && containsUser(username));

    }

    private String getPassword(String username){
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT password from users where username='"+username+"'");
//            rs.next();
//            return rs.getString(1); //here
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
        int index = getUserIndex(username);
        User user = users.get(index);
        return user.getHashedPass();

    }

    public static void main(String[] args) throws SQLException {
        SecurityService service = new SecurityService();
        //System.out.println(service.hashPassword("1234"));
        String passwordInDB = service.getPassword("admin");
        boolean isMatched = service.checkPass("1234", passwordInDB);
        service.editUser("jane412", "jane", "Jane", "Taylor");
        System.out.println(isMatched);


    }
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }


    private boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);

    }

    public boolean containsUser(String username) {
//        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                ResultSet.CONCUR_READ_ONLY);
//        ResultSet rs = stmt.executeQuery("SELECT COUNT(1) FROM users WHERE username='"+username+"'");//"+username);
//        int exist = 0;
//        if ( rs.next() ) {
//            exist = rs.getInt(1);
//        }
//        return exist==1;
        for(User user : users){
            if(user.getUserName().equals(username)) return true;
        }
        return false;

    }

    public boolean isValidUserPwd(String username, String password){

        if(containsUser(username)){
            String passwordDB = getPassword(username);
            return passwordDB.equals(password);
        }
        return false;

    }

    public void removeUser(String username) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM users WHERE username='"+username+"'");
        int index = getUserIndex(username);
        users.remove(index);

    }

    private int getUserIndex(String username){
        int i = 0;
        for(User user : users){
            if(user.getUserName().equals(username)) return i;
            i++;
        }
        return -1;
    }

    public List<User> getUsers(){ return users;}

    public void editUser(String oldUserName, String newUserName, String firstName, String lastName) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE users SET username ='"+newUserName+"', firstname ='"+firstName+"'"+", lastname ='"+
                lastName+"' WHERE username = '"+oldUserName+"'");

    }



}
