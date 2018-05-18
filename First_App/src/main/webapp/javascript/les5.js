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
		
		cell1.innerHTML= value.Name;
		cell2.innerHTML= value.Capital;
		cell3.innerHTML= value.Region;
		cell4.innerHTML= value.Surface;
		cell5.innerHTML= value.Population;
		
		row.addEventListener("click", (function(){ 
			showWeather(value.Lat, value.Ing, value.Capital);
		}));
	}
	
});

}