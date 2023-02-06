function validateForm() {
console.log("Testing.");
  let x = document.forms["signupForm"]["password"].value;
  let y = document.forms["signupForm"]["cpassword"].value;
  if (x != y) {
  console.log("error");
  document.getElementById("error").innerHTML  = "!! Salle sahi password tho de de !!";
  }
}