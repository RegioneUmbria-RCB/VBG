<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCCoeffContribAttivita" Title="Gestione coefficienti per altre tabelle" Codebehind="CCCoeffContribAttivita.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
	            <div>
					<asp:Label runat="server" ID="lblOCCBaseDestinazioni" Text="Destinazione di base" AssociatedControlID="ddlOCCBaseDestinazioni" />
					<asp:DropDownList runat="server" ID="ddlOCCBaseDestinazioni" DataValueField="ID" DataTextField="DESTINAZIONE" OnSelectedIndexChanged="ddlOCCBaseDestinazioni_SelectedIndexChanged" AutoPostBack="True"/>
		        </div>
		        <div>
					<asp:Label runat="server" ID="lblConfigurazioneSettori" Text="Altre tabelle" AssociatedControlID="ddlConfigurazioneSettori" />
					<asp:DropDownList runat="server" ID="ddlConfigurazioneSettori" DataValueField="CODICESETTORE" DataTextField="SETTORE" OnSelectedIndexChanged="ddlConfigurazioneSettori_SelectedIndexChanged" AutoPostBack="True"/>
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

