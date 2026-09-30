<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_mercatid2cassegnaz.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_mercatid2cassegnaz.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid2cassegnaz/list" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="mercatid2cassegnaz" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatid2cassegnaz" />
		    </jsp:include>
	<table width="100%">
	<tr class="titoloSezione">
		<td colspan="2"><fmt:message key="label.campi_dinamici_criteri_assegnazione_mercato" /></td>
	</tr>
	<tr >
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">
		<form name="mercatid2cassegnazForm" action="list.htm">
			<jmesa:springTableFacade
				id="mercatid2cassegnaz_id" 
				items="${mercatid2cassegnazList}" 
				var="mercatid2cassegnaz_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%"/>
						<jmesa:htmlColumn property="dyn2Campi.nomecampo" titleKey="label.nome_campo" />
						<jmesa:htmlColumn property="" titleKey="label.elimina" sortable="false" filterable="false" width="5%">
							<a class="eliminaRiga" href="javascript:doHref('deleteDyn2Campi.htm?codice=${mercatid2cassegnaz_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.azioni" />&nbsp;${endo_inc_var.inventarioprocedimentoincompatibile.procedimento}">
								<label><fmt:message key="label.elimina" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			   <input type="hidden" value="${mercato.id.codice}" name="codiceMercato"/>
			   
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceMercato=${mercato.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_mercatid2cassegnaz.title"/>';
		</script>
		</td>
	</tr>
	<tr>
		<td width="2%">
			<fmt:message key="label.modello" />
		</td>
		<td>
		<jsp:include page="../includes/autocompletergenericoTT.jsp" >
			<jsp:param name="idElemento" value="dyn2ModellitMercato" />		
			<jsp:param name="propertyPath" value="dyn2Modellit" />				
			<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
			<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
			<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
			<jsp:param name="id_help" value="help_modello" />	
			<jsp:param name="help" value="help.modelli_archivi_base" />
		</jsp:include>
		</td>
	</tr>
	<tr>
		<td width="2%">
			<fmt:message key="label.campo" />
		</td>
		<td>
		<script type="text/javascript">
				function filterModello(element, entry) {
					return entry + "&codiceModello=" + document.getElementById("dyn2ModellitMercato_hidden").value;								
				}
				function saveCampoDinamico(inputField,listItem){
					var a = listItem.id;
					document.getElementById('dyn2CampiMercato_hidden').value = a;
					doSubmit('insertDyn2CampiAssegnazioni.htm?codiceCampo='+a,'',document.inviodati);				
				}
		</script>
		<jsp:include page="../includes/autocompletergenericoTT.jsp" >
			<jsp:param name="idElemento" value="dyn2CampiMercato" />		
			<jsp:param name="propertyPath" value="dyn2Campi" />				
			<jsp:param name="pathPropertyDescription" value="dyn2Campi.nomecampo" />
			<jsp:param name="pathPropertyCode" value="dyn2Campi.id.codice" />
			<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
			<jsp:param name="ajaxCallBack" value="filterModello" />
			<jsp:param name="afterUpdateElement" value="saveCampoDinamico" />
			<jsp:param name="id_help" value="help_campo" />	
		</jsp:include>
		<spring-form:hidden path="mercati.id.codice"/>
		<spring-form:hidden path="dyn2Campi.id.codice"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"><fmt:message key="help.lista_mercatid2cassegnaz" /></td>
	</tr>
	<%-- 
	<tr>
		<td>
			<div id="functions">
				<ul>
					<li><a href="javascript:doHref('create.htm?codiceMercato=${mercato.id.codice}','');"><fmt:message key="button.new" /></a></li>
				</ul>
			</div>
		</td>
	</tr>
	--%>	
	<%-- Configurazione del campo dinamico per la preferenza dell'uso del mercato  --%>
	</table>
	
	
	<table width="100%" style="padding-top: 30px;">
	<tr  class="titoloSezione">
		<td colspan="2"><fmt:message key="label.campi_dinamici_criteri_assegnazione_mercato_uso" /></td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td width="2%">
			<fmt:message key="label.modello" />
		</td>
		<td>
		<jsp:include page="../includes/autocompletergenericoTT.jsp" >
			<jsp:param name="idElemento" value="dyn2Modellit" />		
			<jsp:param name="propertyPath" value="dyn2Modellit" />				
			<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
			<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
			<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
			<jsp:param name="id_help" value="help_modello_" />	
			<jsp:param name="help" value="help.modelli_archivi_base" />
		</jsp:include>
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="label.campo" />
		</td>
		<td>
		<script type="text/javascript">
				function filter(element, entry) {
					return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
				}
				function saveCampoDinamicoMercato(inputField,listItem){
					var a = listItem.id;
					document.getElementById('dyn2Campi_hidden').value = a;
					doHref('../mercati/updateCampoDinamicoMercato.htm?codiceMercato=${mercato.id.codice}&codiceCampoDinamico='+a,'');
				}
		</script>
		<jsp:include page="../includes/autocompletergenericoTT.jsp" >
			<jsp:param name="idElemento" value="dyn2Campi" />		
			<jsp:param name="propertyPath" value="dyn2Campi" />				
			<jsp:param name="pathPropertyDescription" value="mercati.dyn2Campi.nomecampo" />
			<jsp:param name="pathPropertyCode" value="mercati.dyn2Campi.id.codice" />
			<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
			<jsp:param name="ajaxCallBack" value="filter" />
			<jsp:param name="afterUpdateElement" value="saveCampoDinamicoMercato" />
			<jsp:param name="id_help" value="help_campo_" />	
		</jsp:include>
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="label.campo_preferenza_posteggio" />
		</td>
		<td>
		<script type="text/javascript">
				function filter(element, entry) {
					return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
				}
				function saveCampoDinamicoMercatoPreferenzaposteggio(inputField,listItem){
					var a = listItem.id;
					document.getElementById('dyn2Campi_hidden').value = a;
					doHref('../mercati/updateCampoDinamicoPreferenzaPosteggioMercato.htm?codiceMercato=${mercato.id.codice}&codiceCampoDinamico='+a,'');
				}
		</script>
		<jsp:include page="../includes/autocompletergenericoTT.jsp" >
			<jsp:param name="idElemento" value="dyn2CampiPrefPosteggio" />		
			<jsp:param name="propertyPath" value="dyn2CampiPrefPosteggio" />				
			<jsp:param name="pathPropertyDescription" value="mercati.dyn2CampiPrefPosteggio.nomecampo" />
			<jsp:param name="pathPropertyCode" value="mercati.dyn2CampiPrefPosteggio.id.codice" />
			<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
			<jsp:param name="ajaxCallBack" value="filter" />
			<jsp:param name="afterUpdateElement" value="saveCampoDinamicoMercatoPreferenzaposteggio" />
			<jsp:param name="id_help" value="help_campo__" />	
		</jsp:include>
		</td>
	</tr>
</table>
</spring-form:form>	
</div>
<br />
	
	<div id="functions">
		<ul>
		    <c:if test="${mercatid2cassegnaz.mercati.dyn2Campi.id.codice !=null }">
		    	<li><a href="javascript:doHref('../mercati/deletePreferenzaUsoDyn2Dati.htm?codice=${mercato.id.codice}','')"><fmt:message key="button.elimina_preferenza" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>




