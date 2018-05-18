function keyUpFunction() {
	window.sessionStorage.setItem('text', document.querySelector('input').value);
	console.log(window.localStorage)
}

window.addEventListener('storage', function(event) {
	document.querySelector('#inputText').innerHTML = event.newValue;
})