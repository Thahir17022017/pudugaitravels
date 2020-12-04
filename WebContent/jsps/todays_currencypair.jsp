<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <div class="col-sm-3">
    <h3>Currency Pair</h3> 
    <br>
  
  <div class="dropdown">

    <button id="srcCountry" class="btn btn-default dropdown-toggle loginLabels" type="button" id="dropdownMenu1" data-toggle="dropdown"
      aria-haspopup="true" aria-expanded="false" style="border-radius:8px;">Source Country</button>

    <div class="dropdown-menu dropdown-primary loginLabels" style="height:200px;overflow-y:auto;">
	     <c:forEach items="${CountryList}" var="entry">
	    		<a id="${entry}" class="dropdown-item" onclick="chSrcCountry('${entry}')">${entry}</a>
		</c:forEach>
    </div>
 </div>


 
 
  

</div>
<div class="col-sm-3">
 <div class="dropdown destnCtry destnCtryMbl">

    <button id="destCountry"class="btn btn-default dropdown-toggle loginLabels" type="button" id="dropdownMenu1" data-toggle="dropdown"
      aria-haspopup="true" aria-expanded="false" style="border-radius:8px;">Destination Country</button>

    <div class="dropdown-menu dropdown-primary loginLabels" style="height:200px;overflow-y:auto;">
	     <c:forEach items="${CountryList}" var="entry">
	    		<a id="${entry}" class="dropdown-item" onclick="chDestCountry('${entry}')">${entry}</a>
		</c:forEach>
    </div>
</div>
</div>
<div class="col-sm-3 " >
 <button class="btn btn-default loginLabels checkPairBtn checkPairBtnMbl" style="border-radius:8px;" onclick="checkCurrPair()" type="button">
   <i class="fas fa-chart-line"></i>
    Check Pair&nbsp;&nbsp; <span id="currRoller" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="visibility:hidden;"></span>
</button>
</div>



   	

 
 