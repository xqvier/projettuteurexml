<html>
  <body>
  <?php
$connect = oci_connect("lp10","d3whrc2","iutdb"); 
//Connexion à la base 

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

ocilogoff($connect); 
//On se déconnecte du serveur
	?>
  </body>
</html>