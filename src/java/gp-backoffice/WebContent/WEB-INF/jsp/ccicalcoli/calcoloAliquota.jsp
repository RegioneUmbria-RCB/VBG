<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti"%>
<%@page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"
%>
<%@page
	import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"
%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Determinazione dell'aliquota</title>


</head>
<body>
	<script type="text/javascript">
		dojo.require("dijit.form.NumberTextBox");
		dojo.require("dijit.form.CheckBox");
		dojo.require("dijit.form.Select");
		
		function initCalcoloAliquota(){
			//eventi delle select
			var field = dijit.byId('destinazione');
			dojo.connect(field,"onChange",caricaCoefficiente);
			field = dijit.byId('tipointervento');
			dojo.connect(field,"onChange",caricaCoefficiente);
			//eventi campo coefficiente
			field = dijit.byId('coefficiente');
			dojo.connect(field,"onChange",calcolaCoefficienteTotale);
			//eventi radio buttons
			<c:forEach items="${causaliriduzionit }" var="causridt">
				<c:forEach items="${causridt.ccCausaliriduzionirs }" var="causridr">
					field = dijit.byId("riduzione_${causridt.id.codice }_${causridr.id.codice}");
					dojo.connect(field,"onChange",calcolaCoefficienteTotale);
				</c:forEach>
			</c:forEach>		
		}

		function calcolaCoefficienteTotale(){
			var coeffField = dijit.byId('coefficiente');
			var totCoeff = coeffField.value;
			if(!totCoeff){
				totCoeff = 0.0; 
			}
			var field, idField;
			<c:forEach items="${causaliriduzionit }" var="causridt">
				<c:forEach items="${causridt.ccCausaliriduzionirs }" var="causridr">
					field = dijit.byId("riduzione_${causridt.id.codice }_${causridr.id.codice}");
					if(field.checked){
						totCoeff *= Number(field.value);
						idField = dojo.byId("idcausaleriduzioner_${causridt.id.codice }");
						if(idField){
							idField.value= "${causridr.id.codice}";
						}
					}
				</c:forEach>
			</c:forEach>		
			var totCoeffField = dijit.byId('coefficientetot');
			totCoeffField.setValue(totCoeff);
			var costoCostruzioneField = dijit.byId('costocostruzione');
			var totContributoField = dijit.byId('contributotot');
			totContributoField.setValue(totCoeff * costoCostruzioneField.value);
		}
		
		function caricaCoefficiente(){
			var destVal = dijit.byId('destinazione').value;
			var tipoVal = dijit.byId('tipointervento').value;
			var coeffVal = Number(dojo.byId('validitacoefficiente').value);
			if(destVal != "" && tipoVal != ""){
				var ts = new Date().getTime();	
				var sendData = {
					_ts: ts,
					idcomunealias: '<%= ORMHelper.getIdcomuneAlias()%>',
					idDestinazione: destVal,
					idTipoIntervento: tipoVal,
					idValiditaCoefficiente: coeffVal
				};
				jQuery.ajax({
					url: '<%=request.getContextPath()%>/ccicalcoli/caricaCoefficiente.htm',
					dataType: 'json',
					data: sendData,
					success: caricaCoefficienteCallback,
					error: ajaxFailure
				});
			}
			else{
				var coeffField = dijit.byId('coefficiente');
				coeffField.setValue("");
				calcolaCoefficienteTotale();
			}
		}
		
		function caricaCoefficienteCallback(data, status, xhr){
			var coefficiente = data.coefficiente;
			var field = dijit.byId('coefficiente');
			field.setValue(coefficiente);
			calcolaCoefficienteTotale();
		}
		
		function ajaxFailure(xhr,  textStatus, errorThrown){
			var msg = xhr.responseText;
			if(!msg || msg == ''){
				msg = "Si è verificato un'errore nella comunicazione con il server.";
			}
			displayError(msg);
		}
		
		function displayError(msg){
			alert(msg);
		}
		
		function salvaCalcoloOneri(){
			var destVal = dijit.byId('destinazione').value;
			var tipoVal = dijit.byId('tipointervento').value;
			var errMsg = "";
			if(!destVal){
				errMsg += "Selezionare una destinazione.\r\n";
			}
			if(!tipoVal){
				errMsg += "Selezionare un tipo intervento.\r\n";
			}
			if(!errMsg){
				document.inviodati.submit();				
			}
			else{
				displayError(errMsg);
			}
		}
		
		dojo.addOnLoad(initCalcoloAliquota);
		dojo.addOnLoad(caricaCoefficiente);
	</script>
	<style type="text/css">
		/* stile tabelle */
		.TabellaOneri TH{
			border:thin solid #333333;
			background-color: #CCDDFF;
		}
		.TabellaOneri TD{
			border:thin solid #333333;
			background-color: white;
			empty-cells: hide;
			text-align: center;
		}
		.TabellaOneri TD.totali{
			border:medium solid #333333;
			background-color: #FDD05F;
			font-weight: bold;
		}
		/* stile campi di input nelle tabelle */
		.inputOneri {
			padding-left: 0px;
			padding-right: 0px;
			background-color: transparent;
		}
		.inputOneri INPUT{
			border: 1px solid transparent ;
			/*width: 97%;*/
			height: 12px;
			text-align: right;
			background-color: transparent;
		}
		.inputOneri DIV{
			background-color: transparent;
		}
	</style>
