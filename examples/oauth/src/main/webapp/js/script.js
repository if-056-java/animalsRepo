var root = 'http://localhost:8080/oauth/webapi/login/';

function FBLog() {
$('.btn-facebook').click(function() {
		console.log("ping");
	   window.location = root + "facebook";

	});
} 

function googleLog() {
$('.btn-google').click(function() {
		console.log("ping");
	   window.location = root + "google";

	});
}

function gitLog() {
$('.btn-github').click(function() {
		console.log("ping");
	   window.location = root + "github";

	});
}

function twitterLog() {
$('.btn-twitter').click(function() {
		console.log("ping");
	   window.location = root + "twitter";

	});
}    


window.onload = function() {
	googleLog();
	FBLog();
	gitLog();
	twitterLog();
}




