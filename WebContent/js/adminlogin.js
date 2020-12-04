function closeDropDown(){

    if($('#button1').is(':visible'))
    {
        // Code
        // console.log("from body");
        $('#navbarSupportedContent').removeClass('show');
        
   
    }
}

function printTable()
{
	console.log("printing table");
	var divToPrint=document.getElementById("viewLedgerTable");
	var accName = document.getElementById("ledgerAccName");
	var accBal = document.getElementById("ledgerAccBal");
	var dateRange = document.getElementById("dateRange");
	newWin= window.open("");
	newWin.document.write(accName.outerHTML+accBal.outerHTML+dateRange.outerHTML+divToPrint.outerHTML);
	newWin.print();
	newWin.close();
//	printJS('viewLedgerTable', 'html');
}

function printTrialBal()
{
	var divToPrint=document.getElementById("trialbalance");
	var accName = document.getElementById("heading1");
	var accBal = document.getElementById("heading2");
	
	newWin= window.open("");
	newWin.document.write(accName.outerHTML+accBal.outerHTML+divToPrint.outerHTML);
	newWin.print();
	newWin.close();
}

function readTrialBal()
{
	console.log("read trail balance");
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
	sendServer("./readtrialbalance","GET","");
}

function readIncome()
{
	console.log("read Income");
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
	sendServer("./readincome","GET","");
}

function openCalendar()
{
	
	const fp1 = flatpickr("#calJournal", {
	    dateFormat: "d-m-Y"
	});
	fp1.open();
}

function showCloseAccountCalendar()
{
	const fp5 = flatpickr("#closeAccDate", {
		dateFormat: "d-m-Y"
	});
	fp5.open();
}


function closeAllAccounts()
{
	  var closeAccDate= document.getElementById("closeAccDate").value;
	  document.getElementById("closeMsg").style.visibility="hidden";
	  document.getElementById("closeMsg").style.color="red";
	  console.log("closeAccDate",closeAccDate);
	  if(closeAccDate === null || closeAccDate === undefined || closeAccDate === "")
	  {
		  document.getElementById("closeMsg").style.visibility="visible";
	  }
	  else if(closeAccDate !== null || closeAccDate !== undefined || closeAccDate !== "")
	  {
		  document.getElementById("closeAccRoller").style.visibility="visible";
		  var jsonPayload = {};
		  jsonPayload["closeAccDate"] = closeAccDate;
		  sendServer("./closeaccounts","Post",jsonPayload);
	  }
	  
}

function showCloseAccounts(myArr)
{
	document.getElementById("closeAccRoller").style.visibility="hidden";
	if(myArr["closeAccMsg"] === "No accounts are present for closure")
	{
		document.getElementById("closeMsg").innerText=myArr["closeAccMsg"];
		document.getElementById("closeMsg").style.visibility="visible";
	}
	
	if(myArr["closeAccMsg"] === "Accounts are closed")
	{
		document.getElementById("closeMsg").innerText=myArr["closeAccMsg"];
		document.getElementById("closeMsg").style.visibility="visible";
		document.getElementById("closeMsg").style.color="Green";
	}
	if(myArr["closeAccMsg"] === "Error in Ledger Delete")
	{
		document.getElementById("closeMsg").innerText=myArr["closeAccMsg"];
		document.getElementById("closeMsg").style.visibility="visible";
		document.getElementById("closeMsg").style.color="red";
	}

}


function openSearchCalendar()
{
	const fp2 = flatpickr("#searchDate", {
		dateFormat: "d-m-Y"
	});
	fp2.open();
}

function fromJnlCalendar()
{
	const fp3 = flatpickr("#fromLedgerDate", {
		dateFormat: "d-m-Y"
	});
	fp3.open();
}

function toJnlCalendar()
{
	const fp4 = flatpickr("#toLedgerDate", {
		dateFormat: "d-m-Y"
	});
	fp4.open();
}

function loadJournalAcc(name)
{
	document.getElementById("journalAcc").innerText = name;
}


function loadJnlAcc(name)
{
	document.getElementById("jnlAcc").innerText = name;
}


function loadledgerAcc(name)
{
	document.getElementById("ledgerAcc").innerText = name;
}

function loadUpdJnlView(id)
{
	console.log("Loading update journal view",id);
	document.getElementById("linearProg").style.visiblility="visible";
	sendServer("./loadjournalconfig","GET",id);

}




function searchJnrl()
{
	
	var searchDate = document.getElementById("searchDate").value;
	console.log("searchDate",searchDate);
	var searchAcc = document.getElementById("jnlAcc").innerText;
	console.log("searchAcc",searchAcc);
	if(searchAcc === "ACCOUNT :" && searchDate === "" || searchDate === undefined)
	{
		console.log("Search Error");
		document.getElementById("jnlAcc").innerText="ACCOUNT :";
		$("#searchErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchErr").fadeOut(3000);
		}, 3000);
	}
	else if(searchAcc !== "ACCOUNT :" && searchDate === "" || searchDate === undefined)
	{
		console.log("Search Error in Account only");
		$("#searchErr").fadeIn(3000);
	
		setTimeout(function(){
			$("#searchErr").fadeOut(3000);
		}, 3000);
	}
	else if((searchAcc !== "ACCOUNT :" && searchDate !== "") || (searchAcc === "ACCOUNT :" && searchDate !== "")) 
	{
		var jsonPayload = {};
		document.getElementById("searchTblBody").innerHTML="";
		document.getElementById("srchJnlSpinner").style.visibility="visible";
		if(searchAcc === "ACCOUNT :")
		{
			searchAcc="";
		}
		jsonPayload["searchAcc"] = searchAcc;
		jsonPayload["searchDate"] = searchDate;
		document.getElementById("searchDate").value="";
		document.getElementById("jnlAcc").innerText="ACCOUNT :";
		sendServer("./searchjournal","Post",jsonPayload);
	}
	
	
}

function searchLedger()
{
//	
	var fromLedgerDate = document.getElementById("fromLedgerDate").value;
	console.log("fromLedgerDate",fromLedgerDate);
	var toLedgerDate = document.getElementById("toLedgerDate").value;
	console.log("toLedgerDate",toLedgerDate);
	var ledgerAcc = document.getElementById("ledgerAcc").innerText;
	console.log("ledgerAcc",ledgerAcc);
	
	if(fromLedgerDate === "" || fromLedgerDate === undefined || fromLedgerDate === null)
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else if(toLedgerDate === "" || toLedgerDate === undefined || toLedgerDate === null)
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else if(ledgerAcc === "LEDGER ACCOUNT * :")
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else
	{
		document.getElementById("srchLedSpinner").style.visibility="visible";
		var jsonPayload = {};
		jsonPayload["fromLedgerDate"] = fromLedgerDate;
		jsonPayload["toLedgerDate"] = toLedgerDate;
		jsonPayload["ledgerAcc"] = ledgerAcc;
		document.getElementById("fromLedgerDate").value = "";
		document.getElementById("fromLedgerDate").focus();
		document.getElementById("toLedgerDate").value = "";
		document.getElementById("toLedgerDate").focus();
		document.getElementById("ledgerAcc").innerText = "Ledger Account * :";
		sendServer("./searchledger","Post",jsonPayload);
	}
	
	
}

function downloadReport()
{
//	
	
	var fromLedgerDate = document.getElementById("fromLedgerDate").value;
	console.log("fromLedgerDate",fromLedgerDate);
	var toLedgerDate = document.getElementById("toLedgerDate").value;
	console.log("toLedgerDate",toLedgerDate);
	var ledgerAcc = document.getElementById("ledgerAcc").innerText;
	console.log("ledgerAcc",ledgerAcc);
	
	if(fromLedgerDate === "" || fromLedgerDate === undefined || fromLedgerDate === null)
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else if(toLedgerDate === "" || toLedgerDate === undefined || toLedgerDate === null)
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else if(ledgerAcc === "LEDGER ACCOUNT * :")
	{
		$("#searchLedgerErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchLedgerErr").fadeOut(3000);
		}, 3000);
	}
	else
	{
		document.getElementById("cloudProgress").style.visibility="visible";
		var jsonPayload = {};
		jsonPayload["fromLedgerDate"] = fromLedgerDate;
		jsonPayload["toLedgerDate"] = toLedgerDate;
		jsonPayload["ledgerAcc"] = ledgerAcc;
		document.getElementById("fromLedgerDate").value = "";
		document.getElementById("fromLedgerDate").focus();
		document.getElementById("toLedgerDate").value = "";
		document.getElementById("toLedgerDate").focus();
		document.getElementById("ledgerAcc").innerText = "Ledger Account * :";
		jsonPayload=JSON.stringify(jsonPayload);
		sendServer("./downloadreport","GET",jsonPayload);
	}
	
	
}

