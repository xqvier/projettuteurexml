/*
 * Scraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * Classe statique permettant de récuperer les informations a partir d'un site
 * TODO Commenter cette classe
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Scraping {
    /** URL de la page contenant les informations */
    private String url;

    /** Le film retiré des informations de la page */
    private Film film;

    /** Le fichier contenant uniquement les informations nécessaires */
    private String basenamefile;

    /** Le document xml a parser */
    private Document xmlfile;

    /** Le site de provenance de la page */
    private int site;
    
    /** fond de puit pour la fonction recursive de recherche de l'élément voulu */
    private boolean puit;
    
    /** variable contenant la le point d'entrée du tableau de donnée voulu */
    private Node node;
    
    /** Constante indiquant que la page provient d'allocine */
    public static final int ALLOCINE = 1;

    /** Constante indiquant que la page provient du site IMDB */
    public static final int CINEFIL = 2;

    /**
     * Constructeur a partir d'une url d'un site
     * 
     * @param url
     *            l'url du film
     */
    public Scraping(String url, int site) {
        super();
        this.url = url;
        this.basenamefile = "test";
        this.site = site;
    }

    // /**
    // * Charge les éléments de la page web et les met tel quelles dans un
    // nouveau
    // * fichier
    // */
    // public void loadContent() {
    //
    // try {
    // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
    // "proxy", 8080));
    //
    // BufferedWriter dout = new BufferedWriter(new FileWriter(
    // basenamefile + ".htm"));
    // URL u = new URL(url);
    // HttpURLConnection uc = (HttpURLConnection) u.openConnection(proxy);
    // BufferedReader din = new BufferedReader(new InputStreamReader(
    // uc.getInputStream()));
    // String line;
    // while ((line = din.readLine()) != null) {
    // dout.write(line);
    // dout.write('\n');
    // }
    // dout.flush();
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    /**
     * Charge les éléments de la page web et nettoie le fichier pour le rendre
     * parseable via des primitive XSLT
     */
    public void loadContent() {
        Tidy ti = new Tidy();
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    "proxy", 8080));
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection(proxy);
            
            ti.setConfigurationFromFile("tidyconfig.txt");
            // ti.parse(new FileInputStream(basenamefile + ".htm"),
            // new FileOutputStream(basenamefile + ".xml"));
            // Configuration conf = ti.getConfiguration();
            // conf.printConfigOptions(new FileWriter("jtidy.conf"), true);
            // ti.setMakeClean(true);
            // ti.setXmlOut(true);
            ti.setInputEncoding("utf8");
            ti.setOutputEncoding("utf8");
            ti.setErrout(new PrintWriter(this.basenamefile + ".err"));
            
            this.xmlfile = ti.parseDOM(uc.getInputStream(),
                    new FileOutputStream(this.basenamefile + ".xml"));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Retire les information du fichier pour en retirer toutes les information
     * du film referencé Utilise des primitives XSLT
     */
    public void parseContent() {
        if(this.site == Scraping.ALLOCINE){
            // on accede d'abord au tableau contenant toutes les données
            search(this.xmlfile, "div", "Titre original", Node.TEXT_NODE);
        }
    }
    
    private void search(Node start, String element, String contains, int type){
        NodeList nl = start.getChildNodes();
        Node n;
        for(int i = 0; i<nl.getLength(); i++){
            n = nl.item(i);
            System.out.println(n.getNodeName());
            this.search(n,element,contains,type);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Scraping [url=" + url + ", film=" + film + "]";
    }
}
