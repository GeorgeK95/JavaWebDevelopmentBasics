package org.galaxi.handlers.solets;

import com.company.javache.http.HttpResponse;
import org.galaxi.handlers.solets.contracts.IHttpSoletResponse;

import java.io.OutputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSoletResponse extends HttpResponse implements IHttpSoletResponse {

    private OutputStream responseStream;

    public HttpSoletResponse(OutputStream responseStream) {
        this.responseStream = responseStream;
    }

    @Override
    public OutputStream getResponseStream() {
        return this.responseStream;
    }
}
