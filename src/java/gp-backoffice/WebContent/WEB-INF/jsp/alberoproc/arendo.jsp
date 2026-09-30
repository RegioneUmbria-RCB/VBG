<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="alberoproc.label.dettaglio_arendo.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoproc.label.dettaglio_arendo.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<spring-form:form commandName="alberoprocArendo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocArendo" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.famiglia" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="tipifamiglieendo" />		
						<jsp:param name="propertyPath" value="tipifamiglieendo" />				
						<jsp:param name="pathPropertyDescription" value="tipifamiglieendo.tipo" />
						<jsp:param name="pathPropertyCode" value="tipifamiglieendo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findTipifamiglieendo.htm?codicesoftware=" />	
						<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
						<jsp:param name="id_help" value="help_famiglia" />
						<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
					</jsp:include>
					</td>
					<%--
					<td>
						<input id="famiglia_id"  class="searchbox" onchange="checkValue(this,'famiglia_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findTipifamiglieendoSWeTT.htm" idHidden="famiglia_hidden" idInput="famiglia_id" inputTitleKey="label.ricerca_tipo_famiglia_endo"></init:autocompleter>
						<input name="tipifamiglieendo.id.codice" type="hidden" id="famiglia_hidden"  />
						<spring-form:errors path="tipifamiglieendo" cssClass="error"/>
					</td>
					 --%>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.categoria" />
					</td>
					<td>
						<script type="text/javascript">
							function filtertipiendo(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("tipifamiglieendo_hidden").value+"&codicesoftware="+document.getElementById("tipifamiglieendo_software_hidden").value;
							}
						</script>
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendo.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input name="tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
					</td>
				</tr>
				
		
		
			</table>
			<script type='text/javascript'>
				//$('legge_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertArendo.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=' + ${alberoproc.id.codice} + '#arendo_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>