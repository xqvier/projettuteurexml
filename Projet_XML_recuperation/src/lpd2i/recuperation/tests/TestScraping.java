/*
 * TestScraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation.tests;

import lpd2i.recuperation.Scraping;

/**
 * Classe de teste du téléchargement des informations
 * @author Xavier Mourgues
 * @version 0.1
 */
public class TestScraping {

    /**
     * TODO Commenter cette méthode
     * @param args
     */
    public static void main(String[] args) {
        Scraping scrap = new Scraping("http://www.rgagnon.com/javadetails/java-0085.html", Scraping.CINEFIL);
        
        System.out.println("loadContent():");
        scrap.loadContent();
        
        System.out.println("cleanContent():");
        scrap.cleanContent();
        
        /*System.out.println("parseContent():");
        scrap.parseContent();*/
        
        System.out.println(scrap);
    }

}
