<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.areariservata.web.filter.ServiziResolverFilter.ServiziEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.istanze-in-sospeso" /></title>
</head>
<body>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo"><fmt:message key="label.istanze-in-sospeso" /></div>
	<div class="descrizione"></div>

	
			<form name="domandeForm" action="list.htm">
				${htmlTable }				
			</form>		
			
			
			
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?1=1&';
				var _captionTab='<fmt:message key="label.istanze-in-sospeso" />';
			</script>
			
			<div style="padding-top: 20px;">
			<%
			
			boolean mostraBottoniCross = true;
			Cookie[] cs = request.getCookies();
			String cookieName = "bottonicross_" + ORMHelper.getIdcomuneAlias();
			if(cs != null){
				for(int i=0;i< cs.length;i++){
					Cookie c = cs[i];
					// System.out.print(c.getName());
					if(c.getName().equalsIgnoreCase(cookieName)){
					    mostraBottoniCross  = false;
					    break;
					}
				}
			}
			if(mostraBottoniCross){
			%>
				<input type="button" class="bottone-cart" onclick="vaiALeMiePratiche();" value="<fmt:message key='label.istanze-presentate' />" />
				
				
			<%} %>
			<input type="button" id="bottone-elimina-selezionate" style="display: none;" class="bottone-cart" onclick="eliminaSelezionate();" value="<fmt:message key='label.elimina-selezionate' />" />			
			&nbsp;		
			<%@ include file="../includes/chiudiPaginaIniziale.jsp" %>
			</div>
	<script type="text/javascript">
		
	$(".table_button").button();
	
	
		function checkElimina(){
			if($("input[data-tipo='eliminazione']:checked").size()>0){
				$('#bottone-elimina-selezionate').show();
			}else{
				$('#bottone-elimina-selezionate').hide();
			}
		}
		
		
		function eliminaSelezionate(){
			if(confirm("<fmt:message key='alert.elimina-domande' />")){
				var ids = '';
				$("input[data-tipo='eliminazione']:checked").each(function(i, obj) {
				   	ids+= 'id='+$(obj).val()+'&';
				});
				$.blockUI();
				document.location.href="${pageContext.request.contextPath}/fodomande/delete.htm?"+ids;
			}
		}
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function eliminaDomanda(id, idDomanda){
			if(confirm("<fmt:message key='alert.elimina-domanda' />\n"+idDomanda)){
				$.blockUI();
				document.location.href="${pageContext.request.contextPath}/fodomande/delete.htm?id="+id;
			}
		}
		function riprendiDomanda(id, idDomanda){
			if(confirm("<fmt:message key='alert.riprendi-domanda' />\n"+idDomanda)){
				$.blockUI();
				document.location.href="${pageContext.request.contextPath}/fodomande/riprendi.htm?id="+id;
			}
		}
		function onInvokeExportAction(id, action) {
		    var parameterString = createParameterStringForLimit(id);
		    location.href = '${pageContext.request.contextPath}/fodomande/list.htm?' + parameterString;
		}
		 
		function vaiALeMiePratiche(){
			
			location.href="${pageContext.request.contextPath}/<%= ORMHelper.getIdente()%>/<%= ServiziEnum.LEMIEPRATICHE.toString().toLowerCase()%>";
		}
	</script>
</body>
</html>