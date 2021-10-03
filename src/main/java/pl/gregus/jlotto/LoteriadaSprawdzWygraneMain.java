/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.file.LoteriadaFile;

/**
 *
 * @author Grzegorz
 */
public class LoteriadaSprawdzWygraneMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoteriadaSprawdzWygraneMain.class);

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        LoteriadaFile loteriadaFile = new LoteriadaFile();
        Set<String> kupony = loteriadaFile.getCoupons("loteriada-2021-kupony.csv");
        LOGGER.info("Liczba kuponów: " + kupony.size());
        Set<String> kuponyZwycieskie = loteriadaFile.getCouponsOfWinners("loteriada-2021-lista-zwyciezcow.csv");
        LOGGER.info("Liczba kuponów zwycięskich: " + kuponyZwycieskie.size());
        
        Set<String> kuponyTrafione = new HashSet(kupony);
        kuponyTrafione.retainAll(kuponyZwycieskie);

        if (!kuponyTrafione.isEmpty()) {
            LOGGER.info("Kupon(y) wylosowany(e):");
            kuponyTrafione.forEach(System.out::println);
        } else {
            LOGGER.info("BRAK TRAFIONYCH KUPONÓW!");
        }
        LOGGER.info("Liczba kuponów o tzw. weryfikacji negatywnej: " + loteriadaFile.getCountNegativeVerification("loteriada-2021-lista-zwyciezcow.csv"));
        
        LOGGER.info("Done");

    }

}
