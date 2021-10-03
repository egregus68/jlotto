/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.service;

import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gregus.jlotto.xml.factory.XmlFactory;

/**
 *
 * @author Grzegorz
 * @param <T>
 */
public final class XmlService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlService.class);

    private XmlFactory xmlReaderFactory;

    T t;

    public T read(String xmlFile, Class<T> clazz) {
        try {
            xmlReaderFactory = new XmlFactory(xmlFile);
            return t = (T) xmlReaderFactory.unmarshall(clazz);
        } catch (JAXBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    public void write(String xmlFile, T t) {
        xmlReaderFactory = new XmlFactory(xmlFile);
        setT(t);
        try {
            xmlReaderFactory.marshall(t);
        } catch (JAXBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
