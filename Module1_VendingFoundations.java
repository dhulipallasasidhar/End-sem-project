import java.util.Scanner;

public class Module1_VendingFoundations {
    // Constants (final)
    public static final double MIN_DENOMINATION = 0.05; // $0.05 nickel
    public static final double MAX_DENOMINATION = 5.00; // $5.00 bill[cite: 1]
    public static final byte HARDWARE_OK_FLAG = 0b0000_0001;
    public static final byte SENSOR_READY_FLAG = 0b0000_0010;

    public static void main(String[] args) {
        // Scanner for console input
        Scanner scanner = new Scanner(System.in);

        // Primitive data types
        byte systemStatus = (byte) (HARDWARE_OK_FLAG | SENSOR_READY_FLAG); // Bitwise OR operator
        int itemCode = 101;
        double itemPrice = 1.75;
        double userBalance = 0.0;
        boolean isValidatorEnabled = true;

        System.out.println("=================================================");
        System.out.println("   VENDING MACHINE CONTROLLER SIMULATOR (MOD 1)  ");
        System.out.println("=================================================");

        // Formatted printing & Relational / Bitwise evaluation
        boolean isReady = (systemStatus & HARDWARE_OK_FLAG) != 0; // Bitwise AND operator
        System.out.printf("System Status Flag: 0x%02X | Hardware Ready: %b%n", systemStatus, isReady);
        System.out.printf("Selected Item Code: %d | Item Price: $%.2f%n", itemCode, itemPrice);

        // Arithmetic, Unary, and Assignment operators
        System.out.print("\nInsert funds ($0.05, $0.10, $0.25, $1.00, $5.00): $"); //[cite: 1]
        double insertedAmount = scanner.nextDouble();

        // Ternary operator & Unary check
        boolean isValidCoin = (insertedAmount == 0.05 || insertedAmount == 0.10 || 
                               insertedAmount == 0.25 || insertedAmount == 1.00 || 
                               insertedAmount == 5.00); //[cite: 1]
        
        userBalance += isValidCoin ? insertedAmount : 0.0; // Assignment & Ternary

        // Typecasting & Expression evaluation (Precedence and Associativity)
        int centsBalance = (int) Math.round(userBalance * 100); // Explicit typecasting double -> int
        int centsPrice = (int) Math.round(itemPrice * 100);
        int centsDifference = centsBalance - centsPrice; // Expression evaluation

        // Formatted Console Output
        System.out.println("\n----------------- TRANSACTION SUMMARY -----------------");
        System.out.printf("Inserted Amount : $%.2f%n", insertedAmount);
        System.out.printf("Current Balance : $%.2f (%d cents)%n", userBalance, centsBalance);
        System.out.printf("Price Requirement: $%.2f (%d cents)%n", itemPrice, centsPrice);
        System.out.printf("Balance Status  : %s%n", (centsDifference >= 0) ? "Sufficient Funds" : "Insufficient Balance");
        System.out.println("-------------------------------------------------------");

        scanner.close();
    }
}