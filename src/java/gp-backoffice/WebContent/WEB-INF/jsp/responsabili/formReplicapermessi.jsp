<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="responsabili.label.replica_permessi.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="responsabili.label.replica_permessi.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
	            <div>
	             	<fmt:message key="label.responsabile" />:
	        	</div>
           	</div>
           	<div class="parametro">
       	   		<div>
		        	 <c:out value="${responsabile.entity.responsabile}"/>
		   		</div>
		 	</div>
        </div>   
        <br />
        <spring-form:form commandName="responsabile" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="responsabile" />
		    </jsp:include>
			<table width="100%">
				<tr class="titoloSezione">
					<td><fmt:message key="responsabili.label.operatori_replica_permessi" /></td>
				</tr>
				<c:forEach items="${responsabiliList}" var="responsabiliList_var" varStatus="a">
					<tr>
						<td>
							<input type="checkbox" id="responsabile_id${responsabiliList_var.id.codice}" value="${responsabiliList_var.id.codice}" name="responsabiliList" />
							<label for="responsabile_id${responsabiliList_var.id.codice}">${responsabiliList_var.responsabile}</label>						
						</td>
					</tr>
				</c:forEach>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('saveReplicapermessi.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doHref('createPermessi.htm?codice=${responsabile.entity.id.codice}&permessisoftware=TT','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>