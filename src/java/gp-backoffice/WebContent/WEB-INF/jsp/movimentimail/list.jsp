<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="movimentimail.label.lista_movimentimail.title" /></title>
</head>


	<%
		 if(StringUtils.isNotBlank((String)request.getParameter("status_msg")))
		 {
		     String status_msg=(String)request.getParameter("status_msg");
			 pageContext.setAttribute("_status_msg", status_msg);
		 }else
		 {
		     pageContext.setAttribute("_status_msg", "ok");
		 }
	
	%>

<body>
	<span class="titoloPagina"><fmt:message key="movimentimail.label.lista_movimentimail.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../movimentimail/list" />
	</jsp:include>
	<div id="subcontent">
		<br />
		<c:if test="${movimento != null }">
		<span class="parametri"><fmt:message key="label.movimento"/><label> ${movimento.movimento}</label></span>
		</c:if>
		<c:if test="${istanza != null }">
		<span class="parametri"><fmt:message key="label.istanza"/><label> ${istanza.numeroistanza}</label></span>
		</c:if>
		<c:if test="${_status_msg eq '01'}">
		<div id="status_msg" class="success_header alert alert-success" >
			<fmt:message key="movimentimail.label.help.registrazione_email" />
		</div>
		</c:if>
		<form name="movimentimailForm" action="list.htm?codicemovimento=${codicemovimento}&codiceistanza=${codiceistanza}">
			<jmesa:springTableFacade
				id="movimentimail_id" 
				items="${movimentimailList}" 
				var="movimentimail_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.MovimentimailFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
						<c:if test="${movimentimail_var.bozza }">
							<a href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmovimentimail%2FcreateMail.htm%3Fcodicemovimento%3d${movimentiCommand.entity.id.codice}%26codiceistanza%3d${movimentiCommand.entity.istanza.id.codice}%26idMov%3d${movimentimail_var.id.codice}">${movimentimail_var.id.codice}</a>
						</c:if>
						<c:if test="${!movimentimail_var.bozza }">
                           	<a href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmovimentimail%2Fview.htm%3Fcodice%3D${movimentimail_var.id.codice}">${movimentimail_var.id.codice}</a>
                        </c:if>
                        </jmesa:htmlColumn>
                        <c:if test="${movimento == null }">
                        <jmesa:htmlColumn property="movimento.movimento" titleKey="movimentimail.label.movimento" />
                        <jmesa:htmlColumn property="movimento.data" width="80px" titleKey="movimentimail.label.movimento_data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataMovimentoCustomFilter"/>								
						</c:if>
						<jmesa:htmlColumn property="mittente" titleKey="movimentimail.label.mittente" />
						<jmesa:htmlColumn property="destinatario" titleKey="movimentimail.label.destinatario">
						${movimentimail_var.destinatario }
						<c:if test="${not empty movimentimail_var.movimentimailFigli }">
						<label>(<a href="#" onclick="dijit.byId('dialog${movimentimail_var.id.codice }').show();"><fmt:message key="label.movimentimail.risposte" /></a>)</label>
						<div id="dialog${movimentimail_var.id.codice }" dojoType="dijit.Dialog" title="<fmt:message key='label.movimentimail.risposte' />" style="display: none; width: 95%">
						    <div dojoType="dijit.layout.ContentPane" style="width: 98%;" class="jmesa">
					            <table class="table">
					            	<thead class="header">
					            		<tr>
					            			<td style="width: 2%;"><fmt:message key="label.codice" /></td><td style="width: 10%;"><fmt:message key="movimentimail.label.mittente" /></td><td style="width: 10%;"><fmt:message key="movimentimail.label.destinatario" /></td><td style="width: 20%"><fmt:message key="movimentimail.label.oggetto" /></td><td><fmt:message key="movimentimail.label.corpo" /></td><td style="width: 5%"><fmt:message key="movimentimail.label.data_ricezione" /></td>
					            		</tr>
					            	</thead>
					            	<tbody class="tbody">
					            		<c:forEach items="${movimentimail_var.movimentimailFigli }" var="emailFiglio">
					            		<tr class="even" valign="top">
					            			<td>${emailFiglio.id.codice}</td><td>${emailFiglio.mittente}</td><td>${emailFiglio.destinatario}</td><td>${emailFiglio.oggetto}</td><td><a href="#" onclick="showHideDiv('figlio${emailFiglio.id.codice}');">(Visualizza)</a><div id="figlio${emailFiglio.id.codice}" style="display: none;"><c:out value="${emailFiglio.corpo}" escapeXml="false"/></div></td><td><fmt:formatDate value="${emailFiglio.datainvio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/></td>
					            		</tr>
					            		</c:forEach>
					            	</tbody>
					            </table>   
						    </div>
						</div>
						</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="oggetto" titleKey="movimentimail.label.oggetto" />
						<jmesa:htmlColumn property="datainvio" titleKey="movimentimail.label.datainvio.table" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataInviomailCustomFilter" />
						<jmesa:htmlColumn property="messageId" titleKey="movimentimail.label.messageId" />
						<jmesa:htmlColumn property="mailConfig.descrizione" titleKey="label.account_mail_cfg" />
						<jmesa:htmlColumn titleKey="movimentimail.label.bozza">
								<c:if test="${movimentimail_var.bozza }"><span style="color: red;">SI</span></c:if>
								<c:if test="${!movimentimail_var.bozza }"><span style="color: green;">NO</span></c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<c:if test="${movimentimail_var.bozza }">
								<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmovimentimail%2FcreateMail.htm%3Fcodicemovimento%3d${movimentiCommand.entity.id.codice}%26codiceistanza%3d${movimentiCommand.entity.istanza.id.codice}%26idMov%3d${movimentimail_var.id.codice}" title="<fmt:message key="label.edit.record" />${movimentimail_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</c:if>
							<c:if test="${!movimentimail_var.bozza }">
								<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmovimentimail%2Fview.htm%3Fcodice%3D${movimentimail_var.id.codice}" title="<fmt:message key="label.edit.record" />${movimentimail_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</c:if>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden"  value="${codicemovimento}" name="codicemovimento"/>
			 <input type="hidden"  value="${codiceistanza}" name="codiceistanza"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codicemovimento=${codicemovimento}&codiceistanza=${codiceistanza}&';
			var _captionTab='<fmt:message key="movimentimail.label.lista_movimentimail.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>