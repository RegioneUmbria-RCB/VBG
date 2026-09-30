<%@page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
		<fmt:message key="label.archivio_istanze" />
	</title>
	
</head>
<body>
	<span class="titoloPagina">
		<init:editLabel key="label.archivio_istanze" role="ROLE_EDITLABEL" />		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../equitaliatracciato/searchIstanze" />
	</jsp:include>
	
	<div id="subcontent">	

		<spring-form:form commandName="equitaliatracciatoCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="equitaliatracciatoCommand" />
		    </jsp:include>

		    <table>
		    	<table width="100%" border="0">
			    <tr>
			    	<td colspan="6" class="titoloSottoSezione">Filtri recuperati da configurazione</td>
			    </tr>
			    <tr>
				    <%-- prima colonna --%>
			    	<td>Tipo movimento per recupero "Data movimento atto"</td>
			    	<td><spring-form:input path="equitaliaTracciatiCfg.codiceMovDataAtto" readonly="true"/> </td>
			    	<%-- seconda colonna --%>
			    	<td>Tipo movimento per recupero "Data notifica atto"</td>
			    	<td><spring-form:input path="equitaliaTracciatiCfg.codiceMovDataNotificaAtto" readonly="true"/> </td>
			    	<%-- terza colonna --%>
			    	<td>Tipo movimento per recupero "Anno maturazione debito"</td>
			    	<td><spring-form:input path="equitaliaTracciatiCfg.codiceMovAnnoDebito" readonly="true"/> </td>
			    </tr>
			    <tr>
				    <%-- prima colonna --%>
			    	<td>Tipo movimento istanza a ruolo </td>
			    	<td><spring-form:input path="equitaliaTracciatiCfg.codiceMovIstanzaARuolo" readonly="true"/> </td>
			    	<%-- seconda colonna --%>
			    	<td>(verranno escluse tutte le istanze con questo movimento)</td>
			    	<td>&nbsp;</td>
			    	<%-- terza colonna --%>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    </tr>
			     <tr>
				    <%-- prima colonna --%>
			    	<td>Codice registro autorizzazione ordinanza </td>
			    	<td><spring-form:input path="equitaliaTracciatiCfg.codiceRegAutOrdinanza" readonly="true"/> </td>
			    	<%-- seconda colonna --%>
			    	<td>Ambito</td>
			    	<td>
			    		<spring-form:select  id="ambito_id" path="tracciatoEquitaliaFilter.valoredyn2CampiFiltroAmbito">
					    	<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
							<spring-form:options items="${listaAmbiti}" itemValue="id"	itemLabel="ambito" />
						</spring-form:select>
			    	</td>
			    	<%-- terza colonna --%>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    </tr>
			    <tr>
				    <%-- prima colonna --%>
			    	<td colspan="4"><b>* Attenzione dall'estrazione saranno escluse tutte le istanza che sono nello stato "Chiuse". </b></td>
			    	<%-- terza colonna --%>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    </tr>
		    	<tr>
			    	<td colspan="6" class="titoloSottoSezione">Filtri utente</td>
			    </tr>
		        <tr>
					<td>Data (Default data presentazione istanza)</td>
					<td>
						<label><fmt:message key="label.dalla_data" /></label>
						<spring-form:input id="dallaData_id" path="tracciatoEquitaliaFilter.istanzaDataDa" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataInizioIst" idInput="dallaData_id" textKey="label.calendar" />	  	
						<spring-form:errors path="tracciatoEquitaliaFilter.istanzaDataDa" cssClass="error" delimiter="," />
					</td>
					<td>
						<label><fmt:message key="label.alla_data" /></label>
						<spring-form:input id="allaData_id" path="tracciatoEquitaliaFilter.istanzaDataA" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFineIst" idInput="allaData_id" textKey="label.calendar" /> 
						<spring-form:errors path="tracciatoEquitaliaFilter.istanzaDataA" cssClass="error" delimiter="," />
					</td>
					<td>
			    		<spring-form:checkbox path="tracciatoEquitaliaFilter.flagFiltraSuDataAut"/>
			    		Cerca tra le date delle ordinanze
					</td>
					<td colspan="3">
						<label><fmt:message key="label.numero_max_record" /></label>
						<spring-form:input id="allaData_id" path="tracciatoEquitaliaFilter.numeroMaxIstanzeInPacchetto" size="10" />
						<spring-form:errors path="tracciatoEquitaliaFilter.numeroMaxIstanzeInPacchetto" cssClass="error" delimiter="," />
					</td>
				</tr>
				<%-- 
				<tr>
					<td>Codici istanze per tracciato (inserire codici separiti a virgole)"</td>
			    	<td colspan="6" ><spring-form:input path="tracciatoEquitaliaFilter.codiciIstanza" /> </td>
				</tr>
		    	--%>
		    </table>
		    
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:validaPacchettoIstanze();"><fmt:message key="button.valida_istanze_tracciato" /></a></li>	
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
	</div>
	<script type="text/javascript">
	function validaPacchettoIstanze(){
		
		var url  = URLDecode('${_urlback}');			
		ajaxHistorySet(url);			
		setTimeout("doSubmit('validaPacchettoIstanze.htm','',document.inviodati)",10);;
	}
	
	function ajaxHistorySet(url){
			
			var jhqr = jQuery.ajax({
				  url: '../history/ajaxSet.htm?ReturnTo='+url.replace('cercasubito','_###_'),
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 				   
					} 
				});
			
	}
	</script>
	
</body>
</html>