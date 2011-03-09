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
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * Classe utilitaire permettant de récuperer les informations a partir d'un site
 * TODO Commenter cette classe
 * 
 * @author Xavier Mourgues
 * @version 0.1
 */
public class Scraping {
    /**
     * URL de la page contenant les informations
     * @uml.property  name="url"
     */
    private URL url;

    /**
     * Base URL du site (exemple url : http://www.google.fr/index.php , baseurl : http://www.google.fr/)
     * @uml.property  name="baseurl"
     */
    private String baseurl;

    /**
     * Le fichier contenant uniquement les informations nécessaires
     * @uml.property  name="basenamefile"
     */
    private String basenamefile;

    /**
     * Le document xml a parser
     * @uml.property  name="xmlfile"
     * @uml.associationEnd  
     */
    private Document xmlfile;

    /**
     * Le site de provenance de la page
     * @uml.property  name="site"
     */
    private int site;

    /**
     * noeud de resultat de la recherche
     * @uml.property  name="noeud"
     * @uml.associationEnd  
     */
    Node noeud;


   /** Constante indiquant que la page provient d'allocine */
    public static final int ALLOCINE = 1;

    /** Constante indiquant que la page provient du site IMDB */
    public static final int CINEFIL = 2;

    /**
     * Constructeur a partir d'une url d'un site
     * 
     * @param url
     *            l'url du film
     * @throws MalformedURLException
     *             TODO
     */
    public Scraping(String url, int site) throws MalformedURLException {
        super();
        this.url = new URL(url);
        this.baseurl = this.url.getProtocol() + "://" + this.url.getHost();
        this.basenamefile = this.url.getPath().substring(1).replace("/", "-");
//        this.basenamefile = "test";
        this.site = site;
    }

    /**
     * 
     * TODO Commenter cette méthode
     * 
     * @return
     */
    public Film extractFilm() {
        this.loadContent();
        return this.parseFilmContent();
    }

    /**
     * 
     * TODO Commenter cette méthode
     * 
     * @return
     */
    public ArrayList<Personne> extractActors() {
        this.loadContent();
        return this.parseActorsContent();

    }

    /**
     * Charge les éléments de la page web et nettoie le fichier pour le rendre
     * parseable
     */
    private void loadContent() {
        Tidy ti = new Tidy();
        try {
            // Connection via le proxy de l'iut
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    "proxy", 8080));
            HttpURLConnection uc = (HttpURLConnection) this.url
                    .openConnection(proxy);

