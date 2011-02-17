<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css" href="vues/css/css.css"/>
<title>Projet xml</title>
</head>
<body>
 
 <ul>
	<a href="#"><li>Genres | </li></a>
	<a href="#"><li>Films | </li></a>
	<li>Rechercher : <input type="text" name="recherche"/></li>
  </ul> 
  
<?php
include ('modeles/Mconnect.php');

//Connexion à la base 
$bd = new Mconnect();
$connect=$bd->getRessource();

$stmt = ociparse($connect,"select * from PLANTE"); 
//On parse la requête à effectuer sans oublier de lui passer la chaine de connexion en paramêtre 

ociexecute($stmt,OCI_DEFAULT); 
//On execute la requête en lui passant l'option OCI_DEFAULT 


echo "Début----<br>\n\n"; 
  
while (ocifetch($stmt)){ //On parcourt les résultats 
  echo ociresult($stmt,1); //On récupère le premier champ de la ma_table 
  echo ociresult($stmt,2); //On récupère le deuxième champ de la ma_table 
} 
     
echo "<br>----fin\n\n"; 

$bd->close(); 
//On se déconnecte du serveur
	?>
  </body>
</html>