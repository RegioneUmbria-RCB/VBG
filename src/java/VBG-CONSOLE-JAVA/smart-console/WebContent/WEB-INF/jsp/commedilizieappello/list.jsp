<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_convocati" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_convocati" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.numero_commissione" />:</div>
			<div><fmt:message key="label.descrizione" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${commissioniedilizieT.numprotocollo}</div>
			<div>${commissioniedilizieT.descrizione}</div>
		</div>
	</div>
		<form name="commedilizieappelloForm" action="list.htm">
			<jmesa:springTableFacade
				id="commedilizieappello_id" 
				items="${commedilizieappelloList}" 
				var="commedilizieappello_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.CommedilizieappelloFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${commedilizieappello_var.id.codice}">${commedilizieappello_var.id.codice}</a>
                        </jmesa:htmlColumn>		
                        <jmesa:htmlColumn property="componente" titleKey="label.componente"/>
						<jmesa:htmlColumn property="commedilizieCarica.descrizione" titleKey="label.carica" />
						<jmesa:htmlColumn property="presente" titleKey="label.presente" filterEditor="org.jmesa.custom.TrueFalseDroplist" width="150">
                        	   <input  type="checkbox" value="${commedilizieappello_var.id.codice}" name="chk_convocato" ${commedilizieappello_var.presente?'checked':''} onclick="abilita(this, 'result_${commedilizieappello_var.id.codice}')"/>
                        	   <span id="result_${commedilizieappello_var.id.codice}" style="display: none"></span>							
                        </jmesa:htmlColumn>
						<jmesa:htmlColumn property="dettaglio" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${commedilizieappello_var.id.codice}" title="<fmt:message key="label.edit.record" />${commedilizieappello_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${commissioniedilizieT.id.codice}" name="codiceCommissione"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceCommissione=${commissioniedilizieT.id.codice}&';
			var _captionTab='<fmt:message key="label.appello_iniziale" />';
			
			
			function abilita(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/commedilizieappello/ajaxAbilitaDisabilita.htm?codice='+escape(obj.value)+'&abilita='+obj.checked, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(id).innerHTML = transport.responseText;
						$(id).className='succes_ajax_call'
						$(id).style.display='';
						$(id).pulsate
						({
							pulses : 2,
							duration : 2.0
						});
						$(id).fade(
						{
							delay    : 2.0,
							duration : 1.0
						});
				      },
					  onFailure: function(transport){ 
						$(id).innerHTML= transport.responseText;
						$(id).className='error_ajax_call'
						$(id).style.display='';
						$(id).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
			}
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceCommissione=${commissioniedilizieT.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>