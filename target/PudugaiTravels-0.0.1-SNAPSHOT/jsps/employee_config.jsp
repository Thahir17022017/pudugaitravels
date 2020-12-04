<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

      		<div class="col-sm-5">
      				
      			 <div style="font-size:30px;diplay:inline-block;">
      						Employee List 
      			 </div>
      			
      			
      			
      			 
      			<br>
      			<div id="employeeParent" class="container" style="list-style: none;height:380px;overflow-y:auto;">
      				<!-- <li> <a href="#" onclick="openTab('employees')" style="color:black;">&nbsp;&nbsp;Employees</a></li> -->
      						
      						
      						<c:forEach items="${list}"  var="listItem" varStatus="myIndex">
      								<div id="${myIndex.index}" class="row shadow-card-form p-2 m-3 wow fadeIn">
	            					<a id="${listItem.empName}" class="customfont col-sm-12 update" onclick="loadSelectedNames('${listItem.empName}')" style="cursor:pointer;">${listItem.empName}&nbsp;&nbsp;&nbsp;&nbsp;
	            						<c:choose>  
											<c:when test="${listItem.empStatus == 'Y'}">  
											  <span class="material-switch pull-right" onclick="activateUser(event,'${listItem.empName}-switch')">
                            					<input id="${listItem.empName}-switch" name="someSwitchOption001" type="checkbox" checked/>
                            					<label for="${listItem.empName}-switch" class="label-primary"></label>
                        					  </span>
											</c:when>  
											<c:otherwise>  
												<span class="material-switch pull-right" onclick="activateUser(event,'${listItem.empName}-switch')">
	                            					<input id="${listItem.empName}-switch" name="someSwitchOption001" type="checkbox"/>
	                            					<label for="${listItem.empName}-switch" class="label-primary"></label>
                        						</span>
											</c:otherwise>  
										</c:choose>  
	            						
	            						<span class='fas fa-trash col-sm-6 delete'  onclick="deleteSelectedName(event,'${listItem.empName}')"></span>
	            					</a>
	            					</div>
	        				</c:forEach>	
      			
      			</div>
      			
      			
      		</div>
      		<div class="col-sm-6 pt-2 ml-3 configMb">
      				<!-- Default checked -->
					<div class="custom-control custom-checkbox">
					  <input type="checkbox" class="custom-control-input" id="defaultChecked2" style="cursor:pointer;">
					  <label class="custom-control-label loginLabels" for="defaultChecked2" style="font-size:18px;cursor:pointer;">Admin</label>
					</div>
					<br>
	      			<!-- Material input -->
					<div class="md-form">
					  <input type="text" id="empUser" class="form-control" style="cursor:pointer;">
					  <label id="empUserLabel" for="empUser" class="loginLabels" style="font-size:20px;cursor:pointer;">Username :</label>
					  <span id="userError" class="loginLabels" style="color:red;visibility:hidden;">Error</span>
					</div>
				<!-- Material input -->
					<div class="md-form">
					  <input type="password" id="empPwd" class="form-control" style="cursor:pointer;">
					  <label id="empPwdLabel" for="empPwd" class="loginLabels" style="font-size:20px;cursor:pointer;">Password :</label>
					  <span id="pwdError" class="loginLabels" style="color:red;visibility:hidden;">Error</span>
					</div>
					<!-- <div class="spinner-border text-success" role="status">
	  						<span class="sr-only">Loading...</span>
					</div> -->
					
						<button id="addEmpBtn" class="btn btn-primary loginLabels"  onclick="addEmployee()" style="border-radius:8px;width:250px;"><i class="fas fa-plus"></i> Add Employee &nbsp;&nbsp;<span id="addRoller1" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
						<button class="btn btn-default loginLabels" onclick="updateEmployee()" style="border-radius:8px;width:250px"><i class="fas fa-pen"></i> Update Employee &nbsp;&nbsp;<span id="addRoller2" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span></button>
						<br>
					
					<div>
						<!--<div id="addRoller" class="lds-rolling" style="width:50%;height:50%;visibility:hidden;">
							<div>
							</div>
						</div>-->
						<span id="servMsg" class="loginLabels" style="position:relative;left:50px;visibility:hidden;">Employee Added</span>
					</div>
			</div>
			
			<!-- <div class="col-sm-4">
			</div> -->		
      		
      		
