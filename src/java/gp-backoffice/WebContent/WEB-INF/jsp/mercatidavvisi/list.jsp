<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.title.mercati_d_avvisi.list" /></title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="form.title.mercati_d_avvisi.list" />
</span>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid/view" />
	</jsp:include>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>

<div id="subcontent">
	    <jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercatiD.mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercatiD.mercati.descrizione}" />											
		</jsp:include>
<div class="parametriDiv">
  	<div class="etichetta">
		<div><fmt:message key="label.posteggio" />:</div>
	</div>
	<div class="parametro">
		<div><c:out value="${mercatiD.codiceposteggio}" /></div>
 	</div>
</div>
<!-- exportTypes="pdfp,excel,csv" -->
<form name="mercatidavvisiForm" action="list.htm"><jmesa:springTableFacade
	id="mercatidavvisi_id" items="${mercatidavvisiList}" var="mercatidavvisi_var"
	 stateAttr="restore" >
	<jmesa:htmlTable>
		<jmesa:htmlRow>
		    <jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                 <a href="view.htm?codice=${mercatidavvisi_var.id.codice}">${mercatidavvisi_var.id.codice}</a>
            </jmesa:htmlColumn>
		    <jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.titolare" />
			<jmesa:htmlColumn property="tipicausalioneri.coDescrizione" titleKey="label.avviso" />
			<jmesa:htmlColumn property="flagVerificato" titleKey="label.verificato" filterable="false" >
			<c:if test="${mercatidavvisi_var.flagVerificato eq true}">
				<fmt:message key="label.si" />
			</c:if>
			<c:if test="${mercatidavvisi_var.flagVerificato eq false }">
				<fmt:message key="label.no" />
			</c:if>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href="view.htm?codice=${mercatidavvisi_var.id.codice}"	title="<fmt:message key="label.edit.record" />&nbsp;${mercatidavvisi_var.id.codice}">
					<label><fmt:message key="label.edit.record.image" /></label>
				</a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

<script type="text/javascript">
				var _jmesaUrl='list.htm?posteggioId=${mercatiD.id.codice}&';
				var _captionTab='<fmt:message key="form.title.mercati_d_avvisi.list" />';
			</script></div>
<div id="functions">
<ul>

	<%-- <li><a href="javascript:historySet('${_urlback}','../alberoproctipisogback/create.htm?codiceAlberoproc=${alberoproc.id.codice}','')"><fmt:message key="button.new"/></a></li> --%>
	<li><a href="javascript:doHref('../mercatidavvisi/create.htm?posteggioId=${mercatiD.id.codice}','')"><fmt:message key="button.new"/></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>