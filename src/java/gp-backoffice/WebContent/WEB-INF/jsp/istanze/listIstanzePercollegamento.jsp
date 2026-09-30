<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanze" /></title>
</head>
<body>
	<%
	pageContext.setAttribute("SEARCH_ISTANZE_COLLEGATE", WebConstants.SEARCH_ISTANZE_COLLEGATE);
	pageContext.setAttribute("SEARCH_ISTANZE_ACCESSO_ATTI", WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI);
	pageContext.setAttribute("SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA", WebConstants.SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA);
	%>
	<span class="titoloPagina"><fmt:message key="label.lista_istanze" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanze/listIstanze" />
	</jsp:include>
    
    
    <c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${codiceistanzaPerViewInfo}</c:param>
	</c:import>
 	<br class="clear" />
 	<jsp:include page="../includes/history.jsp">
		    <jsp:param name="path" value="../istanze/listIstanze" />
	</jsp:include>
		<div id="subcontent">
			<form name="inviodati" action="listIstanze.htm">
				${htmltable}
			</form>
			
			<form name="COLLEGA_ISTANZE" method="post" action="${pageContext.request.contextPath}/istanzecollegate/addCollegamento.htm">					
				<input type="hidden" name="lista_istanze_da_collegare" id="lista_istanze_da_collegare_id"/>
				<input type="hidden" name="codiceIstanza" value="${istanzaDaConfigurare.id.codice}" id="istanza_origine_id"/>
			</form>
			
			
			<form name="COLLEGA_ISTANZE_PRECEDENTE" method="post" action="${pageContext.request.contextPath}/istanzecollegate/addCollegamentoPrecedente.htm">					
				<input type="hidden" name="lista_istanze_da_collegare" id="lista_istanze_da_collegare_prec_id"/>
				<input type="hidden" name="codiceIstanza" value="${istanzaDaConfigurare.id.codice}" id="istanza_origine_id"/>				
			</form>
			
			
			<form name="COLLEGA_ISTANZE_ACCESSO_ATTI_T" method="post" action="${pageContext.request.contextPath}/istanzeaccessoattit/addCollegamento.htm">					
				<input type="hidden" name="lista_istanze_da_collegare_accesso_atti" id="lista_istanze_da_collegare_accesso_atti_id"/>
				<input type="hidden" name="lista_istanze_con_doc_validi" id="lista_istanze_con_doc_validi_id"/>
				<input type="hidden" name="codiceIstanzaAccessoAtti" value="${codiceIstanzaAccessoAtti}" id="istanze_accesso_atti_t_id"/>
				<input type="hidden" name="checkIstanzeCollegate" value="1" id="checkIstanzeCollegate_id"/>
			</form>
			
			<form name="COLLEGA_ISTANZE_ANAGRAFE_TRIBUTARIA" method="post" action="${pageContext.request.contextPath}/anagrafetributaria/addIstanzaAGruppo.htm">					
				<input type="hidden" name="codiceGruppoAnagrafetributaria" value="${codiceGruppoAnagrafetributaria}" id="codiceGruppoAnagrafetributaria_id"/>
				<input type="hidden" name="codiceIstanzaAnagrafeTributaria" id="codiceIstanzaAnagrafeTributaria_id"/>
			</form>				
				
			<script type="text/javascript">
				var _jmesaUrl='listIstanze.htm?';
				var _captionTab='<fmt:message key="label.lista_istanze" />';
				
				jQuery( document ).ready(function() {
					//jQuery('input[id^="codice_istanza_id"]').css("display", "none");
				});
				
				function collega(){
					document.forms['COLLEGA_ISTANZE'].submit();		
				}
				
				function collegaPrecedente(){
					document.forms['COLLEGA_ISTANZE_PRECEDENTE'].submit();		
				}
				
				function collegaIstanzeAccessoAtti(){
					selectFlgVisualizzaDocValidi();
					document.forms['COLLEGA_ISTANZE_ACCESSO_ATTI_T'].submit();		
				}
				
				function collegaIstanzeAnagrafeTributariaGruppi(){
					document.forms['COLLEGA_ISTANZE_ANAGRAFE_TRIBUTARIA'].submit();		
				}
				
				function addToIstanzaDacollegare(){
					var istDaCollegare = "";
					var almenoUno = false;
					jQuery(".istanze_da_collegare_cls").each(function() {
					    if(this.checked){
					    	istDaCollegare += this.value+",";
					    	almenoUno=true;
					    }
					});
					istDaCollegare = istDaCollegare.replace(/,$/,"");
					if(almenoUno){
						jQuery('#collegta_all_istanza_fun').show();
					}else{
						jQuery('#collegta_all_istanza_fun').hide();
					}
					jQuery('#lista_istanze_da_collegare_id').val(istDaCollegare);
				}
						
				
				function addToIstanzaPrecedenteDacollegare(){
					var istDaCollegare = "";
					var almenoUno = false;
					jQuery(".istanze_da_collegare_cls").each(function() {
					    if(this.checked){
					    	istDaCollegare += this.value+",";
					    	almenoUno=true;
					    }
					});
					istDaCollegare = istDaCollegare.replace(/,$/,"");
					if(almenoUno){
						jQuery('#collegta_all_istanza_prec_fun').show();
					}else{
						jQuery('#collegta_all_istanza_prec_fun').hide();
					}
					jQuery('#lista_istanze_da_collegare_prec_id').val(istDaCollegare);
				}
				
				/**
				Javascript che permette selezione multipla istanze per il collegamento alla tabella
				 accesso atti T
			    **/
				function addToIstanzaDacollegareAccessoAtti(){
					var istDaCollegare = "";
					var almenoUno = false;
					jQuery(".istanze_da_collegare_acesso_atti_cls").each(function() {
					    if(this.checked){
					    	istDaCollegare += this.value+",";
					    	almenoUno=true;
					    }
					});
					istDaCollegare = istDaCollegare.replace(/,$/,"");
					if(almenoUno){
						jQuery('#collegta_all_istanza_atti_fun').show();
					}else{
						jQuery('#collegta_all_istanza_atti_fun').hide();
					}
					jQuery('#lista_istanze_da_collegare_accesso_atti_id').val(istDaCollegare);
				}
				

				
				
				
				function selectFlgVisualizzaDocValidi(){
					var istVisualizzaDocValidi = "";
					jQuery(".lista_flg_visualizza_doc_validi_cls").each(function() {
					    // if(this.checked){
					    	istVisualizzaDocValidi += this.value+",";
					    // }
					});
					istVisualizzaDocValidi = istVisualizzaDocValidi.replace(/,$/,"");
					jQuery('#lista_istanze_con_doc_validi_id').val(istVisualizzaDocValidi);
				}
				
				
				
				
				function attivoSelezioneMultipla(){
					//var checked = jQuery('#addColumn').is(':checked');
					//jQuery(".documenti_da_firmare_cls").each(function() {
					//    this.checked=checked;			    
					//});
					//addToDocDaFirmare();
					//alert('sto per nascondere');
					//jQuery('.addColumn').hide();
					//jQuery(".addColumn").text("Hello world!");
					//alert ('attivo selezione multipla')
				}
				
				
				function addToIstanzaDacollegareAnagrafeTributaria(obj){
					
					
					if(confirm('Attenzione! verrà collegata l\'istanza selezionata.\nProcedere?')){
						let codiceIstanza = obj.dataset.codiceistanza;
						document.getElementById('codiceIstanzaAnagrafeTributaria_id').value = codiceIstanza;
						vbg.mostraModalCaricamento();
						document.COLLEGA_ISTANZE_ANAGRAFE_TRIBUTARIA.submit();
					}else {	
						obj.checked = false;
					}
				}
				
				vbg.ready(() => {	
					
					
					document.querySelectorAll('.anagrafe_tributaria_cls').forEach((item) => {
                    	
                    	
                    	item.addEventListener('click', (e) => {
                    		
                    		addToIstanzaDacollegareAnagrafeTributaria(item);
                    	});
                    	
                    });
					
					
					
				});
				
			</script>
	
	</div>
	<div id="functions">
		<ul>
			<c:if test="${_tipoRicerca == SEARCH_ISTANZE_COLLEGATE}">
				<li><a href="javascript:doHref('../istanze/searchIstanze.htm?tipoRicerca=${_tipoRicerca}&codiceIstanzaDaconfigurare=${istanzaDaConfigurare.id.codice}','')"><fmt:message key="button.back" /></a></li>				
				<li><a id="collegta_all_istanza_fun" style="display: none;" href="javascript:collega();"><fmt:message key="label.collega_istanze" /></a></li>
		     	<li><a id="collegta_all_istanza_prec_fun" style="display: none;" href="javascript:collegaPrecedente();"><fmt:message key="label.collega_istanze" /></a></li>
			</c:if>
			<c:if test="${_tipoRicerca == SEARCH_ISTANZE_ACCESSO_ATTI}">
				<li><a id="collegta_all_istanza_atti_fun" style="display: none;" href="javascript:collegaIstanzeAccessoAtti();"><fmt:message key="button.aggiungi_istanza_accesso_atti" /></a></li>
				<li><a href="javascript:doHref('../istanze/searchIstanze.htm?tipoRicerca=${_tipoRicerca}&codiceIstanzaAccessoAtti=${codiceIstanzaAccessoAtti}','')"><fmt:message key="button.back" /></a></li>
		     </c:if>
			<c:if test="${_tipoRicerca == SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA}">
				<li><a id="collegta_all_anagrafe_trib_fun" style="display: none;" href="javascript:collegaIstanzeAnagrafeTributariaGruppi();"><fmt:message key="button.aggiungi" /></a></li>
				<li><a href="javascript:doHref('../istanze/searchIstanze.htm?tipoRicerca=${_tipoRicerca}&codiceGruppoAnagrafetributaria=${codiceGruppoAnagrafetributaria}','')"><fmt:message key="button.back" /></a></li>
		     </c:if>		     
		</ul>
	</div>
</body>
</html>