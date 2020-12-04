<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-sm-6">
			<h2>Journal Summary</h2>
</div>
<br>
<div class="container-fluid">
	<div class="row">
			<div class="col-sm-4">
							<div class="md-form" style="width:250px;">
											<input tabindex=1 type="text" id="searchDate" class="form-control" onclick="openSearchCalendar()" style="cursor:pointer;" required >
											<label id="searchDtLbl" class="loginLabels" for="searchDate" style="font-size:18px;cursor:pointer;">Search Date :</label>
							</div>
			</div>
			<div class="col-sm-4">
							 <div id="accDropDown" tabindex=3 class="dropdown mt-3" style="outline:0;">
			
			    					<!--Trigger-->
								    <button  id="jnlAcc" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"
								      aria-haspopup="true" aria-expanded="false" style="width:250px;border-radius:8px;outline:0;">Account :</button>
								
								
								    <!--Menu-->
								    <div class="dropdown-menu dropdown-primary" style="height:200px;overflow-y:auto;">
								    	<c:forEach items="${list}"   var="listItem" varStatus="myIndex">
								        	<a class="dropdown-item " onclick="loadJnlAcc('${listItem}')">${listItem}</a>
								      	</c:forEach>
								    </div>
			  				</div>
			  				
			  				
			</div>
			<div class="col-sm-4">
						<button tabindex=9 id="postBtn" type="button" class="btn btn-default ml-0 mt-4 loginLabels" style="width:250px;border-radius:8px;" onclick="searchJnrl()" data-update-Id>Search Journal <span id="srchJnlSpinner" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
			</div>	
	</div>
	<br>
	<div class="row">
		<div class="col-sm-12">
			<i class="fas fa-cloud-download-alt fa-2x downloadLedger" style="cursor:pointer;" onclick="downloadJnlReport()"></i>&nbsp;&nbsp;
			
		</div>
	</div>
	<br>
			<div class="row">
				<div class="col-sm-12">
					<span id="searchErr" class="loginLabels" style="color:red;display:none;background-color:#ffe57f;padding:10px;border-radius:5px;">Atleast date field (or) date with account name are needed for search</span>
				</div>
			</div>
	<br>
	<div class="row">
			<div id="linearProg" class="prog1" style="visibility:hidden;">
			  		<div class="indeter"></div>
			</div>
	</div>
	<br>		
	<div class="row">
				<div class="col-sm-12">
				<!--  -->
					<div class="table-responsive" style="height:800px;overflow-y:auto;">
						  <table id="viewJnlTable" class="table table-striped table-sm">
							<!--Table head-->
						          <thead class="z-depth-3 aqua-gradient white-text th-sm">
						            <tr>
						              <th class="tbColWidth loginLabels" style="text-align:center;">Date</th>
						              <th class="tbColWidth loginLabels" style="text-align:left">Description</th>
						              <th class="tbColWidth loginLabels" style="text-align:left">Account</th>
						              <th class="tbColWidth loginLabels" style="text-align:right">DR Amount</th>
						              <th class="tbColWidth loginLabels" style="text-align:right">CR Amount</th>
						              <th class="tbColWidth loginLabels" style="text-align:center">Operations</th>
						            </tr>
						          </thead>
						         <!--Table head-->
						         <tbody id="searchTblBody">
						     		<c:forEach items="${journals}"   var="journal" varStatus="myIndex">
						            <tr id="${journal.journalId}">
						               <td class="tbColWidth loginLabels" style="text-align:center">${journal.journalDt}</td>
						               <td class="tbColWidth loginLabels" style="text-align:left">${journal.journalDesc}</td>
						               <td class="tbColWidth loginLabels" style="text-align:left">${journal.journalAcc}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${journal.journalDrAmt}</td>
						               <td class="tbColWidth loginLabels" style="text-align:right">${journal.journalCrAmt}</td>
						                <!--<c:if test="${journal.journalDrCrType == 'Debit'}">
											<td class="tbColWidth" style="text-align:center"><i onclick="loadJournal(${journal.journalId})" class="fas fa-pen-alt"></i>&nbsp;&nbsp;<i onclick="delJournal(${journal.journalId})"class="fas fa-trash-alt"></i></td>
										</c:if>-->
										<c:choose>  
											<c:when test="${journal.journalDrCrType == 'Debit'}">  
											  <td class="tbColWidth" style="text-align:center"><i onclick="loadUpdJnlView(${journal.journalId})" class="fas fa-expand updateIcon2" style="cursor:pointer;"></i></td>
											</c:when>  
											<c:otherwise>  
												<td class="tbColWidth" style="text-align:center"></td>	
											</c:otherwise>  
										</c:choose> 
						               
						               
						            </tr>
						            </c:forEach>
						         </tbody>
						 </table>
					</div>		         	
				</div>
	</div>
</div>