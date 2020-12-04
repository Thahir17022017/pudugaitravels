<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<div class="col-sm-6">
			<h2>Journal Config</h2>
		</div>
		<br>
		<div class="container-fluid">
			<div class="row">
					<div class="col-sm-3">
							<div class="md-form" style="width:250px;">
											<input tabindex=1 type="text" id="calJournal" class="form-control" onclick="openCalendar()" style="cursor:pointer;" required>
											<label id="journalDate" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Journal Date * :</label>
							</div>
					</div>
					<div class="col-sm-3">
							<div class="md-form" style="width:250px;" required>
									<input tabindex=2 type="text" id="journalDesc" class="form-control" style="cursor:pointer;">
									<label id="accUserLabel" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Description * :</label>
						   </div>
					</div>		
					<div class="col-sm-3">
								<div id="accDropDown" tabindex=3 class="dropdown mt-3" style="outline:0;">
			
			    					<!--Trigger-->
								    <button  id="journalAcc" class="btn btn-primary dropdown-toggle ml-0" type="button" data-toggle="dropdown"
								      aria-haspopup="true" aria-expanded="false" style="width:250px;border-radius:8px;outline:0;">Account * :</button>
								
								
								    <!--Menu-->
								    <div class="dropdown-menu dropdown-primary" style="height:200px;overflow-y:auto;">
								    	<c:forEach items="${list}"   var="listItem" varStatus="myIndex">
								        	<a class="dropdown-item " onclick="loadJournalAcc('${listItem}')">${listItem}</a>
								      	</c:forEach>
								    </div>
			  					</div>
			
					</div>
					<div class="col-sm-3">
				     			<div id="drcrDropDown" tabindex=4 class="dropdown mt-3" style="outline:0;">
			
			    					<!--Trigger-->
								    <button  id="drcrType" class="btn btn-primary dropdown-toggle ml-0 loginLabels" type="button" data-toggle="dropdown"
								      aria-haspopup="true" aria-expanded="false" style="width:250px;border-radius:8px;outline:0;">Debit/Credit *:</button>
								
								
								    <!--Menu-->
								    <div class="dropdown-menu dropdown-primary">
								    	<a class="dropdown-item" onclick="loadDrCrType('Debit')">Debit</a>
								    	<a class="dropdown-item" onclick="loadDrCrType('Credit')">Credit</a>
								      	
								    </div>
			  					</div>
			  		</div>
			</div>
			<br>
			<div class="row">
					<div class="col-sm-3">
						<div class="md-form" style="width:250px;">
									<input tabindex=5 id="amount" type="text" class="form-control" onkeypress="isNumberCheck(this)" style="cursor:pointer;" required>
									<label id="accUserLabel" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Amount * :</label>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="md-form" style="width:250px;">
									<input tabindex=6 id="rate" type="text" class="form-control" onkeypress="isNumberCheck(this)" onblur="calculateAmt()" style="cursor:pointer;" required>
									<label id="accUserLabel" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Rate * :</label>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="md-form" style="width:250px;">
									<input tabindex=7 id="dbCrAmt" type="text" class="form-control" style="cursor:pointer;">
									<label id="drcrAmt" class="loginLabels" for="accUser" style="font-size:18px;cursor:pointer;">Debit Amount * :</label>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="md-form" style="width:250px;">
									<input tabindex=8 id="dbCrId" type="text" class="form-control" style="visibility:hidden;">
									<label id="drcrId" class="loginLabels" for="accUser" style="font-size:18px;visibility:hidden;">Credit Id :</label>
						</div>
					</div>
			</div>
			<br>
			<div class="row">
					<div class="col-sm-3">
						<button tabindex=9 id="postBtn" type="button" class="btn btn-default ml-0 mt-3 loginLabels" style="width:250px;border-radius:8px;" onclick="postTransaction()" data-update-Id>Post <span id="postSpinner" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
					</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<span id="journalErr" class="loginLabels" style="color:red;visibility:hidden;">Required fields are needed</span>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<h4>Debit Entries</h4>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<div class="table-responsive" style="height:300px;overflow-y:auto;">
						  <table id="dtBasicExample" class="table table-bordered table-striped table-sm">
							<!--Table head-->
						          <thead class="aqua-gradient white-text th-sm">
						            <tr class="shadow-card">
						              <th class="tbColWidth" style="text-align:center;">Debit Id</th>
						              <th class="tbColWidth" style="text-align:center;">Credit Id</th>
						              <th class="tbColWidth" style="text-align:center;">Date</th>
						              <th class="tbColWidth" style="text-align:center">Description</th>
						              <th class="tbColWidth" style="text-align:center">Account</th>
						              <th class="tbColWidth" style="text-align:center">Amount</th>
						              <th class="tbColWidth" style="text-align:center">Rate</th>
						              <th class="tbColWidth" style="text-align:right">DR Amount</th>
						              <th class="tbColWidth" style="text-align:center">Operations</th>
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody id="debitTblBody">
						     		<c:forEach items="${journals}"   var="journal" varStatus="myIndex">
						     		<c:choose>
						     		<c:when test="${journal.journalDrCrType == 'Debit'}"> 
							            <tr id="${journal.journalId}">
							               <td class="tbColWidth" style="text-align:center">${journal.journalId}</td>
							                <td class="tbColWidth" style="text-align:center"></td>
							               <td class="tbColWidth" style="text-align:center">${journal.journalDt}</td>
							               <td class="tbColWidth" style="text-align:center">${journal.journalDesc}</td>
							               <td class="tbColWidth" style="text-align:center">${journal.journalAcc}</td>
							               <td class="tbColWidth" style="text-align:center">${journal.journalAmt}</td>
							               <td class="tbColWidth" style="text-align:center">${journal.journalRate}</td>
							               <td class="tbColWidth" style="text-align:right">${journal.journalDrAmt}</td>
							               <td class="tbColWidth" style="text-align:center"><i onclick="loadJournal('${journal.journalId}','DEBIT')" class="fas fa-pen-alt updateIcon" style='cursor:pointer;'></i>&nbsp;&nbsp;<i onclick="delJournal(${journal.journalId},'DEBIT')"class="fas fa-trash-alt delete" style='cursor:pointer;'></i></td>
							            </tr>
							        </c:when>    
							        </c:choose>    
						            </c:forEach>
						         </tbody>
						 </table>
					</div>		         	
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<h4>Credit Entries</h4>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<div class="table-responsive" style="height:300px;overflow-y:auto;">
						  <table id="dtBasicExample" class="table table-bordered table-striped table-sm">
							<!--Table head-->
						          <thead class="aqua-gradient white-text th-sm">
						            <tr class="shadow-card">
						              <th class="tbColWidth" style="text-align:center;">Credit Id</th>
						              <th class="tbColWidth" style="text-align:center;">Debit Id</th>
						              <th class="tbColWidth" style="text-align:center;">Date</th>
						              <th class="tbColWidth" style="text-align:center">Description</th>
						              <th class="tbColWidth" style="text-align:center">Account</th>
						              <th class="tbColWidth" style="text-align:center">Amount</th>
						              <th class="tbColWidth" style="text-align:center">Rate</th>
						              <th class="tbColWidth" style="text-align:right">CR Amount</th>
						              <th class="tbColWidth" style="text-align:center">Operations</th>
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody id="creditTblBody">
						     		<c:forEach items="${journals}"   var="journal" varStatus="myIndex">
						     		<c:choose>
						     		<c:when test="${journal.journalDrCrType == 'Credit'}"> 
						            <tr id="${journal.journalId}">
						               <td class="tbColWidth" style="text-align:center">${journal.journalId}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalDrId}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalDt}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalDesc}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalAcc}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalAmt}</td>
						               <td class="tbColWidth" style="text-align:center">${journal.journalRate}</td>
						               <td class="tbColWidth" style="text-align:right">${journal.journalCrAmt}</td>
						               <td class="tbColWidth" style="text-align:center"><i onclick="loadJournal('${journal.journalId}','CREDIT')" class="fas fa-pen-alt updateIcon" style='cursor:pointer;'></i>&nbsp;&nbsp;</td>
						               <!--<i onclick="delJournal(${journal.journalId},'CREDIT')"class="fas fa-trash-alt delete" style='cursor:pointer;'></i>-->
						            </tr>
						            </c:when>    
							        </c:choose> 
						            </c:forEach>
						         </tbody>
						 </table>
					</div>		         	
				</div>
			</div>	
		</div>