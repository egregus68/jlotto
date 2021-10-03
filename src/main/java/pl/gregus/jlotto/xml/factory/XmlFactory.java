/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.xml.factory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

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
