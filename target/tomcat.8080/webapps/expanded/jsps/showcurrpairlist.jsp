<%@page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-sm-6">

<!--<c:forEach items="${CurrList}" var="curr">
	    		<p>${curr}</p><br>
</c:forEach>-->
<h3>Result </h3>
<br>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> From : </span>${from}</p>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> To : </span>${to}</p>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> Rate : </span>${rate}</p>
<p class="loginLabels" style="font-size:20px;"><span class="loginLabels" style="font-size:20px;"><i class="fas fa-angle-right"></i> Result : </span>${result}</p>


</div>


