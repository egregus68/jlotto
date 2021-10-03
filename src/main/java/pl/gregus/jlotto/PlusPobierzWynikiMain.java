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
import pl.gregus.jlotto.xml.model.PlusResult;
import pl.gregus.jlotto.xml.model.XmlPlusResults;

/**
 *
 * @author Grzegorz
 */
public class PlusPobierzWynikiMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlusPobierzWynikiMain.class);

    private static final String XML_PLUS_RESULTS = "plus-results.xml";
    
    private static final LocalDateTime DATE_RESULTS = LocalDateTime.now();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        List<PlusResult> plusResults = ((XmlPlusResults) (xmlService.read(XML_PLUS_RESULTS, XmlPlusResults.class))).getPlusResults();
        LOGGER.info("wczytałem dane z pliku: " + XML_PLUS_RESULTS + ", " + plusResults.size() + " wierszy");
                
        List<String> allResults = new ArrayList<>();

        LottoApi lottoApi = new LottoApi(DrawType.PLUS, DATE_RESULTS);
        List<String> results = lottoApi.getResults();
        allResults.addAll(results);
        LOGGER.info("pobrano ze strony LOTTO: " + allResults.size() + " pozycji");

        int counter = 0;
        counter = allResults.stream()
                .map(allResult -> convert(allResult))
                .filter(plusResult -> (!plusResults.contains(plusResult)))
                .map(plusResult -> {
                    plusResults.add(plusResult);
                    return plusResult;
                }).map(item -> 1).reduce(counter, Integer::sum);

        LOGGER.info("dopisano do pliku: " + counter + " pozycji");

        plusResults.sort(Comparator.comparing(PlusResult::getId));

        FileUtil fileUtil = new FileUtil();
        LocalDateTime ldt = LocalDateTime.now();
        String fileName = "plus-results-" + ldt.format(DateTimeFormatter.ISO_DATE_TIME).replace(":", "-");
        fileUtil.writeToText(allResults, fileName + ".txt");
        XmlPlusResults xmlPlusResults = new XmlPlusResults();
        xmlPlusResults.setPlusResults(plusResults);
        if (!(0 == counter)) {
            xmlService.write(fileName + ".xml", xmlPlusResults);
            LOGGER.info("zapisano w pliku: " + fileName + ".xml");
        }
    }

    private static PlusResult convert(String oneLottoResult) {
        PlusResult plusResult = new PlusResult();
        String[] tmp = oneLottoResult.split(";");
        plusResult.setId(Integer.parseInt(tmp[0]));
        plusResult.setDrawDate(LocalDateTime.parse(tmp[1], DateTimeFormatter.ISO_DATE_TIME));
        plusResult.setValue(tmp[2]);
        return plusResult;
    }

}
