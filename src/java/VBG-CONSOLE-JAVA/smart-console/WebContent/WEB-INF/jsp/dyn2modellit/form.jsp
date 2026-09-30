<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init-schededinamiche.js"></script>
	<title>
		<c:if test="${dyn2modellit.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2modellit.title" />
		</c:if> 
		<c:if test="${dyn2modellit.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2modellit.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${dyn2modellit.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2modellit.title" />
		</c:if> 
		<c:if test="${dyn2modellit.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2modellit.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellit/view" />
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="dyn2modellit" />
	    </jsp:include>
		<spring-form:form commandName="dyn2modellit" name="inviodati" action="view.htm">
			<div id="master">
				<table width="100%">
					<spring-form:hidden id="pk_id" path="id.codice" />
					<spring-form:hidden id="sw_id" path="software.codice" />
					<tr>
						<td>
							<fmt:message key="label.codice" />
						</td>
						<td>
							<spring-form:input id="codicescheda_id" path="codiceScheda" size="70" />
							<spring-form:errors path="codiceScheda" cssClass="error"/>
						</td>
					</tr>
					<tr>
						<td>
							Visualizzato online
						</td>
						<td>
							<spring-form:input id="descrizione_id" path="descrizione" size="70" />
							Questo è e' il nome che viene visualizzato nella scheda dei servizi online
							<spring-form:errors path="descrizione" cssClass="error"/>
						</td>
					</tr>
					<input type="hidden" name="basecontesti.id" value="IS" id="contesti_id"/>
			       	<tr>
						<td><fmt:message key="label.solo_lettura" /></td>
						<td><spring-form:checkbox id="flgReadonlyWeb_id" path="flgReadonlyWeb"/>
						<init:help idHelp="help_flgReadonlyWeb" textKey="dyn2modellit.help.flg_readonly_web"/>
						<spring-form:errors path="flgReadonlyWeb" cssClass="error"/></td>
			       	</tr>
			       
				</table>
			</div>
			<div id="functions">
				<ul>
					<c:if test="${dyn2modellit.id.codice==null}">
						<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
					</c:if>
					<c:if test="${dyn2modellit.id.codice!=null}">
						<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
						<li><a href="javascript:aggiungiCampo(${dyn2modellit.id.codice});"><fmt:message key="button.aggiungi_campo" /></a></li>
										
						<li><a href="javascript:historySet('${_urlback}','../dyn2modellit/viewFormule.htm?codice=${dyn2modellit.id.codice}','',document.inviodati)"><fmt:message key="button.formule" /></a></li>
						
						<li><a href="javascript:doHref('../dyn2modellit/separaRighe.htm?codiceModello=${dyn2modellit.id.codice}','')"><fmt:message key="button.separa_righe_modello" /></a></li>
						<li><a href="javascript:historySet('${_urlback }','../dyn2modellit/copy.htm?codice=${dyn2modellit.id.codice}','')"><fmt:message key="button.copia_modello" /></a></li>
						<li><a href="javascript:anteprimaModello(${dyn2modellit.id.codice},'<%=request.getContextPath()%>')"><fmt:message key="button.anteprima_modello" /></a></li>
						<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
					</c:if>
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
				</ul>
			</div>
			<div id="detail">
				<jmesa:springTableFacade
					id="dyn2modellid_id" 
					items="${dyn2modellidList}" 
					var="dyn2modellid_var"
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
	                           	<a href="javascript:dettaglioModelloD(${dyn2modellid_var.id.codice})">${dyn2modellid_var.id.codice}</a>
	                        </jmesa:htmlColumn>								
							<jmesa:htmlColumn property="posverticale" titleKey="label.riga" width="8%"/>
							<jmesa:htmlColumn property="posorizzontale" titleKey="label.colonna" width="8%" />
							<jmesa:htmlColumn property="dyn2Campi.nomecampo" titleKey="label.campo_dinamico">
								${dyn2modellid_var.dyn2Campi.nomecampo}
								<c:if test="${not empty dyn2modellid_var.dyn2Campi}">
									<a class="dettaglioColumn" style="float: inherit;" href="javascript:dettaglioCampoDinamico(${dyn2modellid_var.dyn2Campi.id.codice});" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</c:if>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="transientModelloDtipoTesto" titleKey="label.testo" />
							<jmesa:htmlColumn property="dyn2RegoleAttivo.descrizione" titleKey="label.regole_attivazione" >
								${dyn2modellid_var.dyn2RegoleAttivo.descrizione}
								<c:if test="${not empty dyn2modellid_var.dyn2RegoleAttivo}">
									<a class="dettaglioColumn" style="float: inherit;" href="javascript:dettaglioRegolaAttivazione(${dyn2modellid_var.dyn2RegoleAttivo.id.codice});" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</c:if>
							</jmesa:htmlColumn>
							<c:if test="${dyn2modellid_var.dyn2Modellit.modellomultiplo eq false }">
								<jmesa:htmlColumn width="5%"  property="flagMultipla" titleKey="label.riga_multipla" sortable="false" filterable="false">
										<input id="flagMultiploId${dyn2modellid_var.id.codice}" type="checkbox" onclick="aggiornaFlag(${dyn2modellid_var.id.codice })" ${dyn2modellid_var.flgMultiplo?'checked':''} />
								</jmesa:htmlColumn>
							</c:if>
							
							<jmesa:htmlColumn property="" titleKey="label.elimina" sortable="false" filterable="false" width="5%">
								<a class="eliminaRiga" href="javascript:eliminaCampo(${dyn2modellid_var.id.codice})" title="<fmt:message key="label.elimina" />${dyn2modellid_var.id.codice}">
									<label><fmt:message key="label.elimina.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</div>
			<script type='text/javascript'>
				$('codicescheda_id').focus();
				
				function dettaglioCampoDinamico(idcampo){
					var caller = 'dyn2CampiInputId_'+idcampo;
					var dettaglioCampoDinamico${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2campi/popupview.htm?codice="+idcampo+"&popupCaller="+caller,69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");			
			    }

				function dettaglioRegolaAttivazione(idReg){
					var caller = 'dyn2RegolaAttivoId_'+idReg;
					var dettaglioRegola${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2regole/popupview.htm?codice="+idReg,69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");			
			    }

				function dettaglioModelloD(id){
					var caller = 'dyn2ModellidId_'+id;
					var dettaglioModellod${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2modellid/popupview.htm?codice="+id+"&popupCaller="+caller+"&popup=true",69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");			
			    }

				function aggiungiCampo(idmt){
					var caller = 'dyn2ModellidId_'+idmt;
					var dettaglioCampoDinamico${param.idElemento}Win = window.open("<%=request.getContextPath()%>/dyn2modellid/popupcreate.htm?codiceModelloT="+idmt+"&popupCaller="+caller+"&popup=true",69,"+status=1,menubar=0,scrollbars=1,width=800, height=600");			
			    }

				function eliminaCampo(idmt){
					if(confirm("L'elemento selezionato sarà cancellato: procedere?")){
						var caller = 'dyn2ModellidId_'+idmt;
						doHref("<%=request.getContextPath()%>/dyn2modellid/delete.htm?id.codice="+idmt,"");
					}			
			    }
			    
				function aggiornaFlag(id){
					if(checkConfirmMessage('<fmt:message key="alert.verrano_aggiornate_modelli_D_riga_uguale" />'))
					{
						javascript:doHref('../dyn2modellid/changeFlagMultiplo.htm?codice='+id,'');
					}
				}

				function refreshList(){
					doHref("<%=request.getContextPath()%>/dyn2modellit/view.htm?id.codice=${dyn2modellit.id.codice}","");
				}
				
				var _jmesaUrl='view.htm?id.codice=${dyn2modellit.id.codice}&';
				var _captionTab='<fmt:message key="label.lista_dyn2modellid.title" />';
			</script>	
		</spring-form:form>
	</div>
</body>
</html>