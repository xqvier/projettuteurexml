/*
 * PageConfiguration.java	19 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Gere la configuration pour une entité (infos sur les films ou info sur les
 * acteurs)
 * 
 * @author xMourgues
 * @version
 */
public class PageConfiguration extends AbstractConfiguration<EntityConfiguration> implements HasElements{
    private LinkedList<Element> elements;
    private int currentElementPos;
    
    /**
     * TODO Comment constructor
     *
     */
    public PageConfiguration() {
	super();
	this.elements = new LinkedList<Element>();
	this.currentElementPos=0;
    }
    /**
     * TODO Comment method
     * 
     * @param n2
     */
    public void loadConfig(Node noeud) {
	this.name = noeud.getNodeName();
	System.out.println("PageConfiguration.loadConfig():"+this.name);
	NodeList childs = noeud.getChildNodes();
	Node n2;
	EntityConfiguration entity;
	Element element;
	for (int i = 0; i < childs.getLength(); i++) {
	    n2 = childs.item(i);
	    if (!"#text".equals(n2.getNodeName())
		    && !"attribut".equals(n2.getNodeName())
		    && !"balise".equals(n2.getNodeName())) {
		entity = new EntityConfiguration();
		entity.loadConfig(n2);
		this.content.add(entity);
	    }
	    else if(!"#text".equals(n2.getNodeName())){
		element = new Element();
		try {
	            element.loadConfig(n2);
                } catch (BaliseInconnuException e) {
	            e.printStackTrace();
                }
		this.elements.add(element);
	    }
	}
    }

    /* (non-Javadoc)
     * @see lpd2i.configuration.AbstractConfiguration#getConfiguration(java.lang.String)
     */
    @Override
    public EntityConfiguration getConfiguration(String name)
            throws ConfigurationIntrouvableException {
	Iterator<EntityConfiguration> it = content.iterator();
	EntityConfiguration conf;
	while(it.hasNext()){
	    conf = it.next();
	    if(name.equals(conf.getName())){
		return conf;
	    }
	}
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
