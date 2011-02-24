<?php
class genres {
	
	public function genres(){
		$genres = new Mgenres ();
		$tab = $genres->select_genres();
		
		include_once('vues/Vgenres.php');
		
	}
}
?>