package org.galaxi.handlers.solets;

import com.company.javache.http.HttpRequest;
import org.galaxi.handlers.solets.contracts.IHttpSoletRequest;

import java.io.InputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSoletRequest extends HttpRequest implements IHttpSoletRequest {

    private InputStream requestStream;

    public HttpSoletRequest(String content, InputStream requestStream) {
        super(content);
        this.requestStream = requestStream;
    }

    @Override
    public InputStream getRequestStream() {
        return this.requestStream;
    }
}
