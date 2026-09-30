<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html style="height: 100%; width: 100%;">
<head>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.12.1.min.js"></script>
<script src="js/jquery.format.js"></script>
<script src="js/jquery.datetimepicker.js"></script>
<script src="js/funzioni.js"></script>
<link href="css/jquery-ui-1.12.1.min.css" rel="stylesheet">
<link href="css/jquery.datetimepicker.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
</head>

<body style="height: 97%; margin: 0; padding: 10px">
	<h2>${webapp_name}</h2>
	<div>
	
		<div style="width: 50%; height: 100%; float: left;" >
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1">INFORMAZIONI</a></li>
					<li><a href="#tabs-2">PROCESSA CSV SANZIONI</a></li>
					<%-- <li><a href="#tabs-3">CAMPI SCHEDA MAPPATI</a></li> --%>
				</ul>
	            <div id="tabs-1">
					<div class="top_no_scroll">
						<div class="servizio_form">
						<div class="servizio_title"></div>		    					
							<table border="1">
							    <tr>
							    	<td colspan="4"><b>Versione STC</b></td>
							    </tr>
								<tr>
									<td><b>webapp</b>: ${webapp_version}</td>	
									<td><b>stc.xsd</b>: ${stc_xsd_version}</td>
									<td><b>nla.xsd</b>: ${nla_xsd_version}</td>
									<td><b>types.xsd</b>: ${types_xsd_version}</td>
								</tr>
								<tr>
							    	<td colspan="4"><b>&nbsp;</b></td>
							    </tr>
							    <tr>
							    	<td colspan="4"><b>WSDL</b></td>
							    </tr>
								<tr>
									<td ><a href="${pageContext.request.contextPath}/services/NlaSoap11?wsdl" title="Visualizza il wsdl">NLA</a></td>
									<td colspan="3">Servizio per import pratiche sanzioni su backoffice</a></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				
				<div id="tabs-2">
				<div class="servizio">
					<div class="servizio_title">Strumento che permette di caricare il csv delle sanzione e importarle come pratiche sul backoffice</div>
					<div class="servizio_form">
						<div class="parameter">
							<div style="clear: both;"></div>
						</div>
						
						<form action="processaSanzioniFile.htm" name="invio" enctype="multipart/form-data" method="post">
						<table>
							<tr>
								<td>File sanzioni (Csv)</td>
								<td><input type="file" name="sanzioniFile" id="sanzioniFile" /></td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" onclick="invia()" value="processa" /></td>
							</tr>
						</table>
						<script type="text/javascript">
							function invia() {
								
								if (document.getElementById("sanzioniFile").value == '') {
									alert('E\' obbligatorio specificare un file');
									return;
								}
								document.invio.submit();
							}
						</script>
						</form>
						
					</div>
					<div style="clear: both;"></div>
				</div>
				<div class="separator"></div>
			</div>
			<%-- 
			<div id="tabs-3">
				<div class="top_no_scroll">
					<div class="servizio_form">
					<div class="servizio_title"></div>		    					
						<table border="1">
						    <tr>
						    	<td colspan="4"><b>Lista campi scheda inseriti nella domanda stc</b></td>
						    </tr>
							<tr>
						    	<td colspan="4"><b>&nbsp;</b></td>
						    </tr>
						    <tr>
						    	<td><b>NOME CAMPO</b></td>
						    	<td colspan="3"><b>DESCRIZIONE</b></td>
						    </tr>
						    <c:forEach var="sanzione" items="${campiScheda}">
							<tr>
							    <td>${sanzione.key}</td>
							    <td>${sanzione.value}</td>
							</tr> 
							</c:forEach>  
							  
							
						</table>
					</div>
				</div>
			</div>
			--%>
			</div>
		
			
				<!--  
			    <div id="tabs-1">
					<div class="top">
						<div class="servizio_form">
						<div class="servizio_title">INFORMAZIONI</div>
							<form action="nome_funzione_controller.htm" name="invio1" enctype="multipart/form-data" method="post">
								<table>	
									<tr>
										<td>Campo testo 1*</td>
										<td><input  name="idComuneAlias" id="idComuneAlias" /> Es. E256</td>
									</tr>
									<tr>
										<td>campo testo 2*</td>
										<td><input  name="codiceSoftware" id="codiceSoftware" /> Es. CO </td>
									</tr>
									<tr>
										<td>Campo slezione 1</td>
										<td>
											<select id="operazione" name="operazione">
												<option value="" >Seleziona...</option>
												<option value="0" >Funzione 1</option>
												<option value="1" >Funzione 2</option>
												<option value="2" >Funzione 3</option>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2"><input type="button" onclick="go()" value="attiva funzione" /></td>
									</tr>
								</table>
								<script type="text/javascript">
									function go() {
										
										var error=""
										
										if (document.getElementById("idComuneAlias").value == '') {
											error+='E\' obbligatorio specificare l\'id comune alias \n';
											//alert('E\' obbligatorio specificare l\'id comune alias');
											//return;
										}
										if (document.getElementById("codiceSoftware").value == '') {
											error+='E\' obbligatorio specificare il codice software \n';
											//alert('E\' obbligatorio specificare il codice software');
											//return;
										}
										if (document.getElementById("operazione").value == '') {
											error+='E\' obbligatorio specificare il tipo operazione \n';
											//alert('E\' obbligatorio specificare il codice software');
											//return;
										}
										if(error !='')
										{
											alert(error);
											return;
										}
										document.invio1.submit();
									}
									
									
									
								</script>
							</form>
						</div>
					</div>
				</div>  
				-->
				    
			</div>
		</div>
		<div style="float: left; height: 100%; width: 50%; overflow-y: scroll;">
		     <table>
		     <c:forEach items="${risultato}" var="r">
		      	<c:if test = "${fn:contains(r, 'CREATA')}">
		     	<tr><td style="color: #408E2F;">${r}</td></tr>
		     	</c:if>
		     	<c:if test = "${fn:contains(r, 'SCARTATA')}">
		     	<tr><td  style="color: #801916;">${r}</td></tr>
		     	</c:if>
		     </c:forEach>
		     </table>
			
		</div>
	
	</div>
	
</body>

</html>