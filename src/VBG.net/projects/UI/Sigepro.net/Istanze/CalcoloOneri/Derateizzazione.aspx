<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Sigepro.net.Istanze.CalcoloOneri.Istanze_CalcoloOneri_Derateizzazione" CodeBehind="Derateizzazione.aspx.cs" Title="Derateizzazione oneri"%>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
			    <asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/> 
                    <Columns>
                        <asp:BoundField DataField="fkidtipocausale" HeaderText="Codice" />
                        <asp:BoundField DataField="co_descrizione" HeaderText="Descrizione causale" />
                        <asp:BoundField DataField="prezzo" HeaderText="Importo" >
                            <ItemStyle HorizontalAlign="Right" />
                        </asp:BoundField>
                        <asp:BoundField DataField="prezzoistruttoria" HeaderText="Importo istruttoria" >
                            <ItemStyle HorizontalAlign="Right" />
                        </asp:BoundField>
                        <asp:BoundField DataField="datascadenza" HeaderText="Data scadenza" >
                            <ItemStyle HorizontalAlign="Right" />
                        </asp:BoundField>
                        <asp:BoundField DataField="data" HeaderText="Data" >
                            <ItemStyle HorizontalAlign="Right" />
                        </asp:BoundField>
                        <asp:BoundField DataField="nr_documento" HeaderText="Nr Documento" />
                    </Columns>
                    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </asp:GridView>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdOk1" Text="Chiudi" IdRisorsa="OK" OnClick="cmdOk_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>

