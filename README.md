# Bank Account Management System

A Java-based console application for managing bank accounts, users, and transactions using SQLite. This project demonstrates JDBC integration, user authentication, and basic financial operations.

#### Video Demo https://youtu.be/UoemUnGxlD4

## Key Features

- **User Authentication**: Secure login with username and password.
- **Profile Management**: View account details including User ID, Account Number, and current Balance.
- **Money Transfers**: Send money between different bank accounts.
- **Transaction History**: View a log of previous transactions.
- **Persistent Storage**: Data is stored locally in an SQLite database (`bank.db`).

## Technologies Used

- **Java**: Core programming logic.
- **JDBC**: For database connectivity.
- **SQLite**: Local relational database.
- **SQLite JDBC Driver**: `sqlite-jdbc-3.51.2.0.jar`.

## Why SQLite?

In this project, **SQLite** was chosen over server-based database systems (like PostgreSQL or MySQL) for several key reasons:

- **Simplicity**: Ideally suited for a local console application, SQLite requires zero configuration and no separate server process.
- **Portability**: The entire database is stored in a single file (`bank.db`), making it easy to share or move the project without setting up complex database environments.
- **Efficiency**: It provides a lightweight yet powerful relational database solution that integrates seamlessly with Java via JDBC.
- **Zero-Latency Local Access**: Since the database resides on the same machine, data access is extremely fast and doesn't depend on network stability.

## Project Structure

- `src/Bank/Account.java`: Handles account-related database operations (balance, transfers, history).
- `src/Bank/User.java`: Manages user authentication and user-specific database operations.
- `src/Bank/FrontEnd.java`: Provides the command-line interface for user interaction.
- `src/Bank/Utility.java`: Contains helper methods for generating IDs and account numbers.
- `module-info.java`: Defines the module requirements and exports.

## Demo Credentials

You can use the following accounts to test the application:

### Account 1
- **Username**: argha
- **Password**: eiTBDUy7
- **User ID**: a85bc8dc-80f0-4e6f-aca9-2222b066a9cc
- **Account No**: 79379464

### Account 2
- **Username**: rafid
- **Password**: 5liUI3oF
- **User ID**: 4ff9939a-d2e4-48d7-b848-8897385e8649
- **Account No**: 70888745

## Implementation Details: Methods & SQL

This section provides a technical deep-dive into the core methods of the system, their underlying logic, and the SQL statements they execute.

### 1. `Account.java` (Financial Operations)

| Method | Logic Description | SQL Statement(s) |
| :--- | :--- | :--- |
| `getAccountNo` | Retrieves the 8-digit account number for a specific User ID. | `SELECT account_no FROM account WHERE user_id=?` |
| `createBankAccount` | Creates a new record in the `account` table with a unique account number and initial balance. | `INSERT INTO account (user_id, account_no, balance) VALUES (?, ?, ?)` |
| `isAccountExist` | Verifies if an account number is already registered in the system. | `SELECT Count(user_id) FROM account WHERE account_no=?` |
| `seedAccountNo` | Loops until it generates a random 8-digit number that doesn't exist in the DB. | Uses `isAccountExist` logic. |
| `getBalance` | Fetches the current available balance for a specific account. | `SELECT balance FROM account WHERE account_no=?` |
| `sendMoney` | **Transaction Logic:** 1. Logs the transfer. 2. Debits the sender. 3. Credits the receiver. | `INSERT INTO tx_log ...`<br>`UPDATE account SET balance=? WHERE account_no=?` |
| `getTransHistory` | Retrieves and prints all sent and received transactions for an account. | `SELECT ... FROM tx_log WHERE sender_acc_no=?`<br>`SELECT ... FROM tx_log WHERE receiver_acc_no=?` |

### 2. `User.java` (Authentication & Identity)

| Method | Logic Description | SQL Statement(s) |
| :--- | :--- | :--- |
| `isUserExist` | Checks if a username is already taken. | `SELECT Count(user_id) FROM user WHERE username=?` |
| `isPasswordCorrect`| Validates login credentials by matching username and password. | `SELECT Count(user_id) FROM user WHERE username=? AND password=?` |
| `changePassword` | Updates the password for a specific user. | `UPDATE user SET password=? WHERE username=?` |
| `createUser` | Registers a new user with a generated UUID and random password. | `INSERT INTO user (user_id, username, password, role) VALUES (?, ?, ?, ?)` |
| `getUserId` | Retrieves the internal UUID associated with a username. | `SELECT user_id FROM user WHERE username=?` |

### 3. `Utility.java` (Helper Methods)

| Method | Logic Description | SQL Statement |
| :--- | :--- | :--- |
| `genRandStr` | Generates a cryptographically secure random alphanumeric string. | N/A |
| `genID` | Creates a unique Universal Unique Identifier (UUID). | N/A |
| `genAccountNo` | Generates a random 8-digit integer for bank account numbers. | N/A |

### 4. `FrontEnd.java` (User Interface)

| Method | Role & Logic |
| :--- | :--- |
| `askUserType` | Initial landing screen; directs users to Admin or Regular login flows. |
| `askUserLogin` | Multi-step flow: 1. Validate username. 2. Validate password. 3. Cache session data. |
| `userAction` | Main interactive loop providing the banking menu (Transfer, History, Profile). |
| `userSendMoney` | **Wizard Logic:** Validates recipient exists and sender has enough funds before calling `Account.sendMoney`. |
| `userProfile` | Aggregates and displays user identity and real-time balance. |
| `userTransHistory`| Calls the background service to fetch and display the transaction log. |

## How to Run

1. Ensure you have the JDK installed.
2. Include the `sqlite-jdbc-3.51.2.0.jar` in your classpath.
3. Compile the source files.
4. Run the `Bank.FrontEnd` class.

## Development & Collaboration

The core application logic, database schema design, and architectural structure were developed by the human developer (Argha Nilanjon Nondi).

**Antigravity** (AI Coding Assistant) assisted in:
- Structuring and fulfilling the initial documentation.
- Adding comprehensive Javadoc and explanatory comments for future maintainability.
- Prettifying the console UI with banners and formatted output to enhance the user experience.

## Future Roadmap

To evolve this project into a more robust and modern financial platform, the following features are planned:

- **Web-Based Interface**: Migrating from a console application into a full-stack web application (e.g., Spring Boot or React) for a modern, accessible user experience.
- **Blockchain for Data Integrity**: Integrating blockchain technology into the `tx_log` (transaction log) for immutable data. This will ensure that transaction history is tamper-proof, providing absolute transparency and security.
- **Admin Section Implementation**: Completing the administrative panel to allow for user management, account monitoring, and system-wide controls.
