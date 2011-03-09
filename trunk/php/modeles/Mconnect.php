<?php

class Mconnect {
	
	private $ressource;
        
        public function Mconnect(){
			$this->ressource=$this->connection();
        }

		// Connexion, sélection de la base de données
        public function connection(){
        
			/*--------------- recherche de la configuration du fichier conf.init-----------------*/
			include_once 'Mutilitaire.php';
			$utilisateur = Mutilitaire::configuration('utilisateur');
			$mdp = Mutilitaire::configuration('mdp');
			$path = Mutilitaire::configuration('path');
			/*------------------------------------------------------------------------------*/
			
			$connect = oci_connect(trim($utilisateur),trim($mdp),trim($path));
			return $connect;
        }
        
        //Fermeture de la base de donnée
        public function close(){
			oci_close($this->getRessource());
        }
        
        // Retourne la ressource
        public function getRessource(){
			return $this->ressource;
        }

}

?>