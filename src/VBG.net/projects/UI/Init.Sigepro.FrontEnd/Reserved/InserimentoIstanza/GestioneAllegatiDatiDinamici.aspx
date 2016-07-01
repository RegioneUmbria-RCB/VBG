<%@ Page Title="Untitled" Async="true" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneAllegatiDatiDinamici.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiDatiDinamici" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<script type="text/javascript">
		var preloadedImg = new Image();
		preloadedImg.src = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';

		require(['jquery'], function () {
			$(function () {
				$(".bottoneInvio").click(function (e) {
					$(":file").fadeOut('slow', function () {
						$(this).after($('#templateAttendere').html())
							.parent()
							.find('div')
							.fadeIn('slow');
					});
				});
			});
		});
	</script>

	<div style='display:none' id="templateAttendere">
		<img src='<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>' />
		<b>Caricamento del file in corso...</b><br />
		L'invio di un file di grandi dimensioni potrebbe richiedere anche alcuni minuti
	</div>		
	<div class="inputForm">
		<fieldset>

			<cc2:AttributiAllegato runat="server" ID="ltrLegendaAttributi" NascontiNoteCompilazioneInLegenda="true" Legenda="true" />

			<asp:GridView runat="server" ID="gvRiepiloghiDatiDinamici" 
							GridLines="None" 
							AutoGenerateColumns="false" 
							DataKeyNames="IdModello,IndiceMolteplicita" 
							OnRowDeleting="gvRiepiloghiDatiDinamici_RowDeleting"
							OnRowEditing="gvRiepiloghiDatiDinamici_RowEditing"
							OnRowCancelingEdit="gvRiepiloghiDatiDinamici_RowCancelingEdit"
							OnRowUpdating="gvRiepiloghiDatiDinamici_RowUpdating"
							OnRowCommand="gvRiepiloghiDatiDinamici_RowCommand">
				<Columns>
					<asp:TemplateField HeaderText="&nbsp;" ItemStyle-Width="32px">
						<ItemTemplate>
							<cc2:AttributiAllegato runat="server" ID="attributiAllegato" Obbligatorio='<%# Eval("Richiesto") %>' RichiedeFirma='<%# Eval("RichiedeFirmaDigitale") %>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField>
						<HeaderTemplate>
							Scheda
						</HeaderTemplate>
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrNomeFile" Text='<%# Eval("NomeScheda")%>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField ItemStyle-HorizontalAlign="Center">
						<HeaderTemplate>
							Modello
						</HeaderTemplate>
						<ItemTemplate>
							<%# Eval("LinkDownloadModello")%>
							<asp:ImageButton runat="server" ID="lnkDownloadModello" ImageUrl="~/Images/pdf16x16.gif" CommandArgument='<%#Eval("CommandArgument")%>' OnClick="MostraModelloDinamico"/>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField>
						<HeaderTemplate>Documento</HeaderTemplate>
						<ItemTemplate>

							<asp:HyperLink runat="server" ID="lnkDownloadOggetto"
											NavigateUrl='<%# Eval( "CodiceOggetto" , "~/Reserved/MostraOggettoFo.ashx?IdComune=" + IdComune + "&Software=" + Software + "&CodiceOggetto={0}&Token=" + UserAuthenticationResult.Token + "&IdPresentazione=" + IdDomanda)%>' 
											Text='<%# Eval("NomeFile") %>'
											Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>' 
											 />
							<asp:Label runat="server" 
										ID="lblErroreFirma" 
										CssClass="errori"
										Visible='<%#Eval("MostraBottoneFirma") %>'
										Text="<br />Attenzione, il file non è stato firmato digitalmente" />
						</ItemTemplate>
						
						<EditItemTemplate>
							<asp:FileUpload runat="server" ID="fuAllegato" style="width:350px"  />					
						</EditItemTemplate>
					</asp:TemplateField>
					
					<asp:TemplateField ItemStyle-HorizontalAlign="Right">
						<HeaderTemplate>&nbsp;</HeaderTemplate>
						<ItemTemplate>
							<asp:LinkButton runat="server" 
											ID="lnkEdit" 
											Text="Allega" 
											CommandName="Edit" 
											Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>'  />

							<asp:LinkButton ID="lnkFirma" 
											runat="server" 
											Text="Firma on-line" 
											CommandName="Firma"
											CommandArgument='<%# Eval("CodiceOggetto") %>'
											CausesValidation="false" 
											Visible='<%# Eval("MostraBottoneFirma") %>'/>
																						
							<asp:LinkButton runat="server" ID="lnkElimina" 
											Text="Elimina" 
											CommandName="Delete" 
											OnClientClick="return confirm('L\'allegato selezionato verrà eliminato. Per passare allo step successivo sarà necessario allegare un file firmato digitalmente. Proseguire\?')" 
											Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue && ((bool)Eval("Richiesto"))%>' />
						</ItemTemplate>
						
						<EditItemTemplate>
							<asp:LinkButton runat="server" ID="lnkUpdate" Text="Invia" CommandName="Update" CssClass="bottoneInvio" />
							<asp:LinkButton runat="server" ID="lnkCancel" Text="Annulla" CommandName="Cancel" />
						</EditItemTemplate>
					</asp:TemplateField>
					
				</Columns>
			</asp:GridView>
		</fieldset>
	</div>


</asp:Content>
