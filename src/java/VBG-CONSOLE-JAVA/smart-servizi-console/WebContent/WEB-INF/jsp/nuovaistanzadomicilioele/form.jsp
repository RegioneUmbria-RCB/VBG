<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<div class="titolo_sezione"><fmt:message key='label.scelta-domicilio-elettronico' /></div>
		<div class="sezione">			
			<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.domicilio-elettronico' /></label></td>
				<td>
				<spring-form:select path="domicilioElettronicoTemp" id="domicilioElettronicoId">
				<spring-form:option value=""></spring-form:option>
				<c:forEach items="${list }" var="deh">					
				<spring-form:option value="${deh.email }">
					${deh.estremiSoggetto } <c:if test="${deh.pec eq true }">(PEC)</c:if>
				</spring-form:option>
				</c:forEach>
				</spring-form:select>
				</td>
			</tr>
			<tr id="domicilioElettronicoAltroId" style="display: none">
				<td><label><fmt:message key='label.domicilio-elettronico-altro' /></label></td>
				<td>
				<input type="text" name="domicilioElettronicoAltro" value="${domicilioElettronicoAltro }" size="70" />
				</td>
			</tr>
			</table>
		</div>
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
	<script type="text/javascript">
	$(document).ready(function(){
		if($(this).find(":selected").val() == 'ALTRO'){
			$('#domicilioElettronicoAltroId').show();
		}
		$('#domicilioElettronicoId').change(function() {
			if($(this).find(":selected").val() == 'ALTRO'){
				$('#domicilioElettronicoAltroId').show();
			}else{ 
				$('#domicilioElettronicoAltroId').hide(); 
			}
		});
		
	});
	</script>
</body>
</html>