package org.galaxi.handlers.solets.contracts;

import com.company.javache.http.contracts.IHttpRequest;

import java.io.InputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSoletRequest extends IHttpRequest {
    InputStream getRequestStream();
}
