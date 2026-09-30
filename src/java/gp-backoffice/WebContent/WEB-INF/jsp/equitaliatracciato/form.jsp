<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.tracciato_450.title" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message key="label.tracciato_450.title" /></span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../equitaliatracciato/view" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
<jsp:include page="../includes/displayGlobalMessages.jsp">
	<jsp:param name="commandName" value="equitaliatracciatoCommand" />
</jsp:include>	

<form name="inviodati">

<input type="hidden" name="codice" value="${equitaliatracciato.id.codice}"/>
	<table border="0" width="100%">
		<tr>
			<td width="15%"><fmt:message key="label.data_creazione" /></td>
			<td>
				<input type="text" name="dataTracciato" id="dataTracciato_id" size="10" 
				value="<fmt:formatDate value="${equitaliatracciato.dataCreazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" />
			<init:calendar
								imagePath="/images/cal.gif" idImage="calData" idInput="dataTracciato_id"
								textKey="label.calendar" />
			<a id="mod_data_id"  
				class="vbg-btn btn-salva"
				href="javascript:salvaData()" title="<fmt:message key="label.salva" />"></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.anno" /></td>
			<td>${equitaliatracciato.anno}</td>
		</tr>
		<tr>
			<td><fmt:message key="label.progressivo_anno" /></td>
			<td>${equitaliatracciato.progressivoAnno}</td>
		</tr>
		<tr>
			<td><fmt:message key="label.creato_da" /></td>
			<td>${equitaliatracciato.responsabili.responsabile}</td>
		</tr>
		<tr>
			<td><fmt:message key="label.tracciato" /></td>
			<td>
			
						<jsp:include page="../includes/oggetti.jsp" >	
		       				<jsp:param name="idElemento" value="oggettoIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${equitaliatracciato.oggetti.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
		   					<jsp:param name="parametroLogOperazione" value="1" />
		   				</jsp:include>
	    				<input type="hidden" name="entity.oggetto.id.codice" id="oggetto_id_codice"/>
	    				<input type="hidden" name="entity.oggetto.nomefile" id="oggetto_nomefile"/>
	    		<%--
				<a class="visualizzaDocColumn" href="../file/ajaxDownload.htm?fileId=${equitaliatracciato.oggetti.id.codice}"  title="<fmt:message key="label.visualizza" /> ">
					<label><fmt:message key="label.visualizza.image" /></label>
				</a>
				 --%>
			</td>
		</tr>
	</table>
</form>	
	<c:if test="${not empty resultRowLenth}">
	<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="25%">Tipo record </td>
					<td ><fmt:message key="label.lunghezza" /></td>
	            </tr>
			</thead>
			<tbody class="tbody">
			<%int j=1;%>
			
			<c:forEach var="map" items="${resultRowLenth}"> 
					<tr class="<%=(j%2)==0?"odd":"even"%>">
      				<td>${map.key} </td>
      				<fmt:parseNumber var = "v" type = "number" value = "${map.value}" />
      				<c:choose>
      					<c:when test="${v==450}"><td>${v}</td></c:when>
      					<c:when test="${v>450 || v<450}"><td style="color:#ba0000"><b>${v} [Attenzione la lunghezza di ogni record deve essere di 450 caratteri]</b></td></c:when>
      				</c:choose>
                   </tr>
				<%j++; %>
			</c:forEach>
			</tbody>
		</table>
	</div>
	</c:if>
	
	<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="label.istanza" /> </td>
					<td ><init:editLabel key="label.richiedente" role="ROLE_EDITLABEL" /></td>
	            </tr>
			</thead>
			<tbody class="tbody">
			<%int l=1;%>
			<c:forEach items="${equitaliatracciato.equitaliatracciatoDs}" var="equitalia_d_var">
			<tr class="<%=(l%2)==0?"odd":"even"%>">
			      <td>${equitalia_d_var.istanze.numeroistanza} </td>
				  <td>${equitalia_d_var.istanze.richiedente.descrizioneRichiedente}</td>
	            </tr>
			<%l++; %>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">

salvaData = function(){
	document.inviodati.action="aggiornaDataTracciato.htm";
	document.inviodati.submit();
	
	
}
</script>
<div id="functions">
<ul>
    <li><a href="javascript:doHref('checkfile.htm?codice=${equitaliatracciato.id.codice}','')"><fmt:message key="button.chek_file" /></a></li>
    <li><a href="javascript:doHref('delete.htm?codice=${equitaliatracciato.id.codice}','<fmt:message key="javascript.confirm.delete" />')"><fmt:message key="button.delete" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>