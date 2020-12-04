<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


 
<nav class="navbar fixed-top navbar-expand-lg scrolling-navbar shadow-card pudubar">
                        <a class="navbar-brand" onclick="showHomePage()"> 
                            <img src="icons/actualogo.png" height="60px" width="150px" alt="base image">
                        </a> 
                        <button id="button1" class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fas fa-align-justify" style="color:#F97E41"></i>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        
                            <ul class="navbar-nav">
                            	<li class="nav-item active">
                                    <a id="home" class="loginLabels  update" style="color:white;font-size:20px;" onclick="showHomePage()">Home &nbsp;&nbsp;<span class="sr-only">(current)</span></a>
                                </li>
                                <li class="nav-item">
                                    <a id="employees" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readEmployees()">Employees &nbsp;&nbsp;</a>
                                </li>
                                <li class="nav-item">
                                    <a id="exrates" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readRates()">Exchange Rates &nbsp;&nbsp;</a>
                                </li>
                                 <li class="nav-item">
                                    <a id="accounts" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readAccounts()">Accounts &nbsp;&nbsp;</a>
                                </li>
                                <li class="nav-item">
                                    <a id="journal" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readJournal()">Journal &nbsp;&nbsp;</a>
                                </li>
                                <li class="nav-item">
                                    <a id="ledger" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readLedger()">Ledger &nbsp;&nbsp;</a>
                                </li>
                                <li class="nav-item">
                                    <a id="trialBal" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readTrialBal()">Trial Balance &nbsp;&nbsp;</a>
                                </li>
                                <li class="nav-item">
                                    <a id="income" class="loginLabels  update" style="color:white;font-size:20px;" onclick="readIncome()">Income &nbsp;&nbsp;</a>
                                </li>
                                 
                                 
                                
                                
                            </ul>
                            <form class="form-inline my-2 my-lg-0 ml-auto"> 
	                                <div class="nav-item dropdown">
	        								<a id="loggedUser" data-name="${user}" class="nav-link dropdown-toggle loginLabels" style="color:white;font-size:20px;" id="navbarDropdownMenuLink-333" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          									<i  class="fas fa-user" style="color:white;font-size:20px;"></i>&nbsp;${user}
	        								</a>
									        <div id="dropDown1" class="dropdown-menu dropdown-menu-left dropdown-default text-center" aria-labelledby="navbarDropdownMenuLink-333">
									            <a class="dropdown-item loginLabels" href="./logout"><i class="fas fa-sign-out-alt"></i>&nbsp;&nbsp;Logout</a>
									          
									        </div>
	      							</div>
      						</form>
                            
                        </div>
                        
</nav>

<br>
<br>
<br>
<br>
<br>
<br>
<div class="container" style="height:1000px;">
	<div id="myContainer" class="row" >
		<div class="col-sm-12">
				<h3>Quick Links </h3>
      			<br>
      			<ul style="list-style: none;">
      				<li> <a href="#" onclick="openTab('employees')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fa fa-users" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Employees</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('exrates')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fas fa-dollar-sign" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Exchange Rates</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('accounts')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fa fa-user-tie" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Accounts</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('journal')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="far fa-address-book" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Journal</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('ledger')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fas fa-book" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Ledger</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('trialBal')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fas fa-balance-scale" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Trial Balance</a></li>
      				<br>
      				<li> <a href="#" onclick="openTab('income')" style="font-size:20px;" class="loginLabels homeColor update"><i id="icon" class="fas fa-money-bill-alt" style="text-shadow: rgb(0, 86, 179) 0px 0px 0px, rgb(0, 90, 186) 1px 1px 0px, rgb(0, 93, 194) 2px 2px 0px, rgb(0, 97, 201) 3px 3px 0px, rgb(0, 101, 209) 4px 4px 0px, rgb(0, 105, 217) 5px 5px 0px, rgb(0, 108, 224) 6px 6px 0px, rgb(0, 112, 232) 7px 7px 0px, rgb(0, 116, 240) 8px 8px 0px, rgb(0, 119, 247) 9px 9px 0px; font-size: 24px; color: rgb(255, 255, 255); height: 47px; width: 47px; line-height: 47px; border-radius:10px ; text-align: center; background-color: rgb(0, 123, 255);"></i>&nbsp;&nbsp;Income</a></li>
      			
      			</ul>
		</div>
	</div>
	<br>
	<span id="rateError" style="visibility:hidden;color:red;">Choose a country from the drop down</span> 
	<div id="secondRow" class="row" >
		
	</div>
	<br><br>
	<div id="thirdRow" class="row" >
		
	</div>
	
</div>

<!-- <footer class="page-footer mt-5 footerColor" style="position:relative;">
                
                
        

   
    <div class="footer-copyright py-3 text-center">
        &#169; 2018 - 19 Copyright:
        <a href="#">Pudugai Travels</a> | <a target="_blank" href="https://www.google.com/maps/search/?api=1&query=Fz+Systems+Trichy" style="font-size:14px">Powered by Fz Systems,Trichy</a>
    </div>
    
</footer>-->

