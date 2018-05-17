function initPage() {
	fetch("https://ipapi.co/json/")
		.then(response => response.json())
		.then(function(myJson) {
			document.querySelector("#landcode").innerHTML += myJson.country;
			document.querySelector("#land").innerHTML += myJson.country_name;
			document.querySelector("#regio").innerHTML += myJson.region;
			document.querySelector("#stad").innerHTML += myJson.city;
			document.querySelector("#postcode").innerHTML += myJson.postal;
			document.querySelector("#latitude").innerHTML += myJson.latitude;
			document.querySelector("#longitude").innerHTML += myJson.longitude;
			document.querySelector("#ip").innerHTML += myJson.ip;
			
			showWeather(myJson.latitude, myJson.longitude, myJson.city);
			loadCountries();
		});
}

function showWeather(latitude, longitude, city) {
	fetch("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&APPID=3bdea21154e2c444801afd4a9bb03fa9&units=metric")
	.then(response => response.json())
	
	.then(function(myJson) {
		document.querySelector("#temperatuur").innerHTML += myJson.main.temp;
		document.querySelector("#luchtvochtigheid").innerHTML += myJson.main.humidity;
		document.querySelector("#windsnelheid").innerHTML += myJson.wind.speed;
		document.querySelector("#zonsopgang").innerHTML += Unix_timestamp(myJson.sys.sunrise);
		document.querySelector("#zonsondergang").innerHTML += Unix_timestamp(myJson.sys.sunset);
		document.querySelector("#cityName").innerHTML += city;

	});
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
		for (let value of myJson) {
			fetch("restservices/countries/" +  value.Code)
				.then(response => response.json())
				.then(function(myJson) {
					var row = table.insertRow(-1);
					var clickedRow = document.querySelector("row");
					var cell1 = row.insertCell(0);
					var cell2 = row.insertCell(1);
					var cell3 = row.insertCell(2);
					var cell4 = row.insertCell(3);
					var cell5 = row.insertCell(4);
					
					cell1.innerHTML= myJson.Name;
					cell2.innerHTML= myJson.Capital;
					cell3.innerHTML= myJson.Region;
					cell4.innerHTML= myJson.Surface;
					cell5.innerHTML= myJson.Population;
					
					clickedRow.onclick() = function() {
						console.log("CLICKED");
					}
				})
		}

	});
}