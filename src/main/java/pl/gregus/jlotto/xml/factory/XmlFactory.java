/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.xml.factory;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Grzegorz
 * @param <T>
 */
public class XmlFactory<T> {

    private final String xmlFileName;

    public XmlFactory(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public <T> T unmarshall(Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T t = clazz.cast(unmarshaller.unmarshal(getXmlDefinitionStream()));
        return t;
    }

    private InputStream getXmlDefinitionStream() {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFileName);
    }

    public void marshall(T clazz) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //jaxbMarshaller.marshal(clazz, System.out);
        jaxbMarshaller.marshal(clazz, new File(xmlFileName));

    }

}
