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
public class LottoWyniki2SqlMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LottoWyniki2SqlMain.class);

    private static final String XML_LOTTO_RESULTS = "lotto-results.xml";
    
    private static final LocalDateTime DATE_RESULTS = LocalDateTime.now();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań z pliku
        List<LottoResult> lottoResults = ((XmlLottoResults) (xmlService.read(XML_LOTTO_RESULTS, XmlLottoResults.class))).getLottoResults();
        LOGGER.info("wczytałem dane z pliku: " + XML_LOTTO_RESULTS + ", " + lottoResults.size() + " wierszy");
        
        List<String> allResults = new ArrayList<>();
        
        for (LottoResult lottoResult : lottoResults) {
            System.out.println("INSERT INTO restlotto.lotto(id, draw_id, draw_date, draw_numbers) VALUES (" + lottoResult.getId() + ", " + lottoResult.getId() + ", '" + lottoResult.getDrawDate().format(DateTimeFormatter.ISO_DATE_TIME) + "', '" + lottoResult.getValue() + "');");
            
        }

        LOGGER.info("done");

    }

}
