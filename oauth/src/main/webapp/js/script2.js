var root = 'http://localhost:8080/oauth/webapi/users/';

window.onload = function() {
	console.log("ping onload");	
}

function highlightResult() {
	$('#result').each(function(i, block) {
		hljs.highlightBlock(block);
	});
}

function prepareJson(str) {
	var newstr =  str.replace(/\n/g, '<br/>')
					.replace(/\\n/g, ' ')
					.replace(/\t/g, '&nbsp;&nbsp;');
	return newstr
}

$(document).ready(function(){
	console.log("ping onload2");
	$.ajax({
			url: root,
			method: 'GET'
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t');
				str = prepareJson(str);
				$('#result').html(str);
				highlightResult();
         });
});
