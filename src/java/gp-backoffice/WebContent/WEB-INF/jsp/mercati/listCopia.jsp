<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="java.net.URLEncoder"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="manifestazione.label.lista_manifestazioni.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="manifestazione.label.lista_manifestazioni.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    
   
		    
	<div id="subcontent">
		<form name="mercatiForm" action="listCopia.htm">
		
		 <jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="regolaCommand" />
		    </jsp:include>
		
		
			<jmesa:springTableFacade
				id="mercati_id" 
				items="${mercatiList}" 
				var="mercati_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.MercatiFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${mercati_var.id.codice}">${mercati_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
						<jmesa:htmlColumn property="attivo" titleKey="label.attivo" width="5%">
							<div class="vbg-btn btn-aggiungi copia_mercato" data-id="${mercati_var.id.codice}"></div>
						</jmesa:htmlColumn>

					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listCopia.htm?';
			var _captionTab='<fmt:message key="manifestazione.label.lista_manifestazioni.title" />';
			
			
			jQuery(document).ready(function() {
				jQuery('.copia_mercato').each(function(i, obj) {
					    jQuery(this).on("click", function(){
					    	var idMercato = jQuery(this).data('id');
					    	jQuery('#idmercato').val(idMercato);
					    	jQuery("#dlg").dialog({
								 resizable: false,
								 modal: true,
								 width:'60%',
								 title: 'Copia mercato'
								}
							);
					    });
				});
			});
			function copia(){
				var url='updateCopiaMercato.htm?idMercato='+jQuery('#idmercato').val()+'&idCausaleAcquisizione='+jQuery('#acquisizione_id').val()+'&idCausaleCessazione='+jQuery('#cessazione_id').val();
				doHref(url,'Attenzione!! copiare il mercato?')
			}
		</script>
	</div>
	
	<div id="dlg">
		<input type="hidden" id="idmercato" value="" />
		<table width="100%">
		<tr>
		<td>Causale di cessazione</td>
		<td>
		<select id="cessazione_id">
			<c:forEach items="${cessazionis}" var="causale">
				<option value="${causale.id.codice }">${causale.descrizione }</option>
			</c:forEach>
		</select>
		</td>
		</tr>
		<tr>
		<td>Causale di acquisizione</td>
		<td>
		<select id="acquisizione_id">
			<c:forEach items="${acquisizionis}" var="causale">
				<option value="${causale.id.codice }">${causale.descrizione }</option>
			</c:forEach>
		</select>
		</td>
		</tr>
		</table>
		<div id="functions">
			<ul>
				<li><a href="javascript:copia()">Copia</a></li>
			</ul>
		</div>
		
	</div>

	<div id="functions">
		<ul>
			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>