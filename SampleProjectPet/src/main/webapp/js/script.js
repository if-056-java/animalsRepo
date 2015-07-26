var root = 'http://localhost:8080/SampleProjectPet/webapi/pets/';

function highlightResult() {
	$('#result').each(function(i, block) {
		hljs.highlightBlock(block);
	});
}

function getAllPets() {
	$('#getAll').click(function() {

		$.ajax({
			url: root,
			method: 'GET'
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
			highlightResult();
        });
    });
}        


function getPet() {
	$('#getPet').click(function() {		
		
		var petId=$('#petIdGet').val();
		console.log(petId);

		$.ajax({
			url: root + petId,
			method: 'GET'
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
				highlightResult();
         	});
    });	
}

function getPetOw() {
	$('#getPetOw').click(function() {		
		
		var petId=$('#petIdOwGet').val();
		console.log(petId);

		$.ajax({
			url: root + petId + "/owner",
			method: 'GET'
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
				highlightResult();
         	});
    });	
}

function postPet() {
	$('#postPet').click(function() {
		var petId=$("#petIdP").val(); 
		var petOwId=$("#petOwIdP").val(); 
		var petType=$("#petTypeP").val(); 
		var petOwName=$("#petOwNameP").val(); 
		var petSize=$("#petSizeP").val(); 
		var petOwAd=$("#petOwAdP").val(); 

		var params = ('id='+ petId + '&type=' + petType + '&size=' + petSize + '&ownerName=' + petOwName+ '&ownerID=' + petOwId+ '&ownerAdress=' + petOwAd );

		console.log(params);

		$.ajax({
			url: root + "pet",
			method: 'POST',
			data: params
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
				highlightResult();
         	});		

		
	});
}

// POST with JSON
function postPet2() {
	$('#postPet2').click(function() {
		var petId=$("#petIdP").val(); 
		var petOwId=$("#petOwIdP").val(); 
		var petType=$("#petTypeP").val(); 
		var petOwName=$("#petOwNameP").val(); 
		var petSize=$("#petSizeP").val(); 
		var petOwAd=$("#petOwAdP").val(); 

		var string = ('{"id" :' + petId +', "type" : ' + petType + ', "owner":{"name":' + petOwName + ', "id" : ' + petOwId + ', "adress" : ' + petOwAd + '}, "size" : ' + petSize + '}' );

//		var json = $.parseJSON(string); no need!!!

		console.log(string);
		console.log(json);

		$.ajax({			
			method: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			url: root + "pet",
			data: string			
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
				highlightResult();
         	});			
	});
}


//problem with response. In previous javascript version all worked correctly.
function delPet() {
	$('#delPet').click(function() {		
		
		var petId=$('#petDelId').val();
		console.log(petId);

		$.ajax({
			url: root + petId,
			method: 'DELETE'
			}).then(function() {
				//$('#result').val()="ok";
				document.getElementById("result").innerHTML="smth happened";

				highlightResult();

		});
	});	
}

function updatePet() {
	$('#updPet').click(function() {		
		
		var petId=$('#petIdUp').val();
		var petType=$('#petTypeUp').val();
		console.log(petId + petType);
		var params = ('petId='+ petId + '&petType=' + petType);

		$.ajax({
			url: root + "pet",
			method: 'PUT',
			data: params
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t')
				$('#result').html(
					str.replace(/\n/g, '<br/>')
						.replace(/\\n/g, ' ')
						.replace(/\t/g, '&nbsp;&nbsp;')
				);
				highlightResult();
         	});
    });	
}




window.onload = function() {
	getAllPets();
	getPet();
	getPetOw();
	postPet();
	postPet2();
	delPet();
	updatePet();
}




// OLD SCHOOL JAVA SCRIPT
// var myRequest;

// if (window.XMLHttpRequest) {  
// 	myRequest = new XMLHttpRequest();
// 	} else if (window.ActiveXObject) { // if not, we're in IE
// 	myRequest = new ActiveXObject("Microsoft.XMLHTTP");
// 	}

