
package foodorderingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int accountId;  // Change the type to int
    private String name;
    private int age;
    private String homeAdd;
    private String emailAdd;
    private List<String> orderHistory;
     private int userOrderCounter;

    public User(int accountId, String name, int age, String homeAdd, String emailAdd) {
        this.accountId = accountId;
        this.name = name;
        this.age = age;
        this.homeAdd = homeAdd;
        this.emailAdd = emailAdd;
        this.orderHistory = readOrderHistoryFromFile();
        this.userOrderCounter = 1;
    }
    
    private List<String> readOrderHistoryFromFile() {
        String fileName = "orderHistory_" + accountId + ".txt";
        List<String> orderHistory = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orderHistory.add(line);
            }
        } catch (IOException e) {
            // Ignore if the file doesn't exist, as it might be a new user
        }

        return orderHistory;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int newAccountId) {
        int oldAccountId = this.accountId;
        this.accountId = newAccountId;

        // Update the account ID in the user account file
        updateAccountIdInFile(oldAccountId, newAccountId);
    }
    
    private static final String USER_FILE_PATH = "userAccounts.txt";
    
    private void updateAccountIdInFile(int oldAccountId, int newAccountId) {
        File file = new File(USER_FILE_PATH);
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int existingAccountId = Integer.parseInt(parts[0].trim());
                if (existingAccountId == oldAccountId) {
                    parts[0] = String.valueOf(newAccountId);
                    line = String.join(",", parts);
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(USER_FILE_PATH, false)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeAdd() {
        return homeAdd;
    }

    public void setHomeAdd(String homeAdd) {
        this.homeAdd = homeAdd;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) {
        this.orderHistory = orderHistory;
    }
    
     public void addToOrderHistory(String orderDetails) {
        this.orderHistory.add(orderDetails);
    }
     
    public void saveOrderHistoryToFile() {
        try (FileWriter writer = new FileWriter("orderHistory_" + getAccountId() + ".txt", true)) {
            writer.write(getOrderHistory().get(getOrderHistory().size() - 1) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void incrementUserOrderCounter() {
        this.userOrderCounter++;
    }
}
