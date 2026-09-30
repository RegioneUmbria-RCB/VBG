<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentodoctipo.title" />
		</c:if> 
		<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentodoctipo.title" />
		</c:if> 
		<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipimovimento/createTipiDocumento" />
		<jsp:param name="qs" value="codiceMovimento%3d${tipimovimento.id.tipomovimento}%26software%3d${tipimovimento.software.codice}" />	
	</jsp:include>
	</c:if>
	<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipimovimento/createTipiDocumento" />
			<jsp:param name="qs" value="codiceMovimento%3d${tipimovimento.id.tipomovimento}%26codiceLettera%3d${tipimovimentodoctipo.id.codicelettera}%26software%3d${tipimovimento.software.codice}" />
		</jsp:include>	
	</c:if>
	
	<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${tipimovimento.id.tipomovimento}" /></div>
	    	  <div><c:out value="${tipimovimento.movimento}" /></div>
			</div>
	</div>
	<br class="clear"/>
	   <%
      String  swSettato1="display:none;";
      String  swTT1="display:inline;";
      %>
      <script type="text/javascript">      
       function tuttiSw(){
	       if($('id_flag1').checked){			
			    $('letteretipo_id1').style.display="inline";
			    $('letteretipo_id2').style.display="none";
			}else{
				$('letteretipo_id1').style.display="none";
				$('letteretipo_id2').style.display="inline";
			}
       }
       </script>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimentodoctipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimentodoctipo" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="tipimovimentodoctipo.label.letteretipo" />
					</td>
					<td>
					    <div id="letteretipo_id1" style="<%=swSettato1%>"><spring-form:input id="lettere_tipo_id1" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<div id="letteretipo_id2" style="<%=swTT1%>"><spring-form:input id="lettere_tipo_id2" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<spring-form:errors path="letteretipo" cssClass="error"/> 
						<spring-form:hidden id="lettere_tipo_hidden" path="letteretipo.id.codice"  />
					    <input type="checkbox" id="id_flag1" onclick="tuttiSw();"/>
		                <init:help idHelp="help1" textKey="help.letteretipo_archivi_base"/>
					</td>					
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
				<li><a href="javascript:doSubmit('insertDocumentoTipo.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
				<li><a href="javascript:doSubmit('deleteDocumentoTipo.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>