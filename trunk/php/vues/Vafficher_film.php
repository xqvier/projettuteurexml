	<script>		$(function() {			$("#realisateurs").hide();			$("#acteurs").hide();			$("#btn_acteur").click(function(){				$("#acteurs").show("blind");			});			$("#btn_realisateur").click(function(){				$("#realisateurs").show("blind");			});		});	</script><?php	foreach ($tab as $film) {		echo "<div class='detail_film'>";		echo "<div class='titre'><strong>".$film['TITRE']."</strong></div>";		echo "<div class='resume'>R�sum� : ".$film['RESUME']."<br/>";				/*affichage des acteurs*/		if ($acteurs != NULL){			echo "<br/><a href='#' id='btn_acteur'>Acteur(s)</a><div id='acteurs'>";			$lastacteur = (count($acteurs)-1);			$i=0;			foreach ($acteurs as $acteur) {				echo "<a href='index.php?page=films&acteur=".$acteur['ID_PERS']."'>".$acteur['PRENOM_PERS']." ".$acteur['NOM_PERS']."</a>";				if ($i != $lastacteur){					echo " | ";				} else {					echo ". ";				}				$i++;			}			echo "</div>";		}				/*affichage des realisateurs*/		$lastrealisateur = (count($realisateurs)-1);		$j=0;		if ($realisateurs != NULL){			echo "<br/><a href='#' id='btn_realisateur'>R�alisateur(s)</a><div id='realisateurs'>";			foreach ($realisateurs as $realisateur) {				echo "<a href='index.php?page=films&realisateur=".$realisateur['ID_PERS']."'>".$realisateur['PRENOM_PERS']." ".$realisateur['NOM_PERS']."</a>";				if ($j != $lastrealisateur){					echo " | ";				} else {					echo ". ";				}				$j++;			}			echo "</div><br/><br/>";		}				/*notation + barre des details avec annee et duree du film*/		include('Vnotation.php');		echo "</div>";		echo "<div class='detail'>dur�e : ".$film['DUREE']."min | ann�e : ";		echo $film['ANNEE']."</div>";		echo "</div>";				/*commentaire*/		$tabPseudo = Xml::decoupage(htmlentities($film['PSEUDO']),"pseudo");		$tabCom = Xml::decoupage(htmlentities($film['COMMENTAIRE']), "com");		$tabNote = Xml::decoupage(htmlentities($film['NOTE']), "note");		$tabSource= Xml::decoupage(htmlentities($film['SOURCE']), "source");				for ($i=0; $i<count($tabPseudo); $i++){			echo "<div class='commentaire'>";			echo "<div class='pseudo'><strong>".$tabPseudo[$i]."</strong>";			/*--------note du commentaire-------*/			echo "<div class='note_commentaire'>";			$etoilePleine = $tabNote[$i];			for($j=0;$j<$etoilePleine;$j++){				echo "<img class='etoile' src='vues/img/etoilepleine.jpg'>";			}			$etoileVide = 10- $etoilePleine;			for($j=0;$j<$etoileVide;$j++){				echo "<img class='etoile' src='vues/img/etoilevide.jpg'>";			}			echo "(".$tabNote[$i]."/10)</div>";						echo "</div>";				echo "<div class='com'>";			echo "".$tabCom[$i]."</div>";				echo "<div class='source'>Cet commentaire est tir� de : ".$tabSource[$i]."</div>";				echo "</div>";		}	}	?>