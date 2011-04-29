/*
 * TestIni.java	19 avr. 2011
 * xMourgues 
 */
package lpd2i.recuperation.tests;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

/**
 * TODO Comment class
 *
 * @author xMourgues
 * @version 
 */
public class TestIni {
    public static void main(String[] args) {
	Wini ini = null;
	try {
	    ini = new Wini(new File("temp.ini"));
        } catch (InvalidFileFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        }
        
        ini.add("root"+ini.PATH_SEPARATOR+"child"+ini.PATH_SEPARATOR+"sub");
        ini.get("root").add("SALUT", "BONJOUR");
        ini.get("root"+ini.PATH_SEPARATOR+"child").add("COUCOU", "HELLO");
        try {
	    ini.store();
        } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        }
    }

}
