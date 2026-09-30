<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanze_collegate_accesso_atti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_istanze_collegate_accesso_atti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/listIstanze"/>
		<jsp:param name="qs" value="codice%3D${istanzeaccessoattit.entity.id.codice}"/>
    </jsp:include>
    <br />
	<div id="subcontent">
		<fieldset>
			<fmt:message key="label.istanze_collegate_accessi_atti" />
		</fieldset>
		
		<br />
		<form name="istanzeaccessoattit" action="inviodati">
		<c:forEach items="${accessoAttiTHelpers}" var="accessoAttiTHelper" varStatus="idx">
			<%-- <input type="hidden" value="${accessoAttiTHelper}"  /> --%>
			
		<div id="form" class="vbg-form">
				<fieldset>
				<legend><fmt:message key="label.fascicolo_istanza" />: <b>${accessoAttiTHelper.istanzaaccessoattiDHelper.numeroistanza}</b></legend>				
			<div class="header_dati">
				
				<div class="header_dato">
					<span class="header_dato_etichetta"><fmt:message key="label.richiedente" />:</span>
					<span class="header_dato_valore">${accessoAttiTHelper.istanzaaccessoattiDHelper.descrizioneRichiedente}</span>
				</div>
				<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.selezione_multipla" />:</span>
					<span class="header_dato_valore">
					
						<input type="checkbox" value="${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}" 
						id="codice_istanza_collegata_selezionata_id${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}" 
						class="istanze_da_collegare_acesso_atti_cls" checked="checked"/>
						
						
						
					</span>
				</div>
				<div class="header_dato">
					<span class="header_dato_etichetta"><fmt:message key="label.flag_doc_no_validi_accesso_atti" />:</span>
					<span class="header_dato_valore">
					
						
						<select id="flgVisualizzaDocId${a.index}" 
							data-codiceistanza="${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}" 
							class="lista_flg_visualizza_doc_validi_cls" 
							name="istanza_accesso_atti_t_da_collegare_validate" >
							<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 0 }"> selected </c:if> value="${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}-0"><fmt:message key="label.accesso_atti.solo_i_documenti_validi"/></option>
							<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 2 }"> selected </c:if> value="${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}-2"><fmt:message key="label.accesso_atti.tutti_i_documenti_validi_o_da_verificare"/></option>
							<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 1 }"> selected </c:if> value="${accessoAttiTHelper.istanzaaccessoattiDHelper.codiceIstanza}-1"><fmt:message key="label.accesso_atti.tutti_i_documenti"/></option>														
						</select>
					</span>
				</div>				
			</div>	
			
			<c:if test="${not empty accessoAttiTHelper.istanzaaccessoattiDHelpers }">
				
				
				<div class="titoloTabella"><fmt:message key="label.lista_istanze_collegate_accessi_atti" /></div>
				
					<table class="vbg-table">
						<thead>
							<tr>	
								<th><fmt:message key="label.numeroistanza" /></th>
								<th><fmt:message key="label.richiedente" /></th>
								<th><fmt:message key="label.selezione_multipla" /></th>
								<th><fmt:message key="label.flag_doc_no_validi_accesso_atti" /></th>
							</tr>
						</thead>
						<tbody>
							
						<c:forEach items="${accessoAttiTHelper.istanzaaccessoattiDHelpers}" var="attiDHelper" varStatus="idx">
						<tr>
				    	 	    <td>${attiDHelper.numeroistanza}</td>
				    	 	    <td>${attiDHelper.descrizioneRichiedente}</td>
				    	 		<td>
				    	 			<input type="checkbox" value="${attiDHelper.codiceIstanza}" 
				    	 			id="codice_istanza_collegata_selezionata_id${attiDHelper.codiceIstanza}" 
				    	 			class="istanze_da_collegare_acesso_atti_cls"/></td>

				    	 			<td>
				    	 			
									<select id="flgVisualizzaDocId${a.index}" 
										data-codiceistanza="${attiDHelper.codiceIstanza}" 
										class="lista_flg_visualizza_doc_validi_cls" 
										name="istanza_accesso_atti_t_da_collegare_validate" >
										<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 0 }"> selected </c:if> value="${attiDHelper.codiceIstanza}-0"><fmt:message key="label.accesso_atti.solo_i_documenti_validi"/></option>
										<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 2 }"> selected </c:if> value="${attiDHelper.codiceIstanza}-2"><fmt:message key="label.accesso_atti.tutti_i_documenti_validi_o_da_verificare"/></option>
										<option <c:if test="${accessoAttiTHelper.istanzaaccessoattiDHelper.flgVisualizzaDoc eq 1 }"> selected </c:if> value="${attiDHelper.codiceIstanza}-1"><fmt:message key="label.accesso_atti.tutti_i_documenti"/></option>														
									</select>
									<%--
				    	 			<input type="checkbox" value="${attiDHelper.codiceIstanza}" id="flg_visualizza_doc_validi_id${attiDHelper.codiceIstanza}" class="lista_flg_visualizza_doc_validi_cls" name="istanza_accesso_atti_t_da_collegare_validate" onclick="selectFlgVisualizzaDocValidi();"/>
				    	 			 --%>
				    	 			</td>
				    	 		
				    	 		
						</tr>    	 
						</c:forEach>
						
						</tbody>
					</table>
                   	
				
			</c:if>
			
          
          

		</fieldset>
        </div>  
          
		</c:forEach>
			
			
		</form>
		<form name="ISTANZE_ACCESSO_ATTI_T_COLLEGATE_DA_VALIDARE" method="post" action="${pageContext.request.contextPath}/istanzeaccessoattit/addCollegamento.htm" >
				<input type="hidden" name="lista_istanze_da_collegare_accesso_atti" id="lista_istanze_da_collegare_accesso_atti_id" />
				<input type="hidden" name="lista_istanze_con_doc_validi" id="lista_istanze_con_doc_validi_id"/>
				<input type="hidden" name="codiceIstanzaAccessoAtti" value="${istanzeaccessoattit.entity.id.codice}" id="istanze_accesso_atti_t_id"/>
				<input type="hidden" name="checkIstanzeCollegate" value="0" id="checkIstanzeCollegate_id"/> 
		</form>
		<script type="text/javascript">
		
			function collegaIstanzeAccessoAttiTCollegate(){
				
				addToIstanzaDacollegareAccessoAtti();
				selectFlgVisualizzaDocValidi();
				document.forms['ISTANZE_ACCESSO_ATTI_T_COLLEGATE_DA_VALIDARE'].submit();
			}
			function addToIstanzaDacollegareAccessoAtti(){
				
			    let istDaCollegare = "";
			    document.querySelectorAll('.istanze_da_collegare_acesso_atti_cls').forEach(
					x => { 
						if(x.checked){
					    	istDaCollegare += x.value+",";
					    }										
					}
				);
			    
				istDaCollegare = istDaCollegare.replace(/,$/,"");
				document.getElementById('lista_istanze_da_collegare_accesso_atti_id').value = istDaCollegare;
			} 
		
			function selectFlgVisualizzaDocValidi(){
				let istVisualizzaDocValidi = "";				
				document.querySelectorAll('.lista_flg_visualizza_doc_validi_cls').forEach(
						x => { 
							istVisualizzaDocValidi += x.value+",";									
						}
					);				
				istVisualizzaDocValidi = istVisualizzaDocValidi.replace(/,$/,"");
				document.getElementById('lista_istanze_con_doc_validi_id').value = istVisualizzaDocValidi;
			}
	
		</script>
		
		
	</div>
	<div id="functions">
		<ul>
			<li><a id="collegta_all_istanza_atti_fun" href="javascript:collegaIstanzeAccessoAttiTCollegate();"><fmt:message key="button.aggiungi_istanza_accesso_atti" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>