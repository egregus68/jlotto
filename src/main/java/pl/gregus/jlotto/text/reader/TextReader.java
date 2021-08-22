/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.text.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ggusciora
 */
public class TextReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextReader.class);

    private final String fileName;
    private final boolean hasHeader;

    public TextReader(String fileName, boolean hasHeader) {
        this.fileName = fileName;
        this.hasHeader = hasHeader;
    }

    public Collection<String> read() {
        Collection<String> result = new ArrayList<>();
        int lineCounter = 0;
        File file = getFileFromResource(this.fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lineCounter++;
                if (this.hasHeader && lineCounter == 1) {
                    scanner.nextLine();
                } else if (this.hasHeader && lineCounter > 1) {
                    result.add(scanner.nextLine());
                } else {
                    result.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    private File getFileFromResource(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (null == resource) {
            throw new IllegalArgumentException("File is not found");
        } else {
            return new File(resource.getFile());
        }
    }

}
