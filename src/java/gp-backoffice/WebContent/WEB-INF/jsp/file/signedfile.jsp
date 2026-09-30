<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.informazioni_sul_certificato_del_file" /></title>	
</head>
<body>
<br class="clear"/>

<table border="0" width="800">
		<caption><b><fmt:message key="label.informazioni_sul_certificato_del_file" /></b></caption>
		<tr>	
			<td colspan="2" align="left"><b><fmt:message key="label.informazioni_principali_sul_file_firmato" /></b></td>	
		</tr>
		<tr>
			<td width="30%">
				<b><fmt:message key="label.firmatario" />:</b>
			</td>
			<td>
				${infoCertificato.subject}
			</td>
		</tr>
		<tr>
			<td>
				<b><fmt:message key="label.validita" />: </b>
			</td>
			<td >
				<b><fmt:message key="label.da" />: </b>
				${infoCertificato.validoDa}<b>
				<fmt:message key="label.a" />: </b>
				${infoCertificato.validoFinoAl}
				
				<c:if test="${infoCertificato.valido eq false}">
					<b class="error"><fmt:message key="label.certificato_scaduto" /></b>
					(${infoCertificato.descrizioneValido})
				</c:if>
				<c:if test="${infoCertificato.valido eq true}">
					(<b><fmt:message key="label.certificato_valido" /></b>)
				</c:if>

			</td>
		</tr>
		<tr>
			<td >
				<b><fmt:message key="label.revoca" />:</b>
			</td>
			<td  class="label">
				<c:if test="${infoCertificato.revocato eq false}">
					<fmt:message key="label.certificato_non_revocato" />
				</c:if>
				<c:if test="${infoCertificato.revocato eq true}">					
					<b class="error">(${infoCertificato.descrizioneRevocato})</b>
				</c:if>
			</td>
		</tr>
		<tr>	
			<td colspan="2" align="left"><b><fmt:message key="label.informazioni_secondarie_sul_file_firmato" /></b></td>	
		</tr>	
		<tr>
			<td >
				<b><fmt:message key="label.numero_di_serie" />: </b>
			</td>
			<td >
				${infoCertificato.serialNumber}
			</td>
		</tr>
		<tr>
			<td >
				<b><fmt:message key="label.tipo_algoritmo" />: </b>
			</td>
			<td  >
				${infoCertificato.tipoAlgoritmo}
			</td>
		</tr>
		<tr>
			<td >
				<b><fmt:message key="label.ente_certificatore" />: </b>
			</td>
			<td  >
				${infoCertificato.issuerDN}
			</td>
		</tr>
		<tr>
			<td >
				<b><fmt:message key="label.algoritmo_di_firma" />: </b>
			</td>
			<td  >
				${infoCertificato.signatureAlgoritmoName}
			</td>
		</tr>
		<tr>
			<td >
				<b><fmt:message key="label.indirizzo_crl" />: </b>
			</td>
			<td >
				${infoCertificato.addressCRL}
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
				<a href="${pageContext.request.contextPath}/file/downloadSignedFileClearContent.htm?fileId=${oggetto.id.codice}"><fmt:message key="label.visualizza_file" /></a>
			</td>
		<tr>
	</table>
</body>
</html>