function downloadJnlReport()
{
	var searchDate = document.getElementById("searchDate").value;
	console.log("searchDate",searchDate);
	var searchAcc = document.getElementById("jnlAcc").innerText;
	console.log("searchAcc",searchAcc);
	if(searchAcc === "ACCOUNT :" && searchDate === "" || searchDate === undefined)
	{
		console.log("Search Error");
		document.getElementById("jnlAcc").innerText="ACCOUNT :";
		$("#searchErr").fadeIn(3000);
		setTimeout(function(){
			$("#searchErr").fadeOut(3000);
		}, 3000);
	}
	else if(searchAcc !== "ACCOUNT :" && searchDate === "" || searchDate === undefined)
	{
		console.log("Search Error in Account only");
		$("#searchErr").fadeIn(3000);
	
		setTimeout(function(){
			$("#searchErr").fadeOut(3000);
		}, 3000);
	}
	else if((searchAcc !== "ACCOUNT :" && searchDate !== "") || (searchAcc === "ACCOUNT :" && searchDate !== "")) 
	{
		var jsonPayload = {};
		document.getElementById("linearProg").style.visibility="visible";
		if(searchAcc === "ACCOUNT :")
		{
			searchAcc="";
		}
		jsonPayload["searchAcc"] = searchAcc;
		jsonPayload["searchDate"] = searchDate;
		document.getElementById("searchDate").value="";
		document.getElementById("jnlAcc").innerText="ACCOUNT :";
		jsonPayload = JSON.stringify(jsonPayload);
		sendServer("./downloadjournal","GET",jsonPayload);
	}
}

function downloadFile(responseText,filename)
{
	console.log("response file name",filename);
	var splitFilenames = filename.split("=");
	console.log("actual file name",splitFilenames[1]);
	const a = document.createElement("a");
	a.id = "downloadFileLink";
	a.style = "display: none";
    document.body.appendChild(a);
    a.href = 'data:text/csv;charset=utf-8,' + encodeURI(responseText);
    a.target = '_blank';
    a.download = splitFilenames[1];
    a.click();
//    var downloadFileLink = document.getElementById("downloadFileLink");
//    downloadFileLink.parentNode.removeChild(downloadFileLink);
    document.getElementById("cloudProgress").style.visibility="hidden";
}

function downloadJnlFile(responseText,filename)
{
	console.log("response file name",filename);
	var splitFilenames = filename.split("=");
	console.log("actual file name",splitFilenames[1]);
	const a = document.createElement("a");
	a.id = "downloadFileLink";
	a.style = "display: none";
    document.body.appendChild(a);
    a.href = 'data:text/csv;charset=utf-8,' + encodeURI(responseText);
    a.target = '_blank';
    a.download = splitFilenames[1];
    a.click();
//    var downloadFileLink = document.getElementById("downloadFileLink");
//    downloadFileLink.parentNode.removeChild(downloadFileLink);
    document.getElementById("linearProg").style.visibility="hidden";
}

function showSearchedJournalRecords(myArr)
{
	document.getElementById("srchJnlSpinner").style.visibility="hidden";
	var table =  $('#viewJnlTable').DataTable();
	if(myArr["searchJournalMsg"] === "No records found")
	{
		console.log("No records found");
		table.clear().draw();
		
	}
	else if((myArr["searchJournalMsg"] === "Records found"))
	{
		document.getElementById("searchTblBody").innerHTML="";
		var searchArrValues = myArr["search_results"];
		console.log("searchArrValues",searchArrValues);
		console.log("searchArrValues length",searchArrValues.length);
//		var table =  $('#viewJnlTable').DataTable();
		table.clear();
		
		for(i=0;i<searchArrValues.length;i++)
		{
			
			var rowElements = searchArrValues[i].split(":");
			insertSearchRecords(rowElements,table);
		
		}
		

	}	
		
}


function showSearchedLedgerRecords(myArr)
{
	document.getElementById("srchLedSpinner").style.visibility="hidden";
	var table =  $('#viewLedgerTable').DataTable();
	if(myArr["searchLedgerMsg"] === "No records found")
	{
		console.log("No records found");
		document.getElementById("ledgerAccName").innerText=myArr["ledgerAcc"];
		document.getElementById("ledgerDateRange").innerText=myArr["fromLedgerDate"]+" - "+myArr["toLedgerDate"];
		document.getElementById("ledgerAccBalance").innerText=myArr["ledgerAccBal"];
		table.clear().draw();
		
	}
	else if((myArr["searchLedgerMsg"] === "Records found"))
	{
		document.getElementById("searchTblBody").innerHTML="";
		var searchArrValues = myArr["search_results"];
		console.log("searchArrValues",searchArrValues);
		console.log("searchArrValues length",searchArrValues.length);
		table.clear();
		console.log("ledgerAccName",myArr["ledgerAcc"]);
		console.log("ledgerDateRange",myArr["fromLedgerDate"]+" - "+myArr["toLedgerDate"]);
		console.log("ledgerAccBalance",myArr["ledgerAccBal"]);
		document.getElementById("ledgerAccName").innerText=myArr["ledgerAcc"];
		document.getElementById("ledgerDateRange").innerText=myArr["fromLedgerDate"]+" to "+myArr["toLedgerDate"];
		document.getElementById("ledgerAccBalance").innerText=myArr["ledgerAccBal"];
		for(i=0;i<searchArrValues.length;i++)
		{
			
			var rowElements = searchArrValues[i].split(":");
			console.log("rowElements",rowElements);
			insertSearchLedgerRecords(rowElements,table,i);
		
		}

	}	
		
}

function insertSearchLedgerRecords(elements,table,i)
{
	
	var tableRow = document.createElement("tr");
//	var idAttr = document.createAttribute("id");
//	idAttr.value = elements[0];
//	tableRow.setAttributeNode(idAttr);
	
	var classDivAttr1 = document.createAttribute("class");
	classDivAttr1.value = "tbColWidth loginLabels";
		
	var styleDivAttr1 = document.createAttribute("style");
	styleDivAttr1.value = "text-align:center;";
	
	var tableData1 = document.createElement("td");
	tableData1.innerText = i+1;
	tableData1.setAttributeNode(classDivAttr1);
	tableData1.setAttributeNode(styleDivAttr1);
	tableRow.appendChild(tableData1);
	
	console.log("tableData1");
	
	var classDivAttr2 = document.createAttribute("class");
	classDivAttr2.value = "tbColWidth loginLabels";
		
	var styleDivAttr2 = document.createAttribute("style");
	styleDivAttr2.value = "text-align:center;";
	
	var tableData2 = document.createElement("td");
	tableData2.innerText = elements[0];
	tableData2.setAttributeNode(classDivAttr2);
	tableData2.setAttributeNode(styleDivAttr2);
	tableRow.appendChild(tableData2);
	
	console.log("tableData2");
	
	var classDivAttr3 = document.createAttribute("class");
	classDivAttr3.value = "tbColWidth loginLabels";
		
	var styleDivAttr3 = document.createAttribute("style");
	styleDivAttr3.value = "text-align:left;";
	
	var tableData3 = document.createElement("td");
	tableData3.innerText = elements[1];
	tableData3.setAttributeNode(classDivAttr3);
	tableData3.setAttributeNode(styleDivAttr3);
	tableRow.appendChild(tableData3);
	
	console.log("tableData3");
	
	var classDivAttr4 = document.createAttribute("class");
	classDivAttr4.value = "tbColWidth loginLabels";
		
	var styleDivAttr4 = document.createAttribute("style");
	styleDivAttr4.value = "text-align:right;";
	
	var tableData4 = document.createElement("td");
	if(elements[4] === "Debit")
	{
		tableData4.innerText = elements[2];
	}
	else
	{
		tableData4.innerText = "";
	}	
	
	tableData4.setAttributeNode(classDivAttr4);
	tableData4.setAttributeNode(styleDivAttr4);
	tableRow.appendChild(tableData4);
	
	console.log("tableData4");
	
	var classDivAttr5 = document.createAttribute("class");
	classDivAttr5.value = "tbColWidth loginLabels";
		
	var styleDivAttr5 = document.createAttribute("style");
	styleDivAttr5.value = "text-align:right;";
	
	var tableData5 = document.createElement("td");
	if(elements[4] === "Credit")
	{
		tableData5.innerText = elements[3];
	}
	else
	{
		tableData5.innerText = "";
	}	
	
	tableData5.setAttributeNode(classDivAttr5);
	tableData5.setAttributeNode(styleDivAttr5);
	tableRow.appendChild(tableData5);
	
	console.log("tableData5");
	
	
	
//	$('#viewJnlTable').find('tbody').append(tableRow);
	
	table.row.add(tableRow).draw();
	
	
}

