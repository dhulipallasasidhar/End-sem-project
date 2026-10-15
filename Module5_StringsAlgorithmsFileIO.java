import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class MachineItem implements Comparable<MachineItem> {
    private final String id;
    private final String name;
    private final double price;

    public MachineItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Comparable implementation (Natural order by ID)
    @Override
    public int compareTo(MachineItem other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%.2f", id, name, price);
    }
}

public class Module5_StringsAlgorithmsFileIO {

    public static void main(String[] args) {
        System.out.println("=== MODULE 5: STRINGS, SORTING & NIO FILE I/O ===");

        // 1. String Manipulation & StringBuilder
        StringBuilder transactionLog = new StringBuilder();
        transactionLog.append("TXN_1001").append(";").append("ITEM_SODA").append(";").append("STATUS_SUCCESS");
        String rawLog = transactionLog.toString();
        
        System.out.println("Raw Log String: " + rawLog);
        String[] tokens = rawLog.split(";"); // String split
        System.out.println("Parsed Token [1]: " + tokens[1].trim().toLowerCase()); // Trim & Case conversion

        // String comparison equals() vs ==
        String s1 = new String("SODA");
        String s2 = new String("SODA");
        System.out.println("s1 == s2 : " + (s1 == s2) + " (Reference Comparison)");
        System.out.println("s1.equals(s2) : " + s1.equals(s2) + " (Value Comparison)");

        // 2. Library Algorithms & Custom Comparator
        List<MachineItem> itemList = new ArrayList<>();
        itemList.add(new MachineItem("103", "Chips", 1.25));
        itemList.add(new MachineItem("101", "Soda", 1.50));
        itemList.add(new MachineItem("102", "Candy Bar", 1.00));

        // Natural Order Sorting (Comparable)
        Collections.sort(itemList);
        System.out.println("\nSorted by Natural Order (ID): " + itemList);

        // Comparator Sorting (By Price)
        itemList.sort(Comparator.comparingDouble(MachineItem::getPrice));
        System.out.println("Sorted by Price (Comparator): " + itemList);

        // Binary Search on Array
        int[] itemCodes = {101, 102, 103, 104, 105};
        int target = 103;
        int foundIdx = Arrays.binarySearch(itemCodes, target);
        System.out.printf("Binary Search for item code %d found at index: %d%n", target, foundIdx);

        // 3. Modern File I/O using java.nio.file APIs & Try-with-Resources
        Path logFilePath = Paths.get("vending_transactions.txt");

        // Writing to file
        try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("101,Soda,1.50\n");
            writer.write("102,Candy Bar,1.00\n");
            writer.write("103,Chips,1.25\n");
            System.out.println("\nSuccessfully written transaction record logs to: " + logFilePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("File Write Error: " + e.getMessage());
        }

        // Reading line-by-line using BufferedReader
        System.out.println("\nReading Log File Line-by-Line:");
        try (BufferedReader reader = Files.newBufferedReader(logFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                System.out.printf("Parsed Record -> ID: %s | Item: %s | Cost: $%s%n", parts[0], parts[1], parts[2]);
            }
        } catch (IOException e) {
            System.err.println("File Read Error: " + e.getMessage());
        }
    }
}