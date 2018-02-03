package com.company.byTheCake;

import com.company.byTheCake.model.Cake;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Browse {

    private static final String BROWSE_CAKES_HTML = "browse-cakes.html";
    private static final BiFunction<Map<String, String>, String, Map<String, String>> filterMapElementsByName = (inputParamMap, searchName) -> inputParamMap.entrySet().stream()
            .filter(kvp -> kvp.getKey().contains(searchName))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));

    public static void main(String[] args) {
        Utils.printHTMLHeaders();
        Utils.printHTMLBody(BROWSE_CAKES_HTML);

        //if its get request
        if (!Utils.postRequestValidator(System.getenv(Utils.REQUEST_METHOD))) {
            Map<String, String> namePriceMap = Utils.readFromCSVFile();
            String searchedCakeName = Utils.generateParams(System.getenv(Utils.QUERY_STRING)).values().stream().collect(Collectors.toList()).get(0);
            namePriceMap = Browse.filterWanted(namePriceMap, searchedCakeName);
            List<Cake> foundCakes = Utils.deserializeMapToCakeList(namePriceMap);
            printCakes(foundCakes);
        }
    }

    private static void printCakes(List<Cake> foundCakes) {
        for (Cake foundCake : foundCakes) {
            System.out.println("<p>Name: " + foundCake.getName() + " Price: " + foundCake.getPrice() + "</p>\n");
        }
    }

    private static Map<String, String> filterWanted(Map<String, String> namePriceMap, String searchName) {
        return filterMapElementsByName.apply(namePriceMap, searchName);
    }
}
