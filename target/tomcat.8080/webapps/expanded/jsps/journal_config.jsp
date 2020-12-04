<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
	<div class="row">
		<div class="col-sm-6">
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
		<div class="col-sm-6">
				<button id="viewJnlBtn" type="button" class="btn peach-gradient loginLabels" style="width:240px;border-radius:8px;" onclick="loadJournalView()" data-update-Id>Journal Summary&nbsp;&nbsp;&nbsp;&nbsp;<span id="viewJnlSpinner" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
				<br>
				<button id="addUpdJnlBtn" type="button" class="btn purple-gradient loginLabels" style="width:240px;border-radius:8px;" onclick="loadAddUpdJournalView()" data-update-Id>Journal Config&nbsp;&nbsp;&nbsp;&nbsp;<span id="addupdJnlSpinner" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
		</div>
	</div>
	<br>
	<div id="loadJnlRow" class="row">

	</div>
</div>   


        
