<%-- 
    Document   : cad
    Created on : 19/06/2014, 08:19:34 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
	<head>
                <title>OpenCNC</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width">
                <script type="text/javascript" src="<c:url value='/resources/cad/archivotxt.js'/>"></script>
		<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>-->
                <script src="<c:url value='/resources/cad/lib/external/jquery.min.js'/>"></script>
		<!--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>-->
                <script src="<c:url value='/resources/cad/lib/external/jquery-ui.min.js'/>"></script>
                <script src="<c:url value='/resources/cad/lib/external/raphael-min.js'/>"></script>	
                <script type="text/javascript" src="<c:url value='/resources/cad/rp/rph.js'/>"></script>
                <script src="<c:url value='/resources/cad/component.js'/>"></script>
		<!--<script src="resources/js/customShapes.js"></script>-->
                <script src="<c:url value='/resources/cad/inputHandler.js'/>"></script>
                <script src="<c:url value='/resources/cad/logicDisplay.js'/>"></script>
                <script src="<c:url value='/resources/cad/graphicDisplay.js'/>"></script>
                <script src="<c:url value='/resources/cad/world.js'/>"></script>
                <script src="<c:url value='/resources/cad/customShapes.js'/>"></script>
                
                <link href="<c:url value='/resources/cad/styles/main.css" rel="stylesheet'/>">
                
                
        
	</head>
	<body>  
		
                <div class="cad">
                    <div id="titulo">OpenCNC</div>
                    <div id="paper2"></div>
                    <canvas id="CADCanvas"
                                    width="800"
                                    height="600"
                                    onContextMenu="javascript: return false;"
                                    tabindex="1"></canvas>
                    <div id="paper"></div>
                </div>
            <div id="comandos">
                <textarea id="codex" name="codex" rows="45" cols="30" spellcheck="false">
                </textarea>
            </div>
            <div id="opciones">
                <table border="0">
                    <thead>
                        <tr>Opciones de comandos.
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><label for="textNombre"></label>
                            </td>
                            <td> <label for="textTelefono"></label></td>
                        </tr>
                        <tr>
                            <td><input type="text" id="textNombre" value="NaN" /></td>
                            <td><input type="text" id="textTelefono" value="NaN" /></td>
                        </tr>
                        <tr>
                            <td>X</td>
                            <td>Y</td>
                        </tr>
                        <tr>
                            <td>x1<input type="text" id="x1Pos" name="X1" value="0" /></td>
                            <td>y1<input type="text" id="y1Pos" name="Y1" value="0" /></td>
                        </tr>
                        <tr>
                            <td>x2<input type="text" id="x2Pos" name="X2" value="0" /> </td>
                            <td>y2<input type="text" id="y2Pos" name="Y2" value="0" /></td>
                        </tr>
                         <tr>
                            <td>R<input type="text" name="R" value="0" /></td>
                            <td>A<input type="text" name="A" value="0" /></td>
                        </tr>
                        <tr>
                            <td><input id="xml" type="submit" value="XML" onclick="arranqueXML()"/>    
                            </td>
                            <td><input id="txt" type="submit" value="TXT" onclick="arranqueTXT()"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
        </body>
                
                
</html>
