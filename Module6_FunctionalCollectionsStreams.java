import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

class Transaction {
    private final String transactionId;
    private final String itemCategory;
    private final double amount;

    public Transaction(String transactionId, String itemCategory, double amount) {
        this.transactionId = transactionId;
        this.itemCategory = itemCategory;
        this.amount = amount;
    }

    public String getTransactionId() { return transactionId; }
    public String getItemCategory() { return itemCategory; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("Txn[%s, %s, $%.2f]", transactionId, itemCategory, amount);
    }
}

public class Module6_FunctionalCollectionsStreams {

    public static void main(String[] args) {
        System.out.println("=== MODULE 6: COLLECTIONS & STREAMS PROCESSING ===");

        // 1. Collections Framework Usage
        List<Transaction> transactions = new ArrayList<>(); // Generics & Typed List
        transactions.add(new Transaction("TX100", "Beverage", 1.50));
        transactions.add(new Transaction("TX101", "Snack", 2.00));
        transactions.add(new Transaction("TX102", "Beverage", 1.50));
        transactions.add(new Transaction("TX103", "Candy", 0.75));
        transactions.add(new Transaction("TX104", "Snack", 2.50));

        Set<String> uniqueCategories = new HashSet<>();
        Map<String, Double> itemPriceMap = new HashMap<>(); // Autoboxing double -> Double
        itemPriceMap.put("Soda", 1.50);
        itemPriceMap.put("Chips", 2.00);

        // 2. Functional Interfaces & Lambdas
        Predicate<Transaction> highValueTxn = txn -> txn.getAmount() >= 1.50; // Predicate
        Function<Transaction, String> categoryMapper = Transaction::getItemCategory; // Function & Method Ref
        Consumer<Transaction> logger = txn -> System.out.println("LOG: " + txn); // Consumer
        Supplier<String> statusSupplier = () -> "STREAM_PROCESSING_ACTIVE"; // Supplier

        System.out.println("Status: " + statusSupplier.get());

        // 3. Stream Pipeline Execution
        System.out.println("\n--- Filtered High Value Transactions (>= $1.50) ---");
        List<Transaction> filteredTxns = transactions.stream()
                .filter(highValueTxn) // Intermediate
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()) // Intermediate
                .collect(Collectors.toList()); // Terminal

        filteredTxns.forEach(logger); // Method iteration via Consumer

        // Stream Reduction: Sum of all sales
        double totalRevenue = transactions.stream()
                .map(Transaction::getAmount) // Map to Double
                .reduce(0.0, Double::sum); // Terminal Reduction
        System.out.printf("%nTotal Vending Sales Revenue: $%.2f%n", totalRevenue);

        // Stream Collectors: Grouping By Category & Counting
        System.out.println("\n--- Grouping Transactions by Category ---");
        Map<String, Long> categoryCount = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getItemCategory, Collectors.counting()));

        categoryCount.forEach((cat, count) -> 
            System.out.printf("Category: %-10s | Total Count: %d%n", cat, count)
        );

        // Stream Collector: Joining Names into Summary Report
        String transactionSummary = transactions.stream()
                .map(Transaction::getTransactionId)
                .distinct()
                .limit(4)
                .collect(Collectors.joining(" -> ", "BATCH_START: ", " :BATCH_END"));

        System.out.println("\nTransaction Batch Summary Stream:");
        System.out.println(transactionSummary);
    }
}