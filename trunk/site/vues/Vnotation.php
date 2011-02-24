<?php
	/*---------note du site-----------*/
	echo "<div class='note'>note site : ";
		for($i=0;$i<$film['NOTES_SITE'];$i++){
			echo "<img class='etoile' src='vues/img/etoilepleine.jpg'>";
		}
		for($i=0;$i<(10-$film['NOTES_SITE']);$i++){
			echo "<img class='etoile' src='vues/img/etoilevide.jpg'>";
		}
	echo "(".$film['NOTES_SITE']."/10)";
	echo "</div>";
	/*---------note de l'utilisateur-----*/
	echo "<div class='note'>note utilisateur : ";
		for($i=0;$i<$film['NOTES_UTIL'];$i++){
			echo "<img class='etoile' src='vues/img/etoilepleine.jpg'>";
		}
		for($i=0;$i<(10-$film['NOTES_UTIL']);$i++){
			echo "<img class='etoile' src='vues/img/etoilevide.jpg'>";
		}
	echo "(".$film['NOTES_UTIL']."/10)";
	echo "</div>";
?>