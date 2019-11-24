package com.coindesk.api;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BitcoinPriceHistory {

    private static final String API_HISTORY_URL = "https://api.coindesk.com/v1/bpi/historical/close.json"; ////By default, this will return the previous 31 days' worth of data.

    private HttpResponseGetter mResponseGetter;

    public BitcoinPriceHistory(HttpResponseGetter responseGetter) // create constructor
    {
        this.mResponseGetter = responseGetter;
    }

    public HistoryResult fetchPriceHistory(String currency)   // class HistoryResult extends object
    {
        String strurl = String.format(API_HISTORY_URL + "?index=%s&currency=%s", currency, currency);
        /** ------For Get any  date range bitcoin rate data-----------------
         * String strStartDate;
         * String strEndDate;
         //strEndDate=objCurrentBitRate.getCurrentTimeStamp(); // get Current time
         //strStartDate=objCurrentBitRate.getPreviousTimeStamp(); // get last 30 days date
         * // for get any data range of data used start data and end date like below url.
         //  String strurl = String.format(API_HISTORY_URL + "?start=%s&end=%s&index=%s&currency=%s",strStartDate,strEndDate, currency, currency);
         * */
        String strresponse = mResponseGetter.getResponseBody(strurl);  //HttpResponseGetter extends object  // for get  http response
        if(strresponse == null) {
            // TODO: Handle network error
            return null;
        }

        else
        {
            try{
                // get json date
                List<Double> priceList = new ArrayList<>();
                JSONObject bpi = new JSONObject(strresponse).getJSONObject("bpi");
                bpi.keySet().forEach(key -> priceList.add(bpi.getDouble(key)));  //add key value in List.
                return new HistoryResult(Collections.min(priceList), Collections.max(priceList));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

    }

    // get Current Time
    public  String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); // data format
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    // get any date range
    public  String getPreviousTimeStamp() throws ParseException
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Date myDate = (Date) sdfDate.parse(strDate);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(myDate);
        cal1.add(Calendar.DAY_OF_YEAR, -30);
        Date previousDate = cal1.getTime();
        String strReturnDate=sdfDate.format(previousDate);
        return strReturnDate;
    }
}