function insertSearchRecords(elements,table)
{
	
	var tableRow = document.createElement("tr");
	var idAttr = document.createAttribute("id");
	idAttr.value = elements[0];
	tableRow.setAttributeNode(idAttr);
	
	var classDivAttr1 = document.createAttribute("class");
	classDivAttr1.value = "tbColWidth loginLabels";
		
	var styleDivAttr1 = document.createAttribute("style");
	styleDivAttr1.value = "text-align:center;";
	
	var tableData1 = document.createElement("td");
	tableData1.innerText = elements[1];
	tableData1.setAttributeNode(classDivAttr1);
	tableData1.setAttributeNode(styleDivAttr1);
	tableRow.appendChild(tableData1);
	
	var classDivAttr2 = document.createAttribute("class");
	classDivAttr2.value = "tbColWidth loginLabels";
		
	var styleDivAttr2 = document.createAttribute("style");
	styleDivAttr2.value = "text-align:left;";
	
	var tableData2 = document.createElement("td");
	tableData2.innerText = elements[2];
	tableData2.setAttributeNode(classDivAttr2);
	tableData2.setAttributeNode(styleDivAttr2);
	tableRow.appendChild(tableData2);
	
	var classDivAttr3 = document.createAttribute("class");
	classDivAttr3.value = "tbColWidth loginLabels";
		
	var styleDivAttr3 = document.createAttribute("style");
	styleDivAttr3.value = "text-align:left;";
	
	var tableData3 = document.createElement("td");
	tableData3.innerText = elements[3];
	tableData3.setAttributeNode(classDivAttr3);
	tableData3.setAttributeNode(styleDivAttr3);
	tableRow.appendChild(tableData3);
	
	var classDivAttr4 = document.createAttribute("class");
	classDivAttr4.value = "tbColWidth loginLabels";
		
	var styleDivAttr4 = document.createAttribute("style");
	styleDivAttr4.value = "text-align:right;";
	
	var tableData4 = document.createElement("td");
	if(elements[6] === "Debit")
	{
		tableData4.innerText = elements[4];
	}
	else
	{
		tableData4.innerText = "";
	}	
	
	tableData4.setAttributeNode(classDivAttr4);
	tableData4.setAttributeNode(styleDivAttr4);
	tableRow.appendChild(tableData4);
	
	var classDivAttr5 = document.createAttribute("class");
	classDivAttr5.value = "tbColWidth loginLabels";
		
	var styleDivAttr5 = document.createAttribute("style");
	styleDivAttr5.value = "text-align:right;";
	
	var tableData5 = document.createElement("td");
	if(elements[6] === "Credit")
	{
		tableData5.innerText = elements[5];
	}
	else
	{
		tableData5.innerText = "";
	}	
	
	tableData5.setAttributeNode(classDivAttr5);
	tableData5.setAttributeNode(styleDivAttr5);
	tableRow.appendChild(tableData5);
	
	
	var classDivAttr6 = document.createAttribute("class");
	classDivAttr6.value = "tbColWidth loginLabels";
		
	var styleDivAttr6 = document.createAttribute("style");
	styleDivAttr6.value = "text-align:center;";
	
	var tableData6 = document.createElement("td");
	if(elements[6] === "Debit")
	{
		tableData6.innerHTML = "<i onclick='loadUpdJnlView(\""+elements[0]+"\")' class='fas fa-expand updateIcon2' style='cursor:pointer;'></i>";
	}
	else
	{
		tableData6.innerHTML = "";
	}
	tableData6.setAttributeNode(classDivAttr6);
	tableData6.setAttributeNode(styleDivAttr6);
	tableRow.appendChild(tableData6);
	
//	$('#viewJnlTable').find('tbody').append(tableRow);
	
	table.row.add(tableRow).draw();
	
	
}

function loadDrCrType(name)
{
	document.getElementById("drcrType").innerText = name;
	console.log("running while changing drop down",name);
	if(name === "Credit")
	{
		document.getElementById("drcrId").style.visibility = "visible";
		document.getElementById("dbCrId").style.visibility = "visible";
		document.getElementById("drcrAmt").innerText = "Credit Amount * :";
		document.getElementById("drcrId").innerText = "Debit Id * :";
		
		console.log("credit is set");
	}
	if(name === "Debit")
	{
		document.getElementById("drcrId").style.visibility = "hidden";
		document.getElementById("dbCrId").style.visibility = "hidden";
		document.getElementById("drcrAmt").innerText = "Debit Amount * :";
		document.getElementById("drcrId").innerText = "Credit Id :";
		
		console.log("debit is set");
	}
}

function login()
{
	console.log("Running in Login");
	var user = document.getElementById("form2").value;
    var pwd = document.getElementById("form4").value;
    var errorMsg = document.getElementById("errorMsg");
    console.log("Running in errorMsg",errorMsg);
    var bar  = document.getElementById("progressBar");
    bar.style.visibility='visible';
    
    
    
    
    
    if ((user === "" || user === undefined || user === null) && (pwd === "" || pwd === undefined || pwd === null))
    {
    	
    	
    	bar.style.visibility='hidden';
		errorMsg.innerHTML="Both Username and Password field are required";
		errorMsg.style.visibility='visible';
	    console.log("Running in Both");
    }
    else if(user === "" || user === undefined || user === null)
	{
    	errorMsg.style.visibility="visible";
    	bar.style.visibility='hidden';
		errorMsg.innerHTML="Username is required";
		console.log("Running in User");
	}
    else if(pwd === "" || pwd === undefined || pwd === null)
	{
    	errorMsg.style.visibility="visible";
    	bar.style.visibility='hidden';
		errorMsg.innerHTML="Password is required";
		console.log("Running in Password");
	}
    
    
    
    if(user.length>0 && pwd.length>0)
    {
    	if(errorMsg.style.visibility === "visible")
        {
        	errorMsg.style.visibility = "hidden";
        }
    	
    	var jsonPayload = {};
		jsonPayload["user"] = user;
		jsonPayload["pwd"] = pwd;
		
		bar.style.visibility='visible';
		document.getElementById("form2").value="";
		document.getElementById("form4").value="";
		
		sendServer("./login","Post",jsonPayload);
    }
    
    
    
    

}

function chooseCountry(name)
{
	document.getElementById("chosenCountry").innerHTML = name;
}

function chSrcCountry(name)
{
	document.getElementById("srcCountry").innerHTML = name;
}

function chDestCountry(name)
{
	document.getElementById("destCountry").innerHTML = name;
}



function sendServer(url,type,jsonPayload)
{
	var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
	xmlhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	try {
		    		var myArr = JSON.parse(this.responseText);
		    		
		    		if(myArr["failureMessage"] !== undefined)
		    		{
		    			showErrorMessage(myArr);
		    		}
//		    		if(myArr["names"] !== undefined)
//		    		{
//		    			loadEmployeeNames(myArr);
//		    		}
		    		if(myArr["addEmpMsg"] !== undefined)
		    		{
		    			showAddEmpMsg(myArr);
		    			
		    		}
		    		if(myArr["addAccMsg"] !== undefined)
		    		{
		    			showAddAccMsg(myArr);
		    			
		    		}
		    		if(myArr["updEmpMsg"] !== undefined)
		    		{
		    			showUpdEmpMsg(myArr);
		    			
		    		}
		    		if(myArr["updAccMsg"] !== undefined)
		    		{
		    			showUpdAccMsg(myArr);
		    			
		    		}
		    		if(myArr["delEmpMsg"] !== undefined)
		    		{
		    			showDelEmpMsg(myArr);
		    			
		    		}
		    		if(myArr["delAccMsg"] !== undefined)
		    		{
		    			showDelAccMsg(myArr);
		    			
		    		}
		    		if(myArr["getCurrMsg"] !== undefined)
		    		{
		    			showCurrErrMsg(myArr);
		    		}
		    		if(myArr["postJournalMsg"] !== undefined)
		    		{
		    			showPostTransaction(myArr);
		    		}
		    		if(myArr["delJournalMsg"] !== undefined)
		    		{
		    			showDelTransMsg(myArr);
		    		}
		    		if(myArr["updJournalMsg"] !== undefined)
		    		{
		    			showUpdTransMsg(myArr);
		    		}
		    		if(myArr["searchJournalMsg"] !== undefined)
		    		{
		    			showSearchedJournalRecords(myArr);
		    		}
		    		if(myArr["searchLedgerMsg"] !== undefined)
		    		{
		    			showSearchedLedgerRecords(myArr);
		    		}
		    		if(myArr["closeAccMsg"] !== undefined)
		    		{
		    			showCloseAccounts(myArr);
		    		}
		    		if(myArr["actEmpMsg"] !== undefined)
		    		{
		    		   showActivateEmpMsg(myArr);
		    		}
//		    		
		    	} catch(e) {
		    	    
		    		var decider = this.getResponseHeader("resultfor");
		    		console.log("decider value :",decider);
		    		
		    		if(decider === "admin")
		    		{
		    			showAdminPage(this.responseText);
		    			
		    		}
		    		if(decider === "myContainer")
		    		{
		    			loadSubPage(this.responseText);
		    			
		    		}
		    		if(decider === "bodyContainer")
		    		{
		    			loadLoginPage(this.responseText);
		    			
		    		}
		    		if(decider === "secondRow")
		    		{
		    			loadSecondRow(this.responseText);
		    			document.getElementById("thirdRow").innerHTML = "";
		    		}
		    		if(decider === "thirdRow")
		    		{
		    			showAllCurr(this.responseText);
		    			
		    		}
		    		if(decider === "loadJnlRow")
		    		{
		    			showJournalView(this.responseText);
		    			
		    		}
		    		if(decider === "downloadfile")
		    		{
		    			downloadFile(this.responseText,this.getResponseHeader("Content-Disposition"));
		    			
		    		}
		    		if(decider === "downloadJnlfile")
		    		{
		    			downloadJnlFile(this.responseText,this.getResponseHeader("Content-Disposition"));
		    			
		    		}
		    		
		    		
		    	}
		        
		       
		    	
		    }
		};

	
	if(type === "Post")
	{
		xmlhttp.open("POST", url,true);
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(JSON.stringify(jsonPayload));
	}
	else
	{
		xmlhttp.open("GET", url,true);
		xmlhttp.setRequestHeader("detailType", jsonPayload);
		xmlhttp.send();
	}	
	
	
}

