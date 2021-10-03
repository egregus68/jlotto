/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.xml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author ggusciora
 */
@XmlRootElement(name = "plusResults")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPlusResults {

    @XmlElement(name = "plusResult")
    private List<PlusResult> plusResults;

    public List<PlusResult> getPlusResults() {
        return plusResults;
    }

    public void setPlusResults(List<PlusResult> plusResults) {
        this.plusResults = plusResults;
    }
}
