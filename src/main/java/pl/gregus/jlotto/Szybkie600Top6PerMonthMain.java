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
import pl.gregus.jlotto.xml.model.Szybkie600Result;
import pl.gregus.jlotto.xml.model.XmlSzybkie600Results;

/**
 *
 * @author ggusciora
 */
public class Szybkie600Top6PerMonthMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(Szybkie600Top6PerMonthMain.class);

    private static final String XML_SZYBKIE600_RESULTS = "szybkie600-results.xml";

    private static final LocalDateTime DATE_COMPARE_FROM = LocalDateTime.parse("2021-01-01T00:00:00");
    
    private static final LocalDateTime DATE_COMPARE_TO = LocalDateTime.parse("2021-08-01T00:00:00");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        XmlSzybkie600Results xmlDrawResult = (XmlSzybkie600Results) (xmlService.read(XML_SZYBKIE600_RESULTS, XmlSzybkie600Results.class));
        LOGGER.info("wczytałem " + XML_SZYBKIE600_RESULTS + ", " + xmlDrawResult.getSzybkie600Results().size() + " wierszy");

        // wybrać pozycje spełniające kryterium czasu
        LottoUtils<Szybkie600Result> lottoUtils = new LottoUtils<>(xmlDrawResult.getSzybkie600Results(), DATE_COMPARE_FROM, DATE_COMPARE_TO);

        // częstotliwość losowania danej liczby w każdym roku
        top6PerYear(lottoUtils.frequencyByMonth());

        LOGGER.info("Done");
    }

    

    

}
