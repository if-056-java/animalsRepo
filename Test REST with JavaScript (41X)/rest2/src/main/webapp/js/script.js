var myRequest;

if (window.XMLHttpRequest) {  
	myRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // if not, we're in IE
	myRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}

function getCallback(){
	myRequest.onreadystatechange = function(){
		console.log("We were called!");
		console.log(myRequest.readyState);
		if (myRequest.readyState === 4) {
		    	
		    var str = JSON.stringify(myRequest.responseText);
          	var str2 =myRequest.responseText.replace(/,/g, ', <br/>');
          	var str3 =str2.replace(/{/g, '{ <br/>');
          	var str4 =str3.replace(/}/g, '<br/> }');
          	var str5 =str4.replace(/\\n/g, '<br/>');
            document.getElementById("result").innerHTML=str5;
            //problem with firefox perfomance...

		    //highlight
		    $('#result').each(function(i, block) {
            	hljs.highlightBlock(block);
         	});			        
		}
	}
}

function getAllPets() {
	document.getElementById("getAll").onclick = function() {
		
		getCallback();	
		
		myRequest.open('GET', 'http://localhost:8080/rest2/webapi/pets', true);
		myRequest.send(null);


	}
}

function getPet() {
	document.getElementById("getPet").onclick = function() {
		
		getCallback();

		var petId=document.getElementById("petIdGet").value; 
		console.log(petId);
		myRequest.open('GET', 'http://localhost:8080/rest2/webapi/pets/'+petId, true);
		myRequest.send(null);
	}
}

function getPetOw() {
	document.getElementById("getPetOw").onclick = function() {
		
		getCallback();

		var petId=document.getElementById("petIdOwGet").value; 
		console.log(petId);
		myRequest.open('GET', 'http://localhost:8080/rest2/webapi/pets/'+petId + '/owner', true);
		myRequest.send(null);
	}
}

function postPet() {
	document.getElementById("postPet").onclick = function() {
		
		getCallback();

		var petId=document.getElementById("petIdP").value; 
		var petOwId=document.getElementById("petOwIdP").value; 
		var petType=document.getElementById("petTypeP").value; 
		var petOwName=document.getElementById("petOwNameP").value; 
		var petSize=document.getElementById("petSizeP").value; 
		var petOwAd=document.getElementById("petOwAdP").value; 

		var params = ('id='+ petId + '&type=' + petType + '&size=' + petSize + '&ownerName=' + petOwName+ '&ownerID=' + petOwId+ '&ownerAdress=' + petOwAd );

		console.log(params);		

		myRequest.open('POST', 'http://localhost:8080/rest2/webapi/pets/pet', true);
		myRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		//myRequest.setRequestHeader("Content-length", params.length);
		//myRequest.setRequestHeader("Connection", "close");

		myRequest.send(params);
	}
}

function delPet() {
	document.getElementById("delPet").onclick = function() {
		
		getCallback();

		var petId=document.getElementById("petDelId").value; 
		console.log(petId);
		myRequest.open('DELETE', 'http://localhost:8080/rest2/webapi/pets/'+petId, true);
		myRequest.send(null);
	}
}

function updatePet() {
	document.getElementById("updPet").onclick = function() {
		
		getCallback();

		var petId=document.getElementById("petIdUp").value; 
		var petType=document.getElementById("petTypeUp").value;

		var params = ('petId='+ petId + '&petType=' + petType); 

		console.log(params);

		myRequest.open('PUT', 'http://localhost:8080/rest2/webapi/pets/pet', true);
		myRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");

		myRequest.send(params);
	}
}




window.onload = function() {
	getAllPets();
	getPet();
	getPetOw();
	postPet();
	delPet();
	updatePet();
}

