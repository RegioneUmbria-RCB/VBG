<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.inventarioprocendo.title" /></title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="label.inventarioprocendo.title" />
</span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../inventarioprocendo/view" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent"><spring-form:form commandName="inventarioprocendo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="inventarioprocendo" />
	</jsp:include>
	
    	<div class="parametriDiv">
    	<div class="etichetta">
				<div><fmt:message key="label.endoprocedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocendo.inventarioprocEndoT.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
	
	<table>
	
					<c:set var="isReadOnly" scope="page" value="false"></c:set>
					<c:if test="${ _VIEW_  eq true }">
						<c:set var="isReadOnly" scope="page" value="true"></c:set>
					</c:if>
	
				<c:choose>
					<c:when test="${_COMUNIASSOCIATI_ eq true }">
						<tr id="elementIdBeforeCombo">
							<td width="15%"></td>
							<td></td>
						</tr>						
						<jsp:include page="../includes/comboComuni.jsp">
							<jsp:param name="mostraTutti" value="true" />
							<jsp:param name="readOnly" value="${ isReadOnly }" />
							<jsp:param name="commandPropertyPath" value="comune" />
							<jsp:param name="colspan" value="2" />
							<jsp:param name="comune" value="${inventarioprocendo.comune.codicecomune}" />
							<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
						</jsp:include>
					</c:when>
					<c:otherwise>
						<spring-form:hidden path="comune.codicecomune" />
					</c:otherwise>	
				</c:choose>
	
	
			<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
					</td>
					<td>
					
						<script type="text/javascript">
							function filterfamiglieendo(element, entry) { 
								return entry + "&cercaSoftwareTT="+$('id_flag_software_tt').checked;
							}
						</script>
						<div id="id1_famigliaendo" style="display:none;">
								<input  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" type="text" 
									id="famigliaendo_id1" 
									name="famigliaendo_descrizione" cssClass="searchbox"
									size="67"
									onchange="checkValue(this,'famigliaendo_hidden')"
									onkeydown="return searchAll(this,event)" />
								<init:autocompleter 
									callBack="filterfamiglieendo" 
									methodAjax='findTipifamiglieendoAndIdcomune.htm?isIdcomunebase=1'
									idHidden="famigliaendo_hidden" 
									idInput="famigliaendo_id1" 
									inputTitleKey="label.ricerca_tipo_famiglia_endo" 
									/>
								
							</div>
							<div id="id2_famigliaendo" style="display:inline;">
								<input class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" type="text" 
									id="famigliaendo_id2" 
									name="famigliaendo_descrizione" cssClass="searchbox"
									size="67"
									onchange="checkValue(this,'famigliaendo_hidden')"
									onkeydown="return searchAll(this,event)" />
								<init:autocompleter 
									callBack="filterfamiglieendo" 
									methodAjax='findTipifamiglieendoAndIdcomune.htm?isIdcomunebase='  
									idHidden="famigliaendo_hidden"  
									idInput="famigliaendo_id2" 
									inputTitleKey="label.ricerca_tipo_famiglia_endo" 
									/>
									
							</div>
							
							<input type="hidden" id="famigliaendo_hidden" name="famigliaendo_codice" />
							
							
							<input type="checkbox" id="id_flag_famigliaendo" onclick="switchAutocompleterfamigliaendo();" tabIndex="-1" />
							
							<input type="hidden" id="famigliaendo_idcomunebase_hidden">
							<init:help idHelp="help_help.search_famiglie_e_categorie_endo_archivi_base" textKey="help.ricerca_per_IDCOMUNEBASE"/>


							<input type="checkbox" id="id_flag_software_tt" tabIndex="-1" checked="checked"/>
							<init:help idHelp="help_search_software_base" textKey="help.ricerca_per_software_TT"/>


							
							<%-- END SEZIONE RICERCA --%>
							
							<script type="text/javascript">
								function switchAutocompleterfamigliaendo(){
									if($('id_flag_famigliaendo').checked){
									    $('id1_famigliaendo').style.display="inline";
									    $('id2_famigliaendo').style.display="none";
									    $('famigliaendo_id2').value='';
									    $('famigliaendo_hidden').value='';
									    $('famigliaendo_idcomunebase_hidden').value='1';
									    
									}else{
										$('id1_famigliaendo').style.display="none";
										$('id2_famigliaendo').style.display="inline";
										$('famigliaendo_id1').value='';
									    $('famigliaendo_hidden').value='';
									    $('famigliaendo_idcomunebase_hidden').value='';
									}
								}
								
							</script>					
						
					
					</td>					
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
					</td>
					<td>
						<script type="text/javascript">
							function filtertipiendo(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+"&isIdcomunebase="+document.getElementById("famigliaendo_idcomunebase_hidden").value+"&cercaSoftwareTT="+$('id_flag_software_tt').checked;
							}
						</script>
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendoAndIdcomune.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input name="inventarioprocEndoD.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.inventarioprocendo_d" />
					</td>
					<td>
						<script type="text/javascript">
							function inventarioCallBack(inputField,listItem){
								var a = listItem.id;
								document.getElementById('inventarioprocedimenti_id').value = inputField.value;
								var vals = a.split('#');
								document.getElementById('inventarioprocedimenti_hidden').value = vals[0];
								document.getElementById('inventarioprocedimenti_idcomune_hidden').value = vals[1];
								$('inventarioprocedimenti_id_choices').fade();	
							}
							function filterinventario(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&isIdcomunebase="+document.getElementById("famigliaendo_idcomunebase_hidden").value+"&tipoEndo=STP1&cercaSoftwareTT="+$('id_flag_software_tt').checked;
							}
						</script>
						<spring-form:input id="inventarioprocedimenti_id" path="inventarioprocEndoD.procedimento" cssClass="searchbox" onchange="checkValue(this,'inventarioprocedimenti_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findInventarioprocByFamigliaAndCategoriaEndoAndIdComune.htm" 
								idHidden="inventarioprocedimento_hidden" 
								idInput="inventarioprocedimenti_id" 
								callBack="filterinventario" 
								afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
						<spring-form:errors path="inventarioprocEndoD" cssClass="error"/> 
						<spring-form:hidden id="inventarioprocedimenti_hidden" path="inventarioprocEndoD.id.codice"  />
						<spring-form:hidden id="inventarioprocedimenti_idcomune_hidden" path="inventarioprocEndoD.id.idcomune"  />
					</td>
				</tr>
	
	
	
	
	

			<tr>
				<td>
					<fmt:message key="label.pubblica" />
				</td>
				<td>
			        <spring-form:checkbox path="flagPubblica"/>	
					<spring-form:errors path="flagPubblica" cssClass="error"/> 
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.necessario" />
				</td>
				<td>
			        <spring-form:checkbox path="flagNecessario"/>	
					<spring-form:errors path="flagNecessario" cssClass="error"/> 
				</td>
			</tr>
	</table>
	<script type='text/javascript'>
	
</script>
</spring-form:form></div>
<div id="functions">
<ul>
	<c:if test="${inventarioprocendo.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${inventarioprocendo.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?codiceT=${inventarioprocendo.inventarioprocEndoT.id.codice }','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>