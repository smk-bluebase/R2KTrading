<?php
include("config.php");

$db = new DB_Connect();
$con = $db->connect();

$userName = $_POST["userName"];
$password = $_POST["password"];
 
$sql_query = "SELECT * FROM users WHERE username = '".$userName."' AND password = '".$password."'";
 
$res = mysqli_query($con, $sql_query);
 
$result=array("status"=>"false");
 
while($row = mysqli_fetch_array($res)){
	if ($row["role"] == 1 && $row['admin_access'] == 1){
		$result = array("status"=>"true", "role"=>1, "email"=>$row["email"]);
	}else{
		$result = array("status"=>"true", "role"=>0, "email"=>$row["email"]);
	}
}
 
echo json_encode([$result]);
 
mysqli_close($con);
?>