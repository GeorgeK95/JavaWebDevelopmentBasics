package org.galaxi.handlers.solets.contracts;

import com.company.javache.http.contracts.IHttpResponse;

import java.io.OutputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSoletResponse extends IHttpResponse {
    OutputStream getResponseStream();
}
