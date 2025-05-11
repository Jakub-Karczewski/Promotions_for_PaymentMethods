package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JSONParsing {
    public String path_orders;
    public String path_payments;
    public JSONParsing(String path_o, String path_p){
        this.path_orders = path_o;
        this.path_payments = path_p;
    }

    public ArrayList<Order> parse_orders() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path_orders),
                new TypeReference<ArrayList<Order>>(){});
    }
    public ArrayList<Payment> parse_payments() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path_payments),
                new TypeReference<ArrayList<Payment>>(){});
    }
}
