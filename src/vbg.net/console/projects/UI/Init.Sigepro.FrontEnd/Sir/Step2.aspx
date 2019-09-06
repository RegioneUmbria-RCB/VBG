<%@ Page Title="" Language="C#" MasterPageFile="~/Sir/SirMaster.Master" AutoEventWireup="true" CodeBehind="Step2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Sir.Step2" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
	<script type="text/javascript">

		require(['jquery', 'app/alberointerventi', 'app/wrapDescrizioneNodiPadre'], function ($, alberoInterventi, wrapDescrizioneNodiPadre) {

			$(function () {
				$('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-interno-verde.png")%>');

				alberoInterventi.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;
			});

		});

	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">
	
	<div class="sir-sezione">
		<div class="sir-titolo-sezione">
			<h2>Ricerca</h2>
			<h1>L'INTERVENTO DI TUO INTERESSE</h1>
		</div>	

		<div class="sir-bottoni-sezione">
			<a href="#" id="lnkRicerca">
				<img src='<%= ResolveClientUrl("~/images/contenuti/ricerca-testuale.png") %>' alt="ricerca testuale" border="0" />
			</a>
		</div>
		<div class="clear"></div>

		<div id="stepContenuto" class="ricercaIntervento">

			<%--<asp:Literal runat="server" ID="ltrMessaggioErrore" />--%>
			<cc1:AlberoInterventiJs runat="server" ID="alberoInterventi" 
									EvidenziaVociAttivabiliDaAreaRiservata="true" 
									Note="<i>Le voci contrassegnate con * sono attivabili tramite i servizi online</i>"
									OnFogliaSelezionata="InterventoSelezionato" />
		</div>

	</div>



</asp:Content>

