<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.manifestazione" /></title>
</head>
<body>
	<div class="titolo">
		<c:if test="${manifestazioniCommand.tipoManifestazione eq 'FM' }">
		<fmt:message key="label.nuovo-fiere-mostre" />
		</c:if>
		<c:if test="${manifestazioniCommand.tipoManifestazione eq 'FS' }">
		<fmt:message key="label.nuovo-feste-sagre" />
		</c:if>
	</div>
	<div class="descrizione"></div>
	<c:if test="${param.ret eq 0 }">
		<label style="color: green; font-weight: bold;"><fmt:message key='label.salvataggio-effettuato-con-successo' /></label>			
	</c:if>
	<spring-form:form commandName="manifestazioniCommand" action="salva.htm" method="post" name="viewForm" id="viewFormId">
		<input type="hidden" name="returnto" value="${returnto }" />
		<c:if test="${manifestazioniCommand.tipoManifestazione eq 'FS'}">
		<input type="hidden" name="tipoManifestazione" value="FS" />
			<table class="sezione_table" border="0">	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.data-inserimento' />*</td>
				<td colspan="3">
					<%-- 
					<spring-form:input path="festeSagre.dataInserimento" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" readonly="true"/>''
					<spring-form:errors path="festeSagre.dataInserimento" cssClass="validation_error" />
					--%>
					<spring-form:input path="festeSagre.dataInserimento" cssErrorClass="validation_error_input" maxlength="10" size="8"  readonly="true"/>
				</td>
			</tr>	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.denominazione' />*</td>
				<td colspan="3">
					<spring-form:hidden path="festeSagre.id.codice" />
					<c:set var="codiceManifestazione" value="${manifestazioniCommand.festeSagre.id.codice}" />
					<spring-form:input path="festeSagre.denominazione" cssErrorClass="validation_error_input" maxlength="1000" size="45" />
					<spring-form:errors path="festeSagre.denominazione" cssClass="validation_error" />
				</td>
			</tr>	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.numero-istanza' /></td>
				<td>
					<spring-form:input path="festeSagre.numeroIstanza" cssErrorClass="validation_error_input" />
					<spring-form:errors path="festeSagre.numeroIstanza" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.data-istanza' /></td>
				<td>
					<spring-form:input path="festeSagre.dataIstanza" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="festeSagre.dataIstanza" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.organizzatore' />*</td>
				<td>
					<spring-form:input path="festeSagre.organizzatore" cssErrorClass="validation_error_input" size="45" />
					<spring-form:errors path="festeSagre.organizzatore" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.num-prot-istanza' /></td>
				<td>
					<spring-form:input path="festeSagre.numProtocolloIstanza" cssErrorClass="validation_error_input" />
					<spring-form:errors path="festeSagre.numProtocolloIstanza" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.num-autorizzazione' /></td>
				<td>
					<spring-form:input path="festeSagre.numAutorizzazione" cssErrorClass="validation_error_input" />
					<spring-form:errors path="festeSagre.numAutorizzazione" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.data-autorizzazione' /></td>
				<td>
					<spring-form:input path="festeSagre.dataAutorizzazione" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="festeSagre.dataAutorizzazione" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.dal' />*</td>
				<td>
					<spring-form:input path="festeSagre.dal" maxlength="10" size="8" id="dal" cssClass="data" />
					<spring-form:errors path="festeSagre.dal" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.al' />*</td>
				<td>
					<spring-form:input path="festeSagre.al" maxlength="10" size="8" id="al" cssClass="data" />
					<spring-form:errors path="festeSagre.al" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.tipologia' />*</td>
				<td>
					<spring-form:select path="festeSagre.tipologia" cssErrorClass="validation_error_input">				
						<spring-form:option value=""></spring-form:option>
						<spring-form:option value="SAGRA DELL'UMBRIA"></spring-form:option>
						<spring-form:option value="FESTA POPOLARE"></spring-form:option>
					</spring-form:select>
					<spring-form:errors path="festeSagre.tipologia" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.luogo-svolgimento' />*</td>
				<td>
					<spring-form:input path="festeSagre.luogoSvolgimento" cssErrorClass="validation_error_input" size="45" />
					<spring-form:errors path="festeSagre.luogoSvolgimento" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.comune-svolgimento' />*</td>
				<td>
					<spring-form:select path="festeSagre.comuneSvolgimento.codicecomune" cssErrorClass="validation_error_input">
						<c:if test="${fn:length(comuni) gt 1 }">
						<spring-form:option value=""></spring-form:option>
						</c:if>
						<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
					</spring-form:select>
					<spring-form:errors path="festeSagre.comuneSvolgimento.codicecomune" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.comune-presentazione' />*</td>
				<td>
					<spring-form:select path="festeSagre.comune.codicecomune" cssErrorClass="validation_error_input">
						<c:if test="${fn:length(comuni) gt 1 }">
						<spring-form:option value=""></spring-form:option>
						</c:if>
						<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
					</spring-form:select>
					<spring-form:errors path="festeSagre.comune.codicecomune" cssClass="validation_error" />
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${manifestazioniCommand.tipoManifestazione eq 'FM'}">
		<input type="hidden" name="tipoManifestazione" value="FM" />
		<table class="sezione_table">
		<tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.data-inserimento' />*</td>
				<td colspan="3">
				    <%-- 
					<spring-form:input path="fiereMostre.dataInserimento" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="fiereMostre.dataInserimento" cssClass="validation_error" />
					--%>
					<spring-form:input path="fiereMostre.dataInserimento" cssErrorClass="validation_error_input" maxlength="10" size="8"  readonly="true"/>
				</td>
			</tr>	
			<td class="sezione_table_label"><fmt:message key='label.denominazione' />*</td>
			<td colspan="3">
				<spring-form:hidden path="fiereMostre.id.codice" />
				<c:set var="codiceManifestazione" value="${manifestazioniCommand.fiereMostre.id.codice}" />
				<spring-form:input path="fiereMostre.denominazione" cssErrorClass="validation_error_input" maxlength="1000" size="45" />
				<spring-form:errors path="fiereMostre.denominazione" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.numero-istanza' /></td>
			<td>
				<spring-form:input path="fiereMostre.numeroIstanza" cssErrorClass="validation_error_input" />
				<spring-form:errors path="fiereMostre.numeroIstanza" cssClass="validation_error" />
			</td>
			<td class="sezione_table_label"><fmt:message key='label.data-istanza' /></td>
			<td>
				<spring-form:input path="fiereMostre.dataIstanza" maxlength="10" size="8" cssClass="data" />
				<spring-form:errors path="fiereMostre.dataIstanza" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.organizzatore' />*</td>
			<td>
				<spring-form:input path="fiereMostre.organizzatore" cssErrorClass="validation_error_input" size="45" />
				<spring-form:errors path="fiereMostre.organizzatore" cssClass="validation_error" />
			</td>
			<td class="sezione_table_label"><fmt:message key='label.num-prot-istanza' /></td>
			<td>
				<spring-form:input path="fiereMostre.numProtocolloIstanza" cssErrorClass="validation_error_input" />
				<spring-form:errors path="fiereMostre.numProtocolloIstanza" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.num-autorizzazione' /></td>
			<td>
				<spring-form:input path="fiereMostre.numAutorizzazione" cssErrorClass="validation_error_input" />
				<spring-form:errors path="fiereMostre.numAutorizzazione" cssClass="validation_error" />
			</td>
			<td class="sezione_table_label"><fmt:message key='label.data-autorizzazione' /></td>
			<td>
				<spring-form:input path="fiereMostre.dataAutorizzazione" maxlength="10" size="8" cssClass="data" />
				<spring-form:errors path="fiereMostre.dataAutorizzazione" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label" valign="top"><fmt:message key='label.periodi' /></td>
			<td valign="top">
				<table>
					<c:forEach items="${manifestazioniCommand.periodi}" var="periodo" varStatus="i" begin="0" >
			        <tr id="periodo${i.index}">           
			            <td><fmt:message key='label.dal' /></td>
			            <td>
			            	<spring-form:input path="periodi[${i.index}].dal" id="dal${i.index}" maxlength="10" size="8" cssClass="data" />
			            	<spring-form:errors path="periodi[${i.index}].dal" cssClass="validation_error" />
			            </td>
			            <td><fmt:message key='label.al' /></td>
			            <td>
			            	<spring-form:input path="periodi[${i.index}].al" id="al${i.index}" maxlength="10" size="8" cssClass="data" />
			            	<spring-form:errors path="periodi[${i.index}].al" cssClass="validation_error" />
			            </td>
			            <td>
			             	<a href="javascript: eliminaPeriodo('${i.index}')" title="Elimina">X</a>
			            </td>
			        </tr>
			       	</c:forEach>
			       	<tr>
						<td><fmt:message key='label.dal' />*</td>
						<td>
							<spring-form:input path="periodo.dal" id="dal" maxlength="10" size="8" cssClass="data" />
							<spring-form:errors path="periodo.dal" cssClass="validation_error" />
						</td>		
						<td><fmt:message key='label.al' />*</td>
						<td>
							<spring-form:input path="periodo.al" id="al" maxlength="10" size="8" cssClass="data" />
							<spring-form:errors path="periodo.al" cssClass="validation_error" />
						</td>
					</tr> 
				</table>
			</td>
			<td class="sezione_table_label" valign="top"><fmt:message key='label.merceologie' />*</td>
			<td valign="top">
				<table>
					<c:forEach items="${manifestazioniCommand.merceologie}" var="merceologia" varStatus="i" begin="0" >
			        <tr id="merceologia${i.index}">           		     
			            <td>
			            	<spring-form:input path="merceologie[${i.index}].merceologia" id="merceologia${i.index}-input" size="32" readonly="true" />
			            	<spring-form:errors path="merceologie[${i.index}].merceologia" cssClass="validation_error" />
			            </td>
			            <td>
			             	<a href="javascript: eliminaMerceologia('${i.index}')" title="Elimina">X</a>
			            </td>
			        </tr>
			       	</c:forEach>
			       	<tr>
						<td>
							<spring-form:select path="merceologia.merceologia" id="merceologia" cssErrorClass="validation_error_input">
								<spring-form:option value=""></spring-form:option>
								<spring-form:option value="(01) Agricoltura,Silvicoltura,Zootecnia">(01) Agricoltura,Silvicoltura,Zootecnia</spring-form:option>
								<spring-form:option value="(02) Food, Bevande, Ospitalità">(02) Food, Bevande, Ospitalità</spring-form:option>
								<spring-form:option value="(03) Sport, Hobby, Intrattenimento, Arte">(03) Sport, Hobby, Intrattenimento, Arte</spring-form:option>
								<spring-form:option value="(04) Servizi Business, Commercio">(04) Servizi Business, Commercio</spring-form:option>
								<spring-form:option value="(05) Costruzioni, Infrastrutture">(05) Costruzioni, Infrastrutture</spring-form:option>
								<spring-form:option value="(06) Viaggi, trasporti">(06) Viaggi, trasporti</spring-form:option>
								<spring-form:option value="(07) Sicurezza, Antincendio, Difesa">(07) Sicurezza, Antincendio, Difesa</spring-form:option>
								<spring-form:option value="(08) Formazione, Educazione">(08) Formazione, Educazione</spring-form:option>
								<spring-form:option value="(09) Energia, Combustibili, Gas">(09) Energia, Combustibili, Gas</spring-form:option>
								<spring-form:option value="(10) Protezione dell'ambiente">(10) Protezione dell'ambiente</spring-form:option>
								<spring-form:option value="(11) Stampa, Packaging, Imballaggi">(11) Stampa, Packaging, Imballaggi</spring-form:option>
								<spring-form:option value="(12) Arredamento, Design d'interni">(12) Arredamento, Design d'interni</spring-form:option>
								<spring-form:option value="(13) Casalinghi, giochi, realistica">(13) Casalinghi, giochi, realistica</spring-form:option>
								<spring-form:option value="(14) Bellezza, Cosmetica">(14) Bellezza, Cosmetica</spring-form:option>
								<spring-form:option value="(15) Real Estate, Immobiliare">(15) Real Estate, Immobiliare</spring-form:option>
								<spring-form:option value="(16) Automobili, Motocicli">(16) Automobili, Motocicli</spring-form:option>
								<spring-form:option value="(17) Chimica">(17) Chimica</spring-form:option>
								<spring-form:option value="(18) Elettronica, Componenti">(18) Elettronica, Componenti</spring-form:option>
								<spring-form:option value="(19) Industria, Tecnologia, Meccanica">(19) Industria, Tecnologia, Meccanica</spring-form:option>
								<spring-form:option value="(20) Aviazione, Aerospaziale">(20) Aviazione, Aerospaziale</spring-form:option>
								<spring-form:option value="(21) IT e Telecomunicazioni">(21) IT e Telecomunicazioni</spring-form:option>
								<spring-form:option value="(22) Salute, Attrezzature Ospedaliere">(22) Salute, Attrezzature Ospedaliere</spring-form:option>
								<spring-form:option value="(23) Ottica">(23) Ottica</spring-form:option>
								<spring-form:option value="(24) Gioielli, Orologi, Accessori">(24) Gioielli, Orologi, Accessori</spring-form:option>
								<spring-form:option value="(25) Tessile, Abbigliamento, Moda">(25) Tessile, Abbigliamento, Moda</spring-form:option>
								<spring-form:option value="(26) Trasporti, Logistica, Navigazione">(26) Trasporti, Logistica, Navigazione</spring-form:option>
								<spring-form:option value="(27) Campionarie Generali">(27) Campionarie Generali</spring-form:option>
								<spring-form:option value="(28) Altro (specificare sotto il settore)">(28) Altro (specificare sotto il settore)</spring-form:option>
							</spring-form:select>
							<spring-form:errors path="merceologia.merceologia" cssClass="validation_error" />
						</td>			
					</tr> 
					<tr>
						<td>
							<spring-form:input path="merceologiaAltro.merceologia" id="merceologiaAltro" size="32" cssStyle="display:none" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.luogo-svolgimento' />*</td>
			<td colspan="3">
				<spring-form:input path="fiereMostre.luogoSvolgimento" cssErrorClass="validation_error_input" size="45" />
				<spring-form:errors path="fiereMostre.luogoSvolgimento" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.classificazione' />*</td>
			<td>
				<spring-form:select path="fiereMostre.classificazione" cssErrorClass="validation_error_input">
					<spring-form:option value=""></spring-form:option>
					<spring-form:option value="Fiera generale"></spring-form:option>
					<spring-form:option value="Fiera specializzata"></spring-form:option>
					<spring-form:option value="Mostra mercato"></spring-form:option>
					<spring-form:option value="Esposizione"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="fiereMostre.classificazione" cssClass="validation_error" />
			</td>
			
			<td id="td_qualifica_id" class="sezione_table_label"><fmt:message key='label.qualifica' />*</td>
			<td>
				<spring-form:select path="fiereMostre.qualifica" id="select_qualifica_id">
					<spring-form:option value=""></spring-form:option>
					<spring-form:option value="Internazionale"></spring-form:option>
					<spring-form:option value="Nazionale"></spring-form:option>
					<spring-form:option value="Regionale"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="fiereMostre.qualifica" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
		<td  class="sezione_table_label"><fmt:message key='label.tipologia' /></td>
			<td>
				<spring-form:select path="fiereMostre.tipologia" id="select_tipologia_id">
					<spring-form:option value=""></spring-form:option>
					<spring-form:option value="Locale"></spring-form:option>
					<spring-form:option value="Regionale"></spring-form:option>
					<spring-form:option value="Nazionale"></spring-form:option>
					<spring-form:option value="Internazionale"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="fiereMostre.tipologia" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.comune-svolgimento' />*</td>
			<td>
				<spring-form:select path="fiereMostre.comuneSvolgimento.codicecomune" cssErrorClass="validation_error_input">
					<c:if test="${fn:length(comuni) gt 1 }">
					<spring-form:option value=""></spring-form:option>
					</c:if>
					<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
				</spring-form:select>
				<spring-form:errors path="fiereMostre.comuneSvolgimento.codicecomune" cssClass="validation_error" />
			</td>
			<td class="sezione_table_label"><fmt:message key='label.ente-presentazione' />*</td>
			<td>
				<spring-form:select path="fiereMostre.comune.codicecomune" id="select_comune_id" cssErrorClass="validation_error_input">
					<c:if test="${fn:length(comuni) gt 1 }">
					<spring-form:option value=""></spring-form:option>
					</c:if>
					<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
				</spring-form:select>
				<spring-form:errors path="fiereMostre.comune.codicecomune" cssClass="validation_error" />
			</td>
		</tr>
	</table>
	</c:if>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<spring-security:authorize ifNotGranted="ROLE_READONLY">
			<input type="button" value="<fmt:message key='button.salva' />" id="salva" />
			<c:if test="${codiceManifestazione != null }">
			<input type="button" value="<fmt:message key='button.elimina' />" id="delete" />
			</c:if>
			</spring-security:authorize>
			<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		//$(".data").datepicker();
		
		$( function() {
		    $(".data").datepicker({
		    	buttonImage: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==",
                buttonImageOnly: true,
                showButtonPanel: true,
                changeMonth: true,
		        changeYear: true,
		        yearRange: "-50:+10"
		    });
		} );
		
		$("#salva").click(function(){
			document.viewForm.submit();
		});
		$("#delete").click(function(){
			if(confirm("<fmt:message key='alert.elimina' />")){
				window.location.replace("${pageContext.request.contextPath}/manifestazioni/delete.htm?tipo=${manifestazioniCommand.tipoManifestazione}&codice=${codiceManifestazione}");
			}
		});
		function eliminaPeriodo(id){
			$("#periodo"+id).hide();
			$("#dal"+id).val('');
			$("#al"+id).val('');
		}
		function eliminaMerceologia(id){
			$("#merceologia"+id).hide();
			$("#merceologia"+id+"-input").val('');
		}
		<%--le fiere dei suape non hanno il campo qualifica --%>
		if($("#select_comune_id option:selected").val() != 'REGU'){
			$("#select_qualifica_id").hide();
			$("#td_qualifica_id").hide();
		}
		$("#select_comune_id").on('change', function() {
			  if(this.value == 'REGU'){
				  $("#select_qualifica_id").show();
				  $("#td_qualifica_id").show();
			  }else{
				  $("#select_qualifica_id").hide(); 
				  $("#td_qualifica_id").hide();
			  }
		});		
		if($("#merceologia").val() == '(28) Altro (specificare sotto il settore)'){
			$("#merceologiaAltro").show();
		}
		$("#merceologia").on('change', function() {
			  if(this.value == '(28) Altro (specificare sotto il settore)'){
				  $("#merceologiaAltro").show();
			  }else{
				  $("#merceologiaAltro").hide(); 
			  }
		});
	</script>
	<c:choose>
	<c:when test="${returnto eq 'list'}">
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/manifestazioni/search.htm");
		});
	</script>
	</c:when>
	<c:otherwise>
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
	</c:otherwise>
	</c:choose>
</body>
</html>