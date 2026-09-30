<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>



<c:choose>
  	<c:when test="${isEccezioneRemota eq true}">
  		${errori}
  	</c:when>
 	<c:otherwise>
		<c:if test="${not empty errori}">
			${errori} <init:help idHelp="help_1${param.idCampo}" textKey="service_error.sit.valore_non_trovato.help"/>
		</c:if>
		<c:if test="${empty errori}">
			<c:if test="${empty mapValori}">
				<fmt:message key="html.statusbar.noResultsFound"/>
			</c:if>
		</c:if>
		<br class="clear" />
			
		<c:if test="${not empty mapValori}">
		<table>
	    <tr>
		    <td style="font-size: 14px">${INTESTAZIONE}</td>
			
			<c:if test="${SALVA_OGGETTO eq '1' }">
				<td>
				  <a class="vbg-btn btn-salva" href="javascript:salvaInDocIstanza(${CODICEOGGETTO})"
					         title="<fmt:message key="label.salva_allegato_in_documuenti_istanza"/>">
							<label><fmt:message key="label.visualizza.image" /></label> 
				  </a>
			   </td>
			</c:if> 
			<c:if test="${VISUALIZZA_OGGETTO eq '1' }">
				<td>
					<a  class="dettaglioColumn" href="../file/ajaxDownload.htm?fileId=${CODICEOGGETTO}"	title="<fmt:message key="label.edit.record" />&nbsp;${aree_var.denominazione}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a>
				</td>
			</c:if>
			
		 </tr>
		</table>
		
		</c:if>
	</c:otherwise>
</c:choose>

<div id="functions">
	<ul>
		<li><a href="javascript:closeDialogResultDettaglio();"><fmt:message key="button.back" /></a></li> 
	</ul>
</div>

<%-- 
<div id="functions">
		<ul>
			<li><a href="javascript:salvaInDocIstanza();"><fmt:message key="button.save" /></a></li>
		</ul>
</div>
--%>
<div id="myFiltersValues" style="overflow: auto; height: 100px; display: none;"></div>