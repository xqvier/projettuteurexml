/*
 * Scraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
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
    private String fichier;

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
    }

    /**
     * Charge les éléments de la page web et les met tel quelles dans un nouveau
     * fichier
     */
    public void loadContent() {

        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    "proxy", 8080));

            BufferedWriter dout = new BufferedWriter(new FileWriter("temp.html"));
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection(proxy);
            BufferedReader din = new BufferedReader(new InputStreamReader(
                    uc.getInputStream()));
            String line;
            while ((line = din.readLine()) != null) {
                dout.write(line);
                dout.write('\n');
            }
            dout.flush();
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
    public void cleanContent() {
        Tidy ti = new Tidy();
        try {
            ti.parse(new FileInputStream("temp.html"), new FileOutputStream(
                    "temp.xml"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Retire les information du fichier pour en retirer toutes les information
     * du film referencé Utilise des primitives XSLT
     */
    public void parseContent() {

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
