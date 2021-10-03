/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.file.LoteriadaFile;

/**
 *
 * @author Grzegorz
 */
public class LoteriadaListaZwyciezcowMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoteriadaListaZwyciezcowMain.class);

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        LoteriadaFile loteriadaFile = new LoteriadaFile();
        loteriadaFile.saveListWinners("loteriada-2021-lista-zwyciezcow-surowe-dane-ze-strony.txt", "loteriada-2021-lista-zwyciezcow.csv");
        
        LOGGER.info("done");
    }

}
