<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="AllegatiBandoIncomingBindingGrid.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1.AllegatiBandoIncomingBindingGrid" %>
<fieldset class="blocco aperto allegati-azienda">
	<legend><%=NomeAzienda%></legend>

	<asp:Repeater runat="server" ID="rptAllegati">
		<HeaderTemplate>
			<ul>
		</HeaderTemplate>

		<ItemTemplate>
			<li>
				<div class="bandi-allegato-azienda">
					<span class='<%# (bool)DataBinder.Eval(Container.DataItem,"HaAllegato") ? "ion-checkmark-round" : "ion-alert"%>'></span><asp:Literal runat="server" ID="ltrNomeAllegato" Text='<%#Eval("Descrizione")%>' /><br />

					<!-- 
					#	Gestione files con modello (allegati 1, 2, 3, 4, 7, 10)
					#		Se allegato non presente:
								- Scarica modello compilabile
								- Ricarica file compilato
					#		se allegato presente
								- Scarica modello compilabile
								- Visualizza file caricato
								- Carica un altro file
					#
					#	Gestione dei files senza modello
							se allegato non presente
								- Carica documento
							se allegato presente
								- Visualizza file
								- Sostituisci con un altro documento
								- Rimuovi					
					 -->
					 <asp:HyperLink runat="server" 
									ID="HyperLink1" 
									CssClass="download-modello-compilato"
									Visible='<%#Eval("HaModello") %>' 
									Target="_blank" NavigateUrl='<%#Eval("UrlDownloadModello") %>'><span class="ion-android-download"></span>Scarica modello compilabile<br /></asp:HyperLink>

					<asp:LinkButton runat="server" 
									CssClass="upload-button upload-modello-compilato"									
									ID="LinkButton2" 
									Visible='<%#((bool)Eval("HaModello") && !(bool)Eval("HaAllegato")) %>'><span class="ion-upload" ></span>Ricarica file compilato</asp:LinkButton>

					<asp:HyperLink runat="server" 
									class="bottone-allegati" 
									ID="lnkScaricaModello" 
									Visible='<%#((bool)Eval("HaModello") && (bool)Eval("HaAllegato")) %>' 
									Target="_blank" NavigateUrl='<%#Eval("UrlDownloadAllegatoConModello") %>'><span class="ion-eye"></span>Visualizza file (<i><asp:Literal runat="server" ID="ltrNomeFile" Text='<%#Eval("NomeFile") %>' /></i>)<br /></asp:HyperLink>
					
					<asp:LinkButton runat="server" 
									CssClass="upload-button"									
									ID="LinkButton1" 
									Visible='<%#((bool)Eval("HaModello") && (bool)Eval("HaAllegato")) %>'><span class="ion-upload" ></span>Ricarica un altro file compilato</asp:LinkButton>

					<%-- Gestione dei files senza modello --%>
					<asp:LinkButton runat="server" 
									CssClass="upload-button"									
									ID="lnkRicarica" 
									CommandName="Upload" 
									Visible='<%#(!(bool)Eval("HaModello") && !(bool)Eval("HaAllegato"))%>'><span class="ion-upload" ></span>Carica file</asp:LinkButton>

					<asp:HyperLink runat="server" 
									ID="HyperLink2" 
									Visible='<%#(!(bool)Eval("HaModello") && (bool)Eval("HaAllegato"))%>' 
									Target="_blank" NavigateUrl='<%#Eval("UrlDownloadAllegato") %>'><span class="ion-android-download"></span>Visualizza file (<i><asp:Literal runat="server" ID="Literal1" Text='<%#Eval("NomeFile") %>' /></i>)<br /></asp:HyperLink>

					<asp:LinkButton runat="server" 
									CssClass="upload-button"									
									ID="LinkButton3" 
									CommandName="Upload" 
									Visible='<%#(!(bool)Eval("HaModello") && (bool)Eval("HaAllegato"))%>'><span class="ion-upload" ></span>Sostituisci con un altro file<br /></asp:LinkButton>

					<asp:LinkButton runat="server" 
									ID="lnkRimuoviAllegato" 
									CommandName="Remove" 
									Visible='<%#(!(bool)Eval("HaModello") && (bool)Eval("HaAllegato"))%>' 
									OnClientClick="return confirm('Rimuovere il documento caricato?')"
									OnClick='OnDeleteClicked' 
									CommandArgument='<%#Eval("Id") %>'><span class="ion-trash-a" ></span>Rimuovi</asp:LinkButton>


					<div class="upload-form">
						<fieldset>
							<asp:HiddenField runat="server" id="hidId" Value='<%# Eval("Id") %>' />
							<asp:HiddenField runat="server" id="cercaTag" Value='<%# Eval("TagModello") %>' />
							<asp:FileUpload runat="server" id="fuAllegato" />
							
							<div class="bottoni">
								<asp:Button runat="server" ID="cmdUpload" CssClass="bottone-carica" Text="Carica file" OnClick="onFileUploaded" />
								<div class="caricamento-in-corso" style="display:none" >Caricamento in corso ...</div>
							</div>
						</fieldset>
					</div>
				</div>			
			</li>
		</ItemTemplate>	
		
		<FooterTemplate>
			</ul>
		</FooterTemplate>
	</asp:Repeater>
</fieldset>