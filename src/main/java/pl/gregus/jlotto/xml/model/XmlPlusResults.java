/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.xml.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
