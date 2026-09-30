<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pannello controllo Consolle></title>
</head>
<body>
<span class="titoloPagina">Pannello controllo Consolle</span>
	 	<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../pannelloconsole/view" />
		</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<br class="clear" />
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="cart" />
	</jsp:include>
	<c:if test="${param.disableSchemaValidation eq true}">
		<br class="clear"/>
		<b>
		<span class="error_header">
			<fmt:message key="stp.label.messaggio_errore_validazione_schema" />
		</span>	
		</b>
		<div id="functions">
			<ul>
			<li>
				<a href="javascript:doHref('eseguiOperazione.htm?operazione=${param.operazione}&idEgov=${param.idEgov }&servizio=${param.servizio }&disableSchemaValidation=${param.disableSchemaValidation }&ts_'+new Date().getTime(),'')"><fmt:message key="button.ripeti" /></a>
			</li>
			</ul>
		</div>
		<br class="clear"/>
		<br class="clear"/>
	</c:if>	
	
	<fieldset>
	
<div class="jmesa">
<table border="0" cellpadding="2" cellspacing="0" class="table" width="100%">
	<tbody class="tbody">
	
		
		<tr class="titoloSezione">
			<td colspan="2">Funzioni</td>		
		</tr>
								
				<tr class="odd">
					<td></td>		
					
					<td>
					
					<div id="functions">
						<ul>
								<li>
									<a href="javascript:void(0)" onclick="preElabora();"><fmt:message key="stp.label.elabora" /></a>
								</li>
					
						</ul>
					</div>
						<c:if test="${not empty messaggioErroreInvioDizionario}">
							<span class="error_header">${messaggioErroreInvioDizionario}</span>
						</c:if>
					</td>										
				</tr>	
				
<%--
		<tr>
			<td width="15%"><fmt:message key="label.ricarica_configurazioni" /></td>		
			<td>
				<span id="functions">
					<ul>				
						<li><a href="javascript:ricaricaConfigurazioni();" id="ricaricaConfigurazioniId"><fmt:message key="label.aggiorna" /></a></li>
					</ul>
				</span>
			</td>
		</tr>	
		
 --%>						
	</tbody>
</table>
</div>
</fieldset>


    <%--DIV che compare quando viene premuto il punsante Importa interventi --%>
	  
	
    <%--DIV che compare quando viene premuto il punsante Elabora 
    	vengono mostrate delle informazioni preliminari da configurare prima di eseguire 
    	l'elaborazione del dizionario --%>
	<div dojoType="dijit.Dialog" id="dialogDivPreElaborazione" title="<fmt:message key="stp.label.configurazioni_preliminari" />: " style="width: 90%" >
		<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
			<div id="dialogPreElabora" style="overflow: auto; width: 100%;">&nbsp;</div>
		</div>
	</div>
	




<script type="text/javascript">

	var dialogWorking = null; 
	jQuery(document).ready(function(){
		dialogWorking = new dijit.Dialog({
	       title: "Operazione in corso..." ,
	       style: "overflow:auto; width: 250px;height: 70px;",
	       content: "<img src='../images/spinner.gif'/>"
	    });				
	}); 



	function preElabora() {
		doHref('preElabora.htm','');
	}

	
</script>


<br class="clear"/>
<div id="functions">
<%--	
	<li><a href="javascript:historySet('${_urlback}','..%2Fnaturaendo/list.htm','')"><fmt:message key="button.naturaendo" /></a></li>
 --%>
	<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</div>

</body>
</html>