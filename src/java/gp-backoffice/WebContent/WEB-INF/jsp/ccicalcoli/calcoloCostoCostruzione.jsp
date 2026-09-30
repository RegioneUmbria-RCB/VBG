<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcItabella4"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcItabella3"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcItabella2"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcItabella1"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.CcIcalcoliHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CcIcalcoli"%>
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
<title>Determinazione del costo di costruzione</title>


</head>
<body>
	<script type="text/javascript">
		dojo.require("dijit.form.NumberTextBox");
		dojo.require("dijit.form.CheckBox");
		dojo.require("dijit.form.Select");
		
		var classiEdifici = new Array();
		<c:forEach items="${classiedificio}" var="classe" >
			classiEdifici.push({
				descrizione: '${classe.descrizione}', 
				id: ${classe.id.codice}, 
				da: ${classe.da}, 
				a: ${classe.a}, 
				maggiorazione: ${classe.maggiorazione}
				});
		</c:forEach>
		
		var numRowsTab1 = 5;
		function calcolaTabella1(){
			var totMq = 0;
			var field;
			for(i = 0; i < numRowsTab1; i++){
				field = dijit.byId('tab1_' + i + '1');
				if(field){
					totMq += field.value;
				}
			}
			field = dijit.byId('tab1_tot0');
			field.setValue(totMq);
			var field1;
			var field2;
			var totIncr = 0;
			var perc;
			var incr;
			var coeff;
			if(totMq > 0){
				for(i = 0; i < numRowsTab1; i++){
					field = dijit.byId('tab1_' + i + '1');
					field1 = dijit.byId('tab1_' + i + '2');
					field2 = dijit.byId('tab1_' + i + '4');
					coeff = dojo.byId('tab1_' + i + '3').value;
					if(field && field1){
						var perc = field.value/totMq;
						field1.setValue(perc);
						//field1.value = perc;
						var incr = perc*coeff;
						totIncr += incr;
						field2.setValue(incr);
						//field2.value = incr;
					}
				}
			}
			field = dijit.byId('tab1_tot1');
			field.setValue(totIncr);
			calcolaTabella2();
		} 
		
		function initTab1(){
			var field;
			for(i = 0; i < numRowsTab1; i++){
				field = dijit.byId('tab1_' + i + '1');
				if(field){
					dojo.connect(field,"onChange",calcolaTabella1);
				}
			}
		}
		
		var numRowsTab2 = 5;
		var numRowsTab3 = 4;
		function calcolaTabella2(){
			var totMq = 0;
			var field;
			for(i = 0; i < numRowsTab2; i++){
				field = dijit.byId('tab2_' + i + '0');
				if(field){
					totMq += field.value;
				}
			}
			field = dijit.byId('tab2_tot0');
			field.setValue(totMq);
			var mqSu = dijit.byId('tab1_tot0').value;
			var percSnrSu = 0;
			if(mqSu > 0){
				percSnrSu = totMq/mqSu*100;
			}
			var greaterThan;
			var lessThan;
			var rowIx = 0;
			var incr = 0;
			var fromCondition;
			var toCondition;
			for(i = 0; i < numRowsTab3; i++){
				greaterThan = dojo.byId('tab3_' + i + 'da').value;
				lessThan = dojo.byId('tab3_' + i + 'a').value;
				fromCondition = "true";
				if(greaterThan != undefined && greaterThan != ""){
					fromCondition = "(percSnrSu > greaterThan)";
				}
				toCondition = "true";
				if(lessThan != undefined && lessThan != ""){
					toCondition = "(percSnrSu <= lessThan)";
				}
				var conditionSatisfied = eval(fromCondition + " && " + toCondition);
				if(conditionSatisfied){
					rowIx = i;
					break;
				}
			}
			for(i = 0; i < numRowsTab3; i++){
				field = dijit.byId('tab3_' + i + '1');
				if(i == rowIx){
					field.setValue(percSnrSu);
					incr = Number(dojo.byId('tab3_' + rowIx + '2').value);
				}
				else{
					field.setValue("");
				}
			}
			field = dijit.byId('tab3_tot0');
			if(field){
				field.setValue(incr);
			}
			calcolaRiepilogoSuperfici();
			calcolaTotaleIncrementi();
		}
		
		function initTab2(){
			var field;
			for(i = 0; i < numRowsTab2; i++){
				field = dijit.byId('tab2_' + i + '0');
				if(field){
					dojo.connect(field,"onChange",calcolaTabella2);
				}
			}
		}
		
		function calcolaRiepilogoSuperfici(){
			var field = dijit.byId('tab1_tot0');
			var su = 0;
			if(field){
				su = field.value;
			}
			var snr = 0;
			field = dijit.byId('tab2_tot0');
			if(field){
				snr = field.value;
			}
			dijit.byId('tab4_su').setValue(su);
			dijit.byId('tab4_snr').setValue(snr);
			dijit.byId('tab4_snr60').setValue(snr * 0.6);
			dijit.byId('tab4_sc').setValue(su + snr * 0.6);
			if(ricalcolaTotaleCostoCostruzione){
				calcolaCostoCostruzione();
			}
		}
		
		var numRowsTab4 = 5;
		function calcolaTabella4(){
			var totPerc = 0;
			var field;
			for(i = 0; i < numRowsTab4; i++){
				field = dijit.byId('tab4_' + i + '0');
				if(field && field.checked){
					totPerc += Number(field.value);
				}
			}
			field = dijit.byId('tab4_tot0');
			field.setValue(totPerc);
			calcolaTotaleIncrementi();
		}
		
		function initTab4(){
			var field;
			for(i = 0; i < numRowsTab4; i++){
				field = dijit.byId('tab4_' + i + '0');
				if(field){
					dojo.connect(field,"onChange",calcolaTabella4);
				}
			}
		}
		
		function calcolaTabella5(){
			var field = dijit.byId('tab5_sa');
			var sa = 0;
			if(field){
				sa = field.value;
			}
			field = dijit.byId('tab5_sa60');
			if(field){
				field.setValue(sa * 0.6);
			}
			field = dijit.byId('tab5_sn');
			var sn = 0;
			if(field){
				sn = field.value;
			}
			field = dijit.byId('tab5_st');
			if(field){
				field.setValue(sa * 0.6 + sn);
			}
			if(ricalcolaTotaleCostoCostruzione){
				calcolaCostoCostruzione();
			}
		}
		
		function calcolaTotaleIncrementi(){
			var totI = 0;
			var field = dijit.byId('tab1_tot1');
			if(field){
				totI += field.value;
			}
			field = dijit.byId('tab3_tot0');
			if(field){
				totI += field.value;
			}
			field = dijit.byId('tab4_tot0');
			if(field){
				totI += field.value;
			}
			field = dijit.byId('tot_i');
			if(field){
				field.setValue(totI);
			}
			calcolaMaggiorazione();
		}
		
		function calcolaMaggiorazione(){
			var totI = 0;
			var field = dijit.byId('tot_i');
			if(field){
				totI = field.value;
			}
			var nomeClasse = "Classe non definita";
			var maggiorazione = 0;
			var idClasse = "";
			var classe;
			for(i = 0; i < classiEdifici.length; i++){
				classe = classiEdifici[i];
				if(totI >= classe.da && totI <= classe.a){
					maggiorazione = classe.maggiorazione;
					idClasse = classe.id;
					nomeClasse = classe.descrizione;
					break;
				}
			}
			dojo.byId("classeedificio").innerHTML = nomeClasse;
			dojo.byId("fkclasseedificio").value = idClasse;
			dijit.byId("maggiorazione").setValue(maggiorazione);
			if(ricalcolaTotaleCostoCostruzione){
				calcolaCostoCostruzione();
			}
		}
		
		var ricalcolaTotaleCostoCostruzione = true;
		function calcolaCostoCostruzione(){
			var m = dijit.byId("maggiorazione").value;
			var costomq = dijit.byId("costomq").value;
			dojo.byId("span_maggiorazione").innerHTML = dojo.number.round(m,2);
			dojo.byId("span_costomq").innerHTML = dojo.number.round(costomq,2);
			var costomqmaggiorato = costomq * (1 + m/100);
			dijit.byId("costomqmaggiorato").setValue(costomqmaggiorato);
			dojo.byId("span_costomqmaggiorato").innerHTML = dojo.number.round(costomqmaggiorato,2);
			var sc = dijit.byId("tab4_sc").value;
			var st = dijit.byId("tab5_st").value;
			dojo.byId("span_sc").innerHTML = dojo.number.round(sc,2);
			dojo.byId("span_st").innerHTML = dojo.number.round(st,2);
			var costocostruzione = (sc + st) * costomqmaggiorato;
			dijit.byId("costocostruzione").setValue(costocostruzione);
		}
		
		function initTab5(){
			var suffxs = new Array('sn','sa');
			var field;
			for(i = 0; i < suffxs.length; i++){
				field = dijit.byId('tab5_' + suffxs[i]);
				if(field){
					dojo.connect(field,"onChange",calcolaTabella5);
				}
			}
		}
		/*
		function initComboListini(){
			var field = dijit.byId('listino');
			if(field){
				dojo.connect(field,"onChange",aggiornaCostoMq);
			}
			field = dijit.byId('costomq');
			if(field){
				dojo.connect(field,"onChange",calcolaCostoCostruzione);
			}
		}
		
		function aggiornaCostoMq(){
			var field = dijit.byId('listino');
			var costomq = 0;
			if(field){
				costomq = Number(field.value);
			}
			field = dijit.byId('costomq');
			if(field){
				field.setValue(costomq);
			}
			//calcolaCostoCostruzione();
		}
		*/
		
		function initCalcoloCostiCostruzione(){
			ricalcolaTotaleCostoCostruzione = false;
			initTab1();
			initTab2();
			initTab4();
			initTab5();
			//initComboListini();
			calcolaTabella2();
			calcolaMaggiorazione();
			ricalcolaTotaleCostoCostruzione = true;
		}
		
		function goToCalcoloAliquota(){
			document.inviodati.submit();
		}
		
		dojo.addOnLoad(initCalcoloCostiCostruzione);
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
			height: 12px;
			text-align: right;
			background-color: transparent;
		}
		.inputOneri DIV{
			background-color: transparent;
		}
	</style>
