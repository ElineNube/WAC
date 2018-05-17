function myIntervalFunction() {
	var text = document.getElementById('textArea').value;
	console.log(text);
}

var intervalID = setInterval(myIntervalFunction, 5000);