<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>

<c:set var="identificativoTemporaneoParix" value="" />
<c:if test="${not empty param.identificativoTemporaneoParix}">
	<c:set var="identificativoTemporaneoParix" value="${param.identificativoTemporaneoParix}" />
</c:if>

<div id="visuraParix${identificativoTemporaneoParix}"  style="display: none">
	Indicare il cf impresa: 
	<input type="text" name="cfImpresa${identificativoTemporaneoParix}" id="cfImpresa${identificativoTemporaneoParix}" />
	<div id="functions">
	<ul>
		<li><a href="javascript:ricercaImpresa${identificativoTemporaneoParix}('cfImpresa${identificativoTemporaneoParix}');"><fmt:message key="button.ok" /></a></li>
		</ul>
	</div>		
</div>

	
<div id="dlgvisuraParix${identificativoTemporaneoParix}" style="display: none">
	Indicare il cf impresa: <input type="text" name="cfImpresa${identificativoTemporaneoParix}" id="idCfImpresa${identificativoTemporaneoParix}" />
	<div id="functions">
		<ul>
			<li><a href="javascript:ricercaImpresa${identificativoTemporaneoParix}('idCfImpresa${identificativoTemporaneoParix}');"><fmt:message key="button.ok" /></a></li>
		</ul>
	</div>		
</div>
	
	
	<script type="text/javascript">
		function visuraParixFN${identificativoTemporaneoParix}(){
			jQuery('#visuraParix${identificativoTemporaneoParix}').dialog({ autoOpen: true, modal : true, title: 'ricerca' });			
		}
		
		function ricercaImpresa${identificativoTemporaneoParix}(cfImpresaId){
			
			var cf =  jQuery('#'+cfImpresaId).val();
			disableFunctions();
			
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/ajax/visuraImpresa.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'cfImpresa='+ cf,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			enableFunctions();
	     			
	     			var wWidth = jQuery(window).width();
	     		    var dWidth = wWidth * 0.8;
	     		    var wHeight = jQuery(window).height();
	     		    var dHeight = wHeight * 0.8;
	     		    
	     			jQuery( "#dlgvisuraParix${identificativoTemporaneoParix}" ).dialog( { autoOpen:false, modal: true , width: dWidth, height: dHeight}).html(data);
	     			
	     			jQuery( "#dlgvisuraParix${identificativoTemporaneoParix}" ).dialog( "open" );
	     			
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     			
	     			enableFunctions();
	     		}
	     	});	
		}
	
	</script>