/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import static pl.gregus.jlotto.utils.Combinations.top6;
import pl.gregus.jlotto.utils.Utils;
import pl.gregus.jlotto.xml.model.PlusResult;
import pl.gregus.jlotto.xml.model.XmlPlusResults;

/**
 *
 * @author ggusciora
 */
public class PlusTopNumbersMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlusTopNumbersMain.class);

    private static final String XML_PLUS_RESULTS = "plus-results.xml";

    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("1939-01-02T00:00:00");

    private static final String XLS_FILE_NAME = "plus-wynik.xlsx";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlPlusResults xmlDrawResult = (XmlPlusResults) (xmlService.read(XML_PLUS_RESULTS, XmlPlusResults.class));
        LOGGER.info("wczytałem " + XML_PLUS_RESULTS + ", " + xmlDrawResult.getPlusResults().size() + " wierszy");

        List<PlusResult> plusResults = new ArrayList<>();

        xmlDrawResult.getPlusResults().stream().filter(plusResult -> (plusResult.getDrawDate().isAfter(DATE_COMPARE.minusDays(1)))).forEachOrdered(plusResult -> {
            plusResults.add(plusResult);
        });

        Map<String, String> rawResults = new HashMap<>();
        Set<String> setResults = new HashSet<>();
        List<String> listResults = new ArrayList<>();

        // częstotliwość losowania danej liczby
        Map<Integer, String> mapFinalResults = new TreeMap<>();
        mapFinalResults.put(0, "LICZBA;PROCENT;LICZBA_WYSTAPIEN;LICZBA_LOSOWAN");
        for (int x = 1; x < 50; x++) {
            List<String> tmpResults = new ArrayList<>();
            for (PlusResult plusResult : plusResults) {
                List<String> convertedList = Stream.of(plusResult.getValue().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                for (String string : convertedList) {
                    if ((String.valueOf(x)).equals(string)) {
                        tmpResults.add(string);
                    }
                }
            }
            mapFinalResults.put(x, String.valueOf(x) + ";" + Utils.round(((double) tmpResults.size() / (double) plusResults.size()), 4) + ";" + tmpResults.size() + ";" + plusResults.size());
        }

        plusResults.stream().map(drawResult -> Stream.of(drawResult.getValue().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList())).forEachOrdered(iResults -> {
            //LOGGER.info(drawResult.getValue() + "@" + Collections.min(iResults) + "|" + Collections.max(iResults) + "@" + (Collections.max(iResults) - Collections.min(iResults)));
            listResults.add("" + (Collections.max(iResults) - Collections.min(iResults)));
        });

        listResults.forEach(string -> {
            setResults.add(string);
        });

//        LOGGER.info("" + setResults.size());
        setResults.forEach(setResult -> {
            int licznik = 0;
            licznik = listResults.stream().filter(string -> (setResult.equalsIgnoreCase(string))).map(_item -> 1).reduce(licznik, Integer::sum);
            rawResults.put(setResult, "" + licznik);
        });

        // uporządkowanie wyników
        Map<String, String> sortedResult = new TreeMap<>(rawResults);

        // wyświetlenie wyników
        sortedResult.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + ";" + entry.getValue());
        });

        // zapisz do excela
//        ExcelAction excelAction = new ExcelAction(mapFinalResults);
//        excelAction.save(XLS_FILE_NAME);

        top6(mapFinalResults);

        LOGGER.info("Done");
    }


}
