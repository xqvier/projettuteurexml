/*
 * Film.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.driver.DatabaseError;

/**
 * Classe gérant les informations d'un film
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Film {
    /**
     * Titre du film
     * 
     * @uml.property name="titre"
     */
    private String titre;

    /**
     * Durée du film
     * 
     * @uml.property name="duree"
     */
    private int duree;

    /**
     * Année de sortie du film
     * 
     * @uml.property name="annee"
     */
    private int annee;

    /**
     * Note moyenne donnée par les site
     * 
     * @uml.property name="noteSite"
     */
    private float noteSite;

    /**
     * Résumé du film
     * 
     * @uml.property name="resume"
     */
    private String resume;

    /**
     * Genres du film
     * 
     * @uml.property name="genres"
     * @uml.associationEnd multiplicity="(0 -1)" elementType="java.lang.String"
     */
    private ArrayList<String> genres;

    /**
     * Commentaires du film
     * 
     * @uml.property name="commentaires"
     */
    private ArrayList<Commentaire> commentaires;

    /**
     * Liste des acteurs
     * 
     * @uml.property name="acteurs"
     */
    private ArrayList<Personne> acteurs;

    /**
     * Liste des réalisateurs
     * 
     * @uml.property name="realisateurs"
     * @uml.associationEnd multiplicity="(0 -1)"
     *                     elementType="lpd2i.recuperation.Personne"
     */
    private ArrayList<Personne> realisateurs;

    /** le lien vers l'affiche sur le disque dur (absolu? / relatif ?) */
    private String affiche;

    public Film() {
        this.acteurs = new ArrayList<Personne>();
        this.realisateurs = new ArrayList<Personne>();
        this.genres = new ArrayList<String>();
        this.commentaires = new ArrayList<Commentaire>();
    }

    /**
     * @return the titre
     * @uml.property name="titre"
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre
     *            the titre to set
     * @uml.property name="titre"
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the duree
     * @uml.property name="duree"
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @param duree
     *            the duree to set
     * @uml.property name="duree"
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param parseInt
     * @uml.property name="annee"
     */
    public void setAnnee(int annee) {
        this.annee = annee;

    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param f
     * @uml.property name="noteSite"
     */
    public void setNoteSite(float note) {
        this.noteSite = note;
    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param nodeValue
     * @uml.property name="resume"
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param nodeValue
     */
    public void addGenre(String genre) {
        genres.add(genre);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Film [titre=" + titre + ", duree=" + duree + ", annee="
                + annee + ", noteSite=" + noteSite + ", resume=" + resume
                + ", genres=");
        for (String genre : genres) {
            str.append("," + genre);
        }
        str.append(", commentaires=");
        for (Commentaire com : commentaires) {
            str.append(com);
        }
        str.append(", acteurs=");
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
     * méthode stockant les informations du film dans la base de donnée TODO
     */
    public void save() {
        boolean update = false;
        int idfilm = -1;
        String requete;
        DataBase db;
        DataBase db2;
        try {
            db = new DataBase();
            db2 = new DataBase();
            db.connextionOracle();
            // verifier que le film n'existe pas déjà
            ResultSet films = db
                    .executeRequete("SELECT id_film, titre FROM film");
            while (films.next()) {
                if (films.getString(2).equals(this.titre)) {
                    update = true;
                    idfilm = Integer.parseInt(films.getString(1));
                }
                if (!update) {
                    // si on update pas on stocke le plus haut ID pour
                    // l'incrémenter
                    if (Integer.parseInt(films.getString(1)) > idfilm) {
                        idfilm = Integer.parseInt(films.getString(1));
                    }
                }
            }

            // stocker les information de base (titre, durée, etc..)
            // TODO penser a rajouter les commentaires
            // penser a incrementer l'id si ce n'est pas un update
            StringBuffer comms = new StringBuffer();
            comms.append("<film>");
            for (Commentaire c : this.commentaires) {
                comms.append(c);
            }
            comms.append("</film>");
            if (update) {
                requete = "UPDATE film SET titre = '"
                        + this.titre.replace("'", "''") + "', duree =  "
                        + this.duree + ", annee = " + this.annee
                        + ", notes_site = " + this.noteSite + ", resume = '"
                        + this.resume.replace("'", "''")
                        + "',commentaire = XMLType('" + comms.toString()
                        + "'), affiche = '" + this.affiche
                        + "' WHERE id_film = " + idfilm;
                db.executeRequete(requete);
            } else {
                idfilm++;
                requete = "INSERT INTO film VALUES(" + idfilm + ",'"
                        + this.titre.replace("'", "''") + "'," + this.duree
                        + "," + this.annee + "," + this.noteSite + ",0.0,'"
                        + this.resume.replace("'", "''") + "',XMLType('"
                        + comms.toString() + "'),'')";
                db.executeRequete(requete);
            }
            // stocker les acteurs, realisateur et genre
            ArrayList<Integer> idacteurs = new ArrayList<Integer>();
            for (Personne e : acteurs) {
                idacteurs.add(e.save());
            }
            ArrayList<Integer> idrealisateurs = new ArrayList<Integer>();
            for (Personne e : realisateurs) {
                idrealisateurs.add(e.save());
            }

            // verifier que le genre n'existe pas déjà
            requete = "SELECT id_genre, nom_genre FROM genre";
            ResultSet genres;
            ArrayList<Integer> idgenres = new ArrayList<Integer>();
            int idgenre;
            for (String genre : this.genres) {
                update = false;
                // TODO optimiser pour pas avoir a refaire la requete a chaque
                // itération
                genres = db.executeRequete(requete);
                idgenre = -1;
                genres.beforeFirst();
                while (genres.next()) {
                    if (genres.getString(2).equals(genre)) {
                        update = true;
                        idgenre = Integer.parseInt(genres.getString(1));
                    }
                    if (!update) {
                        // si on update pas on stocke le plus haut ID pour
                        // l'incrémenter
                        if (Integer.parseInt(genres.getString(1)) > idgenre) {
                            idgenre = Integer.parseInt(genres.getString(1));
                        }
                    }
                }
                if (!update) {
                    idgenre++;
                    db2.executeRequete("INSERT INTO genre VALUES(" + idgenre
                            + ", '" + genre + "')");
                }
                idgenres.add(idgenre);
            }
            // stocker les liens (acteur/film, realisateur/film, genre/film)
            // acteur/film
            requete = "SELECT id_film,id_pers FROM jouer";
            ResultSet filmsactors;
            for (Integer id : idacteurs) {
                update = false;
                // TODO optimiser pour pas avoir a refaire la requete a chaque
                // itération
                filmsactors = db.executeRequete(requete);
                // verifier si le lien n'existe pas deja
                filmsactors.first();
                while (filmsactors.next()) {
                    if (idfilm == Integer.parseInt(filmsactors.getString(1))
                            && id == Integer.parseInt(filmsactors.getString(2))) {
                        update = true;
                    }
                }
                if (!update) {
                    db2.executeRequete("INSERT INTO jouer VALUES(" + idfilm
                            + "," + id + ")");
                }
            }
            // realisateur/film
            requete = "SELECT id_film,id_pers FROM realiser";
            ResultSet filmsrealisators;
            for (Integer id : idrealisateurs) {
                update = false;
                // TODO optimiser pour pas avoir a refaire la requete a chaque
                // itération
                filmsrealisators = db.executeRequete(requete);
                // verifier si le lien n'existe pas deja
                filmsrealisators.beforeFirst();
                while (filmsrealisators.next()){
                    if (idfilm == Integer.parseInt(filmsrealisators
                            .getString(1))
                            && id == Integer.parseInt(filmsrealisators
                                    .getString(2))) {
                        update = true;
                    }
                }
                if (!update) {
                    db2.executeRequete("INSERT INTO realiser VALUES('" + idfilm
                            + "','" + id + "')");
                }
            }
            // genre/film
            requete = "SELECT id_film,id_genre FROM est";
            ResultSet filmsgenres;
            for (Integer id : idgenres) {
                update = false;
                // TODO optimiser pour pas avoir a refaire la requete a chaque
                // itération
                filmsgenres = db.executeRequete(requete);
                // verifier si le lien n'existe pas deja
                filmsgenres.beforeFirst();
                while (filmsgenres.next()) {
                    if (idfilm == Integer.parseInt(filmsgenres.getString(1))
                            && id == Integer.parseInt(filmsgenres.getString(2))) {
                        update = true;
                    }
                }
                if (!update) {
                    db2.executeRequete("INSERT INTO est VALUES(" + idfilm + ","
                            + id + ")");
                }
            }
            db.close();
            db2.close();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param personne
     */
    public void addRealisateur(Personne personne) {
        this.realisateurs.add(personne);

    }

    /**
     * TODO Commenter cette méthode
     * 
     * @param parseActorsContent
     */
    public void setActeurs(ArrayList<Personne> acteurs) {
        this.acteurs = acteurs;
    }

}
