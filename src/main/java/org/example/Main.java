package org.example;

import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {

        JSONParsing my_parser = new JSONParsing(args[0] + "/orders.json", args[1] + "/paymentmethods.json");

        ArrayList<Order> my_orders = my_parser.parse_orders();
        ArrayList<Payment> my_payments  = my_parser.parse_payments();


        Collections.sort(my_payments);
        Collections.sort(my_orders);
        Collections.reverse(my_payments);
        Collections.reverse(my_orders);

        Solution heuristic = new Solution(my_orders, my_payments);
        heuristic.run();
    }
}