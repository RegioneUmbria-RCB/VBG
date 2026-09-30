<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.endoprocedimenti.loc.interventi" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.endoprocedimenti.loc.interventi" />
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
		<spring-form:form commandName="alberoprocEndoLoc" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocEndoLoc" />
		    </jsp:include>
		     <%
		  	  String inventario="";
		      String inventario_auto="display:none;";
		    %>
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tipologia_famiglia" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="famigliaendo" />		
						<jsp:param name="propertyPath" value="inventarioprocedimenti.tipoendo.tipifamiglieendo" />				
						<jsp:param name="pathPropertyDescription" value="inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" />
						<jsp:param name="pathPropertyCode" value="inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findTipifamiglieendo.htm" />	
						<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
						<jsp:param name="id_help" value="help_famiglia" />
						<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
					</jsp:include>
					</td>					
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tipi_endo" />
					</td>
					<td>
						<script type="text/javascript">
							function filtertipiendo(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value;
							}
						</script>
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendo.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input name="inventarioprocedimenti.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
					</td>
					<td>
						<script type="text/javascript">
							function inventarioCallBack(inputField,listItem){
								var a = listItem.id;
								
								var vals = a.split('#');
								
								document.getElementById('inventarioprocedimenti_id').value = inputField.value;
								document.getElementById('inventarioprocedimenti_hidden').value = vals[0];
								document.getElementById('inventarioprocedimenti_idcomune_hidden').value = vals[1];
								$('inventarioprocedimenti_id_choices').fade();	
							}
							function filterinventario(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&tipoEndo=STP2";
							}
						</script>
						<spring-form:input id="inventarioprocedimenti_id" path="inventarioprocedimenti.procedimento" cssClass="searchbox" onchange="checkValue(this,'inventarioprocedimenti_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findInventarioprocByFamigliaAndCategoriaEndoAndIdComune.htm" idHidden="inventarioprocedimento_hidden" idInput="inventarioprocedimenti_id" callBack="filterinventario" afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
						<spring-form:errors path="inventarioprocedimenti" cssClass="error"/> 
						<spring-form:hidden id="inventarioprocedimenti_hidden" path="inventarioprocedimenti.id.codice"  />
						<spring-form:hidden id="inventarioprocedimenti_idcomune_hidden" path="inventarioprocedimenti.id.idcomune"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />		
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>	

				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" />
					</td>
					<td>
						<spring-form:checkbox id="flagRichiesto_id" path="flagNecessario" />
						<init:help idHelp="helpflagNecessario" textKey="alberoproc.help.alberoprocEndo_flagRichiesto"/>
						<spring-form:errors path="flagNecessario" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" />
					</td>
					<td>
						<spring-form:checkbox id="flagPubblica_id" path="flagPubblica" />
						<init:help idHelp="helpflagPubblica" textKey="alberoproc.help.alberoprocEndo_flagPubblica"/>
						<spring-form:errors path="flagPubblica" cssClass="error"/>
					</td>
				</tr>

			</table>
			<script type='text/javascript'>
				$('inventarioprocedimento_id').focus();
				 	
		 	</script>

			</script>	
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertIntervento.htm?alberoproc.id.codice='+${alberoproc.id.codice},'',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice='+${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>