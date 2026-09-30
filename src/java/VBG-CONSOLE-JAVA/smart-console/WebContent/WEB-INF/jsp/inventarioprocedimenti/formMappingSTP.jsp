<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="inventarioprocedimenti.label.mapping_stp.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="inventarioprocedimenti.label.mapping_stp.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${inventarioprocedimenti.entity.procedimento}</div>
		</div>
	</div>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
		    <table width="100%">
		    	<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.composizioneCodiceSTC" /></td>
				</tr>
		    	<tr>
		    		<td width="10%">
						<fmt:message key="label.prefissoCodiceSTC" />
					</td>
					<td><fmt:message key="label.amministrazione" />
						<select name="codiceAmm" id="codiceAmm_id" onchange="setCodiceSTP('amm')">
							<option value="" selected="selected">Seleziona...</option>			
							<c:forEach items="${amministrazioni }" var="amm">
								<option value="${amm.stcIdente }${amm.stcIdsportello }">${amm.amministrazione }</option>
							</c:forEach>
						</select>
						&nbsp;
						<fmt:message key="label.nodo" />
						<select name="codiceNodo" id="codiceNodo_id" onchange="setCodiceSTP('nodo')">
							<option value="" selected="selected">Seleziona...</option>
							<c:forEach items="${nodi }" var="nodo">
								<option value="${nodo.idEnte }">${nodo.idEnte }</option>
							</c:forEach>
						</select>
						<fmt:message key="help.composizioneCodiceSTC" />
					</td>
				</tr>
				<tr>
		    		<td width="10%">
						+
					</td>
				</tr>
				<tr>
					<td width="10%">
						<fmt:message key="label.codice" />
					</td>
					<td>	
						<input type="text" name="codiceSTPpost" size="30" id="codiceSTPpost_id" onblur="setCodiceSTP()"/>
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.risultato" /></td>
				</tr>
				<tr>
					<td width="10%">
						<fmt:message key="label.codiceProcSTP" />
					</td>
					<td>
						<spring-form:input id="codicestp_id" path="inventarioprocedimentipeople.codProcPeople" size="50" readonly="true"/>
						<spring-form:errors path="inventarioprocedimentipeople.codProcPeople" cssClass="error"/>
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.displayMode == 0}">
			<li><a href="javascript:submitInsert('insertMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}')"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.displayMode != 0}">
			<li><a href="javascript:submitUpdate('updateMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}')"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('deleteMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		function setCodiceSTP(sel){
			var codAmm = $('codiceAmm_id').options[$('codiceAmm_id').selectedIndex].value;
			var codNodo = $('codiceNodo_id').options[$('codiceNodo_id').selectedIndex].value;
			var codSTP = $('codiceSTPpost_id').value;
			if(sel == 'amm'){
				$('codicestp_id').value=codAmm+codSTP;
				$('codiceNodo_id').options[0].selected=true;
			}
			if(sel == 'nodo'){
				$('codicestp_id').value=codNodo+codSTP;
				$('codiceAmm_id').options[0].selected=true;
			}else{
				if (codAmm != ''){
					$('codicestp_id').value=codAmm+codSTP;
				}else
				if (codNodo != ''){
					$('codicestp_id').value=codNodo+codSTP;
				}else{
					alert("Selezionare un\'Amministrazione o un Nodo");
					return false;
				}
			}
			
		}
		function submitInsert(url){
			if(validateCodiceSTP(false)){
				doSubmit(url);
			}
		}
		function submitUpdate(url){
			if(validateCodiceSTP(true)){
				doSubmit(url);
			}
		}
		function validateCodiceSTP(isUpdate){
			
			var codAmm = $('codiceAmm_id').options[$('codiceAmm_id').selectedIndex].value;
			var codNodo = $('codiceNodo_id').options[$('codiceNodo_id').selectedIndex].value;
			var codSTP = $('codiceSTPpost_id').value;
			
			if(codAmm == '' && codNodo == '' && codSTP == ''){
				if(isUpdate){
					return true;
				}else{
					alert("Selezionare un\'Amministrazione o un Nodo ed inserire un Codice");
					return false;
				}
			}
			if(codAmm == '' && codNodo == '' && codSTP != ''){
				alert("Selezionare un\'Amministrazione o un Nodo");
				return false;
			}
			if((codAmm == '' && codNodo != '') || (codAmm != '' && codNodo == '')){
				if(codSTP == ''){
					alert("Selezionare un Codice");
					return false;
				}
			}
			return true;
		}
	</script>
</body>
</html>