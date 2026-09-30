<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipiprocedure.label.lista_documenti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipiprocedure.label.lista_documenti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <div id="subcontent">
	    <span class="parametri"><fmt:message key="tipiprocedure.label.codice_procedura"/><label> ${tipiprocedure.id.codice}</label></span>
	    <span class="parametri"><fmt:message key="label.procedura"/><label> ${tipiprocedure.procedura}</label></span>		
	<div class="jmesa" >
        <table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="label.descrizione"/></td>
					<td width="2%" ><fmt:message key="label.pubblica" /><init:help idHelp="help_p" textKey="help.documentazione_pubblica"/></td>
					<td width="2%" ><fmt:message key="label.richiesto" /><init:help idHelp="help_r" textKey="help.documentazione_richiesta"/></td>						                
					<td width="2%" ><fmt:message key="label.richiede_firma" /></td>
					<td width="5%"><fmt:message key="label.download"/></td>
					<td width="5%"><fmt:message key="label.elimina"/></td>
				</tr>
				</thead>
                <%
			    int i=0;
			    %>
				<tbody class="tbody">
				<c:forEach items="${tipiproceduredocumentiList}" var="var_documenti" varStatus="index">
				
					<tr class="<%=(i%2)==0?"odd":"even"%>">
					    <td>
						  <a  href="viewDocumenti.htm?codicedocumento=${var_documenti.id.codice}" >${var_documenti.descrizione}</a>
						</td>
						
						<td>								  	 
						      <c:if test="${var_documenti.pubblica == 1}">AR/FE</c:if>
						  	  <c:if test="${var_documenti.pubblica == 2}">AR</c:if>
						  	  <c:if test="${var_documenti.pubblica == 3}">FO</c:if>
		    		    </td>
		    		    <td>
	             	 		<c:if test="${var_documenti.richiesto eq true}">SI</c:if>								  	  
		                	<c:if test="${var_documenti.richiesto eq false}">NO</c:if>								  	  
		                </td>
		              	<td>
			             	<c:if test="${var_documenti.foRichiedefirma eq true}">SI</c:if> 
		                    <c:if test="${var_documenti.foRichiedefirma eq false}">NO</c:if> 
		                </td>
		                <td align="right" width="5%">
						    <c:if test="${var_documenti.oggetto.id.codice!=null}">
								<a class="visualizzaDocColumn" href="${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${var_documenti.oggetto.id.codice}" title="<fmt:message key="label.download" /> ${var_documenti.oggetto.id.codice}">
									<label><fmt:message key="label.download" /></label>
						   		</a>
						    </c:if>
						</td>
                        <td align="right" width="5%">
							<a class="eliminaRiga" href="javascript:doHref('deleteDocumenti.htm?codicedocumento=${var_documenti.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />&nbsp;${var_documenti.descrizione}">
							<label><fmt:message key="label.elimina" /></label>
						</a> 
						</td>
					</tr>
				<%i++;%>
				
				</c:forEach>
				</tbody>
				

			</table>
	    </div>
	</div>   
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createDocumenti.htm?codicetipoprocedura=${tipiprocedure.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${tipiprocedure.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>