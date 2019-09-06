<%@ Page Title="" Language="C#" MasterPageFile="~/Contenuti/ContenutiMaster.Master"
	AutoEventWireup="true" EnableViewState="false" CodeBehind="Default.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Contenuti.Default" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
	<script type="text/javascript">

		require(['jquery','jquery.hoverbox'], function ($) {
			$(function () {
				$('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-centrale-hp.png")%>');

				$('#imageRicercaInt').attr('title', $('#contenutiRicercaInt').html())
									 .hoverbox({ id: 'tooltipAteco' });
				$('#imageRicercaAteco').attr('title', $('#contenutiRicercaAteco').html())
									 .hoverbox({ id: 'tooltipAteco' });
			});
		});
	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">

	<div id="benvenuto">
		<h1><%= this._configurazione.Testi.FrameCentrale.Titolo%></h1>
		<h2>
            
            <%= String.IsNullOrEmpty(this._configurazione.Testi.FrameCentrale.Descrizione) ? 
                    "Nell’area informativa dello sportello dove gli imprenditori possono avviare o sviluppare un’impresa e ricevere tutti i chiarimenti sui requisiti, la modulistica e gli adempimenti necessari." : 
                    this._configurazione.Testi.FrameCentrale.Descrizione%>
			<!--Nell’area informativa dello sportello SUAP <br />
			dove gli imprenditori possono avviare o sviluppare<br />
			un’impresa e ricevere tutti i chiarimenti sui requisiti, <br />
			la modulistica e gli adempimenti necessari.-->
		</h2>
		<div class="tipologieRicerca">
			<%= this._configurazione.Testi.FrameCentrale.IntestazioneLinks%>
			
			<ul class="tipiRicerca">

				<%if (this._configurazione.Testi.FrameCentrale.Link1.Visibile){ %>
					<li id="imageRicercaInt">
						<a href='<%= GetUrlLink1()%>'>
							<div class="titolo"><%=this._configurazione.Testi.FrameCentrale.Link1.Intestazione %></div>
							<div class="descrizione"><%=this._configurazione.Testi.FrameCentrale.Link1.Testo %></div>
						</a>
						<div class="contenutiTooltip" id="contenutiRicercaInt">
							<%=this._configurazione.Testi.FrameCentrale.Link1.DescrizioneEstesa %>
						</div>
					</li>
				<%} %>


				<%if (this._configurazione.Testi.FrameCentrale.Link2.Visibile){ %>
					<li id="Li1">
						<a href='<%= GetUrlLink2()%>'>
							<div class="titolo"><%=this._configurazione.Testi.FrameCentrale.Link2.Intestazione %></div>
							<div class="descrizione"><%=this._configurazione.Testi.FrameCentrale.Link2.Testo %></div>
						</a>
						<div class="contenutiTooltip" id="Div1">
							<%=this._configurazione.Testi.FrameCentrale.Link2.DescrizioneEstesa %>
						</div>
					</li>
				<%} %>
			</ul>
		</div>
	</div>
			
</asp:Content>
