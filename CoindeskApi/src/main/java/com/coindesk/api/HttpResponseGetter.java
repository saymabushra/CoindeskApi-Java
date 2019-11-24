package com.coindesk.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpResponseGetter {
    private HttpClient mHttpClient;
    private EntityToString mEntityToString;
    public HttpResponseGetter(HttpClient httpClient, EntityToString entityToString)   // http response constructor
    {
        mHttpClient = httpClient;
        mEntityToString = entityToString;
    }

    public String getResponseBody(String url) {    /// get http response method
        HttpGet request = new HttpGet(url);
        try{
            HttpResponse response = mHttpClient.execute(request);
            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
              // TODO: check http status
            if (statusCode != HttpStatus.SC_OK) {

                return null ;
            }
            else
            {
            return mEntityToString.getEntityToString(entity);  // call to string method
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
