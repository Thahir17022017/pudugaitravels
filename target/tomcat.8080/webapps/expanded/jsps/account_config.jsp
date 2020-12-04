<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

      		<div class="col-sm-5">
      				
      			 <div style="font-size:30px;diplay:inline-block;">
      						Account List 
      			 </div>
      			
      			
      			
      			 
      			<br>
      			<div id="employeeParent" class="container" style="list-style: none;height:380px;overflow-y:auto;">
      				<!-- <li> <a href="#" onclick="openTab('employees')" style="color:black;">&nbsp;&nbsp;Employees</a></li> -->
      						
      						
      						<c:forEach items="${list}"  var="listItem" varStatus="myIndex">
      								<div id="${myIndex.index}" class="row shadow-card-form p-2 m-3 wow fadeIn">
	            					<a id="${listItem}" class="customfont col-sm-12 update" onclick="loadSelectedAccName('${listItem}')" style="cursor:pointer;">${listItem} <span class='fas fa-trash col-sm-6 delete'  onclick="deleteSelectedAccName(event,'${listItem}')"></span></a>
	            					</div>
	        				</c:forEach>	
      			
      			</div>
      			
      			
      		</div>
      		<div class="col-sm-6 pt-2 ml-3 configMb">
      				<!-- Default checked -->
					
	      			<!-- Material input -->
					<div class="md-form">
					  <input type="text" id="accUser" class="form-control" style="cursor:pointer;">
					  <label id="accUserLabel" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Account Name:</label>
					  <span id="userError" class="loginLabels" style="color:red;visibility:hidden;">Error</span>
					</div>
					
					<!-- Default inline 1-->
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Assets" name="inlineDefaultRadiosExample" checked="checked">
					  <label class="custom-control-label loginLabels" for="Assets">Assets</label>
					</div>
					
					<!-- Default inline 2-->
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Liabilities" name="inlineDefaultRadiosExample">
					  <label class="custom-control-label loginLabels" for="Liabilities">Liabilities</label>
					</div>
					
					<!-- Default inline 3-->
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Capital" name="inlineDefaultRadiosExample">
					  <label class="custom-control-label loginLabels" for="Capital">Capital</label>
					</div>
					
					<!-- Default inline 3-->
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Withdrawal" name="inlineDefaultRadiosExample">
					  <label class="custom-control-label loginLabels" for="Withdrawal">Withdrawal</label>
					</div>
					
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Income" name="inlineDefaultRadiosExample">
					  <label class="custom-control-label loginLabels" for="Income">Income</label>
					</div>
					
					<div class="custom-control custom-radio custom-control-inline">
					  <input type="radio" class="custom-control-input" id="Expense" name="inlineDefaultRadiosExample">
					  <label class="custom-control-label loginLabels" for="Expense">Expense</label>
					</div>
				
					<!-- <div class="spinner-border text-success" role="status">
	  						<span class="sr-only">Loading...</span>
					</div> -->
					<br><br><br>
						<button class="btn btn-primary loginLabels"  onclick="addAccount()" style="border-radius:8px;width:250px;"><i class="fas fa-plus"></i> Add Account &nbsp;&nbsp;<span id="addRoller1" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
						<button class="btn btn-default loginLabels" onclick="updateAccount()" style="border-radius:8px;width:250px;"><i class="fas fa-pen"></i> Update Account &nbsp;&nbsp;<span id="addRoller2" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
						<br>
					
					<div>
						<!--<div id="addRoller" class="lds-rolling" style="width:50%;height:50%;visibility:hidden;">
							<div>
							</div>
						</div>-->
						<span id="servMsg" class="loginLabels" style="position:relative;left:50px;visibility:hidden;">Account Added</span>
					</div>
					<div>
						<div class="md-form" style="width:250px;">
											<input tabindex=1 type="text" id="closeAccDate" class="form-control" onclick="showCloseAccountCalendar()" style="cursor:pointer;" required>
											<label id="closeAccDateLbl" class="loginLabels" for="closeAccDate" style="font-size:18px;cursor:pointer;">From Date * :</label>
						</div>
						<div>
							<span id="closeMsg" class="loginLabels" style="color:red;position:relative;left:50px;visibility:hidden;">Close Account Date Required</span>
						</div>
						<button class="btn btn-primary loginLabels"  onclick="closeAllAccounts()" style="border-radius:8px;width:280px;"><i class="fas fa-times"></i> Close All Accounts &nbsp;&nbsp;<span id="closeAccRoller" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
					</div>
			</div>
			
			<!-- <div class="col-sm-4">
			</div> -->		
      		
      		
