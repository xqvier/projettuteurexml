/*
 * DataBase.java 	9 mars 2011
 * Projet_XML_recuperation
 */ 
package lpd2i.recuperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe de gestion de connexion a la base et d'envoie des requetes SQL
 * @author Xavier Mourgues
 * @version 0.1 
 */
public class DataBase {
    /** login de connexion a la base */
    private final static String LOGIN = "xmlp2";
    
    /** mot de passe de connexion a la base */
    private final static String PASSWORD = "mlp42";
    
    /** Permet la connexion */
    private static Connection cnx;
    
    /** Requete static */
    private static Statement stmt;
    
    
    /**
     * Connexion à une base de donnée Oracle
     * Adresse par default IUT rodez
     */
    public void connextionOracle() throws SQLException {
            // Chargement du pilote oracle
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            // Chaine de connexion a la base de donnée de l'iut de rodez
            // thin driver de type 4 --> Le driver est entièrement écrit en Java
            String url= "jdbc:oracle:thin:@oracle.dometud.iut-rodez.local:1521:XE";
            // Ouverture de la connection
            cnx = DriverManager.getConnection(url, LOGIN, PASSWORD);
            stmt = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    /**
     * Execution d'une requete simple
     * @param requete dont on souhaite obtenir le resutat
     * @return le resultat de la requete executée
     */
    public ResultSet executeRequete(String requete) throws SQLException {
            return stmt.executeQuery(requete);
    }
    
    /**
     * Fermeture de la connexion
     */
    public void close() throws SQLException {
            stmt.close();
            cnx.close();
    }
}
