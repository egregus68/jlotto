/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.service.XmlService;
import pl.gregus.jlotto.xml.model.Szybkie600Result;
import pl.gregus.jlotto.xml.model.XmlSzybkie600Results;

/**
 *
 * @author Grzegorz
 */
public class JacksonListToJsonMain {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonListToJsonMain.class);
    
    private static final String XML_SZYBKIE600_RESULTS = "szybkie600-results.xml";

    /**
     * @param args the command line arguments
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public static void main(String[] args) throws JsonProcessingException {
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        List< String> progLangs = new ArrayList<>();
        progLangs.add("C");
        progLangs.add("C++");
        progLangs.add("Java");
        progLangs.add("Java EE");
        progLangs.add("Python");
        progLangs.add("Scala");
        progLangs.add("JavaScript");
        // Serialize Object to JSON.
        String json = mapper.writeValueAsString(progLangs);

        // Print json
        System.out.println(json);
        
        ObjectMapper mapper2 = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        Map< String, Integer> days = new HashMap<>();
        days.put("MON", Calendar.MONDAY);
        days.put("TUE", Calendar.TUESDAY);
        days.put("WED", Calendar.WEDNESDAY);
        days.put("THU", Calendar.THURSDAY);
        days.put("FRI", Calendar.FRIDAY);
        days.put("SAT", Calendar.SATURDAY);
        days.put("SUN", Calendar.SUNDAY);
        
        String json2 = mapper2.writeValueAsString(days);
        System.out.println(json2);
        
        XmlService xmlService = new XmlService();

        // pobrać wyniki losowań
        List<Szybkie600Result> szybkie600Results = ((XmlSzybkie600Results) (xmlService.read(XML_SZYBKIE600_RESULTS, XmlSzybkie600Results.class))).getSzybkie600Results();
        LOGGER.info("wczytałem " + XML_SZYBKIE600_RESULTS + ", " + szybkie600Results.size() + " wierszy");
        
        ObjectMapper mapper3 = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        
        ObjectWriter writer = mapper3.writer(new DefaultPrettyPrinter().withoutSpacesInObjectEntries());
        
        try {
            writer.writeValue(Paths.get("szybkie600-results.json").toFile(), szybkie600Results);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        
    }
    
}
