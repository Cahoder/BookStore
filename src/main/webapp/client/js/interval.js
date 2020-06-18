function djs(){
	var second = document.getElementById("interval");
	var svalue = second.innerHTML;
	var inter = setInterval(function(){
		svalue--;
		if(svalue==0){
			clearInterval(inter);
			window.location.href = second.parentNode.getAttribute("href");
		}
		second.innerHTML = svalue;
	},1000);
}
window.onload=djs;