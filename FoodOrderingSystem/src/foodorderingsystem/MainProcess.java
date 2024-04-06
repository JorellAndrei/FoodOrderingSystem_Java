package foodorderingsystem;

import static foodorderingsystem.FoodOrderingSystem.GREEN;
import static foodorderingsystem.FoodOrderingSystem.RESET;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class MainProcess {
    private List<MenuItem> menu;
    private List<User> users;
    private int orderCounter;
    private int userOrderCounter;
    private boolean orderDeletedSuccessfully; 
     
     // Define the file name for storing the order counter
    private static final String ORDER_COUNTER_FILE = "order_counter.txt";
    private static final int MODIFY_OPTION_CHANGE_ORDER = 1;
    private static final int MODIFY_OPTION_CHANGE_QUANTITY = 2;
    private static final int MODIFY_OPTION_DONE = 3;
    private static final int MODIFY_OPTION_CANCEL = 4;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public MainProcess() {
        this.menu = new ArrayList<>();
        this.users = new ArrayList<>();
        this.orderCounter = 1;
        this.userOrderCounter = 1;
        this.orderCounter = loadOrderCounter();
        initializeMenu();  // Add predefined menu items
         this.orderDeletedSuccessfully = false; 
    }
    
    // Method to load the order counter from a file
    private int loadOrderCounter() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_COUNTER_FILE))) {
            // Read the order counter from the file
            return Integer.parseInt(reader.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            // If an exception occurs, return the default value (1)
            return 1;
        }
    }
    
    // Method to save the order counter to a file
    private void saveOrderCounter() {
        try (FileWriter writer = new FileWriter(ORDER_COUNTER_FILE)) {
            // Write the current order counter to the file
            writer.write(Integer.toString(orderCounter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMenu() {
    menu.add(new MenuItem(1, "[1.] Seafood Platter (good for 2)", "Appetizers", 5688));
    menu.add(new MenuItem(2, "[2.] Sturia Oscietra Caviar (France)", "Appetizers", 8288));
    menu.add(new MenuItem(3, "[3.] Jumbo Shrimp Cocktail", "Appetizers", 1288));
    menu.add(new MenuItem(4, "[4.] Jumbo Lump Crabmeat Cocktail", "Appetizers", 1488));
    menu.add(new MenuItem(5, "[5.] Lobster Cocktail", "Appetizers", 4298));
    menu.add(new MenuItem(6, "[6.] Soup Of The Day", "Soup & Salads", 468));
    menu.add(new MenuItem(7, "[7.] French Onion Soup", "Soup & Salads", 888));
    menu.add(new MenuItem(8, "[8.] Mixed Green Salad", "Soup & Salads", 428));
    menu.add(new MenuItem(9, "[9.] Caesar Salad", "Soup & Salads", 598));
    menu.add(new MenuItem(10, "[10.] Beverly Hills Chopped Salad", "Soup & Salads", 798));
    menu.add(new MenuItem(11, "[11.] Porterhouse for two (1kg)", "Main Courses", 4288));
    menu.add(new MenuItem(12, "[12.] Prime NY Sirloin Steak (600g)", "Main Courses", 5688));
    menu.add(new MenuItem(13, "[13.] Rib Eye Steak (900g)", "Main Courses", 8988));
    menu.add(new MenuItem(14, "[14.] Fillet Mignon (450g)", "Main Courses", 4288));
    menu.add(new MenuItem(15, "[15.] Lamb Chops", "Main Courses", 6588));
    menu.add(new MenuItem(16, "[16.] Cajun Shrimp with Penne Pasta", "Pasta and Noodles", 2988));
    menu.add(new MenuItem(17, "[17.] Rotelli Pasta with Chicken", "Pasta and Noodles", 2755));
    menu.add(new MenuItem(18, "[18.] Spaghetti Fruttie di Mare", "Pasta and Noodles", 1897));
    menu.add(new MenuItem(19, "[19.] Rotelli with Meat Sauce Bolognese", "Pasta and Noodles", 1945));
    menu.add(new MenuItem(20, "[20.] Spinach and Cheese Ravioli", "Pasta and Noodles", 1787));
    menu.add(new MenuItem(21, "[21.] Mango-Prosecco Tiramisu", "Desserts", 675));
    menu.add(new MenuItem(22, "[22.] Mille-Feuille Ispahan", "Desserts", 780));
    menu.add(new MenuItem(23, "[23.] Chocolate Souffle", "Desserts", 600));
    menu.add(new MenuItem(24, "[24.] Young Coconut Jelly", "Desserts", 450));
    menu.add(new MenuItem(25, "[25.] Seasonal Fruit Platter", "Desserts", 550));
    menu.add(new MenuItem(26, "[26.] Homemade Lychee Iced Tea", "Non-Alcoholic Beverages", 220));
    menu.add(new MenuItem(27, "[27.] Homemade Lemonade", "Non-Alcoholic Beverages", 200));
    menu.add(new MenuItem(28, "[28.] Watermelon Smoothie", "Non-Alcoholic Beverages", 300));
    menu.add(new MenuItem(29, "[29.] Squeezed Orange Juice", "Non-Alcoholic Beverages", 280));
    menu.add(new MenuItem(30, "[30.] Cranberry Chilled Juice", "Non-Alcoholic Beverages", 180));
    menu.add(new MenuItem(31, "[31.] Coke", "Non-Alcoholic Beverages", 190));
    menu.add(new MenuItem(32, "[32.] Coke Zero", "Non-Alcoholic Beverages", 190));
    menu.add(new MenuItem(33, "[33.] Sprite", "Non-Alcoholic Beverages", 190));
    menu.add(new MenuItem(34, "[34.] Royal", "Non-Alcoholic Beverages", 190));
    menu.add(new MenuItem(35, "[35.] Flat White", "Non-Alcoholic Beverages", 220));
    menu.add(new MenuItem(36, "[36.] Cafe Latte", "Non-Alcoholic Beverages", 250));
    menu.add(new MenuItem(37, "[37.] Ristretto", "Non-Alcoholic Beverages", 180));
    menu.add(new MenuItem(38, "[38.] Americano", "Non-Alcoholic Beverages", 220));
    menu.add(new MenuItem(39, "[39.] Mocha", "Non-Alcoholic Beverages", 280));
    menu.add(new MenuItem(40, "[40.] Aqua Panna (750ml) still", "Non-Alcoholic Beverages", 300));
    menu.add(new MenuItem(41, "[41.] San Pellegrino (750ml) sparkling", "Non-Alcoholic Beverages", 300));
    menu.add(new MenuItem(42, "[42.] Johnnie Walker Blue Label (45ml)", "Alcoholic Beverages", 1500));
    menu.add(new MenuItem(43, "[43.] Marker's Mark (45ml)", "Alcoholic Beverages", 380));
    menu.add(new MenuItem(44, "[44.] Jack Daniels Single Barrel (45ml)", "Alcoholic Beverages", 620));
    menu.add(new MenuItem(45, "[45.] Woodford Reserve (45ml)", "Alcoholic Beverages", 700));
    menu.add(new MenuItem(46, "[46.] Buhsmills Original (45ml)", "Alcoholic Beverages", 400));
    menu.add(new MenuItem(47, "[47.] Ketel One", "Alcoholic Beverages", 380));
    menu.add(new MenuItem(48, "[48.] Belvedere", "Alcoholic Beverages", 450));
    menu.add(new MenuItem(49, "[49.] Grey Goose", "Alcoholic Beverages", 450));
    menu.add(new MenuItem(50, "[50.] Tito's", "Alcoholic Beverages", 380));
    menu.add(new MenuItem(51, "[51.] Bacardi Superior (45ml)", "Alcoholic Beverages", 380));
    menu.add(new MenuItem(52, "[52.] Plantation 3 Stars (45ml)", "Alcoholic Beverages", 350));
    menu.add(new MenuItem(53, "[53.] Don Papa Rye (45ml)", "Alcoholic Beverages", 500));
    menu.add(new MenuItem(54, "[54.] Don Papa Port Cask (45ml)", "Alcoholic Beverages", 550));
    menu.add(new MenuItem(55, "[55.] Diplomatico Reserva Exclusiva (45ml)", "Alcoholic Beverages", 450));
    menu.add(new MenuItem(56, "[56.] Patron XO", "Alcoholic Beverages", 420));
    menu.add(new MenuItem(57, "[57.] Patron Silver", "Alcoholic Beverages", 600));
    menu.add(new MenuItem(58, "[58.] Volcan Blanco", "Alcoholic Beverages", 480));
    menu.add(new MenuItem(59, "[59.] Volcan Anejo Cristalino", "Alcoholic Beverages", 760));
    menu.add(new MenuItem(60, "[60.] Clase Azul Plata", "Alcoholic Beverages", 1700));

    }

    public void displayMenu() {
        System.out.println("\n" + YELLOW + "Food Menu:" + RESET);

        // Display each category separately
        displayCategory("Appetizers");
        displayCategory("Soup & Salads");
        displayCategory("Main Courses");
        displayCategory("Pasta and Noodles");
        displayCategory("Desserts");
        displayCategory("Non-Alcoholic Beverages");
        displayCategory("Alcoholic Beverages");
    }

    private void displayCategory(String category) {
        System.out.println("\n"+ YELLOW + "  " + category + ":" + RESET);
        for (MenuItem menuItem : menu) {
            if (menuItem.getCategory().equals(category)) {
                System.out.println("\t" + menuItem.getName() + " - P" + menuItem.getPrice());
            }
        }
    }


    public User signUp(int accountId, String name, int age, String homeAdd, String emailAdd) {
        User newUser = new User(accountId, name, age, homeAdd, emailAdd);
        users.add(newUser);
        return newUser;
    }

    public User logIn(int accountId) throws NoSuchElementException {
        return users.stream()
                .filter(user -> String.valueOf(user.getAccountId()).equals(String.valueOf(accountId)))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    // ----------------------------------------------------------------------
    
    public void placeOrder(User user, List<Integer> itemIndices, Voucher voucher) {
        double totalPrice = 0;
        List<String> orderDetailsList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("\nOrder:");

        for (int index : itemIndices) {
            try {
                MenuItem menuItem = menu.get(index - 1);

                // Prompt the user to enter the quantity
                while (true) {
                    System.out.print("| Enter quantity for " + CYAN + menuItem.getName() + RESET + ": ");

                    if (sc.hasNextInt()) {
                        int quantity = sc.nextInt();
                        sc.nextLine();  // Consume the newline character

                        if (quantity <= 0) {
                            System.err.println("\n\t**** Invalid quantity. Please enter a positive quantity.\n");
                        } else {
                            double itemPrice = menuItem.getPrice() * quantity;
                            totalPrice += itemPrice;

                            // Add item details to the orderDetailsList
                            String itemDetails = menuItem.getName() + " | x" + quantity + "| - P" + itemPrice;
                            orderDetailsList.add(itemDetails);
                            System.out.println(itemDetails);

                            break;  // Exit the loop if a valid quantity is entered
                        }
                    } else {
                        System.err.println("\n\t**** Invalid input. Please enter a valid quantity. ****\n");
                        sc.nextLine();  // Consume the invalid input
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("\t**** Invalid menu item index: " + index);
            }
        }

        // Display the complete receipt
        System.out.println("\n======================================================================");
        System.out.println("" + YELLOW + "Complete Receipt:" + RESET);
        for (String itemDetails : orderDetailsList) {
            System.out.println(itemDetails);
        }

        double discountAmount = applyVoucherDiscount(totalPrice, voucher);
        totalPrice -= discountAmount;

        displayDiscountAppliedMessage(discountAmount);

        System.out.println(YELLOW + "  Total:" + RESET + " P" + totalPrice);
        System.out.println("\n======================================================================");


        user.addToOrderHistory(getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));
        //saveOrderHistoryToFile(user.getAccountId(), getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));

       // Display order details
        int orderAction;

    do {
        orderAction = displayOrderOptions(sc);


        switch (orderAction) {
            case 1:
                displayOrderReceipt(orderCounter, orderDetailsList, discountAmount, totalPrice);
                // Save the order to order history only when the user chooses "Done Ordering"
                user.addToOrderHistory(getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));
                saveOrderHistoryToFile(user.getAccountId(), getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));
                user.incrementUserOrderCounter(); 
                orderCounter=1;        
                saveOrderCounter();
                System.out.println("\n\t" + GREEN + "Order placed successfully!" + RESET);

                break;

            case 2:
                boolean doneModifyingOrder = modifyOrder(user, orderDetailsList, discountAmount, totalPrice, sc, voucher);

                if (doneModifyingOrder) {
                    // Save the order to order history only when the user chooses "Done Ordering"
                    user.addToOrderHistory(getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));
                    saveOrderHistoryToFile(user.getAccountId(), getOrderDetailsString(orderCounter, orderDetailsList, discountAmount, totalPrice));
                    user.incrementUserOrderCounter();
                    orderCounter = 1;
                    saveOrderCounter();
                    System.out.println("\n\t" + GREEN + "Order placed successfully!" + RESET);

                } else {
                    // Handle the case where the user cancels the modification
                    System.err.println("\n\t**** Order modification canceled. ****");
                }
                break;

            case 3:
                boolean deleteConfirmed = confirmOrderDeletion(sc);
                if (deleteConfirmed) {
                    orderDeletedSuccessfully = true;
                    System.out.println("\n\t" + GREEN + "Order cancelled successfully!" + RESET);
                } else {
                    orderDeletedSuccessfully = false;
                    System.err.println("\n\t**** Order cancellation stopped. ****");

                    // Ask the user if they want to return to the order options menu
                    System.out.print("\nDo you want to return to the order options menu? (Y/N): ");
                    String returnToMenu = sc.next().trim();
                    if (returnToMenu.equalsIgnoreCase("Y")) {
                        displayOrderOptions(sc);
                    }
                }
                break;

            default:
                System.err.println("\n\t**** Invalid choice. Please choose a valid option. ****");
        }
    } while (orderAction < 1 || orderAction > 3);
        if (!orderDeletedSuccessfully && orderAction == 1) {
            // Optionally, you can save the receipt to the user's order history
            // Add this line to save the order history to a file
            saveOrderCounter();
        }
    }
    
    //MODIFY ORDER ------------------------------------------
    
    private boolean modifyOrder(User user, List<String> orderDetailsList, double discountAmount, double totalPrice, Scanner sc, Voucher voucher) {
        int modifyOption;

        do {
            modifyOption = displayModifyOrderMenu(sc);

            switch (modifyOption) {
                case MODIFY_OPTION_CHANGE_ORDER:
                    changeOrder(user, orderDetailsList, sc, voucher);
                    
                    break;

                case MODIFY_OPTION_CHANGE_QUANTITY:
                    changeQuantity(user, orderDetailsList, sc, voucher);
                    break;

                case MODIFY_OPTION_DONE:
                    return true;

                case MODIFY_OPTION_CANCEL:
                    return false;

                default:
                    System.err.println("\n\t**** Invalid option. Please enter a valid choice. ****");
                    break;
            }

        } while (true);
    }
    
    private void displayReceipt(User user, List<String> orderDetailsList, Voucher voucher) {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Updated Receipt for Mr./Ms." + user.getName() + ":");

        double totalAmount = 0.0;

        for (String orderDetails : orderDetailsList) {
            String[] parts = orderDetails.split(" - \\$");
            String itemName = parts[0];
            double itemSubtotal = Double.parseDouble(parts[1].substring(1)); // Skip the leading space
            System.out.println("  " + itemName + " - P" + itemSubtotal);
            totalAmount += itemSubtotal;
        }

        double discountAmount = applyVoucherDiscount(totalAmount, voucher);
        totalAmount -= discountAmount;
        displayDiscountAppliedMessage(discountAmount);
        System.out.println("  " + YELLOW + "Total:" + RESET + " P" + totalAmount);
        System.out.println("---------------------------------------------------------");
    }


   private void changeOrder(User user, List<String> orderDetailsList, Scanner sc, Voucher voucher) {
        while (true) {
            System.out.println("\n | Enter the item number to change the order: ");
            int itemNumber = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            String existingOrderDetails = getOrderDetailsByItemNumber(orderDetailsList, itemNumber);

            if (existingOrderDetails != null) {
                System.out.println("\n | Enter the new item number for " + CYAN + existingOrderDetails + RESET + ": ");
                int newItemNumber = sc.nextInt();
                sc.nextLine();  // Consume the newline character

                // Check if the new item is already in the order
                if (getOrderDetailsByItemNumber(orderDetailsList, newItemNumber) != null) {
                    System.err.println("**** Item is already in the order. Please choose a different item. ****");
                    continue;  // Go back to the beginning of the loop
                }

                System.out.println(" | Enter the quantity for new food order: ");
                int newQuantity = sc.nextInt();
                sc.nextLine();  // Consume the newline character

                MenuItem newMenuItem = findMenuItemByNumber(newItemNumber);

                if (newMenuItem != null) {
                    double newSubtotal = newMenuItem.getPrice() * newQuantity;
                    String newItemDetails = newMenuItem.getName() + " x" + newQuantity + " - $" + newSubtotal;

                    // Update the order details list with the new item
                    orderDetailsList.remove(existingOrderDetails);
                    orderDetailsList.add(newItemDetails);

                    System.out.println("\n\t" + GREEN + "Order changed successfully." + RESET);

                    // Display the updated receipt
                    displayReceipt(user, orderDetailsList, voucher);
                    break;  // Exit the loop if the order is successfully changed
                } else {
                    System.err.println("\n\t**** Invalid item number. Order unchanged. ****");
                    break;  // Exit the loop if the item number is invalid
                }
            } else {
                System.err.println("\n\t**** Item not found in the order. Please enter a valid item number. ****");
                break;  // Exit the loop if the item is not found
            }
        }
    }

    private int getOrderQuantity(String orderDetails) {
        // Use regular expression to extract the quantity
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("x(\\d+)").matcher(orderDetails);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Quantity not found in order details: " + orderDetails);
        }
}

    
    private void changeQuantity(User user, List<String> orderDetailsList, Scanner sc, Voucher voucher) {
        System.out.println("Enter the item number to change the quantity: ");
        int itemNumber = sc.nextInt();
        sc.nextLine();  // Consume the newline character

        String existingOrderDetails = getOrderDetailsByItemNumber(orderDetailsList, itemNumber);

        if (existingOrderDetails != null) {
            System.out.println("Enter the new quantity for " + existingOrderDetails + ": ");
            int newQuantity = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            if (newQuantity <= 0) {
                System.err.println("\t**** Invalid quantity. Quantity unchanged. ****");
            } else {
                // Update the order details list with the new quantity
                orderDetailsList.remove(existingOrderDetails);
                MenuItem menuItem = findMenuItemByNumber(itemNumber);
                double newSubtotal = menuItem.getPrice() * newQuantity;
                String newItemDetails = menuItem.getName() + " x" + newQuantity + " - $" + newSubtotal;
                orderDetailsList.add(newItemDetails);

                System.out.println("\n\t" + GREEN + "Quantity changed successfully." + RESET);
                displayReceipt(user, orderDetailsList, voucher);

                
            }
        } else {
            System.err.println("\n\t**** Item not found in the order. Please enter a valid item number. ****");
        }
    }
    
    
    private int displayModifyOrderMenu(Scanner sc) {
        int choice;

        while (true) {
            try {
                System.out.println("\n"+YELLOW+"Modify Order:"+RESET);
                System.out.println("  1. Change Order");
                System.out.println("  2. Change Quantity");
                System.out.println("  3. Done");
                System.out.println("  4. Cancel");
                System.out.print("\n| Enter your choice: ");

                // Check if the next token is an integer
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();

                    // Check if the choice is within the valid range
                    if (choice >= 1 && choice <= 4) {
                        break;  // Exit the loop if a valid integer is entered
                    } else {
                        System.err.println("\n\t**** Invalid choice. Please enter a number between 1 and 4. ****");
                        sc.nextLine();  // Consume the invalid input
                    }
                } else {
                    System.err.println("\n\t**** Invalid input. Please enter a valid integer choice. ****");
                    sc.nextLine();  // Consume the invalid input
                }
            } catch (InputMismatchException e) {
                System.err.println("\n\t**** Invalid input. Please enter a valid integer choice. ****");
                sc.nextLine();  // Consume the invalid input
            }
        }

        return choice;
    }

    
    private MenuItem findMenuItemByNumber(int itemNumber) {
        for (MenuItem menuItem : menu) {
            if (menuItem.getItemNumber() == itemNumber) {
                return menuItem;
            }
        }
        return null;
    }

    private String getOrderDetailsByItemNumber(List<String> orderDetailsList, int itemNumber) {
        for (String itemDetails : orderDetailsList) {
            if (Integer.parseInt(itemDetails.split("\\.")[0].substring(1)) == itemNumber) {
                return itemDetails;
            }
        }
        return null;
    }
    
    private boolean confirmOrderDeletion(Scanner sc) {
        System.out.println("\nAre you sure you want to cancel the current order? (Y/N): ");
        String confirmation = sc.next().trim();
        return confirmation.equalsIgnoreCase("Y");
    }

        private String getOrderDetailsString(int orderCounter, List<String> orderDetailsList, double discountAmount, double totalPrice) {
              StringBuilder orderDetails = new StringBuilder();

                orderDetails.append("Date and Time of order: ").append(getCurrentDateTime()).append("\n");
                orderDetails.append(String.format("%-20s%-10s%-10s%n", " Item/s", "", ""));

                for (String itemDetails : orderDetailsList) {
                    orderDetails.append(itemDetails).append("\n");
                }

                orderDetails.append("\n");
                orderDetails.append(String.format("%-20s%-10s$%s%n", "Total", "", totalPrice));
                orderDetails.append("\n");

                return orderDetails.toString();
        }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    private double applyVoucherDiscount(double totalPrice, Voucher voucher) {
        double discountAmount = 0;

        if (voucher != null) {
            if (totalPrice >= PercentageVoucher.DISCOUNT_TIER_4_AMOUNT) {
                discountAmount = applyDiscountAndDisplayMessage(voucher, 25, totalPrice);
            } else if (totalPrice >= PercentageVoucher.DISCOUNT_TIER_3_AMOUNT) {
                discountAmount = applyDiscountAndDisplayMessage(voucher, 15, totalPrice);
            } else if (totalPrice >= PercentageVoucher.DISCOUNT_TIER_2_AMOUNT) {
                discountAmount = applyDiscountAndDisplayMessage(voucher, 10, totalPrice);
            } else if (totalPrice >= PercentageVoucher.DISCOUNT_TIER_1_AMOUNT) {
                discountAmount = applyDiscountAndDisplayMessage(voucher, 5, totalPrice);
            } else {
                System.err.println("\n\tOrder does not meet the requirements for the voucher. Voucher not applied.");
            }
        }

        return discountAmount;
    }

    private double applyDiscountAndDisplayMessage(Voucher voucher, int percentage, double totalPrice) {
        double discountAmount = voucher.applyDiscount(totalPrice);
        totalPrice -= discountAmount;
        displayDiscountAppliedMessage(percentage, discountAmount);
        return discountAmount;
    }

    private void displayDiscountAppliedMessage(int percentage, double discountAmount) {
        System.out.println("\n" + YELLOW + "  Voucher Discount Applied (" + percentage + "%): " + RESET + "- P" + discountAmount);
    }

    private void displayDiscountAppliedMessage(double discountAmount) {
        //System.out.println("Voucher Discount Applied: -$" + discountAmount);
    }

    private int displayOrderOptions(Scanner sc) {
            int choice = 0;

       while (true) {
           try {
               System.out.println("\n1. Done Ordering");
               System.out.println("2. Modify Order");
               System.out.println("3. Cancel Order");
               System.out.print("\n | Enter your choice: ");

               choice = sc.nextInt();
               break;  // Exit the loop if a valid integer is entered
           } catch (InputMismatchException e) {
               System.err.println("\n\t**** Invalid input. Please enter a valid choice. ****");
               sc.nextLine();  // Consume the invalid inputb
           }
       }

        return choice;
    }

     private void displayOrderReceipt(int orderNumber, List<String> orderDetailsList, double discountAmount, double totalPrice) {
        System.out.println("Order placed successfully!");

        // Display receipt
        System.out.println("\nOrder Receipt:");
        System.out.println("---------------");
        //System.out.println("Order #" + orderNumber);

        for (String itemDetails : orderDetailsList) {
            System.out.println(itemDetails);
        }

        displayDiscountAppliedMessage(discountAmount);

        System.out.println("Total: P" + totalPrice);
    }

    
    public void displayOrderHistory(User user) {
        List<String> orderHistory = readOrderHistoryFromFile(user.getAccountId());

        if (orderHistory.isEmpty()) {
            System.out.println("\n\t"+RED+"No orders placed yet."+RESET);
        } else {
            System.out.println("\n|---------------------------------------------------|");
            System.out.println("\n"+YELLOW+"Order History:"+RESET+"\n");
            for (String orderDetails : orderHistory) {
                System.out.println(orderDetails);
            }
            System.out.println("|---------------------------------------------------|");
        }
    }

    
    private List<String> readOrderHistoryFromFile(int accountId) {
        List<String> orderHistory = new ArrayList<>();
        File file = new File("order_history_" + accountId + ".txt");

        try {
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder orderDetails = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                            orderHistory.add(orderDetails.toString());
                            orderDetails = new StringBuilder();
                        } else {
                            orderDetails.append(line).append("\n");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderHistory;
    }


    private void saveOrderHistoryToFile(int accountId, String orderDetails) {
        try (FileWriter writer = new FileWriter("order_history_" + accountId + ".txt", true)) {
            writer.write(orderDetails + "\n\n");  // Append two newline characters between orders
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
