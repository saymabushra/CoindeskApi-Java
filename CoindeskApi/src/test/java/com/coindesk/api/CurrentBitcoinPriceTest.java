package com.coindesk.api;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CurrentBitcoinPriceTest {
    private HttpResponseGetter mGetter;
    private CurrentBitcoinPrice mBitcoinPrice;

    @Before
    public void setup() {
        mGetter = mock(HttpResponseGetter.class);
        mBitcoinPrice = new CurrentBitcoinPrice(mGetter);
    }

    @Test
    public void testBitcoinRateForEuro() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/currentprice/EUR"))
                .thenReturn("{\"time\":{\"updated\":\"Nov 23, 2019 08:44:00 UTC\",\"updatedISO\":\"2019-11-23T08:44:00+00:00\",\"updateduk\":\"Nov 23, 2019 at 08:44 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"7,185.5050\",\"description\":\"United States Dollar\",\"rate_float\":7185.505},\"EUR\":{\"code\":\"EUR\",\"rate\":\"6,519.6530\",\"description\":\"Euro\",\"rate_float\":6519.653}}}");

        assertEquals("6,519.6530", mBitcoinPrice.getCurrentPrice("EUR"));
    }

    @Test
    public void testBitcoinRateForEuroWithNetworkFail() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/currentprice/EUR"))
                .thenReturn(null);

        assertNull(mBitcoinPrice.getCurrentPrice("EUR"));
    }

    @Test
    public void testInvalidCurrency() {
        when(mGetter.getResponseBody("https://api.coindesk.com/v1/bpi/currentprice/ABC"))
                .thenReturn("Sorry, your requested currency ABC is not supported or is invalid");

        assertEquals("Sorry, your requested currency ABC is not supported or is invalid", mBitcoinPrice.getCurrentPrice("ABC"));
    }
}
