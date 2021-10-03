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
import pl.gregus.jlotto.xml.model.PlusResult;
import pl.gregus.jlotto.xml.model.XmlLottoResults;
import pl.gregus.jlotto.xml.model.XmlPlusResults;

/**
 *
 * @author ggusciora
 */
public class PlusTop6PerYearMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlusTop6PerYearMain.class);

    private static final String XML_PLUS_RESULTS = "plus-results.xml";

    private static final LocalDateTime DATE_COMPARE_FROM = LocalDateTime.parse("2015-01-01T00:00:00");        

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlPlusResults xmlDrawResult = (XmlPlusResults) (xmlService.read(XML_PLUS_RESULTS, XmlPlusResults.class));
        LOGGER.info("wczytałem " + XML_PLUS_RESULTS + ", " + xmlDrawResult.getPlusResults().size() + " wierszy");

        // wybrać pozycje spełniające kryterium czasu
        LottoUtils<PlusResult> lottoUtils = new LottoUtils<>(xmlDrawResult.getPlusResults(), DATE_COMPARE_FROM, LocalDateTime.now());        
        
        // częstotliwość losowania danej liczby w każdym roku
        top6PerYear(lottoUtils.frequencyByYear());

        LOGGER.info("Done");
    }

    
}
