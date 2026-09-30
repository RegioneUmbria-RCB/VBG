<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		Attività
	</title>
</head>
<body>
	<span class="titoloPagina">
		Attività regionale
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../inventarioprocedimenti/viewEndoStp2" />
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="stpEndoTipo2" name="inviodati">
		
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="stpEndoTipo2" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						Codice Endo Regionale 
					</td>
					<td>
						<spring-form:input id="codiceEndoRegionale_id" path="codiceEndoRegionale" size="100" maxlength="250"/>
						<spring-form:errors path="codiceEndoRegionale" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						Tipologia endoprocedimento
					</td>
					<td>
					
						<spring-form:select id="stpTipologieEndo2_id" path="stpTipologieEndo2.id.codice">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${stptipologies}" itemLabel="descrizione" itemValue="id.codice"/>
							
						</spring-form:select>
						<spring-form:errors path="stpTipologieEndo2.id.codice" cssClass="error"/>
					</td>
				</tr>
				<tr >
					<td><fmt:message key="label.alberoproc" /></td>
					<td>
						<jsp:include page="../includes/searchAlberoProc.jsp">
							<jsp:param name="propertyPath" value="alberoproc" />								
							<jsp:param name="pathPropertyDescription" value="alberoproc.descrizioneCompleta" />
							<jsp:param name="pathPropertyCode" value="alberoproc.id.codice" />
							<jsp:param name="isSelectLeafDisable" value="true" />
							<jsp:param name="isSelectNodoPadre" value="true" />
						</jsp:include>
					</td>						
				</tr>
			
			</table>
	<script type="text/javascript">
	
	
	
	
		function salva(){
			if(jQuery('#codiceEndoRegionale_id').val()!='' && jQuery('#stpTipologieEndo2_id').val()!=null){
				doSubmit('insertStpEndoTipo2.htm','',document.inviodati);
			}else{
				alert('Compilare tutti i dati');
			}
		}
		
	
	</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${stpEndoTipo2.id.codice==null}">
				<li><a href="javascript:salva()"><fmt:message key="button.insert" /></a></li>
			</c:if>			
			<li><a href="javascript:doHref('listStpEndo.htm?codiceendo=${stpEndoTipo2.inventarioprocedimenti.id.codice}&software=${stpEndoTipo2.inventarioprocedimenti.software.codice }','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>