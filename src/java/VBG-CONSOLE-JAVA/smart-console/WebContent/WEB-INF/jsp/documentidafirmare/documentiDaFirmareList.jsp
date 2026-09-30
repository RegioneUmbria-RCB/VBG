<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.lista_documenti_da_firmare.title" />
	</title>
</head>
	<body>
		<span class="titoloPagina">
			<fmt:message key="label.lista_documenti_da_firmare.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
			<div id="subcontent">
				<form name="documentiDaFirmare" action="${formAction}">					
						${documentiDaFirmareHtmlTable}					
						
				</form>
				<form name="METTI_ALLA_FIRMA_FRM" method="post" action="${pageContext.request.contextPath}/documentidafirmare/mettiallafirmamultipla.htm">					
						<input type="hidden" name="lista_doc_da_firmare" id="lista_doc_da_firmare_id"/>
				</form>	
				
				<script type="text/javascript">
					var _jmesaUrl='documentiDaFirmareList.htm?';
					var _captionTab='<fmt:message key="label.lista_documenti_da_firmare.title" />';
				</script>		
				<script type="text/javascript">		
					function addToDocDaFirmare(){
						var docDaFirmare = "";
						var almenoUno = false;
						jQuery(".documenti_da_firmare_cls").each(function() {
						    if(this.checked){
						    	docDaFirmare += this.value+",";
						    	almenoUno=true;
						    }
						});
						docDaFirmare = docDaFirmare.replace(/,$/,"");
						if(almenoUno){
							jQuery('#metti_alla_firma_fun').show();
						}else{
							jQuery('#metti_alla_firma_fun').hide();
						}
						jQuery('#lista_doc_da_firmare_id').val(docDaFirmare);
					}
					
					function firma(){
						document.forms['METTI_ALLA_FIRMA_FRM'].submit();		
					}
				</script>						
		</div>
		<div id="metti_alla_firma_fun" style="display: none;">
			<div id="functions">
				<ul>
					<li><a href="javascript:firma();"><fmt:message key="label.firma_i_documenti" /></a></li>
				</ul>
			</div>
		</div>
		<br class="clear" />
		<div id="functions">
			<ul>
				<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>		
	</body>
</html>
					