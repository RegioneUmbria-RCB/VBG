<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneEndoV2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoV2" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="GrigliaEndo" Src="~/Reserved/InserimentoIstanza/GestioneEndoV2GrigliaEndo.ascx" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<style type="text/css" media="all">
		/*.gruppoEndo {font-size: 11px;text-transform: uppercase}*/
		#alberoEndo ul
		{
			margin: 0px;
			padding: 0px;
		}
		#alberoEndo li
		{
			margin-left: 20px;
			list-style-type: none;
			margin-bottom: 5px;
		}
		#alberoEndo > ul > li
		{
			margin-left: 5px;
		}
		#alberoEndo > ul > li > ul
		{
			/*margin-top: 5px;*/
		}
		#alberoEndo  ul
		{
			margin-top: 5px;
		}
		#alberoEndo li > input[type=checkbox]
		{
		}
		#alberoEndo li > div.famigliaEndo
		{
			/*font-size: 10px;*/
			font-weight: bold;
			/*margin-top: 15px;*/
		}
		#alberoEndo li > div.tipoEndo
		{
			/*font-size: 10px;*/
			font-style: italic;
			font-weight: normal;
			text-transform: capitalize;
		}
		#alberoEndo li > div.endo
		{
			font-size: 10px;
			font-style: normal;
			font-weight: normal;
			text-transform: none;
		}
		#alberoEndo li > div.endo > img
		{
			vertical-align: bottom;
		}
		#alberoEndo li > div.endo > img:hover
		{
			cursor: pointer;
		}
	</style>
	<script type="text/javascript">
		require(['jquery', 'jquery.ui'], function ($) {

			function inizializzaContenutoAccordion(elName) {
				$(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
				$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
				$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
			}

			$(function () {

				var el = $("#dettagliEndo");

				if (el.length == 0)
					$('#contenuti').append("<div id='dettagliEndo'></div>");

				// preparo il dialog per i dettagli dell'endo
				$("#dettagliEndo").dialog({
					width: 800,
					height: 500,
					title: "Dettagli dell\'endoprocedimento",
					modal: true,
					autoOpen: false,
					open: function () {
						inizializzaContenutoAccordion('#dettagliEndo');
					}
				});


				$('li > div.endo > img').click(function () {
					var idEndo = $(this).attr('idEndo');

					mostraDettagli(this, idEndo);
				});
			});

			function mostraDettagli(sender, id) {
				var url = '<%= ResolveClientUrl("~/Public/MostraDettagliEndo.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&Id=' + id;

				var oldHtml = $(sender).attr('src');
				var clickElement = $(sender);

				clickElement.attr('src', '<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>');

				$("#dettagliEndo").load(url, null, function (responseText, textStatus, XMLHttpRequest) {
					clickElement.attr('src', oldHtml);
					$(this).dialog('open');
				});
			}
		});
	</script>
	<div class="inputForm">
		<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" >
			<asp:View runat="server" ID="listaEndoView">
				<asp:Panel runat="server" ID="pnlEndoPrincipale">
					<fieldset class="blocco aperto">
						<legend class="gruppoEndo">
							<asp:Literal runat="server" ID="ltrTitoloEndoPrincipale" Text="Procedimento principale" /></legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoPrincipale" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEndoAttivati">
					<fieldset class="blocco aperto">
						<legend class="gruppoEndo">
							<asp:Literal runat="server" ID="ltrTitoloEndoAttivati" Text="Procedimenti attivati" /></legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoAttivati" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEndoAttivabili">
					<fieldset class="blocco aperto">
						<legend class="gruppoEndo">
							<asp:Literal runat="server" ID="ltrTitoloEndoAttivabili" Text="Procedimenti attivabili" /></legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoAttivabili" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlAltriEndo">
					<fieldset class="blocco aperto">
						<legend class="gruppoEndo">
							<asp:Literal runat="server" ID="ltrTitoloAltriEndo" Text="Altri endoprocedimenti attivati"></asp:Literal>
						</legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaAltriEndo" ModificaProcedimentiProposti="false" />
					</fieldset>


				</asp:Panel>

				<div class="bottoni">
					<asp:Button runat="server" ID="cmdAttivaAltriEndo" Text="Altri endoprocedimenti" OnClick="cmdAttivaAltriEndo_click" />
				</div>
			</asp:View>

			<asp:View runat="server" ID="altriEndoView">

			<fieldset class="blocco aperto">
				<legend class="gruppoEndo">
					<asp:Literal runat="server" ID="ltrTitoloAltriEndoAttivabili" Text="Altri endoprocedimenti attivabili"></asp:Literal>
				</legend>
				<uc1:GrigliaEndo runat="server" ID="grigliaAltriEndoAttivabili" />
			</fieldset>
			
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdTornaAllaLista" Text="Torna alla lista degli endoprocedimenti" OnClick="cmdTornaAllaLista_click" />
			</div>
			</asp:View>
		</asp:MultiView>
	</div>
</asp:Content>
