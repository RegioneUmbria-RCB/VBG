<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.pannello_di_amministrazione" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.pannello_di_amministrazione" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="softwaresForm" action="listSoftwareAttivi.htm">
			<jmesa:springTableFacade
				id="softwares_id" 
				items="${softwaresList}" 
				var="softwares_var"
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="codice" titleKey="label.codice" width="2%" filterable="false">
                           	<a href="view.htm?codice=${softwares_var.codice}">${softwares_var.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="softwares.label.descrizione.table" filterable="false"/>						
						<jmesa:htmlColumn property="attivo" titleKey="label.attivo" width="16%" filterable="false">
							<input type="checkbox" value="${softwares_var.codice}" name="chk_attivo" ${softwares_var.attivo?'checked':''} onclick="attiva(this, '${softwares_var.codice}')"></input>		
							<span id="result_${softwares_var.codice}" style="display: none"></span>					
						</jmesa:htmlColumn>						
						<c:set var="_disabled" value="" />				
						<c:if test="${!softwares_var.attivo}">
							<c:set var="_disabled" value="disabled" />
						</c:if>						
						<jmesa:htmlColumn property="attivoFo" titleKey="label.attivo_frontoffice" width="16%" filterable="false">
							<input id="FO_${softwares_var.codice}" ${_disabled} type="checkbox" value="${softwares_var.codice}" name="chk_attivoFo" ${softwares_var.attivoFo?'checked':''} onclick="attivaFO(this, 'result_FO_${softwares_var.codice}')"></input>
							<span id="result_FO_${softwares_var.codice}" style="display: none"></span>							
						</jmesa:htmlColumn>								
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>			 
		</form>
		
		<script type="text/javascript">
			var _jmesaUrl='listSoftwareAttivi.htm?';
			var _captionTab='<fmt:message key="label.pannello_di_amministrazione" />';
		
			function attiva(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/admin/ajaxAttivaDisattiva.htm?codice='+escape(obj.value)+'&attivo='+obj.checked, {
						method: 'post',	
						onSuccess: function(transport){
						  $("result_"+id).innerHTML = transport.responseText;
						  $("result_"+id).className='success_header';
						  $("result_"+id).style.display='';
						  $("result_"+id).pulsate();
						  $("result_"+id).fade();			
						  if(obj.checked){
							  $("FO_"+id).removeAttribute("disabled");  
						  }else{
							  $("FO_"+id).checked = false;
							  $("FO_"+id).disabled = true;
						  }						  
						},
						onFailure: function(transport){ 
						  $("result_"+id).innerHTML= transport.responseText;
						  $("result_"+id).className='error_header';
						  $("result_"+id).style.display='';
						  $("result_"+id).pulsate();
						  $("result_"+id).fade();	
						}
					});
				}
			
			function attivaFO(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/admin/ajaxAttivaDisattivaFO.htm?codice='+escape(obj.value)+'&attivo='+obj.checked, {
						method: 'post',	
						onSuccess: function(transport){
						  $(id).innerHTML = transport.responseText;
						  $(id).className='success_header';
						  $(id).style.display='';
						  $(id).pulsate();
						  $(id).fade();					
						},
						onFailure: function(transport){ 
						  $(id).innerHTML= transport.responseText;
						  $(id).className='error_header';
						  $(id).style.display='';
						  $(id).pulsate();
						  $(id).fade();			
						}
					});
				}			
		
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../admin/view.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>