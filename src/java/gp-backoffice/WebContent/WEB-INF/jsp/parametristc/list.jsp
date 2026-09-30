<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.parametristc.title.list" /></title>
	</head>
	<body>
	 <%
	    String mappingStyle = "";
	    String altridatiStyle = "display: none";
	    String modelliStyle = "display: none";
	    String alberoprocStyle = "display: none";
	    String mappingSchedaClass = "";
	    String altridatiSchedaClass = "";
	    String modelliSchedaClass = "";
	    String alberoprocSchedaClass = "";
	    if (((String) request.getAttribute("VIEW_TAB")).equals("TAB_MAPPING")) {
			mappingSchedaClass = "SchedaAttiva";
			altridatiSchedaClass = "Scheda";
			modelliSchedaClass = "Scheda";
			alberoprocSchedaClass = "Scheda";
	    } else if (((String) request.getAttribute("VIEW_TAB")).equals("TAB_ALTRIDATI")) {
			mappingSchedaClass = "Scheda";
			altridatiSchedaClass = "SchedaAttiva";
			modelliSchedaClass = "Scheda";
			alberoprocSchedaClass = "Scheda";
	    } else if (((String) request.getAttribute("VIEW_TAB")).equals("TAB_MODELLI")) {
			mappingSchedaClass = "Scheda";
			altridatiSchedaClass = "Scheda";
			modelliSchedaClass = "SchedaAttiva";
			alberoprocSchedaClass = "Scheda";
	    }else{
			mappingSchedaClass = "Scheda";
			altridatiSchedaClass = "Scheda";
			modelliSchedaClass = "Scheda";
			alberoprocSchedaClass = "SchedaAttiva";
	    }
	%> 
		<span class="titoloPagina"><fmt:message key="form.parametristc.title.list" /></span>
			<jsp:include page="../includes/innerNavigation.jsp">
				<jsp:param name="navmode" value="list"/>
	        </jsp:include>
        	<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../parametristc/list" />
			</jsp:include>
			<div id="subcontent">
				<div class="vbg-form">
			
				<div class="parametriDiv">				
					<fieldset>
                        <legend><fmt:message key="label.tipomovimento" /></legend>
                        <div class="etichetta">
                            <div><fmt:message key="label.codice" />:</div>
                            <div><fmt:message key="label.tipomovimento" />:</div>
                        </div>
				    	<div class="parametro">
                            <div><c:out value="${tipimovimento.id.tipomovimento}" /></div>
                            <div><c:out value="${tipimovimento.movimento}" /></div>
						</div>
					</fieldset>
			    </div>
				<br class="clear"/>
				
				<fieldset>
					<legend><fmt:message key="form.parametristc.parametri" /></legend>
					<!-- <br class="clear" /> -->
					<ul class="listaSchede">
						<li><a id="mappingScheda" class="<%=mappingSchedaClass%>" href="javascript:changeTab('TAB_MAPPING')"><fmt:message key="form.parametristc.parametri.tab1" /></a></li>
						<li><a id="altridatiScheda" class="<%=altridatiSchedaClass%>" href="javascript:changeTab('TAB_ALTRIDATI')"><fmt:message key="form.parametristc.parametri.tab2" /></a></li>	
						<li><a id="modelliScheda" class="<%=modelliSchedaClass%>" href="javascript:changeTab('TAB_MODELLI')"><fmt:message key="form.parametristc.parametri.tab3" /></a></li>
						<li><a id="alberoprocScheda" class="<%=alberoprocSchedaClass%>" href="javascript:changeTab('TAB_ALBEROPROC')"><fmt:message key="form.parametristc.parametri.tab4" /></a></li>
					</ul>
					<div id="mapping_div" style="<%=mappingStyle%>">
						<table class="vbg-table">
							<thead>
								<tr class="header">
									<th><fmt:message key="form.tipimovstcmapping.amministrazioni" /></th>
									<th><fmt:message key="form.tipimovstcmapping.codiceAttDest" /></th>
									<th><fmt:message key="form.tipimovstcmapping.descrizioneAttDest" /></th>
									<th><fmt:message key="form.tipimovstcmapping.flagRifpratStorica" /></th>
									<th><fmt:message key="form.tipimovstcmapping.flagNonInviareProcedimenti" /></th>
									<th><fmt:message key="label.flag_notificaa_automatica" /></th>
									<th><fmt:message key="label.flag_invia_schede_istanza" /></th>
									<th><fmt:message key="label.edit.record" /></th>
								</tr>
							</thead>
							<tbody class="tbody">							
								<c:forEach var="tipimovstcmapping_var" items="${tipiMovStcMaplist}"	varStatus="tipimovstcmappingStatus">
									<tr>
										<td>${tipimovstcmapping_var.amministrazioni.amministrazione }</td>
										<td>${tipimovstcmapping_var.codiceAttDest }</td>
										<td>${tipimovstcmapping_var.descrizioneAttDest }</td>
										<td>
											<c:if test="${tipimovstcmapping_var.flagRifpratStorica eq true}">
										    	<fmt:message key="label.si" />
										    </c:if>
										    <c:if test="${tipimovstcmapping_var.flagRifpratStorica eq false}">
										    	<fmt:message key="label.no" />
										    </c:if>
										</td>   
										<td>
											<c:if test="${tipimovstcmapping_var.nonInviareProcedimenti eq true}">
										    	<fmt:message key="label.si" />
										    </c:if>
										    <c:if test="${tipimovstcmapping_var.nonInviareProcedimenti eq false}">
										    	<fmt:message key="label.no" />
										    </c:if>
										</td>   
										<td>
											<c:choose>
												<c:when test="${tipimovstcmapping_var.flagNotificaAutomatica eq 0 or empty tipimovstcmapping_var.flagNotificaAutomatica}">
												    	<fmt:message key="label.no" /> - <fmt:message key="label.flag_notifica_automatica.notifica_non_automatica" />
												 </c:when>   
												 <c:when test="${tipimovstcmapping_var.flagNotificaAutomatica eq 1 or empty tipimovstcmapping_var.flagNotificaAutomatica}">
												 	<fmt:message key="label.si" /> - <fmt:message key="label.flag_notifica_automatica.notifica_automatica" />
												 </c:when>
												 <c:when test="${tipimovstcmapping_var.flagNotificaAutomatica eq 2 or empty tipimovstcmapping_var.flagNotificaAutomatica}">
												 	<fmt:message key="label.si" /> - <fmt:message key="label.flag_notifica_automatica.notifica_automatica_inserimento" />
												 </c:when>							    
										    </c:choose>
										</td>
										<td>
											<c:if test="${tipimovstcmapping_var.flagInviaschedeistanza eq true}">
										    	<fmt:message key="label.si" />
										    </c:if>
										    <c:if test="${tipimovstcmapping_var.flagInviaschedeistanza eq false}">
										    	<fmt:message key="label.no" />
										    </c:if>
										</td>         
										<td>
											<a class="vbg-btn btn-modifica" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewMapping.htm?id=${tipimovstcmapping_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcmapping_var.id.codice}">
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<br />
						<div class="form-button">										
			 				<a class="btn btn-primary" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a>
							<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
						</div>
					</div>
				
					<div id="altridati_div" style="<%=altridatiStyle%>">
						<table class="vbg-table">
							<thead>
								<tr class="header">
									<th><fmt:message key="form.tipimovstcaltridati.amministrazioni" /></th>
									<th><fmt:message key="form.tipimovstcaltridati.nomeCampo" /></th>
									<th><fmt:message key="form.tipimovstcaltridati.valoreDefaultCampo" /></th>
									<th><fmt:message key="form.tipimovstcaltridati.etichetta" /></th>
									<th><fmt:message key="form.tipimovstcaltridati.helpText" /></th>
									<th><fmt:message key="form.tipimovstcmapping.flaghelp" /></th>
									<th><fmt:message key="label.edit.record" /></th>
								</tr>
							</thead>
							<tbody class="tbody">						
								<c:forEach var="tipimovstcaltridati_var" items="${tipiMovStcAltriDatilist}"	varStatus="tipimovstcaltridatiStatus">
									<tr>
										<td>${tipimovstcaltridati_var.amministrazioni.amministrazione }</td>
										<td>${tipimovstcaltridati_var.nomeCampo }</td>
										<td>${tipimovstcaltridati_var.valoreDefaultCampo }</td>
										<td>${tipimovstcaltridati_var.etichetta }</td>
										<td>${tipimovstcaltridati_var.helpText }</td>
										<td>
											<c:choose>
												<c:when test="${tipimovstcaltridati_var.flagHelp eq true}">
													<fmt:message key="label.si" />
												</c:when>
												<c:otherwise>
													<fmt:message key="label.no" />
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<a class="vbg-btn btn-modifica" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewAltridati.htm?id=${tipimovstcaltridati_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcaltridati_var.id.codice}">
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>				 		
						<br />
						<div class="form-button">
							<a class="btn btn-primary" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a>
							<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
						</div>		
					</div>
				
					<div id="modelli_div" style="<%=modelliStyle%>">				
						<table class="vbg-table">
							<thead>
								<tr class="header">
									<th><fmt:message key="form.tipimovstcmapping.amministrazioni" /></th>
									<th><fmt:message key="form.tipimovstcmodelli.modello" /></th>
									<th><fmt:message key="label.edit.record" /></th>
								</tr>
							</thead>
							<tbody class="tbody">							
								<c:forEach var="tipimovstcmodelli_var" items="${tipimovStcModellilist}"	varStatus="tipimovstcmodelliStatus">
									<tr>
										<td>${tipimovstcmodelli_var.amministrazioni.amministrazione }</td>
										<td>${tipimovstcmodelli_var.dyn2Modellit.descrizione}</td>
										<td>
											<a class="vbg-btn btn-modifica" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewModelli.htm?id=${tipimovstcmodelli_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','')" title="<fmt:message key="label.edit.record" /> ${tipimovstcmodelli_var_var.id.codice}">
											</a>
										</td>
									</tr>									
								</c:forEach>
							</tbody>
						</table>			
						<br />
						<div class="form-button">
			 				<a class="btn btn-primary" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a>
							<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
						</div>			
					</div>
				
					<div id="alberoproc_div" style="<%=alberoprocStyle%>">					
						<table class="vbg-table">
							<thead>
								<tr class="header">
									<th><fmt:message key="form.tipimovstcmapping.amministrazioni" /></th>
									<th><fmt:message key="form.tipimovstcalberoproc.fkScid" /></th>
									<th><fmt:message key="label.edit.record" /></th>
								</tr>
							</thead>
							<tbody class="tbody">							
								<c:forEach var="tipimovstcalberoproc_var" items="${tipimovStcAlberoproclist}"	varStatus="tipimovstcalberoprocStatus">
								<tr>
									<td>${tipimovstcalberoproc_var.amministrazioni.amministrazione }</td>
									<td>${tipimovstcalberoproc_var.fkScid}</td>
									<td>
										<a class="vbg-btn btn-modifica" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewAlberoproc.htm?id=${tipimovstcalberoproc_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcalberoproc_var_var.id.codice}">
										</a>
									</td>
								</tr>								
								</c:forEach>
							</tbody>
						</table>					
						<br />
						<div class="form-button">
			 				<a class="btn btn-primary" href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createAlberoproc.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a>
							<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
						</div>			
					</div>							
					<br />
				</fieldset>
				<script type="text/javascript">
				
					if(document.querySelector('#mappingScheda').className == "SchedaAttiva"){
						changeTab("TAB_MAPPING");
					}else if (document.querySelector('#altridatiScheda').className == "SchedaAttiva"){
						changeTab("TAB_ALTRIDATI");
					}else if (document.querySelector('#modelliScheda').className == "SchedaAttiva"){
						changeTab("TAB_MODELLI");
					}else{
						changeTab("TAB_ALBEROPROC");
					}
	
				function changeTab(tabId){
					if(tabId == "TAB_MAPPING"){
						document.querySelector('#mappingScheda').className = "SchedaAttiva";
						document.querySelector('#altridatiScheda').className = "Scheda";
						document.querySelector('#mapping_div').style.display = "block";
						document.querySelector('#altridati_div').style.display = "none";	
						document.querySelector('#modelliScheda').className = "Scheda";					
						document.querySelector('#modelli_div').style.display = "none";
						document.querySelector('#alberoprocScheda').className = "Scheda";					
						document.querySelector('#alberoproc_div').style.display = "none";					
					}else if(tabId == "TAB_ALTRIDATI"){
						document.querySelector('#mappingScheda').className = "Scheda";
						document.querySelector('#altridatiScheda').className = "SchedaAttiva";
						document.querySelector('#mapping_div').style.display = "none";
						document.querySelector('#altridati_div').style.display = "block";			
						document.querySelector('#modelliScheda').className = "Scheda";					
						document.querySelector('#modelli_div').style.display = "none";
						document.querySelector('#alberoprocScheda').className = "Scheda";					
						document.querySelector('#alberoproc_div').style.display = "none";					
					}else if(tabId == "TAB_MODELLI"){
						document.querySelector('#mappingScheda').className = "Scheda";
						document.querySelector('#mapping_div').style.display = "none";
						document.querySelector('#altridatiScheda').className = "Scheda";					
						document.querySelector('#altridati_div').style.display = "none";			
						document.querySelector('#modelliScheda').className = "SchedaAttiva";					
						document.querySelector('#modelli_div').style.display = "block";			
						document.querySelector('#alberoprocScheda').className = "Scheda";					
						document.querySelector('#alberoproc_div').style.display = "none";					
					} else {
						document.querySelector('#mappingScheda').className = "Scheda";
						document.querySelector('#mapping_div').style.display = "none";
						document.querySelector('#altridatiScheda').className = "Scheda";					
						document.querySelector('#altridati_div').style.display = "none";			
						document.querySelector('#modelliScheda').className = "Scheda";					
						document.querySelector('#modelli_div').style.display = "none";			
						document.querySelector('#alberoprocScheda').className = "SchedaAttiva";					
						document.querySelector('#alberoproc_div').style.display = "block";		
					}		
				}	
			   </script>
				</div>
			</div>
	</body>
</html>