/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.api.LottoApi;
import pl.gregus.jlotto.types.DrawType;

/**
 *
 * @author Grzegorz
 */
public class LoteriadaPobierzWyniki2021Main {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoteriadaPobierzWyniki2021Main.class);
    
    private static final LocalDateTime DATE_RESULTS = LocalDateTime.parse("2021-08-26T00:00:00");
    
    public static void main(String[] args) {
        
        LottoApi lottoApi = new LottoApi();
        List<String> results = lottoApi.getResults();
        
        List<String> allResults = new ArrayList<>();
        
        allResults.addAll(results);
        LOGGER.info("pobrano ze strony LOTTO: " + allResults.size() + " pozycji");
        
        
    }
    
}
