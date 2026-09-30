<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%--
	parametri obbligatori:
		idElemento: l'identificativo dell'elemento che indica il Div sul quale andare a scrivere le informazioni sull'oggetto
		(DEVE ESSERE UNIVOCO ALL'INTERNO DELLA PAGINA DOVE VIENE USATO)
		fileId:	Il codiceOggetto per scaricare le informazioni sul file (Può essere vuoto)
	parametri opzionali:	
		mostralabel: accetta valori true o false (può essere nullo). Indica se mostrare anche il nome del file e la dimensione a fianco dell'icona di download
		readonly: accetta valori true o false (può essere nullo). Indica se mostrare le funzioni di modifica del file
		
		
		ATTENZIONE!!! SE SI MODIFICA QUESTA PAGINA E' NECESSARIO VERIFICARE LA CLASSE org.jmesa.customColumn.LinkOggetti.java ed i suoi usi
						es:Comunicazioni sulle graduatorie
		
 --%>
 <c:if test="${empty param.idComuneOggetto}">
	[oggetti.jsp]  Attenzione !! non è stato settato il parametro idComuneOggetto.
</c:if>
<c:set var="currtime"><%=System.currentTimeMillis() %></c:set>
<script type="text/javascript">
	jQuery(document).ready(function(){
		viewOggetto_${param.idElemento}_${currtime}_fx();
	});
	
	function viewOggetto_${param.idElemento}_${currtime}_fx (){
		if('${param.idElemento}' != ''){
			var jqxhr = jQuery.ajax({
				  url: "../file/ajaxViewOggettoList.htm",
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "fileId=${param.fileId}&idComuneOggetto=${param.idComuneOggetto}&mostralabel=${param.mostralabel}&readonly=${param.readonly}&jsFx=viewOggetto_${param.idElemento}_${currtime}_fx",
				  success: function(dataResult) { 
					   $('id_${param.idElemento}_${currtime}').innerHTML = dataResult;
					},
				  error: function(dataError){
					  $('id_${param.idElemento}_${currtime}').innerHTML = dataError.innerText;
				  }	
				});
		}
	}
</script>
	<span id="id_${param.idElemento}_${currtime}"></span>
