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
@XmlRootElement(name = "lottoResults")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlLottoResults {

    @XmlElement(name = "lottoResult")
    private List<LottoResult> lottoResults;

    public List<LottoResult> getLottoResults() {
        return lottoResults;
    }

    public void setLottoResults(List<LottoResult> lottoResults) {
        this.lottoResults = lottoResults;
    }
}
