package connection;

import java.sql.*;


/**
 * Classe utilitaire SQL gerant :
 * <li> les connexion avec la base de donnée Oracle, mysql ... </li>
 * <li>permettant également de réaliser des requete sql simple et dynamique avec JDBC< </li> 
 * * @author Peji
 */
public class ConnexionRealisationJDBC {
	
	/** Permet la connexion */
	private static Connection cnx;
	
	/** Requete static */
	private static Statement stmt;
	
	/** Requete dymanique*/
	private static CallableStatement calStmt;
	
	/**
	 * Connexion à une base de donnée Oracle
	 * Adresse par default IUT rodez
	 */
	public static void connextionOracle(String id, String mdp) throws SQLException {
		// Chargement du pilote oracle
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		// Chaine de connexion a la base de donnée de l'iut de rodez
		// thin driver de type 4 --> Le driver est entièrement écrit en Java
		String url= "jdbc:oracle:thin:@oracle.dometud.iut-rodez.local:1521:XE";
		// Ouverture de la connection
		cnx = DriverManager.getConnection(url, id, mdp);
		stmt = cnx.createStatement();
	}
	
	/**
	 * Connexion à une base de donnée mySQL
	 * Adresse par défaut serveur local 
	 */
	public static void connexionMySQL(String id, String mdp) throws SQLException, 
						InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Chargement du pilote MySQL
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// Chaine de connexion a la base de donnée 
		String url = "jdbc:mysql://localhost:3306/NomDeLaBase";
		cnx = DriverManager.getConnection(url, "root", "");
		stmt = cnx.createStatement();
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
	 * Appel à une fonction SQL
	 * @param requete dont on souhaite obtenir le resutat
	 * @return le resultat de la requete executée
	 */
	public ResultSet callStatement(String requete) throws SQLException {
		calStmt = cnx.prepareCall(requete);
		return calStmt.executeQuery();
	}
	
	
	/**
	 * Fermeture de la connexion
	 */
	public void close() throws SQLException {
		stmt.close();
		calStmt.close();
		cnx.close();
	}
}
