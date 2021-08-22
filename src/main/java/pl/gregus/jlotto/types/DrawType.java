/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.types;

/**
 *
 * @author Grzegorz
 */
public enum DrawType {
    LOTTO("LOTTO"),
    PLUS("PLUS"),
    SZYBKIE600("SZYBKIE600");

    private final String value;

    private DrawType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }  
    
}
