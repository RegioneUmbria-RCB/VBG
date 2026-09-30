<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.calendariomercato.title.list" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="form.calendariomercato.title.list" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<div id="subcontent">
<!-- START Tabella principale --> 

<c:forEach var="posteggiolistmercatouso_var" items="${posteggiolistmercatouso}" varStatus="var">
	
	<span class="parametri">
	    <fmt:message key="form.calendariomercato.mercato" />: <label>${mercato.descrizione}</label>
	</span>
	<span class="parametri">
		<fmt:message key="form.calendariomercato.mercatouso" />: <label>${posteggiolistmercatouso_var[0].mercatiUso.descrizione} </label>
	</span>
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%"><fmt:message key="form.calendariomercato.posteggio" /></td>
				<td><fmt:message
					key="form.calendariomercato.sistuazionecontabile" /></td>
			</tr>
		</thead>
		<tbody class="tbody">
			<%
			    int i = 1;
			%>
			<c:forEach var="posteggiolist_var"
				items="${posteggiolistmercatouso_var}"
				varStatus="graduatoriedCampiStatus">
				<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
					<td valign="top"><b>${posteggiolist_var.posteggio.codiceposteggio}</b></td>
					<td valign="top">					
								<%-- START TABELLA ANNIDATA NELLA SECONDA COLONNA --%>
								<div class="jmesa">
								<table border="0" width="100%" cellpadding="2" cellspacing="0"
									class="table">
									<thead>
										<tr class="header">
											<td width="10%"><fmt:message key="form.calendariomercato.anno" /></td>
											<td width="30%"><fmt:message key="form.calendariomercato.conto" /></td>
											<td width="20%" align="right"><fmt:message key="form.calendariomercato.importo" />&nbsp;<fmt:message key="label.valuta" /></td>
											<td width="20%" align="right"><fmt:message key="form.calendariomercato.incasso" />&nbsp;<fmt:message key="label.valuta" /></td>
											<td width="20%" align="right"><fmt:message key="form.calendariomercato.saldo" />&nbsp;<fmt:message key="label.valuta" /></td>
										</tr>
									</thead>
									<tbody class="tbody">
										<%
										    int j = 1;
										%>
										<c:set var="totImporto" value="0.0"/>
										<c:set var="totIncassato" value="0.0"/>							
										<c:forEach var="situazioneContabileList_var"
											items="${posteggiolist_var.situazioneContabileList}">
											<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
												<td>${situazioneContabileList_var.anno}</td>
												<td>${situazioneContabileList_var.conti.descrizione}</td>
												<td align="right"><fmt:formatNumber minFractionDigits="2" value="${situazioneContabileList_var.importo}" /></td>
												<td align="right"><fmt:formatNumber minFractionDigits="2" value="${situazioneContabileList_var.incassato}" /></td>
												<td align="right"><fmt:formatNumber minFractionDigits="2" value="${situazioneContabileList_var.saldo}" /></td>
											</tr>
											<c:set var="totImporto" value="${totImporto+situazioneContabileList_var.importo}"/>
											<c:set var="totIncassato" value="${totIncassato+situazioneContabileList_var.incassato}"/>
											<%
											    j++;
											%>
										</c:forEach>
									</tbody>
									<c:if test="${fn:length(posteggiolist_var.situazioneContabileList) > 0}">
									<tfoot>
										<tr class="header">
											<td colspan="2" align="right"><fmt:message key="label.totalColumn" /></td>
											<td width="20%" align="right"><fmt:formatNumber minFractionDigits="2" value="${totImporto}" /></td>
											<td width="20%" align="right"><fmt:formatNumber minFractionDigits="2" value="${totIncassato}" /></td>
											<td width="20%" align="right"><fmt:formatNumber minFractionDigits="2" value="${totImporto - totIncassato}" /></td>
										</tr>
									</tfoot>
									</c:if>
								</table>
								</div>
								<%-- END FINE TABELLA ANNIDATA --%>
						
					</td>
				</tr>
				<%
				    i++;
				%>
				<tr >
					<td style="border-top:thin dotted" colspan="2">&nbsp;</td>
				</tr>				
			</c:forEach>
		</tbody>
	</table>

	</div>
</c:forEach> 
<!-- END FINE TABELLA PRINCIPALE --> 
<script type="text/javascript">
	var _jmesaUrl = 'list.htm?';
	var _captionTab = '<fmt:message key="form.domain.title.list" />';
</script></div>
<div id="functions">
<ul>
	<li><a
		href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','');"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>