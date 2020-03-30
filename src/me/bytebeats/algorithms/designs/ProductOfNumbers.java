package me.bytebeats.algorithms.designs;

import java.util.ArrayList;
import java.util.List;

public class ProductOfNumbers {
    private List<Integer> list;

    public ProductOfNumbers() {
        list = new ArrayList<>();
    }

    public void add(int num) {
        list.add(num);
    }

    public int getProduct(int k) {
        int sum = 1;
        for (int i = list.size() - 1; i >= list.size() - k; i--) {
            sum *= list.get(i);
        }
        return sum;
    }
}