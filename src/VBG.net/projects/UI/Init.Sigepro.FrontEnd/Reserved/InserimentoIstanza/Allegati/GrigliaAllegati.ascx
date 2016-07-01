<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GrigliaAllegati.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.GrigliaAllegati" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<asp:GridView runat="server" ID="gvAllegati" 
								AutoGenerateColumns="false" 
								DataKeyNames="Id" 
								OnRowDeleting="OnRowDeleting" 
								OnRowUpdating="OnRowUpdating" 
								OnRowCommand="OnRowCommand" 
								GridLines="None" >
	<Columns>
		<asp:TemplateField HeaderText="&nbsp;" ItemStyle-Width="16px">
			<ItemTemplate>
				<cc2:AttributiAllegato runat="server" ID="attributiAllegato" 
														Obbligatorio='<%# Eval("Richiesto") %>' 
														ContieneNote='<%# Eval("HaNote") %>' 
														RichiedeFirma='<%# Eval("RichiedeFirmaDigitale") %>' 
														IdAllegato='<%# Eval("Id") %>'/>
			</ItemTemplate>
		</asp:TemplateField>
							
		<asp:TemplateField HeaderText="Allegato" >
			<ItemTemplate>
				<div style="text-align:justify">
					<asp:Literal runat="server" ID="ltrDescrizioneallegato" Text='<%# Eval("Descrizione") %>' />
				</div>
			</ItemTemplate>								
		</asp:TemplateField>

		<asp:TemplateField HeaderText="Modello" ItemStyle-Width="96px">
			<ItemTemplate>
				<div class="downloadModelli">
					<asp:HyperLink runat="server" ID="lnkDownloadModello" 
													ImageUrl="~/Images/download16x16.png"
													Target="_blank" 
													ToolTip="Scarica Modello" 
													Visible='<%# Eval("HaLinkDownloadSenzaPrecompilazione") %>'
													NavigateUrl='<%#Eval("LinkDownloadSenzaPrecompilazione") %>'/>

					<asp:HyperLink runat="server" ID="lnkMostraPdf" 
													ImageUrl="~/Images/pdf16x16.gif"
													Target="_blank" 
													ToolTip='Scarica il modello in formato Pdf'
													Visible='<%#Eval("HaLinkPdf") %>'
													NavigateUrl='<%#Eval("LinkPdf") %>'  />

					<asp:HyperLink runat="server" ID="lnkMostraPdfCompilabile" 
													ImageUrl="~/Images/pdf16x16.gif"
													Target="_blank" 
													ToolTip='Scarica il modello in formato Pdf'
													Visible='<%#Eval("HaLinkPdfCompilabile") %>'
													NavigateUrl='<%#Eval("LinkPdfCompilabile") %>'  />

					<asp:HyperLink runat="server" ID="lnkMostraRtf" 
													ImageUrl="~/Images/rtf16x16.gif"
													Target="_blank" 
													ToolTip='Scarica il modello in formato Rtf' 
													Visible='<%#Eval("HaLinkRtf") %>'
													NavigateUrl='<%#Eval("LinkRtf") %>' />

					<asp:HyperLink runat="server" ID="lnkMostraDoc" 
													ImageUrl="~/Images/doc16x16.gif"
													Target="_blank" 
													ToolTip='Scarica il modello in formato Word' 
													Visible='<%#Eval("HaLinkDoc") %>'
													NavigateUrl='<%#Eval("LinkDoc") %>'/>

					<asp:HyperLink runat="server" ID="lnkMostraOd" 
													ImageUrl="~/Images/od16x16.png" 
													Target="_blank" 
													ToolTip='Scarica il modello in formato Open Document' 
													Visible='<%#Eval("HaLinkOO") %>'
													NavigateUrl='<%#Eval("LinkOO") %>'/>
				</div>
			</ItemTemplate>
			<EditItemTemplate>
				&nbsp;
			</EditItemTemplate>
		</asp:TemplateField>
		<asp:TemplateField HeaderText="Nome File" ItemStyle-Width="350px">
			<ItemTemplate>
				<div class="displayPanel" idfile='<%# Eval("ID") %>'>

					<asp:HyperLink runat="server" ID="lnkMostraAllegato" 
													Target="_self"
													NavigateUrl='<%#Eval("LinkDownloadFile") %>'  
													ToolTip='<%# Eval("NomeFile") %>' 
													Text='<%# Eval("NomeFile") %>' 
													Visible='<%# Eval("HaFile") %>' />

					<asp:Label runat="server" 
								ID="lblErroreProcura" 
								CssClass="errori"
								Visible='<%#Eval("MostraAvvertimentoFirma") %>'
								Text="<br />Attenzione, il file non è stato firmato digitalmente" />

				</div>

				<div class="editPanel" idfile='<%# Eval("ID") %>'>
					<asp:FileUpload runat="server" ID="EditPostedFile" Visible='<%#	(!(bool) Eval("HaFile")) %>'/>
				</div>

				<div class="sendPanel" idfile='<%# Eval("ID") %>'>
				</div>
			</ItemTemplate>
		</asp:TemplateField>
		<asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="100px">
			<ItemTemplate>
				<div class="displayPanel" idfile='<%# Eval("ID") %>'>
					<asp:LinkButton ID="lnkEditAllegato" 
									runat="server" 
									Text="Allega" 
									CssClass="editLink actionButton"
									idFile='<%# Eval("ID") %>' 
									CommandName="Edit" 
									CausesValidation="false"
									Visible='<%# Eval("MostraBottoneAllega") %>' style="display:block"/>

					<asp:LinkButton runat="server" ID="lnkModificaPdfCompilabile" 
										ToolTip='Compila il modello in formato Pdf'
										Text="Compila on-line" 
										CommandName="Compila"
										Visible='<%# Eval("MostraBottoneCompila") %>'
										CommandArgument='<%# Eval("ID") %>' 
                                        style="display:block"
                                        CssClass="actionButton"/>

					<asp:LinkButton ID="lnkFirma" 
									runat="server" 
									Text="Firma" 
									CommandName="Firma"
									CommandArgument='<%# Eval("CodiceOggetto") %>'
									CausesValidation="false" 
									Visible='<%# Eval("MostraBottoneFirma") %>' 
                                    style="display:block"
                                    CssClass="actionButton"/>

					<asp:LinkButton ID="lnkEliminaAllegato" 
									runat="server" 
									Text="Rimuovi" 
									CommandName="Delete"
									CausesValidation="false" 
									OnClientClick="return confirm('Eliminare l\'allegato selezionato?');" 
									Visible='<%# Eval("MostraBottoneRimuovi") %>' 
                                    style="display:block"
                                    CssClass="actionButton"/>
				</div>

				<div class="editPanel" idfile='<%# Eval("ID") %>'>
					<asp:LinkButton ID="LinkButton3" 
									runat="server" 
									Text="Invia" 
									CssClass="sendLink actionButton"
									idFile='<%# Eval("ID") %>' 
									CommandName="Update" 
									Visible='<%# (!(bool)Eval("HaFile")) %>' style="display:block"/>

					<asp:LinkButton ID="LinkButton2" 
									runat="server" 
									Text="Annulla" 
									CssClass="cancelLink actionButton"
									idFile='<%# Eval("ID") %>' 
									CommandName="Cancel" 
									Visible='<%# (!(bool)Eval("HaFile")) %>' 
									CausesValidation="false" style="display:block" />
				</div>
			</ItemTemplate>
		</asp:TemplateField>
	</Columns>
</asp:GridView>