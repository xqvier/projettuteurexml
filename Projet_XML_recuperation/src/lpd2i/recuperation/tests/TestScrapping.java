/*
 * TestScraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation.tests;

import java.net.MalformedURLException;

import org.w3c.dom.DOMException;

import lpd2i.bean.Film;
import lpd2i.bean.InformationNonPriseEnChargeException;
import lpd2i.configuration.ConfigurationIntrouvableException;
import lpd2i.configuration.ConfigurationManager;
import lpd2i.recuperation.BaliseIntrouvableException;
import lpd2i.recuperation.ElementIntrouvableException;
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
            ConfigurationManager conf = ConfigurationManager.getInstance();
            scrap = new Scrapping(
                    "http://www.allocine.fr/film/fichefilm_gen_cfilm=19776.html",
                    ConfigurationManager.getInstance().getConfiguration("allocine"));
            Film film = scrap.extractFilm();
            film.save();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BaliseIntrouvableException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (DOMException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (ConfigurationIntrouvableException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (InformationNonPriseEnChargeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        } catch (ElementIntrouvableException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        }
    }

}
