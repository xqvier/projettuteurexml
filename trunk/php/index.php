<?php session_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css" href="vues/css/css.css"/>
<script src="jquery/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="jquery/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="jquery/autocomplete/jquery.autocomplete.js"></script>
<script>
	$(function(){
		$('#idrecherche').keyup(function(){
			$('#main').hide();
		});
		$('#idrecherche').focus(function(){
			$('#idrecherche').val("");
			$('#idrecherche').css("color","#000");
			$('#idrecherche').css("text-align","left");
		});
		if ($('#idrecherche').val("recherche")){
			$('#idrecherche').css("color","#666");
			$('#idrecherche').css("text-align","center");
		} 
	});
</script>
<title>Projet xml</title>

<?php 
/*------------------les includes------------------------*/
include_once ('modeles/Mconnect.php');
include_once ('modeles/Mutilitaire.php');
include_once ('modeles/Mgenres.php');
include_once ('modeles/Mfilms.php');
include_once ('modeles/Mpersonne.php');
include_once('controleurs/liste_genres.php');
include_once('jquery/js/function.php');
include_once('controleurs/liste_films.php');
include_once('controleurs/xml.php');
include_once('controleurs/java.php');
/*------------------------------------------------------*/

?>
</head>
<body>
 <ul>
	<form action="index.php" method="get">
		<a style="color:#FFF" href="index.php?page=genres"><li><strong>Genres </strong>| </li></a>
		<a style="color:#FFF" href="index.php?page=films"><li><strong>Films </strong>| </li></a>
		<a style="color:#FFF" href="index.php?page=ajouter"><li><strong>Ajouter un film</strong></li></a>
		<li style="margin-left:245px;">
			<input type="text" autocomplete="off" name="recherche" size="30" maxlength="30" id="idrecherche" value="recherche"
			onkeyup="sendData('recherche='+this.value,'Recherche.php','span_recherche');">
		</li>
	</form>
  </ul> 
  
  
<span id="span_recherche"></span> 
<?php

echo '<div id="main">';

if (isset($_GET['page'])){
	switch ($_GET['page']){
		case 'films':
			include('vues/film_par_alphabet.php');
			if (isset($_GET['initiale'])){
				liste_films::parNom('alphabet', $_GET['initiale']);
			} 
			if (isset($_GET['acteur'])){
				liste_films::parNom('acteur', $_GET['acteur']);
			} 
			if (isset($_GET['realisateur'])){
				liste_films::parNom('realisateur', $_GET['realisateur']);
			}
			break;
		case 'genres':
			if(isset($_GET['genre'])){
				liste_films::parNom("genre",$_GET['genre']);
			} else {
				$genres = new genres();
			}
			break;
		case 'film':
			liste_films::parNom("id",$_GET['id']);
			break;
		case 'ajouter':
			if (isset($_GET['ajout'])){
				java::resultats($_GET['ajout']);
			} else {
				java::formulaire();
			}
			break;
		default:
			echo "page index<br/>";
	}
} elseif (isset($_GET['numpage'])){
	include('vues/Vfilm.php');
} else {
	liste_films::parNom("everything","default");
}

echo "<div>";

	?>
	
	
  </body>
</html>