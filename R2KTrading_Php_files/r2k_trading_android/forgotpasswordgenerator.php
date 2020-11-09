<?php
include("config.php");
require 'sendmail.php';

$db = new DB_Connect();
$con = $db->connect();

$userName = $_POST['userName'];

$otp = rand(1000000, 10000000);

$subject = "R2KTrading App Forgot Password OTP";

$message = "Dear ".$userName.",<br/><br/>

Looks like you have forgotten your password. Please enter<br/>
the OTP below to authenticate yourself.<br/><br/>

OTP - ".$otp."<br/><br/>

Continue shopping with R2KTrading!<br/><br/>

Regards,<br/>
BlueBase Team";

$sql_query = "SELECT count(*), email FROM users WHERE username = '".$userName."'";

$res = mysqli_query($con, $sql_query);

$result = array("status"=>"false");

while($row = mysqli_fetch_array($res)){
    if($row['0'] == 1){
        $email = $row["email"];

        $sql_query = "SELECT count(*) FROM forgotpassword WHERE username = '".$userName."'";

        $res = mysqli_query($con, $sql_query);
        
        while($row = mysqli_fetch_array($res)){
            if($row["0"] == 0){
                $sql_query = "INSERT INTO forgotpassword (username, otp) VALUES ('".$userName."', '".$otp."')";

                $con->query($sql_query);

                if(sendMail($email, $subject, $message)){
                    $result = array("status"=>"true");
                }

            }else {
                $sql_query = "DELETE FROM forgotpassword WHERE username = '".$userName."'";

                $con->query($sql_query);

                $sql_query = "INSERT INTO forgotpassword (username, otp) VALUES ('".$userName."', '".$otp."')";

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