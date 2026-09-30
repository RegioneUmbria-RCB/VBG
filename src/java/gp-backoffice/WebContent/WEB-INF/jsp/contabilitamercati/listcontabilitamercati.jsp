<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.contabilitamercati.title.list" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="form.contabilitamercati.title.list" /></span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../contabilitamercati/search" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<div id="subcontent">

<!-- START Tabella principale --> 
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td colspan="2"><fmt:message key="form.contabilitamercati.mercato" /></td>
			</tr>
		</thead>
		<tbody class="tbody">
			<%
			    int i = 1;
			%>
			<c:forEach var="riepilogomercatiList_var"
				items="${riepilogomercatiList}"
				varStatus="graduatoriedCampiStatus">
				<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
					<td valign="top" width="40%">
					<span class="parametri"><label>${riepilogomercatiList_var.mercati.descrizione} - ${riepilogomercatiList_var.mercatiUso.descrizione}</label></span>
					<script type="text/javascript">
						function disableFunctions${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}() {
							document.getElementById("functions${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}").innerHTML = "<img src='../images/spinner.gif'>";
							buttonSubmitted = true;
						}
						function doHref${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}(hrefLink, confirmMessage) {
							if (buttonSubmitted) {
								return;
							}
							disableFunctions${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}();
							var goTo = '..%2Fcalendariomercato%2Fsituazionecontabile.htm%3Fcodice%3D${riepilogomercatiList_var.mercati.id.codice}%26uso%3D${riepilogomercatiList_var.mercatiUso.id.codice}';
							document.location.href = hrefLink+goTo;
						}
					</script>
					
					<br/>
					
					
                    </td>                  
                   
					<td valign="top">
					<c:if test="${not empty riepilogomercatiList_var.situazionecontabileList}">
						<!-- START TABELLA ANNIDATA NELLA SECONDA COLONNA -->
						<div class="jmesa">
						<table border="0" width="100%"  cellpadding="2" cellspacing="0"
							class="table">
							<thead>
								<tr class="header">
									<td><fmt:message key="form.contabilitamercati.anno" /></td>
	                                <td><fmt:message key="form.registrazionimercato.title.view" /></td>
								</tr>
							</thead>
							<tbody class="tbody">
								<%
								    int j = 1;
								%>
								<c:forEach var="situazioneContabileList_var"
									items="${riepilogomercatiList_var.situazionecontabileList}">
									<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
										<td>${situazioneContabileList_var.anno}</td>
	                                    <td>
	                                  
	                                    
	 									<script type="text/javascript">
	                                       var goToUrl${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice} = "../registrazionimercato/registrazioniByMercato.htm?mercati.id.codice=${riepilogomercatiList_var.mercati.id.codice}&mercatoUso=${riepilogomercatiList_var.mercatiUso.id.codice}&anno=${situazioneContabileList_var.anno}";
	                                       goToUrl${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice} = escape(goToUrl${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice});
											function doHref${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice}(hrefLink, confirmMessage) {
												if (buttonSubmitted) {
													return;
												}
													document.location.href = hrefLink;
											}
										</script>
	                                     <a class="dettaglioColumn" href="javascript:doHref${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice}('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl${situazioneContabileList_var.anno}${riepilogomercatiList_var.mercatiUso.id.codice},'')" title="${situazioneContabileList_var.anno}&nbsp;<fmt:message key="form.registrazionimercato.title.view" />"><label><fmt:message key="form.registrazionimercato.title.view" /></label></a>
	                                   	
	 								</td>
									
									</tr>
									<%
									    j++;
									%>
								</c:forEach>
							</tbody>
						</table></div>	
						</c:if>	
						<c:if test="${empty riepilogomercatiList_var.situazionecontabileList}">
						&nbsp;
						</c:if>			
					<!-- END FINE TABELLA ANNIDATA --></td>
					
					</tr>
				<tr>
					<td colspan="2" class="<%=(i % 2) == 0 ? "odd" : "even"%>">
						<div  id="functions${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}">
							<a href="javascript:doHref${riepilogomercatiList_var.mercati.id.codice}_${riepilogomercatiList_var.mercatiUso.id.codice}('../history/set.htm?ReturnTo=${_urlback}&GoTo=','')" ><img src="${pageContext.request.contextPath}/images/situazionecontabile.gif" title="<fmt:message key="button.situazionecontabile" />" /></a>
							<a href="../registrazioni/pagamentoUtenzeCreate.htm?mercati.id.codice=${riepilogomercatiList_var.mercati.id.codice}&mercatoUso=${riepilogomercatiList_var.mercatiUso.id.codice}" ><img src="${pageContext.request.contextPath}/images/pagamentoutenze.gif" title="<fmt:message key="button.mercati.pagamentoutenze" />" /></a>
							<a href="../mercatidletture/createLettureContatore.htm?mercati.id.codice=${riepilogomercatiList_var.mercati.id.codice}&mercatoUso=${riepilogomercatiList_var.mercatiUso.id.codice}" ><img src="${pageContext.request.contextPath}/images/contatore.gif" title="<fmt:message key="button.mercati.gestioneletture" />" /></a>				
	                	</div>
	                	<br />&nbsp;
					</td>
				</tr>
				<%
				    i++;
				%>
					<tr class="header">
						<td colspan="2" style="padding: 0; font-size: 2px;">&nbsp;</td>
					</tr>		
			</c:forEach>
		</tbody>
	</table>

	</div>

<!-- END FINE TABELLA PRINCIPALE --> 
</div>
<script type="text/javascript">
	function disableFunctionsButton() {
		document.getElementById("functionsButton").innerHTML = "<img src='../images/spinner.gif'>";
		buttonSubmitted = true;
		}
		function doHrefButton(hrefLink, confirmMessage) {
			if (buttonSubmitted) {
				return;
			}
			disableFunctionsButton();
			document.location.href = hrefLink;
		}
</script>
<div id="functionsButton">
	<div id="functions">
	<ul>
		<li><a href="javascript:doHrefButton('createSearch.htm','')"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</div>
</body>
</html>