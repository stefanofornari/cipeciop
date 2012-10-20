<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="btn-group">
    <a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i> <span id="tovalue"><c:out value="${friends[0]}"/></span></a>
  <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
  <ul class="dropdown-menu">
    <c:forEach items="${friends}" var="f">
        <li><a href="#" onclick="$(tovalue).text('<c:out value="${f}"/>')"> <c:out value="${f}"/></a></li>
    </c:forEach>
  </ul>
</div>