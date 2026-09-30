<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CCValiditaCoefficienti" Title="Configurazione validità coefficienti" CodeBehind="CCValiditaCoefficienti.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
        <asp:View runat="server" ID="listaView">

            <fieldset>
                <div>
                    <asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id"
                        OnSelectedIndexChanged="gvLista_SelectedIndexChanged">
<%--                        <AlternatingRowStyle CssClass="RigaAlternata" />
                        <RowStyle CssClass="Riga" />
                        <HeaderStyle CssClass="IntestazioneTabella" />
                        <EmptyDataRowStyle CssClass="NessunRecordTrovato" />--%>
                        <Columns>
                            <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" SortExpression="Id">
                                <HeaderStyle HorizontalAlign="Left" />
                            </asp:ButtonField>
                            <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" SortExpression="Descrizione">
                                <HeaderStyle HorizontalAlign="Left" />
                            </asp:BoundField>
                            <asp:BoundField DataField="Datainiziovalidita" SortExpression="Datainiziovalidita" HeaderText="Data inizio validit&#224;" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="False">
                                <ItemStyle HorizontalAlign="Left" />
                                <HeaderStyle HorizontalAlign="Left" />
                            </asp:BoundField>
                            <asp:BoundField DataField="Costomq" SortExpression="Costomq" HeaderText="Costo al mq" HtmlEncode="False">
                                <ItemStyle HorizontalAlign="Right" />
                                <HeaderStyle HorizontalAlign="Right" />
                            </asp:BoundField>
                        </Columns>
                        <EmptyDataTemplate>
                            <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
                        </EmptyDataTemplate>
                    </asp:GridView>
                </div>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                    <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
                    
                </div>
            </fieldset>
        </asp:View>

        <asp:View runat="server" ID="dettaglioView">
            <fieldset>
                <div>
                    <asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
                    <asp:Label runat="server" ID="lblCodice" />
                </div>
                <div>
                    <asp:Label runat="server" ID="label4" Text="*Descrizione" AssociatedControlID="txtDescrizione" />
                    <asp:TextBox runat="server" ID="txtDescrizione" MaxLength="200" TextMode="MultiLine" Columns="80" Rows="2" />
                </div>
                <div>
                    <asp:Label runat="server" ID="label5" Text="*Data inizio validità" AssociatedControlID="txtDataInizioValidita" />
                    <init:DateTextBox runat="server" ID="txtDataInizioValidita" />
                </div>
                <div>
                    <asp:Label runat="server" ID="label11" Text="*Costo al mq" AssociatedControlID="txtCostoMq" />
                    <init:DoubleTextBox runat="server" ID="txtCostoMq" />
                </div>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
                    <init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
                    <init:SigeproButton runat="server" ID="cmdCoefficientiContributi" Text="Coefficienti per tipo intervento" IdRisorsa="COEFFTIPIINTERVENTO" OnClick="cmdCoefficientiContributi_Click" />
                    <init:SigeproButton runat="server" ID="cmdCoeffContribAttivita" Text="Coefficienti per altre tabelle" IdRisorsa="COEFFALTRETABELLE" OnClick="cmdCoeffContribAttivita_Click" />
                    <init:SigeproButton runat="server" ID="cmdCoeffDettaglio" Text="Coefficienti per intervento e destinazione" OnClick="cmdCoeffDettaglio_Click" />
                    <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
                </div>
            </fieldset>
        </asp:View>

    </asp:MultiView>
</asp:Content>
