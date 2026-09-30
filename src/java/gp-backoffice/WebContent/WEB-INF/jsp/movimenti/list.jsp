<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.movimenti_istanza" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.movimenti_istanza" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../movimenti/list" />
		</jsp:include>				
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${istanza.id.codice}</c:param>
		</c:import>
		<br class="clear" />
		<div id="subcontent">
			<form name="movimentiForm" action="list.htm">
				<jmesa:springTableFacade
					id="movimenti_id" 
					items="${movimentiList}" 
					var="movimenti_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="tipomovimento.id.tipomovimento" titleKey="label.codice" width="2%">
                                  <a href="javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimenti_var.id.codice}','')">${movimenti_var.tipomovimento.id.tipomovimento}</a>
                         	</jmesa:htmlColumn>							
                         	<jmesa:htmlColumn property="data" titleKey="label.data"  cellEditor="org.jmesa.view.editor.DateCellEditor" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"  />
							<jmesa:htmlColumn property="movimento" titleKey="label.movimento" />
							<jmesa:htmlColumn property="amministrazioni.amministrazione" titleKey="label.amministrazione" />
							<jmesa:htmlColumn property="numeroprotocollo" titleKey="label.numero_protocollo" />
							<jmesa:htmlColumn property="dataprotocollo" titleKey="label.data_protocollo" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"  cellEditor="org.jmesa.view.editor.DateCellEditor" />
							<jmesa:htmlColumn property="esito" titleKey="label.esito_positivo" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
							<jmesa:htmlColumn property="parere" titleKey="label.parere">
							<c:choose>
								<c:when test="${fn:length(movimenti_var.parere)<200}">
									${movimenti_var.parere}
								</c:when>
								<c:otherwise>
									${fn:substring(movimenti_var.parere,0, 199)}
									<label style="cursor: pointer; font-weight: bold;" class="error" onclick="dijit.byId('parereMovimento_${movimenti_var.id.codice}').show();">[...]</label>
									<div id="parereMovimento_${movimenti_var.id.codice}" dojoType="dijit.Dialog" title="<fmt:message key="label.parere"/> - ${movimenti_var.tipomovimento.descrizioneEstesa}" style="display: none;">
								    	<div>
								    		<pre>${movimenti_var.parere}</pre>
								    	</div>
								    </div>
								</c:otherwise>
							</c:choose>
														

							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="pubblica" titleKey="label.pubblicato" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%" >
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimenti_var.id.codice}','')" title="<fmt:message key="label.edit.record" /> ${movimenti_var.tipomovimento.movimento}">
									</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceIstanza" value="${istanza.id.codice}"/>				
			</form>
			<c:set var="_tableTitle"><fmt:message key="label.movimenti_istanza" /></c:set>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?codiceIstanza=${istanza.id.codice}&';
				var _captionTab="${fn:replace(_tableTitle,'\'','\\\'')}";
			</script>		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}','../movimenti/create.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnElaborazione')}">						
					<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${istanza.id.codice}','')"><fmt:message key="button.elaborazione" /></a></li>						
				</c:if>
				<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>