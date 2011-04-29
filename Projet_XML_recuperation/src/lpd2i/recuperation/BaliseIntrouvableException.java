/*
 * BaliseIntrouvableException.java	19 avr. 2011
 * xMourgues 
 */
package lpd2i.recuperation;

/**
 * TODO Comment class
 *
 * @author xMourgues
 * @version 
 */
public class BaliseIntrouvableException extends Exception {

    /** TODO Comment attribute */
    private static final long serialVersionUID = -8671563550085088589L;

    /**
     * TODO Comment constructor
     *
     */
    public BaliseIntrouvableException() {
	super("La balise n'a pas été trouvé dans le fichier XML");
    }

}
