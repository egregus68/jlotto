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
@XmlRootElement(name = "szybkie600Results")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSzybkie600Results {

    @XmlElement(name = "szybkie600Result")
    private List<Szybkie600Result> szybkie600Results;

    public List<Szybkie600Result> getSzybkie600Results() {
        return szybkie600Results;
    }

    public void setSzybkie600Results(List<Szybkie600Result> szybkie600Results) {
        this.szybkie600Results = szybkie600Results;
    }
}
