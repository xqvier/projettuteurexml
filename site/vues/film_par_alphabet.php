<?php
	echo "<fieldset><center><a href='index.php?page=films&initiale=09'>0-9 </a>";
	for ($i=ord("A");$i<ord("Z")+1;$i++) {
        echo "<a href='index.php?page=films&initiale=".chr($i)."'>".chr($i)."</a>";
    }
	echo "</center></fieldset>";
?>