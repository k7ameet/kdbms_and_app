package com.example.kdbms;

public class ListItem {

    String barcode;
    int quantity;

    public ListItem(String barcode_c){
        barcode = barcode_c;
        quantity = 1;
    }

    public void oneMore() {
        quantity++;
    }

    public String convertToJson () {
        return "{\"barcode\":\""+barcode+"\",\"quantity\":\""+quantity+"\"}";
    }

}