function postTransaction()
{
	document.getElementById("journalErr").innerText = "Required fields are needed";
	document.getElementById("journalErr").style.visibility = "hidden";
	document.getElementById("journalErr").style.color = "red";
	var date = document.getElementById("calJournal").value;
	console.log("date",date);
	var journalDesc = document.getElementById("journalDesc").value;
	console.log("journalDesc",journalDesc);
	var journalAcc = document.getElementById("journalAcc").innerText;
	console.log("journalAcc",journalAcc);
	var drcrType = document.getElementById("drcrType").innerText;
	console.log("drcrType",drcrType);
	var amount = document.getElementById("amount").value;
	console.log("amount",amount);
	var rate = document.getElementById("rate").value;
	console.log("rate",rate);
	var dbCrAmt = document.getElementById("dbCrAmt").value;
	console.log("dbCrAmt",dbCrAmt);
	var dbCrId =document.getElementById("dbCrId").value;
	console.log("dbCrId",dbCrId);
	
	if(date === "" || date === undefined || date === null)
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if((journalDesc === "" || journalDesc === undefined || journalDesc === null))
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(journalAcc === "Account * :")
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(drcrType === "Debit/Credit *:")
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(amount === "" || amount === undefined || amount === null)
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(rate === "" || rate === undefined || rate === null)	
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(dbCrAmt === "" || dbCrAmt === undefined || dbCrAmt === null)
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else if(drcrType === "CREDIT" && (dbCrId === "" || dbCrId === undefined || dbCrId === null))
	{
		document.getElementById("journalErr").style.visibility = "visible";
	}
	else
	{
			
		
		document.getElementById("postSpinner").style.visibility = "visible";
		var jsonPayload = {};
		jsonPayload["date"] = date;
		jsonPayload["journalDesc"] = journalDesc;
		jsonPayload["journalAcc"] = journalAcc;
		jsonPayload["drcrType"] = drcrType;
		jsonPayload["amount"] = amount;
		jsonPayload["rate"] = rate;
		jsonPayload["dbCrAmt"] = dbCrAmt;
		jsonPayload["dbCrId"] = dbCrId;
		document.getElementById("calJournal").value = "";
		document.getElementById("journalDesc").value = "";
		document.getElementById("journalAcc").innerText = "Account * :";
		document.getElementById("drcrType").innerText = "Debit/Credit * :";
		document.getElementById("amount").value = "";
		document.getElementById("rate").value = "";
		document.getElementById("dbCrAmt").value = "";
		document.getElementById("dbCrId").value = "";
		var postBtn = document.getElementById("postBtn");
		console.log("postBtn.innerText",postBtn.innerText);
		console.log("postBtn.innerText Length",postBtn.innerText.length);
		console.log("postBtn.innerText Check",(postBtn.innerText === "POST "));
		if(postBtn.innerText === "UPDATE ")
		{
			jsonPayload["updId"] = postBtn.dataset.updateId;
			sendServer("./updtransaction","Post",jsonPayload);
		}
		else if(postBtn.innerText === "POST ")
		{
			sendServer("./posttransaction","Post",jsonPayload);
		}	
		
		
	}
	
	
			
	
}

function showJournalView(respText)
{
	document.getElementById("viewJnlSpinner").style.visibility="hidden";
	document.getElementById("addupdJnlSpinner").style.visibility="hidden";
	document.getElementById("loadJnlRow").innerHTML=respText;
	var table = document.getElementById("viewJnlTable");
	if(table!==undefined || table !== "")
	{
		console.log("table found");
		$('#viewJnlTable').DataTable( {
			     paging: true,
		        ordering: false,
		        info:     true,
		        language: { search: '', searchPlaceholder: "Search..." },
		        
		        
		});
//		$('#viewJnlTable>input[type="search"]').attr('placeholder', 'Search...');
	}
  
}

function loadJournalView()
{
	document.getElementById("viewJnlSpinner").style.visibility="visible";
	sendServer("./loadjournalview","GET","");
}

function loadAddUpdJournalView()
{
	document.getElementById("addupdJnlSpinner").style.visibility="visible";
	sendServer("./loadaddupdjournalview","GET","");
}

function showPostTransaction(myArr)
{
	document.getElementById("postSpinner").style.visibility = "hidden";
	if(myArr["postJournalMsg"] === "Not a valid debit id")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["postJournalMsg"];
	}
	else if(myArr["postJournalMsg"] === "Insert credit journal failure")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["postJournalMsg"];
	}
	else if(myArr["postJournalMsg"] === "Insert debit journal failure")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["postJournalMsg"];
	}
	else if(myArr["postJournalMsg"] === "Insert debit journal success")
	{
		console.log("running in debit journal");
		insertEntry("debitTblBody",myArr);
	
	}
	else if(myArr["postJournalMsg"] === "Insert credit journal success")
	{
		console.log("running in credit journal");
		insertEntry("creditTblBody",myArr);
	}
}

function insertEntry(tableId,myArr)
{
	try{
	console.log("insert entry");
	var tableBody = document.getElementById(tableId);
	console.log("table body",tableBody);
	var tableRow = document.createElement("tr");
	var idAttr = document.createAttribute("id");
	idAttr.value = myArr["journalId"];
	tableRow.setAttributeNode(idAttr);
	
	console.log("intial setup done");
	
	var classDivAttr1 = document.createAttribute("class");
	classDivAttr1.value = "tbColWidth";
	
	var styleDivAttr1 = document.createAttribute("style");
	styleDivAttr1.value = "text-align:center;";
	
	var tableData1 = document.createElement("td");
	tableData1.innerText = myArr["journalId"];
	tableData1.setAttributeNode(classDivAttr1);
	tableData1.setAttributeNode(styleDivAttr1);
	tableRow.appendChild(tableData1);
	
	console.log("table data1 added");
	
	var classDivAttr2 = document.createAttribute("class");
	classDivAttr2.value = "tbColWidth";
	
	var styleDivAttr2 = document.createAttribute("style");
	styleDivAttr2.value = "text-align:center;";
	
	var tableData2 = document.createElement("td");
	if(myArr["dbCrId"] !== undefined)
	{
		tableData2.innerText = myArr["dbCrId"];
	}
	tableData2.setAttributeNode(classDivAttr2);
	tableData2.setAttributeNode(styleDivAttr2);
	tableRow.appendChild(tableData2);
	
	console.log("table data2 added");
	
	var classDivAttr3 = document.createAttribute("class");
	classDivAttr3.value = "tbColWidth";
	
	var styleDivAttr3 = document.createAttribute("style");
	styleDivAttr3.value = "text-align:center;";
	
	var tableData3 = document.createElement("td");
	tableData3.innerText = myArr["date"];
	tableData3.setAttributeNode(classDivAttr3);
	tableData3.setAttributeNode(styleDivAttr3);
	tableRow.appendChild(tableData3);
	
	
	
	var classDivAttr4 = document.createAttribute("class");
	classDivAttr4.value = "tbColWidth";
	
	var styleDivAttr4 = document.createAttribute("style");
	styleDivAttr4.value = "text-align:center;";
	
	var tableData4 = document.createElement("td");
	tableData4.innerText = myArr["journalDesc"];
	tableData4.setAttributeNode(classDivAttr4);
	tableData4.setAttributeNode(styleDivAttr4);
	tableRow.appendChild(tableData4);
	
	console.log("table data4 added");
	
	var classDivAttr5 = document.createAttribute("class");
	classDivAttr5.value = "tbColWidth";
	
	var styleDivAttr5 = document.createAttribute("style");
	styleDivAttr5.value = "text-align:center;";
	
	var tableData5 = document.createElement("td");
	tableData5.innerText = myArr["journalAcc"];
	tableData5.setAttributeNode(classDivAttr5);
	tableData5.setAttributeNode(styleDivAttr5);
	tableRow.appendChild(tableData5);
	
	console.log("table data5 added");
	
	var classDivAttr6 = document.createAttribute("class");
	classDivAttr6.value = "tbColWidth";
	
	var styleDivAttr6 = document.createAttribute("style");
	styleDivAttr6.value = "text-align:center;";
	
	var tableData6 = document.createElement("td");
	tableData6.innerText = myArr["amount"];
	tableData6.setAttributeNode(classDivAttr6);
	tableData6.setAttributeNode(styleDivAttr6);
	tableRow.appendChild(tableData6);
	
	console.log("table data6 added");
	
	var classDivAttr7 = document.createAttribute("class");
	classDivAttr7.value = "tbColWidth";
	
	var styleDivAttr7 = document.createAttribute("style");
	styleDivAttr7.value = "text-align:center;";
	
	var tableData7 = document.createElement("td");
	tableData7.innerText = myArr["rate"];
	tableData7.setAttributeNode(classDivAttr7);
	tableData7.setAttributeNode(styleDivAttr7);
	tableRow.appendChild(tableData7);
	
	
	console.log("table data7 added");
	
	var classDivAttr8 = document.createAttribute("class");
	classDivAttr8.value = "tbColWidth";
	
	var styleDivAttr8 = document.createAttribute("style");
	styleDivAttr8.value = "text-align:right;";
	
	var tableData8 = document.createElement("td");
	tableData8.innerText = myArr["dbCrAmt"];
	tableData8.setAttributeNode(classDivAttr8);
	tableData8.setAttributeNode(styleDivAttr8);
	tableRow.appendChild(tableData8);
	
	console.log("table data8 added");
	
	var classDivAttr9 = document.createAttribute("class");
	classDivAttr9.value = "tbColWidth";
	
	var styleDivAttr9 = document.createAttribute("style");
	styleDivAttr9.value = "text-align:center;";
	
	var tableData9 = document.createElement("td");
	if(myArr["drcrType"] === "DEBIT" || myArr["drcrType"] === "Debit")
	{
		tableData9.innerHTML = "<i onclick=loadJournal('"+myArr["journalId"]+"','"+myArr["drcrType"]+"') class='fas fa-pen-alt updateIcon' style='cursor:pointer;'></i>&nbsp;&nbsp;<i onclick=delJournal('"+myArr["journalId"]+"','"+myArr["drcrType"]+"') class='fas fa-trash-alt delete' style='cursor:pointer;'></i>";	
	}
//	tableData9.innerHTML = "<i onclick=loadJournal('"+myArr["journalId"]+"','"+myArr["drcrType"]+"') class='fas fa-pen-alt updateIcon' style='cursor:pointer;'></i>&nbsp;&nbsp;<i onclick=delJournal('"+myArr["journalId"]+"','"+myArr["drcrType"]+"') class='fas fa-trash-alt delete' style='cursor:pointer;'></i>" ;
	if(myArr["drcrType"] === "CREDIT" || myArr["drcrType"] === "Credit")
	{
		tableData9.innerHTML = "<i onclick=loadJournal('"+myArr["journalId"]+"','"+myArr["drcrType"]+"') class='fas fa-pen-alt updateIcon' style='cursor:pointer;'></i>&nbsp;&nbsp;" ;
	}
	tableData9.setAttributeNode(classDivAttr9);
	tableData9.setAttributeNode(styleDivAttr9);
	tableRow.appendChild(tableData9);
	
	console.log("table data9 added");
	
	tableBody.appendChild(tableRow);
	
	console.log("table row added");
	}
	catch(err)
	{
		console.log("Exception",err);
	}
}

