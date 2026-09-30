<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${amministrazioni.id.codice==null}">
			<fmt:message key="amministrazioni.label.nuova_amministrazioni.title" />
		</c:if> 
		<c:if test="${amministrazioni.id.codice!=null}">
			<fmt:message key="amministrazioni.label.modifica_amministrazioni.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${amministrazioni.id.codice==null}">
	<fmt:message key="amministrazioni.label.nuova_amministrazioni.title" />
</c:if> 
<c:if test="${amministrazioni.id.codice!=null}">
	<fmt:message key="amministrazioni.label.modifica_amministrazioni.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../amministrazioni/view" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="amministrazioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="amministrazioni" />
    </jsp:include>
    
	    <c:choose>
				<c:when test="${amministrazioni.flagDisabilitato eq true}">
					<br class="clear"/>
					<div id="disabilitato_status_msg" class="alertLine">
			    		<b><fmt:message key="label.record_disabilitato"/></b>
			    	</div>
			    	<br class="clear"/>
				</c:when>
			</c:choose>
    
    <table>
		<tr>
			<td><fmt:message key="label.amministrazione" /></td>			
			<td colspan="3">
			<spring-form:hidden id="amministrazione_id_codice" path="id.codice" />
			<spring-form:input id="amministrazione_id" path="amministrazione" size="70" />
			<spring-form:errors path="amministrazione" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.partitaiva" /></td>
			<td colspan="3"><spring-form:input id="partitaiva_id" path="partitaiva" size="11" maxlength="11"/>
			<spring-form:errors path="partitaiva" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.ufficio" /></td>
			<td colspan="3"><spring-form:input id="ufficio_id" path="ufficio" size="50" />
			<spring-form:errors path="ufficio" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.referente" /></td>
			<td colspan="3"><spring-form:input id="referente_id" path="referente" size="50" />
			<spring-form:errors path="referente" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.indirizzo" /></td>
			<td colspan="3"><spring-form:input id="indirizzo_id" path="indirizzo" size="50" />
			<spring-form:errors path="indirizzo" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.citta" /></td>
			<td colspan="3"><spring-form:input id="citta_id" path="citta" size="50" />
			<spring-form:errors path="citta" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.cap" /></td>
			<td><spring-form:input id="cap_id" path="cap" size="5" />
			<spring-form:errors path="cap" cssClass="error"/></td>

            <td><fmt:message key="label.provincia" /></td>
			<td><spring-form:input id="provincia_id" path="provincia" size="2" />
			<spring-form:errors path="provincia" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.telefono1" /></td>
			<td><spring-form:input id="telefono1_id" path="telefono1" size="15" />
			<spring-form:errors path="telefono1" cssClass="error"/></td>

            <td><fmt:message key="amministrazioni.label.telefono2" /></td>
			<td><spring-form:input id="telefono2_id" path="telefono2" size="15" />
			<spring-form:errors path="telefono2" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.fax" /></td>
			<td colspan="3"><spring-form:input id="fax_id" path="fax" size="15" />
			<spring-form:errors path="fax" cssClass="error"/></td>
        </tr>

        <tr>
         	<td><fmt:message key="label.email" /></td>
			<td colspan="3"><spring-form:input id="email_id" path="email" size="70" />
			<spring-form:errors path="email" cssClass="error"/></td>
        </tr>
        <tr>
         	<td><fmt:message key="label.pec" /></td>
			<td colspan="3"><spring-form:input id="pec_id" path="pec" size="70" />
			<spring-form:errors path="pec" cssClass="error"/></td>
        </tr>
         <tr>
			<td><fmt:message key="label.web" /></td>
			<td colspan="3"><spring-form:input id="web_id" path="web" size="50" />
			<spring-form:errors path="web" cssClass="error"/></td>
		 </tr>
		 <tr>
			<td><fmt:message key="amministrazioni.label.codiceancitel" /></td>
			<td colspan="3"><spring-form:input id="codiceancitel_id" path="codiceancitel" size="50" />
			<spring-form:errors path="codiceancitel" cssClass="error"/></td>
		 </tr>
		 <%--
		 <tr>
			<td><fmt:message key="label.password" /></td>
			<td ><spring-form:input id="password_clear_id" path="passwordClear" size="50"/>
			<spring-form:errors path="passwordClear" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.password_md5" /></td>
			<td>
				<spring-form:input id="password_id" path="password" size="50" disabled="true"/>
				<spring-form:errors path="password" cssClass="error"/>
			</td>
		</tr>
		
        <tr>
            <td><fmt:message key="amministrazioni.label.flag_silenzio_diniego" /></td>
            <td colspan="3"><spring-form:select  id="flagSilenziodiniego_id"  path="flagSilenziodiniego"  >
                <spring-form:option value="true" ><fmt:message key="label.silenzio_assenso" /></spring-form:option>
                <spring-form:option value="false"><fmt:message key="label.silenzio_diniego" /></spring-form:option>
             </spring-form:select>				
			<spring-form:errors path="flagSilenziodiniego" cssClass="error"/></td>
        </tr>
        <tr>
			<td><fmt:message key="amministrazioni.label.codiceancitel" /></td>
			<td colspan="3"><spring-form:input id="codiceancitel_id" path="codiceancitel" size="50" />
			<spring-form:errors path="codiceancitel" cssClass="error"/></td>
		 </tr>
         <tr>
			<td><fmt:message key="amministrazioni.label.progressivoexport" /></td>
			<td colspan="3"><spring-form:input id="progressivoexport_id" path="progressivoexport" size="50" />
			<spring-form:errors path="progressivoexport" cssClass="error"/></td>
		 </tr>
         <tr>
			<td><fmt:message key="amministrazioni.label.flag_amministrazione_interna" /></td>
			<td colspan="3"><spring-form:checkbox id="flagAmministrazioneinterna_id" path="flagAmministrazioneinterna" onclick="javascript:amministrazioneinterna(flagAmministrazioneinterna_id)" />
			<init:help idHelp="help6" textKey="amministrazioni.help.flag_amministrazione_interna"/>
			<spring-form:errors path="flagAmministrazioneinterna" cssClass="error"/></td>
		</tr>
		 --%>
		<%-- 
		<c:if test="${isVerticalizzazioneProtocolloAttiva==true}">
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="amministrazioni.label.verticalizzazione_protocollo" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.protUo" /></td>
				<td colspan="3"><spring-form:input id="protUo_id" path="protUo" size="50" />
				<init:help idHelp="help4" textKey="help.protocollo_unita_organizzativa"/>
				<spring-form:errors path="protUo" cssClass="error"/></td>
			 </tr>
	         <tr>
				<td><fmt:message key="amministrazioni.label.protRuolo" /></td>
				<td colspan="3"><spring-form:input id="protRuolo_id" path="protRuolo" size="50" />
				<init:help idHelp="help5" textKey="help.protocollo_ruolo"/>
				<spring-form:errors path="protRuolo" cssClass="error"/></td>
			 </tr>
		</c:if>
		
		<c:if test="${isVerticalizzazioneSTCAttiva==true}">
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="amministrazioni.label.verticalizzazione_stc" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.stcIdnodo" /></td>
				<td colspan="3"><spring-form:input id="stcIdnodo_id" path="stcIdnodo" size="10" />
				<spring-form:errors path="stcIdnodo" cssClass="error"/></td>
			</tr>
	        <tr>
				<td><fmt:message key="label.stcIdente" /></td>
				<td colspan="3"><spring-form:input id="stcIdente_id" path="stcIdente" size="10" />
				<spring-form:errors path="stcIdente" cssClass="error"/></td>
			</tr>
		   	<tr>
				<td><fmt:message key="label.stcIdsportello" /></td>
				<td colspan="3"><spring-form:input id="stcIdsportello_id" path="stcIdsportello" size="10" />
	            <init:help idHelp="help9" textKey="help.stcIdsportello"/>
				<spring-form:errors path="stcIdsportello" cssClass="error"/></td>
			</tr>
		</c:if>
		--%>
	</table>
    <br />
    

	<script type='text/javascript'>
		$('amministrazione_id').focus();
	</script>
    <script type='text/javascript'>
			function amministrazioneinterna(flag){
            if(!flag.checked)
            {
                alert('<fmt:message key="amministrazioni.alert.flag_amministrazione_interna" />')
            }
		}				
		</script>	
    
