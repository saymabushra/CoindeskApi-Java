package com.coindesk.api;

import org.json.JSONObject;

public class CurrentBitcoinPrice {
    private static final String CURRENT_PRICE_URL = "https://api.coindesk.com/v1/bpi/currentprice/";  //url for current bitcoin rate
    private HttpResponseGetter mResponseGetter;

    public CurrentBitcoinPrice(HttpResponseGetter responseGetter)     //create constructor
    {
        this.mResponseGetter = responseGetter;
    }

    public String getCurrentPrice(String strcurrency) {  // method for get current price
        String strurl = CURRENT_PRICE_URL + strcurrency;
        String strcurrentPrice = this.mResponseGetter.getResponseBody(strurl);  // get response body  from HttpResponseGetter
        if(strcurrentPrice == null) {
            // TODO: Message network error
            return null;
        }
        else if (strcurrentPrice.startsWith("Sorry"))
        {
            //TODO: Check if the response is invalid / Invalid currency
            return strcurrentPrice;
        }

        else {
            try {
                // json parsing
                JSONObject objrealTimeBpi = new JSONObject(strcurrentPrice).getJSONObject("bpi");
                return objrealTimeBpi.getJSONObject(strcurrency).getString("rate");
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

         }
    }
}
