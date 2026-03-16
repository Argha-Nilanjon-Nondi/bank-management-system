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
