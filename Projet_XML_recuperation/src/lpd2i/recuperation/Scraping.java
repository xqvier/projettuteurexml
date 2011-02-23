/*
 * Scraping.java 	14 févr. 2011
 * Projet_XML_recuperation
 */
package lpd2i.recuperation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
    private String basenamefile;

    /** Le document xml a parser */
    private Document xmlfile;

    /** Le site de provenance de la page */
    private int site;

    /** noeud de resultat de la recherche */
    Node noeud;

    /** iterateur de champ texte */
    private int iterateur = 0;

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
        this.basenamefile = "test";
        this.site = site;
    }

    /**
     * Charge les éléments de la page web et nettoie le fichier pour le rendre
     * parseable
     */
    public void loadContent() {
        Tidy ti = new Tidy();
        try {
            // Connection via le proxy de l'iut
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    "proxy", 8080));
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection(proxy);

            // configuration du parsage
            ti.setConfigurationFromFile("tidyconfig.txt");
            ti.setInputEncoding("utf8");
            ti.setOutputEncoding("utf8");
            ti.setErrout(new PrintWriter(this.basenamefile + ".err"));

            // nettoie le fichier a partir du site web et l'enregistre sur le
            // disque
            this.xmlfile = ti.parseDOM(uc.getInputStream(),
                    new FileOutputStream(this.basenamefile + ".xml"));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Retire les information du fichier pour en retirer toutes les information
     * du film referencé.
     */
    public void parseContent() {
        this.film = new Film();
        if (this.site == Scraping.ALLOCINE) {
            // on accede d'abord au noeud (tableau/div) contenant toutes les
            // données
            this.setNoeud(this.search(this.xmlfile, "class", "colcontent",
                    Node.ATTRIBUTE_NODE));

            // on creer le nouveau document avec uniquement les infos utiles
            this.xmlfile = this.noeud.getOwnerDocument();

            // on parse ensuite toutes les données une a une

            // premierement le titre
            this.setNoeud(search(this.xmlfile, "#text", "Titre original",
                    Node.TEXT_NODE));
            this.setNoeud(nextNode(noeud));
            this.setNoeud(search(this.noeud, "em", null, Node.TEXT_NODE));
            this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            this.film.setTitre(this.noeud.getNodeValue());

            // deuxiemement la durée du film
            this.setNoeud(search(this.xmlfile, "#text", "Durée", Node.TEXT_NODE));
            String[] datas = this.noeud.getNodeValue().split(" ");
            String[] durees = datas[3].split("h");
            int heure = Integer.parseInt(durees[0]);
            int min = Integer.parseInt(durees[1].split("min")[0]);
            this.film.setDuree((heure * 60) + min);

            // annee
            this.setNoeud(nextNode(this.noeud, "a"));
            this.setNoeud(search(this.noeud,"#text", null, Node.TEXT_NODE));
            film.setAnnee(Integer.parseInt(this.noeud.getNodeValue()));

        }
    }

    /**
     * Recherche a partir des parametres un noeud d'un fichier xml correspondant
     * aux element de recherche et retourne ce noeud. Marche recursivement pour
     * naviguer dans les noeud en profondeur.
     * 
     * @param start
     * 
     * @param start
     *            le noeud a partir duquel il faut creuser (cela peut etre
     *            directement un Document)
     * @param element
     *            la valeur de l'élement a rechercher (peut etre l'attribut ou
     *            le nom d'un noeud)
     * @param contains
     *            la valeur que doit contenir l'élément rechercher
     * @param type
     *            le type d'élément rechercher, si c'est par la valeur d'un
     *            attribut ou si c'est par la valeur du noeud
     * @return
     * @return le point d'ancrage où toutes les informations interessantes sont
     * @throws BadAttributeValueExpException
     *             si l'element n'a pas ete trouve
     */
    private Node search(Node start, String element, String contains, short type) {

        Node noeud = null;
        Node attr;
        // on prend la liste des enfants du noeud courant
        NodeList nl = start.getChildNodes();
        // on parcours chaque enfant
        for (int i = 0; i < nl.getLength(); i++) {
            noeud = nl.item(i);
            // System.out.println(n.getNodeName());
            // si on recherche un attribut (type)
            if (type == Node.ATTRIBUTE_NODE) {
                // on verifie que le noeud a des attributs
                if (noeud.hasAttributes()) {
                    // on recupere la liste des attributs
                    NamedNodeMap attrs = noeud.getAttributes();
                    // pour chaque attribut
                    for (int j = 0; j < attrs.getLength(); j++) {
                        attr = attrs.item(j);
                        // on cherche l'attribut correspondant a notre recherche
                        // (element)
                        if (element.equals(attr.getNodeName())) {
                            // on regarde si l'attribut a la valeur recherchée
                            // (contains)
                            if (contains.equals(attr.getNodeValue())) {
                                return noeud;
                            }
                        }
                        // System.out.println(attr.item(j).getNodeName());
                    }
                }
            }
            // si on recherche un texte dans un noeud
            if (type == Node.TEXT_NODE) {
                // on regarde si le noeud correspond a celui de notre recherche
                // (element)
                if (element.equals(noeud.getNodeName())) {
                    // on effectue une recherche dans tous les champs texte du
                    // noeud
                    if (contains == null) {
                        return noeud;
                    } else {
                        if (noeud.getNodeValue().contains(contains)) {
                            return noeud;
                        }
                        // this.setNoeud(noeud);
                        // Node text = extractNextText();
                        // while ((text = extractNextText())!= null) {
                        // if (text.getNodeValue().startsWith(contains)) {
                        // return text;
                        // }
                        // }
                    }
                }
            }
            // si le noeud a encore des enfants on relance la recherche
            // récursivement
            if (noeud.hasChildNodes()) {
                noeud = this.search(noeud, element, contains, type);
                if (noeud != null) {
                    return noeud;
                }
            }
        }
        return null;
    }

    /**
     * Renvoie le noeud frere suivant qui a le meme nom que celui fourni en
     * parametre
     * 
     * @param noeud
     *            le noeud a partir du quelle on navigue
     * @return le noeud frere de meme nom suivant
     */
    private Node nextSameNode(Node noeud) {
        String nodeName = noeud.getNodeName();
        do {
            noeud = noeud.getNextSibling();
        } while (noeud != null && !noeud.getNodeName().equals(nodeName));
        return noeud;
    }

    /**
     * Renvoie le noeud frere suivant en sautant les freres correspondant au
     * nombre passer en parametre
     * 
     * @param noeud
     *            le noeud a partir duquel on navigue
     * @param saut
     *            le nombre de saut
     * @return le noeud frere de meme nom
     */
    private Node nextSameNode(Node noeud, int saut) {
        Node retour = null;
        saut++;
        for (int i = 0; i < saut; i++) {
            retour = nextSameNode(noeud);
        }
        return retour;
    }

    /**
     * Renvoie el frere suivant ayant le nom passer en parametre
     * 
     * @param noeud
     *            le noeud a partir duquel on navigue
     * @param nodeName
     *            le nom du noeud frere suivant que l'on souhaite
     * @return le noeud frere dont le nom correspond a la recherche
     */
    private Node nextNode(Node noeud, String nodeName) {
        do {
            noeud = noeud.getNextSibling();

        } while (noeud != null && !noeud.getNodeName().equals(nodeName));
        return noeud;
    }

    /**
     * Renvoie le noeud directement frere a celui qu'on envoie en parametre
     * 
     * @param noeud
     *            a partir duquel on navigue
     * @return le noeud frere suivant
     */
    private Node nextNode(Node noeud) {
        return noeud.getNextSibling();
    }

    /**
     * Retourne le noeud frere suivant en sautant certain noeud
     * 
     * @param noeud
     *            le noeud a partir duquel on navigue
     * @param saut
     *            le nombre de frere a sauter
     * @return le frere suivant apres le nombre de saut voulu
     */
    private Node nextNode(Node noeud, int saut) {
        Node retour = null;
        saut++;
        for (int i = 0; i < saut; i++) {
            retour = nextNode(noeud);
        }
        return retour;
    }

    /**
     * Extrait le premier texte brut trouvé directement en dessous du noeud
     * donnée Si aucun texte n'est trouvé la méthode renvoie une chaine vide
     * 
     * @param noeud
     *            le noeud sur lequel on cherche du texte brut
     * @return le texte trouvé ou une chaine vide le cas échéant
     */
    private Node extractText() {
        this.setIterateur(0);
        return extractNextText();
    }

    /**
     * Extrait le texte brut trouvé directement en dessous du noeud donnée a la
     * position désirée indiquée en parametre. Si aucun texte n'est trouvé la
     * méthode renvoie une chaine vide
     * 
     * @param noeud
     *            le noeud sur lequel on cherche du texte brut
     * @return le texte trouvé ou une chaine vide le cas échéant
     */
    private Node extractText(int iterateur) {
        this.setIterateur(iterateur);
        return extractNextText();
    }

    /**
     * Extrait le prochain texte brut trouvé directement en dessous du noeud
     * donnée. Si aucun texte n'est trouvé la méthode renvoie une chaine vide
     * 
     * @param noeud
     *            le noeud sur lequel on cherche du texte brut
     * @return le texte trouvé ou une chaine vide le cas échéant
     */
    private Node extractNextText() {
        NodeList listeFils = this.noeud.getChildNodes();
        Node fils;
        int j = 0;
        for (int i = 0; i < listeFils.getLength(); i++) {
            fils = listeFils.item(i);
            if ("#text".equals(fils.getNodeName())) {
                if (this.iterateur == j) {
                    this.iterateur++;
                    return fils;
                }
                j++;
            }
        }
        return null;

    }

    /**
     * Setter de l'attribut noeud, remet l'iterateur de texte a zero lorsque le
     * noeud est modifié
     * 
     * @param noeud
     *            the noeud to set
     */
    public void setNoeud(Node noeud) {
        if (noeud == null) {
            System.err.println("NOEUD NULL");
        }
        this.noeud = noeud;
        this.setIterateur(0);
    }

    /**
     * @param iterateur
     *            the iterateur to set
     */
    public void setIterateur(int iterateur) {
        this.iterateur = iterateur;
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
