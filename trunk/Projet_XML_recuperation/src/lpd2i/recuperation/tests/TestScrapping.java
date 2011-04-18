/*
 * TestScraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation.tests;

import java.net.MalformedURLException;

import lpd2i.bean.Film;
import lpd2i.configuration.ConfigurationManager;
import lpd2i.recuperation.Scrapping;

/**
 * Classe de teste du téléchargement des informations
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class TestScrapping {

    /**
     * TODO Commenter cette méthode
     * 
     * @param args
     * @throws MalformedURLException 
     */
    public static void main(String[] args){
        Scrapping scrap;
        try {
            scrap = new Scrapping(
                    "http://www.allocine.fr/film/fichefilm_gen_cfilm=29233.html",
                    ConfigurationManager.ALLOCINE);
            Film film = scrap.extractFilm();
            film.save();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
