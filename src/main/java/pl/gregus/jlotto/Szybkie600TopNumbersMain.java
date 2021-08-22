/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.Szybkie600Result;
import pl.gregus.jlotto.xml.model.XmlLottoResults;
import pl.gregus.jlotto.xml.model.XmlSzybkie600Results;

/**
 *
 * @author Grzegorz
 */
public class Szybkie600TopNumbersMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(Szybkie600TopNumbersMain.class);

    private static final String XML_SZYBKIE600_RESULTS = "szybkie600-results.xml";
    
    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("1939-01-02T00:00:00");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlSzybkie600Results xmlDrawResult = (XmlSzybkie600Results) (xmlService.read(XML_SZYBKIE600_RESULTS, XmlSzybkie600Results.class));
        LOGGER.info("wczytałem " + XML_SZYBKIE600_RESULTS + ", " + xmlDrawResult.getSzybkie600Results().size() + " wierszy");

        List<Szybkie600Result> szybkie600Results = new ArrayList<>();

        xmlDrawResult.getSzybkie600Results().stream().filter(szybkie600Result -> (szybkie600Result.getDrawDate().isAfter(DATE_COMPARE.minusDays(1)))).forEachOrdered(szybkie600Result -> {
            szybkie600Results.add(szybkie600Result);
        });

        Map<String, String> rawResult = new HashMap<>();
        Set<String> setResults = new HashSet<>();
        List<String> listResults = new ArrayList<>();

        // częstotliwość losowania danej liczby
        Map<Integer, String> mapFinalResults = new TreeMap<>();
        mapFinalResults.put(0, "LICZBA;PROCENT;LICZBA_WYSTAPIEN;LICZBA_LOSOWAN");
        for (int x = 1; x < 50; x++) {
            List<String> tmpResults = new ArrayList<>();
            for (Szybkie600Result szybkie600Result : szybkie600Results) {
                List<String> convertedList = Stream.of(szybkie600Result.getValue().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                for (String string : convertedList) {
                    if ((String.valueOf(x)).equals(string)) {
                        tmpResults.add(string);
                    }
                }
            }
            mapFinalResults.put(x, String.valueOf(x) + ";" + Utils.round(((double) tmpResults.size() / (double) szybkie600Results.size()), 4) + ";" + tmpResults.size() + ";" + szybkie600Results.size());
        }

        szybkie600Results.stream().map(drawResult -> Stream.of(drawResult.getValue().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList())).forEachOrdered(iResults -> {
            listResults.add("" + (Collections.max(iResults) - Collections.min(iResults)));            
        });

        listResults.forEach(string -> {
            setResults.add(string);
        });

        setResults.forEach(setResult -> {
            int licznik = 0;
            licznik = listResults.stream().filter(string -> (setResult.equalsIgnoreCase(string))).map(_item -> 1).reduce(licznik, Integer::sum);
            rawResult.put(setResult, "" + licznik);
        });

        // uporządkowanie wyników
        Map<String, String> sortedResult = new TreeMap<>(rawResult);

        // wyświetlenie wyników
        System.out.println("");
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
