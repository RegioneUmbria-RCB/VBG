<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDatiDinamici.aspx.cs" 
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDatiDinamici" Title="Untitled page" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>
<%@ Register TagPrefix="d2" Src="~/Reserved/InserimentoIstanza/DatiDinamici/PaginatoreSchedeDinamiche.ascx" TagName="PaginatoreSchedeDinamiche"%>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
	<script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.ui.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.tooltip.fix.js")%>'></script>

</asp:Content>


<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">


	<asp:ScriptManager runat="server" ID="ScriptManager1">
	</asp:ScriptManager>
	<div id="datiDinamici">
	
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="selezioneView">
			<asp:Panel runat="server" ID="pnlSchedaCittadiniExtracomunitari">
				<ul>
					<li>
						<b>
							<asp:Literal runat="server" ID="lblTestoSchedaCittadiniExtracomunitari" />
						</b>
						<ul>
							<li class="elementoListaDatiDinamici <%= GetClasseCssSchedaDinamicaCompilata( IsSchedaDinamicaEcCompilata ) %>">
								<asp:LinkButton runat="server" ID="lnkEcSelezioneSchedaIntervento" OnClick="OnSchedaSelezionata" />
								<asp:Literal runat="server" ID="ltrEcAsterisco" />
							</li>
						</ul>
					</li>

				</ul>
			</asp:Panel>

			<div >
				<ul>
					<asp:Repeater runat="server" ID="rptSchedeIntervento">
						<HeaderTemplate>
							<li>
								<b>Schede dell'intervento "<%=ReadFacade.Domanda.AltriDati.Intervento%>"</b>
								<ul>			
						</HeaderTemplate>

						<ItemTemplate>
							<li class="elementoListaDatiDinamici <%# DataBinder.Eval( Container.DataItem , "Compilata" )%>">
								<asp:LinkButton runat="server" ID="lnkSelezioneSchedaIntervento" Text='<%# Eval("Descrizione") %>' CommandArgument='<%#Eval("Codice") %>' OnClick="OnSchedaSelezionata" />
								<asp:Literal runat="server" ID="ltrAsterisco" Text='<%# !Convert.ToBoolean(Eval("Facoltativa")) ? "*" : " " %>' />
							</li>
						</ItemTemplate>

						<FooterTemplate>
								</ul>
							</li>		
						</FooterTemplate>
					</asp:Repeater>
			
			
					<asp:Repeater runat="server" ID="rptEndoprocedimenti">
					<ItemTemplate>
						<li>
							<b>
							<asp:Literal runat="server" ID="ltrNomeEndo" Text='<%# Eval("DescrizioneIntervento") %>'/></b>
							<ul>
							<asp:Repeater runat="server" ID="rptSchedeEndo" DataSource='<%# Eval("Schede") %>'>
								<ItemTemplate>
									<li class="elementoListaDatiDinamici <%# DataBinder.Eval( Container.DataItem , "Compilata" ) %>">
										<asp:LinkButton runat="server" ID="lnkSelezioneSchedaIntervento" Text='<%# Eval("Descrizione") %>' CommandArgument='<%#Eval("Codice") %>' OnClick="OnSchedaSelezionata" />
										<asp:Literal runat="server" ID="ltrAsterisco" Text='<%# !Convert.ToBoolean(Eval("Facoltativa")) ? "*" : " " %>' />
									</li>
								</ItemTemplate>
							</asp:Repeater>
							</ul>
						</li>
					</ItemTemplate>
				
					</asp:Repeater>
				</ul>
			</div>
			<i>(*) E' necessario compilare tutte le schede contrassegnate con un asterisco</i>
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

			<d2:PaginatoreSchedeDinamiche runat="server" id="paginatoreSchedeDinamiche" OnIndiceSelezionato="OnIndiceSelezionato" OnNuovaScheda="OnNuovaScheda" OnEliminaScheda="OnSchedaEliminata" />
			<div class='<%= renderer.DataSource.ModelloMultiplo ? "contenutoScheda" : String.Empty %>' >
				<div class="titoloScheda">
					<asp:Label runat="server" ID="lblTitoloModello" CssClass="" />
				</div>
				<asp:Panel runat="server" ID="pnlErrori" CssClass="errori"></asp:Panel>
				<dd:ModelloDinamicoRenderer ID="renderer" runat="server" />
			
				<div class="bottoni">
					<asp:Button runat="server" ID="cmdSalvaEResta" Text="Salva" OnClick="cmdSalvaeResta_Click"/>
					<asp:Button runat="server" ID="cmdSalva" Text="Salva e torna alla lista delle schede" OnClick="cmdSalva_Click"/>
					<asp:Button runat="server" ID="cmdChiudi" Text="Torna alla lista delle schede senza salvare" CssClass="d2WatchButton" OnClick="cmdChiudi_Click"/>
					<span id="salvataggioInCorso">Salvataggio dati in corso...</span>
				</div>
			</div>
		</asp:View>
		
	
	</asp:MultiView>

	</div>

</asp:Content>
