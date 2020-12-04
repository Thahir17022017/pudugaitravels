



function closeDropDown(){

	
    if($('#button1').is(':visible'))
    {
        // Code
        console.log("from body");
        $('#navbarSupportedContent').removeClass('show');
        
   
    }
}


function loadImages()
{
//	console.log("Getting images");


	// var maskcloud = document.getElementById("maskcloud");
	// maskcloud.style.background = "url(https://www.pudugaitravels.com/img/cloud.png)";
	// maskcloud.classList.add("maskcloud");

	var slide1 = document.getElementById("slide1");
	var downloadImage1 = new Image();
    downloadImage1.onload = function(){
        slide1.src = this.src;
          
    };
	downloadImage1.src = "https://www.pudugaitravels.com/img/slide1.png";
	
	var slide2 = document.getElementById("slide2");
	var downloadImage2 = new Image();
    downloadImage2.onload = function(){
        slide2.src = this.src;
          
    };
	downloadImage2.src = "https://www.pudugaitravels.com/img/slide4.png";

	var slide3 = document.getElementById("slide3");
	var downloadImage3 = new Image();
    downloadImage3.onload = function(){
        slide3.src = this.src;
          
    };
	downloadImage3.src = "https://www.pudugaitravels.com/img/slide5.png";
	
	// var downloadPeopleImage = new Image();
    // downloadPeopleImage.onload = function(){
    //     slantedImg1.style.background = this.src;
          
    // };
    // downloadPeopleImage.src = "https://www.pudugaitravels.com/img/airport_indoors.png";





	var slantedImg1 = document.querySelector("section.slantedDiv1");
//	console.log("slantedImg1",slantedImg1);
	slantedImg1.style.background = "url(https://www.pudugaitravels.com/img/airport_indoors.png)";
	slantedImg1.style.backgroundPosition = "center";
    slantedImg1.style.backgroundAttachment = "fixed";
    slantedImg1.style.backgroundSize = "cover";
	slantedImg1.style.backgroundRepeat = "no-repeat";
	
	var slantedImg2 = document.querySelector("section.slantedDiv2");
//	console.log("slantedImg2",slantedImg2);
	slantedImg2.style.background = "url(https://www.pudugaitravels.com/img/money_transfer.png)";
	slantedImg2.style.backgroundPosition = "center";
    slantedImg2.style.backgroundAttachment = "fixed";
    slantedImg2.style.backgroundSize = "cover";
	slantedImg2.style.backgroundRepeat = "no-repeat";

	
	
	var enquiryImg = document.getElementById("enquiryImg");
	enquiryImg.style.background = "url(https://www.pudugaitravels.com/img/reception.png)";
	enquiryImg.style.backgroundSize = "cover";
	enquiryImg.style.backgroundRepeat = "no-repeat";
	enquiryImg.style.backgroundPosition = "center";

	var slantedImg3 = document.querySelector("section.slantedDiv3");
//	console.log("slantedImg3",slantedImg3);
	slantedImg3.style.background = "url(https://www.pudugaitravels.com/img/reception.png)";
	slantedImg3.style.backgroundPosition = "center";
    slantedImg3.style.backgroundAttachment = "fixed";
    slantedImg3.style.backgroundSize = "cover";
	slantedImg3.style.backgroundRepeat = "no-repeat";
	

	

}

$('#plane').one('animationend', function(){
//    console.log("plane animation end");
    var plane = document.getElementById("plane");
    var ticketContainer = document.getElementById("ticketContainer");
    var moneyContainer = document.getElementById("moneyContainer");

    plane.classList.remove("animated");
    plane.classList.remove("zoomInLeft");
    plane.classList.remove("delay-2s");
    plane.classList.add("animated");
    plane.classList.add("infinite");
    plane.classList.add("pulse");
    ticketContainer.style.display="block";
    ticketContainer.classList.add("animated");
    ticketContainer.classList.add("bounceInLeft");
    moneyContainer.style.display="block";
    moneyContainer.classList.add("animated");
    moneyContainer.classList.add("bounceInRight");



});

