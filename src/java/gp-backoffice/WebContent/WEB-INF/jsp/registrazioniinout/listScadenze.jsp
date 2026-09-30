<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniFilter.title.listscadenze" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniFilter.title.listscadenze" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../registrazioniinout/searchScadenze" />
		</jsp:include> 
        <div class="parametriDiv">
        <fieldset><legend><fmt:message key="label.filtri" /></legend>
             <c:if test="${registrazioniInOutCommand.filter.registrazioniCausali.id.codice!=null}">
             	<span class="parametri">
                   <fmt:message key="form.registrazioniFilter.registrazioniCausali" /> : <label>${registrazioniInOutCommand.filter.registrazioniCausali.descrizione}</label>
              	</span>
             </c:if>              
             <c:if test="${registrazioniInOutCommand.filter.conti.id.codice!=null}">
              	<span class="parametri">
                 	<fmt:message key="form.registrazioniFilter.conti" /> : <label>${registrazioniInOutCommand.filter.conti.descrizioneConto}</label>
                </span>
             </c:if>
             <c:if test="${registrazioniInOutCommand.filter.dataInizio!=null || registrazioniInOutCommand.filter.dataFine!=null}">
	              <span class="parametri">
	             	  <fmt:message key="form.registrazioniFilter.scadenza" />:
	              	  <c:if test="${registrazioniInOutCommand.filter.dataInizio!=null }">
	                 	 <fmt:message key="form.registrazioniFilter.data.inizio" /> : <label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniInOutCommand.filter.dataInizio}"/></label>
	                  </c:if>
	                  <c:if test="${registrazioniInOutCommand.filter.dataFine!=null}">
	       	            <fmt:message key="form.registrazioniFilter.data.fine" /> : <label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniInOutCommand.filter.dataFine}"/></label>
	       	          </c:if>
	              </span> 
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.anagrafe.id.codice!=null}">
	              <span class="parametri">
	                  <fmt:message key="form.registrazioniFilter.anagrafe" /> : <label>${registrazioniInOutCommand.filter.anagrafe.descrizioneRichiedente}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.mercati.id.codice!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioniFilter.mercati" /> : <label>${registrazioniInOutCommand.filter.mercati.descrizione}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.mercatiUso.id.codice!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioni.mercatiUso" /> : <label>${registrazioniInOutCommand.filter.mercatiUso.descrizione}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.posteggio.id.codice!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioni.mercati.posteggio" /> : <label>${registrazioniInOutCommand.filter.posteggio.codiceposteggio}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.alberoproc.id.codice!=null}">
	              <span class="parametri">
	                <fmt:message key="form.registrazioniFilter.alberoproc" /> : <label>${registrazioniInOutCommand.filter.alberoproc.scDescrizione}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.amministrazioni.id.codice!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioniFilter.amministrazioni" /> : <label>${registrazioniInOutCommand.filter.amministrazioni.amministrazione}</label>
	             </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.importo!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioniFilter.importo.maggiore" /> : <label>${registrazioniInOutCommand.filter.importo}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.saldo!=null}">
	              <span class="parametri">
	                 <fmt:message key="form.registrazioniFilter.daincassare.maggiore" /> : <label>${registrazioniInOutCommand.filter.saldo}</label>
	              </span>
              </c:if>
              <c:if test="${registrazioniInOutCommand.filter.registrazioniCausali.id.codice==null 
                   && registrazioniInOutCommand.filter.conti.id.codice==null 
                   && registrazioniInOutCommand.filter.dataInizio==null && registrazioniInOutCommand.filter.dataFine==null 
                   && registrazioniInOutCommand.filter.anagrafe.id.codice==null 
                   && registrazioniInOutCommand.filter.mercati.id.codice==null
                   && registrazioniInOutCommand.filter.alberoproc.id.codice==null 
                   && registrazioniInOutCommand.filter.amministrazioni.id.codice==null
                   && registrazioniInOutCommand.filter.importo==null
                   && registrazioniInOutCommand.filter.posteggio.id.codice==null
                   && registrazioniInOutCommand.filter.saldo == null}">
                  <span class="parametri">
                  	<fmt:message key="form.registrazioniFilter.nofilter" />
                  </span>
               </c:if>
               </fieldset>
           </div>
	    <br class="clear"/>
        <div id="subcontent">
			<form name="registrazioniInOutCommand" action="listScadenze.htm">
				<jmesa:springTableFacade
					id="registrazioniImporti_id" 
					items="${registrazioniInOutCommand.scadenzeList}" 
					var="registrazioniImporti_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateRegImportiFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
							<jmesa:htmlColumn property="scadenza" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" titleKey="form.registrazioniFilter.scadenza" filterEditor="org.jmesa.custom.ScadenzaRegImportoCustomFilter"/>
                            <jmesa:htmlColumn property="registrazioni.progressivo" titleKey="form.registrazioniFilter.progressivo" />						
							<jmesa:htmlColumn property="registrazioni.anagrafe.descrizioneRichiedente" titleKey="form.registrazioniFilter.anagrafe" />
                            <jmesa:htmlColumn property="registrazioni.registrazioniCausali.descrizione" titleKey="form.registrazioniFilter.registrazioniCausali" />
                            <jmesa:htmlColumn property="registrazioni.mercatiD.mercati.descrizione" titleKey="form.registrazioni.mercati" />
							<jmesa:htmlColumn property="registrazioni.mercatiUso.descrizione" titleKey="form.registrazioni.mercatiUso" />
							<jmesa:htmlColumn property="registrazioni.mercatiD.codiceposteggio" titleKey="form.registrazioni.mercati.posteggio" />
                            <jmesa:htmlColumn property="conti.descrizioneConto" titleKey="form.registrazioniimporti.conti" />
							<jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo" headerStyle="text-align:right;"  style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniImporti_var.importo}</fmt:formatNumber>
							</jmesa:htmlColumn> 
							<jmesa:htmlColumn property="incassato" titleKey="form.registrazioniFilter.incassato" headerStyle="text-align:right;"  style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniImporti_var.incassato}</fmt:formatNumber>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="rimanenza" titleKey="form.registrazioniFilter.daincassare"  headerStyle="text-align:right;" style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniImporti_var.rimanenza}</fmt:formatNumber>
							</jmesa:htmlColumn>
                            <jmesa:htmlColumn property="registrazioni.id.codice" titleKey="label.edit.record" sortable="false" filterable="false">
								<a class="assegnaColumn" href="javascript:vaiAScadenze('${registrazioniImporti_var.registrazioni.progressivo}','${registrazioniImporti_var.registrazioni.anagrafe.id.codice}');" title="<fmt:message key="label.assegna" />" >
									<label><fmt:message key="label.assegna.image" /></label>
								</a>
								
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
			//<![CDATA[
				var _jmesaUrl='listScadenze.htm?';
				var _captionTab='<fmt:message key="form.registrazioniFilter.title.list" />';

				function vaiAScadenze(progressivo, codiceAnagrafe){
					var goToUrl = "../registrazioniinout/listScadenze.htm?filter.progressivo="+progressivo+
								"&filter.anagrafe.id.codice="+codiceAnagrafe;
					goToUrl = escape(goToUrl);
					doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
				}
			//]]> 
			</script>
		</div>
		<div id="functions">
			<ul>
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
			</ul>
		</div>
	</body>
</html>