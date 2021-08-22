/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author ggusciora
 */
public class CompareTwoBinaryFiles {

    public static boolean compare(final String fileNameOne, final String fileNameTwo) throws IOException {
        
        if (null == fileNameOne || fileNameOne.isEmpty()) {
            throw new IOException("First name of file for comparsion must NOT be NULL/EMPTY");
        }
        if (null == fileNameTwo || fileNameTwo.isEmpty()) {
            throw new IOException("Second name of file for comparsion must NOT be NULL/EMPTY");
        }
        
        final File fileOne = new File(FilenameUtils.normalize(fileNameOne));
        if (!fileOne.exists()) {
            throw new IOException("File not found: " + fileNameOne);
        }
        final File fileTwo = new File(FilenameUtils.normalize(fileNameTwo));
        if (!fileTwo.exists()) {
            throw new IOException("File not found: " + fileNameTwo);
        }

        return (Files.equal(fileOne, fileTwo));
    }

}
