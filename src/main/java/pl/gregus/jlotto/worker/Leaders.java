/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.worker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import static pl.gregus.jlotto.utils.Combinations.findIntegerTopK;
import pl.gregus.jlotto.xml.model.DrawResult;

/**
 *
 * @author Grzegorz
 * @param <T>
 */
public class Leaders<T extends DrawResult> {

    private final List<T> drawResults;

    private final Set<String> combinations;

    private final LocalDateTime fromDate;

//    private final int thresold;

    private final Map<String, Integer> leaders = new LinkedHashMap<>();

    public Leaders(List<T> drawResults, Set<String> combinations, LocalDateTime fromDate) {
        this.drawResults = drawResults;
        this.combinations = combinations;
        this.fromDate = fromDate;
    }

    public Map<String, Integer> getLeaders() {
        return this.leaders;
    }

    public void generateLeadres() {

        Map<String, Integer> results = new HashMap<>();

        // dostosowanie danych źródłowych do analizy
        List<String> sourceDatas = new ArrayList<>();
        this.drawResults.stream().filter(drawResult -> (!drawResult.getDrawDate().isBefore(this.fromDate))).forEachOrdered(drawResult -> {
            sourceDatas.add("," + drawResult.getValue() + ",");
        });

        // inicjalizacja tablicy wystąpień
        this.combinations.forEach(combination -> {
            results.put(combination, 0);
        });

        // zlicznie wystąpień
        results.entrySet().forEach(entry -> {
            sourceDatas.stream().filter(dana -> (dana.contains(entry.getKey()))).forEachOrdered(_item -> {
                entry.setValue(entry.getValue() + 1);
            });
        });

        TreeSet<Integer> thresholdSet = new TreeSet<>(results.values());
        List<Integer> thresholdList = List.copyOf(thresholdSet);
        List<Integer> sortedTopK = findIntegerTopK(thresholdList, (thresholdList.size() > 4 ? 3 : 2));
        Collections.sort(sortedTopK);        

        // usunięcie mniej znaczących danych (poniżej progu występowania)
        results.values().removeIf(value -> value < sortedTopK.get(0));

        // uporządkowanie elementów od najwiekszego do najmniejszego
        results.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> this.leaders.put(x.getKey(), x.getValue()));

    }

    public List<String> getLeadersFormated() {

        List<String> result = new ArrayList<>();
        this.leaders.entrySet().stream().forEach(entry -> {
            String tmp = "";
            for (T drawResult : this.drawResults) {
                if (("," + drawResult.getValue() + ",").contains(entry.getKey()) && !drawResult.getDrawDate().isBefore(this.fromDate)) {
                    tmp = tmp + ";" + drawResult.getDrawDate().format(DateTimeFormatter.ISO_DATE);
                }
            }
            result.add(entry.getKey().substring(1, entry.getKey().length() - 1) + ";" + entry.getValue() + tmp);
        });

        return result;
    }

    
    
}
