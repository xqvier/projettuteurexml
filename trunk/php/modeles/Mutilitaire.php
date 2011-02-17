<?php
Class Mutilitaire{

        /**
         * permet de recuperer un element a une ligne donnee
         * dans le fichier conf.init
         * @param -> element recherché
         */
        public static function configuration($element){
                /*-------------test de lecture du fichier ini----------------*/
                                $fichier = 'conf.ini';
                                if(file_exists($fichier) && $fichier_lecture=file($fichier)){
                                        foreach($fichier_lecture as $ligne){
                                                $var = explode("=", $ligne);
                                                if ($var[0] == $element){
                                                        return $var[1];
                                                }
                                        }
                                } else {
                                        echo 'Erreur d\'ouverture et/ou de lecture du fichier conf.ini<br/>';
                                }
                /*-------------------------------------------------------------*/
        }
		
}
?>
