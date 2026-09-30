<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.dyn2modellid.detail.title" />
	</title>
</head>
<body>

     <c:set value="<%=WebConstants.CAMPO_DINAMICO %>" scope="page" var="campodinamico"></c:set>
     <c:set value="<%=WebConstants.CAMPO_TESTO %>" scope="page" var="campotesto"></c:set>
	<%
		String displayCampiDinamici="";
		String displayCampiTesto="";
		if(request.getAttribute("tipocampo").equals(WebConstants.CAMPO_DINAMICO))
		{
		     displayCampiDinamici="";
			 displayCampiTesto="display:none";
		}else
		{
		     displayCampiDinamici="display:none";
			 displayCampiTesto="";
		}
		
		
		
		
    %>
	<span class="titoloPagina">
			<fmt:message key="label.dyn2modellid.detail.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellid/popupview" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="dyn2modellid" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="dyn2modellid" />
		    </jsp:include>
			<table width="100%" border="0">
				<input type="hidden" name="popupCaller" value="${ popup }"></input>
				<spring-form:hidden path="dyn2Modellit.id.codice" />
				<spring-form:hidden path="id.codice" />
				<tr>
					<td>
						<fmt:message key="label.riga" />
					</td>
					<td colspan="2">
						<spring-form:input id="posverticale_id" path="posverticale" size="4" onchange="checkNumberInt(this)" />
						<init:help idHelp="help_posverticale" textKey="dyn2modellid.help.posverticale"/>
						<spring-form:errors path="posverticale" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.colonna" />
					</td>
					<td colspan="2">
						<spring-form:input id="posorizzontale_id" path="posorizzontale" size="4" onchange="checkNumberInt(this)" />
						<init:help idHelp="help_posorizzontale" textKey="dyn2modellid.help.posorizzontale"/>
						<spring-form:errors path="posorizzontale" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipo_riga" /></td>
					<c:if test="${dyn2modellid.id.codice==null}">
					<td colspan="2">
						<select id="tipocampoTransient_id" name="tipocampoTransient" onchange="viewCampiDinamiciOrTesto()"> 
							<c:if test="${dyn2modellid.tipocampoTransient==campodinamico}">
								<option value="<%=WebConstants.CAMPO_DINAMICO%>" selected="selected"><fmt:message key="label.campo_dinamico" /></option>
								<option value="<%=WebConstants.CAMPO_TESTO%>"><fmt:message key="label.campo_testo" /></option>
							</c:if>
							<c:if test="${dyn2modellid.tipocampoTransient==campotesto}">
								<option value="<%=WebConstants.CAMPO_DINAMICO%>" ><fmt:message key="label.campo_dinamico" /></option>
								<option value="<%=WebConstants.CAMPO_TESTO%>" selected="selected"><fmt:message key="label.campo_testo" /></option>
							</c:if>
						</select>
						<spring-form:errors path="tipocampoTransient" cssClass="error"/>
					</td>
					</c:if>
					<c:if test="${dyn2modellid.id.codice!=null}">
						<td colspan="2"><spring-form:input id="_tipocampoTransient_id" path="tipocampoTransient" size="20" /></td>
					</c:if>
				</tr>
				
				<tr id="tr_campidimanici_id" style="<%=displayCampiDinamici%>">
				    <td width="10%">
						<fmt:message key="label.campo_dinamico" />
					</td>
					<td width="47%" colspan="2">
					 	<jsp:include page="../includes/dyn2CampiSearch.jsp" >
							<jsp:param name="idElemento" value="dyn2CampiInputId" />
							<jsp:param name="pathdyn2campi" value="dyn2Campi" />
							<jsp:param name="dyn2campiInputSize" value="67"/>		
							<jsp:param name="hideTT" value="true" />					
						</jsp:include>
						&nbsp;
						<a class="dettaglioColumn" style="float: inherit;" href="javascript:dettaglioCampoDinamico();"	title="<fmt:message key="label.edit.record" />">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
						<a class="addColumn" style="float: inherit;" href="javascript:nuovoCampoDinamico();"	title="<fmt:message key="label.nuovo_idsemantico" />">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</td>
					
				</tr>
				<tr id="tr_tipo_testo_id" style="<%=displayCampiTesto%>">
				    <td>
						<fmt:message key="label.tipo_testo" />
					</td>
					<td colspan="2">
						<spring-form:select id="tipotesto_id" path="dyn2Modellidtesti.dyn2Basetipitesto.id"> 
							<spring-form:options items="${basetipitestos}" itemLabel="tipotesto" itemValue="id" />
						</spring-form:select>
					</td>
				</tr>		
				<tr id="tr_testo_id" style="<%=displayCampiTesto%>">
				    <td>
						<fmt:message key="label.testo" />
					</td>
					<td colspan="2">
						<spring-form:textarea id="testo_id" path="dyn2Modellidtesti.testo" cols="60" rows="10" />
						<spring-form:errors path="dyn2Modellidtesti.testo" cssClass="error"/>
					</td>
				</tr>
				<tr>
				    <td>
						<fmt:message key="label.obbligatorio" />
					</td>
					<td>
						<spring-form:checkbox id="obbligatorio_id" path="flgObbligatorio" />
					</td>
				</tr>
				<tr>
				    <td>
						<fmt:message key="label.riga_multipla" />
					</td>
					<td>
						<spring-form:hidden path="flgMultiplo" />
						<b>
						<c:choose>
							<c:when test="${dyn2modellid.flgMultiplo eq true}">
								Si
							</c:when>
							<c:otherwise>No</c:otherwise>
						</c:choose>
						</b>
					</td>
				</tr>					
				<tr>
				    <td>
						<fmt:message key="label.visualizza_se" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="dyn2RegoleAttivo" />		
								<jsp:param name="propertyPath" value="dyn2RegoleAttivo" />				
								<jsp:param name="pathPropertyDescription" value="dyn2RegoleAttivo.descrizione" />
								<jsp:param name="pathPropertyCode" value="dyn2RegoleAttivo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2RegoleAttivazione.htm?codicesoftware=" />	
								<jsp:param name="titleKey" value="label.ricerca_regole_attivazione" />
								<jsp:param name="id_help" value="help.causali_oneri_archivi_base"/>					
						</jsp:include>
						&nbsp;
						<a class="dettaglioColumn" style="float: inherit;" href="javascript:dettaglioRegolaAttivazione();"	title="<fmt:message key="label.edit.record" />">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
						<a class="addColumn"  style="float: inherit;" href="javascript:nuovaRegolaAttivazione();"	title="<fmt:message key="label.nuova_regola" />">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</td>
				</tr>							
			</table>
			<script type='text/javascript'>
			if($('posverticale_id')){
				$('posverticale_id').focus();
			}	
				function viewCampiDinamiciOrTesto()
				{
					if(document.getElementById("tipocampoTransient_id").value=='CAMPO DINAMICO')
			    	{
			    		$('tr_campidimanici_id').style.display = '';
			    		$('tr_testo_id').style.display = 'none';
			    		$('tr_tipo_testo_id').style.display = 'none';
			    	}else
			    	{
			    		$('tr_campidimanici_id').style.display = 'none';
			    		$('tr_testo_id').style.display = '';
			    		$('tr_tipo_testo_id').style.display = '';
			    		
			    	}
				}
				
				function nuovoCampoDinamico(){
					var callerId = "${dyn2modellid.id.codice}";
					if(callerId.length == 0){
						callerId = "0";
					}
					doHref('../dyn2campi/popupcreate.htm?callerModT=${dyn2modellid.dyn2Modellit.id.codice}&callerId=' + callerId,'');
			    }
			
				function dettaglioCampoDinamico(){
					var caller = 'dyn2CampiInputId';
					var codiceDyn=document.getElementById("dyn2CampiInputId_hidden").value;
					if(codiceDyn=='')
					{
						alert('Non è presente un id semantico');
						
					}else{
						//var dettaglioCampoDinamico${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2campi/popupview.htm?codice="+codiceDyn+"&popupCaller="+caller,69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");
						doHref('../dyn2campi/popupview.htm?codice='+codiceDyn+'&callerId=${dyn2modellid.id.codice}');
					}
			    }
				
				
				function nuovaRegolaAttivazione(){
					var callerId = "${dyn2modellid.id.codice}";
					if(callerId.length == 0){
						callerId = "0";
					}
					doHref('../dyn2regole/popupcreate.htm?callerModT=${dyn2modellid.dyn2Modellit.id.codice}&callerId=' + callerId,'');
				}
				
				function dettaglioRegolaAttivazione(){
					var caller = 'dyn2RegoleAttivo_id';
					var codiceReg=document.getElementById("dyn2RegoleAttivo_hidden").value;
					if(codiceReg=='')
					{
						alert('Non è presente nessuna regola');
						
					}else{
						//var dettaglioRegola${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2regole/popupview.htm?codice="+codiceReg+"&popupCaller="+caller,69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");
						doHref('../dyn2regole/popupview.htm?codice='+codiceReg,'');
					}
			    }

			    jQuery(document).ready(function(){
			    	initTextEditors();
				});
				
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${dyn2modellid.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${dyn2modellid.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<li><a href="javascript:closeAndCallback('refreshList')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>