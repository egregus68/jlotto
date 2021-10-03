/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.api;

import java.time.LocalDateTime;
import java.util.List;
import pl.gregus.jlotto.types.DrawType;

/**
 *
 * @author Grzegorz
 */
public class LottoApi {

    private final ContentAnalyzer contentAnalyzer;

    public LottoApi(DrawType drawType, int resultNumber) {
        this.contentAnalyzer = new LottoContentAnalyzer(drawType, resultNumber);
    }
    
    public LottoApi(DrawType drawType, LocalDateTime date) {
        this.contentAnalyzer = new LottoContentAnalyzer(drawType, date);
    }
    
    public LottoApi() {
        this.contentAnalyzer = new LoteriadaContentAnalyzer(DrawType.LOTERIADA);
    }

    public List<String> getResults() {
        return contentAnalyzer.getResults();
    }
}
