<%@ Page Title=" " Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="CompilaSchedeDinamiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CompilaSchedeDinamiche" %>

<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>

<asp:Content ID="Content2" ContentPlaceHolderID="headPagina" runat="server">
	
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div id="contenutoStep">
		<div id="datiDinamici">
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="listaView">
					<asp:Repeater runat="server" ID="rptSchedeDaCompilare">
						<HeaderTemplate>
							<ul>
								<li><b>Schede da compilare</b>
								<ul>
						</HeaderTemplate>
						<ItemTemplate>
								<li class='elementoListaDatiDinamici <%# Convert.ToBoolean(DataBinder.Eval(Container.DataItem,"Compilata")) ? "compilato" : ""%>'>
									<asp:LinkButton runat="server" ID="lnkSchedaDinamica" Text='<%# Eval("Descrizione") %>'
										CommandArgument='<%# Eval("IdScheda") %>' OnClick="lnkSchedaDinamica_Click" />
								</li>
						</ItemTemplate>
						<FooterTemplate>
								</li>
								</ul>
							</ul>
						</FooterTemplate>
					</asp:Repeater>
					<div class="bottoni" id="bottoniMovimento">
						<asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
						<asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
					</div>

				</asp:View>
				<asp:View runat="server" ID="dettaglioView">
					<script type="text/javascript">
						require(['jquery','jquery.form'], function ($) {
							var g_datiDinamiciExtender = null;

							$(function () {
								g_datiDinamiciExtender = new DatiDinamiciExtender('<%=pnlErrori.ClientID %>', '<%=cmdSalva.ClientID %>', $('.d2WatchButton'));
							});
						});
					</script>
					<div class="titoloScheda">
						Scheda:
						<asp:Label runat="server" ID="lblTitoloModello" CssClass="" />
					</div>
					<asp:Panel runat="server" ID="pnlErrori" CssClass="errori">
					</asp:Panel>
					<dd:ModelloDinamicoRenderer ID="renderer" runat="server" />
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdSalva" Text="Salva e torna alla lista delle schede"
							OnClick="cmdSalva_Click" />
						<asp:Button runat="server" ID="cmdChiudi" Text="Torna alla lista delle schede senza salvare"
							OnClientClick="return confirm('Eventuali modifiche non salvate andranno perse.\nContinuare\?')"
							OnClick="cmdChiudi_Click" />
						<span id="salvataggioInCorso">Salvataggio dati in corso...</span>
					</div>
				</asp:View>
			</asp:MultiView>
		</div>
	</div>
</asp:Content>
