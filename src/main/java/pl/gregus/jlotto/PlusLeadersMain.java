/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import static pl.gregus.jlotto.utils.Combinations.generate;
import static pl.gregus.jlotto.utils.Combinations.nameOfCombination;
import pl.gregus.jlotto.worker.Leaders;
import pl.gregus.jlotto.xml.model.PlusResult;
import pl.gregus.jlotto.xml.model.XmlPlusResults;

/**
 *
 * @author Grzegorz
 */
public class PlusLeadersMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlusLeadersMain.class);

    private static final String XML_PLUS_RESULTS = "plus-results.xml";

    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("2001-01-01T00:00:00");

    private static final int LENGTH_OF_COMBINATION = 4;

    private static final int LENGTH_OF_SET = 49;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        List<PlusResult> lottoResults = ((XmlPlusResults) (xmlService.read(XML_PLUS_RESULTS, XmlPlusResults.class))).getPlusResults();
        LOGGER.info("wczytałem " + XML_PLUS_RESULTS + ", " + lottoResults.size() + " wierszy");

        Set<String> kombinacje = generate(LENGTH_OF_SET, LENGTH_OF_COMBINATION);
        LOGGER.info("wygenerowano wszystkie kombinacje " + nameOfCombination(LENGTH_OF_COMBINATION) + ": " + kombinacje.size());

        Leaders<PlusResult> leadres = new Leaders<>(lottoResults, kombinacje, DATE_COMPARE);

        LOGGER.info("wyznaczenie liderów " + nameOfCombination(LENGTH_OF_COMBINATION) + " ...");
        leadres.generateLeadres();

        LOGGER.info("drukowanie wyników ...");
        for (String string : leadres.getLeadersFormated()) {
            System.out.println(string);
        }

        LOGGER.info("Koniec -----------------");
    }

}
