<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_presenze_storico.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.gestione_presenze_storico" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatipresenzeStorico" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeStorico" />
    </jsp:include>
    <fieldset>
    		<legend>Dati Autorizzazione</legend>
		    <table>
		    	<tr>
		    		<td>
			    		<fmt:message key="label.numero" />:
			    	</td>
			    	<td>
			    		${mercatipresenzeStorico.autorizzazioni.autoriznumero}
			    	</td>
			    </tr>
		   		<tr>
		   			<td>
			    		<fmt:message key="label.data" />:
			    	</td>
			    	<td>
			    		<fmt:formatDate value="${mercatipresenzeStorico.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
			   		</td>
			   	</tr>
		    	<tr>
		    		<td>
			    		<fmt:message key="label.comune" />:
			    	</td>
				    <td>
				    	${mercatipresenzeStorico.autorizzazioni.autorizcomune.comune}
					</td>
				</tr> 
				<tr>
					<td>
			    		<fmt:message key="label.registro" />:
			    	</td>
			    	<td>
			    		${mercatipresenzeStorico.autorizzazioni.tipologiaregistro.trDescrizione}
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.anagrafe" />:
					</td>
					<td>
						${mercatipresenzeStorico.autorizzazioni.anagrafe.descrizioneRichiedente}
					</td>
				</tr>
			</table>
	</fieldset>
    <fieldset>
    <legend>Lista presenze nello storico</legend>
    	<form name="listStorico" action="listStorico.htm">
			<jmesa:springTableFacade
				id="filter_id" 
				items="${listStorico}" 
				var="list_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
		                <jmesa:htmlColumn property="id.codice" titleKey="label.codice">
		                	<a href="view.htm?codice=${list_var.id.codice}"> 
		                		${list_var.id.codice}
							</a>
		                </jmesa:htmlColumn>
		                <jmesa:htmlColumn property="mercato.descrizione" titleKey="label.manifestazione" filterable="false" />
		                <jmesa:htmlColumn property="mercatoUso.descrizione" titleKey="form.mercatiPresenzeStorico.mercatouso" filterable="false"/>
						<jmesa:htmlColumn property="posteggio.codiceposteggio" titleKey="label.posteggio" filterable="false" />
						<jmesa:htmlColumn property="anno" titleKey="label.anno" filterable="false" />
		            	<jmesa:htmlColumn property="numeropresenze" titleKey="label.presenze" filterable="false" />
		            	<jmesa:htmlColumn property="numPresProprietario" titleKey="label.presenze_come_proprietario" filterable="false" />
		            	<jmesa:htmlColumn property="catMerc" titleKey="label.categoria_merceologica" filterable="false" />
		            	<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.anagrafe" filterable="false" />
		            	<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${list_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${list_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
		            </jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listStorico.htm?autId=${mercatipresenzeStorico.autorizzazioni.id.codice}&';
			var _captionTab='<fmt:message key="label.gestione_presenze_storico" />';
		</script>
    </fieldset>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('create.htm?autId=${mercatipresenzeStorico.autorizzazioni.id.codice}')"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../mercatipresenzestorico/listDaAutorizzazione.htm?autId=${mercatipresenzeStorico.autorizzazioni.id.codice }','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
