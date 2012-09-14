<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>OpenID FORM Redirection</title></head>
<body onload="document.forms['openid-form-redirection'].submit();">
   <form name="openid-form-redirection" action="${message.OPEndpoint}" method="post" accept-charset="utf-8">
       <c:forEach var="parameter" items="${message.parameterMap}">
       <input type="hidden" name="${parameter.key}" value="${parameter.value}"/>
       </c:forEach>
   </form>
   Authenticating ... 
</body>
</html>