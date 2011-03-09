/*
 * Commentaire.java 	23 févr. 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation;

/**
 * Classe de gestion des commentaires des utilisateurs
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Commentaire {
    /**
     * pseudo de l'utilisateur
     * @uml.property  name="pseudo"
     */
    private String pseudo;
    
    /**
     * le commentaire
     * @uml.property  name="commentaire"
     */
    private String commentaire;
    
    /**
     * note de l'utilisateur associé au commentaire
     * @uml.property  name="note"
     */
    private String note;
    
    /**
     * source du commentaire
     * @uml.property  name="source"
     */
    private String source;

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "<commentaire><pseudo>"+this.pseudo+"</pseudo><com>"+this.commentaire+"</com><note>"+this.note+"</note><source>"+this.source+"</source></commentaire>";
        
    }

    
}
