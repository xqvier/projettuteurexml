package lpd2i.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * class qui gere les elements (titre, duree, annee, etc)
 *
 * @author xMourgues
 * @version 
 */
public class EntityConfiguration extends AbstractConfiguration<Element> implements HasElements{
    private int currentElementPos;
    private LinkedList<Element> elements;
    
    /**
     * TODO Comment constructor
     *
     */
    public EntityConfiguration() {
	super();
	currentElementPos = 0;
	elements = new LinkedList<Element>();
    }
    /* (non-Javadoc)
     * @see lpd2i.configuration.AbstractConfiguration#loadConfig(org.w3c.dom.Node)
     */
    @Override
    public void loadConfig(Node noeud) {
	this.name=noeud.getNodeName();
	System.out.println("EntityConfiguration.loadConfig():"+this.name);
	NodeList childs = noeud.getChildNodes();
	Node n2;
	Element elem;
	for (int i = 0 ; i<childs.getLength();i++){
	    n2 = childs.item(i);
	    if(!"#text".equals(n2.getNodeName())){
		elem = new Element();
		try {
	            elem.loadConfig(n2);
                } catch (BaliseInconnuException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
                }
		content.add(elem);
	    }
	}
	
    }

    /* (non-Javadoc)
     * @see lpd2i.configuration.AbstractConfiguration#getConfiguration(java.lang.String)
     */
    @Override
    public Element getConfiguration(String name)
            throws ConfigurationIntrouvableException {
	throw new ConfigurationIntrouvableException(this.getClass().getName(), this.getName());
    }

    /* (non-Javadoc)
     * @see lpd2i.configuration.HasElements#hasNextElement()
     */
    @Override
    public boolean hasNextElement() {
	return (currentElementPos < elementLength());
    }
    /* (non-Javadoc)
     * @see lpd2i.configuration.HasElements#nextElement()
     */
    @Override
    public Element nextElement() {
	if(currentElementPos >= elementLength()){
	    throw new ArrayIndexOutOfBoundsException(currentElementPos);
	}
	currentElementPos++;
	return elements.get(currentElementPos-1);
    }
    /* (non-Javadoc)
     * @see lpd2i.configuration.HasElements#elementLength()
     */
    @Override
    public int elementLength() {
	return elements.size();
    }

}
