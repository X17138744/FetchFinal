<?php 
$con=mysqli_connect("localhost","username","password","databasename");
$username = $_POST["username"];
$email = $_POST["email"];
$password =$_POST["password"];
$sql = "INSERT INTO tablename(username,email,password)
VALUES ('$username','$email','$password')"
;
$result = mysqli_query( $con,$sql );
if($result) {
echo "registered successfully";
}else {
echo "some error occured";
}
?>
