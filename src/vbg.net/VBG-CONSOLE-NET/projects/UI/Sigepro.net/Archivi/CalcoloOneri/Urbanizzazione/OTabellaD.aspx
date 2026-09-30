<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OTabellaD" Title="Parametri per la determinazione del contributo in base alle classi di costo per numero di addetti all'impianto" Codebehind="OTabellaD.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
		        <div>
					<asp:Label runat="server" ID="lblODestinazioni" Text="Destinazione" AssociatedControlID="ddlODestinazioni" />
					<asp:DropDownList runat="server" ID="ddlODestinazioni" DataValueField="ID" DataTextField="DESTINAZIONE" AutoPostBack="True" OnSelectedIndexChanged="ddlODestinazioni_SelectedIndexChanged"/>
		        </div>
		        <div>
					<asp:Label runat="server" ID="lblOCCBaseTipoIntervento" Text="Tipo intervento di base" AssociatedControlID="ddlOCCBaseTipoIntervento" />
					<asp:DropDownList runat="server" ID="ddlOCCBaseTipoIntervento" DataValueField="ID" DataTextField="INTERVENTO" AutoPostBack="True" OnSelectedIndexChanged="ddlOCCBaseTipoIntervento_SelectedIndexChanged"/>
		        </div>
		        <div>
		            <asp:Label runat="server" ID="Label1" Text="Il coefficiente dipende dall'intervento" AssociatedControlID="chkShowAll" />
		            <asp:CheckBox runat="server" AutoPostBack="true" ID="chkShowAll" OnCheckedChanged="chkShowAll_CheckedChanged"/>
		        </div>
		        <div>
			        <asp:GridView runat="server" ID="gvLista" OnRowDataBound="gvLista_RowDataBound">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
			        </asp:GridView>
		        </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>

