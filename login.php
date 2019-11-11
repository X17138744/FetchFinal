<?php 
$username = $_POST["username"];
$password = $_POST["password"];
$con=mysqli_connect("localhost","username","password","databasename");
$sql = "SELECT * FROM tablename WHERE  username = '$username' AND password = '$password'";
$result = mysqli_query($con,$sql);
if($result->num_rows > 0){
echo "logged in successfully" ;
}else{
   echo "user not found";
}
?>
