<?php
include("config.php");
require 'sendmail.php';

$db = new DB_Connect();
$con = $db->connect();

$userName = $_POST['userName'];
$password = $_POST['password'];
$email = $_POST['email'];
$role = $_POST['role'];

$otp = rand(1000000, 10000000);

$subject = "R2KTrading App OTP";

$message = "Dear ".$userName.",<br/><br/>

Welcome, to R2KTrading! Please enter<br/> 
the OTP below to signup to our services.<br/><br/>

OTP - ".$otp."<br/><br/>

We are thrilled to welcome you to R2KTrading!<br/><br/>

Regards,<br/>
BlueBase Team";

$sql_query = "SELECT count(*) FROM users WHERE username = '".$userName."'";

$res = mysqli_query($con, $sql_query);

$result = array("status"=>"false");

while($row = mysqli_fetch_array($res)){
    if($row["0"] == 0){
        $sql_query = "SELECT count(*) FROM signup WHERE username = '".$userName."'";

        $res = mysqli_query($con, $sql_query);
        
        while($row = mysqli_fetch_array($res)){
            if($row["0"] == 0){
                $sql_query = "INSERT INTO signup (username, password, email, otp, role) VALUES ('".$userName."', '".$password."', '".$email."', '".$otp."', '".$role."')";

                $con->query($sql_query);

                if(sendMail($email, $subject, $message)){
                    $result = array("status"=>"true");
                }
                
            }else{
                $sql_query = "DELETE FROM signup WHERE username = '".$userName."'";

                $con->query($sql_query);

                $sql_query = "INSERT INTO signup (username, password, email, otp, role) VALUES ('".$userName."', '".$password."', '".$email."', '".$otp."', '".$role."')";

                $con->query($sql_query);
                
                if(sendMail($email, $subject, $message)){
                    $result = array("status"=>"true");
                }
    
            }
        }
    }
}

echo json_encode([$result]);

mysqli_close($con);
?>