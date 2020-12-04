<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <div class="col-sm-6">
    <h3>Cumulative Rates</h3> 
    <br>
  
  <div class="dropdown">

    <button id="chosenCountry"class="btn btn-primary dropdown-toggle loginLabels" type="button" id="dropdownMenu1" data-toggle="dropdown"
      aria-haspopup="true" aria-expanded="false" style="border-radius:8px;">Select Country Base</button>

    <div class="dropdown-menu dropdown-primary loginLabels" style="height:200px;overflow-y:auto;">
	     <c:forEach items="${CountryList}" var="entry">
	    		<a id="${entry}" class="dropdown-item" onclick="chooseCountry('${entry}')">${entry}</a>
		</c:forEach>
    </div>
  </div>
 <button class="btn btn-primary cumRatesBtn cumRatesBtnMbl loginLabels" onclick="checkCurrentRate()" type="button" style="border-radius:8px;top:-1px;">
   <i class="fas fa-chart-line"></i>
    Check Rate &nbsp;&nbsp;<span id="currRoller" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span>
 </button>
  
  

</div>
<!-- <div id="currTable" class="col-sm-4">
<button class="btn btn-primary cumRatesBtn cumRatesBtnMbl loginLabels" onclick="checkCurrentRate()" type="button" style="border-radius:8px;">
   <i class="fas fa-chart-line"></i>
    Check Rate <span id="currRoller" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span>
 </button>
</div>
 <div class="col-sm-4">
 </div>-->

   	

 
 