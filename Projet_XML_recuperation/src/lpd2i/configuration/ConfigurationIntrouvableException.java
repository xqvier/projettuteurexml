/*
 * ConfigurationIntrouvableException.java	20 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

/**
 * TODO Comment class
 *
 * @author xMourgues
 * @version 
 */
public class ConfigurationIntrouvableException extends Exception {

    /** TODO Comment attribute */
    private static final long serialVersionUID = 1929522325446543924L;

    /**
     * TODO Comment constructor
     * @param entity 
     * @param name 
     *
     */
    public ConfigurationIntrouvableException(String entity, String name) {
	super("La configuration pour l'élément (" + entity + ":" +  name + ")n'a pas été trouvé");
    }
}
