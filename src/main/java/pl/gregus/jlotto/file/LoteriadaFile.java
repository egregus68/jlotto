/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.file;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

/**
 *
 * @author Grzegorz
 */
public class LoteriadaFile {

    public void saveListCoupons(String rawTxtInputFile, String csvOutputFile) throws IOException {
        Reader in = new FileReader(rawTxtInputFile, Charset.forName("UTF-8"));

        var csvFormatIn = CSVFormat.Builder.create()
                .setEscape('\\')
                .setIgnoreEmptyLines(true)
                .setQuote(null)
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL_NON_NULL)
                .setSkipHeaderRecord(true);

        CSVParser results = CSVParser.parse(in, csvFormatIn.build());

        var csvFormatOut = CSVFormat.Builder.create()
                .setTrim(true)
                .setQuote('"')
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL)
                .setHeader("kod promocyjny");

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputFile));
        CSVPrinter printer = new CSVPrinter(writer, csvFormatOut.build());

        for (CSVRecord record : results) {
            if (13 == record.get(0).length()) {
                printer.printRecord(record);
            }
        }
        printer.flush();
    }

    public void saveListWinners(String rawTxtInputFile, String csvOutputFile) throws IOException {
        Reader in = new FileReader(rawTxtInputFile, Charset.forName("UTF-8"));

        var csvFormatIn = CSVFormat.Builder.create()
                .setDelimiter('\t')
                .setEscape('\\')
                .setIgnoreEmptyLines(true)
                .setQuote(null)
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL_NON_NULL)
                .setSkipHeaderRecord(true)
                .setHeader("nagroda", "wylosowany kod promocyjny", "dane zwycięzcy");

        CSVParser results = CSVParser.parse(in, csvFormatIn.build());

        var csvFormatOut = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setTrim(true)
                .setQuote('"')
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL)
                .setHeader("nagroda", "wylosowany kod promocyjny", "dane zwycięzcy");

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputFile));
        CSVPrinter printer = new CSVPrinter(writer, csvFormatOut.build());

        for (CSVRecord record : results) {
            printer.printRecord(record);
        }
        printer.flush();
    }

    public Set<String> getCoupons(String csvInputFile) throws IOException {
        Set<String> coupons = new HashSet<>();

        Reader in = new FileReader(csvInputFile, Charset.forName("UTF-8"));

        var csvFormatIn = CSVFormat.Builder.create()
                .setTrim(true)
                .setQuote('"')
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL)
                .setSkipHeaderRecord(true)
                .setHeader("kod promocyjny");

        CSVParser results = CSVParser.parse(in, csvFormatIn.build());

        for (CSVRecord record : results) {
            coupons.add(record.get("kod promocyjny"));
        }

        return coupons;
    }

    public Set<String> getCouponsOfWinners(String csvInputFile) throws IOException {
        Set<String> coupons = new HashSet<>();

        Reader inB = new FileReader(csvInputFile, Charset.forName("UTF-8"));

        var csvFormatOut = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setTrim(true)
                .setQuote('"')
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL)
                .setSkipHeaderRecord(true)
                .setHeader("nagroda", "wylosowany kod promocyjny", "dane zwycięzcy");

        CSVParser results = CSVParser.parse(inB, csvFormatOut.build());

        for (CSVRecord record : results) {
            coupons.add(record.get("wylosowany kod promocyjny"));
        }

        return coupons;
    }

    public int getCountNegativeVerification(String csvInputFile) throws IOException {
        int counter = 0;

        Reader inB = new FileReader(csvInputFile, Charset.forName("UTF-8"));

        var csvFormatOut = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setTrim(true)
                .setQuote('"')
                .setRecordSeparator('\n')
                .setQuoteMode(QuoteMode.ALL)
                .setHeader("nagroda", "wylosowany kod promocyjny", "dane zwycięzcy");

        CSVParser results = CSVParser.parse(inB, csvFormatOut.build());

        for (CSVRecord record : results) {
            if ("WERYFIKACJA NEGATYWNA".equalsIgnoreCase(record.get("dane zwycięzcy"))) {
                counter++;
            }
        }

        return counter;
    }

}
