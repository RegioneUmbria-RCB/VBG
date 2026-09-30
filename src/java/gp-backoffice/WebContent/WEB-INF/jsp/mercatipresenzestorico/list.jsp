<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.ParseException"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatiPresenzeStorico.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatiPresenzeStorico.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
		<div class="parametriDiv">
		<fieldset><legend><fmt:message key="label.filtri" /></legend>
		<c:if test="${mercatipresenzeStorico.autorizzazioni.id.codice!=null}">
			<span class="parametri">
              <fmt:message key="label.autorizzazione" /> : 
              <label><c:out value="${mercatipresenzeStorico.autorizzazioni.transientEstremiAut}"/></label>
        	</span>
        </c:if>
		<c:if test="${mercatipresenzeStorico.anagrafe.id.codice!=null}">
			<span class="parametri">
              <fmt:message key="label.anagrafe" /> : 
              <label><c:out value="${mercatipresenzeStorico.anagrafe.descrizioneRichiedente}"/></label>
        	</span>
        </c:if>
        <c:if test="${mercatipresenzeStorico.mercato.id.codice!=null}">
       		 <span class="parametri">
             <fmt:message key="label.manifestazione" /> : 
             <label><c:out value="${mercatipresenzeStorico.mercato.descrizione}"/></label>
    		 </span>
        </c:if>
        <c:if test="${mercatipresenzeStorico.mercatoUso.id.codice!=null}">
        	<span class="parametri">
            <fmt:message key="form.mercatiPresenzeStorico.mercatouso" /> : 
            <label><c:out value="${mercatipresenzeStorico.mercatoUso.descrizione}"/></label>
     		</span>
        </c:if>
        <c:if test="${mercatipresenzeStorico.posteggio.id.codice!=null}">
        	<span class="parametri">
            <fmt:message key="label.posteggio" /> : 
            <label><c:out value="${mercatipresenzeStorico.posteggio.codiceposteggio}"/></label>
     		</span>
        </c:if>
        <c:if test="${mercatipresenzeStorico.anno!=null && mercatipresenzeStorico.anno!=0}">
     		 <span class="parametri">
             <fmt:message key="form.mercatiPresenzeStorico.anno" /> : 
             <label><c:out value="${mercatipresenzeStorico.anno}"/></label>
             </span>
        </c:if>
        <c:if test="${not empty mercatipresenzeStorico.catMerc}">
     		 <span class="parametri">
             <fmt:message key="label.categoria_merceologica" /> : 
             <label><c:out value="${mercatipresenzeStorico.catMerc}"/></label>
             </span>
        </c:if>
        </fieldset>
        </div>
        <br class="clear"/>
        <form name="list" action="list.htm">
			<jmesa:springTableFacade
				id="filter_id" 
				items="${mercatiPresenzeStoricoList}" 
				var="list_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						
						<jmesa:htmlColumn property="anagrafe.descrizioneRichiedenteBreve" titleKey="label.anagrafe" filterable="false" />
						<jmesa:htmlColumn property="anagrafe.codicefiscale" titleKey="label.codicefiscale" filterable="false" />
						<jmesa:htmlColumn property="anagrafe.partitaiva" titleKey="label.partita_iva" filterable="false" />
						<jmesa:htmlColumn property="mercato.descrizione" titleKey="label.manifestazione" filterable="false" />
						<jmesa:htmlColumn property="mercatoUso.descrizione" titleKey="form.mercatiPresenzeStorico.mercatouso" filterable="false" />
						<jmesa:htmlColumn property="posteggio.codiceposteggio" titleKey="label.posteggio" filterable="false" />

						<jmesa:htmlColumn property="anno" titleKey="form.mercatiPresenzeStorico.anno" filterable="false" >
							<c:if test="${list_var.anno!=0}">
							${list_var.anno}
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="catMerc" titleKey="label.categoria_merceologica" filterable="false" />
		                <%-- 
		                	<jmesa:htmlColumn property="autorizzazioni.transientEstremiAut" titleKey="label.autorizzazione" filterable="false" />
		                --%>
		                <jmesa:htmlColumn property="autorizzazioni.autoriznumero" titleKey="label.numero_autorizzazione" filterable="false" />
		                <jmesa:htmlColumn property="autorizzazioni.autorizcomune.comune" titleKey="label.comune" filterable="false" />
		                <jmesa:htmlColumn property="autorizzazioni.autorizdata" titleKey="label.data_autorizzazione" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" />
		            	<jmesa:htmlColumn property="autorizzazioni.tipologiaregistro.trDescrizione" titleKey="label.registro" filterable="false" />
		            	<jmesa:htmlColumn property="numeropresenze" titleKey="label.presenze" filterable="false" />
		            	<jmesa:htmlColumn property="numPresProprietario" titleKey="label.presenze_come_proprietario" filterable="false" />
		            </jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
			<input type="hidden" value="${param.mercatipresenzeStorico.codiceAnagrafe}" name="mercatipresenzeStorico.codiceAnagrafe" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?mercatipresenzeStorico.codiceAnagrafe=${param.mercatipresenzeStorico.codiceAnagrafe}&';
			var _captionTab='<fmt:message key="label.presenze" />';
		</script>
        </div>
        <div id="functions">
        <ul>
        	<c:if test="${mercatipresenzeStorico.autorizzazioni.id.codice!=null}">
        	<li><a href="javascript:doHref('../mercatipresenzestorico/listStorico.htm?autId=${mercatipresenzeStorico.autorizzazioni.id.codice}')"><fmt:message key="button.presenze_storico" /></a></li>
        	</c:if>
        	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
        </ul>
        </div>
	</body>
</html>