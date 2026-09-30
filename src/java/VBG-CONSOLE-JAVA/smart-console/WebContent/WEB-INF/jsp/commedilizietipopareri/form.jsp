<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizietipopareri.id.codice==null}">
			<fmt:message key="commedilizietipopareri.label.nuovo_commedilizietipopareri.title" />
		</c:if> 
		<c:if test="${commedilizietipopareri.id.codice!=null}">
			<fmt:message key="commedilizietipopareri.label.dettaglio_commedilizietipopareri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizietipopareri.id.codice==null}">
			<fmt:message key="commedilizietipopareri.label.nuovo_commedilizietipopareri.title" />
		</c:if> 
		<c:if test="${commedilizietipopareri.id.codice!=null}">
			<fmt:message key="commedilizietipopareri.label.dettaglio_commedilizietipopareri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
		
<script type="text/javascript">
	function tuttiSw(){
		if($('id_flag').checked){
		    $('id1').style.display="inline";
		    $('id2').style.display="none";
		}else
		{
			$('id1').style.display="none";
			$('id2').style.display="inline";
		}
		
	}	
</script>
   <%
      String  mittente="display:appear;";
      String  destinatario="display:appear;";
      String  swSettato="display:none;";
      String  swTT="display:inline;";
    %>
	<div id="subcontent">
		<spring-form:form commandName="commedilizietipopareri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizietipopareri" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tipomovimento" />
					</td>
					<td>
						<div id="id1" style="<%=swSettato%>"><spring-form:input id="tipimovimento_id1" path="tipimovimento.movimento" cssClass="searchbox" size="70" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="id2" style="<%=swTT%>"><spring-form:input id="tipimovimento_id2" path="tipimovimento.movimento" cssClass="searchbox" size="70" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimento" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_hidden" path="tipimovimento.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag" onclick="tuttiSw();"/>
		                <init:help idHelp="help1" textKey="tipologiaregistri.help.tipimovimenti_archivi_base"/>
		            </td>						
				</tr>
				<tr>
					<td>
						<fmt:message key="commedilizietipopareri.label.esito" />
					</td>
					<td>
						<spring-form:select path="esito">
							<spring-form:option value=""></spring-form:option>
							<spring-form:option value="true"><fmt:message key="label.positivo" /></spring-form:option>
							<spring-form:option value="false"><fmt:message key="label.negativo" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="esito" cssClass="error"/> 
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
				function convalida(){
					if($('tipimovimento_hidden').value==''){
						alert('<fmt:message key="label.tipomovimento" />: <fmt:message key="field.required" />');
						return false;
					}	
					return true;
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commedilizietipopareri.id.codice==null}">
				<li><a href="javascript:if(convalida()){doSubmit('insert.htm','',document.inviodati)}"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commedilizietipopareri.id.codice!=null}">
				<li><a href="javascript:if(convalida()){doSubmit('update.htm','',document.inviodati)}"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>