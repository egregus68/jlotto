/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.api.LottoApi;
import pl.gregus.jlotto.file.FileUtil;
import pl.gregus.jlotto.service.XmlService;
import pl.gregus.jlotto.types.DrawType;
import pl.gregus.jlotto.xml.model.Szybkie600Result;
import pl.gregus.jlotto.xml.model.XmlSzybkie600Results;

/**
 *
 * @author Grzegorz
 */
public class Szybkie600PobierzWynikiMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(Szybkie600PobierzWynikiMain.class);

    private static final String XML_SZYBKIE600_RESULTS = "szybkie600-results.xml";
    
    private static final LocalDateTime DATE_RESULTS = LocalDateTime.parse("2021-08-21T00:00:00");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        List<Szybkie600Result> szybkie600Results = ((XmlSzybkie600Results) (xmlService.read(XML_SZYBKIE600_RESULTS, XmlSzybkie600Results.class))).getSzybkie600Results();
        LOGGER.info("wczytałem dane z pliku: " + XML_SZYBKIE600_RESULTS + ", " + szybkie600Results.size() + " wierszy");

        List<String> allResults = new ArrayList<>();

//        for (int i = 147420; i <= 147490; i = i + 10) {
//            LottoApi lottoApi = new LottoApi(DrawType.SZYBKIE600, i);
//            LOGGER.info("pobieram dane dla numeru losowania: " + i);
//            List<String> results = lottoApi.getResults();
//            allResults.addAll(results);
//        }
        LottoApi lottoApi = new LottoApi(DrawType.SZYBKIE600, DATE_RESULTS);
        List<String> results = lottoApi.getResults();
        allResults.addAll(results);
        LOGGER.info("pobrano ze strony LOTTO: " + allResults.size() + " pozycji");

        int counter = 0;
        counter = allResults.stream()
                .map(allResult -> convert(allResult))
                .filter(szybkie600Result -> (!szybkie600Results.contains(szybkie600Result)))
                .map(szybkie600Result -> {
                    szybkie600Results.add(szybkie600Result);
                    return szybkie600Result;
                }).map(item -> 1).reduce(counter, Integer::sum);

        LOGGER.info("dopisano do pliku: " + counter + " pozycji");

        szybkie600Results.sort(Comparator.comparing(Szybkie600Result::getId));

        FileUtil fileUtil = new FileUtil();
        LocalDateTime ldt = LocalDateTime.now();
        String fileName = "szybkie600-results-" + ldt.format(DateTimeFormatter.ISO_DATE_TIME).replace(":", "-");
        fileUtil.writeToText(allResults, fileName + ".txt");
        XmlSzybkie600Results xmlSzybkie600Results = new XmlSzybkie600Results();
        xmlSzybkie600Results.setSzybkie600Results(szybkie600Results);
        if (!(0 == counter)) {
            xmlService.write(fileName + ".xml", xmlSzybkie600Results);
            LOGGER.info("zapisano w pliku: " + fileName + ".xml");
        }
    }

    private static Szybkie600Result convert(String oneSzybkie600Result) {
        Szybkie600Result szybkie600Result = new Szybkie600Result();
        String[] tmp = oneSzybkie600Result.split(";");
        szybkie600Result.setId(Integer.parseInt(tmp[0]));
        szybkie600Result.setDrawDate(LocalDateTime.parse(tmp[1], DateTimeFormatter.ISO_DATE_TIME));
        szybkie600Result.setValue(tmp[2]);
        return szybkie600Result;
    }

}
