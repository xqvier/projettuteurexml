<?php

	foreach ($tab as $film) {
		echo "<a href='index.php?page=genres&genre=".$film['ID_GENRE']."'><div class='genre'>";
		echo $film['NOM_GENRE']."<br/>";
		echo "</div></a>";
	}

?>