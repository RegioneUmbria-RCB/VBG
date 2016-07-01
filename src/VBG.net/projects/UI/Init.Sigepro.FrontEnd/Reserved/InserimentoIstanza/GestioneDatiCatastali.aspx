<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneDatiCatastali.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDatiCatastali"
	Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<asp:ScriptManager runat="server" ID="scriptManager1">
	</asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="listaView">
			<div class="inputForm">
				<fieldset>
					<div>
						<asp:GridView runat="server" ID="dgDatiCatastali" 
													 AutoGenerateColumns="False"
													 DataKeyNames="Id" 
													 OnRowDeleting="dgDatiCatastali_DeleteCommand"
													 GridLines="None">
							<Columns>
								<asp:TemplateField HeaderText="Localizzazione">
									<ItemTemplate>
										<asp:Literal runat="server" id="ltrLocalizzazione" Text='<%# GetLocalizzazione( DataBinder.Eval(Container,"DataItem")) %>' />
									</ItemTemplate>
								</asp:TemplateField>
								<asp:BoundField DataField="TipoCatasto" HeaderText="Tipo catasto"/>
								<asp:BoundField DataField="Foglio" HeaderText="Foglio"/>
								<asp:BoundField DataField="Particella" HeaderText="Particella"/>
								<asp:BoundField DataField="Sub" HeaderText="Subalterno"/>
								<asp:TemplateField ItemStyle-HorizontalAlign="Right">
									<ItemTemplate>
										<asp:LinkButton runat="server" ID="lnkElimina" Text="Rimuovi" CommandName="Delete"
											OnClientClick="return confirm('Proseguire con l\'eliminazione?')" />
									</ItemTemplate>
								</asp:TemplateField>
							</Columns>
						</asp:GridView>
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdNuovo" Text="Aggiungi" OnClick="cmdNuovo_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<script type="text/javascript">
				$(function () {
					var idTipoCatasto = '#<%=ddlTipoCatasto.ClientID %>';
					var idSubalterno = '#pnlSubalterno';

					$(idTipoCatasto).change(function () {
						var el = $(idSubalterno);
						var display = $(this).val() == 'F' ? 'block' : 'none';

						el.css('display', display);
					});

					if ($(idTipoCatasto).val() != 'F')
						$(idSubalterno).css('display', 'none');

				});
			</script>
			<div class="inputForm">
				<fieldset>
					<legend>Nuovo dato</legend>
										<div>
						<asp:Label runat="server" ID="Label5" AssociatedControlID="ddlIndirizzo" Text="Localizzazione" />
						<asp:DropDownList ID="ddlIndirizzo" runat="server" DataValueField="Key" DataTextField="Value" />
					</div>

					<div>
						<asp:Label runat="server" ID="Label1" AssociatedControlID="ddlTipoCatasto" Text="Tipo catasto" />
						<asp:DropDownList ID="ddlTipoCatasto" runat="server" DataValueField="Key" DataTextField="Value" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label2" AssociatedControlID="txtFoglio" Text="Foglio" />
						<asp:TextBox ID="txtFoglio" runat="server" Columns="5" MaxLength="5" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label3" AssociatedControlID="txtParticella" Text="Particella" />
						<asp:TextBox ID="txtParticella" runat="server" Columns="5" MaxLength="5" />
					</div>
					<div id="pnlSubalterno">
						<asp:Label runat="server" ID="Label4" AssociatedControlID="txtSub" Text="Subalterno" />
						<asp:TextBox ID="txtSub" runat="server" Columns="5" MaxLength="5" />
					</div>
					<div class="bottoni">
						<asp:Button ID="cmdAdd" runat="server" Text="Aggiungi" OnClick="cmdAdd_Click" />
						<asp:Button ID="cmdReset" runat="server" Text="Annulla" CausesValidation="False"
							OnClick="cmdReset_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
