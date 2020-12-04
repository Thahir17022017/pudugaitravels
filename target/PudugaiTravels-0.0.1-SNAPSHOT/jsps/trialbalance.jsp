<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
				<i class="fas fa-print fa-2x printTable" style="cursor:pointer;float:right;" onclick="printTrialBal()"></i>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-sm-12">
				<h4 id="heading1" class="text-center">Pudugai Travels</h4>
				<h4 id="heading2" class="text-center">Trial Balance</h4>
				<h6 id="heading3" class="text-center">${date}</h6>
				

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
						           <c:forEach items="${assets}"   var="assets" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${assets.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${assets.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${assets.creditBal}</td>
						              
						                <!--<c:if test="${journal.journalDrCrType == 'Debit'}">
											<td class="tbColWidth" style="text-align:center"><i onclick="loadJournal(${journal.journalId})" class="fas fa-pen-alt"></i>&nbsp;&nbsp;<i onclick="delJournal(${journal.journalId})"class="fas fa-trash-alt"></i></td>
										</c:if>
										<c:choose>  
											<c:when test="${journal.journalDrCrType == 'Debit'}">  
											  <td class="tbColWidth" style="text-align:center"><i onclick="loadUpdJnlView(${journal.journalId})" class="fas fa-expand updateIcon2" style="cursor:pointer;"></i></td>
											</c:when>  
											<c:otherwise>  
												<td class="tbColWidth" style="text-align:center"></td>	
											</c:otherwise>  
										</c:choose> -->
						               
						               
						            </tr>
						            </c:forEach>
						            <c:forEach items="${liabilities}"   var="liabilities" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${liabilities.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${liabilities.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${liabilities.creditBal}</td>
						            </tr>
						            </c:forEach>
						            <c:forEach items="${capital}"   var="capital" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${capital.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${capital.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${capital.creditBal}</td>
						            </tr>
						            </c:forEach>
						            <c:forEach items="${drawings}"   var="drawings" varStatus="myIndex">
						            <tr>
						               <td class="tbColWidth loginLabels" style="text-align:left">${drawings.accName}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${drawings.debitBal}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${drawings.creditBal}</td>
						            </tr>
						            </c:forEach>
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
						               <td class="tbColWidth loginLabels" style="text-align:left">TOTALS</td>
						               <td id="drTotal" class="tbColWidth loginLabels" style="text-align:right">${totalDebits}</td>
						               <td id="crTotal" class="tbColWidth loginLabels" style="text-align:right">${totalCredits}</td>
						            </tr>
						         </tbody>
						  </table>
				</div>
		</div>
	</div>
</div>	