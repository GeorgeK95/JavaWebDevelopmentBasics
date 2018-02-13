package com.company.javache;


import com.company.javache.enums.RequestMethod;
import com.company.javache.http.contracts.IHttpContext;
import com.company.javache.http.contracts.IHttpSessionStorage;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface Application {
    Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> getRoutes();

    IHttpSessionStorage getSession();

//    void setRepository(DbRepository repository);

    void setSession(IHttpSessionStorage session);

    byte[] handleRequest(IHttpContext httpContext);

    void establishResources();

}
