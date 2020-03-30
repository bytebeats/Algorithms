package me.bytebeats.algorithms.designs;

public class Cashier {
    private int n;
    private int discount;
    private int[] products;
    private int[] prices;

    private int count = 0;

    public Cashier(int n, int discount, int[] products, int[] prices) {
        this.n = n;
        this.discount = discount;
        this.products = products;
        this.prices = prices;
    }

    public double getBill(int[] product, int[] amount) {
        count++;
        double bill = 0.0;
        for (int i = 0; i < product.length; i++) {
            int index = index(product[i]);
            int price = prices[index];
            bill += amount[i] * price;
        }
        double dis = 0;
        if (count % n == 0) {
            dis = discount;
        }
        return bill - dis * bill / 100;
    }

    private int index(int product) {
        int index = 0;
        for (int i = 0; i < products.length; i++) {
            if (product == products[i]) {
                index = i;
                break;
            }
        }
        return index;
    }
}
