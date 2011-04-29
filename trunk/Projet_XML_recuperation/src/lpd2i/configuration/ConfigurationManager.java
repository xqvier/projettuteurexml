/*
 * ConfigurationManager.java	11 avr. 2011
 * xMourgues 
 */
package lpd2i.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class singleton gérant les configurations pour les différents sites
 *
 * @author xMourgues
 * @version 
 */
public class ConfigurationManager extends AbstractConfiguration<SiteConfiguration> {

    private String confFileName = "conf.xml";
    
    private Element confFile;
    
    /** variable d'instance de la classe de type singleton */
    private static ConfigurationManager instance = null;
    
    /**
     * Constructeur privé pour le singleton
     */
    private ConfigurationManager(){
	super();
	File file = new File(confFileName );
	try {
	    confFile = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file).getDocumentElement();
        } catch (SAXException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        }
        
        
        loadConfig(confFile);
    }
    
   
    /**
     * TODO Comment method
     *
     */
    public void loadConfig(Node noeud) {
	NodeList childs = noeud.getChildNodes();
	this.name = noeud.getNodeName();
	System.out.println("ConfigurationManager.loadConfig():"+this.name);
	Node n2;
	SiteConfiguration siteConf = new SiteConfiguration();
	for (int i = 0 ; i<childs.getLength();i++){
	    n2 = childs.item(i);
	    if(!"#text".equals(n2.getNodeName())){
		siteConf = new SiteConfiguration();
		siteConf.loadConfig(n2);
		content.add(siteConf);
	    }
	}
    }


    /**
     * renvoie l'instance de la classe singleton
     * @return l'instance de la classe
     */
    public static ConfigurationManager getInstance(){
	if(instance == null){
	    instance = new ConfigurationManager();
	}
	return instance;
    }


    /* (non-Javadoc)
     * @see lpd2i.configuration.AbstractConfiguration#getConfiguration(java.lang.String)
     */
    @Override
    public SiteConfiguration getConfiguration(String name) throws ConfigurationIntrouvableException {
	Iterator<SiteConfiguration> it = content.iterator();
	SiteConfiguration conf;
	while(it.hasNext()){
	    conf = it.next();
	    if(name.equals(conf.getName())){
		return conf;
	    }
	}
	throw new ConfigurationIntrouvableException(this.getClass().getName(), this.getName());
    }
}
