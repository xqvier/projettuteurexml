<?php

class Mgenres {
	public function select_genres(){
		//Connexion à la base 
		$bd = new Mconnect();
		
		$connect=$bd->getRessource();
		
		$stmt = ociparse($connect,"select NOM_GENRE, ID_GENRE from GENRE ORDER BY NOM_GENRE"); 
		//On parse la requête à effectuer sans oublier de lui passer la chaine de connexion en paramêtre 

		ociexecute($stmt,OCI_DEFAULT); 
		//On execute la requête en lui passant l'option OCI_DEFAULT 
		
		$bd->close(); 
		
		$tab = Mutilitaire::tableau($stmt);
		return $tab;
	}
}
?>