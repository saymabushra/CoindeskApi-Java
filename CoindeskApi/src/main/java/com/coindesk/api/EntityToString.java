package com.coindesk.api;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class EntityToString {
    public String getEntityToString(HttpEntity entity) throws IOException   // tostring
    {
        return EntityUtils.toString(entity);
    }
}
