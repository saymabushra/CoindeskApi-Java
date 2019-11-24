package com.coindesk.api;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: java -jar CoindeskApi.jar CURRENCY");  //for jar use
            return;
        }
        
        String currency = args[0].toUpperCase(); //upper case input
        if(currency.equalsIgnoreCase(""))
        {
            System.out.println("Please insert a correct value");  // check for null input
        }
        else {
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponseGetter responseGetter = new HttpResponseGetter(httpClient, new EntityToString()); //  create HttpResponseGetter object
            CurrentBitcoinPrice currentPriceGetter = new CurrentBitcoinPrice(responseGetter);  //create CurrentBitcoinPrice object
            BitcoinPriceHistory priceHistoryGetter = new BitcoinPriceHistory(responseGetter);  // create BitcoinPriceHistory object

            String currentPrice = currentPriceGetter.getCurrentPrice(currency);  // call for current price rate
            // TODO: Error check.
             if(currentPrice==null) {
                 System.out.println("Invalid Input");
             }
             else
             {
                 if (currentPrice.startsWith("Sorry")) {
                     System.out.println(currentPrice);
                 } else {
                     HistoryResult priceHistory = priceHistoryGetter.fetchPriceHistory(currency);  // create HistoryResult object
                     if (priceHistory == null) {
                         System.out.println("Invalid Input");
                     } else {

                         Double minPrice = priceHistory.getMinValue();
                         Double maxPrice = priceHistory.getMaxValue();

                         System.out.println("Current price of "+currency+": " + currentPrice);
                         System.out.println("Min price in last 30 days of "+currency+": " + minPrice);
                         System.out.println("Max price in last 30 days of "+currency+": " + maxPrice);
                     }
                 }


             }
        }
    }
}
