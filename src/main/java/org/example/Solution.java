package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solution implements Runnable{
    private ArrayList<Order> my_orders;
    public ArrayList<Payment> my_payments;
    private boolean[] visited;
    private float[] used_per_payment;
    private boolean[] applied_partial_points;
    private float total_profit = 0.0f;
    HashMap<String, Integer> payment_names_to_indexes = new HashMap<>();
    ArrayList<ArrayList<Integer>> AvailableOrders_for_EachPayment = new ArrayList<>();
    private int points_ind;


    public Solution(ArrayList<Order> orders, ArrayList<Payment> payments){
        this.my_orders = orders;
        this.my_payments = payments;

        visited = new boolean[my_orders.size()];
        Arrays.fill(visited, Boolean.FALSE);
        applied_partial_points = new boolean[my_orders.size()];
        Arrays.fill(applied_partial_points, Boolean.FALSE);
        used_per_payment = new float[my_payments.size()];
        Arrays.fill(used_per_payment, 0.0f);

        fill_hashmap();
        find_orders_forEachPayment();


    }

    public float actualize_profit(int i, Payment pay_i, Order order_j, float act_limit){

        float value_spended = order_j.getValue() * (100.0f - (float) pay_i.getDiscount())/100.0f;
        act_limit -= value_spended;
        used_per_payment[i] += value_spended;
        total_profit += ((float) pay_i.getDiscount() * order_j.getValue())/100.0f;
        return act_limit;

    }

    public void fill_hashmap(){

        for (int i = 0; i < my_payments.size(); i++){
            Payment p = my_payments.get(i);
            if (p.getID().equals("PUNKTY")){
                points_ind = i;
            }
            payment_names_to_indexes.put(p.getID(), i);
        }
    }

    public void find_orders_forEachPayment(){

        for(int i = 0; i < my_payments.size(); i++){
            AvailableOrders_for_EachPayment.add(new ArrayList<>());
        }

        for (int i = 0; i < my_orders.size(); i++) {
            Order o = my_orders.get(i);
            for (String s : o.getPromotions()) {
                Integer pos = payment_names_to_indexes.get(s);
                AvailableOrders_for_EachPayment.get(pos).add(i);
            }
        }
        for (int i = 0; i < my_orders.size(); i++) {
            AvailableOrders_for_EachPayment.get(points_ind).add(i);
        }
    }


    public int calculate_options_before_PartialPoints(){

        int last_index = my_payments.size();
        for (int i = 0; i < my_payments.size(); i++){
            Payment pay_i = my_payments.get(i);
            float act_limit = pay_i.getLimit();
            if (pay_i.getDiscount() == 0 || pay_i.getDiscount() < 10){
                last_index = i;
                break;
            }
            for (Integer j : AvailableOrders_for_EachPayment.get(i)){
                Order order_j = my_orders.get(j);
                if (!visited[j] && act_limit >= order_j.getValue()){
                    visited[j] = true;
                    act_limit = actualize_profit(i, pay_i, order_j, act_limit);
                }
            }
        }
        return last_index;
    }

    public void calculate_PartialPoints(){

        float act_points = my_payments.get(points_ind).getLimit() - used_per_payment[points_ind];

        for (int i = 0; i < my_orders.size(); i++){
            Order order_i = my_orders.get(i);
            if(!visited[i] && act_points >= order_i.getValue() * 0.1f){
                applied_partial_points[i] = true;

                act_points -= order_i.getValue() * 0.1f;
                used_per_payment[points_ind] += order_i.getValue() * 0.1f;
                total_profit += order_i.getValue() * 0.1f;
            }
        }
    }

    public void calculate_worse_than_PartialPoints(int last_ind){

        for (int i = last_ind; i < my_payments.size(); i++){
            Payment pay_i = my_payments.get(i);
            float act_limit = pay_i.getLimit();

            for (Integer j : AvailableOrders_for_EachPayment.get(i)){
                Order order_j = my_orders.get(j);
                if (!visited[j] && !applied_partial_points[j] && act_limit >= order_j.getValue()){
                    visited[j] = true;
                    act_limit = actualize_profit(i, pay_i, order_j, act_limit);
                }
            }
        }
    }


    public void distribute_remaining(){

        int last_available_payment = 0;
        for (int i = 0; i < my_orders.size(); i++){
            Order order_i = my_orders.get(i);
            float remaining;
            if (visited[i]){
                continue;
            }
            else{
                if (applied_partial_points[i]){
                    remaining = 0.8f * order_i.getValue();
                }
                else{
                    remaining = order_i.getValue();
                }
            }

            int k = last_available_payment;

            while(k < my_payments.size()){
                Payment pay_k = my_payments.get(k);
                if (pay_k.getLimit() - used_per_payment[k] >= remaining){
                    used_per_payment[k] += remaining;
                    last_available_payment = k;
                    break;
                }
                else{
                    if (used_per_payment[k] < pay_k.getLimit()) {
                        remaining -= pay_k.getLimit() - used_per_payment[k];
                        used_per_payment[k] = pay_k.getLimit();
                    }
                    k += 1;
                }
            }
        }
    }

    public void print_solution(){
        System.out.println("total_profit " + total_profit);
        for (int i = 0; i < my_payments.size(); i++){
            System.out.println(my_payments.get(i).getID() + " " + used_per_payment[i]);
        }
    }

    @Override
    public void run(){


        int last_index = calculate_options_before_PartialPoints();
        calculate_PartialPoints();
        calculate_worse_than_PartialPoints(last_index);
        distribute_remaining();
        print_solution();

    }

}
