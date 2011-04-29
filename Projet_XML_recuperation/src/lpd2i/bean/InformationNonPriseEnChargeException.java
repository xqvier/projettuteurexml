/*
 * InformationNonPriseEnChargeException.java	20 avr. 2011
 * xMourgues 
 */
package lpd2i.bean;

/**
 * TODO Comment class
 *
 * @author xMourgues
 * @version 
 */
public class InformationNonPriseEnChargeException extends Exception {

    /**
     * TODO Comment constructor
     *
     * @param entityName
     */
    public InformationNonPriseEnChargeException(String entityName) {
	super("L'information ("+entityName+") récupérée est non prise en charge dans l'objet");
    }

    /** TODO Comment attribute */
    private static final long serialVersionUID = 5195601061925134843L;

}
