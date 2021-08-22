/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.xml.model;

import java.time.LocalDateTime;

/**
 *
 * @author Grzegorz
 */
public interface DrawResult {
    
    LocalDateTime getDrawDate();
    
    String getValue();
    
}
