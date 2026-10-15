// Custom Exception Hierarchy
class VendingException extends Exception {
    public VendingException(String message) {
        super(message);
    }
}

class OutOfStockException extends VendingException {
    public OutOfStockException(String item) {
        super("FAULT: Item " + item + " is out of stock!");
    }
}

class InsufficientFundsException extends VendingException {
    public InsufficientFundsException(double required, double actual) {
        super(String.format("FAULT: Insufficient funds! Required: $%.2f, Provided: $%.2f", required, actual));
    }
}

// Interface (Programming to Interface)
interface IVendingMachine {
    void selectItem(String code) throws OutOfStockException;
    void insertCoin(double amount);
    double dispenseItem() throws InsufficientFundsException;
}

// Concrete Encapsulated Class
class Item {
    private final String code;
    private final String name;
    private double price;
    private int quantity;

    public Item(String code, String name, double price, int quantity) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Accessors and Mutators
    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void decrementQuantity() {
        if (this.quantity > 0) this.quantity--;
    }

    @Override
    public String toString() {
        return String.format("Item[Code=%s, Name=%s, Price=$%.2f, Qty=%d]", code, name, price, quantity);
    }
}

// Controller Implementation
public class Module4_OOPAndExceptions implements IVendingMachine {
    private static int totalTransactions = 0; // Static member
    private Item currentSelectedItem;
    private double currentBalance;

    public Module4_OOPAndExceptions() { // Default Constructor
        this.currentSelectedItem = null;
        this.currentBalance = 0.0;
    }

    @Override
    public void selectItem(String code) throws OutOfStockException {
        // Simulated inventory lookup
        Item sampleItem = new Item("A1", "Protein Bar", 2.50, 0); // Quantity 0 to test exception
        if (sampleItem.getQuantity() <= 0) {
            throw new OutOfStockException(sampleItem.getName());
        }
        this.currentSelectedItem = sampleItem;
    }

    @Override
    public void insertCoin(double amount) {
        this.currentBalance += amount;
        System.out.printf("Inserted: $%.2f | Total Balance: $%.2f%n", amount, this.currentBalance);
    }

    @Override
    public double dispenseItem() throws InsufficientFundsException {
        if (currentSelectedItem == null) {
            throw new IllegalStateException("No item selected.");
        }
        if (currentBalance < currentSelectedItem.getPrice()) {
            throw new InsufficientFundsException(currentSelectedItem.getPrice(), currentBalance);
        }
        
        double change = currentBalance - currentSelectedItem.getPrice();
        currentSelectedItem.decrementQuantity();
        totalTransactions++; // Static increment
        this.currentBalance = 0.0;
        return change;
    }

    public static int getTotalTransactions() { // Static method
        return totalTransactions;
    }

    public static void main(String[] args) {
        Module4_OOPAndExceptions machine = new Module4_OOPAndExceptions();

        System.out.println("=== OOP & EXCEPTION HANDLING TEST RUN ===");

        // Test 1: Out of Stock Exception
        try {
            System.out.println("Attempting item selection (A1)...");
            machine.selectItem("A1");
        } catch (OutOfStockException e) {
            System.err.println("[CAUGHT EXCEPTION] " + e.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK] Selection phase finished.\n");
        }

        // Test 2: Insufficient Funds Exception
        try {
            Item mineralWater = new Item("B2", "Mineral Water", 1.25, 5);
            machine.currentSelectedItem = mineralWater;
            machine.insertCoin(0.50); // Inserting less than $1.25
            
            System.out.println("Attempting item dispense...");
            double change = machine.dispenseItem();
            System.out.println("Dispensed successfully. Change: $" + change);
        } catch (InsufficientFundsException e) {
            System.err.println("[CAUGHT EXCEPTION] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[CAUGHT GENERAL EXCEPTION] " + e.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK] Dispense attempt completed.");
        }

        System.out.println("Total Global Machine Transactions: " + Module4_OOPAndExceptions.getTotalTransactions());
    }
}