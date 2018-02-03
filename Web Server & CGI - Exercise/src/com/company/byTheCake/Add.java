package com.company.byTheCake;

import com.company.byTheCake.model.Cake;

import java.util.Map;
import java.util.Scanner;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Add {
    private static final String ADD_CAKE_HTML = "add-cake.html";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    public static void main(String[] args) {
        Utils.printHTMLHeaders();
        Utils.printHTMLBody(ADD_CAKE_HTML);

        //if it's get request then exit
        if (!Utils.postRequestValidator(System.getenv(Utils.REQUEST_METHOD))) {
            return;
        }

        //read form params
        Scanner in = new Scanner(System.in);
        Map<String, String> params = Utils.generateParams(in.nextLine());

        //write to file
        Cake cake = new Cake(params.get(NAME), Double.parseDouble(params.get(PRICE)));
        Utils.writeCakeToFile(cake);

        //print saved item
        System.out.println("<p id=\"nameParagraph\">name: " + params.get("name") + "</p>\n" +
                "    <p id=\"priceParagraph\">price: " + params.get("price") + "</p>");
    }


}