function showUpdTransMsg(myArr)
{
	console.log("Running in update transaction");
	console.log("myArr",myArr);
	document.getElementById("calJournal").disabled = false;
	document.getElementById("journalAcc").disabled = false;
	document.getElementById("drcrType").disabled = false;
	document.getElementById("dbCrId").disabled = false;
	var postBtn = document.getElementById("postBtn");
	postBtn.innerHTML = "POST <span id='postSpinner' class='spinner-border spinner-border-sm' role='status' aria-hidden='true' style='visibility:hidden;'></span>";
	postBtn.dataset.updateId="";
	document.getElementById("postSpinner").style.visibility = "hidden";
	if(myArr["updJournalMsg"] === "Update debit journal failure")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["updJournalMsg"];
	}
	else if(myArr["updJournalMsg"] === "Update credit journal failure")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["updJournalMsg"];
	}
	else
	{
		console.log("runnning in showDelTransMsg else ");
		
		if(myArr["drcrType"] === "DEBIT")
		{
			var tableRows = document.getElementById("debitTblBody").rows;
			for(i=0;i<tableRows.length;i++)
			{
				if(tableRows[i].id === myArr["updId"])
				{
					var cells = tableRows[i].cells;
					cells[2].innerText = myArr["date"];
					cells[3].innerText = myArr["journalDesc"];
					cells[4].innerText = myArr["journalAcc"];
					cells[5].innerText = myArr["amount"];
					cells[6].innerText = myArr["rate"];
					cells[7].innerText = myArr["dbCrAmt"];
					if(myArr["dbCrId"] !== undefined)
					{
						cells[1].innerText = myArr["dbCrId"];
					}
					break;
				}
			}	
		}
		if(myArr["drcrType"] === "CREDIT")
		{
			var tableRows = document.getElementById("creditTblBody").rows;
			for(i=0;i<tableRows.length;i++)
			{
				if(tableRows[i].id === myArr["updId"])
				{
					var cells = tableRows[i].cells;
					cells[2].innerText = myArr["date"];
					cells[3].innerText = myArr["journalDesc"];
					cells[4].innerText = myArr["journalAcc"];
					cells[5].innerText = myArr["amount"];
					cells[6].innerText = myArr["rate"];
					cells[7].innerText = myArr["dbCrAmt"];
					if(myArr["dbCrId"] !== undefined)
					{
						cells[1].innerText = myArr["dbCrId"];
					}
					break;
				}
			}
			document.getElementById("drcrId").style.visibility = "hidden";
			document.getElementById("dbCrId").style.visibility = "hidden";
		}
	}	
	
	
}

function loadJournal(id,trType)
{
	console.log("load journal is running");
	if(trType === "DEBIT")
	{
		console.log("debit update");
		var postBtn = document.getElementById("postBtn");
		postBtn.dataset.updateId=id;
		postBtn.innerHTML="UPDATE <span id='postSpinner' class='spinner-border spinner-border-sm' role='status' aria-hidden='true' style='visibility:hidden;'></span>";
		var tableRows = document.getElementById("debitTblBody").rows;
		for(i=0;i<tableRows.length;i++)
		{
			console.log("table id",tableRows[i].id);
			console.log("act id",id);
			console.log("condition",(tableRows[i].id === id));
			if(tableRows[i].id===id)
			{
				var cells = tableRows[i].cells;
				console.log("cells[1].innerText",cells[1].innerText);
				console.log("cells[2].innerText",cells[2].innerText);
				console.log("cells[3].innerText",cells[3].innerText);
				console.log("cells[4].innerText",cells[4].innerText);
				console.log("cells[5].innerText",cells[5].innerText);
				console.log("cells[6].innerText",cells[6].innerText);
				console.log("cells[7].innerText",cells[7].innerText);
				
				document.getElementById("calJournal").value = cells[2].innerText;
				document.getElementById("calJournal").focus();
//				document.getElementById("calJournal").disabled = true;
				
				document.getElementById("journalDesc").value = cells[3].innerText;
				document.getElementById("journalDesc").focus();

				
				document.getElementById("journalAcc").innerText = cells[4].innerText;
//				document.getElementById("journalAcc").disabled = true;
				document.getElementById("drcrType").innerText = trType;
				document.getElementById("drcrType").disabled = true;
				
				document.getElementById("amount").value = cells[5].innerText;
				document.getElementById("amount").focus();


				document.getElementById("rate").value = cells[6].innerText;
				document.getElementById("rate").focus();


				document.getElementById("dbCrAmt").value = cells[7].innerText;
				document.getElementById("dbCrAmt").focus();

				document.getElementById("dbCrId").style.visibility="hidden";
				document.getElementById("drcrId").style.visibility="hidden";
				document.getElementById("dbCrId").value="";
				document.getElementById("dbCrId").focus();
				document.getElementById("dbCrId").disabled = false;
				
				
				break;
			}
		}
	}
	if(trType === "CREDIT")
	{
		console.log("credit update");
		var postBtn = document.getElementById("postBtn");
		postBtn.dataset.updateId=id;
		postBtn.innerHTML="UPDATE <span id='postSpinner' class='spinner-border spinner-border-sm' role='status' aria-hidden='true' style='visibility:hidden;'></span>";
		var tableRows = document.getElementById("creditTblBody").rows;
		for(i=0;i<tableRows.length;i++)
		{
			if(tableRows[i].id===id)
			{
				var cells = tableRows[i].cells;
				console.log("cells[1].innerText",cells[1].innerText);
				console.log("cells[2].innerText",cells[2].innerText);
				console.log("cells[3].innerText",cells[3].innerText);
				console.log("cells[4].innerText",cells[4].innerText);
				console.log("cells[5].innerText",cells[5].innerText);
				console.log("cells[6].innerText",cells[6].innerText);
				console.log("cells[7].innerText",cells[7].innerText);
				
				document.getElementById("calJournal").value = cells[2].innerText;
				document.getElementById("calJournal").focus();
//				document.getElementById("calJournal").disabled = true;
				
				document.getElementById("journalDesc").value = cells[3].innerText;
				document.getElementById("journalDesc").focus();

				
				document.getElementById("journalAcc").innerText = cells[4].innerText;
//				document.getElementById("journalAcc").disabled = true;
				document.getElementById("drcrType").innerText = trType;
				document.getElementById("drcrType").disabled = true;
				
				document.getElementById("amount").value = cells[5].innerText;
				document.getElementById("amount").focus();


				document.getElementById("rate").value = cells[6].innerText;
				document.getElementById("rate").focus();


				document.getElementById("dbCrAmt").value = cells[7].innerText;
				document.getElementById("dbCrAmt").focus();
				
				
				document.getElementById("dbCrId").style.visibility="visible";
				document.getElementById("drcrId").style.visibility="visible";
				console.log("cells[1].innerText",cells[1].innerText);
				document.getElementById("dbCrId").value = cells[1].innerText;
				document.getElementById("dbCrId").focus();
				document.getElementById("dbCrId").disabled = true;
				document.getElementById("drcrId").innerText = "Debit Id * :";
					
					
					
				
				
				break;
			}
		}
	}
	
}

