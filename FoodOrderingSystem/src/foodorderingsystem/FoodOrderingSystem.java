//programmed by JORELL ANDREI FINEZ

package foodorderingsystem;

import foodorderingsystem.MainProcess;
import foodorderingsystem.User;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FoodOrderingSystem {

    private static MainProcess mp = new MainProcess();
    private static User currentUser = null;
    private static Scanner sc = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static final String USER_FILE_PATH = "userAccounts.txt"; // Place the file in the project directory
    private static final String ORDER_HISTORY_FILE_PREFIX = "order_history_";
    
    public static void main(String[] args) {
        boolean menuBl = true;
        users = readUsersFromFile();

        while (menuBl) {
            try {
                displayMainMenu();
                char menuChoice = sc.next(".").charAt(0);
                sc.nextLine(); // Consume the newline character

                switch (menuChoice) {
                    case 'a', 'A':
                        signUp();
                        break;

                    case 'b', 'B':
                        logIn();
                        break;

                    case 'c', 'C':
                        System.out.print("\n"+PURPLE+"     SavoryTech "+RESET+"Food Ordering System is a Java-based solution designed to streamline the "
                                + "\nprocess of ordering food items. The system allows users to create orders by selecting various food items "
                                + "\navailable in the menu. Each food item is defined by its name and price. It calculates the total cost of the "
                                + "\norder automatically, saving users time and avoiding mistakes. What's cool is that users can customize their "
                                + "\norders by adding different food items, and the system takes care of all the math.\n");
                        System.out.print("\n     The class, FoodOrderingSystem, serves as the entry point for the application, where users can "
                                + "\ninteract with the system by creating orders and viewing the total cost of their selections. The MenuItem class "
                                + "\nrepresents a food item with attributes such as item number, name of the food, category, and price. The food in "
                                + "\nthe menu are from different menus of fine dining restaurants in the Philippines. The MainProcess class manages "
                                + "\na list of selected food items and provides methods to add items, calculate the total cost, and display the order"
                                + "\n details.\n");
                        System.out.print("\n     In addition to managing food items, SavoryTech Food Ordering System includes a Voucher class. This "
                                + "\nclass enables users to apply vouchers or discounts to their orders. And a PercentageVoucher class that enables"
                                + "\n users to apply percentage-based discounts to their orders. This class calculates the discount amount based on "
                                + "\nthe specified percentage and applies it to the order total.\n");

                        break;

                    case 'x', 'X':
                        System.out.println("\n\t" + GREEN + "---- Exiting program. Goodbye! ----" + RESET + "\n");
                        System.exit(0);
                        break;

                    default:
                        System.err.println("\n\t**** Invalid Choice. Try again, Thank you ****");
                }
            } catch (InputMismatchException e) {
                System.err.println("\n\t**** Oops! Input Mismatch. Try again, Thank you ****");
                sc.nextLine();
            }
        }
    } // end of Main

    private static void displayMainMenu() {
        System.out.println("\n\t\t---------------------");
        System.out.println("\t [ " + PURPLE + "  SavoryTech: Food Ordering System" + RESET + " ]");
        System.out.println("\t\t---------------------");
        System.out.println("\t[A]. SIGN UP ACCOUNT");
        System.out.println("\t[B]. LOGIN ACCOUNT");
        System.out.println("\t[C]. ABOUT");
        System.out.println("\t[X]. EXIT");
        System.out.print("\n| Enter your choice: ");
    }

    private static void signUp() {
        boolean signUpSuccess = false;
        while (!signUpSuccess) {
            try {
                System.out.println("\nEnter your account ID:");
                int accountId = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                // Check if the account ID already exists
                if (isAccountExists(accountId)) {
                    System.out.println("Account with ID " + accountId + " already exists. Please choose a different ID.");
                    continue;
                }

                System.out.println("\nEnter your name:");
                String name = sc.nextLine();

                System.out.println("\nEnter your age:");
                int age = 0;
                boolean validInput = false;

                while (!validInput) {
                    try {
                        age = Integer.parseInt(sc.nextLine());
                        validInput = true;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid input. Please enter a valid integer for age.");
                    }
                }

                System.out.println("\nEnter your home address:");
                String homeAdd = sc.nextLine();

                System.out.println("\nEnter your email address:");
                String emailAdd = sc.nextLine();

                currentUser = mp.signUp(accountId, name, age, homeAdd, emailAdd);

                // Save user information to the list
                users.add(currentUser);
                saveUsersToFile(users); // Save the updated list to the file

                System.out.println("\n\t" + GREEN + "---- You have successfully created an account! ----" + RESET);

                // Clear the console screen
                clearConsole();

                signUpSuccess = true; // Set the flag to exit the loop
            } catch (InputMismatchException e) {
                System.err.println("\n\t**** Invalid input. Please enter a valid number. ****");
                sc.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                System.err.println("\n\t**** Invalid input. Please try again. ****");
                sc.nextLine(); // Consume the invalid input
            }
        }
    }

    private static List<User> readUsersFromFile() {
        List<User> userList = new ArrayList<>();
        File file = new File(USER_FILE_PATH);

        try {
            if (!file.exists()) {
                // Create the file if it doesn't exist
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int accountId = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String homeAdd = parts[3].trim();
                    String emailAdd = parts[4].trim();

                    User user = new User(accountId, name, age, homeAdd, emailAdd);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    private static boolean isAccountExists(int accountId) {
        File file = new File(USER_FILE_PATH);

        try {
            if (!file.exists()) {
                // Create the file if it doesn't exist
                file.createNewFile();
                return false; // Account doesn't exist since the file was just created
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int existingAccountId = Integer.parseInt(parts[0].trim());
                    if (existingAccountId == accountId) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void saveUsersToFile(List<User> userList) {
        try (FileWriter writer = new FileWriter(USER_FILE_PATH, false)) {
            for (User user : userList) {
                String userInfo = user.getAccountId() + "," + user.getName() + "," + user.getAge() + ","
                        + user.getHomeAdd() + "," + user.getEmailAdd() + "\n";
                writer.write(userInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private static void removeUserFromFile(int accountIdToRemove) {
        users.removeIf(user -> user.getAccountId() == accountIdToRemove);
        File file = new File(USER_FILE_PATH);
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int existingAccountId = Integer.parseInt(parts[0].trim());
                if (existingAccountId != accountIdToRemove) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            handleIOException(e);
        }

        try (FileWriter writer = new FileWriter(USER_FILE_PATH, false)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            handleIOException(e);
        }
        
        // Delete order history file associated with the deleted account
        deleteOrderHistoryFile(accountIdToRemove);
    }

     private static void deleteOrderHistoryFile(int accountId) {
        File orderHistoryFile = new File(ORDER_HISTORY_FILE_PREFIX + accountId + ".txt");
        if (orderHistoryFile.exists()) {
            if (orderHistoryFile.delete()) {
                System.out.println("\n\t" + GREEN + "Order history file deleted successfully!" + RESET);
            } else {
                System.out.println("\n\t" + RED + "Failed to delete order history file!" + RESET);
            }
        }
    }
     
    private static void logIn() {
        System.out.println("\nEnter your account ID:");
        int loginId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        // Find the user in the list
        currentUser = users.stream()
                .filter(user -> user.getAccountId() == loginId)
                .findFirst()
                .orElse(null);

        if (currentUser != null) {
            displayOrderingSystemMenu();
        } else {
            System.err.println("\n\t**** User not found. Please sign up. ****");
        }
    }

    private static void clearConsole() {
        // Clear the console screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void displayOrderingSystemMenu() {
        boolean orderingSystemMenuBl = true;

        while (orderingSystemMenuBl) {
            try {
                System.out.println("\n\t\t    ---------------------");
                System.out.println("\t [ " + PURPLE + "  SavoryTech: Food Ordering System" + RESET + " ]");
                System.out.println("\t\t    ---------------------");
                System.out.println("[A]. Order Food");
                System.out.println("[B]. View Food Menu");
                System.out.println("[C]. Order History");
                System.out.println("[D]. Account Settings");
                System.out.println("[E]. Log Out");
                System.out.print("\n | Enter your choice: ");
                char orderingSystemMenuChoice = sc.next(".").charAt(0);
                sc.nextLine();

                switch (orderingSystemMenuChoice) {
                     case 'a', 'A' -> {
                        mp.displayMenu();
                        System.out.println("\n  | Enter the numbers of the items you want to order [1-60] (comma-separated): ");
                        String selectedItemsInput = sc.nextLine().trim();

                        // Check if the input is empty
                        if (selectedItemsInput.isEmpty() || selectedItemsInput.equals(",")) {
                            System.err.println("\t**** Empty input. Please enter valid item indices. ****");
                            displayOrderingSystemMenu();
                            return;
                        }

                        String[] itemIndicesArray = selectedItemsInput.split(",");
                        List<Integer> selectedItems = new ArrayList<>();

                        Set<Integer> uniqueItems = new HashSet<>();  // To check for duplicates

                        for (String indexString : itemIndicesArray) {
                            try {
                                int index = Integer.parseInt(indexString.trim());

                                // Check if the index is within the valid range [1-60]
                                if (index < 1 || index > 60) {
                                    System.err.println("\t**** Invalid item index: " + index + ". Please enter valid item indices. ****");
                                    displayOrderingSystemMenu();
                                    return;
                                }

                                // Check for duplicates
                                if (!uniqueItems.add(index)) {
                                    System.err.println("\t**** Duplicate item index: " + index + ". Please enter each item only once in an order. ****");
                                    displayOrderingSystemMenu();
                                    return;
                                }

                                selectedItems.add(index);
                            } catch (NumberFormatException e) {
                                System.err.println("\t**** Invalid input. Please enter valid item indices. ****");
                                displayOrderingSystemMenu();
                                return;
                            }
                        }

                        Voucher voucher = new PercentageVoucher();
                        mp.placeOrder(currentUser, selectedItems, voucher);
                        break;
                    }

                    case 'b', 'B' -> mp.displayMenu();
                    case 'c', 'C' -> mp.displayOrderHistory(currentUser);
                    case 'd', 'D' -> manageAccountSettings();
                    case 'e', 'E' -> {
                        orderingSystemMenuBl = false;
                        System.out.println("\n\t" + GREEN + "---- Logging out. Goodbye, " + currentUser.getName() + "! ----" + RESET + "\n");
                    }
                    default -> System.err.println("\n\t**** Invalid Choice. Try again, Thank you ****");
                }
            } catch (InputMismatchException e) {
                System.err.println("\n\t**** Invalid input. Please enter a valid choice. ****");
                sc.nextLine();  // Clear the input buffer
            }
        }
    }

    private static void manageAccountSettings() {
        boolean accountSettingsMenuBl = true;

        while (accountSettingsMenuBl) {
            try {
                System.out.println("\n\t\t    ---------------------");
                System.out.println("\t [ " + PURPLE + "  SavoryTech: Food Ordering System" + RESET + " ]");
                System.out.println("\t\t    ---------------------");
                System.out.println("\t[A]. View Account Information");
                System.out.println("\t[B]. Change Account ID");
                System.out.println("\t[C]. Delete Account");
                System.out.println("\t[D]. Back to Main Menu");
                System.out.print("\n| Enter your choice: ");
                char accountSettingsChoice = sc.next(".").charAt(0);
                sc.nextLine();

                switch (accountSettingsChoice) {
                    case 'a', 'A' -> viewAccountInformation(currentUser);
                    case 'b', 'B' -> {
                        System.out.println("Enter your new account ID:");
                        int newAccountId = sc.nextInt();
                        sc.nextLine(); // Consume the newline character

                        // Rename order history file if it exists
                        File oldOrderHistoryFile = new File("order_history_" + currentUser.getAccountId() + ".txt");
                        File newOrderHistoryFile = new File("order_history_" + newAccountId + ".txt");

                        if (oldOrderHistoryFile.exists()) {
                            if (oldOrderHistoryFile.renameTo(newOrderHistoryFile)) {
                                System.out.println("\n\t" + GREEN + "Account ID changed successfully!" + RESET);
                            } else {
                                System.out.println("\n\t" + RED + "Failed to rename order history file!" + RESET);
                            }
                        }

                        currentUser.setAccountId(newAccountId);
                    }
                    case 'c', 'C' -> {
                        System.out.println("Are you sure you want to delete your account? (Y/N): ");
                        char deleteConfirmation = sc.next().charAt(0);
                        sc.nextLine(); // Consume the newline character

                        if (deleteConfirmation == 'Y' || deleteConfirmation == 'y') {
                            users.remove(currentUser);
                            removeUserFromFile(currentUser.getAccountId());
                            System.out.println("\n\t" + GREEN + "Account deleted successfully. Goodbye, " + currentUser.getName() + "!" + RESET);
                            accountSettingsMenuBl = false;
                            main(null);
                        } else {
                            System.err.println("\n\t**** Account deletion canceled. **** ");
                        }
                    }
                    case 'd', 'D' -> accountSettingsMenuBl = false;
                    default -> System.err.println("\n\t**** Invalid Choice. Try again, Thank you ****");
                }
            } catch (InputMismatchException e) {
                System.err.println("\n\t**** Invalid input. Please enter a valid choice. ****");
                sc.nextLine();  // Clear the input buffer
            }
        }
    }

    private static void viewAccountInformation(User user) {
        System.out.println("\n\t"+ YELLOW + "| Account Information |" + RESET);
        System.out.println("Account ID: " + user.getAccountId());
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Home Address: " + user.getHomeAdd());
        System.out.println("Email Address: " + user.getEmailAdd());
    }

     private static void handleIOException(IOException e) {
        e.printStackTrace();
        System.err.println("\n\t" + RED + "An error occurred during file operation." + RESET);
    }
     
    static class InvalidMenuItemException extends Exception {
        public InvalidMenuItemException(String message) {
            super(message);
        }
    }
}
