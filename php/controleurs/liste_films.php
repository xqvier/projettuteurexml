<?php
class liste_films {

	public function parNom ($rech, $nom){
		switch ($rech){
			case "genre": 
				$tab = Mfilms::parGenres($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "alphabet": 
				$tab = Mfilms::parAlphabet($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "date": 
				echo "date";
				$tab = Mfilms::parDate($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "nom": 
				echo "nom";
				$tab = Mfilms::parNom($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "acteur": 
				$tab = Mfilms::parActeur($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "realisateur": 
				$tab = Mfilms::parRealisateur($nom);
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "everything": 
				$tab = Mfilms::everything();
				$nb_element_par_page =  Mutilitaire::configuration("nb_pagination");
				$nb_pages = ceil(count($tab)/$nb_element_par_page);
				$_SESSION['tab'] = $tab;
				$_SESSION['nbpages'] = $nb_pages;
				$_SESSION['elemparpage'] = $nb_element_par_page;
				if ($tab == NULL){
					include('vues/Vfilm_absent.php');
				} else {
					include('vues/Vfilm.php');
				}
			break;
			case "id": 
				$tab = Mfilms::parId($nom);
				$acteurs = Mpersonne::acteurParId($nom);
				$realisateurs = Mpersonne::realisateurParId($nom);
				include('vues/Vafficher_film.php');
			break;
		}
		
	}
}
?>