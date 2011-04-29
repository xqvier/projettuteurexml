/*
 * ElementIntrouvableException.java	28 avr. 2011
 * xMourgues 
 */
package lpd2i.recuperation;

import lpd2i.configuration.Element;

/**
 * TODO Comment class
 * 
 * @author xMourgues
 * @version
 */
public class ElementIntrouvableException extends Exception {

    /**
     * TODO Comment constructor
     * 
     * @param element
     */
    public ElementIntrouvableException(Element element) {
	super("L'élement de type \""
	        + (element.getBalise() == Element.BALISE ? "balise" : element
	                .getBalise() == Element.ATTRIBUTE ? "attribut"
	                : "inconnu") + "\" avec comme nom \""
	        + element.getInfoName() + "\" et comme valeur \""
	        + element.getInfoValue() + "\" n'a pas été trouvé");
    }

    /** TODO Comment attribute */
    private static final long serialVersionUID = 7247958971922535070L;

}
