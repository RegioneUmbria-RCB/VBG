<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanzeallegati" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_istanzeallegati" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeallegati/list" />
	</jsp:include>
	<div id="subcontent">
	
	
		<%
		 	Integer sizeList=(Integer)request.getAttribute("sizeList");
			String displayInventarioprocedimenti= "";
			String styledisplayInventarioprocedimenti = "sezioneDatiMeno";
		%>
	
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanze.id.codice}</c:param>
	</c:import>
 	<br class="clear" /><br class="clear" />
 	<c:forEach items="${istanzeallegatiList}" var="istanzeallegatiHelper" varStatus="indice">
 		<!-- TABELLA DI UN INVENTARIO PROCEDIMENTO -->
 		<!-- START -->
 		<table border="0" width="100%">
 		   
 		    <tr>
				<td>
				     <%-- 
				    <a
						class="<%=styledisplayInventarioprocedimenti%>"
						id="id_link_inventario_procedimento${indice.index}"
						href="javascript:showHidePanelBase('id_inventario_procedimento_table${indice.index}', 'id_link_inventario_procedimento${indice.index}', ' ', '${pageContext.request.contextPath}/images/','tr',false);"
						title="<fmt:message key="label.mostra_nasconde_sezione" />${istanzeallegatiHelper.inventarioprocedimenti.procedimento} ">
						<label for="id_link_inventario_procedimento${indice.index}"> ${istanzeallegatiHelper.inventarioprocedimenti.procedimento}</label> 
					</a>
					--%>
					
						<div class="parametriDiv">
						<div class="etichetta">
							<div class="<%=styledisplayInventarioprocedimenti%>" id="id_link_inventario_procedimento${indice.index}" onclick="javascript:showHidePanelBase('id_inventario_procedimento_table${indice.index}', 'id_link_inventario_procedimento${indice.index}', ' ', '${pageContext.request.contextPath}/images/','tr',false);"
								title="<fmt:message key="label.mostra_nasconde_sezione" />&nbsp;${istanzeallegatiHelper.inventarioprocedimenti.procedimento} "> 
								<label for="id_link_inventario_procedimento${indice.index}"> </label> 
		              			<fmt:message key="label.inventarioprocedimento" /> : 
		        			</div>
	        			</div>
	        			<div class="parametro">
							<div>
	              				<c:out value="${istanzeallegatiHelper.inventarioprocedimenti.procedimento}"/>
	        				</div>
       					</div>
	        		</div>
					</td>
					</tr>
				    
						
			
			
 			<tr id="id_inventario_procedimento_table${indice.index}">
 			    <!-- TABELLA DEGLI ALLEGATI -->
 				<!-- START -->
 				<td>
 				<div class="jmesa" >
        		<table border="1"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr  class="header">
						<td width="20%"><fmt:message key="label.codice"/></td>
						<td width="20%"><fmt:message key="label.allegato"/></td>
						<td><fmt:message key="label.presente"/></td>
						<%-- <td><fmt:message key="label.verificato"/></td>--%> 
						<td><fmt:message key="label.valido"/></td>
						<td><fmt:message key="label.doc_atto"/></td>
					</tr>
					</thead>
               		 <%
			    	int i=0;
			    	%>
					<tbody class="tbody">
					    <c:if test="${fn:length(istanzeallegatiHelper.istanzeAllegatis)>0}">
						<c:forEach items="${istanzeallegatiHelper.istanzeAllegatis}" var="istanzaallegato" varStatus="indice_istanzaallegato">
						<tr class="<%=(i%2)==0?"odd":"even"%>">
							<td>
								<a href="javascript:historySet('${_urlback }','../istanzeallegati/viewIstanzaAllegato.htm?codice=${istanzaallegato.id.codice}')">${istanzaallegato.id.codice}</a>
	                        </td>
							<td>
								${istanzaallegato.allegatoextra}
							</td>
	                        <td>
	                       	<input
								id="checkbox_presente_id${istanzaallegato.id.codice}"
								type="checkbox"
								value="${istanzaallegato.presente}"
								name="presente" ${istanzaallegato.oggetto.id.codice!=null?'disabled checked':''}
								${istanzaallegato.presente?'checked':''} onclick="regolaPresente('checkbox_presente_id${istanzaallegato.id.codice}','select_valido${istanzaallegato.id.codice}',${istanzaallegato.id.codice})"  
							 		/>
							<span id="result_presente${istanzaallegato.id.codice}"
								style="display: none"></span>
							</td>
							<%-- 
							<td>
							
							<input
								id="checkbox_verificato_id${istanzaallegato.id.codice}"
								type="checkbox"
								value="${istanzaallegato.verificato}"
								name="verificato"
								${istanzaallegato.verificato?'checked':''} onclick="regolaVerificato('checkbox_presente_id${istanzaallegato.id.codice}','checkbox_verificato_id${istanzaallegato.id.codice}','checkbox_look_id${istanzaallegato.id.codice}',${istanzaallegato.id.codice})"   
								/>
							<span id="result_verificato${istanzaallegato.id.codice}"
								style="display: none"></span>
							</td>
							--%>
							<%-- 
							<td>
							<input
								id="checkbox_look_id${istanzaallegato.id.codice}"
								type="checkbox"
								value="${istanzaallegato.controllook}"
								name="controllook"
								${istanzaallegato.controllook?'checked':''} onclick="regolaValido('checkbox_verificato_id${istanzaallegato.id.codice}','checkbox_look_id${istanzaallegato.id.codice}',${istanzaallegato.id.codice})" 
								/>
							<span id="result_look${istanzaallegato.id.codice}"
								style="display: none"></span>
							</td>
							--%>
							<td>
								<select id="select_valido${istanzaallegato.id.codice}" name="controllook" onchange="regolaValido('select_valido${istanzaallegato.id.codice}','checkbox_presente_id${istanzaallegato.id.codice}',${istanzaallegato.id.codice});">
									<option  value="null" ${istanzaallegato.controllook==null?'selected':''}>Da verificare</option>									
									<option  value="1" ${istanzaallegato.controllook==1?'selected':''}>Valido</option>
									<option  value="0" ${istanzaallegato.controllook==0?'selected':''}>Non valido</option>
								</select>
					        </td>
							<c:choose>
								<c:when test="${istanzaallegato.oggetto.id.codice!=null}">
									<td>
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="ialleg${istanzaallegato.id.codice}" />
				       						<jsp:param name="fileId" value="${istanzaallegato.oggetto.id.codice}" />				   							
				   						</jsp:include>
			                        </td>
								</c:when>
								<c:when test="${istanzaallegato.oggetto.id.codice==null &&  istanzaallegato.stcIdallegato!=null && istanzaallegato.stcIddocumento!=null}">
									<td>
			                        	<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
				       						<jsp:param name="codiceistanza" value="${istanzaallegato.istanza.id.codice}" />
				   							<jsp:param name="stcIddocumento" value="${istanzaallegato.stcIddocumento}" />
				   							<jsp:param name="stcIdallegato" value="${istanzaallegato.stcIdallegato}" />
				   							<jsp:param name="codiceRiferimento" value="${istanzaallegato.id.codice}" />
											<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_ENDO%>" />
											<jsp:param name="indice" value="istanza_all${istanzaallegato.id.codice}"/>	
			   							</jsp:include>	 
		                        	</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>            
						</tr>
						<%i++;%>
						</c:forEach>
						</c:if>
						<c:if test="${fn:length(istanzeallegatiHelper.istanzeAllegatis)==0}">
						<tr  class="<%=(i%2)==0?"odd":"even"%>">
							<td align="center" colspan="6">
								<fmt:message key="label.record_non_presenti"/>
	                        </td>
	                    </tr>
	                    </c:if>
	                </tbody>
				</table>
 				</div>
 				</td>
 				</tr>
 				<tr id="id_inventario_procedimento_table${indice.index}">
					<td>
			    		<div id="functions">
						<ul>
							<li><a href="javascript:historySet('${_urlback }','../istanzeallegati/createIstanzaAllegato.htm?codiceinventario=${istanzeallegatiHelper.inventarioprocedimenti.id.codice }&codiceistanza=${istanze.id.codice}')"><fmt:message key="button.new" /></a></li>
							<li><a href="javascript:historySet('${_urlback }','../istanzeallegati/createIstanzaAllegatoDaEndo.htm?codiceinventario=${istanzeallegatiHelper.inventarioprocedimenti.id.codice }&codiceistanza=${istanze.id.codice}')"><fmt:message key="button.da_endo" /></a></li>
							
						</ul>
		        		</div>
		     		</td>   
				</tr>
 				
 				
		 		<!-- TABELLA DEGLI ALLEGATI -->
		 		<!-- END -->
 		
 		</table>
 		
 		
 		<!-- TABELLA DI UN INVENTARIO PROCEDIMENTO -->
 		<!-- END -->
 		<br class="clear"/>
 	</c:forEach>
	 	
	</div>


   	<script type="text/javascript">
 		
    // GESTISCE LA SELECT BOX VALIDO
		function changeValueValido(obj, id){			
			var opzione="";
		    if(document.getElementById(id).value!='null')
			{
				opzione=document.getElementById(id).value;
			}
			new Ajax.Request('${pageContext.request.contextPath}/istanzeallegati/ajaxChangeValueFieldValido.htm?codice='+obj+'&valido='+opzione, {
						method: 'post',	
						onSuccess: function(transport){
						  dijit.showTooltip(transport.responseText, dojo.byId(id));
						  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
						},
						onFailure: function(transport){ 
						  $(id).innerHTML= transport.responseText;
						  $(id).className='error_checkbox';
						  applyStyle();
						  $(id).style.display='';
						  $(id).pulsate();
						  $(id).fade();
						 }						    		 
				});
		}
    	//GESTICE IL CHECKBOX PRESENTE
		function abilitaDisabilitaCheckbox(codice,presentato,valido) {

	       
			var isPresentato ='';
			var isVerificato ='';
			var isValido = '';
			if(document.getElementById(presentato)!=null)
			{
				var isPresentato = document.getElementById(presentato).value;
				var result ='checkbox_presente_id';
			}
			
			new Ajax.Request(
					'${pageContext.request.contextPath}/istanzeallegati/ajxaChangeCheckboxvalue.htm?codice='+codice+'&presentato='+ isPresentato+ '&verificato='+ isVerificato+'&valido='+isValido,
					{
						onSuccess : function(transport) {
							dijit.showTooltip(transport.responseText, dojo.byId(result+codice));
							setTimeout(function(){dijit.hideTooltip(dojo.byId(result+codice))},1000);
						},
						onFailure : function(transport) {
							$(result+codice).innerHTML = transport.responseText;
							$(result+codice).className = 'error_ajax_call';
							$(result+codice).style.display = '';
							applyStyle();
							$(result+codice).pulsate;
							({
								pulses : 2,
								duration : 1.0
							});
						}
					});
			}
		<%--
 		   Se un documento non è presnete non può essere valido e/o verificato
 		--%>
 		function regolaPresente(presente,valido,codice)
 		{
 			if(document.getElementById(presente).checked==false && document.getElementById(valido).value!='null')
 			{
 				alert('<fmt:message key="label.se_verificato_allora_presente"/>');
 				document.getElementById(presente).checked=true;
 				return false;
 			}
 			abilitaDisabilitaCheckbox(codice,presente,'','');
 		}
 		<%--
 		    Un documento per essere validato e/o verificato deve essere presente
 		--%>
 		function regolaValido(valido,presente,codice)
 		{

 			if(document.getElementById(valido).value!='null' && document.getElementById(presente).checked==false)
 				{
 				alert('<fmt:message key="label.non_verificato_allora_non_valido"/>');
 				document.getElementById(valido).value=null;
 				return false;
 				}
 			changeValueValido(codice, valido);
 		}
   	
   	
   	/*
   	
	 		
	 		//Tramite una chiamata ajax abilita o disabilita il checkbox selezionato
	 		function abilitaDisabilitaCheckbox(codice,presentato,verificato,valido) {

	 	       
		 			var isPresentato ='';
		 			var isVerificato ='';
		 			var isValido = '';
		 			if(document.getElementById(presentato)!=null)
		 			{
		 				var isPresentato = document.getElementById(presentato).value;
		 				var result ='checkbox_presente_id';
		 			}
		 			if(document.getElementById(verificato)!=null)
		 			{
		 				var isVerificato = document.getElementById(verificato).value;
		 				var result ='checkbox_verificato_id';
		 			}
		 			if(document.getElementById(valido)!=null)
		 			{
		 				var isValido = document.getElementById(valido).value;
		 				var result ='checkbox_look_id';
		 			}
		 			
		 			new Ajax.Request(
	 						'${pageContext.request.contextPath}/istanzeallegati/ajxaChangeCheckboxvalue.htm?codice='+codice+'&presentato='+ isPresentato+ '&verificato='+ isVerificato+'&valido='+isValido,
	 						{
	 							onSuccess : function(transport) {
	 								dijit.showTooltip(transport.responseText, dojo.byId(result+codice));
	 								setTimeout(function(){dijit.hideTooltip(dojo.byId(result+codice))},1000);
	 							},
	 							onFailure : function(transport) {
	 								$(result+codice).innerHTML = transport.responseText;
	 								$(result+codice).className = 'error_ajax_call';
	 								$(result+codice).style.display = '';
	 								$(result+codice).pulsate;
	 								({
	 									pulses : 2,
	 									duration : 1.0
	 								});
	 							}
	 						});
	 		}
	 		// Le regole seguono la logica:
	 		//	1- Affinche si possa selezionare il check verificato, deve essere selezionato presente
	 		//	2- Affinche si possa selezionare il check valido ,deve essere selazionato verificato
	 		// Le regole valgono anche nel verso opposto 
	 		//  1-  Non può essere tolta la spunta a verificato se è selezionato valido
	 		//  2- Non può essere tolto il  check presente se è selezionato verificato
	 		
	 		function regolaPresente(presente,verificato,codice)
	 		{
	 			
	 			if(document.getElementById(presente).checked==false && document.getElementById(verificato).checked==true)
	 				{
	 				alert('<fmt:message key="label.se_verificato_allora_presente"/>');
	 				document.getElementById(presente).checked=true;
	 				return false;
	 				}
	 			abilitaDisabilitaCheckbox(codice,presente,'','');
	 		}
	 		function regolaVerificato(presente,verificato,valido,codice)
	 		{
	 			
	 			if(document.getElementById(verificato).checked==true && document.getElementById(presente).checked==false)
	 				{
	 				alert('<fmt:message key="label.non_presente_allora_non_verificato"/>');
	 				document.getElementById(verificato).checked=false;
	 				return false;
	 				}
	 			if(document.getElementById(verificato).checked==false && document.getElementById(valido).checked==true)
	 			{
	 				alert('<fmt:message key="label.se_valido_allora_verificato"/>');
	 				document.getElementById(verificato).checked=true;
	 				return false;
	 				}
	 			abilitaDisabilitaCheckbox(codice,'',verificato,'');
	 		}
	 		function regolaValido(verificato,valido,codice)
	 		{
	 			
	 			if(document.getElementById(valido).checked==true && document.getElementById(verificato).checked==false)
	 				{
	 				alert('<fmt:message key="label.non_verificato_allora_non_valido"/>');
	 				document.getElementById(valido).checked=false;
	 				return false;
	 				}
	 			abilitaDisabilitaCheckbox(codice,'','',valido);
	 		}
	 		
	 		*/
	 		function parseScript(_source, validaScript) {
	 			var source = _source;
	 			var scripts = new Array();
	 			
	 			// Strip out tags
	 			while(source.indexOf("<script") > -1 || source.indexOf("</script") > -1) {
	 				var s = source.indexOf("<script");
	 				var s_e = source.indexOf(">", s);
	 				var e = source.indexOf("</script", s);
	 				var e_e = source.indexOf(">", e);
	 				
	 				// Add to scripts array
	 				scripts.push(source.substring(s_e+1, e));
	 				// Strip from source
	 				source = source.substring(0, s) + source.substring(e_e+1);
	 			}
	 			
	 			// Loop through every script collected and eval it
	 			if(validaScript){
		 			for(var i=0; i<scripts.length; i++) {
		 				try {
		 					eval(scripts[i]);
		 				}
		 				catch(ex) {
		 					// do what you want here when a script fails
		 				}
		 			}
	 			}
	 			
	 			// Return the cleaned source
	 			return source;
	 		}
	 		
	 		
	</script>
	<br class="clear"/>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>