            // configuration du parsage
            ti.setConfigurationFromFile("tidyconfig.txt"); // DO NOT DELETE THIS LINE !!!
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
     * 
     * @return le film 
     */
    private Film parseFilmContent() {
        Node noeud;
        Film film = new Film();
        if (this.site == Scraping.ALLOCINE) {
            // on accede d'abord au noeud (tableau/div) contenant toutes les
            // données
            this.setNoeud(this.search(this.xmlfile, "class", "boxbasicctt",
                    Node.ATTRIBUTE_NODE));

            // on creer le nouveau document avec uniquement les infos utiles
            this.xmlfile = this.noeud.getOwnerDocument();

            // on parse ensuite toutes les données une a une

            // Titre du film
            this.setNoeud(search(this.xmlfile, "property", "v:name",
                    Node.ATTRIBUTE_NODE));
            this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            film.setTitre(this.noeud.getNodeValue());
            // Titre original du film :
            // this.setNoeud(search(this.xmlfile, "#text", "Titre original",
            // Node.TEXT_NODE));
            // this.setNoeud(nextNode(this.noeud));
            // this.setNoeud(search(this.noeud, "em", null, Node.TEXT_NODE));
            // this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            // this.film.setTitre(this.noeud.getNodeValue());

            // durée du film
            this.setNoeud(search(this.xmlfile, "#text", "Durée", Node.TEXT_NODE));
            String[] datas = this.noeud.getNodeValue().split(" ");
            String[] durees = datas[3].split("h");
            int heure = Integer.parseInt(durees[0]);
            int min = Integer.parseInt(durees[1].split("min")[0]);
            film.setDuree((heure * 60) + min);

            // annee
            this.setNoeud(nextNode(this.noeud, "a"));
            this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            film.setAnnee(Integer.parseInt(this.noeud.getNodeValue()));

            // note du site
            // TODO gérer si aucune note n'a été donné
            this.setNoeud(search(this.xmlfile, "class", "notationbar",
                    Node.ATTRIBUTE_NODE));
            this.setNoeud(search(this.noeud, "class", "notezone",
                    Node.ATTRIBUTE_NODE));
            this.setNoeud(search(this.noeud, "class", "moreinfo",
                    Node.ATTRIBUTE_NODE));
            this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            film.setNoteSite(Float.parseFloat(this.noeud.getNodeValue().split(
                    "[(]")[1].split("[)]")[0].replace(',', '.')) * 2);

            // Synopsis
            this.setNoeud(search(this.xmlfile, "property", "v:summary",
                    Node.ATTRIBUTE_NODE));
            this.setNoeud(search(this.noeud, "#text", null, Node.TEXT_NODE));
            film.setResume(this.noeud.getNodeValue());

            // Genres
            this.setNoeud(search(this.xmlfile, "#text", "Genre :",
                    Node.TEXT_NODE));
            this.setNoeud(this.noeud.getParentNode());
            this.setNoeud(search(this.noeud, "href", "/film/tous/genre-",
                    Node.ATTRIBUTE_NODE));
            noeud = search(this.noeud, "#text", null, Node.TEXT_NODE);
            film.addGenre(noeud.getNodeValue());
            while ((this.noeud = nextSameNode(this.noeud)) != null
                    && this.noeud.hasAttributes()
                    && this.noeud.getAttributes().item(0).getNodeValue()
                            .contains("/film/tous/genre-")) {
                noeud = search(this.noeud, "#text", null, Node.TEXT_NODE);
                film.addGenre(noeud.getNodeValue());
            }

            // Réalisateurs
            this.setNoeud(search(this.xmlfile, "rel", "v:directedBy",
                    Node.ATTRIBUTE_NODE));
            noeud = search(this.noeud, "#text", null, Node.TEXT_NODE);
            String[] realisateur = noeud.getNodeValue().split(" ");
            film.addRealisateur(new Personne(realisateur[0], realisateur[1]));
            while ((this.noeud = nextSameNode(this.noeud)) != null
                    && this.noeud.hasAttributes()
                    && getAttribute(this.noeud, "rel").getNodeValue().equals(
                            "v:directedBy")) {
                noeud = search(this.noeud, "#text", null, Node.TEXT_NODE);
                realisateur = noeud.getNodeValue().split(" ");
                film.addRealisateur(new Personne(realisateur[0], realisateur[1]));

            }

            // Acteurs
            this.setNoeud(search(this.xmlfile, "#text", "Avec", Node.TEXT_NODE));
            Scraping acteurs;
            while((this.noeud = nextNode(this.noeud, "a")) != null && this.noeud.hasAttributes() && !getAttribute(this.noeud, "href").getNodeValue().startsWith("/film/casting_gen_cfilm="));

            try {
                acteurs = new Scraping(baseurl + getAttribute(this.noeud, "href").getNodeValue(),
                        this.site);
                film.setActeurs(acteurs.extractActors());
            } catch (MalformedURLException e) {
//                 TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            // Commentaires TODO
        }
        return film;
    }

    /**
     * TODO Commenter cette méthode
     * 
     * @return
     */
    private ArrayList<Personne> parseActorsContent() {
        ArrayList<Personne> acteurs = new ArrayList<Personne>();
        if (this.site == Scraping.ALLOCINE) {
            // on accede d'abord au noeud (tableau/div) contenant toutes les
            // données
            this.setNoeud(this.search(this.xmlfile, "class", "colcontent",
                    Node.ATTRIBUTE_NODE));

            // on creer le nouveau document avec uniquement les infos utiles
            this.xmlfile = this.noeud.getOwnerDocument();
            
            // d'abord les acteurs dont la vue est avec une image (listofmicroviews)
            this.setNoeud(this.search(this.xmlfile, "id", "actors", Node.ATTRIBUTE_NODE));
            this.setNoeud(parentNode(this.noeud, "div"));
            this.setNoeud(nextNode(this.noeud));
            Node listofmicroviews = this.search(this.noeud, "class", "listofmicroviews", Node.ATTRIBUTE_NODE);
            Node noeud = this.search(listofmicroviews, "div", null, Node.TEXT_NODE);
            String acteur[];
            do {
                this.setNoeud(this.search(noeud,"h3", null, Node.TEXT_NODE));
                this.setNoeud(this.search(this.noeud, "a", null, Node.TEXT_NODE));
                this.setNoeud(this.search(this.noeud, "#text", null, Node.TEXT_NODE));
                acteur = this.noeud.getNodeValue().split(" ");
                acteurs.add(new Personne(acteur[0], acteur[1]));
            } while((noeud = nextNode(noeud))!= null);
            
            // ensuite les acteurs dont la vue est en liste
            Node actorlist = listofmicroviews.getParentNode();
            actorlist = nextSameNode(actorlist);
            noeud = this.search(actorlist, "tr", null, Node.TEXT_NODE);
            while((noeud = nextNode(noeud)) != null){
                this.setNoeud(this.search(noeud, "a", null, Node.TEXT_NODE));
                this.setNoeud(this.search(this.noeud,"#text", null, Node.TEXT_NODE));
                acteurs.add(new Personne(this.noeud.getNodeValue()));
            }
        }
        return acteurs;
    }

    /**
     * Recherche a partir des parametres un noeud d'un fichier xml correspondant
     * aux element de recherche et retourne ce noeud. Marche recursivement pour
     * naviguer dans les noeud en profondeur.
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
     * @return le point d'ancrage où toutes les informations interessantes sont
     */
    private Node search(Node start, String element, String contains, short type) {

        Node noeud = null;
        Node attr;
        // on prend la liste des enfants du noeud courant
        NodeList nl = start.getChildNodes();
        // on parcours chaque enfant
        for (int i = 0; i < nl.getLength(); i++) {
            noeud = nl.item(i);
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
                            // if (contains.equals(attr.getNodeValue())) {
                            if (attr.getNodeValue().contains(contains)) {
                                return noeud;
                            }
                        }
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



    private Node getAttribute(Node noeud, String attributeName) {
        if (noeud.hasAttributes()) {
            NamedNodeMap attributes = noeud.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.item(i).getNodeName().equals(attributeName)) {
                    return attributes.item(i);
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
     * Renvoie el frere suivant ayant le nom passer en parametre
     * 
     * @param noeud
     *            le noeud a partir duquel on navigue
     * @param nodeName
     *            le nom du noeud frere suivant que l'on souhaite
     * @return <li>le noeud frere dont le nom correspond a la recherche</li> <li>
     *         null sinon</li>
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
     * @return <li>le noeud frere suivant</li>
     *         <li>null sinon </li>
     */
    private Node nextNode(Node noeud) {
        return noeud.getNextSibling();
    }



    private Node parentNode(Node noeud, String nodeName){
        do{
            noeud = noeud.getParentNode();
        }
        while(noeud != null && !noeud.getNodeName().equals(nodeName));
        return noeud;
    }






    /**
     * Setter de l'attribut noeud, remet l'iterateur de texte a zero lorsque le noeud est modifié
     * @param noeud   the noeud to set
     * @uml.property  name="noeud"
     */
    public void setNoeud(Node noeud) {
        if (noeud == null) {
            System.err.println("NOEUD NULL");
        }
        this.noeud = noeud;
    }



    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Scraping [url=" + url + "]";
    }
}