<span class="titoloPagina"> Determinazione del costo di costruzione</span>
<%-- 
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
	--%>
	<div id="subcontent">
		<spring-form:form 	commandName="ccicalcolotot" name="inviodati" action="salvaCalcoloCostoCostruzione.htm">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="ccicalcolotot" />
			</jsp:include>
		
			<input type="hidden" name="ccicalcolotot_id" value="${testatacalcoli.id.codice }"/>
		
			<table width="100%">
				<colgroup width="50" />
				<colgroup width="50%" />
				<tr>
					<td colspan="2">
					<fieldset><legend>Tabella 1 - Incremento per superficie utile abitabile</legend>
					<div>
		
					<table class="TabellaOneri" id="tab1">
						<tr>
							<th>Classi di superficie (mq)</th>
							<th>Alloggi (n)</th>
							<th>Superficie utile abitabile (mq)</th>
							<th>Rapporto rispetto al totale Su</th>
							<th>% Incremento (Art. 5)</th>
							<th>% Incremento per classi di superficie</th>
						</tr>
						<tr>
							<td style="text-align: center">(1)</td>
							<td style="text-align: center">(2)</td>
							<td style="text-align: center">(3)</td>
							<td style="text-align: center">(4) = (3) : Su</td>
							<td style="text-align: center">(5)</td>
							<td style="text-align: center">(6) = (4) x (5)</td>
						</tr>
		
						<tr>
						<%
							CcIcalcoliHelper dati = (CcIcalcoliHelper)request.getAttribute("ccicalcoli");
							CcIcalcoli totali = dati.getTotali();
							List<CcItabella1> tab1 = dati.getTab1();
							//si da per scontato che i record siano i 5 record previsti per la demo
							CcItabella1 rowTab1 = tab1.get(0);
						%>
							<td>
								<span>&lt;=95</span>
								<input type="hidden" name="tab1_0csid" value="1"/>
								<input type="hidden" name="tab1_0id" value="<%=rowTab1.getId().getCodice() == null ? "" : rowTab1.getId().getCodice().toString()%>"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_00" id="tab1_00" value="<%=rowTab1.getAlloggi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Numero alloggi non valido" constraints="{min:0,max:1000,places:0}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_01" id="tab1_01" value="<%=rowTab1.getSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true"  invalidMessage="Superficie abitabile non valida" constraints="{min:0,pattern:'#########.00'}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_02" id="tab1_02" value="<%=rowTab1.getRapportoSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00',places:2}"/>
							</td>
							<td style="text-align: center">
								<input type="hidden"" name="tab1_03" id="tab1_03" value="<%=rowTab1.getIncremento() %>"/>
								<span>0</span>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_04" id="tab1_04" value="<%=rowTab1.getIncrementoxclassi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
						<%rowTab1 = tab1.get(1); %>
						<tr>
							<td>
								<span>&gt; 95 &lt;=110</span>
								<input type="hidden" name="tab1_1csid" value="2"/>
								<input type="hidden" name="tab1_1id" value="<%=rowTab1.getId().getCodice() == null ? "" : rowTab1.getId().getCodice().toString()%>"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_10" id="tab1_10" value="<%=rowTab1.getAlloggi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Numero alloggi non valido" constraints="{min:0,max:1000,places:0}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_11" id="tab1_11" value="<%=rowTab1.getSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie abitabile non valida" constraints="{min:0,pattern:'#########.00'}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_12" id="tab1_12" value="<%=rowTab1.getRapportoSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00',places:2}"/>
							</td>
							<td style="text-align: center">
								<input type="hidden"" name="tab1_13" id="tab1_13" value="<%=rowTab1.getIncremento() %>"/>
								<span>5</span>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_14" id="tab1_14" value="<%=rowTab1.getIncrementoxclassi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
						<%rowTab1 = tab1.get(2); %>
						<tr>
							<td>
								<span>&gt;110 &lt;=130</span>
								<input type="hidden" name="tab1_2csid" value="3"/>
								<input type="hidden" name="tab1_2id" value="<%=rowTab1.getId().getCodice() == null ? "" : rowTab1.getId().getCodice().toString()%>"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_20" id="tab1_20" value="<%=rowTab1.getAlloggi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Numero alloggi non valido" constraints="{min:0,max:1000,places:0}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_21" id="tab1_21" value="<%=rowTab1.getSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie abitabile non valida" constraints="{min:0,pattern:'#########.00'}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_22" id="tab1_22" value="<%=rowTab1.getRapportoSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00',places:2}"/>
							</td>
							<td style="text-align: center">
								<input type="hidden"" name="tab1_23" id="tab1_23" value="<%=rowTab1.getIncremento() %>"/>
								<span>15</span>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_24" id="tab1_24" value="<%=rowTab1.getIncrementoxclassi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
						<%rowTab1 = tab1.get(3); %>
						<tr>
							<td>
								<span>&gt;130 &lt;=160</span>
								<input type="hidden" name="tab1_3csid" value="4"/>
								<input type="hidden" name="tab1_3id" value="<%=rowTab1.getId().getCodice() == null ? "" : rowTab1.getId().getCodice().toString()%>"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_30" id="tab1_30" value="<%=rowTab1.getAlloggi() %>"  dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Numero alloggi non valido" constraints="{min:0,max:1000,places:0}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_31" id="tab1_31" value="<%=rowTab1.getSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie abitabile non valida" constraints="{min:0,pattern:'#########.00'}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_32" id="tab1_32" value="<%=rowTab1.getRapportoSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00',places:2}"/>
							</td>
							<td style="text-align: center">
								<input type="hidden"" name="tab1_33" id="tab1_33" value="<%=rowTab1.getIncremento() %>"/>
								<span>30</span>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_34" id="tab1_34" value="<%=rowTab1.getIncrementoxclassi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
						<%rowTab1 = tab1.get(4); %>
						<tr>
							<td>
								<span>&gt;160</span>
								<input type="hidden" name="tab1_4csid" value="5"/>
								<input type="hidden" name="tab1_4id" value="<%=rowTab1.getId().getCodice() == null ? "" : rowTab1.getId().getCodice().toString()%>"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_40" id="tab1_40" value="<%=rowTab1.getAlloggi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Numero alloggi non valido" constraints="{min:0,max:1000,places:0}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" name="tab1_41" id="tab1_41" value="<%=rowTab1.getSu() %>"  dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie abitabile non valida" constraints="{min:0,pattern:'#########.00'}"/>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_42" id="tab1_42" value="<%=rowTab1.getRapportoSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00',places:2}"/>
							</td>
							<td style="text-align: center">
								<input type="hidden"" name="tab1_43" id="tab1_43" value="<%=rowTab1.getIncremento() %>"/>
								<span>50</span>
							</td>
							<td style="text-align: center">
								<input class="inputOneri" readonly="readonly" name="tab1_44" id="tab1_44" value="<%=rowTab1.getIncrementoxclassi() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="totali">
								<span>Su:</span>
							</td>
							<td class="totali">
								<input class="inputOneri" readonly="readonly" name="tab1_tot0" id="tab1_tot0" value="<%= totali.getSu() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,places:2}"/>
							</td>
							<td></td>
							<td class="totali"><span>I1</span></td>
							<td class="totali">
								<input class="inputOneri" readonly="readonly" name="tab1_tot1" id="tab1_tot1" value="<%= totali.getI1() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,max:100000,places:2}"/>
							</td>
						</tr>
					</table>
		
		
					</div>
					</fieldset>
					</td>
				</tr>
				<tr style="vertical-align: top">
					<td>
					<fieldset>
						<legend>Tabella 2 - Superfici per servizi o accessori relativi alla parte residenziale</legend>
						<div>
						<%
						List<CcItabella2> tab2 = dati.getTab2();
						//si da per scontato che i record siano i 5 record previsti per la demo
						CcItabella2 rowTab2 = tab2.get(0);	
						%>
						<table class="TabellaOneri" id="tab2">
							<tr>
								<th>Destinazioni</th>
								<th>Superficie netta di servizi e accessori (mq)</th>
							</tr>
							<tr>
								<td style="text-align: center">(7)</td>
								<td style="text-align: center">(8)</td>
							</tr>
			
							<tr>
								<td>
									<span>Androni d'ingresso e porticati liberi</span>
									<input type="hidden" name="tab2_0id" value="<%=rowTab2.getId().getCodice() == null ? "" : rowTab2.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab2_0dsid" value="4"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab2_00" id="tab2_00" value="<%=rowTab2.getSuperficie() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie non valida" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<%rowTab2 = tab2.get(1); %>
							<tr>
								<td>
									<span>Autorimesse collettive</span>
									<input type="hidden" name="tab2_1id" value="<%=rowTab2.getId().getCodice() == null ? "" : rowTab2.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab2_1dsid" value="3"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab2_10" id="tab2_10" value="<%=rowTab2.getSuperficie() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie non valida" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<%rowTab2 = tab2.get(2); %>
							<tr>
								<td>
									<span>Autorimesse singole</span>
									<input type="hidden" name="tab2_2id" value="<%=rowTab2.getId().getCodice() == null ? "" : rowTab2.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab2_2dsid" value="2"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab2_20" id="tab2_20" value="<%=rowTab2.getSuperficie() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie non valida" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<%rowTab2 = tab2.get(3); %>
							<tr>
								<td>
									<span>
										Cantinole, soffitte, locali motore ascensore, cabine idriche,
										lavatoi comuni, centrali termiche ed altri locali a stretto
										servizio delle residenze.
									</span>
									<input type="hidden" name="tab2_3id" value="<%=rowTab2.getId().getCodice() == null ? "" : rowTab2.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab2_3dsid" value="1"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab2_30" id="tab2_30" value="<%=rowTab2.getSuperficie() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie non valida" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<%rowTab2 = tab2.get(4); %>
							<tr>
								<td>
									<span>Logge e balconi</span>
									<input type="hidden" name="tab2_4id" value="<%=rowTab2.getId().getCodice() == null ? "" : rowTab2.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab2_4dsid" value="5"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab2_40" id="tab2_40" value="<%=rowTab2.getSuperficie() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" invalidMessage="Superficie non valida" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
			
							<tr>
								<td class="totali"><span>Snr:</span></td>
								<td class="totali" style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab2_tot0" id="tab2_tot0" value="<%=totali.getSnr() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,places:2}"/>
								</td>
							</tr>
						</table>
			
			
						</div>
					</fieldset>
					</td>
					<td>
					<fieldset>
						<legend>Tabella 3 - Incremento per servizi ed accessori relativi alla parte residenziale (art. 6)</legend>
						<div>
						<%
						List<CcItabella3> tab3 = dati.getTab3();
						CcItabella3 rowTab3 = tab3.get(0);
						
						%>
						<table class="TabellaOneri" id="tab3">
							<tr>
								<th>Intervalli di variabilit&agrave; del rapporto percentuale (Snr/Su) x 100</th>
								<th>Rapporto percentuale (Snr/Su) x 100</th>
								<th>% Incremento</th>
							</tr>
							<tr>
								<td style="text-align: center">(9)</td>
			
								<td style="text-align: center">(10)</td>
								<td style="text-align: center">(11)</td>
							</tr>
			
							<tr>
								<td>
									<span>&lt;=50</span>
									<input type="hidden" name="tab3_0id" value="<%=rowTab3.getId().getCodice() == null ? "" : rowTab3.getId().getCodice().toString()%>"/>
									<input type="hidden"" name="tab3_0da" id="tab3_0da" value="0"/>
									<input type="hidden"" name="tab3_0a" id="tab3_0a" value="50"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab3_01" id="tab3_01" value="" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab3.getIncremento() %></span>
									<input type="hidden"" name="tab3_02" id="tab3_02" value="<%=rowTab3.getIncremento() %>"/>
								</td>
							</tr>
							<%rowTab3 = tab3.get(1); %>
							<tr>
								<td>
									<span>&gt; 50 &lt;= 75</span>
									<input type="hidden" name="tab3_1id" value="<%=rowTab3.getId().getCodice() == null ? "" : rowTab3.getId().getCodice().toString()%>"/>
									<input type="hidden"" name="tab3_1da" id="tab3_1da" value="50"/>
									<input type="hidden"" name="tab3_1a" id="tab3_1a" value="75"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab3_11" id="tab3_11" value="" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab3.getIncremento() %></span>
									<input type="hidden"" name="tab3_12" id="tab3_12" value="<%=rowTab3.getIncremento() %>"/>
								</td>
							</tr>
							<%rowTab3 = tab3.get(2); %>
							<tr>
								<td>
									<span>&gt; 75 &lt;= 100</span>
									<input type="hidden" name="tab3_2id" value="<%=rowTab3.getId().getCodice() == null ? "" : rowTab3.getId().getCodice().toString()%>"/>
									<input type="hidden"" name="tab3_2da" id="tab3_2da" value="75"/>
									<input type="hidden"" name="tab3_2a" id="tab3_2a" value="100"/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab3_21" id="tab3_21" value="" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab3.getIncremento() %></span>
									<input type="hidden"" name="tab3_22" id="tab3_22" value="<%=rowTab3.getIncremento() %>"/>
								</td>
							</tr>
							<%rowTab3 = tab3.get(3); %>
							<tr>
								<td>
									<span>&gt; 100</span>
									<input type="hidden" name="tab3_3id" value="<%=rowTab3.getId().getCodice() == null ? "" : rowTab3.getId().getCodice().toString()%>"/>
									<input type="hidden"" name="tab3_3da" id="tab3_3da" value="100"/>
									<input type="hidden"" name="tab3_3a" id="tab3_3a" value=""/>
								</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab3_31" id="tab3_31" value="" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab3.getIncremento() %></span>
									<input type="hidden"" name="tab3_32" id="tab3_32" value="<%=rowTab3.getIncremento() %>"/>
								</td>
							</tr>
							
							<tr>
								<td></td>
								<td class="totali" style="text-align: center"><span>I2: </span></td>
								<td class="totali" style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab3_tot0" id="tab3_tot0" value="<%=totali.getI2() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:0}"/>
								</td>
							</tr>
						</table>
			
			
						</div>
					</fieldset>
					</td>
				</tr>
				<tr style="vertical-align: top">
					<td>
					<fieldset>
						<legend>Superfici residenziali e relativi servizi ed accessori (Articoli 2 e 3)</legend>
						<div style="width: 100%">
						<table width="100%" class="TabellaOneri" id="tab_riepilogo_superfici">
							<colgroup width="10%" />
							<colgroup width="15%" />
							<colgroup width="55%" />
							<colgroup width="20%" />
							<tr>
								<th colspan="2">Sigla</th>
								<th>Denominazione</th>
								<th>Superficie (mq)</th>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center">(17)</td>
								<td style="text-align: center">(18)</td>
								<td style="text-align: center">(19)</td>
							</tr>
							<tr>
								<td>1</td>
								<td style="text-align: center" nowrap="nowrap">Su (art. 3)</td>
								<td style="text-align: center">Superficie utile abitabile</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab4_su" id="tab4_su" value="<%=totali.getSu() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
							<tr>
								<td>2</td>
								<td style="text-align: center" nowrap="nowrap">Snr (art. 2)</td>
								<td style="text-align: center">Superficie netta non residenziale</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab4_snr" id="tab4_snr" value="<%=totali.getSnr() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
							<tr>
								<td>3</td>
								<td style="text-align: center" nowrap="nowrap">60% Snr</td>
								<td style="text-align: center">Superficie ragguagliata</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab4_snr60" id="tab4_snr60" value="0.00" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap">4=1+3</td>
								<td style="text-align: center" class="totali" nowrap="nowrap">Sc (art. 2)</td>
								<td style="text-align: center" class="totali">Superficie complessiva</td>
								<td style="text-align: center" class="totali">
									<input class="inputOneri" readonly="readonly" name="tab4_sc" id="tab4_sc" value="<%=totali.getSc() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
						</table>
						</div>
					</fieldset>
					</td>
					<td>
					<fieldset>
						<legend>Tabella 4 - Incremento per particolari caratteristiche (art. 7)</legend>
						<div>
						<%
						List<CcItabella4> tab4 = dati.getTab4();
						CcItabella4 rowTab4 = tab4.get(0);
						String checked = rowTab4.getSelezionata() ? "checked='checked'" : "";
						%>
						<table class="TabellaOneri" id="tab4">
							<tr>
								<th>Descrizione caratteristica</th>
								<th>Ipotesi che ricorre</th>
								<th>% Incremento</th>
							</tr>
							<tr>
								<td style="text-align: center">(12)</td>
								<td style="text-align: center">(13)</td>
								<td style="text-align: center">(14)</td>
							</tr>
			
							<tr>
								<td>
									<span>Alloggi di custodia a servizio di uno o pi&ugrave; edifici comprendenti meno di 15 unit&agrave; immobiliari</span>
									<input type="hidden" name="tab4_0id" value="<%=rowTab4.getId().getCodice() == null ? "" : rowTab4.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab4_0carattid" value="5"/>
								</td>
								<td style="text-align: center;">
									<input name="tab4_00" <%=checked %> id="tab4_00" value="<%=rowTab4.getIncremento() %>" dojoType="dijit.form.CheckBox"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab4.getIncremento() %></span>
									<input type="hidden" name="tab4_01" id="tab4_01" value="<%=rowTab4.getIncremento() %>"/>
								</td>
							</tr>
							<%
							rowTab4 = tab4.get(1);
							checked = rowTab4.getSelezionata() ? "checked='checked'" : "";
							%>
							<tr>
								<td>
									<span>
										Altezza libera netta di piano superiore a 3,00 m o a quella
										minima prescritta da norme regolamentari. Per amibienti con altezze
										diverse si fa riferimento all'altezza media ponderale.
									</span>
									<input type="hidden" name="tab4_1id" value="<%=rowTab4.getId().getCodice() == null ? "" : rowTab4.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab4_1carattid" value="3"/>
								</td>
								<td style="text-align: center;">
									<input name="tab4_10" <%=checked %> id="tab4_10" value="<%=rowTab4.getIncremento() %>" dojoType="dijit.form.CheckBox"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab4.getIncremento() %></span>
									<input type="hidden"" name="tab4_11" id="tab4_11" value="<%=rowTab4.getIncremento() %>"/>
								</td>
							</tr>
							<%
							rowTab4 = tab4.get(2);
							checked = rowTab4.getSelezionata() ? "checked='checked'" : "";
							%>
							<tr>
								<td>
									<span>Piscina coperta o scoperta quando sia a servizio di uno o pi&ugrave; edifici comprendenti meno di 15 unit&agrave; immobiliari</span>
									<input type="hidden" name="tab4_2id" value="<%=rowTab4.getId().getCodice() == null ? "" : rowTab4.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab4_2carattid" value="4"/>
								</td>
								<td style="text-align: center;">
									<input name="tab4_20" <%=checked %> id="tab4_20" value="<%=rowTab4.getIncremento() %>" dojoType="dijit.form.CheckBox"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab4.getIncremento() %></span>
									<input type="hidden"" name="tab4_21" id="tab4_21" value="<%=rowTab4.getIncremento() %>"/>
								</td>
							</tr>
							<%
							rowTab4 = tab4.get(3);
							checked = rowTab4.getSelezionata() ? "checked='checked'" : "";
							%>
							<tr>
								<td>
									<span>Pi&ugrave; di un ascensore per ogni scala se questa serve meno di sei piani sopraelevati</span>
									<input type="hidden" name="tab4_3id" value="<%=rowTab4.getId().getCodice() == null ? "" : rowTab4.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab4_3carattid" value="1"/>
								</td>
								<td style="text-align: center;">
									<input name="tab4_30" <%=checked %> id="tab4_30" value="<%=rowTab4.getIncremento() %>" dojoType="dijit.form.CheckBox"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab4.getIncremento() %></span>
									<input type="hidden"" name="tab4_31" id="tab4_31" value="<%=rowTab4.getIncremento() %>"/>
								</td>
							</tr>
							<%
							rowTab4 = tab4.get(4);
							checked = rowTab4.getSelezionata() ? "checked='checked'" : "";
							%>
							<tr>
								<td>
									<span>Scala di servizio non prescritta da leggi e regolamenti o imposta da necessit&agrave; di prevenzione di infortuni e incendi</span>
									<input type="hidden" name="tab4_4id" value="<%=rowTab4.getId().getCodice() == null ? "" : rowTab4.getId().getCodice().toString()%>"/>
									<input type="hidden" name="tab4_4carattid" value="2"/>
								</td>
								<td style="text-align: center;">
									<input name="tab4_40" <%=checked %> id="tab4_40" value="<%=rowTab4.getIncremento() %>" dojoType="dijit.form.CheckBox"/>
								</td>
								<td style="text-align: center">
									<span><%=rowTab4.getIncremento() %></span>
									<input type="hidden"" name="tab4_41" id="tab4_41" value="<%=rowTab4.getIncremento() %>"/>
								</td>
							</tr>
			
							<tr>
								<td></td>
								<td class="totali"><span>I3: </span></td>
								<td class="totali" style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab4_tot0" id="tab4_tot0" value="<%=totali.getI3() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:0}"/>
								</td>
							</tr>
						</table>
			
			
						</div>
					</fieldset>
					</td>
				</tr>
				<tr style="vertical-align: top">
					<td>
					<fieldset>
						<legend>Superfici per attivit&agrave; turistiche, commerciali e direzioni e relativi accessori (Art. 9)</legend>
						<div>
						<table class="TabellaOneri" width="100%" id="tab5">
							<colgroup width="10%" />
							<colgroup width="15%" />
							<colgroup width="55%" />
							<colgroup width="20%" />
							<tr>
								<th colspan="2">Sigla</th>
								<th>Denominazione</th>
								<th>Superficie (mq)</th>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center">(20)</td>
								<td style="text-align: center">(21)</td>
								<td style="text-align: center">(22)</td>
							</tr>
							<tr>
								<td>1</td>
								<td style="text-align: center" nowrap="nowrap">Su (art. 9)</td>
								<td style="text-align: center">Superficie netta non residenziale</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab5_sn" id="tab5_sn" value="<%=totali.getSuArt9() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<tr>
								<td>2</td>
								<td style="text-align: center" nowrap="nowrap">Sa (art. 9)</td>
								<td style="text-align: center">Superficie accessori</td>
								<td style="text-align: center">
									<input class="inputOneri" name="tab5_sa" id="tab5_sa" value="<%=totali.getSa() %>" dojoType="dijit.form.NumberTextBox" selectOnClick="true" constraints="{min:0,pattern:'#########.00'}"/>
								</td>
							</tr>
							<tr>
								<td>3</td>
								<td style="text-align: center" nowrap="nowrap">60% Sa</td>
								<td style="text-align: center">Superficie ragguagliata</td>
								<td style="text-align: center">
									<input class="inputOneri" readonly="readonly" name="tab5_sa60" id="tab5_sa60" value="<%= totali.getSa().multiply(new BigDecimal(0.6)) %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
							<tr>
								<td>4=1+3</td>
								<td style="text-align: center" class="totali" nowrap="nowrap">St (art. 9)</td>
								<td style="text-align: center" class="totali">Superficie totale non residenziale</td>
								<td style="text-align: center" class="totali">
									<input class="inputOneri" readonly="readonly" name="tab5_st" id="tab5_st" value="<%=totali.getSt() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/>
								</td>
							</tr>
						</table>
						</div>
					</fieldset>
					</td>
					<td>
					<fieldset>
						<legend>&nbsp;</legend>
					<div>
					<table class="TabellaOneri" width="100%">
						<tr>
							<th>TOTALE INCREMENTI <i>(I = i1+i2+i3)</i></th>
							<td style="text-align: center" class="totali">I: <input class="inputOneri" readonly="readonly" name="tot_i" id="tot_i" value="0.00" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/></td>
						</tr>
					</table>
					<br />
					<table class="TabellaOneri" width="100%">
						<colgroup width="50%" />
						<colgroup width="50%" />
						<tr>
							<th>Classe edificio</th>
							<th>% Maggiorazione</th>
						</tr>
						<tr>
							<td style="text-align: center">(15)</td>
							<td style="text-align: center">(16)</td>
						</tr>
						<tr>
							<td style="text-align: center">
								<span id="classeedificio">&nbsp;</span>
							</td>
							<td style="text-align: center" class="totali">
								<input class="inputOneri" readonly="readonly" name="maggiorazione" id="maggiorazione" value="<%=totali.getMaggiorazione() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,max:100000,places:2}"/>
								<input type="hidden" name="fkclasseedificio" id="fkclasseedificio"/>
							</td>
						</tr>
					</table>
					</div>
					</fieldset>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<fieldset><legend>Riepilogo</legend>
					<div style="text-align: center">
					<table style="text-align: left" class="TabellaOneri">
						<tr>
							<td style="text-align: left">&nbsp;A - Costo di costruzione dell'edilizia agevolata</td>
							<td style="text-align: left">
								<input class="inputOneri" name="costomq" id="costomq" value="<%=totali.getCostocmq() %>" readonly="readonly" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/> €/mq
							</td>
						</tr>
						<tr>
							<td style="text-align: left;">
							&nbsp;B - Costi a mq di costruzione, maggiorato A x (1 + M / 100) = <b> <span id="span_costomq"><%=totali.getCostocmq() %></span> x ( 1 + <span id="span_maggiorazione"><%=totali.getMaggiorazione() %></span> / 100 )</b>
							</td>
							<td style="text-align: left">
								<input class="inputOneri" readonly="readonly" name="costomqmaggiorato" id="costomqmaggiorato" value="<%=totali.getCostocmqMaggiorato() %>" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/> €/mq
							</td>
						</tr>
						<tr>
							<td style="text-align: left;">&nbsp;C - costo di costruzione dell'edificio ( Sc + St ) x B = <b>(<span id="span_sc"><%=totali.getSc() %></span> + <span id="span_st"><%=totali.getSt() %></span> ) x <span id="span_costomqmaggiorato"><%=totali.getCostocmqMaggiorato() %></span> </b></td>
							<td style="text-align: left">
								<input class="inputOneri" readonly="readonly" name="costocostruzione" id="costocostruzione" value="${icalcolotcontributo.costocEdificio }" dojoType="dijit.form.NumberTextBox" constraints="{min:0,places:2}"/> €
							</td>
						</tr>
					</table>
					</div>
					</fieldset>
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<li>
			<a href="javascript:goToCalcoloAliquota();">
				Salva e Calcola Aliquota
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