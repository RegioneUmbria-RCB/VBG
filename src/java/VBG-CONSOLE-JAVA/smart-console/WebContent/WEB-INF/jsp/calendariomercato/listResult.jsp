<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatipresenzeT.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatipresenzeT.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
  		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../calendariomercato/search" />
		</jsp:include>
		<c:if test="${not empty param.mercato.id.codice}">
        <span class="parametri">
	        <fmt:message key="form.mercatipresenzeT.mercati" />: <label><c:out value="${mercatipresenzeT.mercato.descrizione}"></c:out></label>
        </span>
		</c:if>
		<div id="subcontent">
		 	<form name="mercatipresenzeTForm" action="search.htm">
				<jmesa:springTableFacade
					id="mercatipresenzeT_id" 
					items="${mercatipresenzeTList}" 
					var="mercatipresenzeT_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore"  view="org.jmesa.custom.GroupMercatiAnnoHtmlView" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="anno" titleKey="form.mercatipresenzeT.anno" />
							<jmesa:htmlColumn property="mercato.descrizione" titleKey="form.mercatipresenzeT.mercati" cellEditor="org.jmesa.customColumn.MercatoDaElaborareCellEditor"/>
                            <jmesa:htmlColumn property="mercatoUso.descrizione" titleKey="form.mercatipresenzeT.descrizione" />
							<jmesa:htmlColumn property="id.codice" titleKey="label.edit.record" sortable="false" filterable="false">
								<a href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fcalendariomercato%2Fview.htm%3Fcodice%3D${mercatipresenzeT_var.mercato.id.codice}%26mercatouso.id.codice%3D${mercatipresenzeT_var.mercatoUso.id.codice}%26anno%3D${mercatipresenzeT_var.anno}%26step%3D2"  title="<fmt:message key="button.presenze.market" />">
									<img src="../images/bob.gif" alt="<fmt:message key="button.presenze.market" />" />
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
                <input type="hidden" value="${mercati.id.codice}" name="mercati.id.codice" />
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='search.htm?';
				var _captionTab='<fmt:message key="form.mercatipresenzeT.title.list" />';
				
				
				function consolidaAnno( anno,codiceMercato){
					
					if(confirm('<fmt:message key="javascript.confirm.calendariomercato.consolida_presenze_anno" />')){
						disableFunctions();
						var jhqrPr = jQuery.ajax({
							  url: '../calendariomercato/ajaxUpdateConsolidaPresenzeAnno.htm',
							  context: document.body,
							  cache: false,
							  data: "codiceMercato="+codiceMercato+"&anno="+anno,
							  dataType: "html",
							  success: function(data) {
								  if(data){
									  enableFunctions();
									  if(data=='OK'){
										  jQuery('#da_consolidare_'+anno+'_'+codiceMercato).hide();
										  alert("Operazione avvenuta con successo"); 
									  }else{
										  alert("Operazione avvenuta con errore");
									  }
								  }
							  },
							  error: function(jqXHR, textStatus, errorThrown){
									console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
							}
						});		
					}
				}
				
			</script> 
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createSearch.htm','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>