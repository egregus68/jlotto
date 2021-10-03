/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;

/**
 *
 * @author ggusciora
 */
public class LottoCheckMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoCheckMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";

    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("1939-01-02T00:00:00");

    private static final String ALICJA_NUMBERS = "4,13,28,30,35,41";
    private static final String OLA_NUMBERS = "5,8,9,10,13,24";
    private static final String ANIA_NUMBERS = "6,13,23,30,41,49";
    private static final String SZYMON_NUMBERS = "9,11,21,25,36,44";
    private static final String MARIA_NUMBERS = "3,7,12,18,46,49";
    private static final String GRZEGORZ_NUMBERS = "7,12,23,29,38,44";

    private static final String MY_NUMBERS = "1,7,21,23,32,42";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        Pattern pattern = Pattern.compile(",");

        // pobrać wyniki losowań
        XmlLottoResults xmlDrawResult = (XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class));
        LOGGER.info("wczytałem " + XML_LOTTO_RESULTS + ", " + xmlDrawResult.getLottoResults().size() + " wierszy");

        List<LottoResult> lottoResults = new ArrayList<>();

        xmlDrawResult.getLottoResults().stream().filter(lottoResult -> (lottoResult.getDrawDate().isAfter(DATE_COMPARE.minusDays(1)))).forEachOrdered(lottoResult -> {
            lottoResults.add(lottoResult);
        });

        Set<Integer> mySet = new TreeSet<>(pattern.splitAsStream(MY_NUMBERS)
                .map(Integer::valueOf)
                .collect(Collectors.toList()));

        lottoResults.forEach(lottoResult -> {
            Set<Integer> drawSet = new TreeSet<>(pattern.splitAsStream(lottoResult.getValue())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList()));
            drawSet.retainAll(mySet);
            if (!drawSet.isEmpty() && 3 < drawSet.size()) {
                LOGGER.info(drawSet.size() + "; " + drawSet.toString() + ": " + lottoResult.getDrawDate().format(DateTimeFormatter.ISO_DATE));
            }
        });

        LOGGER.info("Done");
    }

}
