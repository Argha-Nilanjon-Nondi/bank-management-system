package Bank;

import java.sql.*;

/**
The core application logic, database schema design, and architectural structure were developed by
the human developer (Argha Nilanjon Nondi).

Antigravity (AI Coding Assistant) assisted in:
- Structuring and fulfilling the initial documentation.
- Adding comprehensive Javadoc and explanatory comments for future maintainability.
- Prettifying the console UI with banners and formatted output to enhance the user experience.

**/

/**
 * Handles user management operations, including existence checks,
 * authentication, registration, and profile data retrieval.
 * Interaction is primarily performed against the 'user' table in the SQLite
 * database.
 */
public class User {

    /** SQLite database connection URL */
    static String url = "jdbc:sqlite:bank.db";

    /**
     * Checks if a user with the given username exists in the database.
     *
     * @param username The username to check.
     * @return true if the user exists, false otherwise.
     */
    public static boolean isUserExist(String username) {

        try (Connection con = DriverManager.getConnection(url)) {
            // Count users with matching username
            PreparedStatement stat = con.prepareStatement("SELECT Count(user_id) as Id from user where username=?;");
            stat.setString(1, username);
            ResultSet s = stat.executeQuery();

            if (s.getInt("Id") == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Authenticates a user by checking if the username and password match.
     *
     * @param username The username for authentication.
     * @param password The password to verify.
     * @return true if credentials are correct, false otherwise.
     */
    public static boolean isPasswordCorrect(String username, String password) {

        try (Connection con = DriverManager.getConnection(url)) {
            // Verify username and password combination
            PreparedStatement stat = con
                    .prepareStatement("SELECT Count(user_id) AS Id from user where username=? AND password=?;");
            stat.setString(1, username);
            stat.setString(2, password);
            ResultSet s = stat.executeQuery();

            if (s.getInt("Id") == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Updates the password for a specific user.
     *
     * @param username     The username whose password will be changed.
     * @param new_password The new password to set.
     * @return true if the password was successfully updated, false otherwise.
     */
    public static boolean changePassword(String username, String new_password) {
        try (Connection con = DriverManager.getConnection(url)) {
            PreparedStatement stat = con.prepareStatement("UPDATE user SET password=? WHERE username=? ;");
            stat.setString(1, new_password);
            stat.setString(2, username);
            int s = stat.executeUpdate();

            if (s == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Registers a new user and automatically creates a bank account if the role is
     * 'USER'.
     *
     * @param username The desired username.
     * @param role     The role assigned to the user (e.g., 'USER', 'ADMIN').
     * @return true if user creation was successful, false otherwise.
     */
    public static boolean createUser(String username, String role) {
        try (Connection con = DriverManager.getConnection(url)) {
            // Generate unique user ID and random initial password
            String userid = Utility.genID();
            String password = Utility.genRandStr(8);

            PreparedStatement stat = con
                    .prepareStatement("INSERT INTO user (user_id,username,password,role) VALUES (?,?,?,?);");
            stat.setString(1, userid);
            stat.setString(2, username);
            stat.setString(3, password);
            stat.setString(4, role);

            int s = stat.executeUpdate();

            if (s == 1) {
                System.out.println("Password :" + password);
                System.out.println("User ID :" + userid);
                // Automatically create a bank account with initial balance for 'USER' role
                if (role.equals("USER")) {
                    Account.createBankAccount(userid, 10000);
                }
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the internal User ID for a given username.
     *
     * @param username The username to look up.
     * @return The unique user_id string, or an empty string if not found.
     */
    public static String getUserId(String username) {

        try (Connection con = DriverManager.getConnection(url)) {
            PreparedStatement stat = con.prepareStatement("SELECT user_id from user where username=? ;");
            stat.setString(1, username);
            ResultSet s = stat.executeQuery();

            return s.getString("user_id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static void main(String[] arg) {
        // System.out.println(obj.isUserExist("ruhme"));
        // System.out.println((obj.isPasswordCorrect("rume","pass")));
        // System.out.println(obj.changePassword("rume","pass1234"));
        // System.out.println(obj.createUser("argha","user"));
        // System.out.println(obj.createBankAccount("876876877",1000));
        // System.out.println(User.createUser("argha","USER"));
        // System.out.println(User.createUser("rafid","USER"));
        // System.out.println(Utility.genAccountNo());
        // System.out.println("Hi");
    }
}
