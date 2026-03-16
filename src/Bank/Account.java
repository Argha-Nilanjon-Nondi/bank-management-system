package Bank;

import java.sql.*;

/**
 * Handles bank account operations, including account creation,
 * balance inquiries, money transfers, and transaction history.
 * Interaction is primarily performed against the 'account' and 'tx_log'
 * tables in the SQLite database.
 */
public class Account {
    /** SQLite database connection URL */
    static String url = "jdbc:sqlite:bank.db";

    /**
     * Retrieves the account number associated with a given User ID.
     *
     * @param user_id The unique ID of the user.
     * @return The 8-digit account number, or 0 if not found.
     */
    public static int getAccountNo(String user_id) {
        try (Connection con = DriverManager.getConnection(url)) {
            PreparedStatement stat = con.prepareStatement("SELECT account_no from account where user_id=? ;");
            stat.setString(1, user_id);
            ResultSet s = stat.executeQuery();
            return s.getInt("account_no");
        } catch (SQLException e) {
            System.out.println("[!] DB Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Creates a new bank account for a user with an initial balance.
     * Automatically seeds a unique 8-digit account number.
     *
     * @param userid  The unique ID of the user.
     * @param balance The initial balance to deposit.
     * @return true if account creation was successful, false otherwise.
     */
    public static boolean createBankAccount(String userid, int balance) {
        try (Connection con = DriverManager.getConnection(url)) {
            int account_no = Account.seedAccountNo();
            PreparedStatement stat = con.prepareStatement("INSERT INTO account (user_id,account_no,balance) VALUES (?,?,?);");
            stat.setString(1, userid);
            stat.setInt(2, account_no);
            stat.setInt(3, balance);
            int s = stat.executeUpdate();
            if (s == 1) {
                System.out.println("Account No created: " + account_no);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("[!] DB Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a bank account with the given account number exists.
     *
     * @param account_no The 8-digit account number to check.
     * @return true if the account exists, false otherwise.
     */
    public static boolean isAccountExist(int account_no) {
        try (Connection con = DriverManager.getConnection(url)) {
            PreparedStatement stat = con.prepareStatement("SELECT Count(user_id) as Id from account where account_no=?;");
            stat.setInt(1, account_no);
            ResultSet s = stat.executeQuery();
            return s.getInt("Id") > 0;
        } catch (SQLException e) {
            System.out.println("[!] DB Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Seeds a unique account number by repeatedly generating random numbers
     * until one is found that does not already exist in the database.
     *
     * @return A unique 8-digit account number.
     */
    public static int seedAccountNo() {
        while (true) {
            int account_no = Utility.genAccountNo();
            if (!Account.isAccountExist(account_no)) {
                return account_no;
            }
        }
    }

    /**
     * Retrieves the current balance for a specific bank account.
     *
     * @param account_no The 8-digit account number.
     * @return The current balance, or 0 if query fails.
     */
    public static int getBalance(int account_no) {
        try (Connection con = DriverManager.getConnection(url)) {
            PreparedStatement stat = con.prepareStatement("SELECT balance from account where account_no=? ;");
            stat.setInt(1, account_no);
            ResultSet s = stat.executeQuery();
            return s.getInt("balance");
        } catch (SQLException e) {
            System.out.println("[!] DB Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Executes a money transfer between two accounts.
     * This method performs three distinct database operations:
     * 1. Logs the transaction in the 'tx_log' table.
     * 2. Debits the sender's account.
     * 3. Credits the receiver's account.
     *
     * @param sender_acc_no   The 8-digit account number of the sender.
     * @param recevier_acc_no The 8-digit account number of the receiver.
     * @param amount          The amount to transfer.
     * @return true if all steps of the transaction were successful, false
     *         otherwise.
     */
    public static boolean sendMoney(int sender_acc_no, int recevier_acc_no, int amount) {
        try (Connection con = DriverManager.getConnection(url)) {
            // 1. insert into transaction for history
            PreparedStatement stat0 = con.prepareStatement("INSERT INTO tx_log(sender_acc_no,receiver_acc_no,amount) VALUES (?,?,?);");
            stat0.setInt(1, sender_acc_no);
            stat0.setInt(2, recevier_acc_no);
            stat0.setInt(3, amount);
            stat0.executeUpdate();

            // 2. decrease sender account balance
            PreparedStatement stat1 = con.prepareStatement("UPDATE account SET balance=? WHERE account_no=?;");
            stat1.setInt(1, Account.getBalance(sender_acc_no) - amount);
            stat1.setInt(2, sender_acc_no);
            stat1.executeUpdate();

            // 3. increase reciver account balance
            PreparedStatement stat2 = con.prepareStatement("UPDATE account SET balance=? WHERE account_no=?;");
            stat2.setInt(1, Account.getBalance(recevier_acc_no) + amount);
            stat2.setInt(2, recevier_acc_no);
            stat2.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("[!] DB Error in sendMoney: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prints the transaction history for a specific account.
     * Displays both Sent and Received transfers.
     *
     * @param account_no The 8-digit account number.
     */
    public static void getTransHistory(int account_no) {
        try (Connection con = DriverManager.getConnection(url)) {
            // 1. Retrieve SENT transactions
            System.out.println("[ SENT MONEY ]");
            PreparedStatement statSent = con
                    .prepareStatement("SELECT receiver_acc_no, amount from tx_log where sender_acc_no=? ;");
            statSent.setInt(1, account_no);
            ResultSet sSent = statSent.executeQuery();
            boolean hasSent = false;
            while (sSent.next()) {
                System.out.println("  -> To: " + sSent.getInt("receiver_acc_no") + " | Amount: " + sSent.getInt("amount"));
                hasSent = true;
            }
            if (!hasSent) System.out.println("  (No sent transactions)");

            // 2. Retrieve RECEIVED transactions
            System.out.println("\n[ RECEIVED MONEY ]");
            PreparedStatement statRecv = con
                    .prepareStatement("SELECT sender_acc_no, amount from tx_log where receiver_acc_no=? ;");
            statRecv.setInt(1, account_no);
            ResultSet sRecv = statRecv.executeQuery();
            boolean hasRecv = false;
            while (sRecv.next()) {
                System.out.println("  <- From: " + sRecv.getInt("sender_acc_no") + " | Amount: " + sRecv.getInt("amount"));
                hasRecv = true;
            }
            if (!hasRecv) System.out.println("  (No received transactions)");

        } catch (SQLException e) {
            System.out.println("[!] DB Error retrieving history: " + e.getMessage());
        }
    }
}
