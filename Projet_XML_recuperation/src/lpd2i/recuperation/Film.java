/*
 * Film.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.util.ArrayList;

/**
 * Classe gérant les informations d'un film
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Film {
    /** Titre du film */
    private String titre;

    /** Durée du film */
    private int duree;

    /** Liste des acteurs */
    private ArrayList<Personne> acteurs;

    /** Liste des réalisateurs */
    private ArrayList<Personne> realisateurs;

    
    public Film(){
        this.acteurs = new ArrayList<Personne>();
        this.realisateurs = new ArrayList<Personne>();
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Film [titre=" + titre + ", duree=" + duree + ", acteurs=");
        for (Personne acteur : acteurs) {
            str.append(acteur);
        }
        str.append(", realisateurs=");
        for (Personne realisateur : realisateurs) {
            str.append(realisateur);
        }
        str.append("]");
        return str.toString();
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the duree
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

}
