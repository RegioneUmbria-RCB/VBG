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
					<li><a href="#tabs-2">PROCESSA FILE MDA</a></li>
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
									<td colspan="3">Servizio per invocato dal backoffice per comunicazioni verso infocamere</a></td>
								</tr>
								<tr>
									<td ><a href="${pageContext.request.contextPath}/services/PddPortEnte?wsdl">PDD_ENTE</a></td>
									<td colspan="3">Servizio invocato da infocamere per invare messaggi al nodo infocamere</a></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				
				<div id="tabs-2">
				<div class="servizio">
					<div class="servizio_title">Strumento che, permette di processare un file MDA xml ed estrarre i nome dei campi dinamici che verranno passati nell xml STC</div>
					<div class="servizio_form">
						<div class="parameter">
							<div style="clear: both;"></div>
						</div>
						
						<form action="processaFileMDA.htm" name="invio" enctype="multipart/form-data" method="post">
						<table>
							<tr>
								<td>File MDA.xml</td>
								<td><input type="file" name="mdaFile" id="mdaFile" /></td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" onclick="invia()" value="processa" /></td>
							</tr>
						</table>
						<script type="text/javascript">
							function invia() {
								
								if (document.getElementById("mdaFile").value == '') {
									alert('E\' obbligatorio specificare un mda (xml)');
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
			<textarea id="result" style="height: 98%; width: 100%;">RISULTATI: ${result}</textarea>
		</div>
	
	</div>
	
</body>

</html>