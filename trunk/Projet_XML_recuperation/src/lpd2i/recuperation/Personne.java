/*
 * Personne.java 	14 févr. 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation;

import java.util.ArrayList;

/**
 * Classe contenant les informations d'une personne
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Personne {
    /** prenom de la personne */
    private String prenom;
    
    /** nom de la personne */
    private String nom;
    
    /** liste des films realise */
    private ArrayList<Film> realise;
  
    /** liste des films dont la personne est actrice */
    private ArrayList<Film> joue;

    /**
     * TODO Commenter le constructeur
     * @param string
     * @param string2
     */
    public Personne(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Personne [prenom=" + prenom + ", nom=" + nom +"]";
    }
    
    
}
