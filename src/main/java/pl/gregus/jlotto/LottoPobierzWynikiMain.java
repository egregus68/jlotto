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
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;

/**
 *
 * @author Grzegorz
 */
public class LottoPobierzWynikiMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoPobierzWynikiMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";
    
    private static final LocalDateTime DATE_RESULTS = LocalDateTime.parse("2021-08-21T00:00:00");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań z pliku
        List<LottoResult> lottoResults = ((XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class))).getLottoResults();
        LOGGER.info("wczytałem dane z pliku: " + XML_LOTTO_RESULTS + ", " + lottoResults.size() + " wierszy");
        
        List<String> allResults = new ArrayList<>();

        LottoApi lottoApi = new LottoApi(DrawType.LOTTO, DATE_RESULTS);
        List<String> results = lottoApi.getResults();
        allResults.addAll(results);
        LOGGER.info("pobrano ze strony LOTTO: " + allResults.size() + " pozycji");

        int counter = 0;
        counter = allResults.stream()
                .map(allResult -> convert(allResult))
                .filter(lottoResult -> (!lottoResults.contains(lottoResult)))
                .map(lottoResult -> {
                    lottoResults.add(lottoResult);
                    return lottoResult;
                }).map(item -> 1).reduce(counter, Integer::sum);

        LOGGER.info("dopisano do pliku: " + counter + " pozycji");

        lottoResults.sort(Comparator.comparing(LottoResult::getId));

        FileUtil fileUtil = new FileUtil();
        LocalDateTime ldt = LocalDateTime.now();
        String fileName = "lotto-results-" + ldt.format(DateTimeFormatter.ISO_DATE_TIME).replace(":", "-");
        fileUtil.writeToText(allResults, fileName + ".txt");
        XmlLottoResults xmlLottoResults = new XmlLottoResults();
        xmlLottoResults.setLottoResults(lottoResults);
        if (!(0 == counter)) {
            xmlService.write(fileName + ".xml", xmlLottoResults);
            LOGGER.info("zapisano w pliku: " + fileName + ".xml");
        }
    }

    private static LottoResult convert(String oneLottoResult) {
        LottoResult lottoResult = new LottoResult();
        String[] tmp = oneLottoResult.split(";");
        lottoResult.setId(Integer.parseInt(tmp[0]));
        lottoResult.setDrawDate(LocalDateTime.parse(tmp[1], DateTimeFormatter.ISO_DATE_TIME));
        lottoResult.setValue(tmp[2]);
        return lottoResult;
    }

}