function delJournal(id,trType)
{
	console.log("del journal",id+" : "+trType);
	var jsonPayload ={};
	jsonPayload["deleteId"] = id;
	jsonPayload["trType"] = trType;
	sendServer("./deltransaction","Post",jsonPayload);
}

function showDelTransMsg(myArr)
{
	console.log("runnning in showDelTransMsg");
	console.log("myArr",myArr);
	console.log("creditArray",myArr["creditIds"]);
	console.log("debitArray",myArr["debitIds"]);
	if(myArr["delJournalMsg"] === "Delete journal failure")
	{
		document.getElementById("journalErr").style.visibility = "visible";
		document.getElementById("journalErr").innerText = myArr["delJournalMsg"];
	}
	else
	{
		console.log("runnning in showDelTransMsg else ");
		console.log(myArr["trType"]);
		if(myArr["trType"] === "DEBIT")
		{
//			var row = document.getElementById(myArr["deleteId"]);
//			var tbody = row.parentNode;
//			tbody.removeChild(row);
			console.log("runnning in showDelTransMsg debit ");
			var rows = document.getElementById("debitTblBody").rows;
			console.log("rows",rows);
			console.log("rows length",rows.length);
			for(i=0;i<rows.length;i++)
			{
				console.log("indv row",rows[i]);
				console.log("indv row id",rows[i].id);
				if(rows[i].id === myArr["deleteId"])
				{
					rows[i].remove();
					break;
				}
			}
			if(myArr["creditIds"] !== undefined)
			{
				var creditIds = myArr["creditIds"];
				console.log("creditIds",creditIds);
				var rows = document.getElementById("creditTblBody").rows;
				console.log("creditTblBody rows",rows);
				for(i=0;i<rows.length;i++)
				{
					console.log("indv row",rows[i]);
					console.log("indv row id",rows[i].id);
					for(j=0;j<creditIds.length;j++)
					{
						if(rows[i].id === creditIds[j])
						{
							rows[i].remove();
						}
					}
				}
			}
		}
		if(myArr["trType"] === "CREDIT")
		{
//			var row = document.getElementById(myArr["deleteId"]);
//			var tbody = row.parentNode;
//			tbody.removeChild(row);
			console.log("runnning in showDelTransMsg credit ");
			var creditTable= document.getElementById("creditTblBody");
			var rows = document.getElementById("creditTblBody").rows;
			console.log("rows",rows);
			console.log("rows length",rows.length);
			for(i=0;i<rows.length;i++)
			{
				console.log("indv row",rows[i]);
				console.log("indv row id",rows[i].id);
				if(rows[i].id === myArr["deleteId"])
				{
					rows[i].remove();
					break;
				}
			}
			if(myArr["debitIds"] !== undefined)
			{
				var debitIds = myArr["debitIds"];
				var rows = document.getElementById("debitTblBody").rows;
				for(i=0;i<rows.length;i++)
				{
					console.log("indv row",rows[i]);
					console.log("indv row id",rows[i].id);
					for(j=0;j<debitIds.length;j++)
					{
						if(rows[i].id === debitIds[j])
						{
							rows[i].remove();
						}
					}
				}
			}
		}
		
		
	}
}

function isNumberCheck(obj)
{
	 obj.value = obj.value.replace(/[^0-9.]/g,"");

}

function calculateAmt()
{
	var rate = document.getElementById("rate").value;
	var amount = document.getElementById("amount").value;
	
	var bigAmt = new Big(amount);
	var bigRt = new Big(rate);
	var result = bigAmt.times(bigRt);
	document.getElementById("dbCrAmt").value = result.toFixed(2);
	document.getElementById("dbCrAmt").focus();
	
	
}

function checkCurrentRate()
{
	document.getElementById("rateError").innerHTML = "Choose a country from the drop down";
	var chosenCountry = document.getElementById("chosenCountry").innerText;
	console.log("chosen country",chosenCountry);
	if(chosenCountry === "SELECT COUNTRY BASE")
	{
		document.getElementById("rateError").style.visibility = "visible";
	}
	else
	{
		document.getElementById("rateError").style.visibility = "hidden";
		console.log("sending chosen country");
		document.getElementById("currRoller").style.visibility = "visible";
		sendServer("./cumhourlyrate","GET",chosenCountry);
	}
	
}

function checkCurrPair()
{
	document.getElementById("rateError").innerHTML = "Choose a country from the drop down";
	
	var srcCountry = document.getElementById("srcCountry").innerText;
	var destCountry = document.getElementById("destCountry").innerText;
	
	
	
	
	if((srcCountry === "SOURCE COUNTRY") && (destCountry === "DESTINATION COUNTRY"))
	{
		document.getElementById("rateError").style.visibility = "visible";
		document.getElementById("rateError").innerHTML = "Choose both source and destination country";
	}
	else if(srcCountry === "SOURCE COUNTRY")
	{
		document.getElementById("rateError").style.visibility = "visible";
		document.getElementById("rateError").innerHTML = "Choose the source country";
	}
	else if(destCountry === "DESTINATION COUNTRY")
	{
		document.getElementById("rateError").style.visibility = "visible";
		document.getElementById("rateError").innerHTML = "Choose the destination country";
	}
	
	if((srcCountry !== "SOURCE COUNTRY") && (destCountry !== "DESTINATION COUNTRY"))
	{
		console.log("working good");
		document.getElementById("rateError").style.visibility = "hidden";
		document.getElementById("currRoller").style.visibility = "visible";
		var chosenCountry = srcCountry+":"+destCountry;
		sendServer("./currpairconversion","GET",chosenCountry);
	}	
	
}

function showCurrErrMsg(myArr)
{
	document.getElementById("currRoller").style.visibility = "hidden";
	document.getElementById("rateError").style.visibility = "visible";
	document.getElementById("rateError").innerHTML = myArr["getCurrMsg"];
}

function showAllCurr(myArr)
{
	document.getElementById("currRoller").style.visibility = "hidden";
	var thirdRow = document.getElementById("thirdRow");
	console.log("came here");
	thirdRow.innerHTML = myArr;
	var table = document.getElementById("dtBasicExample");
	if(table!==undefined || table !== "")
	{
		console.log("table found");
		$('#dtBasicExample').DataTable({
			language: { search: '', searchPlaceholder: "Search..." }
		});
	}
}

function showErrorMessage(myArr)
{
	var bar  = document.getElementById("progressBar");
	bar.style.visibility='hidden';
	document.getElementById('errorMsg').style.visibility="visible";
	document.getElementById('errorMsg').innerHTML=myArr["failureMessage"];
	
}




function showAdminPage(result)
{
	document.getElementById("admin").innerHTML = result;
	document.getElementById("title").innerHTML = "Admin Page";
	
}


function loadSubPage(result)
{
	document.getElementById("myContainer").innerHTML = result;
//	document.getElementById("title").innerHTML = "Admin Login";
	var table = document.getElementById("viewLedgerTable");
	
	if(table!==undefined || table !== "")
	{
		console.log("table found");
		$('#viewLedgerTable').DataTable({
			language: { search: '', searchPlaceholder: "Search..." }
		});
	}
//	var trialTable = document.getElementById("trialBalTbl");
//	if(trialTable!==undefined || trialTable !== "")
//	{
//		console.log("trialTable found");
//		var debitSum=0.00,creditSum=0.00;
//		var rows = trialTable.rows;
//		for(i=0;i<rows.length;i++)
//		{
//			
//			if(i===(rows.length-1))
//			{
//				var bigDebitSum = new Big(debitSum);
//				var bigCreditSum = new Big(creditSum);
//				document.getElementById("drTotal").innerText=bigDebitSum.toFixed(2);
//				document.getElementById("crTotal").innerText=bigCreditSum.toFixed(2);
//				console.log("break");
//				break;
//			}
//			var cells = rows[i].cells;
//			if(cells[1] !== "" || cells[1] !== undefined)
//			{
//				debitSum = new Big(debitSum)+new Big(cells[1].innerText);
//			}
//			if(cells[2] !== "" || cells[2] !== undefined)
//			{
//				creditSum = new Big(creditSum)+new Big(cells[2].innerText);
//			}
//			
//			
//		}	
//	}
	
}

function loadSecondRow(result)
{
	document.getElementById("secondRow").innerHTML = result;
}

function loadLoginPage(result)
{
	document.getElementById("bodyContainer").innerHTML = result;
	
}


function openTab(id)
{
	console.log("id value",id);
	document.getElementById(id).click();
}

function readEmployees()
{
	console.log("reading employees");
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
//	document.getElementById("roller").style.display="block";
//	var ulTag = document.getElementById("employeeParent");
//	ulTag.innerHTML="";
	sendServer("./employees","GET","");
}


