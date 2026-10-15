import java.util.Scanner;

public class Module2_FSMControlFlow {
    // Enum representing the Finite State Machine states[cite: 1]
    enum State {
        IDLE, SELECT_ITEM, COIN_ACCUMULATE, VALIDATE_FUNDS, DISPENSE_ITEM, CHANGE_RETURN, FAULT_HANDLING
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        State currentState = State.IDLE; //[cite: 1]
        
        // Machine state variables
        int selectedItem = -1;
        double itemPrice = 0.0;
        double currentBalance = 0.0;
        int stockCount = 3; // Item stock
        boolean systemRunning = true;

        System.out.println("=== ADVANCED FSM VENDING MACHINE CONTROLLER ===");

        // Main FSM Execution Loop
        while (systemRunning) {
            switch (currentState) {
                case IDLE: //[cite: 1]
                    System.out.println("\n[STATE: IDLE] Active Output: display_ready, led_idle"); //[cite: 1]
                    System.out.println("1. Select Item Code (101 - Soda, $1.50)");
                    System.out.println("2. Simulate Fault (Jam Signal)");
                    System.out.println("3. Shutdown Controller");
                    System.out.print("Enter choice: ");
                    int choice = scanner.nextInt();

                    if (choice == 1) {
                        currentState = State.SELECT_ITEM; //[cite: 1]
                    } else if (choice == 2) {
                        currentState = State.FAULT_HANDLING; //[cite: 1]
                    } else if (choice == 3) {
                        systemRunning = false;
                        System.out.println("Shutting down controller...");
                    } else {
                        System.out.println("Invalid choice. Remaining in IDLE.");
                    }
                    break;

                case SELECT_ITEM: //[cite: 1]
                    System.out.println("\n[STATE: SELECT_ITEM] Active Output: check_stock, display_price"); //[cite: 1]
                    System.out.print("Enter Item Code (101): ");
                    selectedItem = scanner.nextInt();

                    // Nested-if & ladder
                    if (selectedItem == 101) {
                        if (stockCount > 0) {
                            itemPrice = 1.50;
                            System.out.printf("Item 101 selected. Price: $%.2f. In Stock: %d%n", itemPrice, stockCount);
                            currentState = State.COIN_ACCUMULATE; //[cite: 1]
                        } else {
                            System.out.println("Error: Item 101 Out of Stock!");
                            currentState = State.FAULT_HANDLING; //[cite: 1]
                        }
                    } else {
                        System.out.println("Unknown item code.");
                        currentState = State.IDLE; //[cite: 1]
                    }
                    break;

                case COIN_ACCUMULATE: //[cite: 1]
                    System.out.println("\n[STATE: COIN_ACCUMULATE] Active Output: display_balance, enable_validator"); //[cite: 1]
                    do {
                        System.out.printf("Current Balance: $%.2f / Required: $%.2f%n", currentBalance, itemPrice);
                        System.out.print("Insert Coin/Bill ($0.05, $0.10, $0.25, $1.00, $5.00) or 0 to finish: $"); //[cite: 1]
                        double coin = scanner.nextDouble();

                        if (coin == 0) break;

                        // Validating inputs using switch
                        int coinCents = (int) Math.round(coin * 100);
                        switch (coinCents) {
                            case 5: case 10: case 25: case 100: case 500: //[cite: 1]
                                currentBalance += coin;
                                break;
                            default:
                                System.out.println("Invalid coin rejected by sensor!");
                                continue; // Skip to next iteration
                        }
                    } while (currentBalance < itemPrice);

                    currentState = State.VALIDATE_FUNDS; //[cite: 1]
                    break;

                case VALIDATE_FUNDS: //[cite: 1]
                    System.out.println("\n[STATE: VALIDATE_FUNDS] Active Output: comparator_enable"); //[cite: 1]
                    if (currentBalance >= itemPrice) {
                        System.out.println("Validation Passed: Sufficient balance verified.");
                        currentState = State.DISPENSE_ITEM; //[cite: 1]
                    } else {
                        System.out.println("Validation Failed: Insufficient balance inserted.");
                        currentState = State.CHANGE_RETURN; // Refund[cite: 1]
                    }
                    break;

                case DISPENSE_ITEM: //[cite: 1]
                    System.out.println("\n[STATE: DISPENSE_ITEM] Active Output: motor_drive_en, inv_decrement"); //[cite: 1]
                    // Loop simulating motor pulse cycles
                    for (int pulse = 1; pulse <= 3; pulse++) {
                        System.out.printf("Actuating dispenser motor... Pulse %d/3%n", pulse);
                    }
                    stockCount--;
                    currentBalance -= itemPrice;
                    System.out.println("Item successfully dispensed!");
                    currentState = State.CHANGE_RETURN; //[cite: 1]
                    break;

                case CHANGE_RETURN: //[cite: 1]
                    System.out.println("\n[STATE: CHANGE_RETURN] Active Output: coin_dispense_pulse, display_change"); //[cite: 1]
                    if (currentBalance > 0) {
                        System.out.printf("Dispensing change amount: $%.2f%n", currentBalance);
                        currentBalance = 0.0;
                    } else {
                        System.out.println("No change due.");
                    }
                    currentState = State.IDLE; // Reset to IDLE[cite: 1]
                    break;

                case FAULT_HANDLING: //[cite: 1]
                    System.out.println("\n[STATE: FAULT_HANDLING] Active Output: alarm_out, display_error"); //[cite: 1]
                    System.out.println("Clearing mechanical jam/error status... System reset completed.");
                    currentState = State.IDLE; //[cite: 1]
                    break;
            }
        }
        scanner.close();
    }
}