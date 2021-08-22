/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.setup;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Grzegorz
 */
public class LoggerSetup {

    private final String logFile;

    public LoggerSetup(String path) {
        this.logFile = path;
    }

    public void setup() {

        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%m%n";
        layout.setConversionPattern(conversionPattern);

        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile(this.logFile);
        fileAppender.setLayout(layout);

        fileAppender.activateOptions();
        org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);

        rootLogger.addAppender(fileAppender);
    }

}
