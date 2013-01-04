<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="btn-group">
  <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-user icon-white"></i> <span id="tovalue"><c:out value="${friends[0]['name']}"/></span></a>
  <ul class="dropdown-menu">
    <c:forEach items="${friends}" var="f">
        <li><a href="#" onclick="$(tovalue).text('<c:out value="${f['name']}"/>'); $('input[name=to]').val('<c:out value="${f['id']}"/>');"> <c:out value="${f['name']}"/></a></li>
    </c:forEach>
  </ul>
</div>