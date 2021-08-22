/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import pl.gregus.jlotto.xml.model.DrawResult;

/**
 *
 * @author Grzegorz
 * @param <T>
 */
public final class LottoUtils<T extends DrawResult> {

    private List<T> t;

    private LocalDateTime from;

    private LocalDateTime to;

    public LottoUtils(List<T> t, LocalDateTime from, LocalDateTime to) {
        this.t = filterByTime(t, from, to);
        this.from = from;
        this.to = to;
    }

    public List<T> get() {
        return t;
    }

    public void set(List<T> t) {
        this.t = t;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    private List<T> filterByTime(List<T> input, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<T> result = new ArrayList<>();
        for (T t : input) {
            if (!t.getDrawDate().isBefore(dateFrom) && !t.getDrawDate().isAfter(dateTo)) {
                result.add(t);
            }
        }
        return result;
    }

    public Map<Integer, Map<Integer, String>> frequencyByYear() {
        Map<Integer, Map<Integer, String>> mapFinalResults = new TreeMap<>();
        for (T lottoResult : this.t) {
            if (!mapFinalResults.containsKey(lottoResult.getDrawDate().getYear())) {
                mapFinalResults.put(lottoResult.getDrawDate().getYear(), new TreeMap<>());
            }
        }

        for (Map.Entry<Integer, Map<Integer, String>> entry : mapFinalResults.entrySet()) {
            for (int x = 1; x <= 49; x++) {
                List<String> tmpResults = new ArrayList<>();
                int counter = 0;
                for (T t : this.t) {
                    if (entry.getKey() == t.getDrawDate().getYear()) {
                        counter++;
                        List<String> convertedList = Arrays.asList(t.getValue().split(","));
                        for (String string : convertedList) {
                            if ((String.valueOf(x)).equals(string)) {
                                tmpResults.add(string);
                            }
                        }
                    }
                }
                entry.getValue().put(x, String.valueOf(x) + ";" + Utils.round(((double) tmpResults.size() / (double) counter), 4) + ";" + tmpResults.size() + ";" + counter);
            }
        }
        return mapFinalResults;
    }

    public Map<Integer, Map<Integer, String>> frequencyByMonth() {
        Map<Integer, Map<Integer, String>> mapFinalResults = new TreeMap<>();
        for (T lottoResult : this.t) {
            Integer key = Integer.parseInt("" + lottoResult.getDrawDate().getYear() + String.format("%02d", lottoResult.getDrawDate().getMonthValue()));
            if (!mapFinalResults.containsKey(key)) {
                mapFinalResults.put(key, new TreeMap<>());
            }
        }

        for (Map.Entry<Integer, Map<Integer, String>> entry : mapFinalResults.entrySet()) {
            for (int x = 1; x <= 49; x++) {
                List<String> tmpResults = new ArrayList<>();
                int counter = 0;
                for (T lottoResult : this.t) {
                    Integer key = Integer.parseInt("" + lottoResult.getDrawDate().getYear() + String.format("%02d", lottoResult.getDrawDate().getMonthValue()));
                    if (key.equals(entry.getKey())) {
                        counter++;
                        List<String> convertedList = Arrays.asList(lottoResult.getValue().split(","));
                        for (String string : convertedList) {
                            if ((String.valueOf(x)).equals(string)) {
                                tmpResults.add(string);
                            }
                        }
                    }
                }
                entry.getValue().put(x, String.valueOf(x) + ";" + Utils.round(((double) tmpResults.size() / (double) counter), 4) + ";" + tmpResults.size() + ";" + counter);
            }
        }
        return mapFinalResults;
    }
}
