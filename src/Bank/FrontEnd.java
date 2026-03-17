package Bank;

import java.util.Scanner;

/**
The core application logic, database schema design, and architectural structure were developed by
the human developer (Argha Nilanjon Nondi).

Antigravity (AI Coding Assistant) assisted in:
- Structuring and fulfilling the initial documentation.
- Adding comprehensive Javadoc and explanatory comments for future maintainability.
- Prettifying the console UI with banners and formatted output to enhance the user experience.

**/

/**
 * Provides the console-based User Interface for the Bank application.
 * Handles user login (both Admin and regular User), profile viewing,
 * transaction history, and the money transfer wizard.
 */
public class FrontEnd {

    /** Scanner for reading user input from the console */
    Scanner type = new Scanner(System.in);

    /** Local cache for the logged-in user's username */
    private String username;

    /** Local cache for the logged-in user's account number */
    private int account_no;

    /** Local cache for the logged-in user's unique ID */
    private String user_id;

    /**
     * Wizard-style interface for sending money to another account.
     * Guides the user through entering the recipient's account number
     * and the transfer amount, with validation for existence and balance.
     */
    public void userSendMoney() {
        System.out.println("\n========================================");
        System.out.println("          MONEY TRANSFER WIZARD         ");
        System.out.println("========================================");
        while (true) {
            System.out.print("Enter recipient account no : ");
            int recipent_accno = type.nextInt();

            // Prevent self-transfers
            if (this.account_no == recipent_accno) {
                System.out.println("[!] Error: You cannot send money to yourself.");
                break;
            }

            // Verify recipient existence before asking for amount
            if (Account.isAccountExist(recipent_accno) == true) {
                while (true) {
                    System.out.print("Enter amount to send : ");
                    int amount = type.nextInt();
                    // Verify sufficient balance
                    if (Account.getBalance(this.account_no) >= amount) {
                        if (Account.sendMoney(this.account_no, recipent_accno, amount) == true) {
                            System.out.println("\n>>> SUCCESS: Transaction completed successfully! <<<");
                        }
                        break;
                    } else {
                        System.out.println("[!] Error: Insufficient balance. Current balance: " + Account.getBalance(this.account_no));
                    }
                }
                break;
            } else {
                System.out.println("[!] Error: Recipient account not found. Please try again.");
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * Retrieves and displays the transaction history for the logged-in user.
     */
    public void userTransctionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        Account.getTransHistory(this.account_no);
        System.out.println("---------------------------\n");
    }

    /**
     * Displays the full profile and current balance of the logged-in user.
     */
    public void userProfile() {
        System.out.println("\n+---------------------------------------+");
        System.out.println("|             USER PROFILE              |");
        System.out.println("+---------------------------------------+");
        System.out.printf("| Username   : %-24s |\n", this.username);
        System.out.printf("| Account No : %-24d |\n", this.account_no);
        System.out.printf("| User ID    : %-24s |\n", this.user_id);
        System.out.println("+---------------------------------------+");
        System.out.printf("| BALANCE    : %-24d |\n", Account.getBalance(this.account_no));
        System.out.println("+---------------------------------------+\n");
    }

    /**
     * Main action loop for a logged-in User.
     * Provides basic banking options like sending money and checking history.
     */
    public void userAction() {
        while (true) {
            System.out.println("********** MAIN MENU **********");
            System.out.println("1. Send Money");
            System.out.println("2. View History");
            System.out.println("3. My Profile");
            System.out.println("0. Logout (Close App)");
            System.out.println("*******************************");
            System.out.print("Choose an option : ");
            int option = type.nextInt();
            switch (option) {
                case 1:
                    this.userSendMoney();
                    break;
                case 2:
                    this.userTransctionHistory();
                    break;
                case 3:
                    this.userProfile();
                    break;
                case 0:
                    System.out.println("Logging out... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("[!] Invalid option. Please select 1, 2, or 3.");
            }
        }

    }

    /**
     * Placeholder for administrative actions.
     */
    public void adminAction() {
        System.out.println("\n[ADMIN PANEL MODE]");
        System.out.println("Coming Soon...\n");
    }

    /**
     * Entry point for selecting the user type (Admin vs User) and logging in.
     */
    public void askUserType() {
        System.out.println("****************************************");
        System.out.println("*      WELCOME TO ANTIGRAVITY BANK     *");
        System.out.println("****************************************");
        System.out.println("Please select your user type:");
        System.out.println("1. Administrator");
        System.out.println("2. Regular User");
        System.out.print("Choice > ");

        int user_type = type.nextInt();
        switch (user_type) {
            case 1:
                this.askAdminLogin();
                break;
            case 2:
                this.askUserLogin();
                break;
            default:
                System.out.println("Invalid type. Exiting.");
        }
    }

    /**
     * Guides a regular user through the multi-step login process:
     * 1. Prompt and verify username existence.
     * 2. Prompt and verify password against the stored credential.
     * 3. Upon success, cache user details and enter userAction() loop.
     */
    public void askUserLogin() {
        System.out.println("\n--- USER LOGIN ---");
        while (true) {
            System.out.print("Username : ");
            this.username = type.next();
            if (User.isUserExist(username) == true) {
                break;
            } else {
                System.out.println("[!] Error: User not found.");
            }
        }

        while (true) {
            System.out.print("Password : ");
            String password = type.next();
            if (User.isPasswordCorrect(this.username, password) == true) {
                this.user_id = User.getUserId(this.username);
                this.account_no = Account.getAccountNo(this.user_id);
                System.out.println(">>> Login Successful! Welcome, " + this.username + " <<<\n");
                break;
            } else {
                System.out.println("[!] Error: Incorrect password.");
            }
        }

        this.userAction();

    }

    /**
     * Guides an administrator through the login process (Coming Soon).
     */
    public void askAdminLogin() {
        System.out.println("\n--- ADMIN LOGIN ---");
        System.out.println("Administrative feature is currently under development.");
        System.out.println("Returning to main menu...\n");
        this.askUserType();
    }

    /**
     * Main method: Launch the application.
     */
    public static void main(String[] args) {
        new FrontEnd().askUserType();
    }
}
