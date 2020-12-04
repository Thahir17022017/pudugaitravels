<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
				<h3>Ledger Summary</h3>

		</div>
	</div>
	<br>
	<div clas="row">
		<div class="col-sm-12">
			<div class="table-responsive">
						  <table id="dtBasicExample" class="table table-bordered table-striped table-sm">
							<!--Table head-->
						          <thead class="aqua-gradient white-text th-sm">
						            <tr class="shadow-card">
						              <th class="tbColWidth" style="text-align:center;">Accounting Element</th>
						              <th class="tbColWidth" style="text-align:center;">Normal Balance</th>
						              <th class="tbColWidth" style="text-align:center">To Increase</th>
						              <th class="tbColWidth" style="text-align:center">To Decrease</th>
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody>
						     		
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Assets</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						            </tr>
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Liabilities</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						            </tr>
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Capital</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						            </tr>
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Withdrawal</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						            </tr>
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Income</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						            </tr>
						            <tr>
						              <th class="tbColWidth" style="text-align:center">Expense</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Debit</th>
						               <th class="tbColWidth" style="text-align:center">Credit</th>
						            </tr>
						           
						         </tbody>
						             
						  </table>
				</div>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-sm-3">
			<div class="md-form" style="width:250px;">
											<input tabindex=1 type="text" id="fromLedgerDate" class="form-control" onclick="fromJnlCalendar()" style="cursor:pointer;" required>
											<label id="fromJnlDateLbl" class="loginLabels" for="fromJnlDate" style="font-size:18px;cursor:pointer;">From Date * :</label>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="md-form" style="width:250px;">
											<input tabindex=2 type="text" id="toLedgerDate" class="form-control" onclick="toJnlCalendar()" style="cursor:pointer;" required>
											<label id="toJnlDateLbl" class="loginLabels" for="toJnlDate" style="font-size:18px;cursor:pointer;">To Date * :</label>
			</div>
		</div>
		<div class="col-sm-3">
			<div id="accDropDown" tabindex=3 class="dropdown mt-3" style="outline:0;">
			
			    					<!--Trigger-->
								    <button  id="ledgerAcc" class="btn btn-primary dropdown-toggle ml-0" type="button" data-toggle="dropdown"
								      aria-haspopup="true" aria-expanded="false" style="width:250px;border-radius:8px;">Ledger Account * :</button>
								
								
								    <!--Menu-->
								    <div class="dropdown-menu dropdown-primary" style="height:200px;overflow-y:auto;">
								    	<c:forEach items="${list}"   var="listItem" varStatus="myIndex">
								        	<a class="dropdown-item " onclick="loadledgerAcc('${listItem}')">${listItem}</a>
								      	</c:forEach>
								    </div>
			</div>
		</div>
		<div class="col-sm-3">
			<button tabindex=4 id="searchLedBtn" type="button" class="btn btn-default ml-0 mt-4 loginLabels" style="width:250px;border-radius:8px;" onclick="searchLedger()" >Search Ledger <span id="srchLedSpinner" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-sm-12">
		   	<span id="searchLedgerErr" class="loginLabels" style="color:red;display:none;background-color:#ffe57f;padding:10px;border-radius:5px;">Required fields are needed</span>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-sm-6">
			<i class="fas fa-cloud-download-alt fa-2x downloadLedger" style="cursor:pointer;" onclick="downloadReport()"></i>&nbsp;&nbsp;
			
		</div>
		<div class="col-sm-6">
		   	<i class="fas fa-print fa-2x printTable" style="cursor:pointer;float:right;" onclick="printTable()"></i>
		</div>
	</div>
	<br>
	<div id="cloudProgress" class="prog1" style="visibility:hidden;">
			  		<div class="indeter"></div>
	</div>
	<div class="row">
		<div class="col-sm-12" id="printContent">
		 		<h4 id="ledgerAccName" class="text-center loginLabels"></h4><br>
				<h5 id="ledgerAccBal" class="loginLabels" style="">Account Balance : <span id="ledgerAccBalance" class="loginLabels"></span></h5><br>
				<h5 id="dateRange" class="loginLabels" style="">Date Range : <span id="ledgerDateRange" class="loginLabels"></span></h5><br>
				<div class="table-responsive" style="height:600px;overflow-y:auto;">
						  <table id="viewLedgerTable" class="table table-striped table-sm">
							<!--Table head-->
						          <thead class="z-depth-3 aqua-gradient white-text th-sm">
						            <tr>
						              <th class="tbColWidth loginLabels" style="text-align:center;">#</th>
						              <th class="tbColWidth loginLabels" style="text-align:center;">Date</th>
						              <th class="tbColWidth loginLabels" style="text-align:left">Description</th>
						              <th class="tbColWidth loginLabels" style="text-align:right">DR Amount</th>
						              <th class="tbColWidth loginLabels" style="text-align:right">CR Amount</th>
						             
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody id="searchTblBody">
						     		<c:forEach items="${journals}"   var="journal" varStatus="myIndex">
						            <tr id="${journal.journalId}">
						               <td class="tbColWidth loginLabels" style="text-align:center">${journal.journalDt}</td>
						               <td class="tbColWidth loginLabels" style="text-align:center">${journal.journalDt}</td>
						               <td class="tbColWidth loginLabels" style="text-align:left">${journal.journalDesc}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${journal.journalDrAmt}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${journal.journalCrAmt}</td>
						            </tr>
						            </c:forEach>
						         </tbody>
						 </table>
					</div>
		</div>
	</div>
</div>