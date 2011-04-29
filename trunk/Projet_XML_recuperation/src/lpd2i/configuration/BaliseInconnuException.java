/*
 * BaliseInconnuException.java	20 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

/**
 * TODO Comment class
 *
 * @author xMourgues
 * @version 
 */
public class BaliseInconnuException extends Exception {

    /** TODO Comment attribute */
    private static final long serialVersionUID = 6241897488208000275L;

    /**
     * TODO Comment constructor
     *
     */
    public BaliseInconnuException(String baliseName) {
	super("La balise "+baliseName+" est inconnu dans le fichier de configuration");
    }
}
