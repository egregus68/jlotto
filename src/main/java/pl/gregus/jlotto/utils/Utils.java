/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Grzegorz
 */
public class Utils {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static Collection<String> read(String fileName) {
        Collection<String> values = new HashSet<>();
        File file = new File(fileName);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                values.add(line);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return values;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
