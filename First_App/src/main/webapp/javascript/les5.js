function initPage() {
	fetch("https://ipapi.co/json/")
		.then(response => response.json())
		.then(function(myJson) {
			document.querySelector("#landcode").innerHTML = myJson.country;
			document.querySelector("#land").innerHTML = myJson.country_name;
			document.querySelector("#regio").innerHTML = myJson.region;
			document.querySelector("#stad").innerHTML = myJson.city;
			document.querySelector("#postcode").innerHTML = myJson.postal;
			document.querySelector("#latitude").innerHTML = myJson.latitude;
			document.querySelector("#longitude").innerHTML = myJson.longitude;
			document.querySelector("#ip").innerHTML = myJson.ip;
			
			
			showWeather(myJson.latitude, myJson.longitude, myJson.city);
			loadCountries();
			
			var stad = document.querySelector("#stad");
			stad.addEventListener("click", (function(){ 
				showWeather(myJson.latitude, myJson.longitude, myJson.city);
			}));
		});
}

function showWeather(latitude, longitude, city) {
	document.querySelector("#cityName").innerHTML = "Het weer in " + city;
	
	var weatherJson = JSON.parse(window.sessionStorage.getItem(city));
	
	var weatherJsonExist = true;
	if (weatherJson == null) {
		weatherJsonExist = false
	}
	
	if (weatherJsonExist &&
			(weatherJson.coord.lon < longitude+0.1 && weatherJson.coord.lon > longitude- 0.1) &&
			(weatherJson.coord.lat < latitude+0.1 && weatherJson.coord.lat > latitude- 0.1) &&
			!((new Date) - weatherJson.dt < (10*60 * 1000))) {
				console.log('Herkent!')
				document.querySelector("#temperatuur").innerHTML = weatherJson.main.temp;
				document.querySelector("#luchtvochtigheid").innerHTML = weatherJson.main.humidity;
				document.querySelector("#windsnelheid").innerHTML = weatherJson.wind.speed;
				document.querySelector("#zonsopgang").innerHTML = Unix_timestamp(weatherJson.sys.sunrise);
				document.querySelector("#zonsondergang").innerHTML = Unix_timestamp(weatherJson.sys.sunset);
			} else {
				fetch("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&APPID=3bdea21154e2c444801afd4a9bb03fa9&units=metric")
				.then(response => response.json())
				.then(function(myJson) {
					console.log("API CALL")
					window.sessionStorage.setItem(city, JSON.stringify(myJson));
					document.querySelector("#temperatuur").innerHTML = myJson.main.temp;
					document.querySelector("#luchtvochtigheid").innerHTML = myJson.main.humidity;
					document.querySelector("#windsnelheid").innerHTML = myJson.wind.speed;
					document.querySelector("#zonsopgang").innerHTML = Unix_timestamp(myJson.sys.sunrise);
					document.querySelector("#zonsondergang").innerHTML = Unix_timestamp(myJson.sys.sunset);
				});
			}
			}
	
	

function Unix_timestamp(t) {
	var dt = new Date(t*1000);
	var hr = dt.getHours();
	var m = "0" + dt.getMinutes();
	var s = "0" + dt.getSeconds();
	return hr+ ':' + m.substr(-2) + ':' + s.substr(-2);  
}

