<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.qrxmlbase" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.qrxmlbase" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="qrxmlbaseForm" action="list.htm">
				<jmesa:springTableFacade
					id="qrxmlbase_id" 
					items="${list}" 
					var="qrxmlbase_var"
					exportTypes="pdfp,csv,excel" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							
							<jmesa:htmlColumn property="codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${qrxmlbase_var.id.codice}">${qrxmlbase_var.codice}</a>
                            </jmesa:htmlColumn>                           

							<jmesa:htmlColumn property="titolo" titleKey="label.titolo" />
							<jmesa:htmlColumn property="help" titleKey="label.help"></jmesa:htmlColumn>
							<jmesa:htmlColumn property="id" titleKey="documentiistanza.label.oggetto"  sortable="false" filterable="false" width="5%" >
								<c:if test="${qrxmlbase_var.oggetti != null}">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="qrxmlbase_${qrxmlbase_var.oggetti.id.codice}" />
			       						<jsp:param name="idComuneOggetto" value="${qrxmlbase_var.oggetti.id.idcomune}" />
			       						<jsp:param name="fileId" value="${qrxmlbase_var.oggetti.id.codice}" />
			       						<jsp:param value="true" name="readonly" />
			   						</jsp:include>
		   						</c:if>	
							</jmesa:htmlColumn>
							 
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="8%">
								<a href="view.htm?codice=${qrxmlbase_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${qrxmlbase_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${qrxmlbase_var.id.codice}"/>
								</a>
							</jmesa:htmlColumn>
							
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?1=1';
				var _captionTab='Quadri di base';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				 
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>