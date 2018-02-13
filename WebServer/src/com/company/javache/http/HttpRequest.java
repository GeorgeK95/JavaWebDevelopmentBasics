package com.company.javache.http;


import com.company.javache.http.contracts.IHttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.company.javache.utils.ServerConstants.*;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpRequest implements IHttpRequest {

    private Map<String, String> headers;
    private Map<String, String> bodyParams;
    private Map<String, String> cookies;

    private String method;
    private String url;

    public HttpRequest(String content) {
        this.initializeData(content);
    }

    private void initializeData(String requestContent) {
        this.setHeaders(requestContent);
        this.setCookies();
        this.setBodyParams(requestContent);
        String[] requestData = requestContent.split(SPACE);
        this.setMethod(requestData[0]);
        this.setUrl(requestData[1]);
    }

    private void setCookies() {
        this.cookies = new HashMap<>();
        if (!this.headers.containsKey(COOKIE)) {
            return;
        }
        String[] cookieValues = this.headers.get(COOKIE).split(COLON_SPLITTER)[1].split(SEMICOLON_SPLITTER);
        for (String cookieValue : cookieValues) {
            String[] currentCookieKeyValue = cookieValue.split("\\" + EQUAL_SPLITTER);
            this.cookies.put(currentCookieKeyValue[0], currentCookieKeyValue[1]);
        }
    }

    private String[] extractHeaders(String requestContent) {
        String headersData = requestContent.substring(0, requestContent.indexOf(System.lineSeparator().concat(System.lineSeparator())));
        String[] byLineSeparator = headersData.split(System.lineSeparator());
        List<String> headers = Arrays.stream(byLineSeparator).collect(Collectors.toList()).subList(1, byLineSeparator.length);
        return headers.toArray(new String[0]);
    }

    private void setHeaders(String requestContent) {
        this.headers = new HashMap<>();
        String[] headers = this.extractHeaders(requestContent);//potential bug
        Arrays.stream(headers).forEach(h -> this.addHeader(h.split(COLON_SPLITTER)[0], h));
    }

    private void setBodyParams(String requestContent) {
        this.bodyParams = new HashMap<>();
        String inputData = requestContent.substring(requestContent.indexOf(System.lineSeparator()
                .concat(System.lineSeparator())), requestContent.length()).substring(System.lineSeparator().length() * 2);
        try {
            inputData = URLDecoder.decode(inputData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.bodyParams = this.extractInputDataParams(inputData);
    }

    private Map<String, String> extractInputDataParams(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] splittedByAmpersand = formData.split(AMPERSAND_SPLITTER);
        for (String firstSplit : splittedByAmpersand) {
            String[] data = this.filterData(firstSplit);
            if (data.length != 0)
                params.put(data[0], data[1].replace(HTML_SPACE_ENCODED_SYMBOL, SPACE));
        }
        return params;
    }

    private String[] filterData(String firstSplit) {
        List<String> filteredData = Arrays.stream(firstSplit.split("=")).filter(d -> !d.isEmpty()).collect(Collectors.toList());
        return filteredData.toArray(new String[filteredData.size()]);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public Map<String, String> getBodyParams() {
        return this.bodyParams;
    }

    @Override
    public Map<String, String> getCookies() {
        return this.cookies;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParam(String bodyParam, String value) {
        this.bodyParams.put(bodyParam, value);
    }

    @Override
    public boolean isResource() {
       /* String fileExtension = WebUtils.getFileExtension(this.url);

        //.html or empty - actual route, otherwise - resource
        return fileExtension.equals(HTML_CONTENT_TYPE_STRING) || fileExtension.isEmpty();*/
        return false;
    }
}
