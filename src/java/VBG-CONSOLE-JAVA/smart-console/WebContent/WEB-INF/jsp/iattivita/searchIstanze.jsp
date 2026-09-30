<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.utility_creazione_attivita"/>
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.utility_creazione_attivita" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="filterIstanze" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="filterIstanze" />
		    </jsp:include>
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2" align="center"><fmt:message key="label.creazione_attivita"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><fmt:message key="label.descrizione_utility_crea_attivita" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.configurazione_regola"/></td>
				</tr>
				<tr>
				   <td width="25%"><fmt:message key="label.tipo_raggruppamento" /></td>
				   <td>
					   <input id="id_checkbox_azione" type="radio" checked="checked" onclick="javascript:changeCheckbox('','id_check_box_localizzazione');"/>
					   <fmt:message key="label.azione"/>
				   
						 <input id="id_check_box_localizzazione" type="radio" onclick="javascript:changeCheckbox('id_select_azioni','id_checkbox_azione');"/>
						 <fmt:message key="label.stessa_localizzazione"/>
				   </td>
				</tr>
				<tr>
				   <td><fmt:message key="label.ignora_esistenza"/></td>
				   <td>
				   		<spring-form:checkbox path="flagIgnoraEsistenza" />
				   		<fmt:message key="label.descrizione_ignora_esistenza"/>
				   </td>
				</tr>
				
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.configurazione_filtri_parametri"/></td>
				</tr>
				<tr>
					 <td><fmt:message key="label.tipo_azione"/></td>
					 <td>
				   		<spring-form:select id="id_select_azioni" path="azioni.azId">
				   			<option label="<fmt:message key="label.seleziona"/>" value=""/>
				   			<spring-form:options items="${listAzioni}" itemLabel="azDescrizione" itemValue="azId"/>
				   		</spring-form:select>
				   </td>
				</tr>	
				<tr id="elementIdBeforeCombo">
					<td colspan="2" style="height: 0px"></td>
					<jsp:include page="../includes/comboComuni.jsp">
						<jsp:param name="comune" value="${codiceComune}" />
						<jsp:param name="mostraTutti" value="false" />
						<jsp:param name="readOnly" value="false" />
						<jsp:param name="commandPropertyPath" value="comuni" />
						<jsp:param name="colspan" value="1" />
						<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
					</jsp:include>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.moduli" />
					</td>
					<td>
						<select  name="software" multiple="multiple">						
							<c:forEach var="software_var" items="${listSoftwareAbilitati}">									 
								<option value="${software_var.codice}">${software_var.descrizione}</option>						
							</c:forEach>
						</select>
						<fmt:message key="label.descrizione_uso_select_moduli"/>
					</td>
				</tr>
				 
			</table>
		</spring-form:form>
	</div>
	
	<script type="text/javascript">
		function changeCheckbox(idFiled,idCheckBoxChange)
		{
			
			document.getElementById(idCheckBoxChange).checked=false
			if(idFiled!='')
			{
				document.getElementById(idFiled).disabled=true;
				document.getElementById('id_select_azioni').value='';
			}else
			{
				document.getElementById('id_select_azioni').disabled=false;
			}
			
		}
	</script>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('creaAttivita.htm','',document.inviodati)"><fmt:message key="button.crea_attivita" /></a></li>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>