<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.MappatureCommand"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mappature.displayMode==mappature.displayConstants.SEARCH}">
			<fmt:message key="mappature.label.gestione_mappature.title" />
		</c:if> 
		<c:if test="${mappature.displayMode==mappature.displayConstants.VIEW}">
			<fmt:message key="mappature.label.dettaglio_mappature.title" />
		</c:if>
	</title>
	<script type='text/javascript'>
        vbg.ready(() => {
            let regole = document.querySelectorAll('tr.mappature');
            regole.forEach((el) => {
                let campoindex = el.dataset.campoIndex;
                let mapindex = el.dataset.mappaturaIndex;
                let tiporegola = el.querySelector('select').value;
                
                mostraconfronto(campoindex,mapindex,tiporegola);
            });
            
            
        	$('nometagpeople_id').focus();
	    });
        
        function cleartagscheda(inputField,listItem) {
            var a = listItem.id;
            document.getElementById('nometagpeople_id').value = inputField.value;
            document.getElementById('nometagpeople_hidden').value = a;
            $('nometagpeople_id_choices').fade();
            clearField('dyn2Modellit_id', 'dyn2Modellit_hidden');
        }
        function filtertagpeoplesoftware(element, entry) {  
            if(document.getElementById("nometagpeople_id")!=null){
                return entry + "&nometagpeople=" + document.getElementById("nometagpeople_id").value;
            }
            if(document.getElementById("nometagpeople_id")==null){
                return entry;
            }
        }   
        function viewScheda(inputField,listItem) {
            var a = listItem.id;
            document.getElementById('dyn2Modellit_id').value = inputField.value;
            document.getElementById('dyn2Modellit_hidden').value = a;
            $('dyn2Modellit_id_choices').fade();
            view();
        }
        function view(){
            if(document.getElementById("dyn2Modellit_hidden").value!=null && document.getElementById("dyn2Modellit_hidden").value!=""){
                codiceScheda = document.getElementById("dyn2Modellit_hidden").value;
                nometagpeople = document.getElementById("nometagpeople_id").value;
                doHref('view.htm?codiceScheda='+ codiceScheda +'&nometagpeople='+nometagpeople+'','');
            }else{
                vis_errore(document.getElementById("dyn2Modellit_id"),"Scheda non Specificata");
            }
        }
        function mostraconfronto(campoindex,mapindex,tiporegola){
        	let display = (tiporegola==2 || tiporegola==3) ? '' : 'none';
        	
        	let valoreConfronto = document.getElementById('valoreconfronto_id'+campoindex+'map'+mapindex);
            let valoreDecodifica = document.getElementById('valoredecodifica_id'+campoindex+'map'+mapindex);
            let aggiungi = document.getElementById('aggiungi_id'+campoindex+'map'+mapindex);
        	
            valoreConfronto.style.display = display;
            valoreDecodifica.style.display = display;
            aggiungi.style.display = display;
            
            if(display === 'none'){
            	valoreConfronto.value = "";
            	valoreDecodifica.value = "";
            }
        }                   
    
        function dettaglioTabAltreSchede(divId, campo, scheda, conMappatureConfigurate){
            dijit.byId(divId).show();
            dettaglioTabSchede(campo, scheda, conMappatureConfigurate);
        }

        function dettaglioTabSchede(codiceCampo, codiceScheda, conMappatureConfigurate) {
                    
            new Ajax.Request('${pageContext.request.contextPath}/ajax/dettaglioAltreSchede.htm?codiceCampo='+ 
                    codiceCampo+ '&codiceScheda='+ codiceScheda + '&conMappatureConfigurate='+ conMappatureConfigurate,{
                    method : 'post',
                    onSuccess : function(transport) {                           
                        var response = transport.responseText;      
                        if(conMappatureConfigurate=='true'){
                            $("dettaglioMappature" + codiceCampo).innerHTML = response;
                        }else{
                            $("dettaglioSchede" + codiceCampo).innerHTML = response;    
                        }
                        
                        console.log(response);
                        
                        applyStyle();       
                    },
                    onFailure : function(transport) {
                        var response = transport.responseText;
                        alert(response);
                    }
            });
        }
	</script>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mappature.displayMode==mappature.displayConstants.SEARCH}">
			<fmt:message key="mappature.label.gestione_mappature.title" />
		</c:if> 
		<c:if test="${mappature.displayMode==mappature.displayConstants.VIEW}">
			<fmt:message key="mappature.label.dettaglio_mappature.title" />
		</c:if>
	</span>
	<c:if test="${mappature.displayMode==mappature.displayConstants.SEARCH}">
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="search"/>
		</jsp:include>
	</c:if>
	<c:if test="${mappature.displayMode==mappature.displayConstants.VIEW}">
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
	</c:if>
	<div id="subcontent">
	   <div class="vbg-form">
			<spring-form:form commandName="mappature" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="mappature" />
			    </jsp:include>
			    <fieldset>
			      <legend><fmt:message key="label.ricerca" /></legend>
			      <div class="form-group">
			          <label><fmt:message key="label.tag" /></label>
	                  <jsp:include page="../includes/autocompletergenerico.jsp" >
	                      <jsp:param name="idElemento" value="nometagpeople" />       
	                      <jsp:param name="propertyPath" value="nometagpeople" />             
	                      <jsp:param name="pathPropertyDescription" value="nometagpeople" />                          
	                      <jsp:param name="autocompleterAjax" value="findNometagpeople.htm" />                            
	                      <jsp:param name="titleKey" value="label.ricerca_mappature" />
	                      <jsp:param name="afterUpdateElement" value="cleartagscheda" />                                                                  
	                  </jsp:include>  
	                  <init:help idHelp="mappature_tag_help_id" textKey="mappature.help.tag" />
			      </div>
			      <div class="form-group">
			          <label><fmt:message key="label.scheda" /></label>
	                  <jsp:include page="../includes/autocompletergenerico.jsp" >
	                      <jsp:param name="idElemento" value="dyn2Modellit" />        
	                      <jsp:param name="propertyPath" value="dyn2Modellit" />              
	                      <jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
	                      <jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
	                      <jsp:param name="autocompleterAjax" value="findSchedaByTagAndSoftware.htm" />                           
	                      <jsp:param name="titleKey" value="label.ricerca_scheda" />
	                      <jsp:param name="ajaxCallBack" value="filtertagpeoplesoftware" />   
	                      <jsp:param name="afterUpdateElement" value="viewScheda" />                      
	                  </jsp:include>
	                  <init:help idHelp="mappature_dyn2Modellit_help_id" textKey="mappature.help.dyn2Modellit" />
			      </div>
			    </fieldset>
				<c:if test="${mappature.displayMode==mappature.displayConstants.VIEW}">										
					<fieldset>
					    <legend>${mappature.modellitHelper.scheda.descrizione}</legend>
	                    <%
	                        Integer tabIndex = 4;                       
	                    %>
						<table class="vbg-table">
							<thead>
								<tr>
									<th><fmt:message key="label.nome_campo"/></th>
									<th><fmt:message key="label.tagpeople"/></th>
				                    <th>
				                        <fmt:message key="label.tipo_regola"/>
				                    </th>
				                    <th><fmt:message key="label.valore_confronto"/></th>
				                    <th><fmt:message key="label.valore_decodifica"/></th>
				                    <th></th>				                                                      
								</tr>
							</thead>
					    	<tbody>
					    		<c:forEach items="${mappature.modellitHelper.campiHelpers}" var="current" varStatus="a">
									<c:forEach items="${current.mappatures}" var="currentmap" varStatus="b">
										<tr class="mappature" data-campo-index='${a.index}' data-mappatura-index='${b.index}'>
											<td>
												<c:if test="${b.index==0}">
													<label>${current.campo.nomecampo}</label>
													<c:set var="visualizzaAltreSchede" scope="page" value="display:none;" ></c:set>
													<c:if test="${fn:length(current.campo.dyn2Modellids)>1}">
														<c:set var="visualizzaAltreSchede" scope="page" value="" ></c:set>
													</c:if>
													<div id="dettaglioAltreSchede_id${current.campo.id.codice}" style="${visualizzaAltreSchede}">													
														   	<a style="cursor: pointer;" onclick="dettaglioTabAltreSchede('dettaglioDialogDivSchede${current.campo.id.codice}','${current.campo.id.codice}','${mappature.dyn2Modellit.id.codice}','false');" title="<fmt:message key="mappature.label.visualizza_altre_schede"/>">
														   	<fmt:message key="label.altre_schede"/></a>												   	
														   	<div dojoType="dijit.Dialog" id="dettaglioDialogDivSchede${current.campo.id.codice}" title="<fmt:message key="mappature.label.altre_schede" />: "> 
														   		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px; height: 100%;">
																	<div id="dettaglioSchede${current.campo.id.codice}">
																	</div>
																</div>
														   	</div>														   	
												   	</div>
												   	<c:set var="visualizzaAltreMappature" scope="page" value="display:none;" ></c:set>
													<c:if test="${fn:length(current.campo.mappatures)>1}">
														<c:set var="visualizzaAltreMappature" scope="page" value="" ></c:set>
													</c:if>
													<div id="dettaglioAltreMappature_id${current.campo.id.codice}" style="${visualizzaAltreMappature}">													
														   	<a style="cursor: pointer;"  onclick="dettaglioTabAltreSchede('dettaglioDialogDivMappature${current.campo.id.codice}','${current.campo.id.codice}','${mappature.dyn2Modellit.id.codice}','true');" title="<fmt:message key="mappature.label.visualizza_altre_schede_mappature"/>" ><fmt:message key="label.altre_mappature"/></a>												   	
														   	<div dojoType="dijit.Dialog" id="dettaglioDialogDivMappature${current.campo.id.codice}" title="<fmt:message key="mappature.label.altre_schede_con_mappature" />: "> 
														   		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px; height: 100%;">
																	<div id="dettaglioMappature${current.campo.id.codice}">
																	</div>
																</div>
														   	</div>														   	
												   	</div>								   												
												</c:if>
											</td>
											<td>
												<spring:bind path="modellitHelper.campiHelpers[${a.index}].mappatures[${b.index}].nometagpeople">
													<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" maxlength="400" size="80"/>						 
												</spring:bind>																													
											</td>
											<td>
												<spring:bind path="modellitHelper.campiHelpers[${a.index}].mappatures[${b.index}].tiporegola">
													<select tabindex="<%=tabIndex++%>" name="${status.expression}" id="tiporegola_id${a.index}" onchange="mostraconfronto(${a.index},${b.index},this.value)">
														<option value="0" <c:if test="${currentmap.tiporegola==0}">selected="selected"</c:if>><fmt:message key='mappature.label.valore_people'/></option>
														<option value="1" <c:if test="${currentmap.tiporegola==1}">selected="selected"</c:if>><fmt:message key='mappature.label.descrizione_people'/></option>
														<option value="4" <c:if test="${currentmap.tiporegola==4}">selected="selected"</c:if>><fmt:message key='mappature.label.valore_descrizione_people'/></option>
														<option value="2" <c:if test="${currentmap.tiporegola==2}">selected="selected"</c:if>><fmt:message key='mappature.label.decodifica'/></option>
														<option value="3" <c:if test="${currentmap.tiporegola==3}">selected="selected"</c:if>><fmt:message key='mappature.label.espressione_regolare'/></option>													
													</select>
												</spring:bind>									
											</td>			
											<c:set var="_displayConfronto" value="style=\"display: none;\""></c:set>
											<c:if test="${currentmap.tiporegola==2 || currentmap.tiporegola==3}">
												<c:set var="_displayConfronto" value=""></c:set>
											</c:if>
											<td>
												<div id="valoreconfronto_id${a.index}map${b.index}" ${_displayConfronto}>
													<spring:bind path="modellitHelper.campiHelpers[${a.index}].mappatures[${b.index}].valoreconfronto">
														<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="valconf_id${a.index}map${b.index}" value="${status.value}" maxlength="150"/>
													</spring:bind>
												</div>
											</td>
											<td>
												<div id="valoredecodifica_id${a.index}map${b.index}" ${_displayConfronto}>
													<spring:bind path="modellitHelper.campiHelpers[${a.index}].mappatures[${b.index}].valoredecodifica">
														<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="valdec_id${a.index}map${b.index}"	value="${status.value}" maxlength="150"/>
													</spring:bind>
												</div>
											</td>
											<td>
												<c:choose>
													<c:when test="${b.index==0}">
														<div id="aggiungi_id${a.index}map${b.index}">
															<a id="imgAggiungi${a.index}" href="javascript:doSubmit('aggiungi.htm?codicecampo=${mappature.modellitHelper.campiHelpers[a.index].campo.id.codice}','',document.inviodati);" 
															title="<fmt:message key="label.aggiungi_mappatura" />" class="vbg-btn btn-aggiungi">
															</a>
														</div>											
													</c:when>
													<c:when test="${b.index!=0}">
														<div id="elimina_id${a.index}map${b.index}">
															<a onclick="alert('<fmt:message key="mappature.confirm.salvataggio_mappature" />');" id="imgElimina${a.index}" class="vbg-btn btn-elimina" 
															href="javascript:doSubmit('elimina.htm?codicecampo=${mappature.modellitHelper.campiHelpers[a.index].campo.id.codice}&codicemap=${mappature.modellitHelper.campiHelpers[a.index].mappatures[b.index].id.codice}&indicemap=${b.index}','',document.inviodati);" 
															title="<fmt:message key="label.elimina_mappatura" />">
															</a>														
														</div>
													</c:when>
												</c:choose>
											</td>	
											<c:remove var="_displayConfronto" />		
										</tr>										
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</fieldset>							
				</c:if>
			</spring-form:form>
		</div>
	</div>
	<div>
        <c:if test="${mappature.displayMode==mappature.displayConstants.VIEW}">				
            <a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
        </c:if>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>