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
import pl.gregus.jlotto.xml.model.Szybkie600Result;
import pl.gregus.jlotto.xml.model.XmlSzybkie600Results;

/**
 *
 * @author Grzegorz
 */
public class Szybkie600LeadersMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(Szybkie600LeadersMain.class);

    private static final String XML_SZYBKIE600_RESULTS = "szybkie600-results.xml";

    private static final LocalDateTime DATE_COMPARE = LocalDateTime.parse("2001-01-01T00:00:00");

    private static final int LENGTH_OF_COMBINATION = 4;

    private static final int LENGTH_OF_SET = 32;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        List<Szybkie600Result> szybkie600Results = ((XmlSzybkie600Results) (xmlService.read(XML_SZYBKIE600_RESULTS, XmlSzybkie600Results.class))).getSzybkie600Results();
        LOGGER.info("wczytałem " + XML_SZYBKIE600_RESULTS + ", " + szybkie600Results.size() + " wierszy");

        Set<String> kombinacje = generate(LENGTH_OF_SET, LENGTH_OF_COMBINATION);
        LOGGER.info("wygenerowano wszystkie kombinacje " + nameOfCombination(LENGTH_OF_COMBINATION) + ": " + kombinacje.size());

        Leaders<Szybkie600Result> leadres = new Leaders<>(szybkie600Results, kombinacje, DATE_COMPARE);

        LOGGER.info("wyznaczenie liderów " + nameOfCombination(LENGTH_OF_COMBINATION) + " ...");
        leadres.generateLeadres();

        LOGGER.info("drukowanie wyników ...");
        for (String string : leadres.getLeadersFormated()) {
            System.out.println(string);
        }

        LOGGER.info("Koniec -----------------");
    }

}
