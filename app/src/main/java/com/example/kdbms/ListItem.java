package com.example.kdbms;

import org.json.JSONException;
import org.json.JSONObject;

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

    public String getBarcode(){
        return barcode;
    }

    public JSONObject itemToJson () {
        JSONObject json = new JSONObject();
        try {
            json.put("barcode", barcode);
            json.put("quantity", quantity);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public boolean equals(Object o){
        if(o instanceof ListItem){
            ListItem p = (ListItem) o;
            return this.barcode.equals(p.getBarcode());
        } else
            return false;
    }

}
