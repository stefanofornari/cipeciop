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
<!DOCTYPE html>   
<html lang="en">   
    <head>   
        <title>Cip&AMP;Ciop</title>     
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>  
    <body>  

        <div style="display: table; height: 400px; width: 300px; margin: auto;">
            <div style="display: table-cell; vertical-align: middle;">
                <div id="main" style="">
                    <form class="well form-inline" action="cip.bsh">
                        <div class="controls"><div class="input-append">
                        <input type="text" class="span3" placeholder="Type your cip..."><span class="add-on"><i class="icon-user"></i></span>
                        </div></div>
                        <button type="submit" class="btn btn-primary btn-small">Cip</button>
                        <button type="submit" class="btn btn-success btn-small">Ciop</button> 
                    </form>
                </div>
            </div>
        </div>
    </body>  
</html> 
