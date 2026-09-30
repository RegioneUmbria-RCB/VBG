<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="attivita.label.lista_attivita.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="attivita.label.lista_attivita.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../attivita/list" />
</jsp:include>
<div id="subcontent">

<!-- Controlla se il codicesectore è popolato, se si sto visualizzando una lista di attività a partire da un settore
     quindi visulizzo il settore in uso -->

<c:if test="${codicesectore != null && codicesectore != '' }">

<div class="parametriDiv">
<div class="etichetta">
<div><fmt:message key="settori.label.settore" />:</div>
</div>
<div class="parametro">

<div><c:out value="${settori.settore}" /></div>
</div>
</div>

</c:if>
<form name="attivitaForm" action="list.htm"><jmesa:springTableFacade
	id="attivita_id" items="${attivitaList}" var="attivita_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore"
	filterMatcherMap="org.jmesa.custom.AttivitaFilterMatcherMap">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codiceistat" titleKey="label.codice"
				width="2%">
				<a
					href="view.htm?codice=${attivita_var.id.codiceistat}&codicesectore=${codicesectore}">${attivita_var.id.codiceistat}</a>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="istat" titleKey="attivita.label.istat" />
			<jmesa:htmlColumn property="settori.settore"
				titleKey="attivita.label.settori" />
			<jmesa:htmlColumn property="flagDisabilitato"
				cellEditor="org.jmesa.custom.SiNoCellEditor"
				filterEditor="org.jmesa.custom.SiNoDroplist"				
				titleKey="label.disabilitato" width="5%" />
			<jmesa:htmlColumn property="disabilitato" titleKey="label.disabilita"
				sortable="false" filterable="false" width="150">
				<input type="checkbox" value="${attivita_var.id.codiceistat}"
					name="chk_attivita"
					${attivita_var.flagDisabilitato?'checked':''} onclick="abilita(this, 'result_${attivita_var.id.codiceistat}')">
				<span id="result_${attivita_var.id.codiceistat}"
					style="display: none"></span>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="" titleKey="label.edit.record"
				sortable="false" filterable="false" width="5%">

				<a class="dettaglioColumn"
					href="view.htm?codice=${attivita_var.id.codiceistat}&codicesectore=${codicesectore}"
					title="<fmt:message key="label.edit.record" />&nbsp;${attivita_var.istat}">
				<label><fmt:message key="label.edit.record.image" /></label> </a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade>
<input type="hidden" value="${codicesectore}"
	name="codicesectore" /></form>
<script type="text/javascript">
			var _jmesaUrl='list.htm?codicesectore=${codicesectore}&';
			var _captionTab='<fmt:message key="attivita.label.lista_attivita.title" />';


			function abilita(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/attivita/ajaxAbilitaDisabilita.htm?codice='+escape(obj.value)+'&abilita='+obj.checked, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(id).innerHTML = transport.responseText;
						$(id).className='succes_ajax_call'
						$(id).style.display='';
						applyStyle();
						$(id).pulsate({ pulses: 2.0, duration: 1.0 });
						$(id).fade({    delay : 2.0, duration :1.0 });
				      },
					  onFailure: function(transport){ 
						$(id).innerHTML= transport.responseText;
						$(id).className='error_ajax_call'
						$(id).style.display='';
						applyStyle();
						$(id).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
			}
			
		</script></div>
<div id="functions">
<ul>
 
	<li><a
		href="javascript:doHref('create.htm?codicesectore=${codicesectore}','');"><fmt:message
		key="button.new" /></a></li>

	 <li><a
		href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>