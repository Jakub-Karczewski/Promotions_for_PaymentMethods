package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Order implements Comparable<Order>{

    private String ID;
    private  Float value;
    private ArrayList<String> promotions = new ArrayList<>();
    public Order(String ID, Float value, ArrayList<String> promotions){
        this.ID = ID;
        this.value = value;
        this.promotions = promotions;
    }
    public Order() {}
    public String getID()           { return ID; }
    public void setID(String ID)    { this.ID = ID; }
    public Float getValue()         { return value; }
    public void setValue(Float v)   { this.value = v; }
    public ArrayList<String> getPromotions() { return promotions; }
    public void setPromotions(ArrayList<String> p) { this.promotions = p; }

    @Override
    public int compareTo(Order other) {

        if (this.value > other.value){
            return 1;
        }
        else if (this.value.equals(other.value)){
            return Integer.compare(this.promotions.size(), other.promotions.size());
        }
        else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "ID =  " + this.ID + ", value = " + this.value + ", obslugiwane = " + Objects.requireNonNull(this.promotions).toString();
    }

}
