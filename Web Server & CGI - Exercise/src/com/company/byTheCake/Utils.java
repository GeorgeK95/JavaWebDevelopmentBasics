package com.company.byTheCake;

import com.company.byTheCake.model.Cake;

import java.io.*;
import java.util.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
final class Utils {
    private static final String HTML_FOLDER = "C:\\xampp\\cgi-bin\\com\\resources\\";
    static final String REQUEST_METHOD = "REQUEST_METHOD";
    static final String QUERY_STRING = "QUERY_STRING";

    private static final String POST_REQUEST = "POST";
    private static final String DB_FILE = "C:\\xampp\\cgi-bin\\com\\company\\byTheCake\\data\\database.csv";
    private static final String HTML_SPACE_ENCODED_SYMBOL = "+";
    private static final String NORMAL_SPACE = " ";

    static void printHTMLHeaders() {
        String output = "Content-Type: text/html".concat(System.lineSeparator());
        System.out.println(output);
    }

    static void printHTMLBody(String path) {
        String body = Utils.readHTMLFile(HTML_FOLDER + path);
        System.out.println(body);
    }

    private static String readHTMLFile(String pathToFile) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToFile)));
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

    static boolean postRequestValidator(String request) {
        return request.equals(POST_REQUEST);
    }

    static void writeCakeToFile(Cake cake) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String line = cake.getName() + "," + cake.getPrice() + "\r\n";
            File file = new File(DB_FILE);
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static Map<String, String> readFromCSVFile() {
        Scanner scanner;
        Map<String, String> namePriceMap = new LinkedHashMap<>();
        try {
            scanner = new Scanner(new File(DB_FILE));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                namePriceMap.put(split[0], split[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return namePriceMap;
    }

    static Map<String, String> generateParams(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] splittedByAmpersand = formData.split("\\&");
        for (String firstSplit : splittedByAmpersand) {
            String[] data = firstSplit.split("=");
            params.put(data[0], data[1].replace(HTML_SPACE_ENCODED_SYMBOL, NORMAL_SPACE));
        }
        return params;
    }

    static List<Cake> deserializeMapToCakeList(Map<String, String> namePriceMap) {
        List<Cake> cakeList = new ArrayList<>();
        for (Map.Entry<String, String> namePricePair : namePriceMap.entrySet()) {
            Cake newCake = new Cake(namePricePair.getKey(), Double.parseDouble(namePricePair.getValue()));
            cakeList.add(newCake);
        }
        return cakeList;
    }
}
