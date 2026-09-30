<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${protocolloregistri.id.codice==null}">
			<fmt:message key="tipologiaregistri.label.nuovo_protocollo_registri.title" />
		</c:if> 
		<c:if test="${protocolloregistri.id.codice!=null}">
			<fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${protocolloregistri.id.codice==null}">
			<fmt:message key="tipologiaregistri.label.nuovo_protocollo_registri.title" />
		</c:if> 
		<c:if test="${protocolloregistri.id.codice!=null}">
			<fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
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
	<spring-form:form commandName="protocolloregistri" name="inviodati">
		<input type="hidden" name="codiceRegistro" value="${codiceRegistro}" />
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="protocolloregistri" />
	    </jsp:include>
		<div class="vbg-form">				
	        <div class="form-group">
				<label><b><fmt:message key="label.comune" /></b></label>							
				<spring-form:hidden  id="comune_id" path="comune.codicecomune" />
				<c:if test="${protocolloregistri.comune != null}">
					<c:if test="${protocolloregistri.comune.codicecomune == null}">	
						<fmt:message key="label.tutti" />
					</c:if>
					<c:if test="${protocolloregistri.comune.codicecomune != null}">
						${protocolloregistri.comune.comune}
					</c:if>
				</c:if>
			</div>
			<c:choose>
				<c:when test="${not empty listaDocumento}">
			 		<div class="form-group">
				        <label><fmt:message key="tipologiaregistri.label.tipodocumento" /></label>
						<spring-form:select id="tipodocumento_id" path="idtipodocumento">
							<spring-form:options items="${listaDocumento}" itemValue="codice" itemLabel="descrizione"  />
						</spring-form:select> 
						<spring-form:errors path="idtipodocumento" cssClass="error"/>
			        </div>
		        </c:when>
		        <c:otherwise>
		        	<div class="form-group">
				        <label><fmt:message key="tipologiaregistri.label.tipodocumento" /></label>
						<spring-form:input id="tipodocumento_id" path="idtipodocumento" />
		       		</div>		        	
		        </c:otherwise>
		   </c:choose>        		
			<div class="form-group"> 
				<label><fmt:message key="label.tipimovimento" /></label>
				<div id="id1" style="<%=swSettato%>"><spring-form:input id="tipimovimento_id1" path="tipimovimento.movimento" cssClass="searchbox" size="80" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
				<div id="id2" style="<%=swTT%>"><spring-form:input id="tipimovimento_id2" path="tipimovimento.movimento" cssClass="searchbox" size="80" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
				<spring-form:errors path="tipimovimento" cssClass="error"/> 
				<spring-form:hidden id="tipimovimento_hidden" path="tipimovimento.id.tipomovimento"  />
			    <input type="checkbox" id="id_flag" onclick="tuttiSw();"/>
				<init:help idHelp="help1" textKey="tipologiaregistri.help.tipimovimenti_archivi_base"/>			          
			</div>       
	        <div class="form-group">
				<label><fmt:message key="label.mailtipo" /></label>
				<spring-form:input id="mailtipo_id" path="mailtipo.descrizione" cssClass="searchbox" size="80" onchange="checkValue(this,'mailtipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findMailtipo.htm"  idHidden="mailtipo_hidden"  idInput="mailtipo_id" inputTitleKey="label.ricerca_mailtipo"></init:autocompleter>
				<spring-form:errors path="mailtipo" cssClass="error"/> 
				<spring-form:hidden id="mailtipo_hidden" path="mailtipo.id.codice"  />
			</div>
	        <div class="form-group">
	        	<label><fmt:message key="tipologiaregistri.label.classifica" /></label>		
	        	<c:if test="${not empty  listaClassifiche  }">
					<spring-form:select id="classifica_id" path="classifica">
						<spring-form:options items="${listaClassifiche}" itemValue="codice" itemLabel="descrizione"  />
					</spring-form:select> 
				</c:if>
				<c:if test="${empty listaClassifiche }">
					<spring-form:input id="classifica_id" path="classifica" size="70" />
				</c:if>				
				<spring-form:errors path="classifica" cssClass="error"/> 
	        </div>
	        <div class="form-group">
	        	<label><fmt:message key="tipologiaregistri.label.protocollo_flusso" /></label>
				<spring-form:select id="protocolloflusso_id" path="protocolloFlusso.codice" onchange="amministrazioniMittDest();">
					<spring-form:options items="${listaflussi}" itemValue="codice" itemLabel="descrizione"  />
				</spring-form:select> 
				<spring-form:errors path="protocolloFlusso" cssClass="error" />
	        </div>
	        <div class="form-group" id="mittente" style="<%=mittente%>">
				<label><fmt:message key="tipologiaregistri.label.amministrazione_mittente" /></label>

				<script type="text/javascript">
					function getComune(element, entry) { 
						if(document.getElementById("comune_id")){
							return entry + "&codiceComune=" + document.getElementById("comune_id").value;
						}else{
							return entry + "&codiceComune=";
						}
					}
				</script>
			
				<spring-form:input id="mittente_id" path="mittente.amministrazione" cssClass="searchbox" size="80" onchange="checkValue(this,'mittente_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findAmministrazioniForProtocolloRegistri.htm" callBack="getComune" idHidden="mittente_hidden"  idInput="mittente_id" inputTitleKey="label.ricerca_amministrazione"></init:autocompleter>
				<spring-form:errors path="mittente" cssClass="error"/> 
				<spring-form:hidden id="mittente_hidden" path="mittente.id.codice"  />

			</div>
	        <div class="form-group" id="destinatario" style="<%=destinatario%>">
				<label><fmt:message key="tipologiaregistri.label.amministrazione_destinataria" /></label>
				<spring-form:input id="destinataria_id" path="destinatario.amministrazione" cssClass="searchbox" size="80" onchange="checkValue(this,'destinataria_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findAmministrazioniForProtocolloRegistri.htm" callBack="getComune"  idHidden="destinataria_hidden"  idInput="destinataria_id" inputTitleKey="label.ricerca_amministrazione"></init:autocompleter>
				<spring-form:errors path="destinatario" cssClass="error"/> 
				<spring-form:hidden id="destinataria_hidden" path="destinatario.id.codice"  />
			</div>
		</div>

		<script type='text/javascript'>
	
		if($('tipodocumento_id')){
			$('tipodocumento_id').focus();
		}
		
	    amministrazioniMittDest();
	    function amministrazioniMittDest()
			{
				var idprotocolloFlusso=document.getElementById("protocolloflusso_id").value;
				if(idprotocolloFlusso=='P')
				{
					//$("mittente").fade();
					$("destinatario").fade();
				}
				if(idprotocolloFlusso=='I')
				{
					$("mittente").appear();
					$("destinatario").appear();
				}
			}
		</script>	     
	</spring-form:form>
	</div>
	<div class="form-button">

		<c:if test="${protocolloregistri.id.codice==null}">
			<a class="btn btn-primary" href="javascript:doSubmit('insertProtocolloRegistri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${protocolloregistri.id.codice!=null}">
			<a class="btn btn-primary" href="javascript:doSubmit('updateProtocolloRegistri.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('deleteProtocolloRegistri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:historyBack('')"><fmt:message key="button.back" /></a>

	</div>
</body>
</html>