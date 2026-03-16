module bank.account {
    // Required for JDBC (Connection, DriverManager, etc.)
    requires java.sql;

    // Required specifically for the SQLite driver
    requires org.xerial.sqlitejdbc;

    // Allows the JVM to access your Bank.User class
    exports Bank;
}