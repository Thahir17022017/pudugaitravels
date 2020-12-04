<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
				<h4 class="text-center">Pudugai Travels</h4>
				<h4 class="text-center">Income Statement</h4>
				<h6 class="text-center">${date}</h6>
				

		</div>
	</div>
	<br>
	<div clas="row">
		<div class="col-sm-12">
			<div class="table-responsive" style="height:1000px;overflow-y:auto;">
						  <table id="trialbalance" class="table table-bordered table-striped table-sm">
							<!--Table head-->
						          <thead class="aqua-gradient white-text th-sm">
						            <tr class="shadow-card">
						              <th class="tbColWidth" style="text-align:left;">Account Title</th>
						              <th class="tbColWidth" style="text-align:right;">Debit (Dr)</th>
						              <th class="tbColWidth" style="text-align:right;">Credit (Cr)</th>
						              
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody id="trialBalTbl">
						           					            
						            <c:forEach items="${income}"   var="income" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${income.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${income.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${income.creditBal}</td>
						            </tr>
						            </c:forEach>
						             <c:forEach items="${expenses}"   var="expenses" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${expenses.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${expenses.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${expenses.creditBal}</td>
						            </tr>
						            </c:forEach>
						            <tr>
						               <td colspan="2" class="tbColWidth loginLabels" style="text-align:center">${profitLoss}</td>
						               <!--<td id="drTotal" class="tbColWidth loginLabels" style="text-align:right">${totalDebits}</td>-->
						               <td id="crTotal" class="tbColWidth loginLabels" style="text-align:right">${incomeval}</td>
						            </tr>
						         </tbody>
						  </table>
				</div>
		</div>
	</div>
</div>	