function loadCountries() {
var table = document.getElementById("table")
fetch("restservices/countries/")
.then(response => response.json())
.then(function(myJson) {
	
	for(let value of myJson){
		var row = table.insertRow(-1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		var cell5 = row.insertCell(4);
		var cell6 = row.insertCell(5);
		var cell7 = row.insertCell(6);
		
		cell1.innerHTML= value.Name;
		cell2.innerHTML= value.Capital;
		cell3.innerHTML= value.Region;
		cell4.innerHTML= value.Surface;
		cell5.innerHTML= value.Population;
		cell6.innerHTML= '<input type="button" value="Verwijderen" id="delete"></input>';
		cell7.innerHTML= '<input type="button" value="Wijzigen" id="update"></input>';
		
		row.addEventListener("click", (function(){ 
			showWeather(value.Lat, value.Ing, value.Capital);
		}));
		
		//Delete
		cell6.addEventListener("click", (function(){	
		var code = value.Code;
		var index = this.parentElement.rowIndex;
		var index2 = index - 1;
		fetch("restservices/countries/"+ code, { method: 'DELETE', headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")} })
		.then(function (response) {
		if (response.ok) { // response-status = 200 OK 
			console.log("Country deleted!");
			table.deleteRow(index)
		}
		else if (response.status == 404)
		console.log("Country not found!");
		else console.log("Cannot delete country!");
		})
		
		
	}));
		
		//Update
		cell7.addEventListener("click", (function() {
			var wijzigform = document.getElementById("wijzigform")
			var row = wijzigform.insertRow(0);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
			var cell5 = row.insertCell(4);
			var cell6 = row.insertCell(5);
			var cell7 = row.insertCell(6);
			
			cell1.innerHTML='<label>Naam</label><input type="text" name="name" value="'+ value.Name + '"></input>';
			cell2.innerHTML='<label>Hoofdstad</label><input type="text" name="capital" value="'+ value.Capital + '"></input>';
			cell3.innerHTML='<label>Regio</label><input type="text" name="region" value="'+ value.Region + '"></input>';
			cell4.innerHTML='<label>Oppervlakte</label><input type="text" name="surface" value="'+ value.Surface + '"></input>';
			cell5.innerHTML='<label>Inwoners</label><input type="text" name="population" value="'+ value.Population + '"></input>';
			cell6.innerHTML= '<input type="button" value="Opslaan" id="save"></input>';
			cell7.innerHTML= '<input type="button" value="Annuleren" id="exit"></input>';
			
			var id = this.parentElement.rowIndex;
			console.log(id)
			
			document.querySelector("#save").addEventListener("click", function () {
				var code = value.Code;
				var formData = new FormData(document.querySelector("#PUTform"));
				var encData = new URLSearchParams(formData.entries());
				
				fetch("restservices/countries/"+ code, { method: 'PUT', body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")}})
				.then(response => response.json())
				.then(function(myJson) { 
					var table = document.getElementById("table");
					console.log(myJson.name)
					table.rows[id].cells[0].innerHTML = myJson.name;
					table.rows[id].cells[1].innerHTML = myJson.capital;
					table.rows[id].cells[2].innerHTML = myJson.region;
					table.rows[id].cells[3].innerHTML = myJson.surface;
					table.rows[id].cells[4].innerHTML = myJson.population;
					console.log(myJson); 
					wijzigform.deleteRow(this);
					})
			});
			
			document.querySelector("#exit").addEventListener("click", function () {
				wijzigform.deleteRow(this);
			});
		}))
	}
	
});

}

document.querySelector("#post").addEventListener("click", function() {
	var formData = new FormData(document.querySelector("#form2"));
	var encData = new URLSearchParams(formData.entries());
	fetch("restservices/countries", { method: 'POST', body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")}} )
    .then(response => response.json())
	.then(function(myJson) { 
		var table = document.getElementById("table");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		var cell5 = row.insertCell(4);
		var cell6 = row.insertCell(5);
		var cell7 = row.insertCell(6);
		
		cell1.innerHTML= myJson.name;
		cell2.innerHTML= myJson.capital;
		cell3.innerHTML= myJson.region;
		cell4.innerHTML= myJson.surface;
		cell5.innerHTML= myJson.population;
		cell6.innerHTML= '<input type="button" value="Verwijderen" id="delete"></input>';
		cell7.innerHTML= '<input type="button" value="Wijzigen" id="update"></input>';
		
		console.log(myJson); })
	console.log("saved new country")
	
	
	});
























