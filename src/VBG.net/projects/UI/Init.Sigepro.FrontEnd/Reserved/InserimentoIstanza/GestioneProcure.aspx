<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneProcure.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneProcure" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<script type="text/javascript">
		var preloadedImg = new Image();
		preloadedImg.src = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';
	
		$(function() {
			$(".bottoneInvio").click(function(e) { 
				
			    $('.bottoneAzioni').hide();

			    $(":file").fadeOut('slow', function () {

					$(this).after($('#testoAttendere').html())
							.parent()
							.find('div')
							.fadeIn('slow');				
				});
			});
		});
		
	</script>

	<div style='display:none' id="testoAttendere">
		<img src='<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>' />
		<b>Caricamento del file in corso...</b><br />
		L'invio di un file di grandi dimensioni potrebbe richiedere anche alcuni minuti
	</div>

	<div class="inputForm">
		<fieldset>
			<asp:GridView runat="server" ID="gvProcure" GridLines="None" AutoGenerateColumns="false" 
							DataKeyNames="CodiceProcuratore,CodiceAnagrafe" 
							OnRowDeleting="gvProcure_RowDeleting"
							OnRowEditing="gvProcure_RowEditing"
							OnRowCancelingEdit="gvProcure_RowCancelingEdit"
							OnRowUpdating="gvProcure_RowUpdating"
							OnRowCommand="gvProcure_RowCommand">
				<Columns>
					<asp:TemplateField>
						<HeaderTemplate>
							Rappresentato
						</HeaderTemplate>
						<ItemTemplate>
							<%# Eval("NomeAnagrafe")%>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField>
						<HeaderTemplate>
							Procuratore
						</HeaderTemplate>
						<ItemTemplate>
							<%# Eval("NomeProcuratore")%>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField>
						<HeaderTemplate>Documento</HeaderTemplate>
						<ItemTemplate>

							<asp:HyperLink runat="server" 
										   ID="lnkDownloadOggetto" 
										   Target="_blank" 
										   Visible='<%# Eval("AllegatoPresente") %>'
										   NavigateUrl='<%# Eval( "PathDownload")%>' 
										   Text='<%# Eval("NomeFile") %>' />
							<asp:Label runat="server" ID="lblErroreProcura" 
										CssClass="errori"
										Visible='<%# (bool)Eval("AllegatoPresente") && ( (bool)Eval("RichiedeFirmaDigitale") && !(bool)Eval("IsFirmatoDigitalmente") ) %>'>
								<div>Attenzione, il file non è stato firmato digitalmente</div>
							</asp:Label>
						</ItemTemplate>
						
						<EditItemTemplate>
							<asp:FileUpload runat="server" ID="fuProcura" style="width:350px"  />					
						</EditItemTemplate>
					</asp:TemplateField>
					
					<asp:TemplateField ItemStyle-HorizontalAlign="Right">
						<HeaderTemplate>&nbsp;</HeaderTemplate>
						<ItemTemplate>
							<asp:LinkButton runat="server" ID="lnkEdit" 
											Text="Allega" 
											CommandName="Edit" 
											Visible='<%# !(bool)Eval("AllegatoPresente") %>'
                                            CssClass="bottoneAzioni"/>

							<asp:LinkButton runat="server" ID="LinkButton1" 
											Text="Firma" 
											CommandName="Firma" 
											CommandArgument='<%# Eval("CodiceOggetto") %>'
											Visible='<%# (bool)Eval("AllegatoPresente") && ( (bool)Eval("RichiedeFirmaDigitale") && !(bool)Eval("IsFirmatoDigitalmente") )%>'
                                            CssClass="bottoneAzioni"/>
						
							<asp:LinkButton runat="server" ID="lnkElimina" 
											Text="Rimuovi" 
											CommandName="Delete" 
											OnClientClick="return confirm('Eliminare il file corrente\?')" 
											Visible='<%# Eval("AllegatoPresente") %>'
                                            CssClass="bottoneAzioni"/>
						</ItemTemplate>
						
						<EditItemTemplate>
							<asp:LinkButton runat="server" ID="lnkUpdate" Text="Invia" CommandName="Update" CssClass="bottoneInvio bottoneAzioni" />
							<asp:LinkButton runat="server" ID="lnkCancel" Text="Annulla" CommandName="Cancel" CssClass="bottoneAzioni" />
						</EditItemTemplate>
					</asp:TemplateField>
					
				</Columns>
			</asp:GridView>
		</fieldset>
	</div>
</asp:Content>
