<%@ Page Title=" " Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="CompilaSchedeDinamiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CompilaSchedeDinamiche" %>

<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>

<asp:Content ID="Content2" ContentPlaceHolderID="headPagina" runat="server">
		<script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.ui.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.tooltip.fix.js")%>'></script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div id="contenutoStep">
		<div id="datiDinamici">
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="listaView">

                    <h3>Schede da compilare</h3>

					<asp:Repeater runat="server" ID="rptSchedeDaCompilare">
						<HeaderTemplate>
							<ul>
								<ul>
						</HeaderTemplate>
						<ItemTemplate>
								<li class='elementoListaDatiDinamici'>
                                    <i class='glyphicon <%# Convert.ToBoolean(DataBinder.Eval(Container.DataItem,"Compilata")) ? "glyphicon-ok" : "glyphicon-pencil"%>'></i>
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

						    function fixAggiungiRiga() {
						        var button = $('tr.bloccoMultiploAggiungiRiga>td>input');
						        var el = $('<i />', { 'class': 'ion-android-add-circle' });

						        button.addClass('aggiungiRiga');
						        button.before(el);
						    }

						    function fixAggiungiBlocco() {
						        var button = $('#datiDinamici input[name*=btnAggiungi][type=submit][value=\'Aggiungi\']');

						        var el = $('<i />', { 'class': 'ion-android-add-circle' });

						        button.addClass('aggiungiRiga');
						        button.before(el);
						    }

						    function fixEliminaRiga() {
						        var button = $('.divEliminazioneBlocco>a');
						        var el = $('<i />', { 'class': 'ion-android-remove-circle' });

						        button.addClass('eliminaRiga');
						        button.before(el);

						    }

						    $(function () {
						        g_datiDinamiciExtender = new DatiDinamiciExtender('<%=pnlErrori.ClientID %>', '<%=cmdSalva.ClientID %>', $('.d2WatchButton'));

						        fixAggiungiRiga();
						        fixAggiungiBlocco();
						        fixEliminaRiga();
						    });
						});
					</script>

					<h2>
						<asp:Label runat="server" ID="lblTitoloModello" CssClass="" />
					</h2>

					<asp:Panel runat="server" ID="pnlErrori" CssClass="alert alert-danger" Style="display: none"></asp:Panel>

					<dd:ModelloDinamicoRenderer ID="renderer" runat="server" />

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdSalva" Text="Salva e torna alla lista delle schede" OnClick="cmdSalva_Click" />
						<asp:Button runat="server" ID="cmdChiudi" Text="Torna alla lista delle schede senza salvare" CssClass="d2WatchButton" OnClick="cmdChiudi_Click" />
					</div>
				</asp:View>
			</asp:MultiView>
		</div>
	</div>
</asp:Content>
