<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="settori.label.lista_settori.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="settori.label.lista_settori.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="settoriForm" action="list.htm">
				<jmesa:springTableFacade
					id="settori_id" 
					items="${settoriList}" 
					var="settori_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.SettoriFilterMatcherMap" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codicesettore" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${settori_var.id.codicesettore}">${settori_var.id.codicesettore}</a>
                         	</jmesa:htmlColumn>	
        					<jmesa:htmlColumn property="settore" titleKey="settori.label.settore" />	
							<jmesa:htmlColumn property="flagContamqattivita"
							                  cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterEditor="org.jmesa.custom.SiNoDroplist" 
                                              titleKey="settori.label.flag_contamq_attivita" width="5%" />
                            <jmesa:htmlColumn property="disabilitato" titleKey="label.disabilita" sortable="false" filterable="false" width="150">
                        	   <input  type="checkbox" value="${settori_var.id.codicesettore}" name="chk_settore" ${settori_var.flagDisabilitato?'checked':''} onclick="abilita(this, 'result_${settori_var.id.codicesettore}')"/>
                        	   <span id="result_${settori_var.id.codicesettore}" style="display: none"></span>							
                            </jmesa:htmlColumn>                                       
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="view.htm?codice=${settori_var.id.codicesettore}" title="<fmt:message key="label.edit.record" />&nbsp;${settori_var.settore}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="settori.label.lista_settori.title" />';


				function abilita(obj, id){			
					new Ajax.Request('${pageContext.request.contextPath}/settori/ajaxAbilitaDisabilita.htm?codice='+escape(obj.value)+'&abilita='+obj.checked, {
						  method: 'post',	
						  onSuccess: function(transport){
							$(id).innerHTML = transport.responseText;
							$(id).className='succes_ajax_call'
							$(id).style.display='';
							$(id).pulsate
								({ pulses: 2, 
								   duration: 2.0 
								});
							$(id).fade
							({ 
							   delay: 2, 
							   duration: 2.0 
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
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>