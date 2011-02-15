/*
 * TestSax.java 	15 févr. 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation.tests;

import java.io.FileReader;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * TODO Commenter cette classe
 * @author Xavier Mourgues
 * @version 0.1
 */
public class TestSax extends DefaultHandler {

    /**
     * TODO Commenter le constructeur
     */
    public TestSax() {
        super();
    }

    /**
     * TODO Commenter cette méthode
     * @param args
     */
    public static void main(String[] args) throws Exception {
        XMLReader xr = XMLReaderFactory.createXMLReader();
        
        TestSax app = new TestSax();
        xr.setContentHandler(app);
        xr.setErrorHandler(app);
        FileReader r = new FileReader("temp.xml");
        xr.parse(new InputSource(r));
        
    }

}
