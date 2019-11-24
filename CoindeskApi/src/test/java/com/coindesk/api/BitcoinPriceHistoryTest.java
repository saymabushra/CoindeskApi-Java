package com.coindesk.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BitcoinPriceHistoryTest {
    private HttpResponseGetter mGetter;
    private BitcoinPriceHistory mBitcoinPriceHistory;

    @Before
    public void setup() {
        mGetter = mock(HttpResponseGetter.class);
        mBitcoinPriceHistory = new BitcoinPriceHistory(mGetter);
    }

    @Test
    public void testBitcoinHistoryForEuro() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/historical/close.json?index=EUR&currency=EUR"))
                .thenReturn("{\"bpi\":{\"2019-10-23\":6718.3303,\"2019-10-24\":6695.6528,\"2019-10-25\":7826.0969,\"2019-10-26\":8358.8238,\"2019-10-27\":8622.325,\"2019-10-28\":8310.5796,\"2019-10-29\":8493.1092,\"2019-10-30\":8222.2151,\"2019-10-31\":8217.8236,\"2019-11-01\":8296.2305,\"2019-11-02\":8338.8086,\"2019-11-03\":8250.2958,\"2019-11-04\":8473.0303,\"2019-11-05\":8421.2979,\"2019-11-06\":8443.9711,\"2019-11-07\":8331.5787,\"2019-11-08\":7959.1296,\"2019-11-09\":7998.368,\"2019-11-10\":8207.3344,\"2019-11-11\":7910.2097,\"2019-11-12\":8010.2448,\"2019-11-13\":7971.513,\"2019-11-14\":7838.2705,\"2019-11-15\":7665.4141,\"2019-11-16\":7688.3269,\"2019-11-17\":7705.4343,\"2019-11-18\":7398.0404,\"2019-11-19\":7344.5356,\"2019-11-20\":7311.591,\"2019-11-21\":6900.961,\"2019-11-22\":6616.951},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as EUR.\",\"time\":{\"updated\":\"Nov 23, 2019 00:03:00 UTC\",\"updatedISO\":\"2019-11-23T00:03:00+00:00\"}}");


        HistoryResult priceHistory = mBitcoinPriceHistory.fetchPriceHistory("EUR");
        double minValue = priceHistory.getMinValue();
        double maxValue = priceHistory.getMaxValue();
        assertEquals(6616.951d, minValue, 0.001);
        assertEquals(8622.325d, maxValue, 0.001);
    }

    @Test
    public void testBitcoinHistoryForEuroWithNetworkFail() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/historical/close.json?index=EUR&currency=EUR"))
                .thenReturn(null);

        assertNull(mBitcoinPriceHistory.fetchPriceHistory("EUR"));
    }

    @Test
    public void testInvalidCurrency() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/historical/close.json?index=ABC&currency=ABC"))
                .thenReturn("Sorry, that currency was not found");

        assertNull(mBitcoinPriceHistory.fetchPriceHistory("ABC"));
    }
}
