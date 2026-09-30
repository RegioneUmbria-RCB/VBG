<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCCoeffContributo" Title="Gestione coefficienti per tipo intervento" Codebehind="CCCoeffContributo.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
		        <div runat="server" id="divFkAreeCodiceArea">
					<asp:Label runat="server" ID="lblFkAreeCodiceArea" Text="Area" AssociatedControlID="ddlFkAreeCodiceArea" />
					<asp:DropDownList runat="server" ID="ddlFkAreeCodiceArea" DataValueField="CODICEAREA" DataTextField="DENOMINAZIONE" OnSelectedIndexChanged="ComboValueChanged" AutoPostBack="True"/>
		        </div>
		        <div>
					<asp:Label runat="server" ID="lblOCCBaseDestinazioni" Text="Destinazione di base" AssociatedControlID="ddlOCCBaseDestinazioni" />
					<asp:DropDownList runat="server" ID="ddlOCCBaseDestinazioni" DataValueField="ID" DataTextField="DESTINAZIONE" OnSelectedIndexChanged="ddlOCCBaseDestinazioni_SelectedIndexChanged" AutoPostBack="True"/>
		        </div>
		        <div>
					<asp:Label runat="server" ID="lblOCCBaseTipoIntervento" Text="Tipo intervento di base" AssociatedControlID="ddlOCCBaseTipoIntervento" />
					<asp:DropDownList runat="server" ID="ddlOCCBaseTipoIntervento" DataValueField="ID" DataTextField="INTERVENTO" OnSelectedIndexChanged="ComboValueChanged" AutoPostBack="True"/>
		        </div>
		        <div>
		            <asp:Label runat="server" ID="Label1" Text="Il coefficiente dipende dall'intervento" AssociatedControlID="chkShowAll" />
		            <asp:CheckBox runat="server" AutoPostBack="true" ID="chkShowAll" OnCheckedChanged="chkShowAll_CheckedChanged"/>
		        </div>
		        
		        <asp:Panel runat="server" ID="pnlAttivita">
					<asp:Label runat="server" ID="lblAttivita" Text="Settori" AssociatedControlID="ddlAttivita" />
					<asp:DropDownList runat="server" ID="ddlAttivita" DataTextField="Attivita" DataValueField="Id" AutoPostBack="true" OnSelectedIndexChanged="ComboValueChanged"></asp:DropDownList>
		        </asp:Panel>
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

