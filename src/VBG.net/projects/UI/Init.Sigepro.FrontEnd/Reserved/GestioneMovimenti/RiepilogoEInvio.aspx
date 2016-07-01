<%@ Page Title="Conferma e invio" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="RiepilogoEInvio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.RiepilogoEInvio" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<style media="all">
		li
		{
			padding-bottom: 10px;
			list-style-type:disc;
		}
		ul
		{
			margin-left: 10px;
		}
	</style>
	<script type="text/javascript">
		function confermaInvio() {
			return confirm("Proseguendo con l'invio non sarà più possibile apportare modifiche ai dati immessi.\r\nContinuare?");
		}

		$(function () {
			$('#<%=cmdAggiungiAllegato.ClientID %>').click(function (e) {
				e.preventDefault();
				$('#bottoniMovimento').fadeOut(function () {
					$('#bottoniAllegato').fadeIn();
				});
			});

			$('#attenderePrego').css('display', 'none');

			$('#<%=cmdAnnullaAggiuntaAllegato.ClientID %>').click(function (e) {
				e.preventDefault();

				$('#bottoniAllegato').fadeOut(function () {
					$('#bottoniMovimento').fadeIn();
				});
			});

			$('#divSalvataggioNote').css('display', 'none');

			$('#<%=txtNote.ClientID %>').change(function () {
				$('#divSalvataggioNote').fadeIn(function () {
					$('#<%=cmdSalvaNote.ClientID%>').focus();
				});
			});

			$('#<%=txtNote.ClientID %>').keydown(function () {
				$('#divSalvataggioNote').fadeIn();
			});

			$('.uploadAllegato').click(function (e) {
				$('#attenderePrego').dialog({
					title: 'Invio del file in corso...',
					modal: true,
					width: 500
				});
			});
		});
	</script>

	<div class="inputForm">
		<fieldset>
			<legend><asp:Literal runat="server" Text='Riepilogo dei dati immessi'/></legend>
			<div>
				<asp:Label runat="server" ID="Label1" AssociatedControlID="txtNote">Note</asp:Label>
				<asp:TextBox runat="server" ID="txtNote" TextMode="MultiLine" Columns="60" Rows="5" Text='<%# DataBinder.Eval(MovimentoDaEffettuare, "Note") %>'/>
			</div>

			<div class="bottoni" id="divSalvataggioNote">
				<asp:Label runat="server" ID="Label4" AssociatedControlID="cmdSalvaNote">&nbsp;</asp:Label>
				<asp:Button runat="server" ID="cmdSalvaNote" Text="Aggiorna note" OnClick="cmdSalvaNote_Clinck" />
			</div>
		</fieldset>

		<fieldset id="fsSchede" runat="server" visible='<%# Convert.ToInt32( DataBinder.Eval( MovimentoDaEffettuare, "RiepiloghiSchedeDinamiche.Count") ) > 0 %>'>
			<legend>Schede compilate</legend>
			<ul>
				<asp:Repeater runat="server" ID="rptSchedeCompilate">
					<ItemTemplate>
						<li>
							<b><asp:Literal runat="server" ID="ltrNomeScheda" Text='<%# Eval("NomeScheda") %>' /></b><br />
							<i>
								<asp:HyperLink runat="server" 
												ID="hlDownloadRiepilogo" 
												Text='<%# Eval("NomeFile") %>' 
												NavigateUrl='<%# Eval( "CodiceOggetto" , "~/MostraOggetto.ashx?IdComune=" + IdComune + "&codiceOggetto={0}")%>' 
												Target="_blank"/>
							</i>
						</li>
					</ItemTemplate>
				</asp:Repeater>
			</ul>
		</fieldset>

		<fieldset id="fldSetAllegati" runat="server" visible='<%# Convert.ToInt32(DataBinder.Eval( MovimentoDaEffettuare,"Allegati.Count" )) > 0 %>'>
			<legend>Allegati inseriti</legend>
			<div>
				<asp:GridView ID="dgAllegatiMovimento" runat="server" 
														GridLines="None" 
														AutoGenerateColumns="False" 
														OnRowCommand="OnRowCommand"
														DataSource='<%# DataBinder.Eval( MovimentoDaEffettuare, "Allegati" ) %>' >
					<Columns>
						<asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
						<asp:TemplateField HeaderText="Nome file">
							<ItemTemplate>
								<asp:HyperLink ID="lnkMostraAllegato" runat="server" 
												NavigateUrl='<%# Eval( "IdAllegato", "~/MostraOggetto.ashx?idComune=" + IdComune + "&CodiceOggetto={0}" ) %>' 
												Target="_blank" 
												Text='<%# Eval("Note") %>'  />
								<div>
									<asp:Label runat="server" ID="lblMessaggioFirmaDigitale" Text="Attenzione, il file non è firmato digitalmente oppure non contiene firme digitali valide" 
												CssClass="errori" 
												Visible='<%# !(bool)Eval("FirmatoDigitalmente")%>'/>
								</div>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="">
							<ItemTemplate>
								<asp:LinkButton ID="lnkElimina" runat="server" 
												CommandArgument='<%# Eval("IdAllegato") %>' 
												Text="Elimina" 
												OnClientClick="return confirm('Eliminare l\'allegato selezionato\?')" 
												OnClick="EliminaAllegato"/>

								<asp:LinkButton runat="server" 
												ID="lnkFirma" 
												Text="Firma" 
												Visible='<%# !(bool)Eval("FirmatoDigitalmente") %>' 
												CommandName="Firma"
												CommandArgument='<%# Eval("IdAllegato") %>' />
								</ItemTemplate>
						</asp:TemplateField>
					</Columns>
					<EmptyDataTemplate>
						<div>Non sono ancora stati caricati allegati</div>
					</EmptyDataTemplate>
				</asp:GridView>
			</div>
		</fieldset>

		<fieldset id="bottoniAllegato" style='<%= MostraBottoniAllegato ? "" : "display:none"%>'>
			<legend>Aggiungi allegato</legend>
			<div>
				<asp:Label runat="server" ID="Label2" AssociatedControlID="txtDescrizioneAllegato">Descrizione allegato<br /><i>(obbligatoria)</i></asp:Label>
				<asp:TextBox runat="server" ID="txtDescrizioneAllegato" TextMode="MultiLine" Columns="60" Rows="5" Text=''/>
			</div>

			<div>
				<asp:Label runat="server" ID="Label3" AssociatedControlID="fuAllegato">Allegato firmato digitalmente</asp:Label>
				<asp:FileUpload runat="server" ID="fuAllegato" />
			</div>

			<div class="bottoni" >
				<asp:Button runat="server" ID="cmdCaricaAllegato" Text="Aggiungi allegato" OnClick="cmdCaricaAllegato_Click" class="uploadAllegato" />
				<asp:Button runat="server" ID="cmdAnnullaAggiuntaAllegato" Text="Annulla" />
			</div>

		</fieldset>

		<fieldset id="bottoniMovimento" style='<%= MostraBottoniAllegato ? "display:none" : ""%>'>
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click"/>
				<asp:Button runat="server" ID="cmdAggiungiAllegato" Text="Aggiungi allegato" />
				<asp:Button runat="server" ID="cmdConferma" OnClick="cmdConferma_Click" Text="Trasmetti al comune" OnClientClick="return confermaInvio();" />
			</div>
		</fieldset>
	</div>

	<div id="attenderePrego">
		L'invio di un file di grandi dimensioni potrebbe richiedere anche alcuni minuti.<br /><br />
		Attendere senza effettuare alcuna operazione.
	</div>

</asp:Content>
