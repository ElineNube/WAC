function login(event) {
	
	var formData = new FormData(document.querySelector("#loginform"));
	var encData = new URLSearchParams(formData.entries());
	
	fetch("restservices/authentication", { method: 'POST', body: encData })
	.then(function(response) {
		if (response.ok) {
			GoToNextPage();
			return response.json();
		}
		else throw "Wrong username/password";
	})
	.then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))
	.catch(error => console.log(error));
}
document.querySelector("#login").addEventListener("click", login);

function GoToNextPage() {
	window.location="les5.html"
}