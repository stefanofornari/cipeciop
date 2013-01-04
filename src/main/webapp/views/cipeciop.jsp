<%--
/*
* Cip&Ciop
* Copyright (C) 2012 Stefano Fornari
*
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU Affero General Public License version 3 as published by
* the Free Software Foundation with the addition of the following permission
* added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
* WORK IN WHICH THE COPYRIGHT IS OWNED BY Stefano Fornari, Stefano Fornari
* DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program; if not, see http://www.gnu.org/licenses or write to
* the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
* MA 02110-1301 USA.
*/
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cip&AMP;Ciop</title>
        <link href="images/favicon.gif" rel="icon" type="image/gif">
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script src="autosize/jquery.autosize-min.js"></script>
        <meta name="userid" content="<c:out value="${openid_user['userid']}"/>"/>
        <meta charset="UTF-8">
    </head>
    <body style="font-size: 75%">
        <div style="max-width: 400px; margin: 0 auto;">
            <div id="main">
                <div class="pagination-right">
                <c:if test="${empty openid_user}"><a href="auth?openid=https%3A%2F%2Fme.yahoo.com">login</a></c:if>
                <c:if test="${not empty openid_user}"><a href="logout.bsh">logout</a></c:if>
                </div>
                <form class="well well-small form-inline" action="cip.bsh" method="POST">
                    <div class="container-fluid pagination-right">
                    <%@ include file="/views/friends.jsp" %> <button type="submit" class="btn btn-primary">Cip</button>
                    </div>
                    <div class="row-fluid pagination-centered" style="padding-top: 3px;">
                        <textarea name="cip" class="span12" placeholder="Type your cip..." maxlength="950"></textarea>
                    </div>
                    <input type="hidden" name="to" value="<c:out value="${friends[0]['id']}"/>"/>
                </form>
            </div>
            <div id="cipsciops">
                <c:forEach items="${cips}" var="cip">
                    <div id="cip<c:out value='${cip.id}'/>" class="well">
                        <button onclick="deleteCipCiop(<c:out value='${cip.id}'/>, '<c:out value='${cip.objectid.entityName}'/>');" class="close">&times;</button>
                        <i class="icon-user"></i> <strong><c:out value="${cip.from}"/></strong>
                        <div id="cipciop"><c:out value="${cip.text}" escapeXml="false"/></div>
                        <small class="muted" style="font-size:75%"><c:out value="${cip.created}"/></small>
                        <c:if test="${cip.mobile}"><img class="pull-right" src="images/phone-8x16.png" style="margin-left: 3px"/></c:if>
                        <c:if test="${not (cip.seen eq null)}"><i class="icon-check pull-right"></i></c:if>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>

    <script lang="JavaScript">
        $(document).ready(function(){
          $('textarea').autosize();
        });

        function deleteCipCiop(id, type) {
          $.getJSON(
          '/cipeciop/ajax/cip.bsh?action=delete&id='+id+'&type='+type,
          {},
          function(data) {
              $('#cip'+id).hide('slow')
          });
        }  
    </script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</html> 
