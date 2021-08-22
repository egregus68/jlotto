/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class ContentAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentAnalyzer.class);

    private String baseUrl;

    private final DrawType drawType;

    private final Integer resultNumber;

    private final LocalDateTime date;

    private static final Pattern REGEXP_DRAW_RESULT = Pattern.compile("[ ][0-9]{1,2}[ ][0-9]{1,2}[ ][0-9]{1,2}[ ][0-9]{1,2}[ ][0-9]{1,2}[ ][0-9]{1,2}$");

    public ContentAnalyzer(DrawType drawType, int resultNumber) {
        this.drawType = drawType;
        this.resultNumber = resultNumber;
        this.date = null;
    }

    public ContentAnalyzer(DrawType drawType, LocalDateTime date) {
        this.drawType = drawType;
        this.resultNumber = null;
        this.date = date;
    }

    public List<String> getResults() {

        setBaseUrl();

        int counter;

        Document document = new Document(this.baseUrl);
        try {
            String address = "";
            if (null != this.resultNumber) {
                address = this.baseUrl + "number," + this.resultNumber;
            } else if (null != this.date) {
                address = this.baseUrl + "date," + this.date.format(DateTimeFormatter.ISO_DATE) + (DrawType.SZYBKIE600 == this.drawType ? ",260" : ",10");
            }
            LOGGER.info("pobieram dane z url: " + address);
            document = Jsoup.connect(address).get();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);            
        }

        Elements elements = new Elements();

        Map<Integer, String> dates = new TreeMap<>();
        Map<Integer, String> results = new TreeMap<>();

        Elements dateElements = document.select("p.sg__desc-title");

        if (null != this.drawType) {
            switch (this.drawType) {
                case LOTTO ->
                    elements = document.select("div.recent-result-item.Lotto");
                case PLUS ->
                    elements = document.select("div.recent-result-item.LottoPlus");
                case SZYBKIE600 ->
                    elements = document.select("div.recent-result-item.Szybkie600");
            }
        }

        counter = 0;
        for (Element element : dateElements) {
            dates.put(counter++, element.text());
        }
        Map<Integer, String> formatedDates = getFormatedDates(dates);

        counter = 0;
        for (Element element : elements) {
            results.put(counter++, element.text());
        }
        Map<Integer, String> formatedResults = getFormatedResults(results);

        List<String> wyniki = new ArrayList();

        if (!formatedResults.isEmpty()) {
            for (Map.Entry<Integer, String> entry : formatedDates.entrySet()) {

                if (null != formatedResults.get(entry.getKey())) {
                    String[] tmp = formatedResults.get(entry.getKey()).split(";");

                    String tmp2 = tmp[0] + ";" + entry.setValue(this.drawType.getValue()) + ";" + tmp[1];
                    wyniki.add(tmp2);
                }
            }
        }
        return wyniki;
    }

    private void setBaseUrl() {
        switch (this.drawType) {
            case LOTTO ->
                this.baseUrl = Config.URL_LOTTO_PLUS;
            case PLUS ->
                this.baseUrl = Config.URL_LOTTO_PLUS;
            case SZYBKIE600 ->
                this.baseUrl = Config.URL_SZYBKIE600;
        }
    }

    private Map<Integer, String> getFormatedDates(Map<Integer, String> dataset) {
        Map<Integer, String> results = new TreeMap<>();
        dataset.entrySet().forEach(entry -> {
            String stringLDT = "";
            String[] tmp = entry.getValue().split(",");
            String[] a = tmp[1].trim().split("\\.");
            if (3 == tmp.length) {
                stringLDT = a[2] + "-" + a[1] + "-" + a[0] + "T" + tmp[2].replace("godz. ", "").trim() + ":00";
            } else if (2 == tmp.length) {
                stringLDT = a[2] + "-" + a[1] + "-" + a[0] + "T00:00:00";
            }
            results.put(entry.getKey(), stringLDT);
        });
        return results;
    }

    private Map<Integer, String> getFormatedResults(Map<Integer, String> dataset) {
        Map<Integer, String> results = new TreeMap<>();
        dataset.entrySet().forEach(entry -> {
            String tmp2 = "";
            String[] tmp = entry.getValue().split("\\s+");

            Matcher matcher2 = REGEXP_DRAW_RESULT.matcher(entry.getValue());

            while (matcher2.find()) {
                if (DrawType.LOTTO == this.drawType) {
                    tmp2 = tmp[3] + ";" + matcher2.group().trim().replace(" ", ",");
                } else if (DrawType.PLUS == this.drawType || DrawType.SZYBKIE600 == this.drawType) {
                    tmp2 = tmp[4] + ";" + matcher2.group().trim().replace(" ", ",");
                }
            }
            results.put(entry.getKey(), tmp2);
        });
        return results;
    }

}
