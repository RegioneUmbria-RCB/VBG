<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OConfigurazioneTipiOnere" Title="Altre configurazioni" Codebehind="OConfigurazioneTipiOnere.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
			<div>
			    <asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" OnRowDataBound="gvLista_RowDataBound" OnRowDeleting="gvLista_RowDeleting">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:TemplateField HeaderStyle-HorizontalAlign="Left" HeaderText="Onere di base"><ItemTemplate><asp:Label runat="server" ID="lblDescrizioneOneriBase"/></ItemTemplate></asp:TemplateField>
					    <asp:TemplateField HeaderStyle-HorizontalAlign="Left" HeaderText="Tipo causale"><ItemTemplate><asp:Label runat="server" ID="lblDescrizioneTipoCausaleOneri"/></ItemTemplate></asp:TemplateField>
					    <asp:TemplateField ItemStyle-HorizontalAlign="Right">
					        <ItemTemplate>
					            <input type="hidden" id="lblCodiceOnereBase" runat="server" />
					            <asp:ImageButton runat="server" ID="cmdElimina" ImageUrl="~/images/Delete.gif" AlternateText="Elimina la riga corrente" CommandName="Delete" />
					        </ItemTemplate>
                        </asp:TemplateField>
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </asp:GridView>
			</div>
				<div class="Bottoni">
				    <init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                    <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="ImageButton1_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="Label1" Text="*Onere di base" AssociatedControlID="ddlOnereBase" />
					<asp:DropDownList runat="server" ID="ddlOnereBase" DataValueField="ID" DataTextField="DESCRIZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="lblTipoCausale" Text="*Tipo causale" AssociatedControlID="ddlTipoCausaleOnere" />
					<asp:DropDownList runat="server" ID="ddlTipoCausaleOnere" DataValueField="CoId" DataTextField="CoDescrizione"/>
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>

