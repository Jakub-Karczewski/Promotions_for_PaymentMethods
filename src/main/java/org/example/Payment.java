package org.example;

public class Payment implements Comparable<Payment> {
    private String ID;
    private int discount;
    private Float limit;

    public Payment(String ID, int discount, Float limit){
        this.ID = ID;
        this.discount = discount;
        this.limit = limit;
    }
    public Payment() {}

    public Float getLimit() {
        return limit;
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int compareTo(Payment other) {
        if (this.discount > other.discount){
            return 1;
        }
        else if (this.discount == other.discount){
            return Float.compare(this.limit, other.limit);
        }
        else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "ID =  " + this.ID + ", discount = " + this.discount + ", limit = " + this.limit;
    }
}
