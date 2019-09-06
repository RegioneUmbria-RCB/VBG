<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="FirmaEInvioIncoming.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1.FirmaEInvioIncoming" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"%>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

	<script type="text/javascript">

		require(['jquery', 'jquery.ui'], function($) {

			$(function() {
				$('.upload-form').hide();
				$('.upload-form').each(function(idx, item) {
					$(item).parent().find('.upload-button').on('click', function(e) {

						e.preventDefault();

						$(item).show();
					});
				});

				$('.bottone-carica').on('click', function(e) {
					$(this).hide(function() {
						$(this).parent().find('.caricamento-in-corso').show();
					});
				});

				$('.upload-modello-compilato').hide();
				$('.download-modello-compilato').on('click', function() {
					$(this).parent().find('.upload-modello-compilato').show();
				});


				$('.bottoneInvio').click(function (e) {

				    var msg = "Non sarà più possibile apportare modifiche alla domanda successivamente all'invio. Procedere con l'invio della domanda?";

				    if (!confirm(msg))
				    {
				        e.preventDefault();
				        return false;
				    }

					nascondiBottoni();
					mostraMessaggio();
				});

				function nascondiBottoni() {
					$('.bottoni').css('display', 'none')
				}

				function mostraMessaggio() {
				    $('.modal-invio-istanza-in-corso').modal('show');
				}
			});
		});
	</script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">

		<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
			<asp:View runat="server" ID="vwAllegati">			

				<fieldset>
					<asp:Repeater runat="server" ID="rptAllegatiDaFirmare">
						<HeaderTemplate>
							<div class="allegati-azienda">
							<ul >
						</HeaderTemplate>
						<ItemTemplate>
							<li class="bandi-allegato-azienda">
							<span class='<%# (bool)DataBinder.Eval(Container.DataItem,"HaFileFirmato") ? "ion-checkmark-round" : "ion-alert"%>'></span>
								<asp:Literal runat="server" ID="ltrNomeAllegato" Text='<%#Eval("Descrizione")%>' /><br />
								
								<% if (!BandoConcluso) { %>

								<asp:HyperLink runat="server" 
												ID="HyperLink1" 
												Visible='<%#Eval("HaFileFirmato")%>'
												NavigateUrl='<%#Eval("UrlDownloadFileFirmatoDigitalmente") %>'>
													<span class="ion-android-download"></span>Visualizza il file caricato (<i><asp:Literal runat="server" ID="Literal2" Text='<%#Eval("NomeFileFirmatoDigitalmente") %>' /></i>)<br />
								</asp:HyperLink>

								<asp:LinkButton runat="server" 
												ID="lnkRimuoviAllegato" 
												Visible='<%#Eval("HaFileFirmato")%>'
												OnClientClick="return confirm('Rimuovere il documento caricato?')" 
												OnClick='OnDeleteClicked'
												CommandArgument='<%#Eval("Id") %>'>
													<span class="ion-trash-a" ></span>Rimuovi
								</asp:LinkButton>

								<asp:HyperLink runat="server" 
												ID="HyperLink2" 
												Visible='<%#!(bool)Eval("HaFileFirmato")%>'
												Target="_blank" 
												NavigateUrl='<%#Eval("UrlDownloadFileDaFirmare") %>'>
													<span class="ion-android-download"></span>Scarica il file da firmare (<i><asp:Literal
														runat="server" ID="Literal1" Text='<%#Eval("NomeFile") %>' /></i>)<br />
								</asp:HyperLink>

								<asp:LinkButton runat="server" 
												CssClass="upload-button" 
												ID="lnkRicarica" 
												CommandName="Upload"
												Visible='<%#!(bool)Eval("HaFileFirmato")%>'>
													<span class="ion-upload" ></span>Carica il file firmato
								</asp:LinkButton>


								<div class="upload-form">
									<fieldset>
										<asp:HiddenField runat="server" ID="hidId" Value='<%# Eval("Id") %>' />
										<asp:FileUpload runat="server" ID="fuAllegato" />
										<div class="bottoni">
											<asp:Button runat="server" ID="cmdUpload" CssClass="bottone-carica" Text="Carica file"
												OnClick="OnFileUploaded" />
											<div class="caricamento-in-corso" style="display: none">
												Caricamento in corso ...</div>
										</div>
									</fieldset>
								</div>

								<% } %>
							</li>
						</ItemTemplate>
						<FooterTemplate>
							</ul>
							</div>
						</FooterTemplate>
					</asp:Repeater>

				</fieldset>

				<asp:Panel runat="server" ID="pnlInvio" GroupingText="Invio della pratica" >
					<asp:Literal runat="server" ID="ltrTestoInvio">
						L'invio della pratica è un'operazione irreversibile.<br />
						Proseguendo con l'invio <span style="text-decoration:underline">non sarà più possibile</span> apportare modifiche alla domanda.
					</asp:Literal>

					<div class="bottoni">
						<asp:Button runat="server" CssClass="bottoneInvio" id="cmdInvia" Text="Procedi con l'invio della pratica" OnClick="InviaPratica"/>
					</div>

				</asp:Panel>			
			


			</asp:View>


			<asp:View runat="server" ID="vwErrori">
				<asp:Label runat="server" ID="lblErroreInvio"></asp:Label>
			</asp:View>
		</asp:MultiView>


		
	</div>
</asp:Content>
