<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Codebehind="GestioneEndoDomanda.aspx.cs" Inherits="Sigepro.net.Archivi.IndividuazioneProcedimenti.GestioneEndoDomanda"
	Title="Endoprocedimenti della domanda" Debug="true" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="Informazioni">
		<span class="Etichetta">Domanda:</span> <span class="Descrizione">
			<%= Domanda.Domanda %>
		</span>
	</div>
	<fieldset>
		<legend>Endoprocedimenti associati</legend>
	</fieldset>
	
	<init:GridViewEx Width="100%" ShowHeader="False" ID="gvLista" runat="server" AutoGenerateColumns="False" DatabindOnFirstLoad="True" DefaultSortDirection="Ascending"
		DefaultSortExpression="" DataSourceID="ObjectDataSource1" DataKeyNames="Codiceinventario" OnRowDeleting="gvLista_RowDeleting">
		<AlternatingRowStyle CssClass="RigaAlternata" />
		<RowStyle CssClass="Riga" />
		<HeaderStyle CssClass="IntestazioneTabella" />
		<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
		<Columns>
			<asp:BoundField DataField="Procedimento" HeaderText="Endoprocedimento"  >
				<itemstyle width="90%" />
			</asp:BoundField>
			<asp:TemplateField ShowHeader="False" >
				<itemtemplate >
<asp:ImageButton runat="server" Text="Delete" CommandName="Delete" AlternateText="Rimuove l'endoprocedimento da questa domanda" ImageUrl="~/Images/cestino.gif" CausesValidation="False" id="ImageButton1" onclientclick="return confirm('Rimuovere l\'endoprocedimento selezionato dalla domanda corrente?')"></asp:ImageButton>
</itemtemplate>
			</asp:TemplateField>
		</Columns>
		<EmptyDataTemplate>
			<asp:Label ID="Label6" runat="server">Alla domanda non sono associati endoprocedimenti.</asp:Label>
		</EmptyDataTemplate>
	</init:GridViewEx>
	
	
	<asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="FindProcedimentiDomandaFront" TypeName="Init.SIGePro.Manager.InventarioProcedimentiMgr">
		<SelectParameters>
			<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
			<asp:QueryStringParameter Name="codiceDomandaFront" QueryStringField="IdDomanda" Type="Int32" />
		</SelectParameters>
	</asp:ObjectDataSource>
	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<fieldset>
		<legend>Aggiungi endoprocedimento</legend>
		<asp:UpdatePanel ID="UpdatePanel1" runat="server">
			<ContentTemplate>
				<init:LabeledDropDownList ID="ddlFamiglia" Descrizione="Famiglia" runat="server" OnValueChanged="ddlFamiglia_ValueChanged" Item-DataTextField="Tipo" Item-DataValueField="Codice"
					Item-AutoPostBack="true" />
				<init:LabeledDropDownList ID="ddlTipologia" Descrizione="Tipologia" runat="server" Item-DataTextField="Tipo" Item-DataValueField="Codice" Item-AutoPostBack="true"
					OnValueChanged="ddlTipologia_ValueChanged" />
				<init:LabeledDropDownList ID="ddlNuovoEndo" Descrizione="Endoprocedimento" runat="server" Item-DataTextField="Procedimento" Item-DataValueField="Codiceinventario"
					Item-Width="500px" />
			</ContentTemplate>
		</asp:UpdatePanel>
		<div class="Bottoni">
			<init:SigeproButton ID="cmdAggiungi" runat="server" IdRisorsa="AGGIUNGI" OnClick="cmdAggiungi_Click" />
			<init:SigeproButton ID="cmdChiudi" runat="server" IdRisorsa="CHIUDI" OnClientClick="self.close()" />
		</div>
	</fieldset>
</asp:Content>
