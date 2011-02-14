/*
 * Scraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Classe statique permettant de récuperer les informations a partir d'un site
 * TODO Commenter cette classe
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Scraping {
    /** URL de la page contenant les informations */
    private String url;
    
    /** Le film retiré des informations de la page */
    private Film film;
    
    /** Le fichier contenant uniquement les informations nécessaires */
    private String fichier;
    
    /** Constante indiquant que la page provient d'allocine */
    public static final int ALLOCINE = 1;
    
    /** Constante indiquant que la page provient du site IMDB */
    public static final int CINEFIL = 2;

    /**
     * Constructeur a partir d'une url d'un site
     * @param url l'url du film
     */
    public Scraping(String url, int site) {
        super();
        this.url = url;
    }
    
    /**
     * Charge les éléments de la page web et les met tel quelles dans un nouveau fichier
     */
    public void loadContent() {
        try {
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 8080)));
            uc.connect();
            DataInputStream flot = new DataInputStream(uc.getInputStream());
            String line;
            while((line=flot.readLine()) != null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Nettoie le fichier pour le rendre parseable via des primitive XSLT
     */
    public void cleanContent(){
        
    }
    
    /**
     * Retire les information du fichier pour en retirer toutes les information du film referencé
     * Utilise des primitives XSLT
     */
    public void parseContent(){
        
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Scraping [url=" + url + ", film=" + film + "]";
    }
}