</spring-form:form>
</div> 
<div id="functions">
<ul>
	<c:if test="${amministrazioni.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
    <c:if test="${amministrazioni.id.codice!=null}">
        <li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>	
	    <li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	    <%--
	    <li><a href="javascript:doHref('listemail.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="amministrazioni.button.lista_email" /></a></li>
        <li><a href="javascript:doHref('listamministrazionireferenti.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="amministrazioni.button.lista_amministrazioni_referenti" /></a></li>
	    
	    <c:if test="${amministrazioni.flagAmministrazioneinterna==true}">
        <li><a href="javascript:doHref('listresponsabili.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="amministrazioni.button.lista_responsabili" /></a></li>
        <li><a href="javascript:doHref('listruoli.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="amministrazioni.button.lista_ruoli" /></a></li>        
        </c:if>
        --%>
        <li>
			<c:choose>
				<c:when test="${amministrazioni.flagDisabilitato eq true}">
					<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.abilita"><fmt:param value="Amministrazioni"/></fmt:message>',document.inviodati)"><fmt:message key="button.abilita" /></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.disabilita"><fmt:param value="Amministrazioni" /></fmt:message>',document.inviodati)"><fmt:message key="button.disabilita" /></a>
				</c:otherwise>
			</c:choose>
		</li>
		<%--
		<li><a href="javascript:historySet('${_urlback}','../amministrazioni/listparametriprotocollo.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="amministrazioni.button.parametri_protocollo" /></a></li>
		 --%>	
	</c:if>	
	
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
