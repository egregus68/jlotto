/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.api;

import java.util.List;

/**
 *
 * @author Grzegorz
 */
interface ContentAnalyzer {
    
    public void setBaseUrl();
    
    public List<String> getResults();
    
}
