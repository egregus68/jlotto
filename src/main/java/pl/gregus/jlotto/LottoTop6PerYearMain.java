/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import static pl.gregus.jlotto.utils.Combinations.top6PerYear;
import pl.gregus.jlotto.utils.LottoUtils;
import pl.gregus.jlotto.xml.model.LottoResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;

/**
 *
 * @author ggusciora
 */
public class LottoTop6PerYearMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoTop6PerYearMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";

    private static final LocalDateTime DATE_COMPARE_FROM = LocalDateTime.parse("2021-01-01T00:00:00");        

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlLottoResults xmlDrawResult = (XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class));
        LOGGER.info("wczytałem " + XML_LOTTO_RESULTS + ", " + xmlDrawResult.getLottoResults().size() + " wierszy");

        // wybrać pozycje spełniające kryterium czasu
        LottoUtils<LottoResult> lottoUtils = new LottoUtils<>(xmlDrawResult.getLottoResults(), DATE_COMPARE_FROM, LocalDateTime.now());        
        
        // częstotliwość losowania danej liczby w każdym roku
        top6PerYear(lottoUtils.frequencyByYear());

        LOGGER.info("Done");
    }

    
}
