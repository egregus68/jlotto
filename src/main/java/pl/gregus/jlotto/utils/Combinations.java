/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 *
 * @author Grzegorz
 */
public class Combinations {

    public static Set<String> generate(int n, int r) {
        Set<String> result = new TreeSet<>();
        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(n, r);
        while (iterator.hasNext()) {
            final int[] combination = iterator.next();
            String tmp = ",";
            for (int i : combination) {
                tmp = tmp + (i + 1) + ",";
            }
            result.add(tmp);
        }
        return result;
    }

    public static String nameOfCombination(int k) {
        switch (k) {
            case 3 -> {
                return "trójek";
            }
            case 4 -> {
                return "czwórek";
            }
            case 5 -> {
                return "piątek";
            }
            case 6 -> {
                return "szóstek";
            }
        }
        return "";
    }

    public static List<Double> findDoubleTopK(List<Double> input, int k) {
        PriorityQueue<Double> maxHeap = new PriorityQueue<>();

        input.forEach(number -> {
            maxHeap.add(number);

            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        });

        List<Double> topKList = new ArrayList<>(maxHeap);
        Collections.reverse(topKList);

        return topKList;
    }

    public static List<Integer> findIntegerTopK(List<Integer> input, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();

        input.forEach(number -> {
            maxHeap.add(number);

            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        });

        List<Integer> topKList = new ArrayList<>(maxHeap);
        Collections.reverse(topKList);

        return topKList;
    }

    public static void top6(Map<Integer, String> mapFinalResults) {
        List<Double> candidatesTop6 = new ArrayList<>();
        mapFinalResults.remove(0);
        for (Map.Entry<Integer, String> entry : mapFinalResults.entrySet()) {
            String[] tmp = entry.getValue().split(";");
            candidatesTop6.add(Double.parseDouble(tmp[1]));
        }

        List<Double> sortedTop6 = findDoubleTopK(candidatesTop6, 6);
        Collections.sort(sortedTop6, Collections.reverseOrder());

        TreeSet<String> resultset = new TreeSet<>();
        for (Double double1 : sortedTop6) {
            for (Map.Entry<Integer, String> entry : mapFinalResults.entrySet()) {
                if (entry.getValue().contains(double1.toString())) {
                    String[] tmp = entry.getValue().split(";");
                    resultset.add(String.format("%.4f", Double.parseDouble(tmp[1])) + ";" + String.format("%02d", Integer.parseInt(tmp[0])) + ";" + tmp[2] + ";" + tmp[3]);
                }
            }
        }

        Set<String> resultsetReverse = resultset.descendingSet();

        for (String string : resultsetReverse) {
            System.out.println(string);
        }

    }

    public static void top6PerYear(Map<Integer, Map<Integer, String>> mapFinalResults) {

        for (Map.Entry<Integer, Map<Integer, String>> entryset : mapFinalResults.entrySet()) {
            List<Double> candidatesTop6 = new ArrayList<>();
            System.out.println(entryset.getKey());

            for (Map.Entry<Integer, String> entry : entryset.getValue().entrySet()) {
                String[] tmp = entry.getValue().split(";");
                candidatesTop6.add(Double.parseDouble(tmp[1]));
            }

            List<Double> sortedTop6 = findDoubleTopK(candidatesTop6, 6);
            Collections.sort(sortedTop6, Collections.reverseOrder());

            TreeSet<String> resultset = new TreeSet<>();
            for (Double double1 : sortedTop6) {
                for (Map.Entry<Integer, String> entry : entryset.getValue().entrySet()) {
                    if (entry.getValue().contains(double1.toString())) {
                        String[] tmp = entry.getValue().split(";");
                        resultset.add(String.format("%.4f", Double.parseDouble(tmp[1])) + ";" + String.format("%02d", Integer.parseInt(tmp[0])) + ";" + tmp[2] + ";" + tmp[3]);
                    }
                }
            }

            Set<String> resultsetReverse = resultset.descendingSet();

            for (String string : resultsetReverse) {
                System.out.println(string);
            }

        }

    }

}
