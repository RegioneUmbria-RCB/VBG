<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<spring-form:form commandName="dettaglioPeriodicitaHelper" name="periodoform">
	
		<div>
			<fmt:message key="bollgestione.periodo.anno.table" />
			<spring-form:select path="anno" id="periodo_anno" items="${anniCalcolaPeriodi }" itemValue="codice" itemLabel="descrizione" onchange="" />
		</div>
		
			<table>
				<th></th>
			<spring:bind path="elementi">
				<c:forEach items="${dettaglioPeriodicitaHelper.elementi}" var="elemento" >
					<tr>
						<td>
							<input type="radio" name="${status.expression}" value="${elemento.chiave}" />
						</td>
						<td>
							<spring-form:label path="" >${elemento.descrizione }</spring-form:label>
						</td>
					
					</tr>
				
				</c:forEach>
			</spring:bind>
		</table>
	</spring-form:form>
	
	<div id="functions">
		<ul>
				<li><a href="javascript:ajaxAggiornaPeriodo();"><fmt:message key="button.ok" /></a></li>
		</ul>
	</div>

</body>
</html>