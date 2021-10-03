/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;

/**
 *
 * @author Grzegorz
 */
public class LottoTylkoParzysteMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoTylkoParzysteMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań z pliku
        List<LottoResult> lottoResults = ((XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class))).getLottoResults();
        LOGGER.info("wczytałem dane z pliku: " + XML_LOTTO_RESULTS + ", " + lottoResults.size() + " wierszy");

        List<String> allEvenResults = new ArrayList<>();
        List<String> allOddResults = new ArrayList<>();

        for (LottoResult lottoResult : lottoResults) {
            if (isEven6(lottoResult.getValue())) {
                allEvenResults.add(lottoResult.getDrawDate().format(DateTimeFormatter.ISO_DATE) + ": " + lottoResult.getValue());
            }
        }

        for (LottoResult lottoResult : lottoResults) {
            if (isOdd6(lottoResult.getValue())) {
                allOddResults.add(lottoResult.getDrawDate().format(DateTimeFormatter.ISO_DATE) + ": " + lottoResult.getValue());
            }
        }

        LOGGER.info("parzyste 6: " + allEvenResults.size());
        for (String allEvenResult : allEvenResults) {
            LOGGER.info(allEvenResult);
        }

        LOGGER.info("nieparzyste 6: " + allOddResults.size());
        for (String allOddResult : allOddResults) {
            LOGGER.info(allOddResult);
        }

        LOGGER.info("done");

    }

    private static boolean isEven6(String value) {
        String[] tmp = value.split(",");
        int licznik = 0;
        for (String string : tmp) {
            if (0 == (Integer.parseInt(string) % 2)) {
                licznik++;
            }
        }
        return licznik == 6;
    }

    private static boolean isOdd6(String value) {
        String[] tmp = value.split(",");
        int licznik = 0;
        for (String string : tmp) {
            if (0 != (Integer.parseInt(string) % 2)) {
                licznik++;
            }
        }
        return licznik == 6;
    }

}
