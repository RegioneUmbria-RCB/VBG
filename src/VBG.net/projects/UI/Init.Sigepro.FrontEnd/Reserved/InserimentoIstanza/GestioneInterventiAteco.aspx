<%@ Page Title="Untitled page" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneInterventiAteco.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneInterventiAteco" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>
<%@ Register TagPrefix="cc2" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Ateco" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">

<link href='<%=ResolveClientUrl("~/css/contenuti/dettagliIntervento.css") %>' type="text/css" rel="stylesheet" />

	<script type="text/Javascript">

		require(['jquery', 'app/alberointerventi', 'app/wrapDescrizioneNodiPadre', 'jquery.ui'], function ($, AlberoInterventi, wrapDescrizioneNodiPadre) {

			AlberoInterventi.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;

			function inizializzaContenutoAccordion(elName) {
				$(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
				$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
				$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
			}

			$(function () {

				var el = $("#dettagliIntervento");
				var el2 = $("#dettagliEndo");

				if (el.length == 0)
					$('#contenuti').append("<div id='dettagliIntervento'></div>");

				if (el2.length == 0)
					$('#contenuti').append("<div id='dettagliEndo'></div>");

				// preparo il dialog per i dettagli dell'endo
				$("#dettagliEndo").dialog({
					width: 600,
					height: 500,
					title: "Dettagli dell\'endoprocedimento",
					modal: true,
					autoOpen: false,
					open: function () {
						inizializzaContenutoAccordion('#dettagliEndo');
					}
				});

				$('#dettagliIntervento').dialog({
					height: 500,
					width: 800,
					title: "Dettagli dell\'intervento",
					modal: true,
					autoOpen: false,
					open: function () {
						inizializzaContenutoAccordion('#dettagliIntervento');

						$('.linkDettagliendo').click(function (e) {
							e.preventDefault();

							var url = $(this).attr('href');

							$("#dettagliEndo").load(url, null, function () {
								$(this).dialog('open');
							});
						});
					}
				});


			});

			function mostraDettagli(sender, id) {
				var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&fromAreaRiservata=true&Id=' + id;

				var oldHtml = $(sender).html();
				var clickElement = $(sender);

				clickElement.html('<img src=\'<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>\' />');
				$("#dettagliIntervento").html('Caricamento in corso...');
				$("#dettagliIntervento").load(url, null, function (responseText, textStatus, XMLHttpRequest) {
					clickElement.html(oldHtml);
					$(this).dialog('open');
				});
			}

			window.mostraDettagli = mostraDettagli;
		});
	</script>

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

	<div class="inputForm">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_activeViewChanged">
		
		<asp:View runat="server" ID="introduzioneView">
			<div>
				<asp:Literal runat="server" ID="ltrTestoIntroduzione"></asp:Literal>
			</div>
			
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdRicercaAteco" Text="Ricerca per classificazione ATECO" OnClick="cmdRicercaAteco_Click" />
				<asp:Button runat="server" ID="cmdRicercaIntervento" Text="Ricerca per classificazione attività produttiva" OnClick="cmdRicercaIntervento_Click" />
				<asp:Button runat="server" ID="cmdAnnullaRicerca" Text="Annulla" OnClick="AnnullaRicercaClick" />
			</div>
		</asp:View>
	
		<asp:View runat="server" ID="atecoView">
			<fieldset>
				<legend><asp:Literal runat="server" ID="ltrIntestazioneRicercaAteco" /></legend>
				<asp:Literal runat="server" ID="ltrTestoRicercaAteco" />
				<a href="#" id="lnkRicerca">[Ricerca testuale]</a>
			</fieldset>
		
			<cc2:AlberoAtecoJs runat="server" 
							   ID="alberoAteco" 
							   ClientIdLinkRicerca="lnkRicerca" 
							   OnFogliaSelezionata="BindAlberoInterventi" AreaRiservata="true" />

			<div class="bottoni">
				<asp:Button runat="server" ID="cmdAnnullaAteco" Text="Annulla" OnClick="AnnullaRicercaClick" />
			</div>				   
		</asp:View>
		
		<asp:View runat="server" ID="alberoView">
			<fieldset>
				<legend><asp:Literal runat="server" ID="ltrIntestazioneRicercaIntervento" /></legend>
				<asp:Literal runat="server" ID="ltrTestoRicercaIntervento" />
				<a href="#" id="lnkRicerca">[Ricerca testuale]</a>
		
			<cc1:AlberoInterventiJs EvidenziaVociAttivabiliDaAreaRiservata="false" runat="server" AreaRiservata="true" ID="alberoInterventi" OnFogliaSelezionata="InterventoSelezionato" />

			<div class="bottoni">
				<asp:Button runat="server" ID="cmdAnnullaAlbero" Text="Annulla" OnClick="AnnullaRicercaClick" />
			</div>				   
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="dettaglioView">

			<script type="text/javascript">

			require(['jquery','jquery.ui'],function($){
				var contieneDati = <%=VerificaEsistenzaDatiStepSuccessivi().ToString().ToLower() %>;

				$(function () {
					$('#<%=cmdSelezionaIntervento.ClientID %>').click(function (e) {

						if (!contieneDati)
							return;

						$('#alertDiv').dialog({
							resizable: false,
							title: 'Attenzione',
							width: 550,
							modal: true,
							buttons: {
								"Mantieni l'intervento corrente": function () {
									$(this).dialog("close");
								},
								"Seleziona un intervento differente": function () {
									$(this).dialog("close");
									contieneDati = false;
									$('#<%=cmdSelezionaIntervento.ClientID %>').trigger('click');
								}
							}
						});

						e.preventDefault();
					});

				});
			});
			</script>


			<fieldset>
				<legend><asp:Literal runat="server" ID="ltrIntestazioneDettaglio" /></legend>
				<asp:Literal runat="server" ID="ltrTestoDettaglio" />
			
		
				<cc1:InterventiTreeRenderer runat="server" 
											ID="treeRendererDettaglio" 
											StartCollapsed="false" 
											CssClass="treeView"
											UrlIconaHelp="~/Images/help_interventi.gif"
											UrlDettagliIntervento="javascript:mostraDettagli(this,{0});"									
											/>
				<div class="clear"></div>
				<div class="bottoni">
					<asp:Button runat="server" ID="cmdSelezionaIntervento" Text="Cambia l’intervento selezionato" OnClick="cmdSelezionaIntervento_Click" />
				</div>
				<div style="display:none" id="alertDiv">
					Modificando l'intervento selezionato eventuali endoprocedimenti ed allegati inseriti nei passaggi successivi verranno eliminati.<br /><br />
					La scelta NON è reversibile, si desidera proseguire? 
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
	
</div>
	<div id="debugDiv"></div>
</asp:Content>
