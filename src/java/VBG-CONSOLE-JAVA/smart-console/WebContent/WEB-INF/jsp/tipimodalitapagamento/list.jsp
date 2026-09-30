<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipimodalitapagamento.label.lista_tipimodalitapagamento.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipimodalitapagamento.label.lista_tipimodalitapagamento.title" /></span>
	<div id="subcontent">
		<div>
			<spring-form:form commandName="cfgonericmd" name="inviodati" action="saveConfig.htm" id="inviodati"> 
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="cfgonericmd" />
			    </jsp:include>
				<table  width="100%">
					<tr class="titoloSezione">
						<td colspan="2">
							<fmt:message key="tipimodalitapagamento.parametri_generali" />
							
							<c:if test="${cfgonericmd.displayComuneLocalizzazione eq true}">
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="inventarioprocedimentioneri.label.comune" />
								</td>
								<td>
									<%-- <c:if test="${cfgonericmd.displayComuniResponsable eq true}"> --%>
										<spring-form:select path="comuneLocalizzazione.codicecomune" id="codicecomunecfg">
											<spring-form:option value=""><fmt:message key="inventarioprocedimentioneri.label.tuttiicomuni" /></spring-form:option>
											<spring-form:options items="${cfgonericmd.comuniResponsabile}" itemLabel="comune.comune" itemValue="comune.codicecomune" />
										</spring-form:select>
									<%-- </c:if>
									<c:if test="${cfgonericmd.displayComuniResponsable eq false}"> 
											<input type="hidden" name="cfgOneri.comune.codicecomune" value="${cfgonericmd.comuneLocalizzazione.codicecomune}"/>
											<input type="text" readonly="readonly" name="cfgOneri.comuneLocalizzazione.comune" value="${cfgonericmd.descrizioneComuneLocalizzazione}" />
									<%-- </c:if> --%>
							</c:if>
							<spring-form:hidden path="cfgOneri.comune.codicecomune" />
						</td>
					</tr>			
					<tr>
						<td width="25%">
							<fmt:message key="tipimodalitapagamento.infotext" />
							<init:help idHelp="infoPagamenti_help" textKey="tipimodalitapagamento.infotext.title"/>
						</td>
						<td>
							<spring-form:textarea path="cfgOneri.infoPagamenti" id="infoPagamenti" rows="8" cols="20" />
							<spring-form:hidden path="cfgOneri.id.idcomune" />
							<spring-form:hidden path="cfgOneri.id.codice" />
							<input type="hidden" name="save" id="save" value="0"/>
						</td>
					</tr>
					<tr>
						<td width="25%">
							<fmt:message key="tipimodalitapagamento.onerientiterzi.pagamentoonline" />
						</td>
						<td>
							<spring-form:checkbox path="cfgOneri.flagOnlineEntiTerzi" id="flagOnlineEntiTerzi"/>
							<init:help idHelp="flagOnlineEntiTerzi_help" textKey="tipimodalitapagamento.onerientiterzi.pagamentoonline.title"/>
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div id="functions">
								<ul>
									<li><a href="javascript:doSubmit('saveConfig.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>
								</ul>
							</div>
						</td>
					</tr>			
					<tr class="titoloSezione">
						<td colspan="2">
							<fmt:message key="tipimodalitapagamento.label.lista_tipimodalitapagamento" />
						</td>
					</tr>			
				</table>
			</spring-form:form>
		</div>
		<form name="jmesaform" action="list.htm">
			<div>
				<jmesa:springTableFacade
					id="tipimodalitapagamento_id" 
					items="${tipimodalitapagamentoList}" 
					var="tipimodalitapagamento_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
	                           	<a href="view.htm?codice=${tipimodalitapagamento_var.id.codice}">${tipimodalitapagamento_var.id.codice}</a>
	                        </jmesa:htmlColumn>								
							<jmesa:htmlColumn property="mpDescrestesa" titleKey="label.descrizione" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${tipimodalitapagamento_var.id.codice}" title="<fmt:message key="label.edit.record" />${tipimodalitapagamento_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				 </jmesa:springTableFacade>
			</div>
			<div id="functions">
				<ul>
					<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
				</ul>
			</div>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="tipimodalitapagamento.label.lista_tipimodalitapagamento.title" />';
			jQuery(document).ready(function(){
				var editor = initTextEditors('#infoPagamenti');
				jQuery('#codicecomunecfg').change(function(){
					jQuery('#inviodati').get(0).setAttribute('action', 'list.htm');
					if(checkEditing()){
						jQuery('#save').val('1');
					}
					jQuery('#inviodati').submit();
				});
				jQuery('#flagOnlineEntiTerzi').change(setEditing);
				editor.then(function(editors){
					if(editors.length > 0){
						editors[0].on('change',setEditing);
					}
				});
			});

			var editing = false;
			var setEditing = function(){
				editing = true;
			};

			var checkEditing = function(){
				if(editing){
					return confirm("<fmt:message key='tipimodalitapagamento.msg.confirmsaveonericfg' />");
				}
				else{
					return false;
				}
			};
		</script>
	</div>
</body>
</html>