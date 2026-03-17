package Bank;

import java.security.SecureRandom;
import java.sql.*;
import java.util.UUID;

/**
The core application logic, database schema design, and architectural structure were developed by
the human developer (Argha Nilanjon Nondi).

Antigravity (AI Coding Assistant) assisted in:
- Structuring and fulfilling the initial documentation.
- Adding comprehensive Javadoc and explanatory comments for future maintainability.
- Prettifying the console UI with banners and formatted output to enhance the user experience.

**/

/**
 * Utility class providing helper methods for unique ID generation,
 * random alphanumeric strings, and bank account numbers.
 * These methods are used throughout the Bank application for
 * user and account identification.
 */
public class Utility {
    /** SQLite database connection URL */
    static final String url = "jdbc:sqlite:bank.db";

    /** Character set used for random string generation */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /** Secure random number generator for cryptographic strength strings */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a secure random alphanumeric string of a specified length.
     * Primarily used for generating initial user passwords.
     *
     * @param length The number of characters to generate.
     * @return A random alphanumeric string.
     * @throws IllegalArgumentException if length is less than 1.
     */
    public static String genRandStr(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be at least 1");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // Get a random index from the character set
            int randomIndex = random.nextInt(CHARACTERS.length());
            // Append the character to the result
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    /**
     * Generates a unique Universal Unique Identifier (UUID).
     * Used for unique User IDs (user_id) in the system.
     *
     * @return A string representation of a random UUID.
     */
    public static String genID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Generates a random 8-digit bank account number.
     * The number ranges from 10,000,000 to 99,999,999.
     *
     * @return An 8-digit integer representing a new account number.
     */
    public static int genAccountNo() {
            // Generates a random number between 10,000,000 (inclusive) and 100,000,000 (exclusive)
            int eightDigitNumber = (int) (Math.random() * 90000000) + 10000000;
            return eightDigitNumber;
    }
}


