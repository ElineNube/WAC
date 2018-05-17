var buttons = document.querySelectorAll("button");

for (var i = 0; i < buttons.length; i++ ) {
	buttons[i].onclick = function(e) {
		var input = document.querySelector("#display");
		var inputValue = input.innerHTML;
		var btnValue = this.innerHTML;
		
		document.getElementById("btn_clear").onclick = function() {
			input.innerHTML = "";
		};
		
		if (btnValue == "=") {
			var equation = inputValue;
			if(equation) {
				input.innerHTML = eval(equation);
			}
		}
		else {
				input.innerHTML += btnValue;
			}
			
	}
}