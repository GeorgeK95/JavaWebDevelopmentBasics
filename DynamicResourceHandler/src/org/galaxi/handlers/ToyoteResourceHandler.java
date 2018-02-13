package org.galaxi.handlers;

import com.company.javache.http.HttpRequest;
import com.company.javache.http.HttpResponse;
import com.company.javache.http.contracts.IHttpRequest;
import com.company.javache.http.contracts.IHttpResponse;
import com.company.javache.io.InputStreamReader;
import com.company.javache.io.OutputStreamWriter;
import com.company.javache.lib.handler.IRequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.galaxi.handlers.ToyoteResourceHandlerConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ToyoteResourceHandler implements IRequestHandler {

    private String rootPath;
    private IHttpRequest request;
    private IHttpResponse response;
    private boolean isIntercepted;

    public ToyoteResourceHandler() {
    }

    public ToyoteResourceHandler(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void handleRequest(InputStream inputStream, DataOutputStream dataOutputStream) {
        try {
            this.initData(inputStream);

            this.isIntercepted = false;
            if (this.request.getUrl().equals(HTML_TOYOTE)) {
                this.ok();

                byte[] content = this.readContent();
                this.response.setContent(content);

                OutputStreamWriter.writeOutputStream(this.response.getBytes(), dataOutputStream);
                this.isIntercepted = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] readContent() {
        String path = this.rootPath.concat(STATIC_HTML_INDEX_TOYOTE_HTML);
        return InputStreamReader.readResourceAndGetHtmlContent(path);
    }

    private void initData(InputStream inputStream) throws IOException {
        String readClientInputStream = InputStreamReader.readClientInputStream(inputStream);
        this.request = new HttpRequest(readClientInputStream);
        this.response = new HttpResponse();
        this.isIntercepted = false;
    }

    private void ok() {
        this.response.addHeader(OK, HTTP_1_1_200_OK);
        this.response.addHeader(HTML, CONTENT_TYPE_TEXT_HTML);
        this.response.addHeader(DISPOSITION, CONTENT_DISPOSITION_INLINE);
    }

    @Override
    public boolean hasIntercepted() {
        return this.isIntercepted;
    }
}
