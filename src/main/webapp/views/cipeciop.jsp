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
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script src="autosize/jquery.autosize-min.js"></script>
        <meta name="userid" content="<c:out value="${openid_user['userid']}"/>"/>
        <meta charset="UTF-8">
    </head>
    <body>
        <div style="display: table; height: 400px; width: 280px; margin: auto;">
            <div style="display: table-cell; vertical-align: middle;">
                <div id="main" class="pagination-right">
                    <c:if test="${empty openid_user}"><a href="auth?openid=https%3A%2F%2Fme.yahoo.com">login</a></c:if>
                    <c:if test="${not empty openid_user}"><a href="logout.bsh">logout</a></c:if>

                    <form class="well form-inline" action="cip.bsh" method="POST">
                        <div class="controls"><div class="input-append">
                            <%@ include file="/views/friends.jsp" %>
                            <textarea name="cip" class="span3" placeholder="Type your cip..." style="height: 16px;"></textarea>
                        </div></div>
                        <input type="hidden" name="to" value="<c:out value="${friends[0]['id']}"/>"/>
                        <button type="submit" class="btn btn-primary btn-small">Cip</button>
                        <%--<button type="submit" class="btn btn-success btn-small">Ciop</button>--%>
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
    <script src="bootstrap/js/bootstrap.js"></script>
</html> 