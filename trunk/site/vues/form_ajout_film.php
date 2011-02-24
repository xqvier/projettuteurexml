<?php 
	echo "<strong>Ajoutez un film quelconque ?</strong><br/><span style='font-size:10px'>(attention de ne pas faire d'erreurs au nom)</span><br/><br/>";
	echo "<form action='index.php' method='get'>";
	echo "<input type='hidden' name='page' value='ajouter'>";
	echo "<input type='text' name='ajout'>";
	echo "<input type='submit' value='ok'>";
	echo "</form>";
?>