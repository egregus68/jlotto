/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ggusciora
 */
public class ListFilesInDirectory {
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ListFilesInDirectory.class);
    
    private final String path;
    private final boolean recursive;
    
    public ListFilesInDirectory(String path, boolean recursive) {
        this.path = path;
        this.recursive = recursive;
    }
    
    public Collection<String> getFiles() {
        List<String> collect = null;
        Path start = Paths.get(this.path);
        Integer maxDepth = 1;
        if (this.recursive) {
            maxDepth = Integer.MAX_VALUE;
        }        
        
        try (Stream<Path> stream = Files.walk(start, maxDepth).filter(Files::isRegularFile)) {
            collect = stream
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return collect;
    }
    
}
