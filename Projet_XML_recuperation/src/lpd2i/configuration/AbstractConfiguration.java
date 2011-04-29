/*
 * AbstractConfiguration.java	19 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * TODO Comment class
 * 
 * @author xMourgues
 * @version
 */
public abstract class AbstractConfiguration<E> {
    protected int currentPos;

    protected String name = "DEFAULT";
    
    protected LinkedList<E> content;
    
    /**
     * TODO Comment constructor
     *
     */
    public AbstractConfiguration() {
	currentPos=0;
	content = new LinkedList<E>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    public Boolean hasNext() {
	return (currentPos < length());
    }

    public E next(){
	if(currentPos >= length()){
	    throw new ArrayIndexOutOfBoundsException(currentPos);
	}
	currentPos++;
	return content.get(currentPos-1);
	
    }

    /**
     * TODO Comment method
     * 
     * @return
     */
    private int length() {
	return content.size();
    }
    

    /**
     * TODO Comment method
     *
     * @param string
     * @return
     * @throws ConfigurationIntrouvableException 
     */
    public abstract E getConfiguration(String name) throws ConfigurationIntrouvableException;/* {
	Iterator<E> it = content.iterator();
	E conf;
	while(it.hasNext()){
	    conf = it.next();
	    if(name.equals(((Class<? extends AbstractConfiguration>) conf).getName())){
		return conf;
	    }
	}
	throw new ConfigurationIntrouvableException(this.getClass().getName(), this.getName());
    }*/
    
    /**
     * TODO Comment method
     * 
     * @param noeud
     */
    public abstract void loadConfig(Node noeud);
}