<span class="titoloPagina"> Calcolo del coefficiente per stabilire la quota di contributo relativa al costo di costruzione</span>
<%-- 
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
	--%>
	<div id="subcontent">
		<spring-form:form 	commandName="aliquotaCommand" name="inviodati" action="salvaContributo.htm">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="calcolocoefficiente" />
			</jsp:include>
			
			<input type="hidden" name="idValiditaCoefficiente" id="validitacoefficiente" value="${validitacoefficiente.id.codice }" />
			<input type="hidden" name="idContributoCalcolo" id="contributocalcolo" value="${calcolotcontributo.id.codice }" />
		
			<div class="UpdatePanel" id="UpdatePanel1">
	
				<div id="pnlDestinazioni">
						<table style="min-width: 550px; margin-left: 10px;">
							<tr>
								<td width="40%">	
									<label class="obbligatorio" for="destinazione" id="Label1">*Destinazione</label>
								</td>
								<td width="60%">
									<select name="idDestinazione" onchange="" id="destinazione" dojoType="dijit.form.Select" style="width: 300px;">
										<option value="">&nbsp;</option>
										<c:forEach items="${destinazioni }" var="dest">
											<c:if test="${dest.id.codice == calcolotcontributo.ccDestinazioni.id.codice}">
												<c:set value="selected='selected'" var="selectedString"></c:set>
											</c:if>
											<c:if test="${dest.id.codice != calcolotcontributo.ccDestinazioni.id.codice}">
												<c:set value="" var="selectedString"></c:set>
											</c:if>
											<option ${selectedString } value="${dest.id.codice}">${dest.destinazione }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
				</div>
				
				<fieldset style="min-width: 552px;">
					<legend class="titoloSezione"><span>Coefficiente calcolato in base a: tipo intervento</span></legend>
				
				
					<div id="pnlTipoIntervento">
						<table style="min-width: 550px;">
							<tr>
								<td width="40%">
									<label class="obbligatorio" for="tipointervento" id="Label2">*Tipo intervento</label>
								</td>
								<td width="60%">
									<select name="idTipoIntervento" onchange="" id="tipointervento" dojoType="dijit.form.Select" style="width: 300px;">
										<option value="">&nbsp;</option>
										<c:forEach items="${tipiintervento }" var="tint">
											<c:if test="${tint.id.codice == idtipointervento}">
												<c:set value="selected='selected'" var="selectedString"></c:set>
											</c:if>
											<c:if test="${tint.id.codice != idtipointervento}">
												<c:set value="" var="selectedString"></c:set>
											</c:if>
											<option ${selectedString } value="${tint.id.codice}">${tint.intervento }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td width="40%">
									<label for="coefficiente" id="Label4">Coefficiente</label>
								</td>
								<td width="60%">
									<input class="inputOneri" name="coefficiente" value="" id="coefficiente" style="text-align: right;" type="text" readonly="readonly" dojoType="dijit.form.NumberTextBox" invalidMessage="Coefficiente non valido" constraints="{min:0,max:100000,places:2}"/>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
				<c:forEach items="${causaliriduzionit }" var="causridt">
					<fieldset style="min-width: 552px;">
						<legend class="titoloSezione"><span>${causridt.descrizione}</span></legend>
						<input type="hidden" name="idcausaleriduzioner_${causridt.id.codice }" id="idcausaleriduzioner_${causridt.id.codice }" value=""/>
						<table style="min-width: 550px;">
							<c:forEach items="${causridt.ccCausaliriduzionirs }" var="causridr">
								<c:forEach items="${calcolotcontributo.ccIcalcolotcontributoRiduzs }" var="iriduz">
									<c:if test="${iriduz.ccCausaliriduzionir.id.codice == causridr.id.codice}">
										<c:set value="checked='checked'" var="checkedString"></c:set>
									</c:if>
									<%-- 
									<c:if test="${iriduz.ccCausaliriduzionir.id.codice != causridr.id.codice}">
										<c:set value="" var="checkedString"></c:set>
									</c:if>
									--%>
								</c:forEach>
								<tr>
									<td width="40%">${causridr.descrizione}</td>
									<td width="10%" align="char">${causridr.riduzioneperc}</td>
									<td width="50%" align="left">
										<input type="radio" ${checkedString} name="riduzione_${causridt.id.codice }" id="riduzione_${causridt.id.codice }_${causridr.id.codice}" value="${causridr.riduzioneperc}" dojoType="dijit.form.RadioButton" />
									</td>
								</tr>
								<c:set value="" var="checkedString"></c:set>
							</c:forEach>
						</table>
					</fieldset>
				</c:forEach>
				<fieldset style="min-width: 552px;">
					<legend class="titoloSezione"><span>Totale contributo relativo al costo di costruzione</span></legend>
						<div>
							<table style="min-width: 550px;">
								<tr>
									<td width="40%">
										<label for="coefficientetot">Coefficiente Tot.</label>
									</td>
									<td width="60%">
										<input name="coefficientetot" value="" id="coefficientetot" class="inputOneri" readonly="readonly" style="text-align: right;" type="text" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
									</td>
								</tr>
								<tr>
									<td width="40%">
										<label for="costocostruzione">Costo di costruzione</label>
									</td>
									<td width="60%">
										<input name="costocostruzione" value="${calcolotcontributo.costocEdificio }" id="costocostruzione" class="inputOneri" readonly="readonly" style="text-align: right;" type="text" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:1000000000,places:2}"/> €
									</td>
								</tr>
								<tr>
									<td width="40%">
										<label for="contributotot">Contributo</label>
									</td>
									<td width="60%">
										<input name="contributotot" value="${calcolotcontributo.ccIcalcolotot.quotacontribTotale }" id="contributotot" class="inputOneri" readonly="readonly" style="text-align: right;" type="text" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/> €
									</td>
								</tr>
							</table>
						</div>
				</fieldset>
			</div>
		</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<li>
			<a href="javascript:salvaCalcoloOneri();">
				Salva
			</a>
		</li>
		<li><a
			href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"
		><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
<%-- 
<script type="text/javascript">
	function mergeDocument() {
		document.location.href = 'stampaDocumento.htm?'
				+ getQueryStringFromForm(document.inviodati);
	}
</script>
--%>

</body>
</html>