function sendQuery()
{
	// console.log("send button is clicked");

	var progressCircle = document.getElementById('progressCircle');
	progressCircle.style.visibility='visible';
	
	var phFlag=false;
	var emailFlag=false;
	
	var reqMsg = document.getElementById('reqMsg');
	
	
	
	var token = document.getElementById('g-recaptcha-response').value;
	// console.log("token",token);
//	document.getElementById('g-recaptcha-response').value="";
//	console.log("after clearing token",token);
	
	var name = document.getElementById('form100').value;
	// console.log("name",name);
//	document.getElementById('form100').value="";
	
	
	var phNumber = document.getElementById('form101').value;
	// console.log("phNumber",phNumber);
//	document.getElementById('form101').value="";
	
	var email = document.getElementById('form102').value;
	// console.log("email",email);
//	document.getElementById('form102').value="";
	
	var subject = document.getElementById('form103').value;
	// console.log("subject",subject);
//	document.getElementById('form103').value="";
	
	var message = document.getElementById('form104').value;
	// console.log("message",message);
//	document.getElementById('form104').value="";
	
	if(reqMsg.innerHTML==="Invalid Phone Number")
	{
		reqMsg.innerHTML = "Required fields are needed !!!!";
	}
	
	if(reqMsg.innerHTML==="Invalid Email Address")
	{
		reqMsg.innerHTML = "Required fields are needed !!!!";
	}
	
	if(reqMsg.innerHTML === "Failed to send message!!! Retry after sometime....")
	{
		reqMsg.innerHTML = "Required fields are needed !!!!";
	}

	if(reqMsg.innerHTML === "Message Sent Successfully !!!")
	{
		reqMsg.innerHTML = "Required fields are needed !!!!";
	}

	if(reqMsg.innerHTML === "Captcha Error!!! Re-Enter form again...")
	{
		reqMsg.innerHTML = "Required fields are needed !!!!";
	}
		
	if(reqMsg !== "" && reqMsg !== null)
	{
		if(reqMsg.style.visibility === "visible")
		{
			reqMsg.style.visibility="hidden";
		}

	}
	
	
	
	if(name === "" || name === undefined || name === null)
	{
		reqMsg.style.visibility="visible";
		progressCircle.style.visibility='hidden';
	}
	
	if(phNumber === "" || phNumber === undefined || phNumber === null)
	{
		reqMsg.style.visibility="visible";
		progressCircle.style.visibility='hidden';
	}
	
	
	
	if(email === "" || email === undefined || email === null)
	{
		
		reqMsg.style.visibility="visible";
		progressCircle.style.visibility='hidden';
	}
	

	
	
	if(subject === "" || subject === undefined || subject === null)
	{
		reqMsg.style.visibility="visible";
		progressCircle.style.visibility='hidden';
	}
	
	if(message === "" || message === undefined || message === null)
	{
		reqMsg.style.visibility="visible";
		progressCircle.style.visibility='hidden';
	}
	
	if(phNumber !== "" && phNumber !== undefined && phNumber !== null)
	{
		var numbers = /^[0-9]+$/;
		if(!phNumber.match(numbers))
		{
			reqMsg.innerHTML="Invalid Phone Number";
			reqMsg.style.visibility="visible";
			progressCircle.style.visibility='hidden';
			phFlag=true;
		}
	
	}
	
	if(email !== "" && email !== undefined && email !== null)
	{
			
			if(emailIsValid(email) === false) {
				reqMsg.innerHTML="Invalid Email Address";
				reqMsg.style.visibility="visible";
				progressCircle.style.visibility='hidden';
				emailFlag=true;
			 }
	}
	
	
	if(name.length>0 && phNumber.length>0 && email.length>0 && subject.length>0 && message.length>0 && phFlag === false && emailFlag === false)
	{
		var jsonPayload = {};
		jsonPayload["name"] = name;
		jsonPayload["phNumber"] = phNumber;
		jsonPayload["email"] = email;
		jsonPayload["subject"] = subject;
		jsonPayload["message"] = message;
		jsonPayload["token"] = token;
		
//		console.log("Json payload before sending=",jsonPayload);
		
		document.getElementById('enqForm').reset();
//		document.getElementById('form100').blur();
//		document.getElementById('form101').blur();
//		document.getElementById('form102').blur();
//		document.getElementById('form103').blur();
//		document.getElementById('form104').blur();
		
		
		sendServer("./v1/sendquery","Post",jsonPayload);
	}
	
	
	
}

function emailIsValid (email) {
	return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }

function sendServer(url,type,jsonPayload)
{
	var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
	xmlhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	try {
		    		var myArr = JSON.parse(this.responseText);
		    		if(myArr["errorMessage"] !== undefined)
		    		{
		    			showErrorMessage();
		    		}
		    		if(myArr["successMessage"] !== undefined)
		    		{
		    			showSuccessMessage();
		    		}
		    		if(myArr["emailErrMessage"]!== undefined)
		    		{
		    			showEmailErrorMessage();
		    		}
//		    		
		    	} catch(e) {
		    	    
//		    		
		    		
		    	}
		        
		       
		    	
		    }
		};

	
	if(type === "Post")
	{
		xmlhttp.open("POST", url,true);
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(JSON.stringify(jsonPayload));
	}
	
	
}

function showErrorMessage()
{
	document.getElementById('reqMsg').style.visibility="visible";
	document.getElementById('reqMsg').focus();
	document.getElementById('reqMsg').innerHTML="Captcha Error!!! Re-Enter form again...";
	var progressCircle = document.getElementById('progressCircle');
	progressCircle.style.visibility='hidden';
}

function showSuccessMessage()
{
	document.getElementById('reqMsg').style.visibility="visible";
	document.getElementById('reqMsg').focus();
	document.getElementById('reqMsg').innerHTML="Message Sent Successfully !!!";
	var progressCircle = document.getElementById('progressCircle');
	progressCircle.style.visibility='hidden';
}

function showEmailErrorMessage()
{
	document.getElementById('reqMsg').style.visibility="visible";
	document.getElementById('reqMsg').focus();
	document.getElementById('reqMsg').innerHTML = "Failed to send message!!! Retry after sometime....";
	var progressCircle = document.getElementById('progressCircle');
	progressCircle.style.visibility='hidden';
}
