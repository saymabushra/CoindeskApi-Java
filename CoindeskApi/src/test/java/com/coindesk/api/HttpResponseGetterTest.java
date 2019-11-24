package com.coindesk.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class HttpResponseGetterTest {
    private HttpClient httpClient;
    private HttpResponseGetter responseGetter;
    private EntityToString entityToString;

    @Before
    public void setup() {
        httpClient = mock(HttpClient.class);
        entityToString = mock(EntityToString.class);
        responseGetter = new HttpResponseGetter(httpClient, entityToString);
    }

    @Test
    public void entitytoStringTest() throws IOException {
        HttpEntity entity = mock(HttpEntity.class);
        when(entityToString.getEntityToString(entity))
                .thenReturn("Hello");

        assertEquals("Hello", entityToString.getEntityToString(entity));
    }



    @Test
    public void testResponseBodyWhenNetworkError() throws IOException {
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        when(httpClient.execute(any())).thenThrow(new IOException("Network error!"));
        assertNull(responseGetter.getResponseBody("http://google.com"));
    }
}
