/*
 * Personne.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe contenant les informations d'une personne
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Personne {
    /**
     * prenom de la personne
     * 
     * @uml.property name="prenom"
     */
    private String prenom;

    /**
     * nom de la personne
     * 
     * @uml.property name="nom"
     */
    private String nom;

    /**
     * liste des films realise
     * 
     * @uml.property name="realise"
     */
    private ArrayList<Film> realise;

    /**
     * liste des films dont la personne est actrice
     * 
     * @uml.property name="joue"
     */
    private ArrayList<Film> joue;

    /**
     * TODO Commenter le constructeur
     * 
     * @param string
     * @param string2
     */
    public Personne(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    /**
     * TODO Commenter le constructeur
     * 
     * @param nodeValue
     */
    public Personne(String prenomnom) {
        String text[] = prenomnom.split(" ");
        this.prenom = text[0];
        this.nom = text[1];
        for (int i = 2; i < text.length; i++) {
            this.nom = this.nom + " " + text[i];
        }
    }

    /**
     * méthode de stockage d'un acteur ou réalisateur dans la base TODO
     * 
     * @return l'id de la personne une fois sauvé dans la base
     */
    public int save() {
        // verifier que la personne n'y soit pas déja
        DataBase db = new DataBase();
        Boolean update = false;
        int id = -1;
        try {
            db.connextionOracle();
            String requete = "SELECT id_pers, prenom_pers, nom_pers FROM personne";
            ResultSet acteurs = db.executeRequete(requete);
            while(acteurs.next()) {
                if (acteurs.getString(2).equals(this.prenom)
                        && acteurs.getString(3).equals(this.nom)) {
                    update = true;
                    id = Integer.parseInt(acteurs.getString(1));
                }
                if (!update) {
                    // si on update pas on stocke le plus haut ID pour
                    // l'incrémenter
                    if (Integer.parseInt(acteurs.getString(1)) > id) {
                        id = Integer.parseInt(acteurs.getString(1));
                    }
                }
            }
            if (!update) {
                id++;
                requete = "INSERT INTO personne VALUES(" + id + ",'"
                        + this.nom.replace("'", "''") + "','"
                        + this.prenom.replace("'", "''") + "')";
                db.executeRequete(requete);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Personne [prenom=" + prenom + ", nom=" + nom + "]";
    }

}
