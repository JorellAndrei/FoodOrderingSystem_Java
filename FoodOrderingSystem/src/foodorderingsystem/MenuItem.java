package foodorderingsystem;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private int itemNumber;

    public MenuItem(int itemNumber, String name, String category, double price) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getItemNumber() {
        return itemNumber;
    }
}
