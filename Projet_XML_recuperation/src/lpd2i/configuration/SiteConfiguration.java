package lpd2i.configuration;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SiteConfiguration extends AbstractConfiguration<PageConfiguration>{        
    /**
     * TODO Comment method
     *
     * @param noeud
     */
    public void loadConfig(Node noeud) {
	this.name = noeud.getNodeName();
	System.out.println("SiteConfiguration.loadConfig():"+this.name);
	NodeList childs = noeud.getChildNodes();
	Node n2;
	PageConfiguration pageConf = new PageConfiguration();
	for (int i = 0 ; i<childs.getLength();i++){
	    n2 = childs.item(i);
	    if(!"#text".equals(n2.getNodeName()) && !"attribut".equals(n2.getNodeName()) && !"balise".equals(n2.getNodeName())){
		pageConf = new PageConfiguration();
		pageConf.loadConfig(n2);
		content.add(pageConf);
	    }
	}
    }

    /* (non-Javadoc)
     * @see lpd2i.configuration.AbstractConfiguration#getConfiguration(java.lang.String)
     */
    @Override
    public PageConfiguration getConfiguration(String name)
            throws ConfigurationIntrouvableException {
	Iterator<PageConfiguration> it = content.iterator();
	PageConfiguration conf;
	while(it.hasNext()){
	    conf = it.next();
	    if(name.equals(conf.getName())){
		return conf;
	    }
	}
	throw new ConfigurationIntrouvableException(this.getClass().getName(), this.getName());
    }
}