function readRates()
{
	console.log("reading rate config page");
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
//	document.getElementById("roller").style.display="block";
//	var ulTag = document.getElementById("employeeParent");
//	ulTag.innerHTML="";
	sendServer("./configrates","GET","");
}

function showTodaysRate()
{
	console.log("reading rate config page");
	sendServer("./checkrateui","GET","");

}

function showCurrConversion()
{
	console.log("show currency conversion");
	sendServer("./showcurrconversion","GET","");
	
}

function readAccounts()
{
	console.log("reading accounts");
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
//	document.getElementById("roller").style.display="block";
//	var ulTag = document.getElementById("employeeParent");
//	ulTag.innerHTML="";
	sendServer("./accounts","GET","");
}

function readJournal()
{
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
	sendServer("./readjournal","GET","");
}

function readLedger()
{
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
	sendServer("./ledger","GET","");
}

function loadSelectedNames(name)
{
	
	console.log("selected employee name",name);
	
	var userName = document.getElementById("empUser");
	userName.focus();
	userName.value = name;
	userName.disabled=true;
	document.getElementById("addEmpBtn").disabled=true;
	
}

function loadSelectedAccName(name)
{

	console.log("selected employee name",name);
	if(name.indexOf("-") !== -1)
	{
		var values = name.split("-");
		var accName = document.getElementById("accUser");
		accName.focus();
		accName.value = values[0].trim();
		accName.name = values[0].trim();
		var accType = document.getElementById(values[1].trim());
		accType.checked=true;
		var radioButtons = document.querySelectorAll("input[type='radio']");
		console.log("total radio buttons",radioButtons.length);
		console.log("radio buttons",radioButtons);
		for(i=0;i<radioButtons.length;i++)
		{
			radioButtons[i].disabled=true;
		}
	}
	
	
	
}

function activateUser(e,name)
{
	e.stopPropagation();
	e.preventDefault();
	console.log("employee name to be activated",name);
	var activRes = document.getElementById(name).checked;
	console.log("activRes",activRes);
	var splitValues = name.split("-");
	var jsonPayload = {};
	jsonPayload["name"] = splitValues[0];
	jsonPayload["activRes"] = activRes;
	sendServer("./activate","Post",jsonPayload);
//	e.preventDefault();
}

function showActivateEmpMsg(myArr)
{
	if(myArr["actEmpMsg"] !== "Db Error")
	{
		if(myArr["activRes"] === "false")
		{
			document.getElementById(myArr["name"]+"-switch").checked = true;
		}
		else
		{
			document.getElementById(myArr["name"]+"-switch").checked = false;
		}
		document.getElementById("servMsg").innerText = myArr["actEmpMsg"];
		document.getElementById("servMsg").style.color = "green";
		document.getElementById("servMsg").style.visibility = "visible";
	}
	else
	{
		document.getElementById("servMsg").innerText = myArr["actEmpMsg"];
		document.getElementById("servMsg").style.color = "red";
		document.getElementById("servMsg").style.visibility = "visible";
	}
}

function deleteSelectedName(e,name)
{
	e.stopPropagation();
	console.log("employee name to be deleted",name);
	var jsonPayload = {};
	jsonPayload["user"] = name;
	
	sendServer("./deleteemployee","Post",jsonPayload);
	
}




function deleteSelectedAccName(e,name)
{
	e.stopPropagation();
	console.log("account name to be deleted",name);
	var values = name.split("-");
	var jsonPayload = {};
	jsonPayload["user"] = values[0].trim();
	jsonPayload["accType"] = values[1].trim();
	sendServer("./deleteaccount","Post",jsonPayload);
		
	
	
	
}

function addEmployee()
{
	console.log("Add employee is clicked");
	var addRoller = document.getElementById("addRoller1");
	addRoller.style.visibility="visible";
	var userTag = document.getElementById("empUser");
	var pwdTag = document.getElementById("empPwd");
	var user = document.getElementById("empUser").value;
	var pwd = document.getElementById("empPwd").value;
	var userMsg = document.getElementById("userError");
	var pwdMsg = document.getElementById("pwdError");
	
	var servMsg = document.getElementById("servMsg");
	
	var isAdmin = document.getElementById("defaultChecked2").checked;
	
	console.log("isAdmin",isAdmin);
	
	if ((user === "" || user === undefined || user === null) && (pwd === "" || pwd === undefined || pwd === null))
    {
    	
    	
		addRoller.style.visibility='hidden';
    	userMsg.innerText="Username is required";
    	userMsg.style.visibility="visible";
    	pwdMsg.innerText = "Password is required";
    	pwdMsg.style.visibility="visible";
    	
	}
	else if(user === "" || user === undefined || user === null)
	{
		addRoller.style.visibility='hidden';
		userMsg.innerText="Username is required";
    	userMsg.style.visibility="visible";
    	pwdMsg.style.visibility="hidden";
		
	}
	else if(pwd === "" || pwd === undefined || pwd === null)
	{
		addRoller.style.visibility='hidden';
		pwdMsg.innerText = "Password is required";
    	pwdMsg.style.visibility="visible";
    	userMsg.style.visibility="hidden";
	}
	
	if(user.length>0 && pwd.length>0)
    {
		userMsg.style.visibility="hidden";
		pwdMsg.style.visibility="hidden";
		servMsg.style.visibility="hidden";
		addRoller.style.visibility="visible";
		userTag.value="";
		pwdTag.value="";
		var empType = (isAdmin === true) ? "Admin" : "Employee";
		console.log("empType",empType);
		var jsonPayload = {};
		jsonPayload["user"] = user;
		jsonPayload["pwd"] = pwd;
		jsonPayload["empType"] = empType;
		sendServer("./addemployee","Post",jsonPayload);
		
    }
    
}


function addAccount()
{
	console.log("Add account is clicked");
	var addRoller = document.getElementById("addRoller1");
	addRoller.style.visibility="visible";
	var userTag = document.getElementById("accUser");
	var user = document.getElementById("accUser").value;
	var userMsg = document.getElementById("userError");
	var servMsg = document.getElementById("servMsg");
	var accType;
	
if(document.getElementById("Assets").checked)
{
	accType = "Assets";
}

if(document.getElementById("Liabilities").checked)
{
	accType = "Liabilities";
}

if(document.getElementById("Capital").checked)
{
	accType = "Capital";
}

if(document.getElementById("Withdrawal").checked)
{
	accType = "Withdrawal";
}

if(document.getElementById("Income").checked)
{
	accType = "Income";
}

if(document.getElementById("Expense").checked)
{
	accType = "Expense";
}
	
	
	
	if (user === "" || user === undefined || user === null)
    {
    	
    	
		addRoller.style.visibility='hidden';
    	userMsg.innerText="Account name is required";
    	userMsg.style.visibility="visible";
    	
    	
	}
	
	
	
	if(user.length>0)
    {
		userMsg.style.visibility="hidden";
		
		servMsg.style.visibility="hidden";
		addRoller.style.visibility="visible";
		userTag.value="";
		console.log("Accountype",accType);
		var jsonPayload = {};
		jsonPayload["user"] = user;
		jsonPayload["accType"] = accType; 
		sendServer("./addaccount","Post",jsonPayload);
		
    }
    
}

