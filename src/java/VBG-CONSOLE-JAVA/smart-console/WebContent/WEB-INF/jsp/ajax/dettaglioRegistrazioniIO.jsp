<%@ include file="../includes/taglibs.jsp"%>
<table>
	<tr>
		<td><fmt:message key="form.registrazioniInOut.importo" /></td>
		<td>
			<input id="importo_hidden" type="hidden" name="entity.id.codice" value="${entity.id.codice }"/> 
			<c:if test="${not empty entity.importo }">
			<input id="importo_id" name="entity.importo" type="text" size="10" value="<fmt:formatNumber minFractionDigits="2" groupingUsed="false"  value="${entity.importo }"/>" readonly="readonly"/>
			</c:if>
			<c:if test="${empty entity.importo }">
			<input id="importo_id" name="entity.importo" type="text" size="10" onchange="setRimanenza(this);"/>
			</c:if>
		</td>
		<td><fmt:message key="form.registrazioniInOut.rimanenza" /></td>
		<td>
		 	<c:if test="${not empty entity.rimanenza and entity.rimanenza gt 0}">
		 	<input id="rimanenza_id" class="inputRed" name="entity.rimanenza" type="text" size="10" readonly="readonly" value="<fmt:formatNumber minFractionDigits="2" groupingUsed="false"  value="${entity.rimanenza }"/>" />
		 	</c:if>
		 	<c:if test="${empty entity.rimanenza or entity.rimanenza eq 0}">
		 	<input id="rimanenza_id" class="inputRed" name="entity.rimanenza" type="text" size="10" readonly="readonly"/>
		 	</c:if>
		 	<a href="javascript:setAllValues();" title="Assegna tutti">
			<img src="${pageContext.request.contextPath}/images/money_add.gif" alt="Assegna tutti"/>
			</a>
		</td>
		<td><fmt:message key="form.registrazioniInOut.datadistinta" /></td>
		<td>
			<input id="dataDistinta_id"
				name="entity.dataDistinta" type="text" size="10" maxlength="10"
				onblur="isValidDate(this,true);" value="<fmt:formatDate value="${entity.dataDistinta }" pattern="dd/MM/yyyy"/>"/>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="form.registrazioniInOut.tipimodalitapagamento" /></td>
		<td>
			<select name="entity.tipimodalitapagamento.id.codice">
				<c:forEach items="${tipimodalitapagamentoList}" var="item">
					<option value="${item.id.codice}">${item.mpDescrestesa}</option>
				</c:forEach>
			</select>
		</td>
		<td><fmt:message key="form.registrazioniInOut.riferimentipagamento" /></td>
		<td>
			<input id="riferimentiPagamento_id" name="entity.riferimentiPagamento" type="text" value="${entity.riferimentiPagamento }"/>
		</td>
         <td><fmt:message key="form.registrazioniInOut.dataincasso" /></td>
		<td>
			<input id="dataIncasso_id"
				name="entity.dataIncasso" type="text" size="10" maxlength="10"
				onblur="isValidDate(this,true);" value="<fmt:formatDate value="${entity.dataIncasso }" pattern="dd/MM/yyyy"/>"/>
		</td>
		<td><fmt:message key="form.registrazioniInOut.note" /></td>
		<td>
			<textarea id="note_id" name="entity.note" cols="20" rows="3">${entity.note }</textarea> 
		</td>
       
	</tr>
</table>