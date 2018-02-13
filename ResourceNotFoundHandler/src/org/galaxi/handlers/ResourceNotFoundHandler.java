package org.galaxi.handlers;

import com.company.javache.http.HttpResponse;
import com.company.javache.http.contracts.IHttpResponse;
import com.company.javache.io.ConsoleOutputWriter;
import com.company.javache.io.InputStreamReader;
import com.company.javache.io.OutputStreamWriter;
import com.company.javache.lib.handler.IRequestHandler;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.galaxi.handlers.ResourceNotFoundHandlerConstants.ERROR_404_HTML_PATH;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ResourceNotFoundHandler implements IRequestHandler {

    private IHttpResponse response;
    private String rootPath;

    public ResourceNotFoundHandler() {
    }

    public ResourceNotFoundHandler(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void handleRequest(InputStream requestStream, DataOutputStream responseStream) throws FileNotFoundException {
        this.response = new HttpResponse();
        this.notFound();
        String path = this.rootPath + ERROR_404_HTML_PATH;
        ConsoleOutputWriter.writeOnConsole(path);
        response.setContent(InputStreamReader.readResourceAndGetHtmlContent(path));
        this.writeResponse(responseStream);
    }

    private void notFound() {
        this.response.addHeader("notFound", "HTTP/1.1 404 Not Found");
        this.response.addHeader("html", "Content-Type: text/html");
        this.response.addHeader("disposition", "Content-Disposition: inline");
    }

    private void writeResponse(DataOutputStream outputStream) {
        byte[] handledRequest = this.response.getBytes();
        try {
            OutputStreamWriter.writeOutputStream(handledRequest, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasIntercepted() {
        return true;
    }

    public IHttpResponse getResponse() {
        return this.response;
    }
}
