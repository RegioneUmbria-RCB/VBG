<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_tipititolo.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_tipititolo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_tipititolo.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_tipititolo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>    
			<table>				
				<tr>
					<td>
						<fmt:message key="label.tipo_titolo" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="inventarioprocTipititolo_id" path="inventarioprocTipititolo.tipotitolo" size="70"/>
						<spring-form:errors path="inventarioprocTipititolo.tipotitolo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.flag_mostra_data"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flagMostra_data_id" path="inventarioprocTipititolo.flgMostraData"/>
						<spring-form:errors path="inventarioprocTipititolo.flgMostraData" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.flag_mostra_numero"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgMostraNumero_id" path="inventarioprocTipititolo.flgMostraNumero"/>
						<spring-form:errors path="inventarioprocTipititolo.flgMostraNumero" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.flag_mostra_rilasciatoDa"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgMostraRilasciatoDa_id" path="inventarioprocTipititolo.flgMostraRilasciatoDa"/>
						<spring-form:errors path="inventarioprocTipititolo.flgMostraRilasciatoDa" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.flag_richiede_allegato"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgRichiedeAllegato_id" path="inventarioprocTipititolo.flgRichiedeAllegato" onchange="hideAndShowEl(this,'flgVerificaFirmaAllegato_tr_id','flgVerificaFirmaAllegato_id');"/>
						<spring-form:errors path="inventarioprocTipititolo.flgRichiedeAllegato" cssClass="error"/>
					</td>
				</tr>	
				 <!--  Gestisce la visualizzazione in update del campo flgRichiedeAllegato -->
			    <c:set scope="page" value="display:none;" var="verificaFirma"></c:set>
				<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.flgRichiedeAllegato eq true}">
					<c:set scope="page" value="" var="verificaFirma"></c:set>
				</c:if>		
				<tr id="flgVerificaFirmaAllegato_tr_id" style="${verificaFirma}">
					<td>
						<fmt:message key="label.flag_verifica_firma_allegato"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgVerificaFirmaAllegato_id" path="inventarioprocTipititolo.flgVerificaFirmaAllegato"/>
						<spring-form:errors path="inventarioprocTipititolo.flgVerificaFirmaAllegato" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.flag_non_pubblicare"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgNonPubblicare_id" path="inventarioprocTipititolo.flgNonPubblicare" />
						<spring-form:errors path="inventarioprocTipititolo.flgNonPubblicare" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.allegato_obbligatorio"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgAllObbligatorio_id" path="inventarioprocTipititolo.flgAllObbligatorio" />
						<spring-form:errors path="inventarioprocTipititolo.flgAllObbligatorio" cssClass="error"/>
					</td>
				</tr>
											
			</table>
			<script type='text/javascript'>
				$('inventarioprocTipititolo_id').focus();
				
				function hideAndShowEl(elem,id,valueid){
				
					if(elem.checked){
						$(id).style.display="";
					}else
					{
						$(id).style.display="none";
						$(valueid).checked=false;
					}
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice==null}">
				<li><a href="javascript:doSubmit('insertTipititolo.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.inventarioprocTipititolo.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateTipititolo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteTipititolo.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listtipititolo.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>