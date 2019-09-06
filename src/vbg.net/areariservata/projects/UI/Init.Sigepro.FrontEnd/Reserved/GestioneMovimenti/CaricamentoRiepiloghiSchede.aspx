<%@ Page Title="Riepiloghi schede" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master"
	AutoEventWireup="true" CodeBehind="CaricamentoRiepiloghiSchede.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CaricamentoRiepiloghiSchede" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		$(function () {
			$('.uploadRiepilogo').css('display', 'none');
			$('#attenderePrego').css('display', 'none');

			$('.selezionaRiepilogo').click(function (e) {
				e.preventDefault();

				$('.uploadRiepilogo').fadeOut();

				$(this).fadeOut(function () {
					$(this).parent().parent().find('.uploadRiepilogo').fadeIn();
				});
			});

			$('.annullaUploadRiepilogo').click(function (e) {
				e.preventDefault();

				var selezionaRiepilogo = $(this).parent().find('.selezionaRiepilogo');

				$('.uploadRiepilogo').fadeOut(function () {
					selezionaRiepilogo.fadeIn();
				});
			});

			$('.invia').click(function () {
			    $('#caricamentoFileIncorso').modal('show');
			});
		})
	</script>

	<div id="contenutoStep">
		<div class="descrizioneStep">
			<asp:Literal runat="server" ID="ltrDescrizioneStep" />
		</div>
		<div class="inputForm">
				<h2>Firma schede</h2>
				<asp:GridView runat="server" ID="gvRiepiloghi" 
											 AutoGenerateColumns="false" 
											 GridLines="None" 
											 OnRowUpdating="gvRiepiloghi_RowUpdating"
											 OnRowDeleting="gvRiepiloghi_RowDeleting"
											 OnRowCommand="gvRiepiloghi_RowCommand"
											 DataKeyNames="IdScheda"
                                             CssClass="table">
					<Columns>
						<asp:TemplateField ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Image runat="server" ID="imgPresente"
											ImageUrl='<%# ((int?)Eval("CodiceOggetto")).HasValue ? "~/Images/Check-icon.png" : "~/Images/required-16x16.png"%>'
											Title='<%# ((int?)Eval("CodiceOggetto")).HasValue ? "Riepilogo della scheda caricato correttamente" : "Per proseguire è necessario scaricare, firmare e ricaricare il riepilogo della scheda"%>'
								 />
							</ItemTemplate>
						</asp:TemplateField>
						<asp:BoundField HeaderText="Scheda" DataField="NomeScheda" ItemStyle-Width="40%" />

						<asp:TemplateField HeaderText="Scarica" ItemStyle-Width="5%" HeaderStyle-HorizontalAlign="Center" ItemStyle-HorizontalAlign="Center">
							<ItemTemplate>
								<asp:ImageButton runat="server" 
												 ID="lbDownload" 
												 ImageUrl="~/Images/pdf16x16.gif" 
												 CommandName="DownloadModello" 
												 CommandArgument='<%# Eval("IdScheda") %>'/>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="Documento"  ItemStyle-Width="35%">
							<ItemTemplate>
								<asp:HyperLink runat="server" 
												ID="lnkDownloadOggetto" 
												Target="_blank" 
												NavigateUrl='<%# Eval( "CodiceOggetto" , "~/MostraOggetto.ashx?IdComune=" + IdComune + "&codiceOggetto={0}")%>' 
												Text='<%# Eval("NomeFile") %>'
												Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>'
											 />
								<asp:FileUpload runat="server" 
												ID="fuAllegato" 
												class="uploadRiepilogo"
												style="width:350px;" 
												Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>' />	

                                <%if (this.RichiedeFirmaDigitale) {%>
								    <div>
									    <asp:Label runat="server" ID="lblMessaggioFirmaDigitale" Text="Attenzione, il file non è firmato digitalmente oppure non contiene firme digitali valide" 
												    CssClass="errori" 
												    Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue && !(bool)Eval("FirmatoDigitalmente")%>'/>
								    </div>
                                <%} %>
							</ItemTemplate>
						</asp:TemplateField>
						
						<asp:TemplateField HeaderText="Azioni"  ItemStyle-Width="15%">
							<ItemTemplate>
								<asp:LinkButton runat="server" 
												ID="lnkAllega" 
												Text="Allega" 
												class="selezionaRiepilogo" 
												Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>' />
								
								<asp:LinkButton runat="server" 
												ID="lnkInvia" 
												Text="Invia" 
												class="uploadRiepilogo invia" 
												Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>' 
												CommandName="Update" />
								
								<asp:LinkButton runat="server" 
												ID="lnkAnnulla" 
												Text="Annulla" 
												class="uploadRiepilogo annullaUploadRiepilogo" 
												Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>' />
								
								<asp:LinkButton runat="server" 
												ID="lnkElimina" 
												Text="Elimina" 
												Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>' 
												CommandName="Delete" 
												OnClientClick="return confirm('Rimuovere il riepilogo selezionato\?')" />

								<asp:LinkButton runat="server" 
												ID="lnkFirma" 
												Text="Firma" 
												Visible='<%# this.RichiedeFirmaDigitale && ((int?)Eval("CodiceOggetto")).HasValue && !(bool)Eval("FirmatoDigitalmente") %>' 
												CommandName="Firma"
												CommandArgument='<%# ((int?)Eval("CodiceOggetto")) %>' />

							</ItemTemplate>
						</asp:TemplateField>
					</Columns>
				</asp:GridView>

				<div class="bottoni" id="bottoniMovimento">
					<asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
					<asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
				</div>
		</div>
	</div>
</asp:Content>
