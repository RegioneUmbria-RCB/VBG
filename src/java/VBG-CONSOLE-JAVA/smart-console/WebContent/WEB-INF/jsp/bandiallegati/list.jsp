<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.bandiallegati.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.bandiallegati.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>

		<div id="subcontent">			
			<span class="parametri"><fmt:message key="form.bandiallegati.title.bando" />: <label><c:out value="${bando.descrizione}"/></label></span>
			<br />
			<form name="bandiallegatiForm" action="list.htm">
				<jmesa:springTableFacade
					id="bandiallegati_id" 
					items="${bandiallegatiList}" 
					var="bandiallegati_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateBandiAllegatiFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${bandiallegati_var.id.codice}">${bandiallegati_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="descrizione" titleKey="form.bandiallegati.descrizione" width="60%"/>
							<jmesa:htmlColumn property="dataInserimento" width="5%" titleKey="form.bandiallegati.dataInserimento"  pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DatainserimentoCustomFilter" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="10%">
								<a id="posteggioDettaglio_id" class="dettaglioColumn" href="view.htm?codice=${bandiallegati_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${bandiallegati_var.descrizione}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<c:if test="${bandiallegati_var.oggetto.id.codice!=null}">							
								<a class="visualizzaDocColumn" href="../file/ajaxDownload.htm?fileId=${bandiallegati_var.oggetto.id.codice}"  title="<fmt:message key="label.visualizza" /> ${bandiallegati_var.oggetto.nomefile}">
						            <label><fmt:message key="label.visualizza.image" /></label>
					            </a>
					            </c:if>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${bando.id.codice}" name="codice" />
			</form>
			
			<script type="text/javascript">
			//<![CDATA[
				var _jmesaUrl='list.htm?codice=${bando.id.codice}&';
				var _captionTab='<fmt:message key="form.bandiallegati.title.list" />';			
		    //]]> 
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?codice=${bando.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../bandi/view.htm?codice=${bando.id.codice}','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>