<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="quesiti.label.lista_quesiti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="quesiti.label.lista_quesiti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="quesitiForm" action="list.htm">
			<jmesa:springTableFacade
				id="quesiti_id" 
				items="${quesitiList}" 
				var="quesiti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.QuesitiFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${quesiti_var.id.codice}">${quesiti_var.id.codice}</a>
                        </jmesa:htmlColumn>
						<jmesa:htmlColumn property="data" titleKey="label.data" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataQuesitiCustomFilter" width="10%"/>
						<jmesa:htmlColumn property="quesito" titleKey="quesiti.label.quesito" width="36%"/>
						<jmesa:htmlColumn property="nominativo" titleKey="quesiti.label.nominativo" width="20%" />
						<jmesa:htmlColumn property="software.descrizione" titleKey="label.tipo" width="15%"/>
						<jmesa:htmlColumn property="letto" titleKey="quesiti.label.letto" filterEditor="org.jmesa.custom.TrueFalseDroplist" width="16%">							
							<input type="checkbox" value="${quesiti_var.id.codice}" name="chk_quesiti" ${quesiti_var.letto?'checked':''} onclick="abilita(this, 'result_${quesiti_var.id.codice}')"></input>							
							<span id="result_${quesiti_var.id.codice}" style="display: none"></span>							 
						</jmesa:htmlColumn>				
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${quesiti_var.id.codice}" title="<fmt:message key="label.edit.record" />${quesiti_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="quesiti.label.lista_quesiti.title" />';


						
			function abilita(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/quesiti/ajaxAbilitaDisabilita.htm?codice='+escape(obj.value)+'&letto='+obj.checked, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(id).innerHTML = transport.responseText;
						$(id).className='success_header'
						$(id).style.display='';
						$(id).pulsate({ pulses: 2, duration: 1.0 });						
				      },
					  onFailure: function(transport){ 
						$(id).innerHTML= transport.responseText;
						$(id).className='error_header'
						$(id).style.display='';
						$(id).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
			}			
		</script>
	</div>
	<div id="functions">
		<ul>			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>