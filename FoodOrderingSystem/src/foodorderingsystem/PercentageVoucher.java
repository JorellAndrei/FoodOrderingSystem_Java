package foodorderingsystem;

public class PercentageVoucher implements Voucher {
    public static final double DISCOUNT_TIER_1_AMOUNT = 300;
    public static final double DISCOUNT_TIER_2_AMOUNT = 500;
    public static final double DISCOUNT_TIER_3_AMOUNT = 1000;
    public static final double DISCOUNT_TIER_4_AMOUNT = 2000;

      @Override
    public double applyDiscount(double totalPrice) {
        // Apply different discount percentages based on the total order amount
        if (totalPrice >= DISCOUNT_TIER_4_AMOUNT) {
            return (25.0 / 100.0) * totalPrice;  // 25% discount
        } else if (totalPrice >= DISCOUNT_TIER_3_AMOUNT) {
            return (15.0 / 100.0) * totalPrice;  // 15% discount
        } else if (totalPrice >= DISCOUNT_TIER_2_AMOUNT) {
            return (10.0 / 100.0) * totalPrice;  // 10% discount
        } else if (totalPrice >= DISCOUNT_TIER_1_AMOUNT) {
            return (5.0 / 100.0) * totalPrice;   // 5% discount
        } else {
            return 0;  // No discount
        }
    }
}

