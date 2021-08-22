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
