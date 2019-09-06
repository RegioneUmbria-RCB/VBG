<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DatiDinamiciCtrl.ascx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.DatiDinamiciCtrl" %>
<%@ Register TagPrefix="cc1" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>
<%@ Register TagPrefix="cc1" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>

<%if (multiView.ActiveViewIndex == 0){ %>
	<script type="text/javascript">
		function VisualizzaStorico()
		{
			var url = '<%= GetUrlStorico()%>';
			var feats = "width=900px,height=400px,statusbar=1,scrollbars=1,resizable=1";
			window.open(url,"storico",feats);
		}

		var g_datiDinamiciExtender = null;

		$(function () {
			g_datiDinamiciExtender = new DatiDinamiciExtender('<%=datiDinamiciPnlErrori.ClientID %>', '<%=cmdSalva.ClientID %>', $('.d2WatchButton'));
		});
	
	</script>
<%} %>

<asp:ScriptManager ID="ScriptManager1" runat="server">
</asp:ScriptManager>
<div class="clear">
<!-- lista delle schede dell'istanza -->
	<asp:Repeater runat="server" ID="rptListaSchede" >
		<HeaderTemplate>
			<ul class="listaSchede">
		</HeaderTemplate>
		<ItemTemplate>
			<li >
				<asp:LinkButton runat="server" ID="lnkCambiaScheda" CssClass='<%# ClasseCella( DataBinder.Eval(Container.DataItem, "Id") ) + "  d2WatchButton" %>' CommandName="CambiaScheda" CommandArgument='<%# Bind("Id") %>' Text='<%# Bind("Descrizione") %>' OnClick="CambiaScheda"></asp:LinkButton>
			</li>
		</ItemTemplate>
		<FooterTemplate>
			</ul>
			<div style="clear:both"></div>
		</FooterTemplate>
	</asp:Repeater>
	
	<!-- contenuto della scheda attiva oppure pagina di inserimento di una nuova scheda -->
	<div class="ContenutoScheda">
		<asp:MultiView ID="multiView" runat="server"  ActiveViewIndex="0">

			<asp:View runat="server" ID="schedaView">
				<asp:Repeater runat="server" ID="rptMolteplicita">
					<HeaderTemplate>
						<table><tr><td>
						<div>
							<asp:label runat="Server" ID="lblTitolo" AssociatedControlID="lblTitolo"><b>Codice scheda:&nbsp;</b></asp:label>
					</HeaderTemplate>
					
					<ItemTemplate>
							<asp:LinkButton runat="server" CssClass="d2WatchButton" ID="lnkApriIndice" CommandArgument='<%# DataBinder.Eval( Container , "DataItem" ) %>' Text='<%# TestoIndice( DataBinder.Eval( Container , "DataItem" ) ) %>' OnClick="CambiaIndice" Visible='<%# !IndiceCorrente( DataBinder.Eval( Container , "DataItem" ) ) %>'/>
							<b><asp:Literal runat="server" ID="ltrIndice" Text='<%# TestoIndice( DataBinder.Eval( Container , "DataItem" )) %>' Visible='<%# IndiceCorrente( DataBinder.Eval( Container , "DataItem" ) ) %>' /></b>
					</ItemTemplate>
					
					<FooterTemplate>
							<asp:ImageButton runat="server" CssClass="d2WatchButton" ID="lnkNuovaScheda" ToolTip="Aggiungi una nuova copia della scheda corrente" AlternateText="Aggiungi" ImageUrl="~/Images/nuovo.gif" OnClick="NuovaSchedaMultipla" />
							<asp:ImageButton runat="server" CssClass="d2WatchButton" ID="lnkDuplicaScheda" ToolTip="Duplica i valori della scheda corrente in una nuova scheda" AlternateText="Duplica" ImageUrl="~/Images/copia.gif" OnClick="DuplicaSchedaMultipla" />
							<asp:ImageButton runat="server" ID="lnkEliminaScheda" ToolTip="Elimina i valori della scheda corrente" AlternateText="Elimina" ImageUrl="~/Images/delete.gif" OnClick="EliminaSchedaMultipla" onclientclick="return confirm('Eliminare questa copia della scheda\?')" />
						</div>
						</td></tr></table>
					</FooterTemplate>
				</asp:Repeater>
			
			
				<br />
				<div runat="server" id="datiDinamiciPnlErrori" class="ErroriDatiDinamici" style="display:none">
				</div>
				<cc1:ModelloDinamicoRenderer ID="renderer" runat="server"></cc1:ModelloDinamicoRenderer>
			</asp:View>

			
			<asp:View runat="server" ID="aggiungiSchedaView">

				<script type="text/javascript">
					var _textboxNuovaScheda;
					var _hiddenNuovaScheda;
					var _token = '<%= Request.QueryString["Token"] %>';
					var _codice = '<%= Request.QueryString[NomeChiaveCodice] %>';
					var _codiceMovimento = '<%= Request.QueryString["CodiceMovimento"]%>';
					var _requestPath = '<%= Request.Path %>/GetListaModelliDisponibili';
					var _cercaNegliArchiviDiBase;
					var _bottoneAggiungi;
					var _usaDownArrowPerCercare = true;

					function getDataSource(request, response) {
						$.ajax({
							url: _requestPath,
							type: "POST",
							contentType: "application/json; charset=utf-8",
							dataType: "json",
							data: JSON.stringify({
								token: _token,
								codice: _codice,
								partial: _textboxNuovaScheda.val(),
								cercaTT: _cercaNegliArchiviDiBase.is(':checked'),
								codiceMovimento: _codiceMovimento
							}),
							success: function (data) {
								response($.map(data.d, function (item) {
									return {
										label: item.label,
										value: item.value
									}
								}))
							}
						})
					}

					function onAutocompleteItemSelected(event, ui) {

						_textboxNuovaScheda.val(ui.item.label);
						_hiddenNuovaScheda.val(ui.item.value);

						if(ui.item.value)
							_bottoneAggiungi.removeAttr('disabled');

						_usaDownArrowPerCercare = true;

						return false;
					}

					function onBeginSearch(){
						_hiddenNuovaScheda.val('');
						_bottoneAggiungi.attr('disabled','disabled');
						_usaDownArrowPerCercare = false;
					}

					$(function () {

						_textboxNuovaScheda = $('#<%= txtNuovaScheda.ClientID%>');
						_hiddenNuovaScheda = $('#<%= hidIdNuovaScheda.ClientID%>');
						_cercaNegliArchiviDiBase = $('#chkCercaNuovaSchedaSuTT');
						_bottoneAggiungi = $('#<%= cmdAggiungiScheda.ClientID%>');

						_textboxNuovaScheda.autocomplete({
							source: getDataSource,
							select: onAutocompleteItemSelected,
							search: onBeginSearch
						});

						_textboxNuovaScheda.keydown(function(e){
						    if (e.keyCode == 40 && _usaDownArrowPerCercare) { 
							   _textboxNuovaScheda.autocomplete('search','%%');
							   return false;
							}
						});

						_cercaNegliArchiviDiBase.click(function (e) {
							_textboxNuovaScheda.autocomplete('search');
						});
					});
				</script>

				<fieldset>
					<legend>Selezionare la scheda da aggiungere</legend>
					<div>&nbsp;</div>
					<div>
						<asp:Label runat="server" ID="lblNuovaScheda" Text="Scheda da aggiungere" AssociatedControlID="txtNuovaScheda"></asp:Label>
						<asp:HiddenField runat="server" ID="hidIdNuovaScheda" />
						<asp:TextBox runat="server" ID="txtNuovaScheda" Columns="70" CssClass="searchBox"></asp:TextBox>
						<span style='<%=StileSpanSoftwareTT()%>'>
							<input type="checkbox" id="chkCercaNuovaSchedaSuTT" />
							Mostra anche le schede degli archivi di base
						</span>
					</div>
				</fieldset>
				<div class="Bottoni">
				</div>
			</asp:View>
			

			<asp:View runat="server" ID="nessunaScheda">
				<fieldset>
					<div style="width:100%;text-align:center"><br />
						Non sono presenti schede dinamiche collegate all'istanza corrente
					</div>
				</fieldset>
				<div class="Bottoni">
				</div>
			</asp:View>

			<asp:View ID="aggiungiSchedaAttivita" runat="server">
				<script type="text/javascript">
					var _textboxNuovaScheda;
					var _hiddenNuovaScheda;
					var _token = '<%= Request.QueryString["Token"] %>';
					var _codice = '<%= Request.QueryString[NomeChiaveCodice] %>';
					var _requestPath = '<%= Request.Path %>/GetListaModelliDisponibili';
					var _dropDownSoftware;
					var _bottoneAggiungi;

					function getDataSource(request, response) {
						$.ajax({
							url: _requestPath,
							type: "POST",
							contentType: "application/json; charset=utf-8",
							dataType: "json",
							data: JSON.stringify({
								token: _token,
								codice: _codice,
								partial: _textboxNuovaScheda.val(),
								software: _dropDownSoftware.val()
							}),
							success: function (data) {
								response($.map(data.d, function (item) {
									return {
										label: item.label,
										value: item.value
									}
								}))
							}
						})
					}

					function onAutocompleteItemSelected(event, ui) {

						_textboxNuovaScheda.val(ui.item.label);
						_hiddenNuovaScheda.val(ui.item.value);

						if (ui.item.value)
							_bottoneAggiungi.removeAttr('disabled');

						return false;
					}

					function onBeginSearch() {
						_hiddenNuovaScheda.val('');
						_bottoneAggiungi.attr('disabled', 'disabled');
					}

					$(function () {

						_textboxNuovaScheda = $('#<%= txtNuovaSchedaAtt.ClientID%>');
						_hiddenNuovaScheda = $('#<%= hidIdNuovaSchedaAtt.ClientID%>');
						_dropDownSoftware = $('#<%=ddlCercaInModuloAtt.ClientID %>');
						_bottoneAggiungi = $('#<%= cmdAggiungiScheda.ClientID%>');

						_textboxNuovaScheda.autocomplete({
							source: getDataSource,
							select: onAutocompleteItemSelected,
							search: onBeginSearch
						});
					});
				</script>

				<fieldset>
					<legend>Selezionare la scheda da aggiungere</legend>
					<div>&nbsp;</div>
					<div>
						<asp:Label runat="server" ID="lblModuloSoftware" Text="Cerca nel modulo" AssociatedControlID="ddlCercaInModuloAtt"/>
						<asp:DropDownList runat="server" ID="ddlCercaInModuloAtt" DataTextField="Value" DataValueField="Key" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label1" Text="Scheda da aggiungere" AssociatedControlID="txtNuovaSchedaAtt" />
						<asp:HiddenField runat="server" ID="hidIdNuovaSchedaAtt" />
						<asp:TextBox runat="server" ID="txtNuovaSchedaAtt" Columns="70" CssClass="searchBox"></asp:TextBox>
					</div>
				</fieldset>
				<div class="Bottoni">
				</div>
			</asp:View>

		</asp:MultiView>
		<br />
	</div>
	<fieldset>
		<div class="Bottoni">
			<cc1:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
			<cc1:SigeproButton runat="server" ID="cmdAggiungiScheda" CssClass="d2WatchButton" Text="Aggiungi scheda" IdRisorsa="AGGIUNGISCHEDA" OnClick="AggiungiNuovaScheda" />
			<cc1:SigeproButton runat="server" ID="cmdEliminaScheda" Text="Elimina scheda" IdRisorsa="ELIMINASCHEDAATTIVA" OnClick="EliminaSchedaAttiva" />
			<cc1:SigeproButton runat="server" ID="cmdStorico" CssClass="d2WatchButton" Text="Storico" IdRisorsa="STORICO" OnClientClick="VisualizzaStorico(); return false;" />
			<cc1:SigeproButton runat="server" ID="cmdChiudi" CssClass="d2WatchButton" Text="Chiudi" CausesValidation="false" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
			<span id="salvataggioInCorso">Salvataggio dati in corso...</span>
			<span id="salvataggioEffettuato">Dati salvati correttamente</span>
		</div>
	</fieldset>
</div>