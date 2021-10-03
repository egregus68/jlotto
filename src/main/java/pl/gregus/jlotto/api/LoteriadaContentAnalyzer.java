/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.types.DrawType;

/**
 *
 * @author Grzegorz
 */
public class LoteriadaContentAnalyzer implements ContentAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoteriadaContentAnalyzer.class);

    private String baseUrl;

    private final DrawType drawType;

    public LoteriadaContentAnalyzer(DrawType drawType) {
        this.drawType = drawType;
    }

    @Override
    public List<String> getResults() {

        setBaseUrl();

        Document document = new Document(this.baseUrl);
        try {
            String address = this.baseUrl;
            LOGGER.info("pobieram dane z url: " + address);
            document = Jsoup.connect(address).get();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        Map<Integer, String> dates = new TreeMap<>();
        Map<Integer, String> results = new TreeMap<>();

        Elements elements = document.select("draw-item");

        List<String> wyniki = new ArrayList();

        wyniki.add("aaa");

        return wyniki;

    }

    @Override
    public void setBaseUrl() {
        switch (this.drawType) {
            case LOTERIADA ->
                this.baseUrl = Config.URL_LOTERIADA;
        }
    }

}
