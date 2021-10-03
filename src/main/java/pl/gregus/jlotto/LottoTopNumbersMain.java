/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import static pl.gregus.jlotto.utils.Combinations.top6;
import pl.gregus.jlotto.utils.Utils;
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;

/**
 *
 * @author ggusciora
 */
public class LottoTopNumbersMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoTopNumbersMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";

    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("1939-01-02T00:00:00");

    private static final String XLS_FILE_NAME = "lotto-wynik.xlsx";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlLottoResults xmlDrawResult = (XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class));
        LOGGER.info("wczytałem " + XML_LOTTO_RESULTS + ", " + xmlDrawResult.getLottoResults().size() + " wierszy");

        List<LottoResult> lottoResults = new ArrayList<>();

        xmlDrawResult.getLottoResults().stream().filter(lottoResult -> (lottoResult.getDrawDate().isAfter(DATE_COMPARE.minusDays(1)))).forEachOrdered(lottoResult -> {
            lottoResults.add(lottoResult);
        });

        // częstotliwość losowania danej liczby
        Map<Integer, String> mapFinalResults = new TreeMap<>();
        mapFinalResults.put(0, "LICZBA;PROCENT;LICZBA_WYSTAPIEN;LICZBA_LOSOWAN");
        for (int x = 1; x < 50; x++) {
            List<String> tmpResults = new ArrayList<>();
            for (LottoResult lottoResult : lottoResults) {
                List<String> convertedList = Stream.of(lottoResult.getValue().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                for (String string : convertedList) {
                    if ((String.valueOf(x)).equals(string)) {
                        tmpResults.add(string);
                    }
                }
            }
            mapFinalResults.put(x, String.valueOf(x) + ";" + Utils.round(((double) tmpResults.size() / (double) lottoResults.size()), 4) + ";" + tmpResults.size() + ";" + lottoResults.size());
        }

        // zapisz do excela
//        ExcelAction excelAction = new ExcelAction(mapFinalResults);
//        excelAction.save(XLS_FILE_NAME);
        
        top6(mapFinalResults);

        LOGGER.info("Done");
    }

}
