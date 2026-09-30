<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_verticalizzazioni_base" /></title>
	</head>
	<body>
	    <span class="titoloPagina"><fmt:message key="label.lista_verticalizzazioni_base" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
		   <jsp:param name="navmode" value="list"/>
	    </jsp:include>
	    <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../verticalizzazionibase/listregoleconfigurate" />
		</jsp:include>
		<div id="subcontent">
			<div class="parametriDiv">
		   		<div class="etichetta">
					<div><c:out value="${verticalizzazionibase.entity.modulo}" />:</div>
				</div>
				<div class="parametro">
					<div><c:out value="${verticalizzazionibase.entity.descrizione}" /></div>
			 	</div>
			</div>
			<br />
		 	<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="verticalizzazionibase" />
			</jsp:include>
		 	<div>
				<table class="vbg-table">
		            <thead>
		                <tr>
		                    <c:choose >
						        <c:when test="${hideComuni ne true }">
							       <th><fmt:message key="label.comune" /></th>
		                        </c:when>
						    </c:choose>
						    <th><fmt:message key="label.modulo" /></th>
						    <th><fmt:message key="label.descrizione_software" /></th>
		                    <th><fmt:message key="label.attivo" /></th>
		                    <th><fmt:message key="label.azioni" /></th>
		                </tr>
		            </thead>
		            <tbody class="tbody">
		                <%int j=1;%>
		                <c:forEach items="${verticalizzazionibase.entity.verticalizzazionis}" var="verticalizzazione">
		                    <tr>
		                        <c:if test="${hideComuni ne true }">
		                            <td>
		                                <c:choose>
		                                    <c:when test="${empty verticalizzazione.comune}">
		                                        <fmt:message key="label.tutti" />
		                                    </c:when>
		                                    <c:otherwise>
		                                        ${verticalizzazione.comune.comune}
		                                    </c:otherwise>
		                                </c:choose>
		                            </td>
		                        </c:if>
		                        <td>${verticalizzazione.software.codice}</td>
		                        <td>${verticalizzazione.software.descrizione}</td>
		                        <c:if test="${verticalizzazione.flagSoftwarePerAbilitatotransiet eq true}">
		                            <td>
		                                <input id="id_checkbox" type="checkbox" value="${verticalizzazione.attivo}" name="chk_attivita"
		                                    ${verticalizzazione.attivo==1?'checked':''} 
		                                    onclick="abilita(this, 'result_<%=j%>',jQuery('#codice_${verticalizzazione.id.codice}'));">
		                                <input type="hidden" id="codice_${verticalizzazione.id.codice}" value="${verticalizzazione.id.codice}">
						                <input type="hidden" id="modulo_${verticalizzazione.verticalizzazionibase.modulo}" value="${verticalizzazione.verticalizzazionibase.modulo}">
						                <input type="hidden" id="software_${verticalizzazione.software.codice }" value="${verticalizzazione.software.codice}">
							           <span id="result_<%=j%>" style="display: none"></span>
		                            </td>
		                        </c:if>
		                        <c:if test="${verticalizzazione.flagSoftwarePerAbilitatotransiet eq false}">
		                            <td>
		                                <input type="checkbox" value="${verticalizzazione.attivo}" disabled="true" ${verticalizzazione.attivo==1?'checked':''} title="<fmt:message key="label.operatore_non_abilitato_per_software" />" >
		                            </td>
		                        </c:if>
		                        <c:if test="${verticalizzazione.flagSoftwarePerAbilitatotransiet eq true}">
		                            <td>					
		                                <a class="dettaglioColumn" style="" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+escape('../verticalizzazionibase/listparametribase.htm?codice=${verticalizzazione.id.codice}'),'')" title="<fmt:message key="label.edit.record" />">
										    <label><fmt:message key="label.edit.record.image" /></label>
										</a>
		                                <a class="eliminaRiga" href="javascript: void(0)"; onclick="doHref('eliminaVerticalizzazioneComuneAndSoftware.htm?codice=${verticalizzazione.id.codice}','<fmt:message key="javascript.confirm.delete" />');" title="<fmt:message key="label.azioni" /> ${verticalizzazionibase.entity.modulo}">
		                                    <label><fmt:message key="label.azioni" /></label>
		                                </a> 					
		                            </td>
		                        </c:if>
		                        <c:if test="${verticalizzazione.flagSoftwarePerAbilitatotransiet eq false}">
						            <td></td>
		                        </c:if>
		                    </tr>
		                    <%j++; %>
		                </c:forEach>		
		            </tbody>
		        </table>
		    </div>
		</div>
        <br />
		<script type="text/javascript">
			function abilita(obj,id,codice){				
				new Ajax.Request('${pageContext.request.contextPath}/verticalizzazionibase/ajaxAbilitaDisabilita.htm?codice='+codice.val()+'&abilita='+obj.value, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(id).innerHTML = transport.responseText;
						$(id).className='success_header';
						$(id).style.display='';
						applyStyle();
						$(id).pulsate({ pulses: 2, duration: 1.0 });						
				      },
					  onFailure: function(transport){ 
						$(id).innerHTML= transport.responseText;
						$(id).className='error_header';
						$(id).style.display='';
						$(id).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
			}
	    </script>
		<c:if test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.NEW}">
			<spring-form:form commandName="verticalizzazionibase" name="inviodati">
			    <div class="vbg-form">
				    <fieldset>
				        <legend><fmt:message key="label.inserimento_nuovo_software_modulo"/>&nbsp;${verticalizzazionibase.entity.modulo}</legend>
				        <c:choose>
			                <c:when test="${hideComuni eq true }">
			                    <input type="hidden" name="verticalizzazioni.comune.codicecomune" value=""></input>
			                </c:when>
			                <c:otherwise>
			                    <div class="form-group">
			                        <label><fmt:message key="label.comune" /></label>
									<spring-form:select id="comune_id" path="verticalizzazioni.comune.codicecomune">
			                            <spring-form:option value=""><fmt:message key="label.tutti" /></spring-form:option>
			                            <spring-form:options items="${comuniList}" itemValue="comune.codicecomune" itemLabel="comune.comune" />
									</spring-form:select> 
									<spring-form:errors path="verticalizzazioni.comune.codicecomune" cssClass="error" />
			                    </div>
			                </c:otherwise>
			            </c:choose>
			            <div class="form-group">
			                <label><fmt:message key="label.modulo" /></label>
			                <spring-form:select id="software_id" path="verticalizzazioni.software.codice">
			                    <spring-form:options items="${softwareList}" itemValue="codice" itemLabel="descrizione" />
			                </spring-form:select> 
			                <spring-form:errors path="verticalizzazioni.software.codice" cssClass="error" />
			            </div>
			            <div class="form-group">
			                <label><fmt:message key="label.attivo" /></label>
			                <spring-form:checkbox id="attivo_id" path="verticalizzazioni.attivo" value="1" />
			            </div>
					</fieldset>
                </div>
			</spring-form:form>	
	    </c:if>
		<div class="form-button">
	        <c:if test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.NEW}">
	            <a class="btn btn-primary" href="javascript:doSubmit('insertVerticalizzazione.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.LIST}">
				<a class="btn btn-primary" href="javascript:doHref('createVerticalizzazione.htm?modulo=${verticalizzazionibase.entity.modulo}','');"><fmt:message key="button.new" /></a>
			</c:if>
			<c:if test="${verticalizzazionibase.displayMode != verticalizzazionibase.displayConstants.NEW}">
                <a class="btn btn-primary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+escape('../verticalizzazionibase/riepilogo.htm?codice=${verticalizzazionibase.entity.modulo}'),'')"><fmt:message key="label.riepilogo" /></a>
            </c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>