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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>   
<html lang="en">   
    <head>   
        <title>Cip&AMP;Ciop</title>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script src="autosize/jquery.autosize-min.js"W></script>
    </head>  
    <body>
        <c:out value="${openid_user['userid']}"/>
        
        <div style="display: table; height: 400px; width: 300px; margin: auto;">
            <div style="display: table-cell; vertical-align: middle;">
                <div id="main" class="pagination-right">
                    <c:if test="${empty openid_user}"><a href="auth?openid=https%3A%2F%2Fme.yahoo.com">login</a></c:if>
                    <c:if test="${not empty openid_user}"><a href="logout.bsh">logout</a></c:if>
                    
                    <form class="well form-inline" action="cip.bsh">
                        <div class="controls"><div class="input-append">
                            <input name="to"/><br/>
                            <textarea name="cip" class="span3" placeholder="Type your cip..." style="height: 16px;"></textarea><span class="add-on"><i class="icon-user"></i></span>
                        </div></div>
                        <button type="submit" class="btn btn-primary btn-small">Cip</button>
                        <button type="submit" class="btn btn-success btn-small">Ciop</button> 
                    </form>
                </div>
                <c:forEach items="${cips}" var="cip">
                <div class="well">
                    <c:out value="${cip}"/>
                </div>
                </c:forEach>
            </div>
        </div>
    </body>
    <script lang="JavaScript">
        $(document).ready(function(){
            $('textarea').autosize();  
        });
    </script>
</html> 