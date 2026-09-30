<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table" style="width: 100%;">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.codice" /></td>
				<td><fmt:message key="label.descrizione" /></td>
				<td><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
			<tr>
					<td colspan="3">Nessun metadato configurato</td>
			</tr>					
			</c:if>
			<c:forEach items="${list}" var="md_var" varStatus="a">
				<tr class="${((a.index%2)==0)? 'odd':'even'}">
					<td>${md_var.id.fkidmetadatidizbase}</td>
					<td>${md_var.metadato.label}</td>
					<td>
						<a class="eliminaRiga"
							href="javascript:ajaxEliminaMetadato('${md_var.id.fkidmetadatidizbase}')"
								title="<fmt:message key="label.elimina" />"> <label><fmt:message
								key="label.azioni" /></label> 
						</a>
				    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${not empty listMDB}">
	<div>
	<table style="width: 310px;">
	<tr><td>
	<select name="listametadati" id="listametadati_id">
		<option value=""><fmt:message key="label.select.default" /></option>
		<c:forEach items="${listMDB}" var="mdb">
			<option value="${mdb.id}">${mdb.label}</option>
		</c:forEach>
	</select>	
	<a style="float: right;" class="addColumn"
		href="javascript:ajaxAggiungiMetadato('listametadati_id')"
		title="<fmt:message key="label.add" />"> <label><fmt:message key="label.azioni" /></label> 
	</a></td></tr>
	</table>
	</div>
	</c:if>
</div>