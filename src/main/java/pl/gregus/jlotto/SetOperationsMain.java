/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author ggusciora
 */
public class SetOperationsMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Set<String> setA = setOf("a", "b", "c", "d");
        Set<String> setB = setOf("b", "c", "e", "f");

        Set<String> intersect = new HashSet(setA);
        intersect.retainAll(setB);
        System.out.println("INTERSECT ... A and B");
        intersect.forEach(System.out::println);
        assertEquals(intersect, setOf("b", "c"));

        Set<String> union = new HashSet(setA);
        union.addAll(setB);
        System.out.println("UNION ... A + B");
        union.forEach(System.out::println);
        assertEquals(union, setOf("a", "b", "c", "d", "e", "f"));

        Set<String> minusAB = new HashSet(setA);
        minusAB.removeAll(setB);
        System.out.println("MINUS ... A - B");
        minusAB.forEach(System.out::println);
        assertEquals(minusAB, setOf("a", "d"));

        Set<String> minusBA = new HashSet(setB);
        minusBA.removeAll(setA);
        System.out.println("MINUS ... B - A");
        minusBA.forEach(System.out::println);
        assertEquals(minusBA, setOf("e", "f"));

    }

    private static Set<String> setOf(String... values) {
        return new HashSet<>(Arrays.asList(values));
    }

}
