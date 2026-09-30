<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${forum.id.codice==null}">
			<fmt:message key="forum.label.nuovo_forum.title" />
		</c:if> 
		<c:if test="${forum.id.codice!=null}">
			<fmt:message key="forum.label.dettaglio_forum.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
	<c:if test="${forum.id.codice==null}">
		<fmt:message key="forum.label.nuovo_forum.title" />
	</c:if> 
	<c:if test="${forum.id.codice!=null}">
		<fmt:message key="forum.label.dettaglio_forum.title" />
	</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="forum" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="forum" />
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
						<fmt:message key="forum.label.moderato" />
					</td>
					<td>
						<spring-form:checkbox id="moderato_id" path="moderato" />
						<spring-form:errors path="moderato" cssClass="error" />
					</td>
				</tr>
			</table>	
			<script type='text/javascript'>
				$('descrizione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${forum.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${forum.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>			
	<br/><br/><br/>
	<c:if test="${forum.id.codice!=null and forum.moderato==true}">	
	
	<span class="titoloTabella"><fmt:message key="forum.label.lista_messaggi.table" /></span>
	<%-- <h2><fmt:message key="forum.label.lista_messaggi.table" /></h2>  --%>
	<form name="messaggiForm" action="view.htm">
			<jmesa:springTableFacade
				id="messaggi_id" 
				items="${forum.messaggis}" 
				var="messaggi_var"				
				exportTypes="" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.MessaggiFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>	
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" >
                        	<a onclick="dettaglioTabMessaggio(${messaggi_var.id.codice});">${messaggi_var.id.codice}</a>
                        	<span id="messaggio_dettaglio${messaggi_var.id.codice}" style="display: none; text-align: left;z-index: 1000;"></span>
                        </jmesa:htmlColumn>							
						<jmesa:htmlColumn property="oggetto" titleKey="messaggi.label.oggetto" width="52%" />
						<jmesa:htmlColumn property="autore" titleKey="messaggi.label.autore" width="20%" />
						<jmesa:htmlColumn property="dataMessaggio" titleKey="label.data" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataMessaggioMessaggiCustomFilter" width="10%"/>						
						<jmesa:htmlColumn property="autorizzato" titleKey="messaggi.label.autorizzato" 
								filterEditor="org.jmesa.custom.TrueFalseDroplist" width="16%">							
							<input type="checkbox" value="${messaggi_var.id.codice}" name="chk_messaggi" ${messaggi_var.autorizzato?'checked':''}
									onclick="abilita(this, 'result_${messaggi_var.id.codice}')">
							</input>							
							<span id="result_${messaggi_var.id.codice}" style="display: none"></span>							 
						</jmesa:htmlColumn>																				
					</jmesa:htmlRow>									
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
			<input type="hidden" value="${forum.id.codice}" name="codice"/>
			<script type="text/javascript">
				var _jmesaUrl='../messaggi/list.htm?codiceforum='+${forum.id.codice}+'&';
				var _captionTab='<fmt:message key="forum.label.list_messaggi.title" />';

						
				var tabMessaggio=false;
				function dettaglioTabMessaggio(obj){								
					if(tabMessaggio==false){
						new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioMessaggio.htm', {
							method: 'post',
							parameters: {codiceMessaggio: obj},
						  	onSuccess: function(transport){
								var response = transport.responseText;		
							  	$("messaggio_dettaglio"+obj).innerHTML = response;
							  	$("messaggio_dettaglio"+obj).appear();
							  	applyStyle();					  
						  	},
							onFailure: function(transport){ 
							  	var response = transport.responseText;
							    alert(response); }						    		 
						});
						tabMessaggio=true;
					}else{
						$("messaggio_dettaglio"+obj).fade();
						tabMessaggio=false;
					}		
					
				}			
				
				function chiudiTabMessaggio(obj){
					$("messaggio_dettaglio"+obj).fade();
					tabMessaggio=false;
					}	

				function abilita(obj, id){			
					new Ajax.Request('${pageContext.request.contextPath}/forum/ajaxAbilitaDisabilita.htm?codice='+escape(obj.value)+'&autorizzato='+obj.checked, {
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
		</form>	
	</c:if>
</body>
</html>