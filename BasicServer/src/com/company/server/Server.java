package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by George-Lenovo on 6/29/2017.
 * Implemented very basic server /alone/ to exercise and understand the principles of HTTP protocol.
 */

public class Server {
    private static final String POST = "POST";
    private static final String GET = "GET";

    private static final String RESOURCES_PATH = "E:\\OfflineProjects\\BasicServer\\src\\com\\resources\\";

    private static final String INDEX_HTML = "index.html";
    private static final String IMAGE_HTML = "gClass";
    private static final String CLEAN_HTML = "cleanHtml.html";
    private static final String PLAIN_TEXT_HTML = "plainText.html";
    private static final String WORD_HTML = "wordDownload.html";

    private static final String GCLASS_JPG = "gclass.png";
    private static final String PDF_HTML = "pdfDownload.html";
    private static final String SCHEDULE_PDF = "simpleSchedule.pdf";
    private static final String SCHEDULE_WORD = "simpleSchedule.docx";

    private static final String RESPONSE_OK = "ok";
    private static final String RESPONSE_NOT_FOUND = "not_found";

    private static final String PICTURE_ME_ROLLING_IN_MY_500_BENZ = "<h4>picture me rolling in my 500 benz</h4>";
    private static final String SPACE = "\\s";

    private HashMap<String, String> responseLines;

    private int port;
    private Socket connection;
    private ServerSocket serverSocket;

    private DataOutputStream responseStream;
    private BufferedReader requestStream;

    public Server(int port) {
        this.port = port;
        this.seedResponseLines();
    }

    private void seedResponseLines() {
        this.responseLines = new HashMap<>();
        this.responseLines.put("ok", "HTTP/1.1 200 RESPONSE_OK");
        this.responseLines.put("bad_request", "HTTP/1.1 400 Bad Request");
        this.responseLines.put("unathorized", "HTTP/1.1 401 Unathorized");
        this.responseLines.put("not_found", "HTTP/1.1 404 Not Found");
        this.responseLines.put("server_error", "HTTP/1.1 500 Internal Server Error");
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);

        System.out.println(String.format("Waiting on port %d ...", this.port));

        //listen for clients
        while (true) {
            //connected
            this.connection = serverSocket.accept();
            System.out.println("Established connection.");

            //read request
            this.requestStream = this.getInputStream();

            StringBuilder constructedResponse = null;

            String line = "";
            StringBuilder requestHeaders = new StringBuilder();
            while ((line = requestStream.readLine()) != null && line.length() > 0) {
                requestHeaders.append(line).append(SPACE);
            }

            if (this.getRequestType(requestHeaders).equals(GET)) {
                constructedResponse = this.handleGetRequest(this.getTargetPath(requestHeaders));
            } else if (this.getRequestType(requestHeaders).equals(POST)) {
                //write/save data to some file on hdd
//                TODO: constructedResponse = this.handlePostRequest(this.getTargetPath(requestHeaders));
            } else {
                //this basic server does not implement other request types /later maybe it will/
//                TODO: constructedResponse = this.handleOtherRequests(this.getTargetPath(requestHeaders));
            }

            //close streams
            this.responseStream.close();
            this.requestStream.close();
        }
    }

    private StringBuilder handleGetRequest(String targetPath) throws IOException {
        String htmlContent;
        StringBuilder response = new StringBuilder();
        this.responseStream = this.getOutputStream();
        List<byte[]> responseByteArraysList = new ArrayList<>();

        if (targetPath.isEmpty()) {
            htmlContent = this.readHTMLFile(RESOURCES_PATH + INDEX_HTML);
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Content-Type: text/html").append(System.lineSeparator()).append(System.lineSeparator())
                    .append(htmlContent);
        } else if (targetPath.equals(IMAGE_HTML)) {
//            htmlContent = this.readHTMLFile(RESOURCES_PATH + IMAGE_HTML);
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
//                    .append("Request URL: http://localhost:3310/").append(System.lineSeparator())
                    .append("Content-Type: image/png").append(System.lineSeparator())
                    .append("Content-Disposition: inline").append(System.lineSeparator()).append(System.lineSeparator());
//                    .append(htmlContent);
            byte[] imageBytes = Files.readAllBytes(Paths.get(RESOURCES_PATH + GCLASS_JPG));
            responseByteArraysList.add(imageBytes);
        } else if (targetPath.equals(CLEAN_HTML)) {
            htmlContent = this.readHTMLFile(RESOURCES_PATH + CLEAN_HTML);
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Request URL: http://localhost:3310/").append(CLEAN_HTML)
                    .append("Content-Type: text/html").append(System.lineSeparator()).append(System.lineSeparator())
                    .append(htmlContent);
        } else if (targetPath.equals(PLAIN_TEXT_HTML)) {
            htmlContent = PICTURE_ME_ROLLING_IN_MY_500_BENZ;
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Request URL: http://localhost:3310/").append(PLAIN_TEXT_HTML)
                    .append("Content-Type: text/plain").append(System.lineSeparator()).append(System.lineSeparator())
                    .append(htmlContent);
        } else if (targetPath.equals(PDF_HTML)) {
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Request URL: http://localhost:3310").append(System.lineSeparator())
                    .append("Content-Type: application/pdf").append(System.lineSeparator())
                    .append("Content-Disposition: attachment; filename=\"simpleSchedule.pdf\"").append(System.lineSeparator()).append(System.lineSeparator());
            byte[] pdfByteFile = Files.readAllBytes(Paths.get(RESOURCES_PATH + SCHEDULE_PDF));
            responseByteArraysList.add(pdfByteFile);
        } else if (targetPath.equals(WORD_HTML)) {
            response.append(this.responseLines.get(RESPONSE_OK)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Request URL: http://localhost:3310").append(System.lineSeparator())
                    .append("Content-Type: application/msword").append(System.lineSeparator())
                    .append("Content-Disposition: attachment; filename=\"simpleSchedule.docx\"").append(System.lineSeparator()).append(System.lineSeparator());
            byte[] wordByteFile = Files.readAllBytes(Paths.get(RESOURCES_PATH + SCHEDULE_WORD));
            responseByteArraysList.add(wordByteFile);
        } else {
            htmlContent = String.format("<h1 style=\"color:red\">Resource %s was not found on the server !</h1>", targetPath);
            response.append(this.responseLines.get(RESPONSE_NOT_FOUND)).append(System.lineSeparator())
                    .append("Server: CustomServer 1.0").append(System.lineSeparator())
                    .append("Content-Type: text/html").append(System.lineSeparator()).append(System.lineSeparator())
                    .append(htmlContent);
        }

        byte[] bytesResponse = response.toString().getBytes();
        responseByteArraysList.add(bytesResponse);
        this.sendResponse(responseByteArraysList);
        return response;
    }

    private void sendResponse(List<byte[]> byteFiles) throws IOException {
        Collections.reverse(byteFiles);

        for (byte[] currentByteArray : byteFiles) {
            if (currentByteArray != null)
                responseStream.write(currentByteArray);
        }
    }

    private BufferedReader getInputStream() {
        try {
            return new BufferedReader(new InputStreamReader(
                    this.connection.getInputStream()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private DataOutputStream getOutputStream() {
        try {
            return new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readHTMLFile(String absolutePath) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    private String getTargetPath(StringBuilder requestStream) {
        return requestStream.toString().split(SPACE)[1].substring(1);//GET /home HTTP/1.1
    }

    private String getRequestType(StringBuilder requestStream) {
        return requestStream.toString().split(SPACE)[0];
    }
}