// function getCallback(){
// 	myRequest.onreadystatechange = function(){
// 		console.log("We were called!");
// 		console.log(myRequest.readyState);
// 		if (myRequest.readyState === 4) {
		    	
// 		    var str = JSON.stringify(myRequest.responseText);
//           	var str2 =myRequest.responseText.replace(/,/g, ', <br/>');
//           	var str3 =str2.replace(/{/g, '{ <br/>');
//           	var str4 =str3.replace(/}/g, '<br/> }');
//           	var str5 =str4.replace(/\\n/g, '<br/>');
//             document.getElementById("result").innerHTML=str5;

// 		    //highlight
// 		    $('#result').each(function(i, block) {
//             	hljs.highlightBlock(block);
//          	});			        
// 		}
// 	}
// }

// function getAllPets() {
// 	document.getElementById("getAll").onclick = function() {
		
// 		getCallback();	
		
// 		myRequest.open('GET', 'http://localhost:8080/webapi/pets', true);
// 		myRequest.send(null);


// 	}
// }

// function getPet() {
// 	document.getElementById("getPet").onclick = function() {
		
// 		getCallback();

// 		var petId=document.getElementById("petIdGet").value; 
// 		console.log(petId);
// 		myRequest.open('GET', 'http://localhost:8080/webapi/pets/'+petId, true);
// 		myRequest.send(null);
// 	}
// }

// function getPetOw() {
// 	document.getElementById("getPetOw").onclick = function() {
		
// 		getCallback();

// 		var petId=document.getElementById("petIdOwGet").value; 
// 		console.log(petId);
// 		myRequest.open('GET', 'http://localhost:8080/webapi/pets/'+petId + '/owner', true);
// 		myRequest.send(null);
// 	}
// }

// function postPet() {
// 	document.getElementById("postPet").onclick = function() {
		
// 		getCallback();

// 		var petId=document.getElementById("petIdP").value; 
// 		var petOwId=document.getElementById("petOwIdP").value; 
// 		var petType=document.getElementById("petTypeP").value; 
// 		var petOwName=document.getElementById("petOwNameP").value; 
// 		var petSize=document.getElementById("petSizeP").value; 
// 		var petOwAd=document.getElementById("petOwAdP").value; 

// 		var params = ('id='+ petId + '&type=' + petType + '&size=' + petSize + '&ownerName=' + petOwName+ '&ownerID=' + petOwId+ '&ownerAdress=' + petOwAd );

// 		console.log(params);		

// 		myRequest.open('POST', 'http://localhost:8080/webapi/pets/pet', true);
// 		myRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
// 		//myRequest.setRequestHeader("Content-length", params.length);
// 		//myRequest.setRequestHeader("Connection", "close");

// 		myRequest.send(params);
// 	}
// }

// function delPet() {
// 	document.getElementById("delPet").onclick = function() {
		
// 		getCallback();

// 		var petId=document.getElementById("petDelId").value; 
// 		console.log(petId);
// 		myRequest.open('DELETE', 'http://localhost:8080/webapi/pets/'+petId, true);
// 		myRequest.send(null);
// 	}
// }

// function updatePet() {
// 	document.getElementById("updPet").onclick = function() {
		
// 		getCallback();

// 		var petId=document.getElementById("petIdUp").value; 
// 		var petType=document.getElementById("petTypeUp").value;

// 		var params = ('petId='+ petId + '&petType=' + petType); 

// 		console.log(params);

// 		myRequest.open('PUT', 'http://localhost:8080/webapi/pets/pet', true);
// 		myRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");

// 		myRequest.send(params);
// 	}
// }




// window.onload = function() {
// 	getAllPets();
// 	getPet();
// 	getPetOw();
// 	postPet();
// 	delPet();
// 	updatePet();
// }



