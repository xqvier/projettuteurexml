/*
 * ElementConfiguration.java	19 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * TODO Comment class
 * 
 * @author xMourgues
 * @version
 */
public class Element {
    public static final short BALISE = 0;
    public static final short ATTRIBUTE = 1;
    private short balise=-1;
    private String infoName=null;
    private String infoValue=null;

    /**
     * TODO Comment method
     * 
     * @param n2
     * @throws BaliseInconnuException 
     */
    public void loadConfig(Node n2) throws BaliseInconnuException {
	String baliseName = n2.getNodeName();
	System.out.print("Element.loadConfig():"+baliseName);
	if("balise".equals(baliseName)){
	    balise=BALISE;
	} else if ("attribut".equals(baliseName)){
	    balise = ATTRIBUTE;	    
	} else {
	    throw new BaliseInconnuException(baliseName);
	}
	NamedNodeMap attr = n2.getAttributes();
	if (attr != null) {
	    infoName = attr.getNamedItem("name").getNodeValue();
	    infoValue = null;
	    if (attr.getLength() > 1) {
		infoValue = attr.getNamedItem("value").getNodeValue();
	    }
	}
	System.out.println(" - "+infoName+" | "+infoValue);
    }

    /**
     * TODO Comment method
     *
     * @return
     */
    public String getInfoName() {
	return infoName;
    }

    /**
     * TODO Comment method
     *
     * @return
     */
    public String getInfoValue() {
	return infoValue;
    }

    /**
     * TODO Comment method
     *
     * @return
     */
    public short getBalise() {
	return balise;
    }
}
