<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
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
		
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${tipimovimento.id.tipomovimento}" /></div>
	    	  <div><c:out value="${tipimovimento.movimento }" /></div>
			</div>
	    </div>
		<br class="clear"/>
			
			<fieldset><legend><fmt:message key="form.parametristc.parametri" /></legend>
			<br class="clear" />
			<ul class="listaSchede">
				<li><a id="mappingScheda" class="<%=mappingSchedaClass%>" href="javascript:changeTab('TAB_MAPPING')"><fmt:message key="form.parametristc.parametri.tab1" /></a></li>
				<li><a id="altridatiScheda" class="<%=altridatiSchedaClass%>" href="javascript:changeTab('TAB_ALTRIDATI')"><fmt:message key="form.parametristc.parametri.tab2" /></a></li>	
				<li><a id="modelliScheda" class="<%=modelliSchedaClass%>" href="javascript:changeTab('TAB_MODELLI')"><fmt:message key="form.parametristc.parametri.tab3" /></a></li>
				<li><a id="alberoprocScheda" class="<%=alberoprocSchedaClass%>" href="javascript:changeTab('TAB_ALBEROPROC')"><fmt:message key="form.parametristc.parametri.tab4" /></a></li>
			</ul>
			<div id="mapping_div" style="<%=mappingStyle%>">
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="form.tipimovstcmapping.amministrazioni" /></td>
							<td><fmt:message key="form.tipimovstcmapping.codiceAttDest" /></td>
							<td><fmt:message key="form.tipimovstcmapping.descrizioneAttDest" /></td>
							<td><fmt:message key="form.tipimovstcmapping.flagRifpratStorica" /></td>
							<td><fmt:message key="form.tipimovstcmapping.flagNonInviareProcedimenti" /></td>
							<td><fmt:message key="label.flag_notificaa_automatica" /></td>
							<td><fmt:message key="label.flag_invia_schede_istanza" /></td>
							<td><fmt:message key="label.edit.record" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%
					    int i = 1;
					%>
					<c:forEach var="tipimovstcmapping_var" items="${tipiMovStcMaplist}"	varStatus="tipimovstcmappingStatus">
						<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
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
								<a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewMapping.htm?id=${tipimovstcmapping_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcmapping_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${tipimovstcmapping_var.id.codice}"/>
								</a>
							</td>
						</tr>
						<%
						    i++;
						%>
					</c:forEach>
					</tbody>
					</table>
			 </div>
			 <br />
			 <div id="functions">
					<ul>		
		 				<li><a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a></li>
						<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>
			</div>
			
			<div id="altridati_div" style="<%=altridatiStyle%>">
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td ><fmt:message key="form.tipimovstcaltridati.amministrazioni" /></td>
							<td ><fmt:message key="form.tipimovstcaltridati.nomeCampo" /></td>
							<td> <fmt:message key="form.tipimovstcaltridati.valoreDefaultCampo" /></td>
							<td ><fmt:message key="form.tipimovstcaltridati.etichetta" /></td>
							<td><fmt:message key="label.edit.record" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%
					    int j = 1;
					%>
					<c:forEach var="tipimovstcaltridati_var" items="${tipiMovStcAltriDatilist}"	varStatus="tipimovstcaltridatiStatus">
						<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
							<td>${tipimovstcaltridati_var.amministrazioni.amministrazione }</td>
							<td>${tipimovstcaltridati_var.nomeCampo }</td>
							<td>${tipimovstcaltridati_var.valoreDefaultCampo }</td>
							<td>${tipimovstcaltridati_var.etichetta }</td>
							<td>
								<a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewAltridati.htm?id=${tipimovstcaltridati_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcaltridati_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${tipimovstcaltridati_var.id.codice}"/>
								</a>
							</td>
						</tr>
						<%
						    j++;
						%>
					</c:forEach>
					</tbody>
					</table>
			 </div>	
			 <br />
			 <div id="functions">
					<ul>
		
		 				<li><a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a></li>
						<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>			
			</div>
			
			
			
			<div id="modelli_div" style="<%=modelliStyle%>">
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td ><fmt:message key="form.tipimovstcmapping.amministrazioni" /></td>
							<td ><fmt:message key="form.tipimovstcmodelli.modello" /></td>
							<td><fmt:message key="label.edit.record" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%
					    j = 1;
					%>
					<c:forEach var="tipimovstcmodelli_var" items="${tipimovStcModellilist}"	varStatus="tipimovstcmodelliStatus">
						<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
							<td>${tipimovstcmodelli_var.amministrazioni.amministrazione }</td>
							<td>${tipimovstcmodelli_var.dyn2Modellit.descrizione}</td>
							<td>
								<a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewModelli.htm?id=${tipimovstcmodelli_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','')" title="<fmt:message key="label.edit.record" /> ${tipimovstcmodelli_var_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${tipimovstcmodelli_var.id.codice}"/>
								</a>
							</td>
						</tr>
						<%
						    j++;
						%>
					</c:forEach>
					</tbody>
					</table>
			 </div>	
			 <br />
			 <div id="functions">
					<ul>
		
		 				<li><a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a></li>
						<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>			
			</div>
			
		<div id="alberoproc_div" style="<%=alberoprocStyle%>">
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td ><fmt:message key="form.tipimovstcmapping.amministrazioni" /></td>
							<td ><fmt:message key="form.tipimovstcalberoproc.fkScid" /></td>
							<td><fmt:message key="label.edit.record" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%
					    j = 1;
					%>
					<c:forEach var="tipimovstcalberoproc_var" items="${tipimovStcAlberoproclist}"	varStatus="tipimovstcalberoprocStatus">
						<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
							<td>${tipimovstcalberoproc_var.amministrazioni.amministrazione }</td>
							<td>${tipimovstcalberoproc_var.fkScid}</td>
							<td>
								<a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/viewAlberoproc.htm?id=${tipimovstcalberoproc_var.id.codice}&tipimovimento.idtipomovimento=${idtipomovimento}','');" title="<fmt:message key="label.edit.record" /> ${tipimovstcalberoproc_var_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${tipimovstcalberoproc_var.id.codice}"/>
								</a>
							</td>
						</tr>
						<%
						    j++;
						%>
					</c:forEach>
					</tbody>
					</table>
			 </div>	
			 <br />
			 <div id="functions">
					<ul>
		
		 				<li><a href="javascript:void 0" onclick="historySet('${_urlback }','../parametristc/createAlberoproc.htm?tipimovimento.idtipomovimento=${idtipomovimento}','');"><fmt:message key="button.new" /></a></li>
						<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>			
			</div>
			
						
				<br />
			</fieldset>
			<script type="text/javascript">
			if($('mappingScheda').className == "SchedaAttiva"){
				changeTab("TAB_MAPPING");
			}else if ($('altridatiScheda').className == "SchedaAttiva"){
				changeTab("TAB_ALTRIDATI");
			}else if ($('modelliScheda').className == "SchedaAttiva"){
				changeTab("TAB_MODELLI");
			}else{
				changeTab("TAB_ALBEROPROC");
			}

			function changeTab(tabId){
				if(tabId == "TAB_MAPPING"){
					$('mappingScheda').className = "SchedaAttiva";
					$('altridatiScheda').className = "Scheda";
					$('mapping_div').style.display = "block";
					$('altridati_div').style.display = "none";	
					$('modelliScheda').className = "Scheda";					
					$('modelli_div').style.display = "none";
					$('alberoprocScheda').className = "Scheda";					
					$('alberoproc_div').style.display = "none";					
				}else if(tabId == "TAB_ALTRIDATI"){
					$('mappingScheda').className = "Scheda";
					$('altridatiScheda').className = "SchedaAttiva";
					$('mapping_div').style.display = "none";
					$('altridati_div').style.display = "block";			
					$('modelliScheda').className = "Scheda";					
					$('modelli_div').style.display = "none";
					$('alberoprocScheda').className = "Scheda";					
					$('alberoproc_div').style.display = "none";					
				}else if(tabId == "TAB_MODELLI"){
					$('mappingScheda').className = "Scheda";
					$('mapping_div').style.display = "none";
					$('altridatiScheda').className = "Scheda";					
					$('altridati_div').style.display = "none";			
					$('modelliScheda').className = "SchedaAttiva";					
					$('modelli_div').style.display = "block";			
					$('alberoprocScheda').className = "Scheda";					
					$('alberoproc_div').style.display = "none";					
				} else {
					$('mappingScheda').className = "Scheda";
					$('mapping_div').style.display = "none";
					$('altridatiScheda').className = "Scheda";					
					$('altridati_div').style.display = "none";			
					$('modelliScheda').className = "Scheda";					
					$('modelli_div').style.display = "none";			
					$('alberoprocScheda').className = "SchedaAttiva";					
					$('alberoproc_div').style.display = "block";		
				}		
			}	
		   </script>

		</div>
	</body>
</html>