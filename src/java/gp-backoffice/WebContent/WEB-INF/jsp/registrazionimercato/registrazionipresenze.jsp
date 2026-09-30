<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatipresenzeT.registrazioni.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatipresenzeT.registrazioni.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../registrazionimercato/registrazionipresenze" />
		</jsp:include>
		<div id="subcontent">
		<span class="parametri"><fmt:message key="form.gestionepresenze.mercato" />:<label> ${mercato.descrizione}</label></span>	
		<span class="parametri"><fmt:message key="form.gestionepresenze.mercatiUso" />:<label> ${mercatoUso.descrizione}</label></span>
		<span class="parametri"><fmt:message key="form.gestionepresenze.data" />:<label><fmt:formatDate value="${giornoMercato}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></label></span><br />
		<spring-form:form commandName="mercatipresenzeT" name="inviodati">
		
		<script type="text/javascript">
		
		function assegna(controllo, valore){
			if(controllo.value==null || controllo.value==""){
				controllo.value = valore;
				return;
			}
			if(isNaN(controllo.value)){
				controllo.value=valore;
				return;
			}	
		}
	var _confirmMessage = '';
	
	<c:if test="${mercatipresenzeT.flagRegfatte eq false or mercatipresenzeT.flagRegfatte eq null}">
		_confirmMessage = '<fmt:message key="form.registrazionipresenze.confirm.message" />';
	</c:if>
	function valida(){

		<c:if test="${mercatipresenzeT.flagRegfatte eq true}">
			alert('<fmt:message key="form.registrazionipresenze.registrazioni.fatte" />');
		</c:if>

		return true;
	}
	var importi = new Array(); 

	function dettaglioRegistrazione(codice){
		var goToUrl = "../registrazioni/view.htm?codice="+codice;
		goToUrl = escape(goToUrl);
		doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
		
	}
		</script>
		<table>
		
		<tr>
			<td>
				<fmt:message key="form.registrazionipresenze.operatore" />
			</td>
			<td>
				<spring-form:input id="responsabile_id" path="responsabile.responsabile" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'responsabile_hidden')" size="67"/>
				<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile"/>
				<spring-form:errors path="responsabile.responsabile" cssClass="error"/> 
				<spring-form:hidden id="responsabile_hidden" path="responsabile.id.codice"  />
			</td>
		</tr>
		
		
		</table>
		<br/>
		
		
<script type="text/javascript">
	function searchAll(inputField,evt){
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
	   inputField.value='%';
	 }
	}
	function showHideRif(obj,divId){
		if(obj.value!=''){
			$('rif_parent').appear();
			$(divId).appear();
		}else{
			$(divId).fade();
			$('rif_parent').fade();			
		}	
	}
