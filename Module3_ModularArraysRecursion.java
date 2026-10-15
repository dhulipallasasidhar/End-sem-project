public class Module3_ModularArraysRecursion {

    // 1D Array Denominations in Cents ($5.00, $1.00, $0.25, $0.10, $0.05)[cite: 1]
    private static final int[] DENOMINATIONS = {500, 100, 25, 10, 5};

    public static void main(String[] args) {
        System.out.println("=== MODULAR VENDING ENGINE & RECURSIVE CHANGE RETURN ===");

        // 1D Array operations: Traversal, Summation, Search
        double[] salesData = {1.50, 2.00, 1.25, 3.50, 2.50};
        System.out.printf("Total Sales Sum : $%.2f%n", calculateTotalSales(salesData));
        System.out.printf("Average Transaction: $%.2f%n", calculateAverageSales(salesData));
        
        int searchIdx = searchSale(salesData, 3.50);
        System.out.printf("Search $3.50 transaction index: %d%n", searchIdx);

        // Recursive Change Dispenser Call
        int changeInCents = 385; // $3.85 change
        System.out.printf("%nCalculating recursive change breakdown for %d cents ($%.2f):%n", 
                          changeInCents, changeInCents / 100.0);
        calculateChangeRecursive(changeInCents, 0);

        // 2D Array Matrix representation (Rack Grid: 3 Rows x 3 Cols Inventory Matrix)
        int[][] inventoryGrid = {
            {5, 8, 2},
            {0, 4, 9},
            {3, 6, 1}
        };

        System.out.println("\n--- Vending Inventory Grid (3x3 Matrix) ---");
        displayMatrix(inventoryGrid);
        
        // Matrix Arithmetic: Restock matrix addition
        int[][] restockMatrix = {
            {2, 2, 2},
            {5, 2, 1},
            {2, 2, 2}
        };
        
        int[][] updatedGrid = addInventoryMatrices(inventoryGrid, restockMatrix);
        System.out.println("\n--- Updated Inventory Grid Post-Restock ---");
        displayMatrix(updatedGrid);
    }

    // Method Definition & Overloading
    public static double calculateTotalSales(double[] sales) {
        double sum = 0;
        for (double sale : sales) { // Enhanced for-each loop
            sum += sale;
        }
        return sum;
    }

    public static double calculateAverageSales(double[] sales) {
        if (sales.length == 0) return 0.0;
        return calculateTotalSales(sales) / sales.length; // Method invocation
    }

    public static int searchSale(double[] sales, double target) {
        for (int i = 0; i < sales.length; i++) {
            if (sales[i] == target) return i;
        }
        return -1;
    }

    // Recursive Change Calculation (Greedy Coin-Return Algorithm)[cite: 1]
    public static void calculateChangeRecursive(int remainingCents, int denominationIndex) {
        // Base Cases
        if (remainingCents <= 0 || denominationIndex >= DENOMINATIONS.length) {
            return;
        }

        int coinValue = DENOMINATIONS[denominationIndex];
        int count = remainingCents / coinValue;
        int newRemaining = remainingCents % coinValue;

        if (count > 0) {
            System.out.printf("Dispense %d x coin/bill of %d cents ($%.2f)%n", 
                              count, coinValue, coinValue / 100.0);
        }

        // Recursive Step
        calculateChangeRecursive(newRemaining, denominationIndex + 1);
    }

    // 2D Matrix Arithmetic Functions
    public static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%3d ", val);
            }
            System.out.println();
        }
    }

    public static int[][] addInventoryMatrices(int[][] m1, int[][] m2) {
        int rows = m1.length;
        int cols = m1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return result;
    }
}