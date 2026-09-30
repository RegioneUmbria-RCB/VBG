<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="parametriDiv">
	<div class="etichetta">
		<div><fmt:message key="label.istanza" />:</div>
		<div><fmt:message key="label.richiedente" />:</div>
	</div>		
	<div class="parametro">       		 	
		<div>
			<a href="../istanze/view.htm?codice=${istanza.id.codice}">${istanza.numeroistanza}</a>
			<%--<a href="javascript:funzioniIstanza(${istanza.id.codice});" title="<fmt:message key="label.istanza" /> ${istanza.numeroistanza}">${istanza.numeroistanza}</a>--%>
		</div>
		<div>
			${istanza.transientRichiedenteQualitaAzienda}		
			<span id="fx_interdizioni" style="display: none;"></span>
			<!-- §§§BEGIN§§§ -->
			<c:if test="${inite:isEnterprise()}">
				<script type="text/javascript">			
				jQuery(document).ready(function(){
					var _ts  = new Date().getTime();
						new Ajax.Request('<%=request.getContextPath()%>/ajax/isAnagrafeInterdetta.htm?ts_='+_ts, {
								method: 'post',	
								parameters: {codiceAnagrafe: ${istanza.richiedente.id.codice}},
								onSuccess: function(transport){
									if(transport.responseText!=''){
										$('fx_interdizioni').innerHTML=" ("+transport.responseText+")";
										$('fx_interdizioni').style.display='';
									}else{
										$('fx_interdizioni').style.display='none';
									}
								},
								onFailure: function(transport){ 
									  
									}			
							});
				});
				</script>
			</c:if>
			<!-- §§§END§§§ -->
		</div>
	</div>
</div>
<%--
<script type="text/javascript">
<!--
	function funzioniIstanza(codice){		
		historySet('<%= URLEncoder.encode("../istanze/view.htm?codice=")%>${istanza.id.codice}', '../istanze/view.htm?codice=${istanza.id.codice}','');
	}
//-->
</script>
<script type="text/javascript">
<!--
	
	var _fxFunzioniIstanzaDlg = new dijit.Dialog({
	    title: "<fmt:message key="label.funzioni_istanza" />" ,
	    style: "overflow:auto; width: 600px;height: 300px;"
	});
	function funzioniIstanza(codice){
			  _fxFunzioniIstanzaDlg.attr("content", $('fxIstanzeDiv').innerHTML);
			  _fxFunzioniIstanzaDlg.show();							  
	}
//-->
</script>
<div id="fxIstanzeDiv" style="display: none;">
	<jsp:include page="../includes/istanze_funzioni.jsp">
    	<jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
	</jsp:include>
</div>
--%>