</script>
		<div class="jmesa" >
		
			<c:if test="${not empty mercatipresenzeT.listaPresenze or not empty assenzeConcessionariList}">
				<div class="titoloSezione">	
					<fmt:message key="form.mercatipresenzeD.registrazioni.nuove"/>
				</div>
			</c:if>
			<%
			int i=0;
			%>
			<c:if test="${not empty mercatipresenzeT.listaPresenze}">
			<span class="titoloTabella"><fmt:message key="form.mercatipresenzeD.registrazioni.spuntisti"/> - ${causaleAumento.descrizione}</span>
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="form.registrazioni.anagrafe" /></td>
					<td><fmt:message key="form.mercatipresenzeD.posteggio.codiceposteggio" /></td>
					<td><fmt:message key="form.mercatipresenzeD.importoSpuntista" /></td>
					<td><fmt:message key="form.mercatipresenzeD.tipimodalitapagamento" />
					<init:help idHelp="help_pagamento" textKey="form.mercatipresenzeD.tipimodalitapagamento.help"/>
					</td>
					<td>
					<span style="display: none" id="rif_parent">
						<fmt:message key="form.mercatipresenzeD.riferimentiPagamento" />
					</span>
					</td>
				</tr>
				</thead>
				<tbody class="tbody">
				<c:forEach items="${mercatipresenzeT.listaPresenze}" var="var_spuntista" varStatus="index">
				
					<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
						<td>${var_spuntista.occupante.descrizioneRichiedente}</td>
						<td>${var_spuntista.posteggio.codiceposteggio}</td>					
						<td style="text-align: right;">
							<fmt:formatNumber minFractionDigits="2" value="${var_spuntista.costoPosteggio.importo}" />
						</td>
						<td>
							<spring-form:select path="listaPresenze[${index.index}].incasso.tipimodalitapagamento.id.codice" onchange="showHideRif(this,'rif_${index.index}');return false">
								<spring-form:option value=""></spring-form:option>
								<spring-form:options itemLabel="mpDescrestesa" items="${tipipagamentolist}" itemValue="id.codice"/>
							</spring-form:select>
							
						</td>
						<td>
						<span style="display: none" id="rif_${index.index}">
							<spring-form:textarea path="listaPresenze[${index.index}].incasso.riferimentiPagamento"  cols="40" rows="2"/>
						</span>
						</td>										
					</tr>
				<%i++;%>
				
				</c:forEach>
				</tbody>
			</table>
			</c:if>			
			<%--
			<c:if test="${not empty assenzeConcessionariList}">
			<span class="titoloTabella"><fmt:message key="form.mercatipresenzeD.registrazioni.concessionari"/> - ${causaleDiminuzione.descrizione}</span>
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="form.registrazioni.anagrafe" /></td>
					<td><fmt:message key="form.mercatipresenzeD.posteggio.codiceposteggio" /></td>
					<td><fmt:message key="form.mercatipresenzeD.importoSpuntista" />&nbsp;<fmt:message key="label.valuta" /></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<c:forEach items="${assenzeConcessionariList}" var="var_concessionario" varStatus="index">
				
					<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
						<td>${var_concessionario.concessionario.descrizioneRichiedente}</td>
						<td>${var_concessionario.posteggio.codiceposteggio}</td>					
						<td>
							${var_concessionario.costoPosteggio.importo}
						</td>
					</tr>
				<%i++;%>
				
				</c:forEach>
				</tbody>
			</table>
			</c:if>
			--%>			
			<br/>
			<c:if test="${not empty registrazioniSpuntistiList or not empty registrazioniConcessionariList}">
				<div class="titoloSezione">	
					<fmt:message key="form.mercatipresenzeD.registrazioni.create"/>
				</div>
			</c:if>
			<c:if test="${not empty registrazioniSpuntistiList}">
			<span class="titoloTabella"><fmt:message key="form.mercatipresenzeD.registrazioni.spuntisti"/></span>
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="form.registrazioni.anagrafe" /></td>
					<td><fmt:message key="form.mercatipresenzeD.posteggio.codiceposteggio" /></td>
					<td><fmt:message key="form.registrazioni.progressivo" /></td>
					<td><fmt:message key="form.registrazioni.registrazioniCausali" /></td>
					<td align="right"><fmt:message key="form.registrazioni.importo" />&nbsp;<fmt:message key="label.valuta" /></td>
					<td><fmt:message key="label.edit.record" /></td>
				</tr>
				</thead>
				<%
				i=0;
				%>
				<tbody class="tbody">
				<c:forEach items="${registrazioniSpuntistiList}" var="var_registrazione" varStatus="index">
				
				<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
					<td>${var_registrazione.anagrafe.descrizioneRichiedente}</td>
					<td>${var_registrazione.mercatiD.codiceposteggio}</td>
					<td>${var_registrazione.progressivo}</td>
					<td>${var_registrazione.registrazioniCausali.descrizione}</td>
					<td align="right"><fmt:formatNumber minFractionDigits="2" value="${var_registrazione.importo}" /></td>
					<td>
						<a class="dettaglioColumn" href="javascript:dettaglioRegistrazione('${var_registrazione.id.codice}');"  title="<fmt:message key="label.edit.record" /> ${var_registrazione.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
						</a> 
					</td>	
					</tr>
				<%i++;%>
				</c:forEach>
				</tbody>
			</table>
			</c:if>
			<%--
			<c:if test="${not empty registrazioniConcessionariList}">
			<span class="titoloTabella"><fmt:message key="form.mercatipresenzeD.registrazioni.concessionari" /></span>
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="form.registrazioni.anagrafe" /></td>
					<td><fmt:message key="form.mercatipresenzeD.posteggio.codiceposteggio" /></td>
					<td><fmt:message key="form.registrazioni.progressivo" /></td>
					<td><fmt:message key="form.registrazioni.registrazioniCausali" /></td>
					<td><fmt:message key="form.registrazioni.importo" />&nbsp;<fmt:message key="label.valuta" /></td>
					<td><fmt:message key="label.edit.record" /></td>
				</tr>
				</thead>
				<%
				i=0;
				%>
				<tbody class="tbody">
				<c:forEach items="${registrazioniConcessionariList}" var="var_registrazione" varStatus="index">
				
				<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
					<td>${var_registrazione.anagrafe.descrizioneRichiedente}</td>
					<td>${var_registrazione.mercatiD.codiceposteggio}</td>
					<td>${var_registrazione.progressivo}</td>
					<td>${var_registrazione.registrazioniCausali.descrizione}</td>
					<td>${var_registrazione.importo}</td>
					<td>
						<a class="dettaglioColumn" href="javascript:dettaglioRegistrazione('${var_registrazione.id.codice}');"  title="<fmt:message key="label.edit.record" /> ${var_registrazione.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
						</a> 
					</td>	
					</tr>
				<%i++;%>
				</c:forEach>
				</tbody>
			</table>
			</c:if>
			--%>
			</div>
			<input type="hidden" name="mercati.id.codice" value="<%=request.getParameter("mercati.id.codice")%>" />
			<input type="hidden" name="mercatouso.id.codice" value="<%=request.getParameter("mercatouso.id.codice")%>" />
			<input type="hidden" name="giornoMercato" value="<%=request.getParameter("giornoMercato")%>" />
			
			</spring-form:form>
		</div>
<br />
<br />
		<div id="functions">
			<ul>			
				<li><a href="javascript:if(valida()){doSubmit('sistemaContabilitaGiornoMercato.htm',_confirmMessage,document.inviodati);}"><fmt:message key="button.save" /></a></li>
			
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>