function showAddEmpMsg(myArr)
{
	console.log("running in show add employee")
	var addRoller = document.getElementById("addRoller1");
	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	
	console.log("servMsg",myArr["addEmpMsg"]);
	if(myArr["addEmpMsg"] === "Employee Added")
	{
		servMsg.innerHTML = myArr["addEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
		
		var parentTag = document.getElementById("employeeParent");
		
		var rowTag = document.createElement("div");
		var classDivAttr = document.createAttribute("class");
		classDivAttr.value = "row shadow-card-form p-2 m-3 wow fadeIn";
		rowTag.setAttributeNode(classDivAttr);
		
		
		
		var anchorTag = document.createElement("a")
		var idAttr = document.createAttribute("id");
		idAttr.value = myArr["empName"];
		var classAttr = document.createAttribute("class");
		classAttr.value="customfont col-sm-12 update";
		var clickAttr = document.createAttribute("onclick");
		clickAttr.value = "loadSelectedNames('"+myArr["empName"]+"')";
		var styleAttr = document.createAttribute("style");
		styleAttr.value = "cursor:pointer;";
		
		anchorTag.setAttributeNode(idAttr);
		anchorTag.setAttributeNode(styleAttr);
		anchorTag.setAttributeNode(classAttr);
		anchorTag.setAttributeNode(clickAttr); 
		
		anchorTag.innerHTML = myArr["empName"] + "&nbsp;&nbsp;&nbsp;&nbsp;<span class='material-switch pull-right' onclick='activateUser(event,\""+myArr["empName"]+"-switch\")'><input id='"+myArr["empName"]+"-switch' name='someSwitchOption001' type='checkbox'/>" +
				"<label for='"+myArr["empName"]+"-switch' class='label-primary'></label></span><span class='fas fa-trash col-sm-6 delete'  onclick='deleteSelectedName(event,\""+myArr["empName"]+"\")'></span>";
		rowTag.appendChild(anchorTag);
		
		parentTag.appendChild(rowTag);
		rowTag.scrollIntoView(false);
		
	}
	else
	{
		servMsg.innerHTML = myArr["addEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	
}

function showAddAccMsg(myArr)
{
	console.log("running in show add account")
	var addRoller = document.getElementById("addRoller1");
	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	
	console.log("servMsg",myArr["addAccMsg"]);
	if(myArr["addAccMsg"] === "Account Added")
	{
		servMsg.innerHTML = myArr["addAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
		
		var parentTag = document.getElementById("employeeParent");
		
		var rowTag = document.createElement("div");
		var classDivAttr = document.createAttribute("class");
		classDivAttr.value = "row shadow-card-form p-2 m-3 wow fadeIn";
		rowTag.setAttributeNode(classDivAttr);
		
		
		
		var anchorTag = document.createElement("a")
		var idAttr = document.createAttribute("id");
		idAttr.value = myArr["accName"];
		var classAttr = document.createAttribute("class");
		classAttr.value="customfont col-sm-12 update";
		var clickAttr = document.createAttribute("onclick");
		clickAttr.value = "loadSelectedAccName('"+myArr["accName"]+"')";
		var styleAttr = document.createAttribute("style");
		styleAttr.value = "cursor:pointer;";
		
		anchorTag.setAttributeNode(idAttr);
		anchorTag.setAttributeNode(styleAttr);
		anchorTag.setAttributeNode(classAttr);
		anchorTag.setAttributeNode(clickAttr); 
		
		anchorTag.innerHTML = myArr["accName"] + "&nbsp;<span class='fas fa-trash col-sm-6 delete'  onclick='deleteSelectedAccName(event,\""+myArr['accName']+"\")'></span>";
		rowTag.appendChild(anchorTag);
		
		parentTag.appendChild(rowTag);
		rowTag.scrollIntoView(false);
		document.getElementById("Assets").checked = true;
	}
	else
	{
		servMsg.innerHTML = myArr["addAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	
}


function updateEmployee()
{
	console.log("Update employee is clicked");
	var addRoller = document.getElementById("addRoller2");
	addRoller.style.visibility="visible";
	var userTag = document.getElementById("empUser");
	var pwdTag = document.getElementById("empPwd");
	var user = document.getElementById("empUser").value;
	var pwd = document.getElementById("empPwd").value;
	var userMsg = document.getElementById("userError");
	var pwdMsg = document.getElementById("pwdError");
	
	var servMsg = document.getElementById("servMsg");
	
	var isAdmin = document.getElementById("defaultChecked2").checked;
	
	console.log("isAdmin",isAdmin);
	
	if ((user === "" || user === undefined || user === null) && (pwd === "" || pwd === undefined || pwd === null))
    {
    	
    	
		addRoller.style.visibility='hidden';
    	userMsg.innerText="Username is required";
    	userMsg.style.visibility="visible";
    	pwdMsg.innerText = "Password is required";
    	pwdMsg.style.visibility="visible";
    	
	}
	else if(user === "" || user === undefined || user === null)
	{
		addRoller.style.visibility='hidden';
		userMsg.innerText="Username is required";
    	userMsg.style.visibility="visible";
    	pwdMsg.style.visibility="hidden";
		
	}
	else if(pwd === "" || pwd === undefined || pwd === null)
	{
		addRoller.style.visibility='hidden';
		pwdMsg.innerText = "Password is required";
    	pwdMsg.style.visibility="visible";
    	userMsg.style.visibility="hidden";
	}
	
	if(user.length>0 && pwd.length>0)
    {
		userMsg.style.visibility="hidden";
		pwdMsg.style.visibility="hidden";
		servMsg.style.visibility="hidden";
		addRoller.style.visibility="visible";
		userTag.value="";
		pwdTag.value="";
		var empType = (isAdmin === true) ? "Admin" : "Employee";
		console.log("empType",empType);
		var jsonPayload = {};
		jsonPayload["user"] = user;
		jsonPayload["pwd"] = pwd;
		jsonPayload["empType"] = empType;
		sendServer("./updateemployee","Post",jsonPayload);
		
    }
    
}

function updateAccount()
{
	console.log("Update account is clicked");
	var addRoller = document.getElementById("addRoller2");
	addRoller.style.visibility="visible";
	var userTag = document.getElementById("accUser");
	
	var user = document.getElementById("accUser").value;
	var oldUser = document.getElementById("accUser").name;
	console.log("oldUser",oldUser);
	var userMsg = document.getElementById("userError");
	
	
	var servMsg = document.getElementById("servMsg");
	var accType;
	
	if(document.getElementById("Assets").checked)
	{
		accType = "Assets";
	}

	if(document.getElementById("Liabilities").checked)
	{
		accType = "Liabilities";
	}

	if(document.getElementById("Capital").checked)
	{
		accType = "Capital";
	}

	if(document.getElementById("Withdrawal").checked)
	{
		accType = "Withdrawal";
	}

	if(document.getElementById("Income").checked)
	{
		accType = "Income";
	}

	if(document.getElementById("Expense").checked)
	{
		accType = "Expense";
	}

	if(user === "" || user === undefined || user === null)
    {
    	
    	
		addRoller.style.visibility='hidden';
    	userMsg.innerText="Account name is required";
    	userMsg.style.visibility="visible";
    	
    	
	}
	
	
	if(user.length>0)
    {
		userMsg.style.visibility="hidden";
		servMsg.style.visibility="hidden";
		addRoller.style.visibility="visible";
		userTag.value="";
		
		
		var jsonPayload = {};
		jsonPayload["user"] = user;
		jsonPayload["accType"] = accType;
		jsonPayload["oldUser"] = oldUser;
		sendServer("./updateaccount","Post",jsonPayload);
		
    }
    
}

function showUpdEmpMsg(myArr)
{
	console.log("running in show upd employee");
	document.getElementById("empUser").disabled=false;
	document.getElementById("addEmpBtn").disabled=false;
	var addRoller = document.getElementById("addRoller2");
	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	
	console.log("servMsg",myArr["updEmpMsg"]);
	if((myArr["updEmpMsg"] === "Db Error") || (myArr["updEmpMsg"] === "User is an Admin") || (myArr["updEmpMsg"] === "User is an Employee") || (myArr["updEmpMsg"] === "Db Error in update"))
	{
		servMsg.innerHTML = myArr["updEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	else
	{
		servMsg.innerHTML = myArr["updEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
	}
	
}

function showUpdAccMsg(myArr)
{
	console.log("running in show upd account");
	var addRoller = document.getElementById("addRoller2");
	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	console.log("servMsg",myArr["updAccMsg"]);
	if(myArr["updAccMsg"] === "Db Error in update")
	{
		servMsg.innerHTML = myArr["updAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	else
	{
		servMsg.innerHTML = myArr["updAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
		var idVal = myArr["oldAccName"]
		document.getElementById(idVal).innerHTML = myArr["newAccName"] + "&nbsp;<span class='fas fa-trash col-sm-6 delete'  onclick='deleteSelectedAccName(event,\""+myArr["newAccName"]+"\")'></span>";
		document.getElementById(idVal).setAttribute( "onClick", "loadSelectedAccName('"+myArr["newAccName"]+"')");
		document.getElementById(idVal).id = myArr["newAccName"];
		var radioButtons = document.querySelectorAll("input[type='radio']");
		for(i=0;i<radioButtons.length;i++)
		{
			radioButtons[i].disabled=false;
		}
		document.getElementById("Assets").checked = true;
	}
	
}


function showDelEmpMsg(myArr)
{
	console.log("running in show del employee")
//	var addRoller = document.getElementById("addRoller");
//	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	
	console.log("servMsg",myArr["delEmpMsg"]);
	if((myArr["delEmpMsg"] === "Db Error") || (myArr["delEmpMsg"] === "Db Error in delete"))
	{
		servMsg.innerHTML = myArr["delEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	else
	{
		servMsg.innerHTML = myArr["delEmpMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
		var element = document.getElementById(myArr["name"]);
		var row = element.parentNode;
		var container = row.parentNode;
		container.removeChild(row);
		
	}
	
}


function showDelAccMsg(myArr)
{
	console.log("running in showDelAccMsg")
//	var addRoller = document.getElementById("addRoller");
//	addRoller.style.visibility="hidden";
	var servMsg = document.getElementById("servMsg");
	
	console.log("servMsg",myArr["delAccMsg"]);
	if(myArr["delAccMsg"] === "Db Error in delete")
	{
		servMsg.innerHTML = myArr["delAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "red";
	}
	else
	{
		servMsg.innerHTML = myArr["delAccMsg"];
		servMsg.style.visibility = "visible";
		servMsg.style.color = "green";
		var element = document.getElementById(myArr["name"]);
		var row = element.parentNode;
		var container = row.parentNode;
		container.removeChild(row);
		
	}

}


function showHomePage()
{
	document.getElementById("secondRow").innerHTML = "";
	document.getElementById("thirdRow").innerHTML = "";
	document.getElementById("rateError").style.visibility = "hidden";
	var loggedUser = document.getElementById("loggedUser").dataset.name;
	console.log("loggedUser",loggedUser);
	sendServer("./home","GET",loggedUser);
}



