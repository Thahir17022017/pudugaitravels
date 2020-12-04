<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-sm-12">

<!--<c:forEach items="${CurrList}" var="curr">
	    		<p>${curr}</p><br>
</c:forEach>-->
<h3>Result </h3>
<br>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> From : </span>${from}</p>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> Date/Time : </span>${timestamp}</p>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> Result : </span>${result}</p>
<br>
<!-- <div class="table-responsive" style="height:1000px;overflow-y:auto;"> -->
<div class="table-responsive" style="height:1000px;">
  <table id="dtBasicExample" class="table table-bordered table-striped table-sm" cellspacing="0" width="100%">
	<!--Table head-->
          <thead class="aqua-gradient white-text th-sm">
            <tr class="shadow-card">
              <th class="tbColWidth" style="text-align:center;">#</th>
              <th class="tbColWidth" style="text-align:center;">Country</th>
              <th class="tbColWidth" style="text-align:center">Rate</th>
            </tr>
          </thead>
         <!--Table head-->
         <tbody>
     		<c:forEach items="${ctryList}" var="curr" varStatus="i">   
            <tr>
              <th class="tbColWidth" style="text-align:center" scope="row">${i.index+1}</th>
              <td class="tbColWidth" style="text-align:center">${curr.ctryValue}</td>
              <td class="tbColWidth" style="text-align:center">${curr.currValue}</td>
            </tr>
            </c:forEach>
         </tbody>
             
  </table>
  
</div>
<!-- </div>-->

